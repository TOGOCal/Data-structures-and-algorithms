import java.util.HashMap;

public class question1 {
    /**
     * 题目描述：
     * 给定一个信息模式 [x , y , z]
     * x只会有三种状态0 1 2 ，0代表这个石头还未上色 ，后面的y和z有意义，代表将其染成红色的代价y，染成蓝色的代价z
     * 1代表这个石头是红色的，不能改变颜色，后面两个数无意义
     * 2代表蓝色，同理
     *
     * 现要求将所有石头染色，且蓝色和红色一样多
     * 做不到就返回-1
     * 做得到就放回最小代价
     *
     */

    public static void main(String[] args) {

    }

    public static class Stone{

        int nowColor;
        int valueRed;
        int valueBlue;

        public Stone(int nowColor, int valueRed, int valueBlue) {

            this.nowColor = nowColor;
            this.valueRed = valueRed;
            this.valueBlue = valueBlue;
        }
    }


    public static class OfficialSolution{
        /**
         *  官方解题方法：
         *  先将所有石头染成红色，再选出变成蓝色代价最小的几个石头进行变化
         */

        public void swap(Stone[] stones , int a, int b) {

            Stone temp = stones[a];
            stones[a] = stones[b];
            stones[b] = temp;
        }

        public int mainMethod(Stone[] stones) {

            if(stones == null || stones.length == 0 || stones.length %2==1){

                return -1;
            }

            int redNumber = 0;
            int blueNumber = 0;

            int result = 0;

            int size = stones.length;

            for(int i = 0; i < stones.length; i++){

                if(stones[i].nowColor == 1){

                    redNumber++;
                    size--;
                    swap(stones , i, size);
                }else if(stones[i].nowColor == 2){

                    blueNumber++;
                    size--;
                    swap(stones , i , size);
                }else{

                    result +=stones[i].valueRed;//先将所有染成红色
                }
            }

            if(redNumber > stones.length/2){

                return -1;
            }else if(blueNumber > stones.length/2){

                return -1;
            }

            quickSort(stones, 0, size);//只用对有效范围（白色进行排序

            for(int i = 0; i < stones.length/2 - blueNumber; i++){
                //要将多少个石头染成蓝色
                result += stones[i].valueBlue - stones[i].valueRed;
            }

            return result;
        }

        public void quickSort(Stone[] stones, int left, int right) {

            if(left >= right){

                return;
            }

            Stone randStone = stones[(int)(Math.random()*(right-left+1))];

            int rand = randStone.valueBlue - randStone.valueBlue;

            int p1 = left-1;
            int p2 = right+1;

            for(int i = left; i < p2; i++){

                if(stones[i].valueBlue - stones[i].valueRed < rand){

                    swap(stones, p1+1, i);
                    p1++;
                }else if(stones[i].valueBlue - stones[i].valueRed > rand){

                    swap(stones, p2-1, i);
                    p2--;
                    i--;
                }
            }

            quickSort(stones, left, p1);
            quickSort(stones, p2, right);
        }

    }

    //======================================================================================

    public static class Solution{

        public void swap(Stone[] stones , int a, int b) {

            Stone temp = stones[a];
            stones[a] = stones[b];
            stones[b] = temp;
        }

        public int mainMethod(Stone[] stones){

            if(stones == null || stones.length == 0 || stones.length %2==1){

                return -1;
            }

            int redNumber = 0;
            int blueNumber = 0;

            int size = stones.length;

            for(int i = 0; i < size; i++){

                if(stones[i].nowColor == 1){

                    redNumber++;
                    size--;
                    swap(stones , i, size);
                }else if(stones[i].nowColor == 2){

                    blueNumber++;
                    size--;
                    swap(stones , i , size);
                }
            }

            if(redNumber > stones.length/2){

                return -1;
            }else if(blueNumber > stones.length/2){

                return -1;
            }


            return method(stones , 0 , redNumber - blueNumber , size).value;

        }

        public Information method(Stone[] stones , int index , int lastValue ,int size){
            //index代表此时正在对哪个石头进行分析
            //lastValue代表此时红色和蓝色的石头相差几个(只有相差为0的情况才作数
            //具体指红色 - 蓝色 ， 每次多一个红色的石头++，多一个蓝色石头--

            Information result = new Information();

            if(index == size){

                if(lastValue == 0){

                    result.suitable = true;
                }
                return result;
            }


            Information ifo1 = method(stones , index+1 , lastValue+1 , size);

            Information ifo2 = method(stones , index+1 , lastValue-1 , size);


            if(ifo1.suitable || ifo2.suitable){
                //代表至少有一个合适的
                ifo1.value += stones[index].valueRed;//这个石头选择红色的时候
                ifo2.value += stones[index].valueBlue;

                result.suitable = true;
                if(ifo1.suitable && ifo2.suitable){

                    result.value = Math.min(ifo1.value, ifo2.value);
                }else if(ifo1.suitable){

                    result.value = ifo1.value;
                }else if(ifo2.suitable){
                    result.value = ifo2.value;
                }

            }else {
                //这里则代表一个合适的都没有
                result.suitable = false;
            }

            return result;

        }

        public class Information{

            int value;
            boolean suitable;

            public Information(){
                this.value = 0;
                this.suitable = false;
            }

            public Information(int value, boolean suitable){
                this.value = value;
                this.suitable = suitable;
            }
        }

    }
    //=======================================================


    public static class Solution2{

        HashMap<String , Information> map;

        Solution2(){

            map = new HashMap<>();
        }

        public void swap(Stone[] stones , int a, int b) {

            Stone temp = stones[a];
            stones[a] = stones[b];
            stones[b] = temp;
        }

        public int mainMethod(Stone[] stones){

            if(stones == null || stones.length == 0 || stones.length %2==1){

                return -1;
            }

            int redNumber = 0;
            int blueNumber = 0;

            int size = stones.length;

            for(int i = 0; i < size; i++){

                if(stones[i].nowColor == 1){

                    redNumber++;
                    size--;
                    swap(stones , i, size);
                }else if(stones[i].nowColor == 2){

                    blueNumber++;
                    size--;
                    swap(stones , i , size);
                }
            }

            if(redNumber > stones.length/2){

                return -1;
            }else if(blueNumber > stones.length/2){

                return -1;
            }


            return method(stones , 0 , redNumber - blueNumber , size).value;

        }

        public Information method(Stone[] stones , int index , int lastValue ,int size){
            //index代表此时正在对哪个石头进行分析
            //lastValue代表此时红色和蓝色的石头相差几个(只有相差为0的情况才作数
            //具体指红色 - 蓝色 ， 每次多一个红色的石头++，多一个蓝色石头--

            if(map.containsKey(index + "|" + lastValue)){

                return map.get(index + "|" + lastValue);
            }

            Information result = new Information();

            if(index == size){

                if(lastValue == 0){

                    result.suitable = true;
                }
                return result;
            }


            Information ifo1 = method(stones , index+1 , lastValue+1 , size);

            Information ifo2 = method(stones , index+1 , lastValue-1 , size);


            if(ifo1.suitable || ifo2.suitable){
                //代表至少有一个合适的
                ifo1.value += stones[index].valueRed;//这个石头选择红色的时候
                ifo2.value += stones[index].valueBlue;

                result.suitable = true;
                if(ifo1.suitable && ifo2.suitable){

                    result.value = Math.min(ifo1.value, ifo2.value);
                }else if(ifo1.suitable){

                    result.value = ifo1.value;
                }else if(ifo2.suitable){
                    result.value = ifo2.value;
                }

            }else {
                //这里则代表一个合适的都没有
                result.suitable = false;
            }

            map.put(index + "|" + lastValue, result);

            return result;

        }

        public class Information{

            int value;
            boolean suitable;

            public Information(){
                this.value = 0;
                this.suitable = false;
            }

            public Information(int value, boolean suitable){
                this.value = value;
                this.suitable = suitable;
            }
        }

    }
}
