import java.util.Scanner;

public class 暴力递归6 {

    /**
     * 问题背景：
     * n皇后问题，
     * 在n*n的棋盘上，摆放n个皇后
     * 要求，任意两个皇后之间不同行，不同列，不同斜线，返回有多少种摆法
     */

    public static void main(String[] args) {

        Scanner s =  new Scanner(System.in);

        int n =s.nextInt();

        long beginTime = System.currentTimeMillis();
        System.out.println(Solution.howMany(n));
        long endTime = System.currentTimeMillis();
        System.out.println("原方法花费时间："+(endTime - beginTime) +"ms");

        beginTime = System.currentTimeMillis();
        System.out.println(Solution2.method(n));
        endTime = System.currentTimeMillis();
        System.out.println("改进方法花费时间："+(endTime - beginTime) +"ms");

        s.close();
    }

    public static class Solution{

        public static int howMany(int n){

            int[] arr=new int[n];
            return method(arr , 0 , n);
        }

        public static int method(int[] already , int nowRow , int n){
            //n代表有多少个皇后或者有多少列

            if(nowRow == n){

                //当现在的行走到无效的区域上了，说明前面的已经排好了，算是一种有效情况
                return 1;
            }

            int result = 0;

            for(int i = 0;i < n;i++){

                if(isSuitable(already , nowRow, i)){

                    already[nowRow] = i;
                    result += method(already,nowRow+1,n);
                }
            }

            return result;
        }

        public static boolean isSuitable(int[] already, int nowRow , int nowCol){

            for(int i = 0 ; i < nowRow ; i++){

                if(already[i] == nowCol || Math.abs(already[i] - nowCol) == Math.abs(i - nowRow)){
                    //当：现在棋子所在的列与之前有过的列重复，或者
                    //斜率为1：在同一条直线上
                    return false;
                }
            }

            return true;
        }
    }

    public static class Solution2{

        /**
         * 方法2：
         * 大体方法一致，但是按照位运算的方式对方法1进行改进
         * 位运算可以对一些常数项v操作进行快速运算
         */

        public static int method(int n){

            if(n<1 || n>32){
                //这个算法的缺点：不能操作超过32位int的数
                return 0;
            }

            int limit = (1 << n) - 1;
            return howMany(limit , 0 , 0 , 0);
        }

        public static int howMany(int limit , int colLimit , int leftLimit , int rightLimit){
            /**
             * 方法变量：
             * limit：有多少个皇后，这个limit的int类型变量中就有多少个1（辅助功能，对之后的一些功能进行辅助
             * colLimit：列限制，哪个位置有过点了，这个colLimit的第几位就是1（2进制（1代表这个位置已经不能放皇后了，0代表可以放
             * liftLimit：左斜线限制，（代表所有的点的左斜线限制
             * rightLimit：右斜线限制
             */

            if(limit == colLimit){
                //当colLimit满了，代表所有的点都放进去了（同时也代表所有点都找到了合适的位置，这是一种合适的情况
                return 1;
            }

            int allLimit = (colLimit | leftLimit | rightLimit);//所有的按位或起来，1代表所有的不能放点的地方
            //此时所有的0代表所有能放皇后的位置，之前我们学过找出一个数字最右边的第一个1的位置
            //所以我们需要将问题转移

            int allPossible = limit & (~allLimit);
            //不直接异或的好处：如果在0位置有1，那它的左限制会超边界，但是皇后并不能放出去，所以要想办法把这个1消掉
            //通过这种方式，就可以越约掉这些1

            int result =0;
            while(allPossible != 0){

                int mostRight = allPossible & (~allPossible + 1);//找出最右边的点
                allPossible ^= mostRight;
                result += howMany(limit , colLimit | mostRight , (leftLimit | mostRight)<<1 , (rightLimit | mostRight)>>1);
                //操作方式：limit保留，左斜线限制先或上这个1，再向左移，即可达成要求
            }

            return result;
        }
    }
}
