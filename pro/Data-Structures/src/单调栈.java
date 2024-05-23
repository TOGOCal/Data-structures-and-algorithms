import java.util.ArrayList;
import java.util.Scanner;

public class 单调栈 {

    /**
     * 问题背景：给定一个无序数组，如何使用时间复杂度为O(N)
     * 的算法得到每一个位置左边第一个比这个数大的数和右边第一个比这个数大的数
     *
     * 使用单调栈：
     * 本质上是个栈，但在任何时候他都保证从底到顶所有元素都是单调的（单增或单减
     * 要解决这个问题，则使用单调栈，如果新加入的位置比上一个加入的位置小，则可以加入
     * 否则，执行弹出程序直到满足上一条条件
     *
     * 在弹出过程中，每一个弹出的元素的左边第一个比它大的元素就是栈中下一个元素
     * 而右边以一个比他大的元素就是现在在外面挂着，等待着放进去的元素
     *
     */

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int n = s.nextInt();
        int[] arr= new int[n];

        for(int i =0;i< n;i++){
            arr[i] = s.nextInt();
        }

        Information[] ifos = mainMethod(arr);

        for(Information ifo:ifos){

            System.out.println(ifo.toString());
        }

        s.close();
    }

    public static Information[] mainMethod(int[] arr){//以上方法可以处理无重复的情况

        ArrayList<Integer> list = new ArrayList<>();
        Information[] result = new Information[arr.length];

        for(int i =0;i<result.length;i++){

            result[i] = new Information();
        }

        for(int i=0;i<arr.length;i++){

            if(isSuitable(list, i ,arr)){

                list.add(i);
            }else{

                while(!isSuitable(list , i ,arr)){

                    int index = list.getLast();
                    list.removeLast();
                    if(!list.isEmpty()){

                        result[index].left = list.getLast();
                    }

                    result[index].right = i;//右边大的是这个元素，左边是栈中下一个

                }
                list.add(i);
            }

        }

        while(!list.isEmpty()){

            int index = list.getLast();
            list.removeLast();

            if(list.isEmpty()){

                break;
            }

            result[index].left = list.getLast();
        }

        return result;
    }

    public static boolean isSuitable(ArrayList<Integer> list , int now , int[] arr){

        if(list.isEmpty()){

            //里面什么都没有代表可以加入
            return true;
        }

        if(arr[list.getLast()] > arr[now]){

            //上一个数比这个数大，满足条件
            return true;
        }

        return false;
    }

    public static class Information{

        int left;
        int right;

        Information(){
            left = -1;
            right = -1;
        }

        @Override
        public String toString() {
            return "["+left+" "+right+"]";
        }
    }
}
