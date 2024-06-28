import java.util.Scanner;

public class question8 {

    /**
     * 问题背景：
     * 给定两个已经排好序的数组arr1和arr2
     * 从其中各取一个数字，求大小为前k的数是哪几个
     * 返回前k个数的数组
     */

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("请输入数组arr1的长度");
        int[] arr1 = new int[s.nextInt()];

        System.out.println("请输入数组arr2的长度");
        int[] arr2 = new int[s.nextInt()];

        System.out.println("请输入数组arr1的元素");
        for(int i = 0; i < arr1.length; i++){

            arr1[i] = s.nextInt();
        }

        System.out.println("请输入数组arr2的元素");
        for(int i = 0; i < arr2.length; i++){

            arr2[i] = s.nextInt();
        }

        int k = s.nextInt();

        int[] res = new Solution().maxValue(arr1 , arr2 , k);

        for (int re : res) {

            System.out.print(re + " ");
        }

        System.out.println();

        s.close();
    }

    static class Solution{

        /**
         * 方法：
         * 构建一个arr1组成行，arr2组成列的二维数组，
         * 由于最大的数是由arr1和arr2的最大的数组成的，（最右下角
         * 而较小的数字始终在其其左侧或右侧
         * 而在下一个数就是前几次比较剩下的和新加入的两个
         * 每次从大根堆中弹出最大的就行了
         *
         * 验证大根堆最大只需要k：
         * 每次弹出一个，每次最多加入两个，因此每次相当于最多加入1各，最多加入k次，因此大根堆大小为k就行了
         */
        public int[] maxValue(int[] arr1, int[] arr2 , int k){

            MyHeap myHeap = new MyHeap(k);

            boolean[][] havePush = new boolean[arr1.length][arr2.length];//已经加入过的

            int[] res = new int[k];
            int point1 = arr1.length-1;
            int point2 = arr2.length-1;

            myHeap.push(point1 , point2 ,arr1[point1] + arr2[point2]);
            havePush[point1][point2] = true;

            for(int i = 0; i < k; i++){

                Node node = myHeap.pop();
                res[i] = node.value;

                point1 = node.point1;
                point2 = node.point2;
                //加入这个的左以及上

                if(point1 >1 && !havePush[point1-1][point2]){

                    myHeap.push(point1-1 , point2 ,arr1[point1-1] + arr2[point2]);
                    havePush[point1-1][point2] = true;
                }

                if(point2 >1 && !havePush[point1][point2-1]){

                    myHeap.push(point1 , point2-1 ,arr1[point1] + arr2[point2-1]);
                    havePush[point1][point2-1] = true;
                }
            }

            return res;

        }


        //以下为堆的构建
        //堆是大根堆
        //左：index*2+1 右：index*2+2
        public class Node{
            //位置及其对应的值
            int point1;
            int point2;

            int value;

            Node(int point1, int point2, int value){

                this.point1 = point1;
                this.point2 = point2;

                this.value = value;
            }
        }

        public class MyHeap{

            Node[] heap;
            int index;//index始终是无效位置

            MyHeap(int size){

                //通过数组构建堆
                heap = new Node[size];
                index = 0;
            }

            public void push(int point1 , int point2 ,int num){

                Node newNode = new Node(point1,point2,num);
                heap[index] = newNode;;
                checkFromButtonToTop(index);
                index ++;//加在最后的，因此需要向上检查这个位置是否合理
            }

            public Node pop(){

                Node res = heap[0];

                //将最后一个元素放到第一个位置
                heap[0] = heap[--index];

                //从上往下检查这个数是否合格
                checkFromTopToButton(0);

                return res;
            }

            private void checkFromTopToButton(int position){

                int l = position*2+1;
                if(l >= index){

                    return;
                }

                int r = position*2+2;

                int biggerIndex = (r >= index ? l : (heap[l].value > heap[r].value ? l : r));

                if(heap[position].value < heap[biggerIndex].value){

                    swap(heap,position,biggerIndex);
                    checkFromTopToButton(biggerIndex);//递归检查这个位置是否合格
                }
            }

            //从下往上检查这个数是否合格
            private void checkFromButtonToTop(int index){

                int pre = index;

                pre -= ( pre%2 == 0 ? 2 : 1);//父节点
                pre /=2;//找到父节点坐标

                while(pre >= 0){

                    if(heap[pre].value < heap[index].value){

                        swap(heap,pre,index);//交换这两个数
                        index = pre;
                        pre -= ( pre%2 == 0 ? 2 : 1);//父节点
                        pre /=2;//找到父节点坐标
                    }else{

                        //说明找到正确的地方了
                        break;
                    }
                }
            }

            private void swap(Node[] arr , int i, int j){

                Node temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }


        }
    }


}
