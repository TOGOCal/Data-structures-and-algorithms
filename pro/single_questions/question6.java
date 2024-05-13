import java.util.ArrayList;

public class question6 {

    /**
     * 题目描述：
     * 要求：
     * a<=x2<=b
     * c<=y3<=d
     * 求满足 x2 - y3 <e 的组合有多少个
     * 要求x ， y均是正整数
     *
     */

    public static void main(String[] args) {

    }

    public static class Solution{

        int a, b, c ,d,e;

        Solution(int a, int b, int c, int d, int e) {

            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
        }

        public int method(){

            /**
             * 这里约束式和最终的式子中x都是以平方形式出现，所以可以直接存x2，如果不同建议存x
             */

            ArrayList<Integer> x = new ArrayList<>();
            ArrayList<Integer> y = new ArrayList<>();

            for(int i =0;;i++){

                int sqr2 = (int)Math.pow(i,2);
                int sqr3 = (int)Math.pow(i,3);

                if(sqr2 > b && sqr3 > c){

                    break;
                }

                if(sqr2 > a){

                    x.add(i);
                }

                if(sqr3 > d){

                    y.add(i);
                }
            }//存好了所有可能的x和y，时间复杂度n

            int index = 0;//分别指向两个数组

            int result = 0;

            for(Integer i : x){
                //x和y都是从小到大排好了的
                //所以不满足 x2 - y3 <e 的情况是 x过大或者y过小

                for(;index < y.size();index++){
                //为什么indexY是在继承：因为如果x变大了，那之前的y都是不满足的，只有y在之前的基础上找到更大的才有可能满足条件
                    int valueY = y.get(index);
                    if( Math.pow(i,2) - Math.pow(valueY,2) < e ){

                        result += y.size() - index;
                        //从某个位置开始，xy开始满足条件了，在该x的情况下，后面的y都满足
                        //边缘分析：如果index == 0的时候满足，那这个x有y.size()个对应组合  ，y.size() - index,满足
                        break;
                    }
                }
            }

            return result;
        }

    }
}
