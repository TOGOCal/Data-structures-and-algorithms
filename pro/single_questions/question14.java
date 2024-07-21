import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class question14 {

    /**
     * 问题提背景：
     * 给定一个正数数组，求其子集的最小不可组成和是多少
     * 定义：
     * 子集：不要求连续，不要求保留顺序，只是从数组中取数
     * 子序列：在子集的基础上，要求保持原有前后顺序
     * 子数组：在子序列的基础上，要求取数连续
     * （本题要求为子集
     * 最小不可组成喝：
     *  若是枚举出所有子集的和，如果在最小到最大之间有数不存在，则取所有不存在的数中最小的那个
     *  如果从最小到最大的区间范围内都连续，则定义最小组成和为最大和+1
     */


    /**
     * 进阶题目：
     * 在之前的题目中。时间复杂度只能做到n * sum的时间复杂度
     * 现给定条件：数组中一定包含1，则能否用更简单的方式解决题目
     */

    /**
     * 类似题目：
     * 给定一个已经排好序的正数数组，和一个属aimRange
     * 求如果想要使得所有1-aimRange中的所有数字都能够被加到
     * 求至少好需要在数组中增添几个数
     */

    static class Solution {

        /**
         * 思路：
         * 第一遍遍历：得出组成和综合
         * 同时得出最小值min
         * 则所有可能的取值范围就是在min到sum之间了
         * 构建一张二维表，类型是boolean
         * 横轴代表所有可能的值
         * 纵轴小标同时也代表数组下表
         *
         * 这张二维表的转意是
         * 前index位置的元素能够组成其横轴中的哪些值，
         * 从左往右，从上到下填写这张表
         * 具体填写逻辑：
         *  可以不取这个位置的数，，那每个位置的值就与其头顶上的取值相同
         *  可以取这个位置的数，那每个位置的值就与头顶上value-arr[index]的值相同
         *  这两个只要成立一个这个位置就是true
         *
         *  最后遍历最下面一行，第一个false就是得不到的数
         */

        public int minUnformedSum(int[] nums) {

            int sum = 0;
            int min = Integer.MAX_VALUE;

            for(int i: nums) {

                sum += i;
                min = Math.min(min, i);
            }

            //0-1可以取两个数
            int sumNum = sum - min + 1;

            boolean[][] dp = new boolean[nums.length][sumNum];

            dp[0][nums[0]-min] = true;//第一排能够到达的数只有这个数代表的值

            for(int i = 1; i < nums.length; i++) {

                for(int j = 0; j < sumNum; j++) {

                    if(j+min == nums[0]){

                        dp[i][j] = true;
                    }else{

                        //dp[i][j] = dp[i-1][j] || (j >= nums[0] ? dp[i-1][j-nums[i]] : false);
                        dp[i][j] = dp[i-1][j] || (j >= nums[0] && dp[i - 1][j - nums[i]]);
                    }

                }
            }

            for(int j = 0; j < sumNum; j++) {

                if(!dp[nums.length-1][j]){

                    return j + min;
                }
            }

            return sum + 1;
        }
    }

    static class Solution2 {

        public int minUnformedSum(int[] nums) {

            int range =1;//从1开始到range之内的数都是可以到达的
            //从1开始的原因就是因为已知有1，所以1-1之内都是可以到达的

            Arrays.sort(nums);//排序

            for(int i: nums) {

                //由于1-range之内都是可以到达的
                //现在加上这个数后到达的区间为i- 1+i - range+i => i - range+i
                //只要 i > range+1就不能拼接两个区间了（等于的话还是连续的i刚好是range下一个

                if(i > range+1){

                    return range+1;
                }else{


                    range += i;
                }
            }

            return range+1;
        }
    }

    public class MySolution3{

        public List<Integer> minUnformedSum(int[] nums , int aimRange) {
            /*思路：
                        先填充第一个数前面的区间，指导包含第一个数
                        逐渐往后遍历，直到range扩充到了aimRange：
             */

            List<Integer> result = new ArrayList<>();

            int range=0;

            for(int i: nums) {

                while ( i > range+1){
                    //为什么加以：2和3仍然是连续的
                    result.add(range+1);
                    range+=range+1;

                    if( range >= aimRange){

                        return result;
                    }

                }

                //当范围可以包含这个数之后，可以到达的范围就可以加上这个数了
                //比如要包括17，15小了，需要加上16，达到32，此时边界已经扩到32+17了
                range+=i;

                if( range >= aimRange){

                    return result;
                }

            }

            //直到找完了还没有达到
            while(aimRange > range){

                result.add(range+1);
                range+=range+1;
            }

            return result;
        }

    }
}
