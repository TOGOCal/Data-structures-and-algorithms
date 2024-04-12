public class 打表相关技巧 {
    public static void main(String[] args) {

        // Solution1.printReslutOfQuestion1();

        // Solution2.printReslutOfQuestion2();

        // Solution3.printReslutOfQuestion3();
    }

    public static class Solution1 {
        /**
         * 题目背景：
         * 小明需要买苹果，他只拥有两种袋子，一种能装6个，一种能装8个
         * 要求是每个袋子必须装满，
         * 输入参数为可能会买多少个苹果，输出参数是最少需要多少几个袋子
         * 如果无论如何都不能装满则返回-1
         */

        public static int howManyMethod1(int n) {// 方法1，完全不找规律的暴力解法

            int min = n / 8;

            int num = (n - min * 8) / 6;

            while (min * 8 + num * 6 != n) {

                min--;
                if (min < 0) {
                    return -1;
                }

                num = (n - 8 * min) / 6;
            }

            return min + num;
        }

        public static int howManyMethod2(int n) {// 方法2，按照数学方法分析规律

            if ((n & 1) != 0) {// 如果是奇数，那一定装不完

                return -1;
            }

            int min = n / 8;

            int num = (n - min * 8) / 6;

            while (min * 8 + num * 6 != n) {

                min--;

                if (min < 0) {

                    return -1;
                }

                int a = n - 8 * min;

                if (a > 24) {// 分析：当相差的数等于24的时候，既可以用3_8个袋子装完,又可以用4_6个袋子装完
                    // 但此时一定选择用3_8，因为要找出用袋子最少的方法
                    // 所以一旦问题超过了24，问题就会转为a-24的问题，相当于这是以一个循环
                    return -1;
                }

                num = a / 6;
            }

            return min + num;
        }

        public static int howManyMethod3(int n) {// 方法3：通过打表通过人的肉眼找到的答案

            /**
             * 使用这种方法的限制:
             * 1.输入输出都很简单（具体怎么简单具体题目具体分析
             * 2.要先找到一个易于实现且不会出错的方法去执行程序得出答案
             */

            // for (int i = 0; i < 200; i++) {

            // System.out.println(i + "\t" + howManyMethod1(i) + "\t" + howManyMethod2(i));
            // } // 通过这种方法找到了0-200之间的所用结果

            // 下面是肉眼找规律找到的结果：
            if ((n & 1) != 0) {

                return -1;
            } // 如果是奇数则都是-1

            if (n >= 18) {

                return n / 8 + ((n % 8 == 0) ? 0 : 1);// n/8向上取整
            } else if (n == 0) {

                return 0;
            } else if (n <= 4) {

                return -1;
            } else if (n <= 8) {

                return 1;
            } else if (n == 10) {

                return -1;
            } else if (n <= 16) {

                return 2;
            } // 肉眼发现，18之后的偶数（包括18）都会连续出现4个数字
              // 这些数字的规律是n/8向上取整

            return -1;
        }

        public static void printReslutOfQuestion1() {

            for (int i = 0; i < 200; i++) {

                System.out.println(i + "\t" + howManyMethod1(i) + "\t" + howManyMethod2(i) + "\t" + howManyMethod3(i));
            } // 通过这种方法找到了0-200之间的所用结果
        }
    }

    public static class Solution2 {

        /**
         * 问题背景：
         * 假定牧场中有n份草料
         * 现在有牛一只，羊一只
         * 牛和羊会去吃草料
         * 但每次只能吃4的n次方份
         * 由于包括1，所以不存在吃不完的情况
         * 
         * 牛与羊中会有一个最为先手，先决定吃多少
         * 给定草料总分量n，
         * 编写程序返回先手获胜还是后手获胜
         */

        public static String method1(int n) {

            /**
             * 0份 null
             * 1份 先手获胜，先手可以吃完
             * 2份 后手获胜，先手只能吃1份
             * 3份 先手
             * 4份 先手
             */

            if (n == 0) {

                return null;
            } else if (n == 2) {

                return "后手";
            } else if (n < 5) {

                return "先手";
            }

            int eat = 1;

            while (eat <= n) {

                if (method1(n - eat).equals("后手")) {// 主过程先手在内函数中是后手

                    return "先手";
                }

                if (eat > n / 4) {

                    break;
                } // 防止超过Integer.MAX_Value

                eat *= 4;
            }

            return "后手";
        }

        public static String method2(int n) {

            if (n == 0) {

                return null;
            }

            n = n % 5;
            if (n == 2 || n == 0) {

                return "后手";
            } else {

                return "先手";
            }
        }

        public static void printReslutOfQuestion2() {

            for (int i = 0; i < 200; i++) {

                System.out.println(i + " " + method1(i) + ":" + method2(i) + "=" + (method1(i) == method2(i)));
            }
        }
    }

    public static class Solution3 {

        /**
         * 问题背景：
         * 有这么一类数，可以被分解为几个连续正整数的和
         * 例如：6=1+2+3
         * 而有些数不能，写一个函数判断
         */

        public static boolean method1(int n) {// 暴力方法，用于打表寻找规律

            for (int i = 0; i < n; i++) {

                int sum = 0;
                int i_copy = i;
                int time = 0;
                do {

                    i_copy++;
                    time++;
                    sum += i_copy;
                    if (sum == n && time >= 2) {

                        return true;
                    }
                } while (sum < n);
            }

            return false;
        }

        public static boolean method2(int n) {// 打表找到的规律

            /**
             * 找到的规律是2的幂次方就是false
             */

            return !((n & (n - 1)) == 0);
            // 这一步的算法分析：如果这里需要判断是不是二的幂次方
            // 二的幂次方数的二进制特点是二进制数中只有一个1
            // 而减一后会把这个1打散
            // 所以如果(n & (n - 1)) = 0则代表这是二的幂次方，
            // 但题目背景是如果是2的幂次方返回false，所以要加“！”
        }

        public static void printReslutOfQuestion3() {

            for (int i = 0; i < 200; i++) {

                System.out.println(i + " : " + method1(i) + " : " + method2(i) + " = " + (method1(i) == method2(i)));
            }
        }
    }

    public static class Solution4 {

        /**
         * 问题背景：
         * 假定有一串灯，一开始随机处于开或关的状态
         * 现有两个人，每个人可以决定从某盏灯开始之后的灯的开关状态全部改变
         * 当某个人将所有灯全部调整为开时为胜
         * 
         * 寄，老子不会逻辑简单的方法（可能是提前知道内部原理了
         */

        public static boolean method(boolean str[]) {

            if (str[str.length - 1]) {// 原理：不管如何调整这个灯，最后一个灯始终会被使用到
                // 所以如果在先手开始的时候最后的灯已经打开了，那他无论如何都一定会将它关闭

                return false;
            } else {

                return true;
            }
        }
    }
}
