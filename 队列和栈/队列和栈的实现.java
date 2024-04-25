import java.util.Scanner;

public class 队列和栈的实现 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        doubleLinkList d = new doubleLinkList();
        d.mainmenu();

        boolean key1 = true;
        boolean key2 = true;

        while (key1 && key2) {

            char A = s.nextLine().charAt(0);
            if (A == '1') {
                key2 = false;
            }

            else if (A == '2') {
                key1 = false;
            } else {

                System.out.println("输入有误，请重新输入");
            }
        }

        while (key1) {

            d.menu1();
            char function = s.nextLine().charAt(0);
            switch (function) {
                case '1':
                    d.printList();
                    break;

                case '2':
                    System.out.print("输入id:");
                    int id = s.nextInt();
                    s.nextLine();
                    System.out.print("输入name:");
                    String name = s.nextLine();
                    Node n1 = new Node(id, name);

                    d.addList(n1);
                    break;

                case '3':
                    Node n2 = d.poop();

                    if (n2 != null)
                        System.out.println("id是:" + n2.id + "\nname是:" + n2.name);

                    break;

                default:
                    key1 = false;
                    break;
            }
            System.out.println("======================================");
        }

        while (key2) {
            d.menu2();
            char function = s.nextLine().charAt(0);
            switch (function) {
                case '1':
                    d.printList();
                    break;
                case '2':
                    System.out.print("输入id:");
                    int id = s.nextInt();
                    s.nextLine();

                    System.out.print("输入姓名:");
                    String name = s.nextLine();

                    Node n1 = new Node(id, name);

                    d.addList(n1);
                    break;

                case '3':
                    Node n2 = d.out();
                    if (n2 != null) {

                        System.out.println("id是:" + n2.id + "\nname是:" + n2.name);
                    }
                    break;

                default:
                    key2 = false;
                    break;
            }
            System.out.println("===================================================");
        }
        s.close();
    }

    public static class doubleLinkList {

        public void mainmenu() {
            System.out.println("1.栈操作");
            System.out.println("2.队列操作");
            System.out.print("请输入:");
        }

        public void menu1() {

            System.out.println("1.打印栈\t2.入栈");
            System.out.println("3.出栈\t4.退出程序");
        }

        public void menu2() {
            System.out.println("1.打印队列\t2.入队列");
            System.out.println("3.出队列\t4.退出程序");
        }

        Node head = new Node(0, "");

        public void printList() {

            if (head.next == null) {

                System.out.println("链表为空");
                return;
            }

            Node temp = head.next;

            while (temp != null) {

                System.out.println(temp.toString());
                temp = temp.next;
            }
        }

        public void addList(Node n) {// 入

            n.next = head.next;
            n.prev = head;
            if (head.next != null) {

                head.next.prev = n;
            }

            head.next = n;
        }

        public Node poop() {// 出栈

            if (head.next == null) {

                System.out.println("链表为空");
                return null;
            }
            Node temp = head.next;
            head.next = temp.next;
            temp.next.prev = head;

            temp.next = null;
            temp.prev = null;
            return temp;
        }

        public Node out() {// 尾部出队列

            if (head.next == null) {
                System.out.println("链表为空");
                return null;
            }

            Node temp = head.next;
            while (temp.next != null) {

                temp = temp.next;
            }

            temp.prev.next = null;

            temp.next = null;
            temp.prev = null;

            return temp;
        }

    }

    public static class Node {

        int id;
        String name;
        Node next;
        Node prev;

        Node(int id, String name) {

            this.id = id;
            this.name = name;
        }

        public String toString() {

            return ("id是:" + id + "\n名字是:" + name);
        }
    }
}

/**
 * (双向链表实现)
 * 使用双向链表
 * 栈就可以从头部入从头部出
 * 队列从头部入从尾部出
 */
