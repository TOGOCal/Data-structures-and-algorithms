public class question2 {

    /**
     * 题目描述：
     * 给定一个数组arr
     * 代表每个小朋友的得分
     * 要求：每个每个小朋友只会和左右两边的小朋友进行比较
     * 得分较大的小朋友拿到的糖果不能比左右两边得分小朋友拿到的糖果少
     * 如果得分相同，那怎样分糖果都无所谓
     * 每个小朋友至少拿到1块糖果，要求分析怎样分糖果才能够使得分的糖果总数最少
     * 注：小朋友是坐成环形的，所以第一个小朋友和最后一个小朋友要纳入考虑
     */

    public static void main(String[] args) {

    }

    public static int way1(int[] scores) {
        //分析：如果这道题的情况是一条线，而不是环

        int[] left = new int[scores.length];
        int[] right = new int[scores.length];

        left[0] = 1;
        for (int i = 1; i < scores.length; i++) {

            if(scores[i] > scores[i-1]) {

                left[i] = left[i-1] + 1;
            }else{
                left[i] = 1;
            }
        }//只有大于的时候上升，其余情况返回1

        right[scores.length-1] = 1;
        for (int i = scores.length-2; i >= 0; i--) {

            if(scores[i] > scores[i+1]) {

                right[i] = right[i+1] + 1;
            }else{
                right[i] = 1;
            }
        }

        int result = 0;
        for(int i =0; i < scores.length; i++) {

            result += Math.max(left[i], right[i]);//以大的作为结果
        }

        return result;
    }

    public static int way2(int[] scores) {
        //如何处理环的结构
        /**
         * 选出最小的数据，将其加在两边
         *
         * 例如：  3 2 1 4 3 2 5
         * 等效为：1 4 3 2 5 3 2 1//这样就可以完成环的解耦合
         */

        int minPosition = -1;

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < scores.length; i++) {

            if(scores[i] < min) {
                min = scores[i];
                minPosition = i;
            }
        }

        int[] arr = new int[scores.length+1];

        for(int i =0;i<arr.length;i++){

            if(minPosition + i == scores.length){

                minPosition = -i;
            }
            arr[i] = scores[i+minPosition];
        }//完成更改

        //再重复上面方法的步骤
        int[] left = new int[arr.length];
        int[] right = new int[arr.length];

        left[0] = 1;
        for (int i = 1; i < arr.length; i++) {

            if(arr[i] > arr[i-1]) {

                left[i] = left[i-1] + 1;
            }else{
                left[i] = 1;
            }
        }//只有大于的时候上升，其余情况返回1

        right[arr.length-1] = 1;
        for (int i = arr.length-2; i >= 0; i--) {

            if(arr[i] > arr[i+1]) {

                right[i] = right[i+1] + 1;
            }else{
                right[i] = 1;
            }
        }

        int result = 0;
        for(int i =0; i < arr.length; i++) {

            result += Math.max(left[i], right[i]);
        }

        result -= 1;//开头的位置
        return result;
    }

}
