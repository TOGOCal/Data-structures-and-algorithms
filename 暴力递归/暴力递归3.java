import java.util.HashMap;
import java.util.Scanner;

public class 暴力递归3 {

    /**
     * 题目描述：
     * 规定 1 对应字符 a ， 2 对应字符b ，。。。。。。26对应字符z
     * 给定一串纯数字字符的字符串
     * 判断最多能解析出多少个小写字母字符串
     *
     */

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        String str = s.nextLine();

        System.out.println(howMany(str));

        s.close();
    }

    public static int howMany(String str){

        char[] chars = str.toCharArray();

        int res = 0;

        res = mathod(chars , 0);

        return res;

    }

    public static int mathod(char[] chars,int index){

        if(index >= chars.length){

            return 1;
        }//当结束时代表是一种方案

        if(chars[index] == '0'){

            return 0;//0是没有办法做转化的，此时需要换一种跳跃的方法
        }

        StringBuilder sb = new StringBuilder();
        sb.append(chars[index]);

        int res = 0;

        if(index != chars.length-1){
            sb.append(chars[index+1]);

            if(Integer.valueOf(sb.toString()) <= 26){

                res += mathod(chars,index+2);
            }
        }

        res += mathod(chars,index+1);
        return res;
    }
}
