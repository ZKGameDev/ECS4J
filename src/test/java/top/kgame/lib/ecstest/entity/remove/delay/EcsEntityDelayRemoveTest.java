package top.kgame.lib.ecstest.entity.remove.delay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import top.kgame.lib.ecs.EcsWorld;
import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.command.EcsCommandScope;
import top.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove1;
import top.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove2;
import top.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove3;

/**
 * Entity延迟移除测试用例
 */
class EcsEntityDelayRemoveTest {
    private EcsWorld ecsWorld;
    public static String data = "";

    @BeforeEach
    void setUp() {
        System.out.println("Setting up " + this.getClass().getSimpleName() + "...");
        String packageName = EcsEntityDelayRemoveTest.class.getPackage().getName();
        ecsWorld = EcsWorld.generateInstance(packageName);
    }

    @Test
    void removeEntityDelayAfterSystem()  {
        ecsWorld.setContext(EcsCommandScope.SYSTEM);
        Entity e1 = ecsWorld.createEntity(1);
        Entity e12 = ecsWorld.createEntity(12);
        Entity e123 = ecsWorld.createEntity(123);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;

        long remove1Time = startTime + interval * 20;
        EntityDelayRemove1 e1c1 = e1.getComponent(EntityDelayRemove1.class);
        e1c1.destroyTime = remove1Time;

        long remove123Time = startTime + interval * 30;
        EntityDelayRemove1 e123c1 = e123.getComponent(EntityDelayRemove1.class);
        EntityDelayRemove2 e123c2 = e123.getComponent(EntityDelayRemove2.class);
        EntityDelayRemove3 e123c3 = e123.getComponent(EntityDelayRemove3.class);
        e123c2.destroyTime = remove123Time;

        long remove12Time = startTime + interval * 40;
        EntityDelayRemove1 e12c1 = e12.getComponent(EntityDelayRemove1.class);
        EntityDelayRemove2 e12c2 = e12.getComponent(EntityDelayRemove2.class);
        e12c2.destroyTime = remove12Time;

        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            ecsWorld.update(startTime);

            if (startTime < remove1Time) {
                Entity te1 = ecsWorld.getEntity(e1.getIndex());
                assert te1 == e1;
                EntityDelayRemove1 entityRemove1 = e1.getComponent(EntityDelayRemove1.class);
                assert entityRemove1.updateTime == startTime;
            } else {
                Entity te1 = ecsWorld.getEntity(e1.getIndex());
                assert te1 == null;

                assert e1c1.updateTime == remove1Time;
            }

            if (startTime < remove123Time) {
                Entity te123 = ecsWorld.getEntity(e123.getIndex());
                assert te123 == e123;
                EntityDelayRemove1 entityRemove1 = e123.getComponent(EntityDelayRemove1.class);
                assert entityRemove1.updateTime == startTime;
                EntityDelayRemove2 entityRemove2 = e123.getComponent(EntityDelayRemove2.class);
                assert entityRemove2.updateTime == startTime;
                EntityDelayRemove3 entityRemove3 = e123.getComponent(EntityDelayRemove3.class);
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
                EntityDelayRemove1 entityRemove1 = e12.getComponent(EntityDelayRemove1.class);
                assert entityRemove1.updateTime == startTime;
                EntityDelayRemove2 entityRemove2 = e12.getComponent(EntityDelayRemove2.class);
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

    @Test
    void removeEntityDelayAfterSystemGroup()  {
        ecsWorld.setContext(EcsCommandScope.SYSTEM_GROUP);
        Entity e1 = ecsWorld.createEntity(1);
        Entity e12 = ecsWorld.createEntity(12);
        Entity e123 = ecsWorld.createEntity(123);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;

        long remove1Time = startTime + interval * 20;
        EntityDelayRemove1 e1c1 = e1.getComponent(EntityDelayRemove1.class);
        e1c1.destroyTime = remove1Time;

        long remove123Time = startTime + interval * 30;
        EntityDelayRemove1 e123c1 = e123.getComponent(EntityDelayRemove1.class);
        EntityDelayRemove2 e123c2 = e123.getComponent(EntityDelayRemove2.class);
        EntityDelayRemove3 e123c3 = e123.getComponent(EntityDelayRemove3.class);
        e123c2.destroyTime = remove123Time;

        long remove12Time = startTime + interval * 40;
        EntityDelayRemove1 e12c1 = e12.getComponent(EntityDelayRemove1.class);
        EntityDelayRemove2 e12c2 = e12.getComponent(EntityDelayRemove2.class);
        e12c2.destroyTime = remove12Time;

        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            ecsWorld.update(startTime);

            if (startTime < remove1Time) {
                Entity te1 = ecsWorld.getEntity(e1.getIndex());
                assert te1 == e1;
                EntityDelayRemove1 entityRemove1 = e1.getComponent(EntityDelayRemove1.class);
                assert entityRemove1.updateTime == startTime;
            } else {
                Entity te1 = ecsWorld.getEntity(e1.getIndex());
                assert te1 == null;

                assert e1c1.updateTime == remove1Time;
            }

            if (startTime < remove123Time) {
                Entity te123 = ecsWorld.getEntity(e123.getIndex());
                assert te123 == e123;
                EntityDelayRemove1 entityRemove1 = e123.getComponent(EntityDelayRemove1.class);
                assert entityRemove1.updateTime == startTime;
                EntityDelayRemove2 entityRemove2 = e123.getComponent(EntityDelayRemove2.class);
                assert entityRemove2.updateTime == startTime;
                EntityDelayRemove3 entityRemove3 = e123.getComponent(EntityDelayRemove3.class);
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
                EntityDelayRemove1 entityRemove1 = e12.getComponent(EntityDelayRemove1.class);
                assert entityRemove1.updateTime == startTime;
                EntityDelayRemove2 entityRemove2 = e12.getComponent(EntityDelayRemove2.class);
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

    @Test
    void removeEntityDelayAfterWorld()  {
        ecsWorld.setContext(EcsCommandScope.WORLD);
        Entity e1 = ecsWorld.createEntity(1);
        Entity e12 = ecsWorld.createEntity(12);
        Entity e123 = ecsWorld.createEntity(123);

        // 记录开始时间
        final int interval = 33;
        long startTime = 0;
        long endTime = startTime + interval * 100;

        long remove1Time = startTime + interval * 20;
        EntityDelayRemove1 e1c1 = e1.getComponent(EntityDelayRemove1.class);
        e1c1.destroyTime = remove1Time;

        long remove123Time = startTime + interval * 30;
        EntityDelayRemove1 e123c1 = e123.getComponent(EntityDelayRemove1.class);
        EntityDelayRemove2 e123c2 = e123.getComponent(EntityDelayRemove2.class);
        EntityDelayRemove3 e123c3 = e123.getComponent(EntityDelayRemove3.class);
        e123c2.destroyTime = remove123Time;

        long remove12Time = startTime + interval * 40;
        EntityDelayRemove1 e12c1 = e12.getComponent(EntityDelayRemove1.class);
        EntityDelayRemove2 e12c2 = e12.getComponent(EntityDelayRemove2.class);
        e12c2.destroyTime = remove12Time;

        while (startTime < endTime) {
            System.out.println("=====Updating world in " + startTime + "=====");
            // 更新ECS世界
            ecsWorld.update(startTime);

            if (startTime <= remove1Time) {
                Entity te1 = ecsWorld.getEntity(e1.getIndex());
                assert te1 == e1;
                EntityDelayRemove1 entityRemove1 = e1.getComponent(EntityDelayRemove1.class);
                assert entityRemove1.updateTime == startTime;
            } else {
                Entity te1 = ecsWorld.getEntity(e1.getIndex());
                assert te1 == null;

                assert e1c1.updateTime == remove1Time + interval;
            }

            if (startTime <= remove123Time) {
                Entity te123 = ecsWorld.getEntity(e123.getIndex());
                assert te123 == e123;
                EntityDelayRemove1 entityRemove1 = e123.getComponent(EntityDelayRemove1.class);
                assert entityRemove1.updateTime == startTime;
                EntityDelayRemove2 entityRemove2 = e123.getComponent(EntityDelayRemove2.class);
                assert entityRemove2.updateTime == startTime;
                EntityDelayRemove3 entityRemove3 = e123.getComponent(EntityDelayRemove3.class);
                assert entityRemove3.updateTime == startTime;
            } else {
                Entity te123 = ecsWorld.getEntity(e123.getIndex());
                assert te123 == null;

                assert e123c1.updateTime == remove123Time + interval;
                assert e123c2.updateTime == remove123Time + interval;
                assert e123c3.updateTime == remove123Time + interval;
            }

            if (startTime <= remove12Time) {
                Entity te12 = ecsWorld.getEntity(e12.getIndex());
                assert te12  == e12;
                EntityDelayRemove1 entityRemove1 = e12.getComponent(EntityDelayRemove1.class);
                assert entityRemove1.updateTime == startTime;
                EntityDelayRemove2 entityRemove2 = e12.getComponent(EntityDelayRemove2.class);
                assert entityRemove2.updateTime == startTime;
            } else {
                Entity te12 = ecsWorld.getEntity(e12.getIndex());
                assert te12  == null;

                assert e12c1.updateTime == remove12Time + interval;
                assert e12c2.updateTime == remove12Time + interval;
            }

            startTime += interval;
        }

        // 清理资源
        ecsWorld.close();
    }
} 