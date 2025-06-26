package priv.kgame.lib.ecs.system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.system.annotation.UpdateAfterSystem;
import priv.kgame.lib.ecs.system.annotation.UpdateBeforeSystem;
import priv.kgame.lib.ecs.system.struct.Heap;
import priv.kgame.lib.ecs.system.struct.HeapTypeElement;
import priv.kgame.lib.ecs.system.struct.SystemDependency;

import java.util.*;

public abstract class EcsSystemGroup extends EcsSystem{
    private static final Logger logger = LogManager.getLogger(EcsSystemGroup.class);
    private boolean needSortSystem = false;
    protected final List<EcsSystem> systemToUpdate = new ArrayList<>();
    protected final List<EcsSystem> systemsToRemove = new ArrayList<>();
    private final Map<Class<?>, Integer> lookupMap = new HashMap<>();

    @Override
    public void onInit() {
        autoAddSystemToUpdateList();
        super.setAlwaysUpdateSystem(true);
    }

    private void autoAddSystemToUpdateList() {
        EcsWorld ecsWorld = super.getWorld();
        for (Class<? extends EcsSystem> childSystemClass : ecsWorld.getChildSystemInGroup(this)) {
            addSystemToUpdateList(childSystemClass);
        }
        sortSystemUpdateList();
        logger.info("{} order: {}", this.getClass().getSimpleName(), systemToUpdate);
    }

    private void sortSystemUpdateList() {
        if (!needSortSystem) {
            return;
        }
        needSortSystem = false;

        //支持systemGroup嵌套
        systemToUpdate.forEach( system -> {
            if (system instanceof EcsSystemGroup systemGroup) {
                systemGroup.sortSystemUpdateList();
            }
        });

        SystemDependency[] systemDependencies = generateSystemDependencyArray();
        refreshLookupMap(systemDependencies);

        for (SystemDependency systemDependency : systemDependencies) {
            processBeforeDependency(systemDependencies, systemDependency);
            processAfterDependency(systemDependencies, systemDependency);
        }

        Heap<HeapTypeElement> systemHeap = new Heap<>(systemDependencies.length);
        for (int i = 0; i < systemDependencies.length; i++) {
            SystemDependency currentDependencyInfo = systemDependencies[i];
            systemHeap.insert(new HeapTypeElement(i, currentDependencyInfo));
        }

        systemToUpdate.clear();
        int maxStep = systemDependencies.length * 100;
        int currentStep = 0;
        while (!systemHeap.isEmpty()) {
            HeapTypeElement element = systemHeap.extract();
            int systemIndex = element.getUnsortedIndex();
            SystemDependency sd = systemDependencies[systemIndex];
            if (sd.getAfterSystemCount() == 0) {
                systemToUpdate.add(sd.getSystem());
                for (Class<? extends EcsSystem> beforeSystemClass : sd.getUpdateBefore()) {
                    int beforeIndex = lookupSystemIndex(beforeSystemClass);
                    if (beforeIndex < 0) {
                        throw new RuntimeException("Bug in SortSystemUpdateList(), beforeIndex < 0");
                    }
                    SystemDependency beforeSD = systemDependencies[beforeIndex];
                    if (beforeSD.getAfterSystemCount() < 0) {
                        throw new RuntimeException("Bug in SortSystemUpdateList(), AfterSystemCount{" + beforeSD.getAfterSystemCount() + " < 0");
                    }
                    beforeSD.decreaseAfterSystemCount(1);
                }
                systemHeap.reorder();
            } else {
                throw new RuntimeException("EcsSystemGroup could be stuck in an endless loop! group:" + this.getClass().getSimpleName());
            }
            if (++currentStep > maxStep) {
                throw new RuntimeException("EcsSystemGroup could be stuck in an endless loop! group:" + this.getClass().getSimpleName());
            }
        }
    }

    private void processBeforeDependency(SystemDependency[] systemDependencies, SystemDependency currentDependencyInfo) {
        UpdateBeforeSystem beforeAnnotation = currentDependencyInfo.getSystemClass().getAnnotation(UpdateBeforeSystem.class);
        if (null == beforeAnnotation) {
            return;
        }
        Class<? extends EcsSystem>[] beforeClass = beforeAnnotation.systemTypes();
        for (Class<? extends EcsSystem> dependencyClass : beforeClass) {
            if (dependencyClass.equals(currentDependencyInfo.getSystemClass())) {
                logger.warn("Ignoring invalid [UpdateBefore] attribute on {} because a system cannot be " +
                        "updated before itself. ", currentDependencyInfo.getSystemClass());
                continue;
            }
            int dependencyIndex = lookupSystemIndex(dependencyClass);
            if (dependencyIndex < 0) {
                logger.warn("Ignoring invalid [UpdateBefore] attribute on {} because {} not in the same group {}.\n",
                        currentDependencyInfo.getSystemClass(), dependencyClass, this.getClass());
                continue;
            }
            if (currentDependencyInfo.addBeforeDependency(dependencyClass)) {
                systemDependencies[dependencyIndex].addAfterCount();
            }

        }
    }

    private void processAfterDependency(SystemDependency[] systemDependencies, SystemDependency currentDependencyInfo) {
        UpdateAfterSystem afterAnnotation = currentDependencyInfo.getSystemClass().getAnnotation(UpdateAfterSystem.class);
        if (null == afterAnnotation) {
            return;
        }
        Class<? extends EcsSystem>[] afterClass = afterAnnotation.systemTypes();
        for (Class<? extends EcsSystem> dependencyClass : afterClass) {
            if (dependencyClass.equals(currentDependencyInfo.getSystemClass())) {
                logger.warn("Ignoring invalid [UpdateAfter] attribute on {} because a system cannot be " +
                        "updated before itself. ", currentDependencyInfo.getSystemClass());
                continue;
            }
            int dependencyIndex = lookupSystemIndex(dependencyClass);
            if (dependencyIndex < 0) {
                logger.warn("Ignoring invalid [UpdateAfter] attribute on {} because {} not in the same group {}.\n",
                        currentDependencyInfo.getSystemClass(), dependencyClass, this.getClass());
                continue;
            }
            if (systemDependencies[dependencyIndex].addBeforeDependency(currentDependencyInfo.getSystemClass())) {
                currentDependencyInfo.addAfterCount();
            }
        }
    }

    private int lookupSystemIndex(Class<? extends EcsSystem> dependencyClass) {
        return lookupMap.getOrDefault(dependencyClass, -1);
    }

    private void refreshLookupMap(SystemDependency[] systemDependencies) {
        lookupMap.clear();
        for (int i = 0; i < systemDependencies.length; i++) {
            lookupMap.put(systemDependencies[i].getSystemClass(), i);
        }
    }

    private SystemDependency[] generateSystemDependencyArray() {
        SystemDependency[] systemDependencies = new SystemDependency[systemToUpdate.size()];
        for (int i = 0; i < systemToUpdate.size(); i++) {
            EcsSystem system = systemToUpdate.get(i);
            systemDependencies[i] = new SystemDependency(system);
        }
        return systemDependencies;
    }

    private void addSystemToUpdateList(Class<? extends EcsSystem> childSystemClass) {
        EcsWorld ecsWorld = super.getWorld();
        EcsSystem system = ecsWorld.createSystem(childSystemClass);
        if (null == system) {
            logger.error("addSystemToUpdateList failed! reason: generate EcsSystem failed! systemClass:{}", childSystemClass.getSimpleName());
            return;
        }
        if (systemToUpdate.contains(system)) {
            logger.warn("addSystemToUpdateList failed! reason: EcsSystem already exist! systemClass:{}", childSystemClass.getSimpleName());
            return;
        }

        systemToUpdate.add(system);
        needSortSystem = true;
    }

    public void removeSystemFromUpdateList(EcsSystem system) {
        systemToUpdate.remove(system);
        systemsToRemove.add(system);
        needSortSystem = true;
    }

    @Override
    protected void onUpdate() {
        if (needSortSystem) {
            sortSystemUpdateList();
        }

        for (EcsSystem system : systemToUpdate) {
            system.tryUpdate();
        }
        destroyRemovedSystem();
    }

    private void destroyRemovedSystem() {
        if (!systemsToRemove.isEmpty()) {
            for(EcsSystem system : systemsToRemove) {
                system.destroy();
            }
            systemsToRemove.clear();
            needSortSystem = true;
        }
    }

    @Override
    protected void onDestroy() {
        destroyRemovedSystem();
        Iterator<EcsSystem> iterator = systemToUpdate.iterator();
        while (iterator.hasNext()) {
            EcsSystem system = iterator.next();
            system.destroy();
            iterator.remove();
        }
    }
}
