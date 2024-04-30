public class 暴力递归5 {

    /**（这道题好像不是暴力递归
     * 问题背景：
     * 一个村庄有n个人
     * 每个人必须且只能送出一封信
     * 每个人必须写只能接受一封信
     * 每个人不能收到自己的信
     * 求总共有多少种送信方案
     */

    public static void main(String[] args) {

    }

    public static class Solution{

        public static int howManyWays(int people) {

            if(people == 0 || people == 1){
                //0个人或者没有人，没有方案
                return 0;
            }else if(people == 2){

                return 1;
            }else if(people == 3){

                return 2;
            }

            //当有n个人的时候，其中一个人a把信送给了b，那么b有两种选择
            //1.把信寄回给a，这是，这个过程与这两个人无关了，剩下的讨论在n-2个人之间进行
            //这也是为什么3也是base case，因为3个人的时候，如果互相寄就会有一个人寄不到
            //2.把信寄给别人，此时a，b形成了一个整体（相当于一个人
            //a等待接受，b等待送出，相当于一个人的行为
            //以此构建递归函数

            return howManyWays(people-1) + howManyWays(people-2);
            //两种情况，一种是寄回去，一种是不寄回去
        }
    }


}

