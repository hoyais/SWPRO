package exam.sample;

import java.util.HashMap;
import java.util.LinkedList;

public class HashMapTest {
    public static void main(String args[]) {
        Task task1 = new Task(1, 1000, 1);
        Task task2 = new Task(2, 2000, 2);
        Task task3 = new Task(3, 3000, 3);

        LinkedList<Task> list = new LinkedList<>();
        list.add(task1);
        list.add(task2);
        list.add(task3);

        HashMap<Integer, Task> hm = new HashMap<>();
        hm.put(task1.id, task1);
        hm.put(task2.id, task2);
        hm.put(task3.id, task3);

        System.out.println("Before Object task " + task1.amount);
        System.out.println("Before List task " + list.get(0).amount);
        System.out.println("Before List task " + hm.get(1).amount);

        task1.amount = 5000;

        System.out.println("Object task " + task1.amount);
        System.out.println("List task " + list.get(0).amount);
        System.out.println("List task " + hm.get(1).amount);
    }
}

class Task {
    int id;
    int amount;
    int status;

    Task(int id, int amount, int status) {
        this.id = id;
        this.amount = amount;
        this.status = status;
    }
}
