import javax.imageio.stream.IIOByteBuffer;
import java.util.HashMap;
import java.util.Scanner;

public class question9 {

    /**
     * 问题背景：
     * 给定两个数组arrx和arry
     * 第i个点的x，y坐标分别是arrx[i]和 arry[i]
     * 先需要求，在这个平面中一条直线最多穿过几个点
     */
    public static void main(String[] args) {

        Scanner s= new Scanner(System.in);

        int n = s.nextInt();

        int[] arrx = new int[n];
        int[] arry = new int[n];

        for(int i=0;i<n;i++){

            arrx[i] = s.nextInt();
            arry[i] = s.nextInt();
        }

        System.out.println(new Solution().maxNumber(arrx,arry));


        s.close();
    }

    static class Solution{
        /**
         * 解决方案：
         * 遍历每个点，假设直线是从这个点开始的，
         * 求经过了最多点的直线经过的点的数量
         *
         * 一些细节：
         * 1.前面考虑过的点在后面的点进行分析时不用考虑
         * 2.斜率的不表现形式不能用double（可能有精度损失），需要用字符串的形式
         * 3.点之间的关系有三种：重合，斜率正无穷，正常斜率
         */

        public int maxNumber(int[] arrX , int[] arrY){
            int max = 0;

            int num = arrX.length;

            for(int index = 0; index < num; index++){

                int samePositionNum = 0;//相同位置的点的数量
                int sameXNum = 0;//相同的x，但是不同y，这是斜率正无穷的点
                int sameYNum = 0;//相同的y，但是不同x，这是斜率正无穷的点(逻辑上不需要，但是为了能够方便的进行字符串存储，还是希望计算公因数的两个数不要有0
                HashMap<String , Integer> slopeMap = new HashMap<>();//斜率作为key，经过的点的数量作为value
                int x = arrX[index];
                int y = arrY[index];

                for(int i = index+1 ; i < num; i++){
                    if(x == arrX[i] && y == arrY[i]){

                        samePositionNum++;
                    }else if(x == arrX[i]){

                        sameXNum++;
                    }else if(y == arrY[i]){

                        sameYNum++;
                    }else{

                        int cmpY = arrY[i] - y;
                        int cmpX = arrX[i] - x;

                        boolean isPositiveInfinity = true;//是不是正数
                        if(cmpX < 0){

                            isPositiveInfinity = !isPositiveInfinity;
                        }

                        if(cmpY < 0){

                            isPositiveInfinity = !isPositiveInfinity;
                        }

                        cmpX = Math.abs(cmpX);
                        cmpY = Math.abs(cmpY);

                        int gcd = getGreatestCommonFactor( cmpX , cmpY );

                        cmpX = cmpX/gcd;
                        cmpY = cmpY/gcd;

                        StringBuilder key = new StringBuilder();

                        if(!isPositiveInfinity){

                            key.append("-");
                        }

                        key.append(cmpX);
                        key.append("/");
                        key.append(cmpY);


                        if(slopeMap.containsKey(key.toString())){

                            slopeMap.put(key.toString() , slopeMap.get(key.toString()) + 1);
                        }else{

                            slopeMap.put(key.toString() , 1);
                        }

                    }

                }

                int mapMax = 0;//在哈希表中的最大值

                for(int value : slopeMap.values()){

                    mapMax = Math.max(mapMax , value);
                }

                max = Math.max( max , 1 + samePositionNum + Math.max( Math.max( sameXNum , sameYNum ) , mapMax ) );
                //加一是因为包括了这个点本身

            }

            return max;

        }


        //找到最大公因数
        /**
         * 辗转相除法原理：
         * 取模之后剩下的，
         * 最小公因数一定也能解决掉剩下的，因此将剩下的作为新的被除数，直到找到符合的
         */
        public int getGreatestCommonFactor(int a , int b){

            if(a == b){

                return a;
            }

            int bigger;
            int smaller;

            if(a > b){

                bigger = a;
                smaller = b;
            }else{

                bigger = b;
                smaller = a;
            }

            //辗转相除法
            int last = bigger%smaller;

            while(last != 0){

                bigger = smaller;
                smaller= last;

                last = bigger%smaller;
            }


            return smaller;
        }

    }


}
