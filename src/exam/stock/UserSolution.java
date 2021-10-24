package exam.stock;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

class UserSolution {

    private static int MAX_NUM = 200000;
    private static int MAX_STOCK = 5;
    private static int MAX_PRICE = 1000000;
    public static boolean isDebug = false;

    class Order {
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
    }

    HashMap<Integer, LinkedList<Order>> buyHash;
    HashMap<Integer, LinkedList<Order>> sellHash;
    int minPrice[];
    int maxBenefit[];
    int orderStock[][];     // [mNumber][bsType]

    public void printString(String method, int args) {
//        if (isDebug) System.out.println(method + " : " + args);
    }

    public void printList(LinkedList<Integer> list) {
//        if (list !=null && list.size()>0) {
//            for (int i=0; i<list.size(); i++) {
//                System.out.print(list.get(i) + " ");
//            }
//            System.out.println();
//        }
    }

    public void printTime(String func) {
        Date date = new Date();
        System.out.println(func + " : " + date.getTime());
    }

    public void init() {
        buyHash = new HashMap<>();
        sellHash = new HashMap<>();
        minPrice = new int[] {MAX_PRICE,MAX_PRICE,MAX_PRICE,MAX_PRICE,MAX_PRICE,MAX_PRICE};
        maxBenefit = new int[6];
        orderStock = new int[MAX_NUM][2];
    }

    public int buy(int mNumber, int mStock, int mQuantity, int mPrice) {
        Order ord = new Order(mNumber, mStock, mQuantity, mPrice, 1);
        orderStock[mNumber][1] = mStock;

        ord = bsProcess(ord, 1);
        int remainQty = ord.mQuantity;
        if (remainQty > 0) {
            LinkedList<Order> list;
            if (buyHash.get(mStock) == null) {
                list = new LinkedList<>();
                list.add(ord);
            } else {
                list = buyHash.get(mStock);
                boolean addFlag = false;
                for (int i=0; i<list.size(); i++) {
                    Order curData = list.get(i);

                    // add order by High Price - Small mNumber
                    if (ord.mPrice > curData.mPrice ||
                            (ord.mPrice == curData.mPrice && ord.mNumber < curData.mNumber)) {
                        list.add(i, ord);
                        addFlag = true;
                        break;
                    }
                }

                if (!addFlag) {
                    list.add(ord);
                }
            }
            buyHash.put(mStock, list);
        }
        return remainQty;
    }

    public int sell(int mNumber, int mStock, int mQuantity, int mPrice) {
        Order ord = new Order(mNumber, mStock, mQuantity, mPrice, 1);
        orderStock[mNumber][0] = mStock;

        ord = bsProcess(ord, 2);
        int remainQty = ord.mQuantity;
        if (remainQty > 0) {
            LinkedList<Order> list;
            if (sellHash.get(mStock) == null) {
                list = new LinkedList<>();
                list.add(ord);
            } else {
                list = sellHash.get(mStock);
                boolean addFlag = false;
                for (int i=0; i<list.size(); i++) {
                    Order curData = list.get(i);

                    // add order by Lower Price - Small mNumber
                    if (ord.mPrice < curData.mPrice ||
                            (ord.mPrice == curData.mPrice && ord.mNumber < curData.mNumber)) {
                        list.add(i, ord);
                        addFlag = true;
                        break;
                    }
                }

                if (!addFlag) {
                    list.add(ord);
                }
            }
            sellHash.put(mStock, list);
        }
        return remainQty;
    }

    public void cancel(int mNumber) {
        int cancelStock;
        int bsType;

        if (orderStock[mNumber][1] > 0) {   // buy order cancel
            cancelStock = orderStock[mNumber][1];
            orderStock[mNumber][1] = 0;
            bsType = 1;
        } else {    // sell order cancel
            cancelStock = orderStock[mNumber][0];
            orderStock[mNumber][0] = 0;
            bsType = 2;
        };

        if (cancelStock == 0) {
            return;
        }

        if (bsType == 1) {
            LinkedList<Order> list = buyHash.get(cancelStock);

            for (int i=0; i<list.size(); i++) {
                Order order = list.get(i);

                if (order.mNumber == mNumber) {
                    list.remove(i);
                    break;
                }
            }
            buyHash.put(cancelStock, list);
        } else {
            LinkedList<Order> list = sellHash.get(cancelStock);

            for (int i=0; i<list.size(); i++) {
                Order order = list.get(i);

                if (order.mNumber == mNumber) {
                    list.remove(i);
                    break;
                }
            }
            sellHash.put(cancelStock, list);
        }
    }

    public int bestProfit(int mStock) {
        return maxBenefit[mStock];
    }

    public Order bsProcess(Order ord, int bsType) {
        // checkSell hash by stock
        LinkedList<Order> bsList;

        // 1:buy, 2:sell, 3:cancel
        if (bsType == 1) {
            bsList = sellHash.get(ord.mStock);
        } else if (bsType == 2) {
            bsList = buyHash.get(ord.mStock);
        } else {
            bsList = null;
        }

        if (bsList != null && bsList.size()>0) {
            Iterator<Order> iter = bsList.iterator();

            while (iter.hasNext()) {
                Order marketOrd = iter.next();

                if (bsType == 1) {  // buy
                    if (ord.mPrice >= marketOrd.mPrice && marketOrd.mQuantity > 0) {
                        // matching price and Quantity
                        if (ord.mQuantity >= marketOrd.mQuantity) {
                            ord.mQuantity = ord.mQuantity - marketOrd.mQuantity;
                            marketOrd.mQuantity = 0;
                            iter.remove();
                        } else {
                            marketOrd.mQuantity = marketOrd.mQuantity - ord.mQuantity;
                            ord.mQuantity = 0;
                        }
                        // add match price in matchPricehash
                        addMatchPrice(marketOrd.mStock, marketOrd.mPrice);
                    }
                } else {    // sell
                    if (ord.mPrice <= marketOrd.mPrice && marketOrd.mQuantity > 0) {
                        // matching price and Quantity
                        if (ord.mQuantity >= marketOrd.mQuantity) {
                            ord.mQuantity = ord.mQuantity - marketOrd.mQuantity;
                            marketOrd.mQuantity = 0;
                            iter.remove();
                        } else {
                            marketOrd.mQuantity = marketOrd.mQuantity - ord.mQuantity;
                            ord.mQuantity = 0;
                        }
                        // add match price in matchPricehash
                        addMatchPrice(marketOrd.mStock, marketOrd.mPrice);
                    }
                }
                if (ord.mQuantity == 0) break;
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