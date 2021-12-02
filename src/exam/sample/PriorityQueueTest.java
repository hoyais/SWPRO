package exam.sample;

import java.util.PriorityQueue;

public class PriorityQueueTest {
    static class Student implements Comparable<Student> {
        String name;
        int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(Student target) {
            return this.age > target.age ? -1 : 1;
        }

        @Override
        public String toString() {
            return "이름 : " + name + ", 나이 : " + age;
        }
    }

    static PriorityQueue<Student> getPriorityQueueOfStudents() {
        PriorityQueue<Student> priorityQueue = new PriorityQueue<>();

        priorityQueue.offer(new Student("김철수", 20));
        priorityQueue.offer(new Student("김영희", 100));
        priorityQueue.offer(new Student("한택희", 66));
        priorityQueue.offer(new Student("이나영", 7));
        priorityQueue.offer(new Student("이혁", 43));
        priorityQueue.offer(new Student("안영희", 100));

        return priorityQueue;
    }

    public static void main(String[] args) {
        PriorityQueue<Student> priorityQueue = getPriorityQueueOfStudents();

        // 나이가 많은 순으로 학생들 목록을 출력
        while (!priorityQueue.isEmpty())
            System.out.println(priorityQueue.poll());
    }
}
