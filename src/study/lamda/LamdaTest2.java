package study.lamda;

public class LamdaTest2 {
    public static void main(String[] args) {
        int y = 5;
        Foo foo = (x) -> System.out.println(x);

        foo.printInteger(y);
    }
}
