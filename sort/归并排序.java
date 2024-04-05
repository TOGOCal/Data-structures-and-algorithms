import java.util.Scanner;

public class 归并排序 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int arr[] = new int[n];

        for (int i = 0; i < n; i++) {

            arr[i] = s.nextInt();
        } // 完成输入

        mainchange(arr, 0, n - 1);// 调用排序函数

        for (int temp : arr) {
            System.out.print(temp + "     ");
        }
        s.close();
    }

    public static void change(int arr[], int L, int mid, int R) {
        int size = R - L + 1;
        int arr_[] = new int[size];// 让这个数组有序
        int p1 = L;
        int p2 = mid + 1;

        int i = 0;

        while (p1 <= mid && p2 <= R) {
            arr_[i++] = (arr[p1] >= arr[p2] ? arr[p2++] : arr[p1++]);
            // 每次将两边数组中对应更大的数传入，且每次使用完指针后移
        }

        while (p1 <= mid) {
            arr_[i++] = arr[p1++];
        }

        while (p2 <= R) {

            arr_[i++] = arr[p2++];
        } // 如果有数组已经被完全复制，则这两个while复制另一个数组剩下的部分

        for (i = 0; i < arr_.length; i++) {
            arr[L + i] = arr_[i];// 拷贝
        }
    }

    public static void mainchange(int arr[], int L, int R) {
        if (L == R) {
            return;
        }

        int mid = L + ((R - L) >> 1);// 中间值

        mainchange(arr, L, mid);
        mainchange(arr, mid + 1, R);

        change(arr, L, mid, R);
    }
}

/**
 * 时间复杂度为O(N*logN)
 * 原因：
 * T(N) = 2 (N/2)+N(d) (后面这一坨是复制行为)
 * 由master公式得：由于log 2 2 == 1(N=N的一次方)
 * 时间复杂度为 N*logN<选择，冒泡的N^2
 */

/**
 * 将数组差分为两个几乎等长的数组
 * 将两个数组分别排好序后//这两步都是mainchange完成的，
 * 再将两个数组融合使其有序//这步是change完成的
 * 
 * 注意：两边相等的时候默认传入左侧的数，这对排序的稳定性有影响
 */