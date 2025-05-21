package priv.kgame.lib.ecs.test.a.component;

import priv.kgame.lib.ecs.test.a.group.SysGroupA;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.system.annotation.UpdateAfterSystem;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.annotation.UpdateIntervalTime;
import priv.kgame.lib.ecs.test.a.system.SystemA1;
import priv.kgame.lib.ecs.test.a.system.SystemA2;
import priv.kgame.lib.ecs.test.a.system.SystemA3;

@UpdateInGroup(value = SysGroupA.class)
@UpdateAfterSystem(systemTypes = {SystemA1.class, SystemA2.class, SystemA3.class})
@UpdateIntervalTime(interval=0.5f)
public class ComponentA4 implements EcsComponent {
}
