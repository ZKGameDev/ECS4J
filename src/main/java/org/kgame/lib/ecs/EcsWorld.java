package org.kgame.lib.ecs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kgame.lib.ecs.command.EntityCommandBuffer;
import org.kgame.lib.ecs.core.*;
import org.kgame.lib.ecs.extensions.component.DestroyingComponent;
import org.kgame.lib.ecs.command.EcsCommand;
import org.kgame.lib.ecs.extensions.entity.EntityFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 非线程安全，只能在单线程使用
 */
public class EcsWorld{
    private static final Logger logger = LogManager.getLogger(EcsWorld.class);
    private static final int INIT_CURRENT_TIME = -1;

    private State state = State.INIT;
    private long currentTime = INIT_CURRENT_TIME;
    private EcsClassScanner ecsClassScanner;

    private final EcsEntityManager entityManager = new EcsEntityManager(this);
    private final List<Entity> waitDestroyEntity = new ArrayList<>();

    private final EcsSystemManager systemManager = new EcsSystemManager(this);

    private EntityCommandBuffer waitUpdateCommand;

    EntityGroup getOrCreateEntityGroup(ComponentTypeQuery componentTypes) {
        return this.entityManager.getOrCreateEntityGroup(componentTypes);
    }

    public void addDelayCommand(EcsCommand command) {
        waitUpdateCommand.addCommand(command);
    }

    private enum State {
        INIT,
        WAIT_RUNNING,
        RUNNING,
        WAIT_DESTROY,
        DESTROYING,
        DESTROYED,
    }

    EcsWorld() {}

    public static EcsWorld generateInstance(String packageName) {
        EcsWorld ecsWorld = new EcsWorld();
        ecsWorld.init(packageName);
        return ecsWorld;
    }

    void init(String packageName) {
        ecsClassScanner = EcsClassScanner.getInstance(packageName);
        entityManager.init(ecsClassScanner);
        systemManager.init(ecsClassScanner);
        state = State.WAIT_RUNNING;
        waitUpdateCommand = new EntityCommandBuffer();
    }

    /**
     * 关闭World。
     * 如果在update期间调用，会等本次所有System update完成之后才执行关闭逻辑。
     */
    public void close() {
        if (state == State.INIT || state == State.DESTROYED) {
            return;
        }
        if (state == State.RUNNING) {
            state = State.WAIT_DESTROY;
            return;
        }
        logger.info("Disposing ecs world at time {}...", currentTime);
        state = State.DESTROYING;
        currentTime = INIT_CURRENT_TIME;
        waitDestroyEntity.clear();
        systemManager.clean();
        entityManager.clean();
        waitUpdateCommand.clear();
        state = State.DESTROYED;
    }

    public boolean isClosed() {
        return state == State.DESTROYED;
    }

    // 通过EntityFactory类型ID创建实体
    public Entity createEntity(int factoryTypeId) {
        EntityFactory entityFactory = entityManager.getEntityFactory(factoryTypeId);
        return entityFactory == null ? null : entityFactory.create(this.entityManager);
    }

    // 通过工厂类创建实体
    public Entity createEntity(Class<? extends EntityFactory> klass) {
        EntityFactory entityFactory = entityManager.getEntityFactory(klass);
        return entityFactory == null ? null : entityFactory.create(this.entityManager);
    }

    public void requestDestroyEntity(int entityIndex) {
        Entity entity = getEntity(entityIndex);
        if (entity != null) {
            requestDestroyEntity(entity);
        }
    }

    public void requestDestroyEntity(Entity entity) {
        entity.addComponent(DestroyingComponent.generate());
        this.waitDestroyEntity.add(entity);
    }

    public Entity getEntity(int entityIndex) {
        return entityManager.getEntity(entityIndex);
    }

    public Collection<Entity> getAllEntity() {
        return entityManager.getAllEntity();
    }

    /**
     * 执行ECS世界的更新循环,每次更新等于一次逻辑调用
     * <p>
     * 该方法是ECS系统的核心更新方法，负责执行以下操作：
     * 1. 验证时间戳的有效性（必须递增）
     * 2. 更新当前时间
     * 3. 按顺序执行所有SystemGroup的更新
     * 4. 处理待销毁的实体
     * <p>
     * 执行流程：
     * - 首先检查时间戳是否有效（now > currentTime）
     * - 更新内部时间戳
     * - 遍历所有SystemGroup，设置当前SystemGroup并执行更新
     * - 销毁所有标记为待销毁的实体
     * - 清空待销毁实体列表
     * <p>
     * 注意事项：
     * - 时间戳必须严格递增，否则将会抛出异常
     * - SystemGroup的执行顺序由注册顺序决定
     * - 实体销毁操作在所有SystemGroup更新完成后执行
     *
     * @param now 当前时间戳（毫秒），可以是逻辑时间或真实时间，必须大于上次传入的时间
     * @throws IllegalArgumentException 当时间戳无效时（now <= currentTime）抛出异常
     */
    public void update(long now) {
        if (currentTime >= now) {
            throw new IllegalArgumentException(String.format(
                "EcsWorld try update failed! reason: currentTime >= nowTime. currentTime: %d, now: %d", 
                currentTime, now));
        }
        if (state != State.WAIT_RUNNING) {
            logger.warn("EcsWorld request update failed! reason: EcsWorld has disposed");
            return;
        }
        state = State.RUNNING;
        this.currentTime = now;
        systemManager.update();
        for (Entity waitDestroyEntity : this.waitDestroyEntity) {
            entityManager.destroyEntity(waitDestroyEntity);
        }
        waitUpdateCommand.playBack();
        this.waitDestroyEntity.clear();
        if (state == State.WAIT_DESTROY) {
            close();
        } else {
            state = State.WAIT_RUNNING;
        }
    }

    public int getComponentTypeIndex(Class<?> type) {
        return ecsClassScanner.getComponentTypeIndex(type);
    }

    public long getCurrentTime() {
        return currentTime;
    }

    /**
     * 获取当前正在执行的SystemGroup
     *
     * @return 正在质学的SystemGroup的class
     */
    public EcsSystem getCurrentSystemGroupClass() {
        return systemManager.getCurrentSystemGroup();
    }
}
