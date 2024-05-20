import java.util.ArrayList;
import java.util.Scanner;

public class 单调栈pro {

    /**
     * 在单调栈中，我们处理了没有重复数的处理情况
     * 以下处理的是有重复情况的考虑：
     * 将单调栈中每个结构设计为链表，
     * 相同时则挂在链表后面
     */

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int n = s.nextInt();

        int[] arr = new int[n];

        for(int i =0;i<n;i++){

            arr[i]= s.nextInt();
        }

        Information[] ifos = mainMethod(arr);


        for(Information ifo:ifos){

            System.out.println(ifo.toString());
        }

        s.close();

    }

    public static Information[] mainMethod(int[] arr){

        Information[] info = new Information[arr.length];

        for(int i =0;i<info.length;i++){

            info[i] = new Information();
        }

        ArrayList<MyLinkedList> list = new ArrayList<>();

        for(int i=0;i<arr.length;i++){

            int key = isSuitable(arr,i,list);

            if(key ==1 ){

                MyLinkedList array = new MyLinkedList();
                array.add(new Node(i));
                list.add(array);
            }else if(key == 0){

                MyLinkedList array = list.getLast();
                array.add(new Node(i));
            }else{

                while(arr[i] > arr[list.getLast().end.value]){

                    MyLinkedList array = list.getLast();
                    list.removeLast();

                    while(!array.isEmpty()){

                        Node nowNode = array.out();
                        if(!list.isEmpty()){

                            info[nowNode.value].left = list.getLast().end.value;
                        }

                        info[nowNode.value].right = i;
                    }
                }

                i--;//重新对这一位进行判定
            }
        }

        while(!list.isEmpty()){

            MyLinkedList array = list.getLast();
            list.removeLast();

            while(!array.isEmpty()){

                Node nowNode = array.out();
                if(!list.isEmpty()){

                    info[nowNode.value].left = list.getLast().end.value;
                }
            }
        }

        return info;
    }

    public static int isSuitable(int[] arr ,int index , ArrayList<MyLinkedList> list){

        if(list.isEmpty() || arr[index] < arr[list.getLast().end.value]){

            return 1;
        }

        if(arr[index] == arr[list.getLast().end.value]){

            return 0;
        }

        return -1;
    }

    public static class Information{

        int left;
        int right;

        Information(){

            left=-1;
            right=-1;
        }

        @Override
        public String toString() {
            return "{"+ left + " " + right +'}';
        }
    }


    public static class MyLinkedList{

        Node head;
        Node end;

        MyLinkedList(){

            head = new Node(0);
            end =head;
        }

        public boolean isEmpty(){

            return head == end;
        }

        public void add(Node node){

            end.next = node;
            node.pre = end;
            end = node;
        }

        public Node out(){

            Node node = end;
            end = end.pre;
            end.next = null;

            return node;
        }
    }

    public static class Node{

        int value;

        Node next;
        Node pre;
        public Node(int value) {

            this.value = value;
        }
    }
}
