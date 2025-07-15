package priv.kgame.lib.ecs.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.kgame.lib.ecs.EcsCleanable;
import priv.kgame.lib.ecs.EcsSystem;
import priv.kgame.lib.ecs.annotation.UpdateAfterSystem;
import priv.kgame.lib.ecs.annotation.UpdateBeforeSystem;
import priv.kgame.lib.ecs.tools.Heap;
import priv.kgame.lib.ecs.tools.HeapTypeElement;
import priv.kgame.lib.ecs.tools.SystemDependency;

import java.util.*;

public class SortableSystemList implements EcsCleanable {
    private static final Logger logger = LogManager.getLogger(SortableSystemList.class);

    private boolean needSortSystem = true;
    private final List<EcsSystem> systemToUpdate = new ArrayList<>();
    private final Map<Class<?>, Integer> lookupMap = new HashMap<>();

    public void tryReorderSystem() {
        if (!needSortSystem) {
            return;
        }
        needSortSystem = false;

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
                throw new RuntimeException("EcsSystem dependency could be stuck in an endless loop! group:" + this.getClass().getSimpleName());
            }
            if (++currentStep > maxStep) {
                throw new RuntimeException("EcsSystem dependency could be stuck in an endless loop! group:" + this.getClass().getSimpleName());
            }
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

    private void refreshLookupMap(SystemDependency[] systemDependencies) {
        lookupMap.clear();
        for (int i = 0; i < systemDependencies.length; i++) {
            lookupMap.put(systemDependencies[i].getSystemClass(), i);
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

    private int lookupSystemIndex(Class<? extends EcsSystem> dependencyClass) {
        return lookupMap.getOrDefault(dependencyClass, -1);
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

    public Collection<EcsSystem> getSortedSystem() {
        tryReorderSystem();
        return systemToUpdate;
    }

    @Override
    public void clean() {
        for (EcsSystem system : systemToUpdate) {
            system.clean();
        }
        systemToUpdate.clear();
        lookupMap.clear();
    }

    public void addSystem(EcsSystem system) {
        if (systemToUpdate.contains(system)) {
            logger.warn("SortableSystemList addSystem failed! reason: {} already exist!", system.getClass().getSimpleName());
            return;
        }
        systemToUpdate.add(system);
        needSortSystem = true;
    }

    public void removeSystem(EcsSystem system) {
        if (!systemToUpdate.contains(system)) {
            logger.warn("SortableSystemList removeSystem failed! reason: {} not exist!", system.getClass().getSimpleName());
            return;
        }
        systemToUpdate.remove(system);
        needSortSystem = true;
    }

    @Override
    public String toString() {
        return "SortableSystemList{" +
                "needSortSystem=" + needSortSystem +
                ", systemToUpdate=" + systemToUpdate +
                ", lookupMap=" + lookupMap +
                '}';
    }
}
