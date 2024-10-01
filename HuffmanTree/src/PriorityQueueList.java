import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PriorityQueueList<T> {//implementation of priority queue using sorted list(good for small data)
    private ArrayList<T> queue = new ArrayList<>();
    private Comparator<T> comparator;

    public PriorityQueueList(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void insert(T item) {
        queue.add(item);
        Collections.sort(queue, comparator);
    }

    public T extractMin() {
        return queue.remove(0);
    }

    public int size() {
        return queue.size();
    }
}
