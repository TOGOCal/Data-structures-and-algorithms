public class 动态规划3 {
    /**
     * 题目背景：
     * 给定一个数组a，其中的数字各不相同，代表不同面值的货币
     * 每种货币可以使用任意次
     * 给定一个数字aim，代表目标要组成的总钱数
     * 返回总共有多少种组成方案
     */

    public static void main(String[] args) {

    }

    public static class Solution{
        //普通暴力递归步骤
        public static int method(int[] arr , int aim){

            return howMany(arr , aim , 0);
        }

        public static int howMany(int[] arr , int restAim , int index){
            //index代表此时正在分析哪个第几个面值的货币

            if(index==arr.length){

                if(restAim==0){

                    return 1;//这是一种有效方案
                }else{

                    return 0;
                }
            }

            int num = restAim/arr[index];

            int result = 0;

            for(int i =0;i <= num;i++){
                //i代表此时取几张这个面值的货币
                result += howMany(arr , restAim - arr[index] * i, index+1);
            }

            return result;
        }

    }

    public static class Solution2{
        //改成动态规划的方案
        public static int method(int[] arr , int aim){

            //分析上面的暴力递归，有两个变量，通过这两个变量构建dp
            int restAim = aim;

            int indexMax = arr.length ;

            int[][] dp =new int[restAim+1][indexMax+1];

            //restAim和index的位置都会被访问，故而两个都要加一

            dp[0][indexMax] = 1;
            for(int i =1;i<=restAim;i++){

                dp[i][indexMax] = 0;
            }//完成base case初始化，当遍历到indexMax位置的时候只有resAim==0才能代表一种相等的情况


            for(int ra = restAim;ra>=0;ra--){

                for(int index = indexMax-1 ; index >= 0 ; index--){

                    int num = ra/arr[index];

                    for(int j =0;j<=num;j++){

                        dp[ra][index] += dp[ra - arr[index]*j ][index+1];
                    }
                }
            }

            return dp[aim][0];
        }

        public static int methodPro(int[] arr , int aim){
            //对上面的动态规划步骤的改进

            if(aim <= 0 || arr.length == 0 || arr ==null){

                return 0;
            }

            int[][] dp =new int[arr.length][aim+1];//arr.length-1 以及aim的位置都是可以访问的位置

            //对暴力递归base case的分析：
            dp[arr.length-1][0] = 1;
            for(int i =1;i <= aim;i++){

                dp[arr.length-1][i] = 0;
            }

            //对循环顺序的分析：逐渐向上推进
            for(int index = arr.length-2 ; index >= 0 ; index--){

                for(int restAim = 0;restAim <= aim;restAim++){

                    int num = restAim/arr[index];
//                    for(int j =0;j<=num;j++){
//
//                        dp[restAim][index] += dp[restAim - arr[index]*j ][index+1];
//                    }

                    //对循环的改进：相当于每当num+1了之后，
                    // dp[restAim][index]就会在循环中多加一个dp[restAim - arr[index]*j ][index+1];
                    //j= num的情况，存在重复计算，故而可以优化代码计算

                    if(num!=0){
                        //如果不是0，说明前面并未进行计算
                        dp[restAim][index] = dp[restAim - arr[index]][index] + dp[restAim][index+1];
                        //上一步已经计算的结果加上这一步新增的结果
                    }else{
                        //这里是num=0的情况
                        dp[restAim][index] += dp[restAim][index+1];

                    }//这样就简化了循环的过程
                }
            }

            return dp[0][aim];

        }
    }

}
