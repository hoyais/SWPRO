package study.lamda;

import java.util.HashMap;

public class HashMapTest {
    public static void main(String args[]) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("A", 10);
        System.out.println(map.get("A"));

        map.computeIfAbsent("A", key -> 200);
        map.computeIfAbsent("B", key -> 100);

        System.out.println(map.get("A"));
        System.out.println(map.get("B"));
    }


}
