import java.util.HashMap;
import java.util.HashSet;

public class 动态规划5 {

    /**
     * 题目描述：
     * 给定一个目标字符串str
     * 给定一个String类型的数组，代表你能够使用的所有贴纸
     * 所有的贴纸都可以剪开使用，求至少需要多少张贴纸才能够完成目标字符串的拼接
     *
     */

    public static void main(String[] args) {

    }

    public static class Solution1{
        //暴力递归思路：
        //使用一张贴纸后，看剩下的目标字符串使用的第一张贴纸，这样一直递归下去

        public static int mainMethod(String aim , String[] strings){

            HashSet<Character> set = new HashSet<>();

            char[] chars = aim.toCharArray();

            for(int i=0;i<chars.length;i++){

                if(!set.contains(chars[i])){

                    set.add(chars[i]);
                }
            }

            for(int i =0;i<strings.length;i++){

                char[] str = strings[i].toCharArray();
                for(int j=0;j<str.length;j++){

                    if(set.contains(str[j])){

                        set.remove(str[j]);
                    }
                }
            }

            if(!set.isEmpty()){

                return Integer.MAX_VALUE;//说明有些字符无论如何都达不到
            }

            return howMany(aim, strings);
        }

        public static int howMany(String restAim,String[] strings){

            if(restAim.length() == 0 || restAim.equals("")){

                return 0;
            }

            int min = Integer.MAX_VALUE;

            for(String str:strings){

                min = Math.min(min, howMany( delete(restAim,str), strings ));
            }

            return min +1;
        }

        public static String delete(String aim , String str){

            HashMap<Character, Integer> times = new HashMap<>();

            char[] chars = aim.toCharArray();

            for(int i=0;i<chars.length;i++){

                if(times.containsKey(chars[i])){

                    times.put(chars[i],times.get(chars[i])+1);
                }else{

                    times.put(chars[i],1);
                }
            }

            char[] crr = str.toCharArray();

            for(int i=0;i<crr.length;i++){

                if(times.containsKey(crr[i])){

                    times.put(crr[i],times.get(crr[i])-1);

                    if(times.get(crr[i])==0){

                        times.remove(crr[i]);
                    }
                }
            }

            StringBuilder sb = new StringBuilder();

            for(Character c:times.keySet()){

                int time = times.get(c);

                for(int j=0;j<time;j++){

                    sb.append(c);
                }
            }


            return sb.toString();
        }

    }

    public static class Solution2{
        //动态规划做法（记忆搜索

        HashMap<String , Integer> map ;

        Solution2(){

            map=new HashMap<>();
            map.put("",0);
        }
        //剩余字符串对应的最小

        public int mainMethod(String restAim , String[] strings){



            if(map.containsKey(restAim)){

                return map.get(restAim);
            }

            if(restAim.length() == 0){

                return 0;
            }


            int min = Integer.MAX_VALUE;
            for(String s:strings){

                String change = Solution1.delete(restAim , s);

                if(change.equals(restAim)){

                    continue;
                }//如果第一个贴纸再没有作用，那之后进递归就会死循环，所以要加限定条件

                min = Math.min( min, mainMethod( change , strings) );
            }

            map.put(restAim,min);
            return min;

        }


    }
}
