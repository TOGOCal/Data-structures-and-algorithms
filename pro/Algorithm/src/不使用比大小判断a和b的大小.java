import java.util.Scanner;

public class 不使用比大小判断a和b的大小 {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int a =s.nextInt();

        int b =s.nextInt();

//        int a=Integer.MAX_VALUE;
//        int b =Integer.MIN_VALUE;

        System.out.println(max(a,b));

        s.close();
    }

    public static int max(int a,int b){

        int c = a - b;//a和b的差值，可能超量程
        int sa = sign(a);//a的符号
        int sb = sign(b);
        int sc = sign(c);

        int samSign = (sa ^ sb);//a和b的符号是否相同，相同为0，不同为1

        return (samSign^1)*(sc*a + (sc^1)*b) + samSign*(sa*a + sb*b);
        /**
         * 如果a和b的符号相同，则不可能超量程，因此前半部分生效
         *      如果sc为1，则代表a大于b，取a （通过1*a + 0*b）得到
         *      如果sc为0，则代表a小于b，取b （通过0*a + 1*b）得到
         * 如果a和b符号相反，则有可能超量程，后半部分生效
         *      已知sa，sb一正一0，正数有效使得sa，sb中是正数的那个生效
         */
    }

    public static int sign(int a){

        return ((a >> 31) & 1) ^1;
        //((a >> 31) & 1)可以拿到int的符号位（正数为0，负数为1
        //为了使正数为1，负数为0，用异或进行取反
    }
}
