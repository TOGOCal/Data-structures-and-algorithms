
/**
 * 基数排序属于桶排序的一种，同样是不基于比较的排序
 * 基数排序适用条件：正整数
 * 核心思想：
 * 对每个数每位数（以个位举例）上可能出现的情况0-19建立下标为0-9的队列
 * 队列：数字先进先出：使用原因：这样就不会改变上一位的排序情况
 * 直到排完最高位：先低位后高位原因：
 * 这种排序方式最后的排序权重最大，因此将数字中权重最大的最高位放在最后进行排序
 * 艹我在说什么
 */

import java.util.Scanner;
import java.util.HashMap;

public class 基数排序 {

    final static int base = 10;// 代表十进制，当然可以按照情况进行修改

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("数组有多少个数字:");
        int num = s.nextInt();

        int arr[] = new int[num];

        for (int i = 0; i < num; i++) {

            arr[i] = s.nextInt();
        }

        System.out.println("==================================================");

        int max = 0;
        for (int i = 0; i < num; i++) {

            max = Math.max(max, arr[i]);
        } // 找出数组中最大的数

        Bucket bc[] = new Bucket[base];
        for (int i = 0; i < base; i++) {

            bc[i] = new Bucket();
        }

        int d = noDigit(max);// 判断数组中最多的数有多少个数字

        for (int i = 0; i < d; i++) {// 表示第几个数字

            for (int ii = 0; ii < num; ii++) {// 遍历数组

                bc[foundDigit(arr[ii], i)].addBucket(arr[ii]);
            }

            int which = 0;
            for (int ii = 0; ii < num;) {

                while (bc[which].bucketSize == 0) {

                    which++;
                }

                ii = bc[which].outBucket(arr, ii);
            }
        }

        {// 限定了i的使用范围
            int i = 0;
            for (int temp : arr) {

                System.out.print(temp + "   ");
                i++;
                if (i == 5) {

                    System.out.println();
                    i = 0;
                }
            }
        }

        s.close();
    }

    public static int foundDigit(int a, int d) {// 找出一个数的第几位是什么数字(使用编程思想，个位是第0位)

        for (int i = 0; i < d; i++) {

            a /= 10;
        }

        return a % 10;
    }

    public static int noDigit(int i) {// Number Of Digit//一个数有几个数字

        int res = 0;
        while (i > 0) {

            i /= 10;
            res++;
        }

        return res;
    }

    public static class Bucket {// 建立每个数的桶

        HashMap<Integer, Integer> bucket;// 作为先进先出的队列
        int bucketSize;

        Bucket() {

            bucket = new HashMap<>();
            bucketSize = 0;
        }

        public void addBucket(int a) {// 往队列中加入一项

            bucket.put(bucketSize, a);
            bucketSize++;
        }

        public int outBucket(int arr[], int ii) {// 从队列中弹出一项//要求：先入先出

            int i;
            for (i = 0; i < bucketSize; i++) {

                arr[ii + i] = bucket.get(i);
                bucket.remove(i);
            }
            bucketSize = 0;

            // arr[ii+i]这位置有数，但是在出循环的时候i++了，所以|
            return ii + i;// 这个下标对应位置没有数字
        }
    }
}
