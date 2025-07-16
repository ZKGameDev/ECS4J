package org.kgame.lib.ecstest.component.remove.immediately;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kgame.lib.ecs.EcsWorld;
import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove2;
import org.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove3;

/**
 * System默认执行顺序测试用例
 */
class EcsComponentRemoveTest {
    private EcsWorld ecsWorld;
    public static String data = "";

    @BeforeEach
    void setUp() {
        System.out.println("Setting up " + this.getClass().getSimpleName() + "...");
        String packageName = EcsComponentRemoveTest.class.getPackage().getName();
        ecsWorld = EcsWorld.generateInstance(packageName);
    }

    @Test
    void updateWorld() throws InterruptedException {
        Entity entity = ecsWorld.createEntity(1);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;
        boolean inited = false;
        boolean destroy = false;
        boolean addComponents = false;
        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            ecsWorld.update(startTime);
            ComponentRemove2 a2 = entity.getComponent(ComponentRemove2.class);
            if (!destroy) {
                System.out.println("update result: " + a2.data);
                if (!inited) {
                    inited = true;
                    assert a2.data.equals("A1o1o2o3a1a2a3");
                } else if (startTime <= endTime / 2){
                    System.out.println("update result: " + a2.data);
                    assert a2.data.equals("a1a2a3");
                } else {
                    if (!addComponents) {
                        entity.removeComponent(ComponentRemove3.class);
                        System.out.println("add ComponentA3");
                        assert a2.data.equals("a1a2a3");
                        addComponents = true;
                    } else {
                        assert a2.data.equals("a1");
                    }

                }
            } else {
                assert a2 == null;
            }
            if (startTime >= endTime - interval * 10 && !destroy) {
                destroy = true;
                ecsWorld.requestDestroyEntity(entity);
                System.out.println("request destroy entity");
            }
            startTime += interval;
        }

        // 清理资源
        ecsWorld.close();
    }
}