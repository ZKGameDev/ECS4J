package top.kgame.lib.ecstest.entity.add.delay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import top.kgame.lib.ecs.EcsWorld;
import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.command.EcsCommandScope;
import top.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd1;
import top.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd2;
import top.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd3;

/**
 * Entity延迟添加测试用例
 */
public class EcsEntityDelayAddTest {
    public static class LogicContext {
        public Entity entity1;
        public Entity entity12;
        public Entity entity123;
        public long entity1CreateTime = 0;
        public long entity12CreateTime = 0;
        public long entity123CreateTime = 0;

        public EcsCommandScope level = null;
    }

    private EcsWorld ecsWorld;

    @BeforeEach
    void setUp() {
        System.out.println("Setting up " + this.getClass().getSimpleName() + "...");
        String packageName = EcsEntityDelayAddTest.class.getPackage().getName();
        ecsWorld = EcsWorld.generateInstance(packageName);
        ecsWorld.setContext(new LogicContext());
    }

    @Test
    void addEntityAfterSystem() {
        LogicContext context = ecsWorld.getContext();
        context.level = EcsCommandScope.SYSTEM;
        Entity entity23 = ecsWorld.createEntity(23);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;

        context.entity1CreateTime = startTime + interval * 32;
        context.entity123CreateTime = startTime + interval * 50;

        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            ecsWorld.update(startTime);

            if (startTime == context.entity1CreateTime) {
                assert context.entity1.getComponent(EntityDelayAdd1.class).updateTime == 0;
                assert context.entity1.getComponent(EntityDelayAdd1.class).spawnTime == startTime;
            }else if (context.entity1CreateTime > 0 && startTime > context.entity1CreateTime) {
                assert context.entity1.getComponent(EntityDelayAdd1.class).updateTime == startTime;
                assert context.entity1.getComponent(EntityDelayAdd1.class).spawnTime == context.entity1CreateTime;
            }

            if (context.entity12CreateTime > 0 && startTime == context.entity12CreateTime) {
                assert context.entity12.getComponent(EntityDelayAdd1.class).updateTime == 0;
                assert context.entity12.getComponent(EntityDelayAdd2.class).updateTime == 0;

                assert context.entity12.getComponent(EntityDelayAdd1.class).spawnTime == 0;
                assert context.entity12.getComponent(EntityDelayAdd2.class).spawnTime == startTime;
            }else if (context.entity12CreateTime > 0 && startTime > context.entity12CreateTime) {
                assert context.entity12.getComponent(EntityDelayAdd1.class).updateTime == startTime;
                assert context.entity12.getComponent(EntityDelayAdd1.class).spawnTime == context.entity12CreateTime + interval;

                assert context.entity12.getComponent(EntityDelayAdd2.class).updateTime == startTime;
                assert context.entity12.getComponent(EntityDelayAdd2.class).spawnTime == context.entity12CreateTime;
            }

            if (startTime == context.entity123CreateTime) {
                assert context.entity123.getComponent(EntityDelayAdd1.class).updateTime == 0;
                assert context.entity123.getComponent(EntityDelayAdd2.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd3.class).updateTime == startTime;

                assert context.entity123.getComponent(EntityDelayAdd1.class).spawnTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd2.class).spawnTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd3.class).spawnTime == startTime;
            } else if (context.entity123CreateTime > 0 && startTime > context.entity123CreateTime) {
                assert context.entity123.getComponent(EntityDelayAdd1.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd1.class).spawnTime == context.entity123CreateTime;

                assert context.entity123.getComponent(EntityDelayAdd2.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd2.class).spawnTime == context.entity123CreateTime;

                assert context.entity123.getComponent(EntityDelayAdd3.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd3.class).spawnTime == context.entity123CreateTime;
            }

            startTime += interval;
        }
        // 清理资源
        ecsWorld.close();
    }

    @Test
    void addEntityAfterGroup() {
        LogicContext context = ecsWorld.getContext();
        context.level = EcsCommandScope.SYSTEM_GROUP;
        Entity entity23 = ecsWorld.createEntity(23);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;

        context.entity1CreateTime = startTime + interval * 32;
        context.entity123CreateTime = startTime + interval * 50;

        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            ecsWorld.update(startTime);

            if (startTime == context.entity1CreateTime) {
                assert context.entity1.getComponent(EntityDelayAdd1.class).updateTime == 0;
                assert context.entity1.getComponent(EntityDelayAdd1.class).spawnTime == startTime;
            }else if (context.entity1CreateTime > 0 && startTime > context.entity1CreateTime) {
                assert context.entity1.getComponent(EntityDelayAdd1.class).updateTime == startTime;
                assert context.entity1.getComponent(EntityDelayAdd1.class).spawnTime == context.entity1CreateTime;
            }

            if (context.entity12CreateTime > 0 && startTime == context.entity12CreateTime) {
                assert context.entity12.getComponent(EntityDelayAdd1.class).updateTime == 0;
                assert context.entity12.getComponent(EntityDelayAdd2.class).updateTime == 0;

                assert context.entity12.getComponent(EntityDelayAdd1.class).spawnTime == 0;
                assert context.entity12.getComponent(EntityDelayAdd2.class).spawnTime == 0;
            }else if (context.entity12CreateTime > 0 && startTime > context.entity12CreateTime) {
                assert context.entity12.getComponent(EntityDelayAdd1.class).updateTime == startTime;
                assert context.entity12.getComponent(EntityDelayAdd1.class).spawnTime == context.entity12CreateTime + interval;

                assert context.entity12.getComponent(EntityDelayAdd2.class).updateTime == startTime;
                assert context.entity12.getComponent(EntityDelayAdd2.class).spawnTime == context.entity12CreateTime + interval;
            }

            if (startTime == context.entity123CreateTime) {
                assert context.entity123.getComponent(EntityDelayAdd1.class).updateTime == 0;
                assert context.entity123.getComponent(EntityDelayAdd2.class).updateTime == 0;
                assert context.entity123.getComponent(EntityDelayAdd3.class).updateTime == 0;

                assert context.entity123.getComponent(EntityDelayAdd1.class).spawnTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd2.class).spawnTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd3.class).spawnTime == startTime;
            } else if (context.entity123CreateTime > 0 && startTime > context.entity123CreateTime) {
                assert context.entity123.getComponent(EntityDelayAdd1.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd1.class).spawnTime == context.entity123CreateTime;

                assert context.entity123.getComponent(EntityDelayAdd2.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd2.class).spawnTime == context.entity123CreateTime;

                assert context.entity123.getComponent(EntityDelayAdd3.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd3.class).spawnTime == context.entity123CreateTime;
            }

            startTime += interval;
        }
        // 清理资源
        ecsWorld.close();
    }

    @Test
    void addEntityAfterWorld() {
        LogicContext context = ecsWorld.getContext();
        context.level = EcsCommandScope.WORLD;
        Entity entity23 = ecsWorld.createEntity(23);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;

        context.entity1CreateTime = startTime + interval * 32;
        context.entity123CreateTime = startTime + interval * 50;

        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            ecsWorld.update(startTime);

            if (startTime == context.entity1CreateTime) {
                assert context.entity1.getComponent(EntityDelayAdd1.class).updateTime == 0;
                assert context.entity1.getComponent(EntityDelayAdd1.class).spawnTime == 0;
            }else if (context.entity1CreateTime > 0 && startTime > context.entity1CreateTime) {
                assert context.entity1.getComponent(EntityDelayAdd1.class).updateTime == startTime;
                assert context.entity1.getComponent(EntityDelayAdd1.class).spawnTime == context.entity1CreateTime + interval;
            }

            if (context.entity12CreateTime > 0 && startTime == context.entity12CreateTime) {
                assert context.entity12.getComponent(EntityDelayAdd1.class).updateTime == 0;
                assert context.entity12.getComponent(EntityDelayAdd2.class).updateTime == 0;

                assert context.entity12.getComponent(EntityDelayAdd1.class).spawnTime == 0;
                assert context.entity12.getComponent(EntityDelayAdd2.class).spawnTime == 0;
            }else if (context.entity12CreateTime > 0 && startTime > context.entity12CreateTime) {
                assert context.entity12.getComponent(EntityDelayAdd1.class).updateTime == startTime;
                assert context.entity12.getComponent(EntityDelayAdd1.class).spawnTime == context.entity12CreateTime + interval;

                assert context.entity12.getComponent(EntityDelayAdd2.class).updateTime == startTime;
                assert context.entity12.getComponent(EntityDelayAdd2.class).spawnTime == context.entity12CreateTime + interval;
            }

            if (startTime == context.entity123CreateTime) {
                assert context.entity123.getComponent(EntityDelayAdd1.class).updateTime == 0;
                assert context.entity123.getComponent(EntityDelayAdd2.class).updateTime == 0;
                assert context.entity123.getComponent(EntityDelayAdd3.class).updateTime == 0;

                assert context.entity123.getComponent(EntityDelayAdd1.class).spawnTime == 0;
                assert context.entity123.getComponent(EntityDelayAdd2.class).spawnTime == 0;
                assert context.entity123.getComponent(EntityDelayAdd3.class).spawnTime == 0;
            } else if (context.entity123CreateTime > 0 && startTime > context.entity123CreateTime) {
                assert context.entity123.getComponent(EntityDelayAdd1.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd1.class).spawnTime == context.entity123CreateTime + interval;

                assert context.entity123.getComponent(EntityDelayAdd2.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd2.class).spawnTime == context.entity123CreateTime + interval;

                assert context.entity123.getComponent(EntityDelayAdd3.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityDelayAdd3.class).spawnTime == context.entity123CreateTime + interval;
            }

            startTime += interval;
        }
        // 清理资源
        ecsWorld.close();
    }
} 