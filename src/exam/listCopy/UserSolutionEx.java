package exam.listCopy;

import java.util.ArrayDeque;
import java.util.HashMap;

class UserSolutionEx {
    static final int MAX_ARRAY = 5055;
    int[] mParent = new int[MAX_ARRAY + 10];
    int[][] mSource = new int[11][200001];
    int[] mTime = new int[MAX_ARRAY];
    boolean[] mIsCopy = new boolean[MAX_ARRAY];
    ArrayDeque<ChangeInfo>[] mChangeList = new ArrayDeque[MAX_ARRAY];
    HashMap<String, Integer> mMap = new HashMap<>();
    int mNameID, mSrcID, mCall;

    public void init() {
        mNameID = 11;
        mSrcID = mCall = 0;
        mMap.clear();
        for (int i = 0; i < MAX_ARRAY; ++i) {
            mParent[i] = -1;
            mIsCopy[i] = true;
            mChangeList[i] = new ArrayDeque<>();
            mTime[i] = -1;
        }
    }

    int getParent(int x) {
        return mIsCopy[x] ? x : getParent(mParent[x]);
    }

    public void makeList(char mName[], int mLength, int mListValue[]) {
        int id = mSrcID++;
        mTime[id] = mCall++;
        System.arraycopy(mListValue, 0, mSource[id], 0, mLength);
        mMap.put(toStr(mName), id);

    }

    public void copyList(char mDest[], char mSrc[], boolean mCopy) {
        int srcId = mMap.get(toStr(mSrc));
        int dstId = mMap.computeIfAbsent(toStr(mDest), k -> mNameID++);

        mParent[dstId] = getParent(srcId);
        mIsCopy[dstId] = mCopy;
        mTime[dstId] = mCall++;

    }

    public void updateElement(char mName[], int mIndex, int mValue) {
        int id = mMap.get(toStr(mName)), parent = mParent[id];
        (mIsCopy[id] ?  mChangeList[id] : mChangeList[parent]).addFirst(new ChangeInfo(mIndex, mValue, mCall++));
    }

    public int element(char mName[], int mIndex) {
        int id = mMap.get(toStr(mName));
        int value = -1, time = -1, cur = id;
        while (cur != -1) {
            value = mParent[cur] == -1 ? mSource[cur][mIndex] : -1;
            for (ChangeInfo info : mChangeList[cur]) {
                if ((cur == id || info.mOrder < time) && info.mIndex == mIndex) {
                    value = info.mValue;
                    break;
                }
            }
            if (value != -1) break;
            time = cur == id ? (mIsCopy[cur] ? mTime[cur] : mCall) : mTime[cur];
            cur = mParent[cur];
        }
        return value;
    }

    String toStr(char s[]) {
        int n = -1; while (s[++n] != 0) ;
        return new String(s, 0, n);
    }

    class ChangeInfo {
        int mIndex, mValue, mOrder;

        public ChangeInfo(int index, int value, int order) {
            mIndex = index;
            mValue = value;
            mOrder = order;
        }
    }
}