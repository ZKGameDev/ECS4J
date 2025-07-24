package top.kgame.lib.ecs.tools;

import top.kgame.lib.ecs.EcsSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统依赖信息
 * 
 * @param <T> 系统依赖类型，必须实现Comparable接口
 */
public class SystemDependency {
    private final EcsSystem system;
    private final List<Class<? extends EcsSystem>> updateBefore;
    private int afterSystemCount = 0;

    public SystemDependency(EcsSystem system) {
        this.system = system;
        updateBefore = new ArrayList<>();
    }

    public Class<? extends EcsSystem> getSystemClass() {
        return system.getClass();
    }

    public boolean addBeforeDependency(Class<? extends EcsSystem> dependencyClass) {
        if (updateBefore.contains(dependencyClass)) {
            return false;
        }
        updateBefore.add(dependencyClass);
        return true;
    }

    public void addAfterCount() {
        afterSystemCount++;
    }

    public EcsSystem getSystem() {
        return system;
    }

    public List<Class<? extends EcsSystem>> getUpdateBefore() {
        return updateBefore;
    }

    public int getAfterSystemCount() {
        return afterSystemCount;
    }

    public void decreaseAfterSystemCount(int i) {
        afterSystemCount -= i;
    }
}
