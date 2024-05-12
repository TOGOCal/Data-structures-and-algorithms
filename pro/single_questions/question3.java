import java.util.ArrayList;

public class question3 {

    /**
     * 题目描述：
     * 给定一个数组arr，代表每个人的体重，
     * 给定一个数limit，代表船的载重限制，
     * 一艘船可以载重一个或两个人
     * 如果要载重两个人现需满足：
     * 两个人的体重和是偶数且小于船的限制载重，求将所有人载满需要的最少船数
     * (不存在人体重超过船载重的情况
     */

    public static void main(String[] args) {


    }

    public static class Solution{
        /**
         * 题目分析：
         * 由于要求相加是偶数，所以这就代表奇数组合和偶数组合进行单独分析
         * 对奇数组或者偶数组的要求就都变成了只要求两人体重不超过船的载重
         */

        public int howMany(int[] weights , int limit){

            ArrayList<Integer> odd = new ArrayList<>();
            ArrayList<Integer> even = new ArrayList<>();

            for(int i = 0 ; i < weights.length ; i++){

                if(weights[i] % 2 == 0){
                    even.add(weights[i]);
                }else{

                    odd.add(weights[i]);
                }
            }

            int[] arr1 = new int[even.size()];
            int[] arr2 = new int[odd.size()];
            for(int i = 0 ; i < even.size() ; i++){
                arr1[i] = even.get(i);
            }
            for(int i = 0 ; i < odd.size() ; i++){
                arr2[i] = odd.get(i);
            }

            return method(arr1 , limit) + method(arr2 , limit);
        }

        public int method(int[] arr , int limit){

            quickSort(arr , 0 , arr.length - 1);

            int i;
            for(i = 0 ; i < arr.length ; i++){

                if(arr[i] > limit/2){
                    //找到超过限制的第一个点的索引
                    break;
                }
            }

            if(i==0){
                //当所有人的体重都超过了两倍，说明船数一定要与人数相同
                return arr.length;
            }

            int result =0;

            int p1 = i-1;//中间小于一半的值
            int p2 = i;//两个指针

            //通过两个指针向两边遍历
            int num1= 0;
            int num2 = 0;

            while(p1 >=0 && p2<arr.length){

                if(arr[p1] + arr[p2] > limit){
                    //如果此时指指向的两个数加上去大于了限制，说明只有可能向小的找才有可能找到适合这个大的
                    p1--;
                    num1++;
                }else{
                    //找到一个能够成功配对的
                    result++;
                    p1--;
                    p2++;
                }
            }

            while(p1 >=0){

                num1 += p1 - 0;
            }

            while(p2<arr.length){

                num2 += arr.length-1 - p2;
            }

            result += (num1/2 + num1%2 + num2);

            return result;
        }

        public void quickSort(int[] arr , int left, int right){

            if(left >= right){

                return;
            }

            int p1= left-1;
            int p2 = right+1;

            int rand = arr[ (int)(Math.random() * (p2-p1+1))+p1 ];

            for(int i = 0 ; i < p2 ; i++){

                if(arr[i] < rand){

                    swap(arr , i , p1);
                    p1++;
                }else if(arr[i] > rand){

                    swap(arr , i , p2);
                    p2--;
                    i--;
                }
            }

            quickSort(arr , left, p1);
            quickSort(arr , p2, right);
        }

        public void swap(int[] arr , int i , int j){

            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;

        }

    }


}
