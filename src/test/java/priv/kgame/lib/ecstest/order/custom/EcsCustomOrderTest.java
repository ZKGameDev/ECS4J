package priv.kgame.lib.ecstest.order.custom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecstest.order.custom.component.ComponentA1;
import priv.kgame.lib.ecstest.order.custom.component.ComponentA2;
import priv.kgame.lib.ecstest.order.custom.group.SysGroupA;
import priv.kgame.lib.ecstest.order.custom.group.SysGroupSpawn;

/**
 * System默认执行顺序测试用例
 */
class EcsCustomOrderTest {
    private EcsWorld ecsWorld;
    public static String data = "";

    @BeforeEach
    void setUp() {
        System.out.println("Setting up EcsDefaultOrderTest...");
        String packageName = EcsCustomOrderTest.class.getPackage().getName();
        ecsWorld = new EcsWorld(packageName);
        ecsWorld.registerSystemGroup(SysGroupSpawn.class);
        ecsWorld.registerSystemGroup(SysGroupA.class);
    }

    @Test
    void updateWorld() throws InterruptedException {
        Entity entity = ecsWorld.createEntityByFactory(1);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;
        boolean inited = false;
        boolean destroy = false;

        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            ecsWorld.tryUpdate(startTime);
            if (!destroy) {
                if (!inited) {
                    inited = true;
                    ComponentA2 a2 = entity.getComponent(ComponentA2.class);
                    System.out.println("update result: " + a2.data);
                    assert a2.data.equals("A1o4o3o1o2a5a1a3a2a4");
                } else {
                    ComponentA2 a2 = entity.getComponent(ComponentA2.class);
                    System.out.println("update result: " + a2.data);
                    assert a2.data.equals("a5a1a3a2a4");
                }
                ComponentA1 a1 = entity.getComponent(ComponentA1.class);
                a1.data = "";
            }
            if (startTime >= endTime - interval * 10 && !destroy) {
                destroy = true;
                ecsWorld.requestDestroyEntity(entity);
                System.out.println("request destroy entity");
            }
            startTime += interval;
        }

        // 清理资源
        ecsWorld.dispose();
    }
}