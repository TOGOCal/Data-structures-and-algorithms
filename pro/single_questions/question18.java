import java.util.Scanner;
import java.util.Stack;

public class question18 {

    /**
     * 给定一种字符串压缩方式
     * 限定被压缩的字符串只包含小写字母
     *
     * 压缩方式如下：
     * 3{a}2{bc} => aaabcbc
     * 3{a2{c}} => accaccacc
     */


    public static void main(String[] args) {
        Scanner s= new Scanner(System.in);

        Solution solution = new Solution();

        System.out.println(solution.mainMethod(s.nextLine()));


        s.close();
    }


    static class Solution{
        //使用递归
        //特殊的递归结构：当遇到 } 或 遍历到结尾后返回

        static class Information{

            int end;//结束位置
            String str;//当前生成的字符串


            Information(int end, String str){

                this.end = end;
                this.str = str;
            }

        }

        public String mainMethod(String s ){


            return method(s.toCharArray(), 0).str;
        }


        public Information method(char[] s, int start){

            StringBuilder res = new StringBuilder();

            int times = 0;

            while(start < s.length && s[start] != '}'){

                if(s[start] == '{'){

                    //递归调用
                    Information info = method(s, start + 1);

                    res.append(buildString(info.str, times));

                    times = 0;

                    start = info.end + 1;
                }else if(s[start] >= 'a' && s[start] <= 'z'){

                    //是字符
                    res.append(s[start]);
                    start++;
                }else if(s[start] >= '0' && s[start] <= '9'){

                    //是数字
                    //为什么*10，像 13这样的数量，便利的时times先变成1 ，后面遍历到了3，就将1的权重提高一位就能得到13了
                    times = times * 10 + (s[start] - '0');
                    start++;
                }


            }

            return new Information(start, res.toString());
        }


        public String buildString(String s , int times){

            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < times; i++){

                sb.append(s);
            }

            return sb.toString();
        }


    }
}
