package work;

import data.Data;
import data.DataStore;

import java.util.Timer;
import java.util.TimerTask;

public class Worker {
    private static Worker instance;

    public static Worker getInstance() {
        if (instance == null) instance = new Worker();
        return instance;
    }

    public Worker() {
        running = false;
        myTimer = new Timer();
        work = new WorkBySchedule();
    }

    private boolean running;
    private Timer myTimer;
    private WorkBySchedule work;
    private final int PERIOD_LONG = 1000;
    private final int PERIOD_SHORT = 25;
    private int period = PERIOD_LONG;
    private int period_old = PERIOD_LONG;

    public boolean isRunning() {
        return running;
    }

    private int emptyCounterOfQueueSize = 0;

    private class WorkBySchedule extends TimerTask {
        @Override
        public void run() {
            if (running) workWithDataInQueue();
            updateQueueInfo(dataStore.queue.size());
            if (period == PERIOD_LONG) {
                emptyCounterOfQueueSize++;
                if (emptyCounterOfQueueSize > 10) stopWork();
            }
        }
    }

    private void updateQueueInfo(int queueSize) {
        period = (queueSize == 0) ? PERIOD_LONG : PERIOD_SHORT;
        if (period != period_old) restartWork();
        period_old = period;
    }


    // Start/Stop
    private void startWork() {
        myTimer.scheduleAtFixedRate(work, 0, period);
        running = true;
    }

    private void stopWork() {
        myTimer.cancel();
        running = false;
    }

    public void startWorkProcess() {
        startWork();
    }

    public void stopWorkProcess() {
        stopWork();
    }

    private void restartWork() {
        myTimer.cancel();
        myTimer = new Timer();
        work = new WorkBySchedule();
        myTimer.scheduleAtFixedRate(work, 0, period);
        emptyCounterOfQueueSize = 0;
    }


    //Queue
    private DataStore dataStore = new DataStore();

    private void workWithDataInQueue() {
        dataStore.workWithDataInQueue();
    }

    public void addDataToWork(Data data) {
        dataStore.addDataToWork(data);
    }
}
