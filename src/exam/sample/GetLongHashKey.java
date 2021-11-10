package exam.sample;

public class GetLongHashKey {
    public static void main (String args[]) {
        char[] in = {'h', 'e', 'l', 'l', 'o', '\0'};

        String sval = String.valueOf(in);

        System.out.println(sval);
        System.out.println(getLongHash(in));
    }


    public static long getLongHash(char in[]) {
        long rval = 0;
        int i=0;

        while (in[i] != '\0') {
            rval = (rval << 5) + (in[i] - 'a' + 1);
            i++;
        }
        return rval;
    }
}
