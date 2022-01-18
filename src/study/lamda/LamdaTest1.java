package study.lamda;

public class LamdaTest1 {
    public static void main(String[] args) {
        int y = 3;
        Foo foo = new Foo() {
           @Override
           public void printInteger(int x) {
               System.out.println(x);
           }
        };
        foo.printInteger(y);
    }

}
