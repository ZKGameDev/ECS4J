package priv.kgame.lib.ecstest.system.interval;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecstest.system.interval.component.ComponentInterval1;
import priv.kgame.lib.ecstest.system.interval.component.ComponentInterval2;
import priv.kgame.lib.ecstest.system.interval.component.ComponentInterval3;
import priv.kgame.lib.ecstest.system.interval.group.SysGroupInterval;
import priv.kgame.lib.ecstest.system.interval.group.SysGroupSpawnInterval;

class EcsIntervalTest {
    private EcsWorld ecsWorld;

    @BeforeEach
    void setUp() {
        System.out.println("Setting up EcsIntervalTest...");
        // 在每个测试方法执行前都会执行这个方法
        String packageName = this.getClass().getPackage().getName();
        ecsWorld = EcsWorld.generateInstance(packageName);
        ecsWorld.registerSystemGroup(SysGroupSpawnInterval.class);
        ecsWorld.registerSystemGroup(SysGroupInterval.class);
    }

    @Test
    void updateWorld() {
        Entity entity = ecsWorld.createEntity(2);
        final int tickInterval = 33;
        // 记录开始时间
        long startTime = 0;
        long endTime = startTime + 6000;

        ComponentInterval1 componentInterval1 = entity.getComponent(ComponentInterval1.class);
        ComponentInterval2 componentInterval2 = entity.getComponent(ComponentInterval2.class);
        ComponentInterval3 componentInterval3 = entity.getComponent(ComponentInterval3.class);
        // 循环更新，直到达到1分钟
        while (startTime < endTime) {
            // 更新ECS世界
            ecsWorld.update(startTime);
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
        ecsWorld.close();
    }
} 