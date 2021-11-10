package exam.parallel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {

    private final static int MAX_LEN = 10;

    private final static int INIT = 100;
    private final static int NEW_TASK = 200;
    private final static int ADD_PROCESSORS = 300;
    private final static int GET_TASKOPERATIONS = 400;

    private static void String2Char(String s, char[] b) {
        int n = s.length();
        for (int i = 0; i < n; ++i)
            b[i] = s.charAt(i);
        b[n] = '\0';
    }

    private static UserSolution usersolution = new UserSolution();

    private static boolean run(BufferedReader br) throws Exception {
        int query_num;
        boolean ok = false;
        char mTask[] = new char[MAX_LEN + 1];
        int timestamp, mOperations, mMaxProes, mProcessors;
        int ans;

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        query_num = Integer.parseInt(st.nextToken());

        for (int i = 0; i < query_num; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int cmd = Integer.parseInt(st.nextToken());
            if (cmd == INIT) {
                mProcessors = Integer.parseInt(st.nextToken());
                usersolution.init(mProcessors);
                ok = true;
            } else if (cmd == NEW_TASK) {
                timestamp = Integer.parseInt(st.nextToken());
                String2Char(st.nextToken(), mTask);
                mOperations = Integer.parseInt(st.nextToken());
                mMaxProes = Integer.parseInt(st.nextToken());
                usersolution.newTask(timestamp, mTask, mOperations, mMaxProes);
            } else if (cmd == ADD_PROCESSORS) {
                timestamp = Integer.parseInt(st.nextToken());
                mProcessors = Integer.parseInt(st.nextToken());
                usersolution.addProcessors(timestamp, mProcessors);
            } else if (cmd == GET_TASKOPERATIONS) {
                timestamp = Integer.parseInt(st.nextToken());
                String2Char(st.nextToken(), mTask);
                int ret = 0;
                ret = usersolution.getTaskOperations(timestamp, mTask);
                ans = Integer.parseInt(st.nextToken());
                if (ans != ret)
                    ok = false;
            }
        }
        return ok;
    }

    public static void main(String[] args) throws Exception {
        // System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(br.readLine(), " ");

        int TC = Integer.parseInt(line.nextToken());
        int MARK = Integer.parseInt(line.nextToken());

        for (int testcase = 1; testcase <= TC; ++testcase) {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }
        br.close();
    }
}
