import java.util.ArrayList;
import java.util.HashMap;

public class question1 {

    /**
     * 题目背景：
     * 假设给定一大文件，包含40亿个int类型的数据
     * 现在要求写一个办法，判断这个大文件中出现最多的数是什么，出现了几次
     *
     */

    public static void main(String[] args) {

    }

    public static class Solution1{

        /**
         * 可能出现的问题：
         * 40亿个Integer-Integer类型的hashMap预估会造成32G的空间，可能会造成表直接爆掉
         * int为8byte Integer 可能更大，且哈希表还有一些查询冗余空间
         */

        public Information method(int[] arr){

            HashMap<Integer,Integer> map = new HashMap<>();

            int max = Integer.MIN_VALUE;
            int value = 0;
            for(int i:arr){

                map.put(i,map.getOrDefault(i,0)+1);

                if(map.get(i)>max){

                    value = i;
                    max = map.get(i);
                }
            }

            return new Information(value,max);
        }
    }

    public static class Solution2{

        public Information method(int[] arr){

            /**
             * 方法简述：
             * 创建m个文件
             * 遍历所有的数，生成hashcode，将其模固定值m ， 生成值
             *
             * 根据a放入第a个文件
             * 再分别使用普通方法得到每个文件中的，再进行比较
             */

            ArrayList<Integer>[] list = new ArrayList[100];//没说一定这样处理，只是用这种方法模拟生成小文件

            for(int i=0;i<100;i++){

                list[i] = new ArrayList<>();
            }

            for(int i:arr){

                int hashcode = Integer.hashCode(i);

                list[ hashcode%100 ].add(i);
            }

            Information result = new Information(0,Integer.MIN_VALUE);

            for(ArrayList<Integer> l : list){

                Information ifo = function(l);

                if(ifo.times>result.times){

                    result = ifo;
                }
            }

            return result;
        }

        private Information function(ArrayList<Integer> list){

            HashMap<Integer,Integer> map = new HashMap<>();

            int max = Integer.MIN_VALUE;
            int value = 0;
            for(Integer i:list){

                map.put(i,map.getOrDefault(i,0)+1);

                if(map.get(i)>max){

                    max = map.get(i);
                    value = i;
                }
            }

            return new Information(value,max);
        }
    }

    public static class Information{

        int value;
        int times;

        Information(int value, int times) {
            this.value = value;
            this.times = times;
        }
    }
}
