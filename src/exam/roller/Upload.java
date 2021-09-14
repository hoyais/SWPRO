import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class UserSolution {

    private static int MAX_N = 10000;
    private static final int BS = 100;
    private static final int EW = 39;
    private static final int NS = 60;
    private static final int NE = 12;
    private static final int NW = 1011;
    private static final int SE = 45;
    private static final int SW = 78;

    private static boolean isDebug = false;
    private static boolean isDebug2 = false;


    class Rail {
        int mFront;		// 1: front, 0: rear
        int mDirection;

        int row;
        int col;
        int navi;		// 1:E, 2:W, 3:S, 4:N
        int rType;		// EW, NS, NE, NW, SE, SW

        Rail(int mFront, int mDirection, int row, int col, int navi, int rType) {
            this.mFront = mFront;
            this.mDirection = mDirection;
            this.row = row;
            this.col = col;
            this.navi = navi;
            this.rType = rType;
        }

        @Override
        public String toString() {
            return "Rail mFront : " + mFront + " mDirection : " + mDirection + " row : " + row + " col : " + col +
                    " navi : " + navi + " rType : " + rType;
        }
    }

    Map<String, Integer> map;
    LinkedList<Rail> fList;
    LinkedList<Rail> rList;


    void init(int N) {
        map = new HashMap<>();
        fList = new LinkedList<>();
        Rail fRail = new Rail(1, 1, N, N, 1, 39);
        fList.add(fRail);

        rList = new LinkedList<>();
        Rail rRail = new Rail(0, 1, N, N, 2, 39);
        rList.add(rRail);

        map.put(hashKey(N, N), BS);
    }

    public String hashKey(int row, int col) {
        String key = row + "," + col;

        return key;
    }

    void addRail(int mFront, int mDirection) {
        Rail nRail = addPosition(mFront, mDirection);

        if (mFront == 1) {
            fList.add(nRail);
        } else {
            rList.add(nRail);
        }
        map.put(hashKey(nRail.row, nRail.col), nRail.rType);

    }

    public Rail addPosition(int mFront, int mDirection) {
        Rail pRail;

        if (mFront == 1) {		// front rail
            pRail = fList.getLast();
        } else {
            pRail = rList.getLast();
        }

        Rail rail = calculateNavi(pRail, mFront, mDirection);

        return rail;
    }

    public int delRail(int mRow, int mCol) {
        int delCount = 0;

        if (map.get(hashKey(mRow, mCol)) == null || map.get(hashKey(mRow, mCol)) == 0
                || map.get(hashKey(mRow, mCol)) == BS) {
            return delCount;
        }

        delCount = deleteRailList(1, fList, mRow, mCol);

        if (delCount == 0) {
            delCount = deleteRailList(0, rList, mRow, mCol);
        }
        return delCount;
    }

    public int deleteRailList(int mFront, LinkedList<Rail> railList, int mRow, int mCol) {
        int deleteCount = 0;
        boolean isInRail = false;
        boolean isCircle = circularCheck();

        int railSize = railList.size();

        // Search rail in list
        for (int i=1; i<railSize; i++) {
            Rail rail = railList.get(i);
            if (rail.row == mRow && rail.col == mCol) {
                isInRail = true;
                break;
            }
        }

        if (isInRail) {
            if (!isCircle) {
                int idx = 1;
                int isFlag = 0;
                for (int i = 1; i < railSize; i++) {
                    Rail rail = railList.get(idx);

                    if (rail.row == mRow && rail.col == mCol) {
                        isFlag = 1;
                    }

                    if (isFlag == 1) {
                        railList.remove(idx);
                        map.remove(hashKey(mRow, mCol));
                        deleteCount++;
                    } else {
                        idx++;
                    }
                }
            } else {
                for (int i = railSize - 1; i >= 1; i--) {
                    Rail rail = railList.get(i);
                    rail.navi = changeCircleNavi(rail.navi, rail.rType);

                    if (rail.row == mRow && rail.col == mCol) {
                        railList.remove(i);
                        map.remove(hashKey(mRow, mCol));
                        deleteCount++;
                        break;
                    } else {
                        // 1: front, 0: rear
                        if (mFront == 1) {
                            rList.add(rail);
                        } else {
                            fList.add(rail);
                        }
                        railList.remove(i);
                    }
                }
            }
        }
        return deleteCount;
    }

    public int changeCircleNavi(int navi, int rType) {
        int cNavi = navi;		// 1:E, 2:W, 3:S, 4:N
        int cRtype;     // EW = 39, NS = 60, NE = 12, NW = 1011, SE = 45, SW = 78;

        if (rType == EW) {
            if (navi == 1) cNavi = 2;
            else if (navi == 2) cNavi = 1;
        } else if (rType == NS) {
            if (navi == 3) cNavi = 4;
            else if (navi == 4) cNavi = 3;
        } else if (rType == NE) {
            if (navi == 2) cNavi = 3;
            else if (navi == 3) cNavi = 2;
        } else if (rType == NW) {
            if (navi == 1) cNavi = 3;
            else if (navi == 3) cNavi = 1;
        } else if (rType == SE) {
            if (navi == 4) cNavi = 2;
            else if (navi == 2) cNavi = 4;
        } else if (rType == SW) {
            if (navi == 1) cNavi = 4;
            else if (navi == 4) cNavi = 1;
        }
        return cNavi;
    }

    public boolean circularCheck() {
        boolean isCircle = false;

        Rail fRail = fList.getLast();
        Rail rRail = rList.getLast();

        int rowGap = Math.abs(fRail.row - rRail.row);
        int colGap = Math.abs(fRail.col - rRail.col);

        // near rail (1:E, 2:W, 3:S, 4:N)
        if (rowGap == 0 && colGap == 1) {
            if ((fRail.col > rRail.col && fRail.navi == 2 && rRail.navi == 1) ||
                    (fRail.col < rRail.col && fRail.navi == 1 && rRail.navi == 2)){
                isCircle = true;
            }
        } else if (rowGap == 1 && colGap == 0) {
            if ((fRail.row > rRail.row && fRail.navi == 4 && rRail.navi == 3) ||
                    (fRail.row < rRail.row && fRail.navi == 3 && rRail.navi == 4)){
                isCircle = true;
            }
        }
        return isCircle;
    }


    public Rail calculateNavi(Rail prevRail, int mFront, int mDirection) {
        int preNavi, preRow, preCol;

        preNavi = prevRail.navi;
        preRow = prevRail.row;
        preCol = prevRail.col;

        int newNavi = 0;	// 1:E, 2:W, 3:S, 4:N
        int newRailType;	// 12, 45, 78, 1011, 39:horizon, 60:vertical
        int newRow;
        int newCol;

        if (preNavi == 1) {	// 1:E
            if (mDirection == 0) {
                newNavi = 4;
                newRailType = SE;
            } else if (mDirection == 1) {
                newNavi = 1;
                newRailType = EW;
            } else {	// mDirection == 2
                newNavi = 3;
                newRailType = NE;
            }
            newRow = preRow;
            newCol = preCol + 1;
        } else if (preNavi == 2) {	// 2:W
            if (mDirection == 0) {
                newNavi = 3;
                newRailType = NW;
            } else if (mDirection == 1) {
                newNavi = 2;
                newRailType = EW;
            } else {	// mDirection == 2
                newNavi = 4;
                newRailType = SW;
            }
            newRow = preRow;
            newCol = preCol - 1;
        } else if (preNavi == 3) {	// 3:S
            if (mDirection == 0) {
                newNavi = 1;
                newRailType = SW;
            } else if (mDirection == 1) {
                newNavi = 3;
                newRailType = NS;
            } else {	// mDirection == 2
                newNavi = 2;
                newRailType = SE;
            }
            newRow = preRow + 1;
            newCol = preCol;
        } else {	// 4:N
            if (mDirection == 0) {
                newNavi = 2;
                newRailType = NE;
            } else if (mDirection == 1) {
                newNavi = 4;
                newRailType = NS;
            } else {	// mDirection == 2
                newNavi = 1;
                newRailType = NW;
            }
            newRow = preRow - 1;
            newCol = preCol;
        }

        Rail newNRail = new Rail(mFront, mDirection, newRow, newCol, newNavi, newRailType);

        return newNRail;
    }
}