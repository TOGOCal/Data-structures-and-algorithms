public class 动态规划4 {

    /**
     * 题目背景：没有背景
     * 单纯给一个暴力递归的代码
     * 试将其改成动态规划
     *
     */

    public static void main(String[] args) {

    }

    public static class Solution{

        public static int mainMethod(int[] arr){

            if(arr == null || arr.length == 0){

                return 0;
            }

            return Math.max(f(arr, 0, arr.length - 1), s(arr, 0, arr.length - 1));
        }

        public static int f(int[] arr , int L , int R){

            if(L == R){

                return arr[L];
            }

            return Math.max(arr[L] + s(arr,L+1,R),arr[R] + s(arr,L,R-1));
        }

        public static int s(int[] arr , int i , int j){

            if(i == j){

                return 0;
            }

            return Math.min(f(arr,i+1,j) , f(arr,i,j-1));
        }
    }

    public static class Solution2{

        public static int f(int[] arr){

            if(arr == null || arr.length == 0){

                return 0;
            }

            int[][] f = new int[arr.length][arr.length];

            for(int i = 0;i<f.length;i++){

                f[i][i] =arr[i];//f函数的base case
            }

            int[][] s = new int[arr.length][arr.length];

            for(int i = 0;i<s.length;i++){

                s[i][i] = 0;//s函数的base case
            }

            //这道题的填表过程相当于已知两个表的主对角线，开始逐渐向上进行天协（通过斜线向上的方式

            int N = arr.length;

            for(int i =1;i<N;i++){
                //不从0开始的原因： 0 0这条对角线是base case，已经填好了

                int L = 0;
                int R = i;//通过这两个值遍历对角线

                while(L < N && R < N ){

                    //return Math.max(arr[L] + s(arr,L+1,R),arr[R] + s(arr,L,R-1));
                    f[L][R] = Math.max(arr[L] + s[L+1][R], arr[R] + s[L][R-1]);
                    //return Math.min(f(arr,i+1,j) , f(arr,i,j-1));
                    s[L][R] = Math.min(f[L+1][R] , f[L][R-1]);

                    L++;
                    R++;
                }
            }

            //return Math.max(f(arr, 0, arr.length - 1), s(arr, 0, arr.length - 1));
            return Math.max(f[0][arr.length-1], s[0][arr.length-1]);

        }
    }
}
