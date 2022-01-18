package exam.memopad;

import java.util.ArrayList;
import java.util.List;

class UserSolution
{
    private static int MAX_H = 300;
    private static int MAX_W = 300;

    int countArray[][];  // height, alpha count | count character per 0~26 with memo pad
    List<Character> memoList;

    int height, width;   // height, width
    int curH, curW;

    void init(int H, int W, char mStr[]) {
        this.height = H;
        this.width = W;

        this.curH = 0;
        this.curW = 0;

        countArray = new int[height][26];
        memoList = new ArrayList();
        int i=0;
        while (mStr[i] != '\0') {
            memoList.add(mStr[i]);
            countArray[i/width][mStr[i]-'a']++;
            i++;
        }
    }

    void insert(char mChar)
    {

    }

    char moveCursor(int mRow, int mCol)
    {
        return '$';
    }

    int countCharacter(char mChar)
    {
        return -1;
    }
}