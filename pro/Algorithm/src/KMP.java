import java.util.Scanner;

public class KMP {

    /**
     * 关于KMP算法的经典构建
     * 对于场景 验证str2是否是str1的子串
     * 先对str2进行预处理：
     * 求出str2中每个节点 前面的串 中前串和后串相同的最长长度是多少
     * 例如：
     *  'abbsabb'中 前串和后串最长相等的长度为3：abb 和 abb
     * 同时规定：没有 前面的串 则返回-1，有 前面的串 但是没有前串和后串相等 则返回0
     * ====================================================================
     * 在进行比较时，当匹配到i位置发现相等之后继续开始普通匹配，如果相等则继续匹配，如果不相等
     * 则
     * 例如：         01234567
     *      str1：~~~abbsadds~~~~
     *      str2：   abbsabbz~~~~
     *      匹配到了7位置发现两个字符串不相同，同时查看str2中7位置前面的最长相等前串和后串长度
     *      发现是3就可以直接将abb向后移动到匹配位置，如：
     *      str1：~~~abbsadds~~~~
     *      str2：       abbsabbz~~~~
     *      同时从不相同的位置（7位置）继续开始匹配（因为移动之后重合的区域一定相等，如果再遇到相同的则可以重复以上过程
     * 原理解释：
     *      对于字符串~~~---|||---s~~~
     *                 ---|||---a~~~
     *      发现a和s位置不相等，但是前面的位置都是相等的，则只有可能str2向后推移相同长度的前串后串长度，才有可鞥再次遇到相同的串
     *         0123456789012345
     *      ~~~---|||---s~~~
     *             ---|||---a~~~
     *      你可能说像这这样的更长匹配方式会发现，如果真的像这样匹配则再计算前串后串的时候就会将其计算出来，
     *      因为这造成了更长相同前串后串出现
     */

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        String str1 = s.nextLine();
        String str2 = s.nextLine();

        Solution1 solution1 = new Solution1();

        System.out.println(solution1.mainMethod(str1, str2));

        s.close();
    }

    public static class Solution1{
        //当我听了课自己写出来的

        public int mainMethod(String str1 , String str2) {

            int[] arr = calculationMaxLength(str2);

            char[] s1 = str1.toCharArray();
            char[] s2 = str2.toCharArray();

            for(int i = 0; i < s1.length; i++){

                if(s1[i] == s2[0]){

                    for(int j = 0; j < s2.length; j++){

                        if(i+j >= s1.length){

                            return -1;
                        }

                        if(s1[i+j] == s2[j]){

                            ;
                        }else{

                            if(arr[j] == 0){

                                i = i + j -1;//为什么要减去这个1：
                                //break跳到外循环的末尾，会执行++操作没所以要前去这个1
                                break;
                            }

                            i = i+j-arr[j];
                            j = arr[j]-1;//减一道理同理
                        }

                        if(j == s2.length-1){

                            return i;
                        }
                    }
                }

            }

            return -1;
        }

        public int[] calculationMaxLength(String str) {
            int[] res = new int[str.length()];

            char[] chars = str.toCharArray();

            res[0] = -1;
            res[1] = 0;

            for(int i = 2; i < chars.length; i++) {

                res[i] = lengthMax(chars , i);
            }

            return res;

        }

        public int lengthMax(char[] str , int index) {

            int result = 0;
            int length = 0;
            for(int i = 0; i < index-1; i++) {//为什么减1：前面的串 abb 若是整个读取abb，则会导致abb == abb，所以不能包括前面的串的全部

                StringBuilder before = new StringBuilder();
                StringBuilder last = new StringBuilder();

                for(int j = 0; j < length; j++) {

                    before.append(str[j]);
                }

                for(int j = index - length; j < index; j++) {

                    last.append(str[j]);
                }

                if(before.toString().equals(last.toString())) {

                    result = length ;
                }

                length ++;
            }

            return result;
        }

    }

    public static class Solution2{
        //老师的解法
        public int mainMethod(String str1 , String str2) {

            if(str1 == null || str2 == null) {

                return -1;
            }

            if(str1.length() == 0 || str2.length() == 0 || str1.length() < str2.length()){

                return -1;
            }

            char[] s1 = str1.toCharArray();
            char[] s2 = str2.toCharArray();

            int[] arr = calculationMaxLength(str2);

            int p1 = 0, p2 = 0;//分别指向两个字符串位置的指针

            while(p1 < s1.length && p2 < s2.length){

                if(s1[p1] == s2[p2]){

                    p1++;
                    p2++;
                }else if(arr[p2] == -1){
                //这个分支暗含条件：s1[p1] != s2[p2] 且 p2==0
                    p1++;
                }else{
                    p2 = arr[p2];
                    //此时的比对位置p1并不是像我的方法一样一直指向str1匹配时的开头，而是在正在进行匹配的地方，已经相当于我的i+j位置了
                }
            }

            return p2 == s2.length ? p1 - p2 : -1;
        }

        public int[] calculationMaxLength(String str) {

            if( str.length() == 1) {

                return new int[]{-1};
            }

            int[] res = new int[str.length()];

            char[] chars = str.toCharArray();

            res[0] = -1;
            res[1] = 0;

            int index = 2;//当前在对数组哪里进行分析
            int cn = 0;//有两个意思，同时代表此时是哪个字符继续进行比较，也代表可能相等的字符串有多长
            /*
            例如：现在为abb cfd abb cf~~~，检查到了f，c的信息已知
                           012 345 678
            已知前面的字符串是abb cfd abb明显的，cin=3
            在此基础上，我们只需要比较f前面的c和c前面的最长相同前串的后一个字符是什么，
            若是相等在此cn的基础上+1，不相等则调用cn前面的相等最长子串

            实例：    0123456789             上一个位置| |当前位置
                对于： __\__---__\__||||__\__---__\__空空
                在上一个空的基础上，我么已经计算得到了最长相同前串是  __\__
                所以我们只需要确定前一个空是不是等于 __\__（前串）的后一个字符-
                如果相等，则代表最长前串变成了 __\__-
                如果不相等，还有希望相等，就是当前-（6位置）前面的最长相等前串__
                __\可能会与 __空最为相同的前后串出现
                为了达成这一步，相当于cn跳到了cn之前的相同最长前串的位置进行下一步比较
             */

            while(index < chars.length) {

                if(chars[index] == chars[cn]) {

//                    res[index] = cn+1;
//                    index++;
//                    cn++;以下采用等价简写形式：
                    res[index++] = ++cn;

                }else if(cn > 0){
                    //cn不是第一个元素（说明前面的最长相同子串可能还有搭配的希望
                    cn = res[cn];
                }else {

                    res[index++] = 0;
                }
            }

            return res;
        }


    }
}
