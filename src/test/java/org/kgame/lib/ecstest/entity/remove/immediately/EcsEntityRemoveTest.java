package org.kgame.lib.ecstest.entity.remove.immediately;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kgame.lib.ecs.EcsWorld;
import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecstest.entity.remove.immediately.component.EntityRemove1;
import org.kgame.lib.ecstest.entity.remove.immediately.component.EntityRemove2;
import org.kgame.lib.ecstest.entity.remove.immediately.component.EntityRemove3;

/**
 * Entity立即移除测试用例
 */
class EcsEntityRemoveTest {
    private EcsWorld ecsWorld;
    public static String data = "";

    @BeforeEach
    void setUp() {
        System.out.println("Setting up " + this.getClass().getSimpleName() + "...");
        String packageName = EcsEntityRemoveTest.class.getPackage().getName();
        ecsWorld = EcsWorld.generateInstance(packageName);
    }

    @Test
    void removeEntityOutSystem() {
        Entity e1 = ecsWorld.createEntity(1);
        Entity e12 = ecsWorld.createEntity(12);
        Entity e123 = ecsWorld.createEntity(123);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;

        long remove1Time = startTime + interval * 20;
        long remove123Time = startTime + interval * 30;
        long remove12Time = startTime + interval * 40;

        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            if (startTime == remove1Time) {
                ecsWorld.requestDestroyEntity(e1);
                Entity te1 = ecsWorld.getEntity(e1.getIndex());
                assert te1 == e1;

                EntityRemove1 entityRemove1 = e1.getComponent(EntityRemove1.class);
                assert entityRemove1 != null;
            }

            if (startTime == remove123Time) {
                ecsWorld.requestDestroyEntity(e123);
                Entity te123 = ecsWorld.getEntity(e123.getIndex());
                assert te123 == e123;

                EntityRemove1 entityRemove1 = e123.getComponent(EntityRemove1.class);
                assert entityRemove1 != null;
                EntityRemove2 entityRemove2 = e123.getComponent(EntityRemove2.class);
                assert entityRemove2 != null;
                EntityRemove3 entityRemove3 = e123.getComponent(EntityRemove3.class);
                assert entityRemove3 != null;
            }

            if (startTime == remove12Time) {
                ecsWorld.requestDestroyEntity(e12.getIndex());
                Entity te12 = ecsWorld.getEntity(e12.getIndex());
                assert te12 == e12;

                EntityRemove1 entityRemove1 = e12.getComponent(EntityRemove1.class);
                assert entityRemove1 != null;
                EntityRemove2 entityRemove2 = e12.getComponent(EntityRemove2.class);
                assert entityRemove2 != null;
            }
            // 更新ECS世界
            ecsWorld.update(startTime);

            if (startTime < remove1Time) {
                Entity te1 = ecsWorld.getEntity(e1.getIndex());
                assert te1 == e1;
                EntityRemove1 entityRemove1 = e1.getComponent(EntityRemove1.class);
                assert entityRemove1.updateTime == startTime;
            } else {
                Entity te1 = ecsWorld.getEntity(e1.getIndex());
                assert te1 == null;
            }

            if (startTime < remove123Time) {
                Entity te123 = ecsWorld.getEntity(e123.getIndex());
                assert te123 == e123;
                EntityRemove1 entityRemove1 = e123.getComponent(EntityRemove1.class);
                assert entityRemove1.updateTime == startTime;
                EntityRemove2 entityRemove2 = e123.getComponent(EntityRemove2.class);
                assert entityRemove2.updateTime == startTime;
                EntityRemove3 entityRemove3 = e123.getComponent(EntityRemove3.class);
                assert entityRemove3.updateTime == startTime;
            } else {
                Entity te123 = ecsWorld.getEntity(e123.getIndex());
                assert te123 == null;
            }

            if (startTime < remove12Time) {
                Entity te12 = ecsWorld.getEntity(e12.getIndex());
                assert te12  == e12;
                EntityRemove1 entityRemove1 = e12.getComponent(EntityRemove1.class);
                assert entityRemove1.updateTime == startTime;
                EntityRemove2 entityRemove2 = e12.getComponent(EntityRemove2.class);
                assert entityRemove2.updateTime == startTime;
            } else {
                Entity te12 = ecsWorld.getEntity(e12.getIndex());
                assert te12  == null;
            }

            startTime += interval;
        }

        // 清理资源
        ecsWorld.close();
    }

    @Test
    void removeEntityInSystem() {
        Entity e1 = ecsWorld.createEntity(1);
        Entity e12 = ecsWorld.createEntity(12);
        Entity e123 = ecsWorld.createEntity(123);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;

        long remove1Time = startTime + interval * 20;
        EntityRemove1 e1c1 = e1.getComponent(EntityRemove1.class);
        e1c1.destroyTime = remove1Time;
        
        long remove123Time = startTime + interval * 30;
        EntityRemove1 e123c1 = e123.getComponent(EntityRemove1.class);
        EntityRemove2 e123c2 = e123.getComponent(EntityRemove2.class);
        EntityRemove3 e123c3 = e123.getComponent(EntityRemove3.class);
        e123c2.destroyTime = remove123Time;
        
        long remove12Time = startTime + interval * 40;
        EntityRemove1 e12c1 = e12.getComponent(EntityRemove1.class);
        EntityRemove2 e12c2 = e12.getComponent(EntityRemove2.class);
        e12c2.destroyTime = remove12Time;
        
        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            ecsWorld.update(startTime);

            if (startTime < remove1Time) {
                Entity te1 = ecsWorld.getEntity(e1.getIndex());
                assert te1 == e1;
                EntityRemove1 entityRemove1 = e1.getComponent(EntityRemove1.class);
                assert entityRemove1.updateTime == startTime;
            } else {
                Entity te1 = ecsWorld.getEntity(e1.getIndex());
                assert te1 == null;

                assert e1c1.updateTime == remove1Time;
            }

            if (startTime < remove123Time) {
                Entity te123 = ecsWorld.getEntity(e123.getIndex());
                assert te123 == e123;
                EntityRemove1 entityRemove1 = e123.getComponent(EntityRemove1.class);
                assert entityRemove1.updateTime == startTime;
                EntityRemove2 entityRemove2 = e123.getComponent(EntityRemove2.class);
                assert entityRemove2.updateTime == startTime;
                EntityRemove3 entityRemove3 = e123.getComponent(EntityRemove3.class);
                assert entityRemove3.updateTime == startTime;
            } else {
                Entity te123 = ecsWorld.getEntity(e123.getIndex());
                assert te123 == null;

                assert e123c1.updateTime == remove123Time;
                assert e123c2.updateTime == remove123Time;
                assert e123c3.updateTime == remove123Time;
            }

            if (startTime < remove12Time) {
                Entity te12 = ecsWorld.getEntity(e12.getIndex());
                assert te12  == e12;
                EntityRemove1 entityRemove1 = e12.getComponent(EntityRemove1.class);
                assert entityRemove1.updateTime == startTime;
                EntityRemove2 entityRemove2 = e12.getComponent(EntityRemove2.class);
                assert entityRemove2.updateTime == startTime;
            } else {
                Entity te12 = ecsWorld.getEntity(e12.getIndex());
                assert te12  == null;

                assert e12c1.updateTime == remove12Time;
                assert e12c2.updateTime == remove12Time;
            }
            
            startTime += interval;
        }

        // 清理资源
        ecsWorld.close();
    }
} 