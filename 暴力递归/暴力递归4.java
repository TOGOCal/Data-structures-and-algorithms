public class 暴力递归4 {

    /**
     * 问题描述：
     * 给定一个数组a，代表放置成一排的纸牌
     * a同学和b同学可以选择拿走最左边或者右边的牌
     * 使得他们的牌总数最大，
     * a和b都绝顶聪明，试返回谁会赢
     *
     */

    public static void main(String[] args) {

    }

    public static class Solution{

        public static boolean who(int[] arr){
            //返回true就是a赢
            return method(arr , 0 , 0 , -1 , arr.length);
        }

        public static boolean method(int[] arr , int weight1 , int weight2 , int left , int right){
            /**
             * 函数描述：
             * 纸牌，
             * 现在的，现在的,(当前要拿牌的是weight1
             * 拿到左边和右边的第几张牌了(初始都是无效值
             * 函数目的：返回当前状态下谁会胜利
             */

            if(right == left){

                return weight1 > weight2;
            }

            boolean key = method(arr , weight2 , weight1 + arr[left+1] , left + 1 , right);
            /**
             * 传参说明：
             * 当a拿完牌后，下一个函数拿牌的是b，所以要把两个weight的前后顺序互换
             * 如果b在这个函数中赢了，就会返回true，a肯定不会允许b就这么赢了
             * 所以如果函数返回值是true，a就去尝试另一种拿牌方式
             * 由于这种拿已经无法改变，直接返回即可
             */

            if(key){
                //当b后面拿牌赢了，那说明a不能拿左边的牌
                return method(arr , weight2 , weight1 + arr[right-1] , left  , right-1);
            }

            return !key;
        }
    }
}
