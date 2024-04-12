import java.util.Scanner;

public class 矩阵打印技巧 {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        int arr1[][] = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };

        Information ifo1 = getInformation(arr1);

        while (ifo1.x1 <= ifo1.x2 && ifo1.y1 <= ifo1.y2) {

            print1(arr1, ifo1.x1++, ifo1.y1++, ifo1.x2--, ifo1.y2--);
        }

        System.out.println();
        System.out.println("============================================================");
        int arr2[][] = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        Information ifo2 = getInformation(arr2);

        while (ifo2.x1 <= ifo2.x2 && ifo2.y1 <= ifo2.y2) {

            print1(arr1, ifo2.x1++, ifo2.y1++, ifo2.x2--, ifo2.y2--);// 按照这种方式拆解矩阵
        }

        System.out.println();
        System.out.println("=================================================================");

        int arr3[][] = { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 }, { 16, 17, 18, 19, 20 },
                { 21, 22, 23, 24, 25 } };

        Information ifo3 = getInformation(arr3);

        while (true) {

            int x1 = ifo3.x1;
            int y1 = ifo3.y1;
            int x2 = ifo3.x2;
            int y2 = ifo3.y2;

            while (x1 <= x2) {

                mainchange(arr3, x1++, y1++, x2--, y2--);
            }

            normalPrint(arr3);
            if (s.nextLine().charAt(0) == '0') {

                break;
            }
        }

        s.close();
    }

    public static Information getInformation(int arr[][]) {

        int x1 = 0;
        int y1 = 0;
        int x2 = arr.length - 1;
        int y2 = arr[0].length - 1;

        return new Information(x1, y1, x2, y2);
    }

    public static class Information {

        int x1;
        int y1;
        int x2;
        int y2;

        Information(int x1, int y1, int x2, int y2) {

            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    public static void normalPrint(int arr[][]) {

        for (int i = 0; i < arr.length; i++) {

            for (int ii = 0; ii < arr[i].length; ii++) {

                System.out.print(arr[i][ii] + "  ");
            }

            System.out.println();
        }
    }

    // 要求：旋转打印矩阵
    public static void print1(int arr[][], int x1, int y1, int x2, int y2) {// 全是有效坐标
        //
        if (x1 == x2) {

            for (int i = y1; i <= y2; i++) {

                System.out.print(arr[x1][i] + " ");
            }
        } else if (y1 == y2) {

            for (int i = x1; i < x2; i++) {

                System.out.print(arr[i][y1]);
            }
        } else {

            for (int i = y1; i < y2; i++) {

                System.out.print(arr[x1][i] + " ");
            }

            for (int i = x1; i < x2; i++) {

                System.out.print(arr[i][y2] + " ");
            }

            for (int i = y2; i > y1; i--) {

                System.out.print(arr[x2][i] + " ");
            }

            for (int i = x2; i > x1; i--) {

                System.out.print(arr[i][y1] + " ");
            }
        }
    }

    // 要求：顺时针旋转90°矩阵，（已知矩阵都是正方形
    public static void mainchange(int arr[][], int x1, int y1, int x2, int y2) {

        for (int i = 0; i + x1 < x2; i++) {

            int a = arr[x1][y1 + i];
            arr[x1][y1 + i] = arr[x2 - i][y1];
            arr[x2 - i][y1] = arr[x2][y2 - i];
            arr[x2][y2 - i] = arr[x1 + i][y2];
            arr[x1 + i][y2] = a;
        }
    }

}
