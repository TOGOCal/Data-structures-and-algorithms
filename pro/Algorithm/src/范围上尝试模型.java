import java.util.Arrays;

public class 范围上尝试模型 {

    /**
     * 范围上尝试模型是动态规划四大模型之一
     * 以下是这种问题的一个例子：
     */

    /**
     * 问题：
     * 给定一个字符串str，可以向其中任意添加字符，求最少添加多少个字符能够使这个字符串变成回文串
     *
     * 思路：
     * 动态规划转移：
     *  范围L~R这个范围上最少添加多少个字符能够使得L~R范围上的字符串能够成为回文串
     * 基本情况（边界）处理
     *  L=R时不需要进行操作，填0即可
     *  L=R+1时只需要判断这两字符是否相等就行了，不相等填1（向前或向后加一个字符），相等填0
     * 递推过程：
     *  除了边界意外，其他的范围，如果L位置和R位置的字符不相等，
     *  则在[L][R-1]和[L+1][R]这两个范围上分别进行操作，取最小值+1即可（先将少一个字符的字符串变成回文串，再天上这个字符后在对应位置添加一个字符即可
     *  如果L和R位置的字符相同，则只需要将[L+1][R-1]的位置进行调整即可，所以直接[L+1][R-1]=[L][R]就可以了
     * 填写顺序：（L做纵轴，R做横轴）
     *  最终需要得到的是L=0;R=最后位置的值
     *  这个值位于右上角，所以自然的，总体填写顺序是从左往右，从下往上
     *  具体先执行什么其实是无所谓的
     *  可以选择大体从左往右，每列从下往上
     *  也可以选择从下往上，每行从左往右
     *
     *
     *
     *  问题pro：
     *  如果确定最终的一个可能结果字符串长什么样子
     *  思路：
     *  根据dp表反向进行填充
     */

    class Solution{

        public int minInsert(String str){
            if(str == null || str.isEmpty() || str.length() == 1) return 0;
            if(str.length() == 2)return str.charAt(0) == str.charAt(1) ? 0 : 1;

            int[][] dp = getDp(str);


            return dp[0][str.length()-1];
        }

        public int[][] getDp(String str){



            int N = str.length();
            int[][] dp = new int[N][N];
            char[] s = str.toCharArray();


            for(int i = 0 ; i< N ; i++){

                dp[i][i] = 0;//完成主对角线填写
            }

            for(int i = 0; i< N-1 ; i++){

                dp[i][i+1] = ( s[i] == s[i+1] ? 0 : 1 );//完成第二条斜线的填写
            }


            //这里使用答题下到上，每行左到右的填写方式
            for(int L = N-3 ; L>=0 ; L--){

                for(int R = L+2 ; R< N ; R++){//为什么加二：已经填好了两个斜线了

                    dp[L][R] = ( Math.min( Math.min( dp[L+1][R] ,  dp[L][R-1])+1 ,
                            s[L] == s[R] ? dp[L+1][R-1] : Integer.MAX_VALUE) );
                }
            }

            return dp;
        }


        public String getString(String str){

            int[][] dp = getDp(str);

            char[] s = str.toCharArray();

            char[] res = new char[str.length() + dp[0][str.length()-1]];//字符串的长度

            //反向填充可能遇到以下集中情况：
            //1.是由左或者右填充来的
            //2.是由左或者右填充来的，但是不确定是由哪边填充来的（随意
            //3.是由最下角填充来的
            //先给定填充优先级判定：在集中情况都有可能的情况下，先左下，在向左，在向右

            int L = 0;
            int R = str.length()-1;

            int resLeftIndex = 0;
            int resRightIndex = res.length-1;//对结果的填充位置

            while(true){

                if(dp[L][R] == dp[L+1][R-1]){
                    //由对角线直接填充，说明原始字符串左右字母相等，且最终填充在了左右两个边界位置
                    res[resLeftIndex++] = s[L++];
                    res[resRightIndex--] = s[R--];
                }else if(dp[L][R] == dp[L+1][R] + 1){//是在原来的基础上，复制当前范围的第一个字符到末尾得到的

                    res[resRightIndex--] = s[L++];
                }else if(dp[L][R] == dp[L][R-1] + 1) {//是在原来的基础上，复制当前范围的最后一个字符到开头得到的

                    res[resLeftIndex++] = s[R--];
                }

                //每个指针的位置都是等待填写的位置，所以resL = resR 的时候不跳出（因为说明中间还有一个位置没有填好
                if( resLeftIndex > resRightIndex){

                    break;
                }


            }

            return Arrays.toString(res);

        }


    }

}
