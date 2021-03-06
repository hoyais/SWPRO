package exam.parallel;

import java.util.*;

class UserSolutionOrg {
    public static int MAX_INIT_PROC = 1000;
    public static int MAX_TIME = 10000000;
    public static int MAX_TASK_LEN = 10;
    public static int MAX_PROES = 20;
    public static boolean isDeug = false;

    class Task implements Comparable<Task> {
        int timestamp;
        char mTask[];
        int mOperation;     // remain operation count
        int mMaxProes;      // max process count
        String sName;
        int processorCnt;   // set processor count
        int status;         //  0: wating, 1: processing, 2:complete

        Task(int timestamp, char mTask[], int mOperation, int mMaxProes) {
            this.timestamp = timestamp;
            this.sName = charToStr(mTask);
            this.mOperation = mOperation;
            this.mMaxProes = mMaxProes;
            this.processorCnt = 0;
            this.status = 0;
        }

        @Override
        public int compareTo(Task target) {
            // not assigned process fully
            if (this.status == 1 && this.mOperation >= 0 && this.mOperation < this.mMaxProes) {
                return -1;
            } else if (target.status == 1 && target.mOperation >= 0 && target.mOperation < target.mMaxProes) {
                return 1;
            } else if (this.status == 0 && target.status == 0) {
                if (this.timestamp < target.timestamp) {
                    return -1;
                } else {
                    return 1;
                }
            }
            return 1;
        }

        @Override
        public String toString() {
            return "Task{" +
                    "timestamp=" + timestamp +
                    ", mTask=" + Arrays.toString(mTask) +
                    ", mOperation=" + mOperation +
                    ", mMaxProes=" + mMaxProes +
                    ", sName='" + sName + '\'' +
                    ", processorCnt=" + processorCnt +
                    ", status=" + status +
                    '}';
        }
    }

    int currProcCount;
    int currTime;
    PriorityQueue<Task> worklist;
    PriorityQueue<Task> waitlist;
    HashMap<String, Task> thash;

    void init(int mProcessors) {
        currProcCount = mProcessors;
        currTime = 0;
        worklist = new PriorityQueue<>();
        waitlist = new PriorityQueue<>();
        thash = new HashMap<>();
    }

    void newTask(int timestamp, char mTask[], int mOperations, int mMaxProes) {
        processWorking(timestamp);

        Task task = new Task(timestamp, mTask, mOperations, mMaxProes);
        thash.put(task.sName, task);
        waitlist.offer(task);
        setProcess();

        if (worklist.peek() != null)
            if (isDeug) System.out.println("newTask worklist +(" + timestamp+") : " + worklist.peek().toString());

        if (waitlist.peek() != null)
            if (isDeug) System.out.println("newTask waitlist +(" + timestamp+") : " + waitlist.peek().toString());
    }

    void addProcessors(int timestamp, int mProcessors) {
        processWorking(timestamp);
        currProcCount = currProcCount + mProcessors;
        setProcess();

        if (worklist.peek() != null)
            if (isDeug) System.out.println("addProcessors worklist +(" + timestamp+") : " + worklist.peek().toString());

        if (waitlist.peek() != null)
            if (isDeug) System.out.println("addProcessors waitlist +(" + timestamp+") : " + waitlist.peek().toString());
    }

    int getTaskOperations(int timestamp, char mTask[]) {
        processWorking(timestamp);
        String sTask = charToStr(mTask);

        if (isDeug) System.out.println("getTaskOperation +(" + timestamp+") : " + sTask + " - "
                + thash.get(sTask).mOperation);
        return thash.get(sTask).mOperation;
    }

    public void processWorking(int timestamp) {
        // working job
        for (int i = currTime; i < timestamp; i++) {
            Iterator<Task> iter = worklist.iterator();

            while (iter.hasNext()) {
                Task task = iter.next();

                if (task.status == 1) {
                    task.mOperation = task.mOperation - task.processorCnt;
                }

                if (task.mOperation <= 0) {
                    task.mOperation = 0;
                    currProcCount = currProcCount + task.processorCnt;
                    task.status = 2;
                    iter.remove();
                }
            }
            // set processor
            setProcess();
        }

        currTime = timestamp;
    }

    public void setProcess() {
        // Working process check and set remain process if that task is not full
        Task workTask = worklist.peek();

        if (workTask != null && workTask.processorCnt < workTask.mMaxProes) {
            if (workTask.mMaxProes - workTask.processorCnt >= currProcCount) {
                workTask.processorCnt = workTask.processorCnt + currProcCount;
                currProcCount = 0;
            } else {
                currProcCount = currProcCount - (workTask.mMaxProes - workTask.processorCnt);
                workTask.processorCnt = workTask.mMaxProes;
            }
        }

        while (currProcCount > 0) {
            // worklist's processor is already full or null. Set waitlist task to worklist and set processor
            // change status, set process, add to worklist
            Task waitTask = waitlist.poll();

            if (waitTask != null) {
                waitTask.status = 1;    //  0: wating, 1: processing, 2:complete

                if (waitTask.mMaxProes >= currProcCount) {
                    waitTask.processorCnt = currProcCount;
                    currProcCount = 0;
                } else {
                    waitTask.processorCnt = waitTask.mMaxProes;
                    currProcCount = currProcCount - waitTask.mMaxProes;
                }
                worklist.offer(waitTask);
            } else {
                break;
            }
        }
    }

    public String charToStr(char[] args) {
        int i=0;
        StringBuffer sb = new StringBuffer();
        while (args[i] != '\0') {
            sb.append(args[i]);
            i++;
        }

        return sb.toString();
    }
}