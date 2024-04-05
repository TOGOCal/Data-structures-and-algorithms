
/**
 * 我自己猜的（欸嘿）
 * 顺便实现一下小根堆
 * 
 * 噢，就是堆排序啊，那没事了
 */

import java.util.Scanner;

public class 用堆实现排序 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int heepsize = s.nextInt();
        int arr[] = new int[heepsize];
        int sortarr[] = new int[heepsize];// 排好序的

        for (int i = 0; i < heepsize; i++) {

            arr[i] = s.nextInt();
            smallChangeSTF(arr, i);
        }

        int i = 0;
        while (heepsize > 0) {

            sortarr[i] = mySort(arr, heepsize);
            i++;
            heepsize--;
        }

        for (int temp : sortarr) {

            System.out.print(temp + "  ");
        }

        s.close();
    }

    public static int mySort(int arr[], int heepsize) {

        int a = arr[0];
        swap(arr, 0, heepsize - 1);// 与最后一个数字交换

        smallChangeFTS(arr, 0, heepsize - 1);// 剩下讨论的范围整体减少了
        return a;
    }

    public static void smallChangeFTS(int arr[], int i, int heepsize) {

        int left = i * 2 + 1;
        if (left >= heepsize) {
            return;
        }

        int smaller = ((left + 1 < heepsize) && (arr[left + 1] < arr[left])) ? left + 1 : left;

        if (arr[i] > arr[smaller]) {

            swap(arr, i, smaller);
            smallChangeFTS(arr, smaller, heepsize);
        }
    }

    public static void smallChangeSTF(int arr[], int i) {

        if (i == 0) {

            return;
        }
        int f = (i - (2 - (i % 2))) / 2;
        if (arr[i] < arr[f]) {

            swap(arr, i, f);
        }

        smallChangeSTF(arr, f);
    }

    public static void swap(int arr[], int i1, int i2) {

        int tool = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tool;
    }
}
