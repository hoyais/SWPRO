package exam.word;

import java.util.Iterator;
import java.util.PriorityQueue;

public class TestWord {
    public static void main(String args[]) {
        String s = "abc";

        System.out.println(s.substring(0, 0) + "*" + s.substring(1));
        System.out.println(s.substring(0, 1) + "*" + s.substring(1+1));
        System.out.println(s.substring(0, 2) + "*" + s.substring(2+1));

        String a1 = "abc";
        String b1 = "bbc";

        System.out.println(a1.compareTo(b1));

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        pq.offer(2);
        pq.offer(1);
        pq.offer(4);
        pq.offer(3);
        pq.offer(5);
        pq.offer(6);

        Iterator<Integer> iter = pq.iterator();

        while (iter.hasNext()) {
            System.out.println("pq.iter.peek() : " + iter.next());
        }

        int k = pq.size();
        for (int i=0; i<k; i++) {
            System.out.println("pq.poll() : " + pq.poll());
        }
    }
}
