package exam.parallel;

import java.util.HashMap;
import java.util.LinkedList;

class UserSolution {
    public static int MAX_INIT_PROC = 1000;
    public static int MAX_TIME = 10000000;
    public static int MAX_TASK = 10;
    public static int MAX_PROES = 20;

    class Task {
        int timestamp;
        char mTask[];
        int mOperation;
        int mMaxProes;
        long hName;
        String sName;

        Task(int timestamp, char mTask[], int mOperation, int mMaxProes) {
            this.timestamp = timestamp;
            this.sName = String.valueOf(mTask);
            this.hName = getLong(mTask);
            this.mOperation = mOperation;
            this.mMaxProes = mMaxProes;
        }
    }

    int initProcCount;
    HashMap<Long, LinkedList<Task>> tHash;
    int currentTime = 0;

    void init(int mProcessors) {
        initProcCount = mProcessors;
        tHash = new HashMap<>();
    }

    void newTask(int timestamp, char mTask[], int mOperations, int mMaxProes) {
    }

    void addProcessors(int timestamp, int mProcessors) {
    }

    int getTaskOperations(int timestamp, char mTask[]) {
        return -1;
    }

    public void processing(int timestamp) {
        // working job
    }

    public int calTermCount (int timestamp) {
        return timestamp - currentTime;
    }

    public static long getLong(char in[]) {
        long rval = 0;
        int i=0;

        while (in[i] != '\0') {
            rval = (rval<<5) + (in[i]-'a'+1);
        }
        return rval;
    }
}

