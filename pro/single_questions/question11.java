import java.util.Scanner;

public class question11 {

    /**
     * 问题背景：
     * 给定三个字符串str1 , str2 , aim
     * 判断aim是不是str1和str2的交错串
     * 交错串：
     * aim中仅包含str1和str2中的字符，且str1和str2中字符的相对顺序不变
     * 例如
     * str1 = "ab"  str2 = "12"
     * 则其交错串可以是 ab12 a1b2 a12b 12ab等等
     */

    /**
     * 思路考虑：
     * 考虑aim的前i+j的串能否由str2的前i个字符和str1的前j个字符交错组成
     * 构建一张boolean的二维表
     * dp[i][j] 表示aim的前i+j个字符能否由str2的前i个字符和str1的前j个字符交错组成
     *
     * 分析dp[length-1][length-1]的值：
     * 最后的位置的字符要么从str1最后取，要么从str2最后取
     * 因此dp[length-1][length-1] = (dp[length-1][length-2] &&  aim.last == str1.last) || (dp[length-2][length-1] && aim.last == str2.last) )
     * 同理，每个位置的dp都可以通过这种方式确定
     */

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        String str1 = s.nextLine();
        String str2 = s.nextLine();

        String aim = s.nextLine();

        System.out.println(new Solution().isStaggeredComposition(str1,str2,aim));


        System.out.println();


        s.close();
    }


    static class Solution{

        public boolean isStaggeredComposition(String str1, String str2, String aim){

            char[] crr1 = str1.toCharArray();
            char[] crr2 = str2.toCharArray();
            char[] crrAim = aim.toCharArray();

            boolean[][] dp = new boolean[crr2.length+1][crr1.length+1];
            //crr1做行，crr2做列

            dp[0][0] = true;//str1前0个字符和str2前0个字符可以组成空串
            for(int i = 1; i <= crr1.length; i++){

                dp[0][i] = ( dp[0][i-1] && crr1[i-1] == crrAim[i-1] );
            }//填充aim的前i能不能由str1的前i组成

            for(int i = 1; i <= crr2.length; i++){

                dp[i][0] = ( dp[i-1][0] && crr2[i-1] == crrAim[i-1] );
            }

            //填充中间的值
            for(int i = 1; i <= crr2.length; i++){

                for(int j = 1; j <= crr1.length; j++){

                    dp[i][j] = (dp[i-1][j] && crr2[i-1] == crrAim[i+j-1]) || (dp[i][j-1] && crr1[j-1] == crrAim[i+j-1]);
                }
            }

            return dp[crr2.length][crr1.length];

        }

    }


}
