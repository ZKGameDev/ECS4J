package org.kgame.lib.ecstest.component.add.delay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kgame.lib.ecs.EcsWorld;
import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.command.EcsCommandScope;
import org.kgame.lib.ecs.command.SystemCommandAddComponent;
import org.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd1;
import org.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd2;
import org.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd3;

/**
 * System默认执行顺序测试用例
 */
class EcsComponentDelayAddTest {
    private EcsWorld ecsWorld;
    public static String data = "";

    @BeforeEach
    void setUp() {
        System.out.println("Setting up " + this.getClass().getSimpleName() + "...");
        String packageName = EcsComponentDelayAddTest.class.getPackage().getName();
        ecsWorld = EcsWorld.generateInstance(packageName);
    }

    @Test
    void addComponentAfterSystem() {
        Entity entity = ecsWorld.createEntity(1);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;
        long addComponentTime = startTime + interval * 30;

        boolean inited = false;
        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            if (startTime == addComponentTime) {
                ComponentDelayAdd1 componentDelayAdd1 = entity.getComponent(ComponentDelayAdd1.class);
                componentDelayAdd1.command = new SystemCommandAddComponent(entity, new ComponentDelayAdd3());
                componentDelayAdd1.level = EcsCommandScope.SYSTEM;
            }

            // 更新ECS世界
            ecsWorld.update(startTime);
            ComponentDelayAdd2 a2 = entity.getComponent(ComponentDelayAdd2.class);

            System.out.println("update result: " + a2.data);
            if (!inited) {
                inited = true;
                assert a2.data.equals("A1o1o2a1");
            } else if (startTime < addComponentTime) {
                System.out.println("update result: " + a2.data);
                assert a2.data.equals("a1");
            } else if (startTime == addComponentTime) {
                assert a2.data.equals("a1a2a3");
            } else if (startTime == addComponentTime + interval) {
                assert a2.data.equals("o3a1a2a3");
            } else {
                assert a2.data.equals("a1a2a3");
            }
            startTime += interval;
        }
        // 清理资源
        ecsWorld.close();
    }

    @Test
    void addComponentAfterGroup() {
        Entity entity = ecsWorld.createEntity(1);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;
        long addComponentTime = startTime + interval * 30;

        boolean inited = false;
        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            if (startTime == addComponentTime) {
                ComponentDelayAdd1 componentDelayAdd1 = entity.getComponent(ComponentDelayAdd1.class);
                componentDelayAdd1.command = new SystemCommandAddComponent(entity, new ComponentDelayAdd3());
                componentDelayAdd1.level = EcsCommandScope.SYSTEM_GROUP;
            }

            // 更新ECS世界
            ecsWorld.update(startTime);
            ComponentDelayAdd2 a2 = entity.getComponent(ComponentDelayAdd2.class);

            System.out.println("update result: " + a2.data);
            if (!inited) {
                inited = true;
                assert a2.data.equals("A1o1o2a1");
            } else if (startTime < addComponentTime) {
                System.out.println("update result: " + a2.data);
                assert a2.data.equals("a1");
            } else if (startTime == addComponentTime) {
                assert a2.data.equals("a1a3");
            } else if (startTime == addComponentTime + interval) {
                assert a2.data.equals("o3a1a2a3");
            } else {
                assert a2.data.equals("a1a2a3");
            }
            startTime += interval;
        }
        // 清理资源
        ecsWorld.close();
    }

    @Test
    void addComponentAfterWorld() {
        Entity entity = ecsWorld.createEntity(1);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;
        long addComponentTime = startTime + interval * 30;

        boolean inited = false;
        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            if (startTime == addComponentTime) {
                ComponentDelayAdd1 componentDelayAdd1 = entity.getComponent(ComponentDelayAdd1.class);
                componentDelayAdd1.command = new SystemCommandAddComponent(entity, new ComponentDelayAdd3());
                componentDelayAdd1.level = EcsCommandScope.WORLD;
            }

            // 更新ECS世界
            ecsWorld.update(startTime);
            ComponentDelayAdd2 a2 = entity.getComponent(ComponentDelayAdd2.class);

            System.out.println("update result: " + a2.data);
            if (!inited) {
                inited = true;
                assert a2.data.equals("A1o1o2a1");
            } else if (startTime < addComponentTime) {
                System.out.println("update result: " + a2.data);
                assert a2.data.equals("a1");
            } else if (startTime == addComponentTime) {
                assert a2.data.equals("a1");
            } else if (startTime == addComponentTime + interval) {
                assert a2.data.equals("o3a1a2a3");
            } else {
                assert a2.data.equals("a1a2a3");
            }
            startTime += interval;
        }
        // 清理资源
        ecsWorld.close();
    }
}