import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class TimSort<T> {

    private static final int MIN_MERGE = 32;

    private T[] array;
    private Comparator<T> comparator;



    //执行排序
    public void sort(T[] array, Comparator<T> comparator){

        //判断参数是否符合要求
        if(array == null || comparator == null || array.length == 0){

            return;
        }

        //判断长度是否小于MIN_MERGE，小于就执行二分插入排序，大于才执行TimSort的步骤
        if(array.length < MIN_MERGE){

            //二分插入排序
            binarySort(array , comparator , 0 , array.length-1);
            return;
        }


        this.array = array;
        this.comparator = comparator;


        //计算minRun
        int minRun = minRunLength(array.length);

        //初始化栈
        MyStack stack = new MyStack();

        //升序运行 如果得到的子序列长度小于minRun，则通过二分插入将剩下的部分补充进来
        for(int i = 0 ; i < array.length ;){

            Node run = getRunInterval(array , comparator , i);//得到一个区间

            //系啊一个位置
            //int index = run.endPlace + 1;

            while (run.getSize() < minRun){
                //需要使用二分执行扩容操作

                if(run.endPlace >= array.length-1){

                    break;//后面没有元素了
                }

                //扩容
                T sentry = array[run.endPlace + 1];

                //先前寻找正确的位置
                int rightIndex = binarySearchSuitablePlace(array , comparator , run.beginPlace , run.endPlace , array[run.endPlace+1]);


                //将后面的元素后移一位
                for(int j = run.endPlace ; j >= rightIndex ; j--){

                    array[j+1] = array[j];//向前移动
                }

                array[rightIndex] = sentry;//将这个数填到正确的位置上

                run.endPlace++;//更新endPlace
                //index++;//更新index
            }

            i = run.endPlace + 1;//更新i

            //将得到的子序列压栈
            stack.push(run);

            //检查栈中的元素是否满足要求，根据是否满足执行合并
            if(!stack.checkSuitable()){
                //执行合并操作
                stack.merge();
            }

        }

        while(stack.size > 1){

            Node X = stack.pop();
            Node Y = stack.pop();

            stack.doMerge(X , Y);

            Node newNode = new Node(Math.min(X.beginPlace , Y.beginPlace) , Math.max(X.endPlace , Y.endPlace));

            stack.push(newNode);
        }

    }


    /**
     * 得到一个Run区间
     * @param array 数组
     * @param comparator 比较器
     * @param beginPlace 开始寻找的地方
     * @return 得到的区间
     */
    private Node getRunInterval(T[] array, Comparator<T> comparator , int beginPlace){

        Boolean isUp = null;

        int endPlace = array.length-1;

        for(int i = beginPlace ; i < array.length-1 ; i++){

            int compareRes = comparator.compare(array[i] , array[i+1]);
            //前面的比后面的大
            if(compareRes > 0){

                //之前在升序
                if(isUp != null && isUp){

                    endPlace = i;
                    break;
                }

                isUp = false;
            }else if(compareRes < 0){

                if(isUp != null && !isUp){

                    endPlace = i;
                    break;
                }

                isUp = true;
            }//相等则不执行操作
        }

        //如果倒叙则翻转
        if(isUp != null && !isUp){

            turnOver(array , beginPlace , endPlace);
        }

        //得到endPlace
        return new Node(beginPlace , endPlace);

    }


    private void turnOver(T[] array , int beginPlace , int endPlace){

        for(int i = beginPlace , j = endPlace ; i < j ; i++ , j--){

            T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }


    private class MyStack{

        List<Node> stack;
        int size;

        MyStack(){

            stack = new ArrayList<>();
            size = 0;
        }

        void push(Node node){

            stack.add(node);
            size++;
        }

        Node pop(){

            Node node = stack.get(size-1);
            stack.remove(size-1);
            size--;
            return node;
        }

        /**
         * Timsort 为了执行平衡合并（让合并的 run 大小尽可能相同），
         * 制定了一个合并规则，对于在栈顶的三个run，分别用X、Y 和 Z 表示他们的长度，其中 X 在栈顶，它们必须始终维持一下的两个规则：
         *
         * Z > Y + X
         * Y > X
         * 符合规则则不需要执行合并
         */
        public boolean checkSuitable(){

            //不符合合并规则，不需要执行合并
            if(size < 3){

                if(size == 2){

                    return stack.get(0).getSize() > stack.get(1).getSize();
                }

                return true;
            }

            int x = stack.get(size-1).getSize();
            int y = stack.get(size-2).getSize();
            int z = stack.get(size-3).getSize();

            return (z > y + x) & (y > x);
        }


        public void merge(){

            //如果是2这种情况
            if(size == 2){

                Node X = pop();
                Node Y = pop();

                doMerge(X, Y);

                Node newNode = new Node(Math.min(X.beginPlace, Y.beginPlace), Math.max(X.endPlace, Y.endPlace));

                push(newNode);

                return;
            }


//            Node X = stack.get(size-1);
//            Node Y = stack.get(size-2);
//            Node Z = stack.get(size-3);
            Node X = pop();
            Node Y = pop();
            Node Z = pop();


            //一旦有其中的一个条件不被满足，则将 Y 与 X 或 Z 中的较小者合并生成新的 run，
            // 并再次检查栈顶是否仍然满足条件。如果不满足则会继续进行合并，
            // 直至栈顶的三个元素都满足这两个条件，如果只剩两个run，则满足 Y > X 即可。

            if(X.getSize() < Z.getSize()){

                doMerge(X , Y);

                Node newNode = new Node(Math.min(X.beginPlace, Y.beginPlace), Math.max(X.endPlace, Y.endPlace));

                push(newNode);
                push(Z);

            }else{

                doMerge(Z , Y);


                Node newNode = new Node(Math.min(Z.beginPlace, Y.beginPlace), Math.max(Z.endPlace, Y.endPlace));

                push(X);
                push(newNode);
            }
        }


        //内存优化，通过确定需要进行移动的元素
        private void doMerge_MemoryBetter(Node X , Node Y){

            Node begin = X.beginPlace < Y.beginPlace ? X : Y;

            Node end = X.endPlace < Y.endPlace ? Y : X;

            //确认开始位置（在后面数组的第一个元素降压合并的前面区域可以不动
            int beginIndex = binarySearchSuitablePlace(array , comparator ,
                    begin.beginPlace , begin.endPlace , array[end.beginPlace]);

            //找到前面的串的最后一个字符应该在哪个位置
            int endIndex = binarySearchSuitablePlace(array , comparator ,
                    begin.beginPlace , begin.endPlace , array[begin.endPlace]);

            int index = beginIndex;

            @SuppressWarnings({"unchecked"})
            T[] newArray = (T[])java.lang.reflect.Array.newInstance
                    (array.getClass().getComponentType(), endIndex - beginIndex + 1);


            int index1 = beginIndex;
            int index2 = end.beginPlace;

            while(index1 <= endIndex && index2 <= endIndex){

                if(comparator.compare(array[index1] , array[index2]) <= 0){

                    newArray[index++] = array[index1++];
                }else{

                    newArray[index++] = array[index2++];
                }
            }

            while(index1 <= endIndex){

                newArray[index++] = array[index1++];
            }

            while(index2 <= endIndex){

                newArray[index++] = array[index2++];
            }


        }

        private void doMerge(Node X ,Node Y){

            int index1 = X.beginPlace;
            int index2 = Y.beginPlace;

            //Node begin = index1 < index2 ? X : Y;


            @SuppressWarnings({"unchecked"})
            T[] newArray = (T[])java.lang.reflect.Array.newInstance
                    (array.getClass().getComponentType(), X.getSize() + Y.getSize());

            int index =0;
            while(index1 <= X.endPlace && index2 <= Y.endPlace){

                if(comparator.compare(array[index1] , array[index2]) <= 0){

                    newArray[index++] = array[index1++];
                }else{

                    newArray[index++] = array[index2++];
                }

            }

            while(index1 <= X.endPlace){

                newArray[index++] = array[index1++];
            }

            while(index2 <= Y.endPlace){

                newArray[index++] = array[index2++];
            }


            index = Math.min(X.beginPlace , Y.beginPlace);
            for (T t : newArray) {

                array[index++] = t;
            }


        }


    }

    //某个Run区间
    private class Node{

        int beginPlace;
        int endPlace;

        Node next;
        Node pre;

        Node(int beginPlace , int endPlace){

            this.beginPlace = beginPlace;
            this.endPlace = endPlace;

            next = null;
            pre = null;
        }

        public int getSize(){

            return endPlace - beginPlace +1;
        }

    }


    /**
     * 对某个范围的数组执行二分插入排序
     * @param array 将要排序的数组
     * @param comparator 比较器
     * @param leftLimit 左限制
     * @param rightLimit 右限制
     */
    private void binarySort(T[] array , Comparator<T> comparator , int leftLimit , int rightLimit){

        //第一个位置的数默认是排好的
        for(int i = leftLimit+1 ; i <= rightLimit ; i++){
            T sentry = array[i];

            //先前寻找正确的位置
            int index = binarySearchSuitablePlace(array , comparator , leftLimit , i-1 , sentry);


            //将后面的元素后移一位
            for(int j = i-1 ; j >= index ; j--){

                array[j+1] = array[j];//向前移动
            }

            array[index] = sentry;//将这个数填到正确的位置上
        }
    }


    /**
     * 通过二分查找找到合适的位置
     * @param array 要查找的数组
     * @param comparator 比较器
     * @param leftLimit 查找左范围
     * @param rightLimit 查找右范围
     * @param found 要查找的元素
     */
    private int binarySearchSuitablePlace(T[] array , Comparator<T> comparator,
                              int leftLimit , int rightLimit , T found){

        assert leftLimit <= rightLimit;

        int left = leftLimit;
        int right = rightLimit;

        while(left <= right){

            int mid = (left + right) >>> 1;

            if(comparator.compare(array[mid] , found) > 0){

                right = mid -1;

            }else{
                left = mid + 1;
            }

        }

        return right+1;
    }




    /**
     * 通过实验可以知道，当 分块数量 略小于2的幂次方或者等于2的幂次方的时候，可以做到最优
     * 因此我们需要京可能保证 分块数量略小于2的幂次方或者等于
     * 如何实现：
     * 当指向n>>>1的时候，其实就是在对n进行除2操作，如果n是一个2的幂次方，则只需要保证n在16-32之间的16就可以了
     * 如果n不是2的幂次方（那么r就会被置位为1），
     * 假经过了若干次位移，sortedArrayLength/16 = minRun
     * 那么分块数量 sortedArrayLength/minRun就会大于16，这明显不能符合我们的要求
     * 因此，当n不是2的幂次的时候，将minRun+1，就可以做到将分块数量降到16以下，做到了略小于2的幂次方这个要求
     *
     * @param sortedArrayLength 要排序的数组长度
     * @return 计算出的minRun长度
     */
    private static int minRunLength(int sortedArrayLength) {

        assert sortedArrayLength >= 0;

        int r = 0;      // 如果低位任何一位是1，就会变成1
        while (sortedArrayLength >= MIN_MERGE) {
            r |= (sortedArrayLength & 1);
            sortedArrayLength >>= 1;
        }
        //如果sortedArrayLength是2的幂次方，则计算出的结果也是2的幂次方，r为0，满足在分块数量等于2的幂次方这个要求
        //如果sortedArrayLength不是2的幂次方，则计算出的结果也是2的幂次方，r为1，minRun+1，分块数量略小于2的幂次方这个要求
        return sortedArrayLength + r;
    }



    //测试
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int n =s.nextInt();

        Integer[] array = new Integer[n];

        for(int i = 0 ; i < n ; i++){

            array[i] = s.nextInt();
        }

        TimSort<Integer> timSort = new TimSort<>();
        timSort.sort(array , Comparator.comparingInt(o -> o));

        for(int i = 0 ; i < n ; i++){

            System.out.print(array[i] + " ");
        }

    }

}
