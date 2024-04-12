
/**
 * 已知一个数组只有一个出现了奇数次的数，
 * 找那个数
 */

import java.util.Scanner;

public class 异或小寄巧 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        s.close();
    }
}

class method6 {

    public static int choose1(int arr[]) {// 已知一个数组里只有一个元素出现了奇数次

        int eor = arr[0];
        for (int i = 1; i < arr.length; i++) {// 由于异或有交换律，所以这里的数顺序不重要

            eor = eor ^ arr[i];// 偶数个相同的数异或为0//0异或任何数都是本身，所以不影响
        }
        return eor;
    }

    public static String choose1pro(int arr[]) {// 已知一个数组里面有两个元素出现了奇数次，求出这两种元素
        // 部分思想参考choose2
        int eor = arr[0];
        for (int i = 1; i < arr.length; i++) {

            eor = eor ^ arr[i];
        }

        int eor_ = ~eor;
        int a = eor ^ (eor_ + 1);// 剩下的（取最右边的1后剩下的

        eor_ = 0;
        for (int temp : arr) {// 思想：将数组分成两波，一波在a位上有1，一波在a上没有一，对于一波异或何以求出其一，异或可以求出其二7

            if ((temp & a) == 0) {

                eor_ = eor_ ^ temp;
            }
        }

        eor = eor ^ eor_;

        return (eor + "和" + eor_);
    }

    public static int choose2(int x) {// 提出一个数最右边的1，例如1000010110100->1000000000100

        int x_ = ~x;
        return x & (x_ + 1);// 按位非之后，最右边第一个1右边的0全部被转成了1，加1后把只剩下了最右边的1；
    }

    public static int choose3(int x) {// 求一个数的二进制数里面有几位是1

        int num = 0;

        while (x != 0) {

            int x_ = ~x;
            x_ = x & (x_ + 1);// 最右边的一

            num++;
            x ^= x_;// x -= x_;不这样写的原因是如果是负数会越减越小
        }
        return num;
    }

}
