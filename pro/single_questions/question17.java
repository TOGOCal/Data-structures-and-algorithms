import java.util.HashSet;

public class question17 {

    //重要思想：限制转换


    /**
     * 问题背景：
     * 给定一个数组，判断其中的最长可  整合子数组   长度是多少
     * 定义：
     * 可整合子数组：
     * 首先是个子数组（连续）
     * 其次，如果将该数组排序，每个数之间的距离是1，就是说像1 2 3 4 5 这种
     * 则称这个数组为可整合数组
     */


    /**
     * 解法分析：
     * 暴力解法：
     * 枚举所有子数组（时间复杂度 N的平方
     * 判断该数组是不是可整合数组（时间复杂度NlogN
     * 所以总共时间复杂度为 N3longN
     */

    /**
     * 优化解法：
     * 将可整合数组的定义进行转化
     * 转变乘：
     * 数组中不存在重复元素，且 最大值减最小值+1 等于 数组的长度
     * 就可以保证数组的可整合了
     * 优化后，虽然还是需要枚举所有的子数组，但是可以做以下优化：
     * 在枚举每个位置作为开头的时候，遇到重复的就跳出（进行剪枝
     * 这样实际消耗时间也会有所减少
     */


    class Solution{

        public int maxIntegratedAbleLength(int[] arr){


            if(arr == null || arr.length == 0){

                return 0;
            }

            int maxLength = 0;

            int maxNum;
            int minNum;

            HashSet<Integer> included = new HashSet<>();

            //通过begin和end的位置对数组在哪进行限定
            for(int begin = 0 ; begin< arr.length ; begin++){
                maxNum = Integer.MIN_VALUE;
                minNum = Integer.MAX_VALUE;

                included.clear();
                for(int end = begin ; end< arr.length ; end++){

                    if(included.contains(arr[end])){

                        //只要遇到加入了相同的就跳出
                        break;
                    }

                    included.add(arr[end]);

                    //记录当前的最大值和最小值
                    maxNum = Math.max(maxNum, arr[end]);
                    minNum = Math.min(minNum, arr[end]);

                    if(end - begin + 1 == maxNum - minNum +1){

                        maxLength = Math.max(maxLength, end - begin + 1);
                    }
                }
            }


            return maxLength;
        }

    }

}
