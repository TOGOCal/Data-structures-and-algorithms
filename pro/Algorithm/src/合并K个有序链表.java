import java.util.*;

public class 合并K个有序链表 {

    //知识点：堆
    /**
     * 可以选择手搓，也可以使用比较器
     * 这里复习比较器的用法
     */


    public static void main(String[] args) {

        Scanner s= new Scanner(System.in);

        //多少个链表
        int n = s.nextInt();


        List<LinkedList> list = new ArrayList<>();

        for(int i = 0 ; i< n ;i++){

            int length = s.nextInt();

            LinkedList l = new LinkedList();

            for(int ii =0 ;ii< length;ii++){

                l.addNew(s.nextInt());
            }

            list.add(l);

        }


        System.out.println(new Solution().mergeLinkedList(list));

        s.close();

    }


    static class Solution{

        public LinkedList mergeLinkedList(List<LinkedList> list){

            //小根堆
            PriorityQueue<Node> heep = new PriorityQueue<>(new NodeCompare());

            for(LinkedList l : list){

                //加入所有第一个节点
                if(!l.isEmpty()){

                    //因为不为空，所以一定存在next
                    heep.add(l.head.next);
                }
            }

            LinkedList res = new LinkedList();

            while(!heep.isEmpty()){

                Node node = heep.poll();

                //加入系啊一个
                if(node.next!=null){

                    heep.add(node.next);
                }

                res.addNew(node);//将这个加入结果链表

            }

            return res;

        }

    }


    static class NodeCompare implements Comparator<Node>{

        @Override
        public int compare(Node o1, Node o2) {
            return o1.val - o2.val;
            //return o2.val - o1.val;
        }
    }



    static class LinkedList{


        Node head;
        Node tail;
        int size;


        LinkedList(){

            head = new Node(Integer.MAX_VALUE);
            tail = head;

            size = 0;
        }

        public boolean isEmpty(){

            return head == tail;
        }

        public void addNew(int val){

            tail.next = new Node(val);
            tail = tail.next;
            size++;
        }

        public void addNew(Node node){

            tail.next = node;
            tail = tail.next;
            size++;
        }


        public String toString(){


            StringBuilder sb = new StringBuilder();

            Node cur = head.next;

            while(cur != null){

                sb.append(cur.val).append(" ");
                cur = cur.next;
            }

            return sb.toString();
        }

    }

    static class Node{

        int val;
        Node next;

        Node(int val){
            this.val = val;
        }

    }

}
