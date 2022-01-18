package study.lamda;

public class LamdaTest0 implements Foo {
    @Override
    public void printInteger(int x) {
        System.out.println(x);
    }

    public static void main(String[] args) {
        LamdaTest0 js = new LamdaTest0();
        js.printInteger(10);
    }
}
