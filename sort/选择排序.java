
/**
 * 每次选出最大的与最末尾交换
 */

import java.util.Scanner;

public class 选择排序 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int arr[] = new int[n];

        for (int i = 0; i < n; i++) {

            arr[i] = s.nextInt();
        }

        method1.maxchange(n, arr);

        for (int temp : arr) {
            System.out.print(temp + " ");
        }
        s.close();
    }
}

class method1 {

    public static void maxchange(int x, int arr[]) {

        if (x == 0) {
            return;
        }

        int max = 0;

        for (int i = 0; i < x; i++) {

            if (arr[i] > arr[max]) {

                max = i;
            }
        }

        int a = arr[max];
        arr[max] = arr[x - 1];
        arr[x - 1] = a;

        maxchange(x - 1, arr);
    }
}