package priv.kgame.lib.ecstest.system.order.custom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecstest.system.order.custom.component.ComponentA2;

/**
 * System自定义执行顺序测试用例
 */
class EcsCustomOrderTest {
    private EcsWorld ecsWorld;
    public static String data = "";

    @BeforeEach
    void setUp() {
        System.out.println("Setting up EcsDefaultOrderTest...");
        String packageName = EcsCustomOrderTest.class.getPackage().getName();
        ecsWorld = EcsWorld.generateInstance(packageName);
    }

    @Test
    void updateWorld() throws InterruptedException {
        Entity entity = ecsWorld.createEntity(1);
        int entityIndex = entity.getIndex();

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;
        boolean inited = false;
        boolean destroy = false;

        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            ecsWorld.update(startTime);
            Entity tempEntity = ecsWorld.getEntity(entityIndex);
            if (!destroy) {
                ComponentA2 a2 = tempEntity.getComponent(ComponentA2.class);
                if (!inited) {
                    inited = true;
                    System.out.println("update result: " + a2.data);
                    assert a2.data.equals("A1o4o3o1o2a5a1a3a2a4");
                } else {
                    System.out.println("update result: " + a2.data);
                    assert a2.data.equals("a5a1a3a2a4");
                }
            } else {
                assert tempEntity == null;
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