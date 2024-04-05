
/**
 * 
 * 堆排序优化
 */

import java.util.Scanner;

public class 用堆实现排序pro {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int heepsize = s.nextInt();

        int arr[] = new int[heepsize];
        for (int i = 0; i < heepsize; i++) {

            arr[i] = s.nextInt();
        }

        for (int i = heepsize - 1; i >= 0; i--) {

            method(arr, i, heepsize);
        } // 注意，这种方法只能用于已知样本总大小的方法
          // 这种方法好的原因：
        /**
         * 直观理解：堆越往下层走，数量越多，
         * 且越往下层走，往上检索检索越多，相当于原版方法是大*大+小*小
         * 但是这种方法每个节点向下遍历，相当于越往下数量越多的节点
         * 每个节点检索的越少，所以是大*小+小*大
         * 时间复杂度自然相较于之前的方法快
         */

        /**
         * 对于最底层的N/2个数而言，每个进行1次操作
         * N/4 2次
         * N/8 3次
         * 。。。。所以总时间为 N/2 + N/4*2 + N/8*3 + N/16*4。。。。
         * 易得其收敛于常数*N时间，所以时间复杂度为O（N）
         * 就可以使其变成一个堆
         * （虽然后面出堆的仍然需要N*logN的时间，但你就说优化没嘛
         */

        for (int temp : arr) {

            System.out.print(temp + "  ");
        }
        s.close();
    }

    public static void method(int arr[], int i, int heepsize) {// 大根堆
        // 每个节点向下确定是否符合要求，之前的方法是向上求
        // 向下检索
        int left = i * 2 + 1;
        if (left >= heepsize) {
            return;
        }

        int larger = ((left + 1 < heepsize) && (arr[left + 1] > arr[left])) ? left + 1 : left;
        // 大的数交换后，一定子节点都比他小
        if (arr[larger] > arr[i]) {

            swap(arr, larger, i);
            method(arr, larger, heepsize);
        } else {
            return;
        }
    }

    public static void swap(int arr[], int i1, int i2) {

        int tool = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tool;
    }
}
