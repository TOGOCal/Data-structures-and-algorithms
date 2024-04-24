
//import java.util.ArrayList;
import java.util.Scanner;

/**
 * 问题描述：
 * 每个盘有大小，最终移动的结果要求是所有圆盘满足小的在大的上面
 * 同时，中间移动过程中，也需要保证这个大盘在小盘下的要求
 * 现在的问题是：如何从左杆移动到右杆
 * 给定一定数量的圆盘放好了在左列，如何移动到右列
 */

public class 暴力递归1 {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        // Solution.changeTeach(s.nextInt());
        Solution.changeTeachPro(s.nextInt());

        s.close();
    }

    public static class Solution {

        public static void changeTeachProChange(int n) {// 定理：一切递归都可以改成非递归

            while (n > 0) {

            }
        }

        public static void changeTeachPro(int n) {

            fromTo(n, "左", "中", "右");
        }

        public static void fromTo(int n, String from, String other, String to) {

            if (n == 1) {

                System.out.println("将1从" + from + "移动到" + to);
                return;
            }

            fromTo(n - 1, from, to, other);
            System.out.println("将" + n + "从" + from + "移动到" + to);
            fromTo(n - 1, other, from, to);
        }

        /**
         * 解决思路：
         * 先将第n层圆盘移动到右边，此时1-（n-1）层圆盘在中间
         * 进行递归
         */

        // 教的方法
        public static void changeTeach(int n) {

            leftToRight(n);// 调用方法left to right
        }

        public static void leftToRight(int n) {

            if (n == 1) {

                System.out.println("move 1 from left to right");
                return;
            }

            leftToMiddle(n - 1);
            System.out.println("Move " + n + " from left to right");
            middleToRight(n - 1);

            /**
             * 对于每一种情况，都需要先将最后一层移动到最右边
             * 要达成这一需求，要先将其余的n-1层移动到中间，
             * 移动第n层
             * 中间移动到右边
             */
        }

        public static void leftToMiddle(int n) {

            if (n == 1) {

                System.out.println("Move 1 from left to middle");
                return;
            }

            leftToRight(n - 1);
            System.out.println("move " + n + " from left to middle");
            rightToMiddle(n - 1);

        }

        public static void rightToLeft(int n) {

            if (n == 1) {

                System.out.println("move 1 from right to left");
                return;
            }

            rightToMiddle(n - 1);
            System.out.println("move " + n + " from right to left");
            middleToLeft(n - 1);
        }

        public static void rightToMiddle(int n) {

            if (n == 1) {

                System.out.println("move 1 from right to middle");
                return;
            }

            rightToLeft(n - 1);
            System.out.println("move " + n + " from right to middle");
            leftToMiddle(n - 1);
        }

        public static void middleToRight(int n) {

            if (n == 1) {

                System.out.println("move 1 from middle to right");
                return;
            }

            middleToLeft(n - 1);
            System.out.println("move " + n + " from middle to right");
            leftToRight(n - 1);
        }

        public static void middleToLeft(int n) {

            if (n == 1) {

                System.out.println("move 1 from middle to left");
                return;
            }

            middleToRight(n - 1);
            System.out.println("move " + n + " from middle to left");
            rightToLeft(n - 1);
        }

        // ======================================================================================

        // public Stack move(Stack list) {// 左

        // Stack middle = new Stack();// 中
        // Stack result = new Stack();// 右

        // return result;
        // }

        // public static class Stack {

        // ArrayList<Integer> arrayList;
        // int point;

        // Stack() {

        // arrayList = new ArrayList<>();
        // point = 0;
        // }

        // public void add(int n) {

        // if (arrayList.size() >= 1) {

        // if (n > arrayList.get(point - 1)) {

        // System.out.println("错误，不能这么操作");
        // return;
        // }
        // }

        // arrayList.add(n);
        // point++;
        // }

        // public int pop() {

        // if (arrayList.size() == 0) {

        // System.out.println("错误，不能这么操作");
        // return 0;
        // }
        // int n = arrayList.get(point - 1);
        // point--;
        // arrayList.remove(point);// 移出这个点

        // return n;
        // }

        // public void print() {

        // for (int i = 0; i < point; i++) {

        // System.out.print(arrayList.get(i) + " ");
        // }
        // System.out.println();
        // }
        // }
    }
}
