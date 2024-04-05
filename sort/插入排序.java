
/**
 * 对于一个长度为n的数组，
 * 每次检查其前x项是否有序，并调整
 * 逐渐增大x范围，进行排序
 */

import java.util.Scanner;

public class 插入排序 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        int n = s.nextInt();
        int arr[] = new int[n];
        for (int i = 0; i < n; i++) {

            arr[i] = s.nextInt();
        }

        method3.change(0, arr, n);

        for (int temp : arr) {

            System.out.print(temp + " ");
        }
        s.close();
    }
}

class method3 {

    public static void change(int x, int arr[], int n) {// x默认传入0

        if (x == n) {
            return;
        }

        for (int i = x; i > 0; i--) {

            if (arr[i - 1] > arr[i]) {

                int a = arr[i - 1];
                arr[i - 1] = arr[i];
                arr[i] = a;
            } else {
                break;
            } // 由于前面已经确定有序了，所以只要碰到小的说明已经排好了
        }

        change(x + 1, arr, n);
    }
}