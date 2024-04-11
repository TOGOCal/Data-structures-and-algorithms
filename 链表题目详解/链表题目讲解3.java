import java.util.ArrayList;
import java.util.Scanner;

/**
 * 将链表按照某值划分为左边小，中间相等，右边大的三个部分
 * 类似于数组的荷兰国旗问题
 */

public class 链表题目讲解3 {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        String str = s.nextLine();
        ListNode list = new ListNode();
        for (int i = 0; i < str.length(); i++) {

            Node n = new Node(str.charAt(i));
            list.addList(n);
        }

        System.out.println("请输入作为判断标准的字符，多输入按照第一个作数");
        Node com = new Node(s.nextLine().charAt(0));

        // list = Solution.method1(list, com);
        // list.printList();

        // list = Solution.method2(list, com);
        // list.printList();

        list = Solution.method3(list, com);
        list.printList();
        s.close();
    }

    public static class Solution {

        public static ListNode method1(ListNode list, Node n) {

            // 方法1：所有元素放入数组，在数组中进行排序，再复制到另一个数组中
            // 就像原始的荷兰国旗一样，这种方法无法保证稳定性
            ArrayList<Node> arraylist = new ArrayList<>();

            int num = 0;

            Node node = list.top.next;

            while (node != null) {

                arraylist.add(node);
                num++;
                Node no = node.next;
                node.next = null;// 入数组的时候将next初始化为空
                node = no;// 向前移动
            } // 将所有元素加在数组里

            int p1 = -1;
            int p2 = num;
            // 表示小的部分和大的部分的边界(num)

            for (int i = 0; i < p2; i++) {

                if (n.compare(arraylist.get(i)) > 0) {

                    // n大于数组中这个元素
                    // 则将其放进代表小的区域中

                    Node tool = arraylist.get(p1 + 1);
                    arraylist.set(p1 + 1, arraylist.get(i));
                    arraylist.set(i, tool);
                    p1++;
                } else if (n.compare(arraylist.get(i)) < 0) {

                    Node tool = arraylist.get(p2 - 1);
                    arraylist.set(p2 - 1, arraylist.get(i));
                    arraylist.set(i, tool);
                    p2--;
                    i--;
                }
            }

            ListNode l = new ListNode();
            for (int i = 0; i < num; i++) {

                l.addList(arraylist.get(i));
            }

            return l;
        }

        public static ListNode method2(ListNode list, Node n) {

            // 方法2：分别建立小串，等串，大串进行分别储存
            // 最后再将他们拼接起来
            // 明显的，这种方法可以保证方法的稳定性

            // （这是我为了图方便写出来的方法，与交的方法相比，在调用addList函数的时候，要找到函数的尾部
            // 这就导致了我的时间复杂度为O(N2)(我靠突然感觉这个方法好烂)

            // long freeMemory1 = Runtime.getRuntime().freeMemory();

            ListNode small = new ListNode();
            ListNode equal = new ListNode();
            ListNode big = new ListNode();

            ListNode reslut = new ListNode();

            // long freeMemory2 = Runtime.getRuntime().freeMemory();
            // System.out.println(freeMemory1 - freeMemory2);

            Node node = list.top.next;

            while (node != null) {

                Node next = node.next;
                node.next = null;
                if (n.compare(node) > 0) {

                    // n大于数组中这个元素
                    // 则将其放进代表小的区域中
                    small.addList(node);
                } else if (n.compare(node) < 0) {

                    big.addList(node);
                } else {

                    equal.addList(node);
                }
                node = next;
            }

            reslut.addList(small.top.next);
            reslut.addList(equal.top.next);
            reslut.addList(big.top.next);

            return reslut;
        }

        public static ListNode method3(ListNode list, Node n) {

            Node small1 = null;
            Node small2 = null;

            Node equal1 = null;
            Node equal2 = null;

            Node big1 = null;
            Node big2 = null;

            Node node = list.top.next;

            while (node != null) {

                Node next = node.next;
                node.next = null;
                if (n.compare(node) > 0) {

                    // n大于数组中这个元素
                    // 则将其放进代表小的区域中
                    if (small1 == null) {

                        small1 = node;
                        small2 = node;
                    } else {

                        small2.next = node;
                        small2 = node;
                    }

                } else if (n.compare(node) == 0) {

                    if (equal1 == null) {

                        equal1 = node;
                        equal2 = node;
                    } else {

                        equal2.next = node;
                        equal2 = node;
                    }
                } else {

                    if (big1 == null) {

                        big1 = node;
                        big2 = node;
                    } else {

                        big2.next = node;
                        big2 = node;
                    }
                }
                node = next;
            }

            if (small1 != null) {

                small2.next = equal1;// 是空的话就让其等于空,反正不连的话本来就是空
                equal2 = ((equal2 == null) ? (small2) : (equal2));// 决定下一步去连大于区域的 尾部是什么
                // 如果等于区域有数那自然去连接大于区域的就是等于区域
                // 如果没有数那连接大于区域的就是小于区域的尾部
            }

            if (equal2 != null) {// 这是防止前一步没有小于区域

                equal2.next = big1;
            }

            ListNode l = new ListNode();
            l.addList((small1 != null) ? small1 : ((equal1 != null) ? equal1 : big1));

            return l;
        }
    }

    public static class ListNode {// 链表

        Node top = new Node((char) 0);

        public void addList(Node a) {

            Node node = top;

            while (node.next != null) {

                node = node.next;
            }

            node.next = a;
        }

        public void printList() {

            Node node = top.next;

            if (node == null) {

                System.out.println("链表为空");
            }
            while (node != null) {

                System.out.print(node.value);
                node = node.next;
            }
        }
    }

    public static class Node {// 节点

        char value;
        Node next;

        Node(char a) {

            value = a;
        }

        public boolean equals(Node node) {

            if (this.value == node.value) {

                return true;
            } else {

                return false;
            }
        }

        public int compare(Node next) {

            return this.value - next.value;
        }// 返回值为正交换则代表
         // this.value>next.value,按照从小到大排序
    }
}
