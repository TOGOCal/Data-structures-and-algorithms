import java.util.HashMap;

public class question5 {

    public static void main(String[] args) {

    }

    public class PreQuestion{
        /**
         * 对问题5的启发性问题：
         * 给定一个数组a ，给定目标数target，
         * 返回a的子数组中能够相加和为target的子数组个数(子数组一定连续
         * 数组中可能有正有负
         */

        public int mainMethod1(int[] arr ,int target){

            return howMany(arr , target , 0 , 0);
        }

        private int howMany(int[] arr , int target , int nowResult , int index){

            if(nowResult == target){

                return 1;
            }

            if(index == arr.length){

                return 0;
            }


            int result = 0;

            for(int i = index ; i < arr.length ; i++){

                result += howMany(arr , target , nowResult+arr[i] , index+1);
            }

            return result;
        }

        public int mainMethod2(int[] arr , int target){
            //算法分析：将所有子串归类，归为：以index位置结尾的的子串
            //将问题拆分：以index位置的元素结尾的子串中有多少个累加和可以等于target的，将所有相加
            //将问题处理：index位置有多少个相加等于target的子数组，等效于以index-1结尾的子数组有多少个累加和等于target-arr[index]
            //问题再处理：确定一个子串的和，相当于这个子串结尾的数之前所有的数字的和减去这个子串第一个字符前面的所有的数字的和

            HashMap<Integer,Integer> valueTimes = new HashMap<>();
            valueTimes.put(0, 1);//什么都不选作为前缀和出现了一次

            int sum = 0;

            int result = 0;
            for(int i = 0 ; i < arr.length ; i++){

                sum += arr[i];

                /**
                 * sum为子串结尾的数之前所有的数字的和
                 * 要减去 子串第一个字符前面的所有的数字的和（简称为前缀和）（存在了map中
                 * 子串第一个字符前面的所有的数字的和 存在了map中，所以只需要找map中有几个
                 * sum - map = target
                 * 检查 map = sum - target
                 */

                result += valueTimes.getOrDefault(sum - target, 0) ;

                valueTimes.put(arr[i], valueTimes.getOrDefault( sum, 0) + 1);

            }

            return result;
        }


    }

    public class Question{
        /**
         * 题目背景描述：
         * 给定一串0 1字符串，
         * 现要求，将这一字符串进行尽可能多的切分
         * 切分的要求是保证每一块切分中的 0 1 比例相同
         *
         * 给定一串字符串
         * 让你返回一个数组记录
         * 这个字符串以index（遍历）位置结尾的同开头子串能够分成多少个最多划分的个数
         */

        /**
         * 题目分析：
         * 如果这个整个字符串中1和0的比例是a
         * 那所有的分割中 隔出来的所有小块中比例也是a
         * 进一步转化问题，如果一个前缀中比例是a，那剩下的部分中比例也一定是a
         */

        public int[] mainMethod1(String str){

            char[] chars = str.toCharArray();
            int[] result = new int[chars.length];

            HashMap<String,Integer> valueTimes = new HashMap<>();
            //用a|b的方式保存比例
            valueTimes.put("0|0", 1);

            int[] sum = {0,0};

            for(int i = 0 ; i < chars.length ; i++){

                sum[ chars[i]-'0' ]++;
                StringBuilder sb = new StringBuilder();
                sb.append(sum[0]).append("|").append(sum[1]);

                result[i] = valueTimes.getOrDefault( sb.toString() ,0 )+1;
                valueTimes.put( sb.toString() , result[i]);
            }

            return result;
        }

    }
}
