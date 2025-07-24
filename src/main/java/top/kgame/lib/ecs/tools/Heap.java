package top.kgame.lib.ecs.tools;

/**
 * 最小堆实现
 * 
 * @param <T> 堆元素类型，必须实现Comparable接口
 */
public class Heap<T extends Comparable<T>> {
    int capacity;
    int baseIndex = 1;
    Object[] element;
    int size = 0;
    
    public Heap(int capacity) {
        this.capacity = capacity;
        element = new Object[baseIndex + capacity];
    }

    public boolean isEmpty() {
        return size <= 0;
    }

    @SuppressWarnings("unchecked")
    public void insert(T obj) {
        if (size > capacity) {
            throw new RuntimeException("Heap insert failed! reason:size out of capacity!");
        }
        int i = baseIndex + size++;
        while (i > baseIndex) {
            int parentIndex = i / 2;
            T parent = (T)element[parentIndex];
            if (obj.compareTo(parent) >= 0) {
                break;
            }
            element[i] = element[parentIndex];
            i = parentIndex;
        }
        element[i] = obj;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Attempted to Peek() an empty heap.");
        }
        return (T)element[baseIndex];
    }

    @SuppressWarnings("unchecked")
    public T extract() {
        T top = (T)element[baseIndex];
        element[baseIndex] = element[size--];
        if (!isEmpty()) {
            heapIfy(baseIndex);
        }
        return top;
    }

    @SuppressWarnings("unchecked")
    private void heapIfy(int i) {
        int index = i;
        T v = (T) element[index];
        while (index <= (size / 2)) {
            int child = 2 * index;
            if (child < size && ((T)element[child + 1]).compareTo((T)element[child]) < 0 ) {
                child++;
            }
            if (v.compareTo((T)element[child]) <= 0) {
                break;
            }
            element[index] = element[child];
            index = child;
        }
        element[index] = v;
    }

    public void reorder() {
        for (int i = size / 2; i >= baseIndex; i--) {
            heapIfy(i);
        }
    }
}
