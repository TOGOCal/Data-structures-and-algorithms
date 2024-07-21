public class question16 {

    /**
     * 问题背景：
     * 给定两个等长数组，
     * 能力值和购买值
     * 你的能力值必须大于这个能力值才能通过这个数组位置
     * 否则必须要花钱购买
     *
     * 求花多少最少的钱才能够通过整个数组
     *
     * 注：
     * 这道题如果i每个怪兽能力值很小，则可以改成动态归还
     * 单数如果每个怪兽的能力值巨大无比，则不能改成动态规划
     */

    public class Solution{

        int minValue;

        //动态规划
        public int getMinValue(int[] ability, int[] cost) {

            minValue = Integer.MAX_VALUE;

            method(ability, cost, 0, 0, 0);

            return minValue;
        }

        public void method(int[] ability, int[] cost , int myAbility , int nowCost , int index) {

            if(index == ability.length){

                minValue = Math.min(minValue, nowCost);
                return ;
            }

            if(myAbility > ability[index]){
                //可以选择购买或者不购买
                //选择不购买
                method(ability, cost, myAbility, nowCost, index + 1);
            }

            //选择购买
            method(ability, cost, myAbility+ability[index], nowCost+cost[index], index + 1);

        }
    }


    //另一种递归的构建
    public class Solution1{

        public int getMinCost(int[] ability, int[] cost) {

            return method(ability, cost, 0, 0);
        }

        public int method(int[] ability, int[] cost, int myAbility ,int index) {

            //终止田间，此处不需要再花钱，返回0即可
            if(index == ability.length){

                return 0;
            }

            if(myAbility < ability[index]){

                return cost[index] + method(ability, cost, myAbility+ability[index], index + 1);
            }else{

                return Math.min(
                        cost[index] + method(ability, cost, myAbility+ability[index], index + 1),
                        method(ability, cost, myAbility, index + 1)
                );
            }
        }

    }


    //动态规划情况1
    class Solution2{
        //在cost较大，但是单个ability较小的情况下
        //使用index - ability构建整张表

        public int getMinCost(int[] ability, int[] cost) {

            int maxAbility = 0;
            for(int a : ability){

                //购买所有能力可以到达的总能力
                maxAbility+=a;
            }


            //为什么+1：这个能力值是可以到达的，且由于会访问到最后一个位置，所以置位为ability+1
            int[][] dp = new int[ability.length+1][maxAbility+1];

            //动态规划转移为当我拿着ability的能力来到index位置的时候，我该如何通过这个位置
            //dp中每个值代表花费的最少前数


//            if(index == ability.length){
//
//                return 0;
//            }

            for(int i = 0; i< maxAbility+1;i++){

                dp[ability.length][i] = 0;
            }

            //由下面的值决定上面的值，所以从下往上遍历
            for(int i = ability.length-1; i>=0;i--){

                for(int j = 0;j <= maxAbility - ability[i];j++){

                    if(j < ability[i]){

                        dp[i][j] = cost[i] + dp[i+1][j+ability[i]];
                    }else{

                        dp[i][j] = Math.min(cost[i] + dp[i+1][j+ability[i]] , dp[i+1][j]);
                    }
                }
            }

//
//            if(myAbility < ability[index]){
//
//                return cost[index] + method(ability, cost, myAbility+ability[index], index + 1);
//            }else{
//
//                return Math.min(
//                        cost[index] + method(ability, cost, myAbility+ability[index], index + 1),
//                        method(ability, cost, myAbility, index + 1)
//                );
//            }


            return dp[0][0];
        }

    }



    //第二种动态规划
    class Solution3{
        //在能力值较大，但是花费较少的情况下，可以采用使用index和cost构建二维表的方式进行动态规划
        /**
         * 动态转移：
         * index代表此时正在通过哪个位置
         * cost代表此时严格花费了多少
         * 每个单元格中的值代表此时能够到达的最大能力是多少
         * 能够到达下个index代表通过
         */

        public int getMinCost(int[] ability, int[] cost) {

            int maxCost = 0;

            for(int c : cost){

                maxCost+=c;
            }//得出会花的最大钱数

            //                  这里采用哪个作为index超度都可以，毕竟是一样长的
            int[][] dp = new int[cost.length+1][maxCost+1];

            //单独对第一行进行填充
            for(int i = 0 ;i<=maxCost ; i++){

                if(i != cost[0]){

                    dp[0][i] = -1;
                }else{

                    dp[0][1] = ability[0];
                }
            }

            for(int i = 1 ; i < cost.length ; i++){

                for(int j = 0; j <= maxCost ; j++){

                    //说明可以走条路通过这个位置
                    if(dp[i-1][j] != -1){


                        //如果之前被前面的位置购买达到了该位置，那这个地方能够到达的能力值需要记录
                        //之后进行比较，选择较大的那个
                        int record = dp[i][j];

                        //不能选择，只能购买能力
                        if(dp[i-1][j] < ability[i]){

                            dp[i][j] = -1;//不能再花这么多钱通过这个位置
                            dp[i][j + cost[i]] = dp[i-1][j] + ability[i];
                        }else{
                            //可以选择是否购买
                            dp[i][j] = dp[i-1][j];//不购买
                            dp[i][j + cost[i]] = dp[i-1][j] + ability[i];//购买
                        }


                        //肯定保存的能力是较大的能力值，方便后续的通过
                        dp[i][j] = Math.max( dp[i][j] , record );
                    }
                }

            }


            //单独对最后一行进行遍历，得出第一个能够通过的方案（第一个就保证了价格最少
            for(int i =0; i <= maxCost ; i++){

                if(dp[cost.length-1][i] != -1){

                    return i;
                }
            }

            //应该不会走到这条路上来
            return maxCost;
        }

    }




}
