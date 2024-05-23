import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 跳表 {

    /**
     * 调表同之前学习的二叉树相关进阶结构类似
     * 也能实现较低较低代价的查询操作
     *
     * 具体构建流程：
     * 调表中每个节点可以有多个指针指向下一个节点
     *  具体怎么决定有多少个指针：投骰子：每个节点至少有一个节点，剩下的如果骰子骰出1就再增加一个节点，直到投出0
     * 每个跳表初始化的时候初始化出一个头节点
     * 头节点初始有一个指针，但是头节点可以进行指针扩容操作，使得头节点的指针数量与指针最多的节点指针数量相同
     *  如何做到：通过投骰子的方式决定出一个新节点的指针数量之后，如果小了就执行扩容
     *
     * 增加操作：
     *  通过上述方式制造新节点之后，
     *  遍历起始位置：头节点的最高处指针（如果扩容则扩容后的最高，
     *  顺着这个初始位置向后，直到找到下一个刚好大于这个节点的节点（或者走到空
     *  但是取得的位置是刚好小于等于这个值的点（刚好大于或空的前一个点
     *  如果不出意外的话，此时遍历到是节点的最高处指针
     *  持续上述过程，直到找到与这个新节点等高的指针，进行插入连接操作
     *  
     * 删除操作：
     *  直接删并进行链表重连就行了
     *
     * 查找：
     *  从头节点最高位置开始执行类似插入的操作的查找
     *  如果向下到了最低的节点向后遍历 还是找不到 该值说明不存在
     *  原理：所有节点一定在最低的一个链表中存在
     *
     */

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        List<Integer> list = new ArrayList<>();

        System.out.println("输入初始化大小：");
        int n = s.nextInt();

        System.out.println("输入初始化元素：");
        for (int i = 0; i < n; i++){

            list.add(s.nextInt());
        }
        s.nextLine();

        SkipList skipList = new SkipList(list);

        char key = 0;

        while(key != '0'){

            System.out.println("输入操作：");
            System.out.println("1.插入 2.删除 3.查找 4.遍历 5.检查遍历 0.退出");

            key = s.nextLine().charAt(0);

            switch (key){

                case '1':

                    System.out.println("输入要插入的值：");
                    skipList.insert(s.nextInt());
                    s.nextLine();
                    break;

                case '2':

                    System.out.println("输入要删除的值：");
                    if(!skipList.delete(s.nextInt())){

                        System.out.println("删除失败");
                    }
                    s.nextLine();
                    break;

                case '3':

                    System.out.println("输入要查找的值：");
                    if(skipList.exist(s.nextInt())!=null){

                        System.out.println("存在");
                    }else{

                        System.out.println("不存在");
                    }
                    s.nextLine();
                    break;

                case '4':
                    skipList.printTraversal();
                    break;

                case '5':
                    skipList.checkTraversal();
                    break;

                default:
                    key = '0';
            }
        }

        s.close();

    }

    public static class SkipList{

        Node head;

        SkipList(){

            head = new Node(Integer.MIN_VALUE,1);
        }

        SkipList(List<Integer> list){

            head = new Node(Integer.MIN_VALUE,1);

            for(int i:list){

                insert(i);
            }
        }

        public void insert(int value){

            int size = 1;

            while(Math.random()<0.5){

                size++;
            }//50%的概率++

            Node newNode = new Node(value,size);
            head.largeTheHead(size);

            Node.Pointer nowPoint = head.stackTop;

            int height = nowPoint.getTheNode().size;
            Node.Pointer connectPoint = newNode.stackTop;//需要被连接的表

            while(connectPoint!=null){

                while(nowPoint.next!=null &&nowPoint.next.getTheNode().value <= value){

                    nowPoint = nowPoint.next;
                }//找到前一个要连的节点

                if(nowPoint.getTheNode().value == value){

                    return ;
                }
                if(height == size){
                //size在此处起到衡量此时高度的作用
                    Node.Pointer next = nowPoint.next;
                    nowPoint.next = connectPoint;
                    connectPoint.next = next;
                    //完成连接

                    connectPoint = connectPoint.down;
                    size --;
                    //向下移动
                }

                nowPoint = nowPoint.down;
                height --;
            }
        }

        public Node exist(int value){

            Node.Pointer nowPoint = head.stackTop;

            while(nowPoint!=null){

                if(nowPoint.next == null ||nowPoint.next.getTheNode().value > value){

                    nowPoint = nowPoint.down;
                }else if(nowPoint.next.getTheNode().value < value){

                    nowPoint = nowPoint.next;
                }else {
                    //下一个存在，不大不小就是相等
                    return nowPoint.next.getTheNode();
                }
            }

            return null;
        }

        public boolean delete(int value){

            Node node = exist(value);
            if(node == null){

                return false;
            }

            Node.Pointer nowPoint = head.stackTop;

            int height = nowPoint.getTheNode().size;
            int size = node.size;
            Node.Pointer deletePoint = node.stackTop;


            while(deletePoint!=null){

                while(nowPoint.next!=null &&nowPoint.next.getTheNode().value < value){

                    nowPoint = nowPoint.next;
                }

                if(height == size){

                    Node.Pointer next = deletePoint.next;
                    nowPoint.next = next;
                    //完成连接

                    deletePoint = deletePoint.down;
                    size --;
                }

                nowPoint = nowPoint.down;
                height --;

            }

            return true;
        }

        public void printTraversal(){

            Node.Pointer nowPoint = head.stackTop;

            while(nowPoint.down!=null){
                nowPoint = nowPoint.down;
            }

            while(nowPoint.next!=null){
                nowPoint = nowPoint.next;
                System.out.print(nowPoint.getTheNode().value + " ");
            }

            System.out.println();
        }

        public void checkTraversal(){

            Node.Pointer nowPoint = head.stackTop;

            while(nowPoint!=null){

                Node.Pointer p = nowPoint;
                while(p !=null){

                    if(p.getTheNode().value != Integer.MIN_VALUE){

                        System.out.print(p.getTheNode().value+ " ");
                    }
                    p = p.next;
                }
                nowPoint = nowPoint.down;
                System.out.println();
            }
        }

        private class Node{

            Pointer stackTop;
            int size;
            int value;

            private void largeTheHead(int size){
            //只对head有效，因此这里的this都是head的意思
                if(size > this.size){

                    Pointer p = this.stackTop;
                    for(int i = this.size ;i < size;i++){

                        Pointer newP = new Pointer();
                        newP.down = p;
                        p = newP;
                    }

                    this.stackTop = p;
                    this.size = size;
                }
            }

            public Node(int value,int size) {

                this.value = value;
                this.size = size;

                stackTop = new Pointer();

                Pointer p = stackTop;
                for(int i =1;i<size;i++){

                    p.down = new Pointer();
                    p = p.down;
                }
            }

            private class Pointer{

                Pointer next;//下一个节点的相同位置
                Pointer down;//本节点中下一个

                Pointer(){

                    next = null;
                    down = null;
                }

                public Node getTheNode(){

                    return Node.this;
                }
            }
        }
    }
}
