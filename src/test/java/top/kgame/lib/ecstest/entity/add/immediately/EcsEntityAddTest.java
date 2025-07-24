package top.kgame.lib.ecstest.entity.add.immediately;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import top.kgame.lib.ecs.EcsWorld;
import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecstest.entity.add.immediately.component.EntityAdd1;
import top.kgame.lib.ecstest.entity.add.immediately.component.EntityAdd2;
import top.kgame.lib.ecstest.entity.add.immediately.component.EntityAdd3;

/**
 * Entity添加测试用例
 */
public class EcsEntityAddTest {
    private EcsWorld ecsWorld;
    public static String data = "";

    public static class LogicContext {
        public long createTime1;
        public Entity entity1;
        public long createTime12;
        public Entity entity12;
        public long createTime123;
        public Entity entity123;
    }

    @BeforeEach
    void setUp() {
        System.out.println("Setting up " + this.getClass().getSimpleName() + "...");
        String packageName = EcsEntityAddTest.class.getPackage().getName();
        ecsWorld = EcsWorld.generateInstance(packageName);
        ecsWorld.setContext(new LogicContext());
    }

    @Test
    void addEntityBeforeUpdate() {
        Entity entity12 = ecsWorld.createEntity(12);
        Entity entity1 = null;
        Entity entity123 = null;
        Entity entity23 = null;

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;
        long addEntityTime = startTime + interval * 30;
        long addEntityTime1 = startTime + interval * 34;

        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            if (startTime == addEntityTime) {
                System.out.println("create entity type:1");
                entity1 = ecsWorld.createEntity(1);
                System.out.println("create entity type:123");
                entity123 = ecsWorld.createEntity(123);
            }
            if (startTime == addEntityTime1) {
                System.out.println("create entity type:23");
                entity23 = ecsWorld.createEntity(23);
            }
            ecsWorld.update(startTime);
            if (startTime >= addEntityTime) {
                Entity temp1 = ecsWorld.getEntity(entity1.getIndex());
                assert temp1 == entity1;
                assert temp1.getComponent(EntityAdd1.class).updateTime == startTime;
                Entity temp123 = ecsWorld.getEntity(entity123.getIndex());
                assert temp123 == entity123;
                assert temp123.getComponent(EntityAdd1.class).updateTime == startTime;
                assert temp123.getComponent(EntityAdd2.class).updateTime == startTime;
                assert temp123.getComponent(EntityAdd3.class).updateTime == startTime;
            }
            if (startTime >= addEntityTime1) {
                Entity temp23 = ecsWorld.getEntity(entity23.getIndex());
                assert temp23 == entity23;
                assert temp23.getComponent(EntityAdd2.class).updateTime == startTime;
                assert temp23.getComponent(EntityAdd3.class).updateTime == startTime;
            }
            startTime += interval;
        }
        // 清理资源
        ecsWorld.close();
    }

    @Test
    void addEntityInSystem()  {
        LogicContext context = ecsWorld.getContext();
        Entity entity = ecsWorld.createEntity(23);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;
        context.createTime1 = startTime + interval * 30;
        context.createTime123 = startTime + interval * 90;
        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            ecsWorld.update(startTime);
            if (startTime == context.createTime1) {
                assert context.entity1.getComponent(EntityAdd1.class).updateTime == 0;
                assert context.entity1.getComponent(EntityAdd1.class).spawnTime == startTime;
            }else if (startTime > context.createTime1) {
                assert context.entity1.getComponent(EntityAdd1.class).updateTime == startTime;
                assert context.entity1.getComponent(EntityAdd1.class).spawnTime == context.createTime1;
            }

            if ( context.createTime12 > 0 && startTime == context.createTime12) {
                assert context.entity12.getComponent(EntityAdd1.class).updateTime == 0;
                assert context.entity12.getComponent(EntityAdd1.class).spawnTime == 0;
                assert context.entity12.getComponent(EntityAdd2.class).updateTime == 0;
                assert context.entity12.getComponent(EntityAdd2.class).spawnTime == 0;
            }else if (context.createTime12 > 0 && startTime > context.createTime12) {
                assert context.entity12.getComponent(EntityAdd1.class).updateTime == startTime;
                assert context.entity12.getComponent(EntityAdd1.class).spawnTime == startTime;
                assert context.entity12.getComponent(EntityAdd2.class).updateTime == startTime;
                assert context.entity12.getComponent(EntityAdd2.class).spawnTime == startTime;
            }

            if (startTime == context.createTime123) {
                assert context.entity123.getComponent(EntityAdd1.class).updateTime == 0;
                assert context.entity123.getComponent(EntityAdd1.class).spawnTime == startTime;

                assert context.entity123.getComponent(EntityAdd2.class).updateTime == 0;
                assert context.entity123.getComponent(EntityAdd2.class).spawnTime == startTime;

                assert context.entity123.getComponent(EntityAdd3.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityAdd3.class).spawnTime == startTime;
            } else if (context.createTime123 > 0 && startTime > context.createTime123) {
                assert context.entity123.getComponent(EntityAdd1.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityAdd1.class).spawnTime == context.createTime123;

                assert context.entity123.getComponent(EntityAdd2.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityAdd2.class).spawnTime == context.createTime123;

                assert context.entity123.getComponent(EntityAdd3.class).updateTime == startTime;
                assert context.entity123.getComponent(EntityAdd3.class).spawnTime == context.createTime123;
            }

            startTime += interval;
        }
        // 清理资源
        ecsWorld.close();
    }
} 