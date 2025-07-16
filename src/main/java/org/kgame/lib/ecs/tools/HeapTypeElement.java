package org.kgame.lib.ecs.tools;

/**
 * 堆元素类型
 * 
 * @param <T> 堆元素类型，必须实现Comparable接口
 */
public class HeapTypeElement implements Comparable<HeapTypeElement> {
    final int unsortedIndex;
    final Class<?> type;
    final String typeName;
    final SystemDependency systemDependency;
    public HeapTypeElement(int unsortedIndex, SystemDependency sd) {
        this.unsortedIndex = unsortedIndex;
        this.type = sd.getSystemClass();
        this.systemDependency = sd;
        this.typeName = type.getName();
    }

    @Override
    public int compareTo(HeapTypeElement o) {
        int result = Integer.compare(systemDependency.getAfterSystemCount(), o.systemDependency.getAfterSystemCount());
        if (0 == result) {
            result = typeName.compareTo(o.typeName);
        }
        return result;
    }

    public int getUnsortedIndex() {
        return unsortedIndex;
    }
}
