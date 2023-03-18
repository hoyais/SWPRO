package exam.addObject;

import java.util.Scanner;

class Solution {
	private static Scanner sc;
    private static UserSolution userSolution = new UserSolution();

    private final static int CMD_INIT = 100;
    private final static int CMD_ADD = 200;
    private final static int CMD_GROUP = 300;
    private final static int CMD_UNGROUP = 400;
    private final static int CMD_MOVE = 500;

    private static int N;

    private static boolean run() throws Exception {

        int query_num = sc.nextInt();
        int query, mID, y1, x1, y2, x2;
        
        boolean ok = false;

        for (int q = 0; q < query_num; q++) {
        	int ret, ans;
        	
            query = sc.nextInt();

            if (query == CMD_INIT) {
            	N = sc.nextInt();
            	userSolution.init(N);
                ok = true;
            } 
            else if (query == CMD_ADD) {
            	mID = sc.nextInt();
            	y1 = sc.nextInt();
            	x1 = sc.nextInt();
            	y2 = sc.nextInt();
            	x2 = sc.nextInt();
            	
                userSolution.addObject(mID, y1, x1, y2, x2);
            } 
            else if(query == CMD_GROUP) {
                mID = sc.nextInt();
                y1 = sc.nextInt();
                x1 = sc.nextInt();
                y2 = sc.nextInt();
                x2 = sc.nextInt();
                
                ret = userSolution.group(mID, y1, x1, y2, x2);
                ans = sc.nextInt();

                if (ret != ans)
                    ok = false;
            	
            }
        	else if (query == CMD_UNGROUP) {
                y1 = sc.nextInt();
                x1 = sc.nextInt();
                ret = userSolution.ungroup(y1, x1);
                ans = sc.nextInt();

                if (ret != ans)
                    ok = false;
            }
        	else if(query == CMD_MOVE)
        	{
                y1= sc.nextInt();
                x1= sc.nextInt();
                y2= sc.nextInt();
                x2= sc.nextInt();
                ret = userSolution.moveObject(y1, x1, y2, x2);
                ans= sc.nextInt();

                if (ret != ans)
                    ok = false;
        	}
        }
        return ok;
    }

    public static void main(String[] args) throws Exception {
        int T, MARK;

//        System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        sc = new Scanner(System.in);

        T = sc.nextInt();
        MARK = sc.nextInt();

        for (int tc = 1; tc <= T; tc++) {
            int score = run() ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }

        sc.close();
    }
}
