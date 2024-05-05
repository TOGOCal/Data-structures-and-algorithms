public class 动态规划6 {

    /**
     * 问题背景：
     * 寻找两个字符串的最长公共子序列
     *
     * 问题模型考虑：
     * 凡是这种给定两个样本去求一些东西的题，都可以用这种一个样本作为表格横列，一个作为表格纵列的做法
     *
     */

    public static void main(String[] args) {

    }


    public static class Solution1{
        //直接使用模型抽象化成表格

        /**
         * 表格代表含义：
         * 表格位置 i,j代表 str1在 0 - i的的长度对应的子串 与
         * str2在0 - j的长度对应的子串的最长公共子序列
         *
         */
        public static int method(String str1 , String str2){
        //这是只返回字符串长度的
            char[] char1 = str1.toCharArray();
            char[] char2 = str2.toCharArray();

            int[][] map = new int[char1.length][char2.length];

            if(char1[0] == char2[0]){

                map[0][0] = 1;
            }

            for(int i=1;i<char2.length;i++){

                    map[0][i] = map[0][i-1];
            }

            for(int i=1;i<char1.length;i++){

                    map[i][0] = map[i-1][0];
            }
            //完成对第一行以及第一列的初始化


            for(int i=1;i<char1.length;i++){

                for(int j=1;j<char2.length;j++){

                    map[i][j] = Math.max(map[i-1][j],map[i][j-1]);

                    if(char1[i] == char2[j]){

                        map[i][j] = Math.max(map[i][j] , map[i-1][j-1]+1);
                    }else {

                        map[i][j] = Math.max(map[i][j] , map[i-1][j-1]);
                        //不加其实也对
                    }
                }

            }

            return map[char1.length-1][char2.length-1];
        }

        public static String stringMethod(String str1 , String str2){

            char[] char1 = str1.toCharArray();
            char[] char2 = str2.toCharArray();

            StringBuilder[][] map = new StringBuilder[char1.length][char2.length];

//            for(int i=0;i<char1.length;i++){
//
//                map[i] = new StringBuilder[];
//
//                for(int j=0;j<char2.length;j++){
//
//                    map[i][j] = new StringBuilder();
//                }
//            }

            if(char1[0] == char2[0]){

                map[0][0].append(char1[0]);
            }

            for(int i=1;i<char2.length;i++){

                    map[0][i].append(map[0][i-1]);
            }

            for(int i=1;i<char1.length;i++){

                    map[i][0].append(map[i-1][0]);
            }

            for(int i=1;i<char1.length;i++){

                for(int j=1;j<char2.length;j++){

                    StringBuilder sbHelp = map[i-1][j].length() > map[i][j-1].length() ? map[i-1][j] : map[i][j-1];

                    if(char1[i]==char2[j]){
                        sbHelp = sbHelp.length() > map[i-1][j-1].length()+1 ? sbHelp : map[i-1][j-1];
                        sbHelp = new StringBuilder(sbHelp);
                        sbHelp.append(char1[i]);
                    }
                }
            }

            return map[char1.length-1][char2.length-1].toString();
        }
    }

    public static class Solution2{
        //暴力递归写一下

        public static String method(String str1 , String str2 , int p1 ,int p2){
            //p1,p2代表正在分析哪个位置

            String s1 = method(str1 , str2 , p1 -1 , p2);
            String s2 = method(str1 , str2 , p1 , p2 - 1);

            String sHelp = s1.length() > s2.length() ? s1 : s2;

            if(s1.charAt(p1)==s2.charAt(p2)){

                String str = method(str1, str2 , p1-1,p2-1);
                sHelp = s1.length() > str.length()+1 ? sHelp : str + s1.charAt(p1);
            }

            return sHelp;
        }
    }
}
