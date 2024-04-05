
/**
 * 该排序方式属于桶排序
 * 这是一种不基于比较的排序
 */

/**题目背景：
 * 一般是数组中所有元素涵盖范围小的数组才会用到这种方法
 * 比如在统计员工年龄的时候，岁数都在0-100之间，就可以采用这种方式
 */

import java.util.Scanner;

public class 计数排序 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        int arr[] = new int[100];

        int num = s.nextInt();
        int usearr[] = new int[num];// 目标数组

        for (int i = 0; i < 100; i++) {

            arr[i] = 0;
        }

        for (int i = 0; i < num; i++) {

            arr[s.nextInt()]++;
        }

        int index = 0;
        for (int i = 0; i < 100; i++) {

            for (int ii = 0; ii < arr[i]; ii++) {

                usearr[index] = i;
                index++;
            }
        }

        int ln = 0;
        for (int temp : usearr) {

            System.out.print(temp + "    ");
            ln++;

            if (ln == 5) {
                System.out.println();
                ln = 0;
            }
        }
        s.close();
    }
}
