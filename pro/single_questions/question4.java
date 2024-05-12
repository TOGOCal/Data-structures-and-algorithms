import java.util.Scanner;

public class question4 {

    /**
     * 问题描述：
     * 给定一个数字len，代表对字符串的限制：字符串最长擦换高难度为len
     * 字符串
     * 按照字典序生成字符串，并判断str是这些字符串中的第几个
     * 给定一个字符串str
     *
     */

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int length = sc.nextInt();
        String str = sc.nextLine();
        str = str.trim();

        Solution1 sl1 = new Solution1();

        System.out.println(sl1.mainMethod(length , str));

        sc.close();
    }

    public static class Solution1{
        /**
         * 方法1：通过枚举罗列所有的情况，再进行匹配
         * 但是我的暴力递归会栈溢出，所以我们使用编程定律：一切递归都可以改成非递归，进行方法2的构建
         */

        public int mainMethod(int len , String str) {

            if(str.length() > len){

                return -1;
            }


            return which(len , str , 0 , new StringBuilder());
        }

        private int which(int len , String str , int index , StringBuilder sb){
            //index代表现在是第几个字符串
            //sb代表现在的字符串分析

            if(sb.toString().equals(str)){

                return index;
            }

            if(index == len){

                return -1;
            }

            for(int i =0;i<26;i++){

                sb.append((char)('a'+i));
                int result = which(len , str , ++index , sb);
                sb.setLength(sb.length()-1);

                if(result != -1){

                    return result;
                }
            }

            return -1;
        }
    }

//    public static class Solution2{
//          我放弃

//        public int mainMethod(int len , String str) {
//
//            StringBuilder sb = new StringBuilder(str);
//            int result = 0;
//
//
//        }
//
//        private int howMany(int len , String str , int index , StringBuilder sb){
//
//
//        }
//    }
}
