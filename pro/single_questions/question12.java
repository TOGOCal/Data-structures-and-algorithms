import java.util.HashMap;
import java.util.Scanner;

public class question12 {

    public static void main(String[] args) {
        Scanner s= new Scanner(System.in);

        int[] arr = new int[s.nextInt()];

        for( int i = 0; i < arr.length; i++) {

            arr[i] = s.nextInt();
        }

        Solution.Information info = new Solution().canCut(arr);

        s.close();
    }

    /**
     * 题目背景：
     * 给定一个长度大于3的数组
     * 选择三个下标进行切割
     * 看能否切割出 和 相同的四个部分
     * （这也是为什么要有三个数的原因
     * 切掉的数不算在和里面
     * 题目保证每个数是正数
     *
     * 思想：
     * 遍历数组
     * 看哪个位置能够作为第一刀的前缀和
     * 能够当成第一道的条件：
     * 前面的前缀和在后面可以依次找到
     */
    static class Solution{

        public Information canCut(int[] arr) {

            //至少需要七个数，砍掉3个还剩4个
            if (arr.length < 7) {

                return new Information(false);
            }
            //预处理结构，前缀和数组，可以根据数组查找到下标
            HashMap<Integer, Integer> prefixSumMap = new HashMap<>();

            int sum = 0;
            for (int i = 0; i < arr.length; i++) {

                prefixSumMap.put(sum, i);

                sum += arr[i];
            }//得到前缀和数组

            prefixSumMap.put(sum, arr.length - 1);

            int sum1 = 0;
            //为什么从1开始：明显的，0位置不能砍第一刀
            for (int index = 1; index < arr.length; index++) {
                //遍历数组，判断哪个位置可以做成第一刀
                sum1+=arr[index-1];//目前的前缀和

                //这一刀后面还存在一个能够加出sum的我i之，那个位置的后一个可以当成第二刀
                //sum arr[index] sum arr[index]
                int sum2 = sum1*2+arr[index];
                if( prefixSumMap.containsKey(sum2)){
                    int index2 = prefixSumMap.get(sum2);//前缀和

                    int sum3 = sum2+arr[index2] + sum1;

                    if( prefixSumMap.containsKey(sum3)){

                        if( arr[prefixSumMap.get(sum3)]+ sum3 + sum1 == sum) {
                            return new Information(true, new int[]{index, index2, prefixSumMap.get(sum3)});
                        }
                    }

                }
            }

            return new Information(false);
        }

        static class Information{

            boolean isFounded;
            int[] index;

            Information(boolean isFounded){

                this.isFounded = isFounded;
            }

            Information(boolean isFounded,int[] index){

                this.isFounded = isFounded;
                this.index = index;
            }
        }
    }


    /**
     * 寄巧1：
     * 预处理结构
     * 在有很多时候，我们可能需要对数据进行预处理以方便算法的执行
     * 在以下情境中：
     *
     * 给定一个数组，要求
     * 大量查询index=i位置到index=p位置的所有数的累计和
     *
     * 方法：
     * 先预处理为和数组，在根据下表进行相减即可
     */

    static class Solution1{

        static int len;
        //和累加数组
        final static int[] pretreatment = new int[len];

        Solution1(int[] arr){

            len = arr.length;

            int sum = 0;
            for( int i=0;i<len;i++){

                sum+=arr[i];
                pretreatment[i] = sum;
            }//得到和累加数组
        }

        public int sumFromItoJ(int[] arr,int i,int j){

            return pretreatment[j]-pretreatment[i]+arr[i];
        }
    }
}
