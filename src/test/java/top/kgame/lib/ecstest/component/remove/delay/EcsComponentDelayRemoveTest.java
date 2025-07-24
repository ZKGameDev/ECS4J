package top.kgame.lib.ecstest.component.remove.delay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import top.kgame.lib.ecs.EcsWorld;
import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.command.EcsCommandScope;
import top.kgame.lib.ecs.command.SystemCommandRemoveComponent;
import top.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove1;
import top.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove2;
import top.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove3;

/**
 * 延迟移除Component测试用例
 */
class EcsComponentDelayRemoveTest {
    private EcsWorld ecsWorld;
    public static String data = "";

    @BeforeEach
    void setUp() {
        System.out.println("Setting up " + this.getClass().getSimpleName() + "...");
        String packageName = EcsComponentDelayRemoveTest.class.getPackage().getName();
        ecsWorld = EcsWorld.generateInstance(packageName);
    }

    @Test
    void removeComponentAfterSystem() {
        Entity entity = ecsWorld.createEntity(1);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;
        long removeComponentTime = startTime + interval * 30;

        boolean inited = false;
        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            if (startTime == removeComponentTime) {
                ComponentDelayRemove1 componentDelayRemove1 = entity.getComponent(ComponentDelayRemove1.class);
                componentDelayRemove1.command = new SystemCommandRemoveComponent(entity, ComponentDelayRemove3.class);
                componentDelayRemove1.level = EcsCommandScope.SYSTEM;
            }

            // 更新ECS世界
            ecsWorld.update(startTime);
            ComponentDelayRemove2 r2 = entity.getComponent(ComponentDelayRemove2.class);

            System.out.println("update result: " + r2.data);
            if (!inited) {
                inited = true;
                assert r2.data.equals("R1o1o2o3r1r2r3");
            } else if (startTime < removeComponentTime) {
                System.out.println("update result: " + r2.data);
                assert r2.data.equals("r1r2r3");
            } else{
                assert r2.data.equals("r1");
            }
            startTime += interval;
        }
        // 清理资源
        ecsWorld.close();
    }

    @Test
    void removeComponentAfterGroup() {
        Entity entity = ecsWorld.createEntity(1);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;
        long removeComponentTime = startTime + interval * 30;

        boolean inited = false;
        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            if (startTime == removeComponentTime) {
                ComponentDelayRemove1 componentDelayRemove1 = entity.getComponent(ComponentDelayRemove1.class);
                componentDelayRemove1.command = new SystemCommandRemoveComponent(entity, ComponentDelayRemove3.class);
                componentDelayRemove1.level = EcsCommandScope.SYSTEM_GROUP;
            }

            // 更新ECS世界
            ecsWorld.update(startTime);
            ComponentDelayRemove2 r2 = entity.getComponent(ComponentDelayRemove2.class);

            System.out.println("update result: " + r2.data);
            if (!inited) {
                inited = true;
                assert r2.data.equals("R1o1o2o3r1r2r3");
            } else if (startTime < removeComponentTime) {
                System.out.println("update result: " + r2.data);
                assert r2.data.equals("r1r2r3");
            } else if (startTime == removeComponentTime) {
                assert r2.data.equals("r1r2");
            } else{
                assert r2.data.equals("r1");
            }
            startTime += interval;
        }
        // 清理资源
        ecsWorld.close();
    }

    @Test
    void removeComponentAfterWorld() {
        Entity entity = ecsWorld.createEntity(1);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;
        long removeComponentTime = startTime + interval * 30;

        boolean inited = false;
        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            if (startTime == removeComponentTime) {
                ComponentDelayRemove1 componentDelayRemove1 = entity.getComponent(ComponentDelayRemove1.class);
                componentDelayRemove1.command = new SystemCommandRemoveComponent(entity, ComponentDelayRemove3.class);
                componentDelayRemove1.level = EcsCommandScope.WORLD;
            }

            // 更新ECS世界
            ecsWorld.update(startTime);
            ComponentDelayRemove2 r2 = entity.getComponent(ComponentDelayRemove2.class);

            System.out.println("update result: " + r2.data);
            if (!inited) {
                inited = true;
                assert r2.data.equals("R1o1o2o3r1r2r3");
            } else if (startTime < removeComponentTime) {
                System.out.println("update result: " + r2.data);
                assert r2.data.equals("r1r2r3");
            } else if (startTime == removeComponentTime) {
                assert r2.data.equals("r1r2r3");
            } else {
                assert r2.data.equals("r1");
            }
            startTime += interval;
        }
        // 清理资源
        ecsWorld.close();
    }
} 