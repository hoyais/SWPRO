package exam.stockTime;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

class UserSolution {

    class Order implements Comparable<Order>{
        int bsType;     // 1: buy, 2: sell
        int mNumber;
        int mStock;
        int mQuantity;
        int mPrice;
        boolean isYN;

        Order(int bsType, int mNumber, int mStock, int mQuantity, int mPrice) {
            this.bsType = bsType;
            this.mNumber = mNumber;
            this.mStock = mStock;
            this.mQuantity = mQuantity;
            this.mPrice = mPrice;
            this.isYN = true;
        }

        @Override
        public int compareTo(Order target) {
            if (mPrice > target.mPrice) return 1;
            else return -1;
        }
    }

    LinkedList<Order> sList;
    LinkedList<Order> bList;

    public void init() {
    }

    public int buy(int mNumber, int mStock, int mQuantity, int mPrice) {
        return 0;
    }

    public int sell(int mNumber, int mStock, int mQuantity, int mPrice)
    {
        return 0;
    }

    public void cancel(int mNumber)
    {
    }

    public int bestProfit(int mStock)
    {
        return 0;
    }

    public void processing() {

    }

    public void orderListSort(LinkedList<Order> list) {
        Collections.sort(list);
    }
}