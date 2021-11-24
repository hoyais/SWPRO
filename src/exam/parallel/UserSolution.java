package exam.parallel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

class UserSolution {
    public static int MAX_INIT_PROC = 1000;
    public static int MAX_TIME = 10000000;
    public static int MAX_TASK_LEN = 10;
    public static int MAX_PROES = 20;

    class Task {
        int timestamp;
        char mTask[];
        int mOperation;     // remain operation count
        int mMaxProes;      // max process count
        String sName;
        int processorCnt;   // set processor count
        int status;         //  0: wating, 1: processing, 2:complete

        Task(int timestamp, char mTask[], int mOperation, int mMaxProes) {
            this.timestamp = timestamp;
            this.sName = String.valueOf(mTask);
            this.mOperation = mOperation;
            this.mMaxProes = mMaxProes;
            this.processorCnt = 0;
            this.status = 0;
        }
    }

    int currProcCount;
    int currTime;
    LinkedList<Task> tlist;
    HashMap<String,Task> thash;

    void init(int mProcessors) {
        currProcCount = mProcessors;
        currTime = 0;
        tlist = new LinkedList<>();
        thash = new HashMap<>();
    }

    void newTask(int timestamp, char mTask[], int mOperations, int mMaxProes) {
        processWorking(timestamp);

        Task task = new Task (timestamp, mTask, mOperations, mMaxProes);
        task = setProcess(task);

        tlist.add(task);
        thash.put(task.sName, task);
    }

    void addProcessors(int timestamp, int mProcessors) {
        processWorking(timestamp);

    }

    int getTaskOperations(int timestamp, char mTask[]) {
        processWorking(timestamp);
        String sTask = String.valueOf(mTask);
        return thash.get(sTask).mOperation;
    }

    public void processWorking(int timestamp) {
        // working job
        for (int i=currTime; i<timestamp; i++) {
            // need to set processor

            Iterator<Task> iter = tlist.iterator();

            while (iter.hasNext()) {
                Task task = iter.next();

                if (task.status == 1) {
                    task.mOperation = task.mOperation - task.processorCnt;
                }

                if (task.mOperation <=0) {
                    task.mOperation = 0;
                    task.status = 2;
                }
            }
        }
    }

    public Task setProcess(Task task) {
        // process setting
        if (task.mMaxProes >= currProcCount) {
            task.processorCnt = currProcCount;
            currProcCount = 0;
        } else {
            task.processorCnt = task.mMaxProes;
            currProcCount = currProcCount - task.mMaxProes;
        }

        return task;
    }
}

