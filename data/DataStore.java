package data;

import java.util.ArrayDeque;

public class DataStore {

    public DataStore() {
        queue = new ArrayDeque<>();
    }

    public final ArrayDeque<Data> queue;

    private void qWorkWithDataInQueue() {
        if (queue.size() > 0) System.out.println(queue.poll());
        else System.err.println("No Data!!!");
    }

    public void workWithDataInQueue() {
        qWorkWithDataInQueue();
    }

    private void queueAddDataToQueue(Data data) {
        synchronized (queue) {
            queue.add(data);
        }
    }

    public void addDataToWork(Data data) {
        queueAddDataToQueue(data);
    }
}
