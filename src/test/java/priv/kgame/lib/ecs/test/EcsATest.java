package priv.kgame.lib.ecs.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.test.a.group.SysGroupA;
import priv.kgame.lib.ecs.test.a.group.SysGroupSpawn;

class EcsATest {
    private EcsWorld ecsWorld;

    @BeforeEach
    void setUp() {
        // 在每个测试方法执行前都会执行这个方法
        System.out.println("Setting up test...");
        ecsWorld = new EcsWorld("priv.kgame.lib.ecs.test.a");
        ecsWorld.registerSystemGroup(SysGroupSpawn.class);
        ecsWorld.registerSystemGroup(SysGroupA.class);
    }

    @Test
    void updateWorld() throws InterruptedException {
        Entity entity = ecsWorld.createEntityByFactory(1);

        // 记录开始时间
        long startTime = System.currentTimeMillis();
        // 设置结束时间（1分钟后）
        long endTime = startTime + 60000; // 60000ms = 1分钟

        // 循环更新，直到达到1分钟
        while (System.currentTimeMillis() < endTime) {
            // 更新ECS世界
            ecsWorld.tryUpdate(System.currentTimeMillis());
            
            // 等待33ms
            Thread.sleep(33);
        }

        // 清理资源
        ecsWorld.dispose();
    }
}