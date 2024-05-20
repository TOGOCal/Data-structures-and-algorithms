public class 窗口结构更新 {

    /**
     * 什么是窗口：
     * 在数组环境下，给定一个活动左边界L
     * 给定活动右边界R，始终保证L<R
     * 窗口可以滑动（具体表现为L边界和R边界的运动
     * 如何更新窗口结构才能保证随时都能拿到窗口范围内的最大值
     *
     * 方法简述：
     * 通过双端队列构建窗口
     * 当R边界向右华东时，保证新加入的数一直挂在比他大的术后面（不能等于
     * 如果不满足，则一直从尾部弹出，直到弹到空
     * 当L向左滑动时，只需要确定将要出窗口的数是不是最大值就行了
     * 通过这样的方式就能够保证队列中第一项始终是窗口中的最大值了
     *
     * 小细节：
     * 为了使得窗口储存更多的信息，一般储存的是数组的下标而不是值
     * 窗口只能由左向右滑动，维持窗口内部最大值和最小值快速更新的结构
     *
     */

    public static void main(String[] args) {


    }

    public static class Window{

        int L;
        int R;
        Node head;
        Node end;

        Window() {
            L = -1;
            R = -1;

            head = null;
            end = null;
        }

        public int toGetBiggest(int[] arr){

            return arr[head.index];
        }

        public void rightAdd(int arr[], int index){
            //当前index进入窗口（从右

            if(index>=arr.length||index<0){

                return;
            }

            R = index;

            while(end != null && arr[end.index] <= arr[index]){
                rightPop();
            }

            if(head==null){

                head = new Node(index);
                end = head;
            }else{

                Node newNode  = new Node(index);

                end.right = newNode;
                newNode.left = end;

                end = newNode;
            }
        }

        public void leftAdd(int[] arr, int index){
            //窗口左边向R靠近

            if(index>=arr.length||index<0){

                return;
            }

            L = index;

            if(index == head.index){

                leftPop();
            }

        }

        private void leftPop(){
            //左边弹出一个

            if(head==end){

                head = null;
                end = null;
            }else{

                Node node = head.right;
                node.left = null;
                head= node;
            }

        }

        private void rightPop(){
            //向右弹出一个（肯定这个右边的存在啊

            if(head == end){
                //只有一个元素
                head = null;
                end = null;
            }else{

                Node node = end.left;
                node.right = null;
                end = node;
            }

        }

        private class Node{

            int index;
            Node left;
            Node right;

            Node(){
                index = -1;
            }

            Node(int index){

                this.index = index;
                left = null;
                right = null;
            }
        }//通过双向链表构建双向队列
    }
}
