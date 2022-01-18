package exam.memopad;

import java.util.ArrayList;

class UserSolutionEx {
    ArrayList<Character> lists;

    int cursor;
    int h, w, size;
    int[][] count; // 각 W별 문자열의 갯수의 수

    // H : 메모장의높이 (1 ≤ H ≤ 300)
    // W : 메모장의 너비 (1 ≤ W ≤ 300)
    void init(int H, int W, char mStr[]) {
        this.h = H;
        this.w = W;

        count = new int[H + 1][26];
        lists = new ArrayList<>();

        int i = 0;
        while (mStr[i] != '\0') {
            lists.add(i, mStr[i]);

            count[i / w][mStr[i] - 'a']++;

            i++;
        }

        size = lists.size();
        cursor = 0;
    }

    // 한글자 입력 후 커서 우측으로 이동
    // 열이 밀려서 바뀌는 경우 갯수를 다시 계산해준다.
    void insert(char mChar) {
        int curline = cursor / w;
        // 문자열 추가
        lists.add(cursor, mChar);
        // 위에서 이미 add했으므로, i열의 count 에서는 i+1의 첫번째꺼를 빼주고
        // i+1에서는 첫열을 것을 더해주는것을 각 열별로 쭉 진행해준다
        for (int i = curline; i < (lists.size() - 1) / w; i++) {
            count[i][lists.get((i + 1) * w) - 'a']--;
            count[i + 1][lists.get((i + 1) * w) - 'a']++;
        }

        // 추가된 문자열 갯수 추가
        count[curline][mChar - 'a']++;
        // 커서 위치 변경
        cursor += 1;
    }

    // 커서 위치 이동해준다.
    char moveCursor(int mRow, int mCol) {
        char rtn;
        int pos = (mRow - 1) * w + (mCol - 1);
        if (lists.size() > pos) {
            rtn = lists.get(pos);
            cursor = pos;
        } else {
            rtn = '$';
            cursor = lists.size();
        }
        return rtn;
    }

    // 커서 뒤쪽의 문자열 갯수를 리턴한다.
    int countCharacter(char mChar)
    {
        int curline = cursor/w;
        if(curline == lists.size()-1) return 0;

        int rtn = 0;

        // 현재 줄부터 마지막줄까지 쭉 합을 구하고
        for(int i=curline; i<=(lists.size()-1)/w; i++) {
            rtn += count[i][mChar-'a'];
        }
        // 현재줄의 시작에서 현재 위치까지의 동일한 문자열수 뺀다
        for(int i=curline*w; i<=cursor-1; i++)
            if(lists.get(i) == mChar) rtn--;

        return rtn;
    }
}