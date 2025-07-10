package priv.kgame.lib.ecstest.order.def;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecstest.order.def.component.ComponentA1;
import priv.kgame.lib.ecstest.order.def.component.ComponentA2;
import priv.kgame.lib.ecstest.order.def.group.SysGroupA;
import priv.kgame.lib.ecstest.order.def.group.SysGroupSpawn;

/**
 * System默认执行顺序测试用例
 */
class EcsDefaultOrderTest {
    private EcsWorld ecsWorld;
    public static String data = "";

    @BeforeEach
    void setUp() {
        System.out.println("Setting up EcsDefaultOrderTest...");
        String packageName = EcsDefaultOrderTest.class.getPackage().getName();
        ecsWorld = new EcsWorld(packageName);
        ecsWorld.registerSystemGroup(SysGroupSpawn.class);
        ecsWorld.registerSystemGroup(SysGroupA.class);
    }

    @Test
    void updateWorld() throws InterruptedException {
        Entity entity = ecsWorld.createEntityByFactory(1);

        // 记录开始时间
        long startTime = System.currentTimeMillis();
        // 设置结束时间（1分钟后）
        long endTime = startTime + 3300;
        boolean inited = false;;
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
                    assert a2.data.equals("o1o2o3o4a1a2a3a4");
                } else {
                    ComponentA2 a2 = entity.getComponent(ComponentA2.class);
                    System.out.println("update result: " + a2.data);
                    assert a2.data.equals("a1a2a3a4");
                }
                ComponentA1 a1 = entity.getComponent(ComponentA1.class);
                a1.data = "";
            }
            if (System.currentTimeMillis() >= endTime - 330 && !destroy) {
                destroy = true;
                ecsWorld.requestDestroyEntity(entity);
                System.out.println("destroy entity");
            }
            startTime += 33;
        }

        // 清理资源
        ecsWorld.dispose();
    }
}