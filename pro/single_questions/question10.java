import java.util.*;

public class question10 {

    /**
     * 问题背景：
     * 给定一个数组，
     * 每个位置的数字代表从这个数开始最多一步能向前跳几步
     * 求跳到最后一个数
     */

    /**
     * 题目2：
     * 如果一个数的因子只包含2，3，5，则称这个数是丑数
     * 特别的，规定1也是抽数
     * 现需要知道第n个丑数是哪个
     *
     * 思路：每一个丑数一定由其前面的某个丑数或乘2，3，5得到
     */

    public static void main(String[] args) {

        Scanner s =  new Scanner(System.in);

//        int[] nums = new int[s.nextInt()];
//
//        for(int i = 0;i<nums.length;i++){
//
//            nums[i] = s.nextInt();
//        }
//
//        System.out.println(new Solution().minJump(nums));


        int n = s.nextInt();
        System.out.println(new Question2Solution().UglyNumber(n));
        System.out.println(new Question2Solution2().UglyNumber2(n));

        s.close();
    }

    public static class Question2Solution{

        public int UglyNumber(int n){

            //小根堆，每次进3个出两个，所以空间复杂度2N = N
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            int a = 1;
            minHeap.add(a);
            HashSet<Integer> contain = new HashSet<>();

            for(int i =0;i<n;i++){
                a= minHeap.poll();//第一个数（第i+1个数

                if(!contain.contains(a)){

                    contain.add(a);
                }else{

                    i--;
                    continue;//如果重复了，则跳过
                }

                minHeap.add(a*2);
                minHeap.add(a*3);
                minHeap.add(a*5);
            }

            return a;//第n个数
        }

    }

    public static class Question2Solution2{

        public int UglyNumber2(int n){

            //消除堆和哈希表的方法：使用三指针，2指针指着的永远*2，3指针指着的永远乘3，5指针指着的永远乘5
            //每次取得是三者中最小的那个
            //每次乘完对应指针++

            if(n == 0){

                return -1;
            }

            int a = 1;
            int[] nums = new int[n];

            int pointer2 = 0;
            int pointer3 = 0;
            int pointer5 = 0;

            int index = 0;//指向现在是第几个丑数

            nums[0] =a;

            for(int i =1;i<n;i++){

                a = Math.min(nums[pointer2]*2,Math.min(nums[pointer3]*3,nums[pointer5]*5));

                //为什么不用else if：防止出现2*3 = 3*2的重复情况

                if(a == nums[pointer2]*2){

                    pointer2++;
                }

                if(a == nums[pointer3]*3){

                    pointer3++;
                }

                if(a == nums[pointer5]*5){

                    pointer5++;
                }


                nums[++index] = a;

            }

            return nums[index];//第n个数
        }

    }

    static class Solution{

        /**
         * 思路：
         * 假设给定k
         * 求k步内能够到达的最远距离
         * 以及k+1步内能够到达的最远距离
         *
         * 在遍历数组时，一次计算每个位置能够到达的最远距离
         * 如果当前位置的距离大于本布的最大边界，这说明盖到下一步了
         */

        public int minJump(int[] nums){

            if(nums.length == 0 || nums.length == 1){

                return 0;
            }

            int step = 0;//当前是第几步

            int maxLength = 0;//这一步能够到达的最远距离(第0步在0位置) 这个maxLength的位置是有效的
            int nextMaxLength = 0;//下一步能够到达的最远距离 这个值也是个有效位置

            for(int i = 0;i<nums.length;i++){

                if(i > maxLength){

                    step++;
                    maxLength = nextMaxLength;
                }

                nextMaxLength = Math.max(nextMaxLength,i+nums[i]);

                if(nextMaxLength >= nums.length-1){

                    return step+1;
                }
            }


            return -1;//除非是0或负数，否则应该到不了这里
        }


    }
}
