package exam.word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
    private static BufferedReader br;
    private static UserSolution usersolution = new UserSolution();

    static final int ADD_WORD = 100;
    static final int REMOVE_WORD = 200;
    static final int CORRECT = 300;

    static final int MAX_LEN = 40000;
    static final int MAX_WORD = 10;

    private static void strConvert(String str, char[] arr)
    {
        for (int i = 0; i < str.length(); i++)
            arr[i] = str.charAt(i);
        arr[str.length()] = '\0';
    }

    private static int run() throws IOException
    {
        int ret_val = 1;

        char[] buf_b1 = new char[MAX_LEN + 2];
        char[] buf_b2 = new char[MAX_LEN + 2];
        char[] buf_s = new char[MAX_WORD + 2];

        int len = Integer.parseInt(br.readLine());
        strConvert(br.readLine(), buf_b1);

        usersolution.init(len, buf_b1);

        int N = Integer.parseInt(br.readLine());

        int ftype, start, end, ret, ans;
        for (int i = 0; i < N; ++i)
        {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            ftype = Integer.parseInt(st.nextToken());
            switch (ftype)
            {
                case ADD_WORD:
                    strConvert(st.nextToken(), buf_s);
                    usersolution.addWord(buf_s);
                    break;
                case REMOVE_WORD:
                    strConvert(st.nextToken(), buf_s);
                    usersolution.removeWord(buf_s);
                    break;
                case CORRECT:
                    start = Integer.parseInt(st.nextToken());
                    end = Integer.parseInt(st.nextToken());
                    ret = usersolution.correct(start, end);
                    ans = Integer.parseInt(st.nextToken());
                    if (ret != ans)
                        ret_val = 0;
                    break;
            }
        }

        for (int i = 0; i < len; i++)
            buf_b2[i] = '\0';
        usersolution.destroy(buf_b2);
        strConvert(br.readLine(), buf_b1);
        for (int i = 0; i < len; i++)
        {
            if (buf_b1[i] != buf_b2[i])
                ret_val = 0;
        }

        return ret_val;
    }

    public static void main(String[] args) throws Exception {
        int T, MARK;

        //System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");
        T = Integer.parseInt(stinit.nextToken());
        MARK = Integer.parseInt(stinit.nextToken());

        for (int tc = 1; tc <= T; tc++) {
            if (run() == 1)
                System.out.println("#" + tc + " " + MARK);
            else
                System.out.println("#" + tc + " 0");
        }

        br.close();
    }
}