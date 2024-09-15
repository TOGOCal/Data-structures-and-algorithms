public class 股票问题 {

    //注：该类问题有三个问题，以下会一一列举

    /**
     * 问题1：
     * 给定一个数组arr，代表昨天某个时间点股票的价格
     * 先要求判断出在何时买入，何时卖出可以获得作答收益
     * 需要注意的是，只能进行一次交易
     */

    /**
     * 问题2：
     * 给定一个数组代表昨天的股票数量，代表某个时间点股票的价格
     * 不限交易个次数，但是保证手上拥有的股票不能超过一支，
     * 返回最大获得利润
     */

    /**
     * 问题3：
     * 给定一个数组代表昨天的股票数量，代表某个时间点股票的价格
     * 限定交易次数k，同时保证手上拥有的股票数量不超过一支
     * 返回最多交易k次的情况下能够获得大的最大利润
     */

    class Solution1{

        //记录当前时间点前面的最小价格，与当前时间点相减就是当前时间点能够获取的最大利润，遍历所有时间点

        public int maxIncome(int[] arr){

            int minIn = Integer.MAX_VALUE;

            int resMaxIncome = Integer.MIN_VALUE;

            for(int i : arr){

                minIn = Math.min(minIn, i);
                resMaxIncome = Math.max(resMaxIncome, i - minIn);
            }

            return resMaxIncome;
        }
    }


    class Solution2{

        //只要在有上升趋势就能算咋获取的利润中
        public int maxIncome(int[] arr){

            int maxIncome = 0;

            for(int i = 1; i < arr.length; i++){

                if( arr[i] > arr[i - 1]){

                    maxIncome += arr[i] - arr[i - 1];
                }
            }

            return maxIncome;
        }

    }

    class Solution3{

        public int maxIncome(int[] arr, int k){

            if(k > arr.length/2){

                //长度为arr.length的数组，上坡最多有/2个，因为有下坡才有上坡
                //如果没有下坡就只能算同一个上坡，所以k在超过/2的情况下，再往上走是没有意义的，且与问题二的处理策略相同，只需要调用问题二的解决策略就行了

                return new Solution2().maxIncome(arr);
            }


            /*
            现在分析二维表的建立：
            构建一张dp[arr.length][k]的二维表
            表示在 0 -i 的范围内，限制交易k次，能够获得的最大利润
            明显的，第0列（限定交易次数0）全是0
            第0行 在0-0时间范围内进行交易，获取的利润也都是1

            依赖关系分析：
            在dp[i][j]的情况下，有两种情况，在i时间点进行交易和不在i时间进行交易
            如果不在i时间进行交易
            其依赖点就是dp[i-1][k]，因为不做交易的话就与在0- i-1，限定交易次数k次的情况相同了
            如果在i位置进行交易
            明显的，i位置的交易行为不可能是单单的买入，这样不管怎样，相较于前面的情况都是亏损的
            所以如果在i位置有交易行为，一定是卖出行为
            这时候，我们就需要对这个股票是什么时候买入的进行讨论了
            有以下的情况：
            意思是，在 i - m 位置做出决策之后，再在i时间买
            i 时间买，i时间卖（sb行为，但是在程序上合理    dp[i][k-1]+ [i] - [i]
            i-1 时间买     dp[i-1][k-1] + [i] - [i - 1]
            i-2 时间买     dp[i-2][k-1] + [i] - [i - 2]

            0   时间买     dp[0][k-1] + [i] - [0]
            取这几个时间中的较大值

            通过观察我们额可以发现，i+1位置的决策如下：
            dp[i+1][k-1] + [i+1] - [i+1]

            dp[i][k-1] + [i+1] - [i]
            dp[i-1][k-1] + [i+1] - [i-1]
            dp[i-2][k-1] + [i+1] - [i-2]

            可以发现的，将[i+1]提出去之后，
            (dp[i][k-1] - [i] 部分)
            剩下的部分是之前比较过的，所以相当于我们的任务只是比较新加进来的和之前的想不的更大值是多少就行了
             */

            int[][] dp = new int[arr.length][k];

            for( int i = 0; i < arr.length; i++){

                dp[i][0] = 0;//完成第一列填充
            }

            for( int i = 0; i <k; i++){

                dp[0][i] = arr[0];//完成第一行填充
            }


            //填写顺序：每一列从上到下，列从左向右
            for( int j = 1; j < k; j++){

                int max = dp[0][j-1] - arr[0];//记录之前比较结果
                for( int i = 1; i < arr.length; i++) {

                    max = Math.max(max, dp[i - 1][j - 1] - arr[i - 1]);//比较新加入的值)

                    int noFunction = dp[i - 1][j];//不进行交易

                    dp[i][j] = Math.max(max + arr[i], noFunction);//比较交易和不交易的最大值
                }
            }

            return dp[arr.length - 1][k - 1];
        }
    }

}
