package priv.kgame.lib.ecs.test.interval;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.test.interval.component.ComponentInterval1;
import priv.kgame.lib.ecs.test.interval.component.ComponentInterval2;
import priv.kgame.lib.ecs.test.interval.component.ComponentInterval3;
import priv.kgame.lib.ecs.test.interval.group.SysGroupInterval;
import priv.kgame.lib.ecs.test.interval.group.SysGroupSpawnInterval;

class EcsIntervalTest {
    private EcsWorld ecsWorld;

    @BeforeEach
    void setUp() {
        // 在每个测试方法执行前都会执行这个方法
        System.out.println("Setting up test...");
        ecsWorld = new EcsWorld("priv.kgame.lib.ecs.test.b");
        ecsWorld.registerSystemGroup(SysGroupSpawnInterval.class);
        ecsWorld.registerSystemGroup(SysGroupInterval.class);
    }

    @Test
    void updateWorld() throws InterruptedException {
        Entity entity = ecsWorld.createEntityByFactory(2);
        final int tickInterval = 33;
        // 记录开始时间
        long startTime = 0;
        // 设置结束时间（1分钟后）
        long endTime = startTime + 60000; // 60000ms = 1分钟

        ComponentInterval1 componentInterval1 = entity.getComponent(ComponentInterval1.class);
        ComponentInterval2 componentInterval2 = entity.getComponent(ComponentInterval2.class);
        ComponentInterval3 componentInterval3 = entity.getComponent(ComponentInterval3.class);
        // 循环更新，直到达到1分钟
        while (startTime < endTime) {
            // 更新ECS世界
            ecsWorld.tryUpdate(startTime);
            assert componentInterval1.i1.equals("i1");
            if (startTime % tickInterval == 0) {
                assert componentInterval2.i2.equals("i2");
            }
            if (startTime % (tickInterval * 2) == 0) {
                assert componentInterval3.i3.equals("i1i2");
            }
            startTime += tickInterval;
        }

        // 清理资源
        ecsWorld.dispose();
    }
} 