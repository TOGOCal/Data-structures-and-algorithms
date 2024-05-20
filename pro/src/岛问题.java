import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class 岛问题 {

    /**
     * 问题背景：
     * 给定一个二维矩阵，其中只有0或1
     * 若有1和其上下左右其他的1相连，这几个一是同一个岛，其他的一是其他的岛
     * 例如
     * 0 1 0 1 0
     * 1 1 0 1 0
     * 1 0 1 0 0
     * 0 0 0 0 0
     * 中有三个岛
     * 现给定一个二维矩阵，判断其中有多少个岛
     */

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int n = s.nextInt();
        int m = s.nextInt();

        int[][] arr = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                arr[i][j] = s.nextInt();
            }
        }

        Solution1 sol1 = new Solution1();

        System.out.println(sol1.method(arr));

        s.close();
    }

    public static class Solution1 {

        /**
         * 我自己写的方法：遍历通过1进行遍历，将查找过的岛置为0
         * 很好，一样的
         *
         * 时间复杂度O(N*M)
         * 一个点在遍历的时候，最多被上下左右的点访问一遍，所以最多被访问4次，是有限次数的调用次数
         */

        public int method(int[][] islands) {

            int col = islands.length;
            int row = islands[0].length;

            int result = 0;

            for (int i = 0; i < col; i++) {

                for (int j = 0; j < row; j++) {

                    if (islands[i][j] == 1) {

                        delete(islands, i, j);
                        //printArr(islands);
                        result++;
                    }
                }
            }

            return result;
        }

        public static void printArr(int[][] arr) {
            //检查一下

            for (int i = 0; i < arr.length; i++) {

                for (int j = 0; j < arr[i].length; j++) {

                    System.out.print(arr[i][j] + " ");
                }
                System.out.println();
            }

            System.out.println("==============================================");
        }

        private void delete(int[][] islands, int i, int j) {

            // 检查下左右（从上往下遍历可以将向上检索的步骤省略
            if (islands[i][j] == 0) {

                return;
            }

            islands[i][j] = 0;
            if (j > 0) {

                delete(islands, i, j - 1);
            } // 左

            if (j < islands[0].length - 1) {

                delete(islands, i, j + 1);
            } // 右

            if (i < islands.length - 1) {//只去取得到island.length-2，可以继续跑

                delete(islands, i + 1, j);
            } // 下

            if (i > 0) {

                delete(islands, i - 1, j);
            } // 上
        }

    }

    public static class Solution2 {
        /**
         * 在刷题，竞赛以及笔试的时候，几乎所有的问题都是单内存单CPU的问题
         * 就是给定一个输入，给出一个输出
         *
         * 这道题可以使用 并行算法 进行编程（就算是面试中遇到了，也不会考你写代码，只会让你说思路
         *
         * 并行算法设计的目的是为了在处理极大的数据时，可以通过多台机器同时运行对数据进行快速地分析
         */

        /**
         * 算法执行流程：
         * 假设，有两个CPU，将一张地图（特别大）进行切分：
         * 比如 arr[n][m]  分成  arr[n/2][m]和arr[n/2]两部分
         * 交由CPU对这两部分进行分别处理，统计下每个岛的代表点（遍历的时候刚刚进岛的点
         * 同时统计边界信息（有哪些位于交换边界的点是1
         * 再遍历边界对两张表进行统合
         *
         * 使用知识点是并查集，因为要合并两个岛（如果在边界处相交
         */
        

    }
}
