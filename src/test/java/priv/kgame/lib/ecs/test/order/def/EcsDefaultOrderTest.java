package priv.kgame.lib.ecs.test.order.def;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.test.order.def.group.SysGroupA;
import priv.kgame.lib.ecs.test.order.def.group.SysGroupSpawn;

/**
 * System默认执行顺序测试用例
 */
class EcsDefaultOrderTest {
    private EcsWorld ecsWorld;
    public static String data = "";

    @BeforeEach
    void setUp() {
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
        boolean destroy = false;

        while (System.currentTimeMillis() < endTime) {
            System.out.println("=====Updating world in " + System.currentTimeMillis() + "=====");
            // 更新ECS世界
            ecsWorld.tryUpdate(System.currentTimeMillis());
            // 等待33ms
            Thread.sleep(33);

            if (System.currentTimeMillis() >= endTime - 330 && !destroy) {
                destroy = true;
                ecsWorld.requestDestroyEntity(entity);
                System.out.println("destroy entity");
            }
        }

        // 清理资源
        ecsWorld.dispose();
    }
}