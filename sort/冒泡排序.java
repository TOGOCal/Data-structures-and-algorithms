import java.util.Scanner;

public class 冒泡排序 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();

        int arr[] = new int[n];
        for (int i = 0; i < n; i++) {

            arr[i] = s.nextInt();
        }
        method2.change(arr);

        for (int temp : arr) {

            System.out.print(temp + " ");
        }
        s.close();
    }
}

class method2 {

    public static void change(int arr[]) {

        for (int i = 0; i < arr.length; i++) {

            for (int ii = 0; ii < arr.length - 1 - i; ii++) {

                if (arr[ii] > arr[ii + 1]) {

                    int a = arr[ii];
                    arr[ii] = arr[ii + 1];
                    arr[ii + 1] = a;
                }
            }
        }
    }
}