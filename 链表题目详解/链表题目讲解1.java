
/**
 * 四个相似案例
 * 1.输入链表头节点 奇数长度返回中点，偶数长度返回上中点
 * 2.输入链表头节点 奇数长度返回中点，偶数长度返回下中点
 * 3.输入链表头节点 奇数长度返回中点前一个，偶数长度返回上中点前一个
 * 4.输入链表头节点 奇数长度返回中点后一个，偶数长度返回下中点后一个
 * 
 */

/**
 * 哎搞不懂，反正知道有两个指针就行了
 */

import java.util.Scanner;

public class 链表题目讲解1 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        int num = s.nextInt();

        int arr[] = new int[num];

        ListNode list = new ListNode();
        for (int i = 0; i < num; i++) {

            arr[i] = s.nextInt();
            Node a = new Node(arr[i]);
            list.addList(a);
        }

        Solution st = new Solution();
        System.out.println(st.method1(list));
        System.out.println(st.method2(list));
        s.close();
    }

    public static class Solution {

        public Node method1(ListNode head) {// 传入头节点

            Node node = head.top.next;// 第一个有效的节点（可能有效
            if (node == null || node.next == null || node.next.next == null) {

                // 链表为空，链表只有一个，链表只有两个
                return node;
            }

            Node p1 = node.next;// 慢指针一次走一步，
            Node p2 = node.next.next;// 快指针一次走两步

            while (p2.next != null && p2.next.next != null) {

                p1 = p1.next;
                p2 = p2.next.next;
            }

            return p1;
        }

        public Node method2(ListNode head) {

            Node node = head.top.next;

            if (node == null || node.next == null) {

                // 没有元素或者只有一个元素
                return null;
            }

            Node p1 = node.next;// 0 1 2 3 4 5 6 7 8
            Node p2 = node.next;// 0 1 2 3 4 5 6 7 8//第一次快指针少走一个，相当于是问题回到了1的情况

            while (p2.next != null || p2.next.next != null) {

                p1 = p1.next;
                p2 = p2.next.next;
            }

            return p1;
        }

        // public int method3(ListNode head) {

        // Node node = head.top.next;

        // if (node == null || node.next == null || node.next.next == null) {

        // // 链表为空，链表只有一个，链表只有两个
        // return null;
        // }
        // }
    }

    public static class ListNode {// 链表

        Node top = new Node(0);// 头节点不存储东西，同时不计入长度

        public void addList(Node a) {

            Node node = top;

            while (node.next != null) {

                node = node.next;
            }

            node.next = a;
        }// 完成数组链表的创建，不属于解题过程，自然不算时间复杂度等等
    }

    public static class Node {// 节点

        int value;
        Node next;

        Node(int a) {

            value = a;
        }
    }
}
