package exam.memopad;

public class TestFunc {
    public static void main(String args[]) {
        char[] cArr = {'a', 'b', 'c'};

        System.out.println(charToInt(cArr[0]));
        System.out.println(charToInt(cArr[1]));
        System.out.println(charToInt(cArr[2]));

        int height = 10;
        int width = 10;

        int i = 3;        // height = 0
        int i2 = 13;      // height = 1
        int i3 = 20;      // height = 2

        System.out.println("height = " + i/width);
        System.out.println("height = " + i2/width);
        System.out.println("height = " + i3/width);


    }

    public static int charToInt(char c) {
        return c-'a';
    }
}
