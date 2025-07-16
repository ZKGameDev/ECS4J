package org.kgame.lib.ecstest.component.add.immediately;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kgame.lib.ecs.EcsWorld;
import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd1;
import org.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd2;
import org.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd3;

/**
 * System默认执行顺序测试用例
 */
class EcsComponentAddTest {
    private EcsWorld ecsWorld;
    public static String data = "";

    @BeforeEach
    void setUp() {
        System.out.println("Setting up " + this.getClass().getSimpleName() + "...");
        String packageName = EcsComponentAddTest.class.getPackage().getName();
        ecsWorld = EcsWorld.generateInstance(packageName);
    }

    @Test
    void addComponentBeforeUpdate() throws InterruptedException {
        Entity entity = ecsWorld.createEntity(1);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;
        long addComponentTime = startTime + interval * 30;

        boolean inited = false;
        boolean destroy = false;
        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            if (startTime == addComponentTime) {
                Entity entity1 = ecsWorld.getEntity(entity.getIndex());
                assert entity1 == entity;
                entity1.addComponent(new ComponentAdd3());
            }
            ecsWorld.update(startTime);
            ComponentAdd2 a2 = entity.getComponent(ComponentAdd2.class);

            System.out.println("update result: " + a2.data);
            if (!inited) {
                inited = true;
                assert a2.data.equals("A1o1o2a1");
            } else if (startTime < addComponentTime) {
                System.out.println("update result: " + a2.data);
                assert a2.data.equals("a1");
            } else if (startTime == addComponentTime) {
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
    void addComponentInSystem() throws InterruptedException {
        Entity entity = ecsWorld.createEntity(1);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;
        long addComponentTime = startTime + interval * 30;

        boolean inited = false;
        boolean destroy = false;
        ComponentAdd1 componentAdd1 = entity.getComponent(ComponentAdd1.class);
        componentAdd1.addComponent3Time = addComponentTime;
        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            ecsWorld.update(startTime);
            ComponentAdd2 a2 = entity.getComponent(ComponentAdd2.class);

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
}