public class 随机数概率调整 {

    static class Solution1{
        /**
         * 问题背景：
         * 已知random函数能够使得0-1之内的数等等噶率的出现，
         * 因此，0 - 0.6之间的数出现的概率是0.6
         * 现在要求使得 0-x范围的数出现的概率是 x的平方
         */

        public static void main(String[] args) {

            int times = 1000000;

            int count1 = 0;
            int count2 = 0;
            for( int i = 0; i < times; i++){

                if(Math.random() < 0.6){
                    count1++;
                }

                if(random() < 0.6){

                    count2++;
                }
            }

            System.out.println((double)count1/times);
            System.out.println((double)count2/times);
        }

        public static double random(){

            return Math.max(Math.random(), Math.random());
            //两次调用random函数并求最大值可以使得概率平方
            //同理，三次求最大值可以使得概率立方
        }

    }
}
