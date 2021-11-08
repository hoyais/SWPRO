package exam.stock;

import java.util.*;

class UserSolution {

    private static int MAX_NUM = 200000;
    private static int MAX_STOCK = 5;
    private static int MAX_PRICE = 1000000;
    public static boolean isDebug = true;

    class Order implements Comparable<Order> {
        int mNumber;
        int mStock;
        int mQuantity;
        int mPrice;
        int bsType;     // 1:buy, 2:sell, 3:cancel

        Order(int mNumber, int mStock, int mQuantity, int mPrice, int bsType) {
            this.mNumber = mNumber;
            this.mStock = mStock;
            this.mQuantity = mQuantity;
            this.mPrice = mPrice;
            this.bsType = bsType;
        }

        @Override
        public int compareTo(Order pqOrder) {
            int rtn = 0;

            if (this.bsType == 1) {
                // add order by High Price - Small mNumber
                if (this.mPrice < pqOrder.mPrice ||
                        (this.mPrice == pqOrder.mPrice && this.mNumber > pqOrder.mNumber)) {
                    rtn = 1;
                } else {
                    rtn = -1;
                }
            } else {
                // add order by Lower Price - Small mNumber
                if (this.mPrice > pqOrder.mPrice ||
                        (this.mPrice == pqOrder.mPrice && this.mNumber > pqOrder.mNumber)) {
                    rtn = 1;
                } else {
                    rtn = -1;
                }
            }
            return rtn;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "mNumber=" + mNumber +
                    ", mStock=" + mStock +
                    ", mQuantity=" + mQuantity +
                    ", mPrice=" + mPrice +
                    ", bsType=" + bsType +
                    '}';
        }
    }

    HashMap<Integer, PriorityQueue<Order>> buyHash;
    HashMap<Integer, PriorityQueue<Order>> sellHash;
    int minPrice[];
    int maxBenefit[];
    int orderStock[];     // [mNumber] = [bsType] 1:buy, 2:sell, 3:cancel

//    public void printString(String method, int args) {
//        if (isDebug) System.out.println(method + " : " + args);
//    }

    public void init() {
        buyHash = new HashMap<>();
        sellHash = new HashMap<>();
        minPrice = new int[] {MAX_PRICE,MAX_PRICE,MAX_PRICE,MAX_PRICE,MAX_PRICE,MAX_PRICE};
        maxBenefit = new int[6];
        orderStock = new int[MAX_NUM];
    }

    public int buy(int mNumber, int mStock, int mQuantity, int mPrice) {
        Order ord = new Order(mNumber, mStock, mQuantity, mPrice, 1);
        orderStock[mNumber] = 1;

        ord = bsProcess(ord, 1);
        int remainQty = ord.mQuantity;
        if (remainQty > 0) {
            PriorityQueue<Order> pq;
            if (buyHash.get(mStock) == null) {
                pq = new PriorityQueue<>();
                pq.offer(ord);
            } else {
                pq = buyHash.get(mStock);
                pq.offer(ord);
            }
            buyHash.put(mStock, pq);
        }

//        printString("buy : ", remainQty);
        return remainQty;
    }

    public int sell(int mNumber, int mStock, int mQuantity, int mPrice) {
//        printString("sell start : ", mQuantity);
        Order ord = new Order(mNumber, mStock, mQuantity, mPrice, 2);
        orderStock[mNumber] = 2;

        ord = bsProcess(ord, 2);
        int remainQty = ord.mQuantity;
        if (remainQty > 0) {
            PriorityQueue<Order> pq;
            if (sellHash.get(mStock) == null) {
                pq = new PriorityQueue<>();
                pq.offer(ord);
            } else {
                pq = sellHash.get(mStock);
                pq.offer(ord);
            }
            sellHash.put(mStock, pq);
        }
//        printString("sell : ", remainQty);
        return remainQty;
    }

    public void cancel(int mNumber) {
        orderStock[mNumber] = 3;     // bsType - 1:buy, 2:sell, 3:cancel
    }

    public int bestProfit(int mStock) {
//        printString("bestProfit : ", maxBenefit[mStock]);
        return maxBenefit[mStock];
    }

    public Order bsProcess(Order ord, int bsType) {
        // checkSell hash by stock
        PriorityQueue<Order> pq;

        // 1:buy, 2:sell, 3:cancel
        if (bsType == 1) {
            pq = sellHash.get(ord.mStock);
        } else if (bsType == 2) {
            pq = buyHash.get(ord.mStock);
        } else {
            pq = null;
        }

        if (pq != null && pq.size()>0) {
            while (!pq.isEmpty()) {
                Order marketOrd = pq.poll();

                if (orderStock[marketOrd.mNumber] == 3) {
//                    System.out.println("cancel marketOrd : " + marketOrd);
                    continue;
                }

//                System.out.println("bsProcess bsType : " + bsType + " order " + ord);
//                System.out.println("bsProcess " + marketOrd);

                if (bsType == 1) {  // buy
                    if (ord.mPrice >= marketOrd.mPrice && marketOrd.mQuantity > 0) {
                        // matching price and Quantity
                        if (ord.mQuantity >= marketOrd.mQuantity) {
                            ord.mQuantity = ord.mQuantity - marketOrd.mQuantity;
                            marketOrd.mQuantity = 0;
                        } else {
                            marketOrd.mQuantity = marketOrd.mQuantity - ord.mQuantity;
                            ord.mQuantity = 0;
                        }

                        if (marketOrd.mQuantity > 0) {
                            pq.offer(marketOrd);
                            sellHash.put(marketOrd.mStock, pq);
                        }

                        // add match price in matchPricehash
                        addMatchPrice(marketOrd.mStock, marketOrd.mPrice);
                    } else {
                        pq.offer(marketOrd);
                        break;
                    }
                } else {    // sell
                    if (ord.mPrice <= marketOrd.mPrice && marketOrd.mQuantity > 0) {
                        // matching price and Quantity
                        if (ord.mQuantity >= marketOrd.mQuantity) {
                            ord.mQuantity = ord.mQuantity - marketOrd.mQuantity;
                            marketOrd.mQuantity = 0;
                        } else {
                            marketOrd.mQuantity = marketOrd.mQuantity - ord.mQuantity;
                            ord.mQuantity = 0;
                        }

                        if (marketOrd.mQuantity > 0) {
                            pq.offer(marketOrd);
                            buyHash.put(marketOrd.mStock, pq);
                        }

                        // add match price in matchPricehash
                        addMatchPrice(marketOrd.mStock, marketOrd.mPrice);
                    } else {
                        pq.offer(marketOrd);
                        break;
                    }
                }

                if (ord.mQuantity == 0) {
                    break;
                }
            }
        }
        return ord;
    }

    public void addMatchPrice(int mStock, int mPrice) {
        if (minPrice[mStock] > mPrice) {
            minPrice[mStock] = mPrice;
        }

        if (minPrice[mStock] > 0) {
            if (maxBenefit[mStock] < (mPrice - minPrice[mStock])) {
                maxBenefit[mStock] = mPrice - minPrice[mStock];
            }
        }
    }
}