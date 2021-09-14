package exam.roller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
    final private static int CMD_INIT = 100;
    final private static int CMD_ADDRAIL = 200;
    final private static int CMD_DELRAIL = 300;

    private static BufferedReader br;
    private static UserSolution usersolution = new UserSolution();

    private static boolean run() throws IOException
    {
        boolean ret = false;
        int query_cnt, cmd;
        int usr, ans;
        int N, mRow, mCol, mFront, mDirection;

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        query_cnt = Integer.parseInt(st.nextToken());

        for (int q = 0; q < query_cnt; ++q)
        {
            st = new StringTokenizer(br.readLine(), " ");
            cmd = Integer.parseInt(st.nextToken());
            switch (cmd) {
                case CMD_INIT:
                    N = Integer.parseInt(st.nextToken());
                    usersolution.init(N);
                    ret = true;
                    break;
                case CMD_ADDRAIL:
                    mFront = Integer.parseInt(st.nextToken());
                    mDirection = Integer.parseInt(st.nextToken());
                    usersolution.addRail(mFront, mDirection);
                    break;
                case CMD_DELRAIL:
                    mRow = Integer.parseInt(st.nextToken());
                    mCol = Integer.parseInt(st.nextToken());
                    usr = usersolution.delRail(mRow, mCol);
                    ans = Integer.parseInt(st.nextToken());
                    if (usr != ans)
                        ret = false;
                    break;
                default:
                    ret = false;
                    break;
            }
        }

        return ret;
    }

    public static void main(String[] args) throws Exception
    {
        int T, MARK;

        //System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");
        T = Integer.parseInt(stinit.nextToken());
        MARK = Integer.parseInt(stinit.nextToken());

        for (int tc = 1; tc <= T; tc++)
        {
            int score = run() ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }

        br.close();
    }
}