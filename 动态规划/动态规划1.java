public class 动态规划1 {

    /**
     * 题目背景：
     * 还是是背包问题的问题背景
     * 将暴力递归改成动态规划
     * 所有暴力递归都可以改成动态规划
     * 为什么要使用动态规划：除去一些重复计算的的步骤，节省计算的时间
     *
     * 如何看待背包问题的重复计算：
     * 例如货物重量：2 1 3 4 7
     * 在选取 2 1 ，不选3 的时候，剩下的情况面对的是 剩余空间 size-3 ，index位于将要对4进行选择的时候
     * 在不选取 2 1，选择3的时候，剩下的情况仍然是 剩余空间 size-3 ， index位于将要对4进行选择的时候，则两步的计算是重复的
     * 可以通过计算的方式保存结果
     *
     */

    public static void main(String[] args) {

    }

    public static class Solution{
        //传统暴力递归步骤

        public static int maxValue(int[] weights , int[] values , int maxSize) {

            return value(weights , values , maxSize , 0);
        }

        public static int value(int[] weights , int[] values , int restSize , int index){
            //index是将要对index位置的货物进行选择
            if(restSize < 0){

                return -1;//无效情况
            }

            if(index >= weights.length){

                return 0;//当index访问不到货物了，说明出现了一种合理的情况
                //但是后面的价值只能按照0来计算
            }

            int value = value(weights , values , restSize , index+1);//不要这个货物的情况

            int possibleValue = value(weights , values , restSize-weights[index] , index+1);

            if(possibleValue != -1){

                return Math.max(possibleValue+values[index], value);
                //返回要这个和不要这个中更大的一种情况
            }

            return value;

        }
    }

    public static class Solution2{
        //改成动态规划

        //通过目前选择的货物的索引index和背包剩余空间这两个变量构建二维数组进而构建动态规划的查找
        public static int maxValue(int[] weights , int[] values , int maxSize) {

            int restSize = maxSize;
            int[][] dp = new int[weights.length+1][restSize+1];
            //为什么要加上这个1：
            //对于暴力递归，终止条件是访问除了货物的数量范围，也就是说，base case是index会访问到weights.length这个位置的
            //所以加上这个1使得可以访问到这个位置
            //相当于通过扩大数组可访问范围防止访问到不可访问的地点

            for(int i = 0 ; i < dp.length ; i++){

                for(int j = 0 ; j < dp[i].length ; j++){

                    dp[i][j] = 0;//完成初始化
                }
            }

            for(int index = weights.length-1 ; index >= 0 ; index--){
                //通过index逐渐向里面完成表格的填写

                for(int rest = 0 ; rest <= restSize ; rest++){

//                    int value = value(weights , values , restSize , index+1);//不要这个货物的情况
                    int value = dp[index+1][rest];//通过暴力递归代码直接改
//                    int possibleValue = value(weights , values , restSize-weights[index] , index+1);
                    if (rest-weights[index] > 0){

                        int possibleValue = dp[index+1][rest-weights[index]];
                        value = Math.max(possibleValue+values[index], value);
                    }

                    dp[index][rest] = value;
                }
            }
            return dp[0][restSize];
        }
    }
}
