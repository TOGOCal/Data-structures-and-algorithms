public class 暴力递归7 {

    /**
     * 问题描述：
     * 一个机器人，他只能往左或往右走一格，如果在边界点，则只能往内部走（不能走出去了
     * 现给定：
     * size：区域范围
     * beginPlace：现在机器人所在的位置
     * functionNum：机器人一定要走的步数
     * targetPlace：机器人目标到达的位置
     * 求，机器人有多少种走法能够从初始位置走到终止位置
     */

    public static void main(String[] args) {

    }

    public static class Solution1{

        public static int howMany(int size,int nowPlace,int restFunctionNum,int targetPlace){

            if(restFunctionNum==0){

                return nowPlace == targetPlace ? 1 : 0;
                //如果步数走完了，则代表这是一种合理的走法，返回1
            }

            if(nowPlace == 0){

                return howMany(size,nowPlace + 1,restFunctionNum - 1,targetPlace);
                //当现在的位置在边界，就只能有一种走法
            }else if(nowPlace == size-1){

                return howMany(size,nowPlace - 1,restFunctionNum - 1,targetPlace);
            }

            return howMany(size,nowPlace + 1,restFunctionNum - 1,targetPlace) + howMany(size,nowPlace - 1,restFunctionNum - 1,targetPlace);
        }
    }

    public static class Solution2{
        //改进方法：免去一些重复计算（将一些已经算过的步骤存入数组里
        //动态规划之记忆化搜索
        public static int method(int size,int beginPlace,int functionNum,int targetPlace){

            int[][] arr = new int[size][functionNum+1];
            /**
             * 数组解释：对于下面的方法，size和targetPlace是不变的，所以我们构建的数组应该是
             * 现在的位置，还剩的步骤和有多少种方法的集合
             */

            for(int i=0;i<size;i++){

                arr[i] =new int[functionNum+1];

                for(int j=0;j<functionNum+1;j++){

                    arr[i][j] = -1;
                }
            }

            return howMany(size,beginPlace,functionNum,targetPlace,arr);
        }

        public static int howMany(int size,int nowPlace,int restFunctionNum,int targetPlace,int[][] arr){

            if(arr[nowPlace][restFunctionNum] != -1){

                return arr[nowPlace][restFunctionNum];
                //如果不是-1，说明曾经计算过这个值了，那直接返回就可以了
            }

            if(restFunctionNum==0){

                arr[nowPlace][restFunctionNum] = (nowPlace == targetPlace ? 1 : 0);
                return arr[nowPlace][restFunctionNum];
            }

            if(nowPlace == 0) {

                arr[nowPlace][restFunctionNum] = howMany(size, nowPlace + 1, restFunctionNum - 1, targetPlace, arr);
                return arr[nowPlace][restFunctionNum];
            }else if(nowPlace == size-1){

                arr[nowPlace][restFunctionNum] = howMany(size, nowPlace - 1, restFunctionNum - 1, targetPlace, arr);
                return arr[nowPlace][restFunctionNum];
            }

            arr[nowPlace][restFunctionNum] = howMany(size, nowPlace + 1, restFunctionNum - 1, targetPlace, arr) + howMany(size, nowPlace - 1, restFunctionNum - 1, targetPlace, arr);
            return arr[nowPlace][restFunctionNum];
        }//这样就可以节省一部分计算

    }
}
