public class 动态规划2 {

    /**
     * 题目描述：
     * 规定 1 对应字符 a ， 2 对应字符b ，。。。。。。26对应字符z
     * 给定一串纯数字字符的字符串
     * 判断最多能解析出多少个小写字母字符串
     * //详情请见暴力递归3
     */

    public static void main(String[] args) {

    }

    public static class Solution{
        //普通暴力递归方法:

        public static int method(String str){

            char[] chars = str.toCharArray();

            return howMany(chars , 0);
        }

        public static int howMany(char[] str , int index){

            if(index == str.length){

                return 1;//代表这是一种合理的方案
            }

                //当前分析位置不是最后一位
            if(str[index] == '0'){

                return 0;
            }else if(str[index] == '1'){

                int num = howMany(str, index+1);
                if(index != str.length-1){
                    num += howMany(str, index+2);
                }//当前分析位置不是最后一位


                return num;
            }else if(str[index] == '2'){

                int num = howMany(str, index+1);
                if(index != str.length-1 && str[index+1] <= '6'){

                    num += howMany(str, index+2);
                }

                return num;
            }else{

                return howMany(str, index+1);
            }

        }
    }

    public static class Solution2{
        //使用动态规划的方案
        //(所有return的位置就是设置dp的时刻

        public static int method(String str){

            char[] chars = str.toCharArray();
            //查找数组分析：
            //在暴力递归中，仅仅使用一个变量
            //欸，好像不存在重复计算欸

            int[] dp = new int[chars.length+1];
            dp[chars.length] = 1;

            for(int i =chars.length-1; i >= 0; i-- ){

                if(chars[i] == '0'){
                    dp[i] = 0;
                }else if(chars[i] == '1'){

                    dp[i] = dp[i+1];
                    if(i != chars.length-1){

                        dp[i] += dp[i+2];

                    }

                }else if(chars[i] == '2'){

                    dp[i] = dp[i+1];

                    if(i != chars.length-1 && chars[i+1] <= '6'){

                        dp[i] += dp[i+2];

                    }
                }
            }

            return dp[0];
        }
    }
}
