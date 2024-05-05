import java.util.HashMap;

public class 动态规划7 {

    /**
     * 题目背景：
     * 给定一个数组time，代表所有人喝完咖啡的时间点
     * 给定一个参数wash，代表将咖啡杯放进机器中清洗需要花费的时间（咖啡机只能单线洗一个杯子
     * 给定一个参数dry，代表将咖啡杯自然晾干需要花费的时间（可以同时自然晾干多个杯子
     *
     */

    public static void main(String[] args) {

    }

    public static class Solution1{

        public int method(int[] time , int wash , int dry){

            return endTime(time, wash, dry ,0 ,0);
        }

        public int endTime(int[] time, int wash , int dry , int index , int availableTime) {
            //index代表此时正在分析第几个咖啡杯
            //availableTime代表咖啡机开始空闲的时间点
            if(index == time.length-1){
                //分析到最后一个杯子

                return Math.min(
                        Math.max(availableTime, time[index]) + wash , time[index]+ dry
                );
                //选择洗干净这个杯子或者晾干这个杯子
            }

            int washDecide = Math.max(availableTime, time[index]) + wash;
            int next = endTime(time, washDecide, dry, index+1, availableTime + wash);

            int timeDecide1 = Math.max(washDecide , next);

            int dryDecide = time[index] + dry;
            next = endTime(time , wash , dry , index+1, availableTime);

            int timeDecide2 = Math.max(dryDecide , next);

            return Math.max(timeDecide1 , timeDecide2);

        }
    }

    public static class Solution2{
        //动态规划(感觉直接构建二维数组的话会造成很多的浪费，所以我自己的考虑是使用傻缓存

        HashMap<String , Integer> map;

        Solution2(){

            map=new HashMap<>();
        }

        public int endTime(int[] time , int wash , int dry , int index , int availableTime){

            StringBuilder sb =new StringBuilder();
            sb.append(index+"|"+availableTime);

            if(map.containsKey(sb.toString())){

                return map.get(sb.toString());
            }

            if(index == time.length-1){

                int t = Math.min( Math.max(availableTime, time[index]) + wash , time[index]+ dry );
                map.put( sb.toString() , t);
                return t;
            }

            int washDecide = Math.max(time[index], availableTime) + wash;
            int next = endTime(time , wash,  dry , index+1, availableTime + wash);
            int timeDecide1 = Math.max(washDecide , next);

            int dryDecide = time[index] + dry;
            next = endTime(time , wash , dry , index+1, availableTime);
            int timeDecide2 = Math.max(dryDecide , next);

            int t = Math.min(timeDecide1 , timeDecide2);
            map.put( sb.toString() , t);
            return t;
        }
    }

    public static class Solution3{
        //纯粹的动态规划（也不是不行

        public int method(int[] time , int wash , int dry){

            int maxTime = 0;

            if(wash >= dry){

                maxTime = time.length * dry;
            }else{

                for(int i=0;i<time.length;i++){

                    maxTime = Math.max(maxTime , time[i]) + wash;
                }
            }//确定可能花费的最长时间

            int[][] dp = new int[time.length][maxTime+1];//maxTime是可以达到的

//            if(index == time.length-1){
//
//                int t = Math.min( Math.max(availableTime, time[index]) + wash , time[index]+ dry );
//                map.put( sb.toString() , t);
//                return t;
//            }

            dp[time.length-1][maxTime] = maxTime;

            for(int nowTime = maxTime; nowTime >= 0; nowTime--){

                for(int index = time.length-1; index >= 0; index--){

                    //完成填写就行
                }
            }

            return dp[0][0];
        }

    }
}
