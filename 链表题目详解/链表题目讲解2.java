
/**
 * 判断一个链表是否是回文结构
 */

import java.util.Scanner;
import java.util.Stack;

public class 链表题目讲解2 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        ListNode list = new ListNode();

        String str = s.nextLine();

        for (int i = 0; i < str.length(); i++) {

            Node node = new Node(str.charAt(i));
            list.addList(node);
        }

        System.out.println(method.method1(list));
        System.out.println(method.method2(list));
        System.out.println(method.method3(list));
        list.printList();// 检查是否回到原顺序
        s.close();
    }

    public static class method {

        public static boolean method1(ListNode list) {// 最原始的方法

            Stack<Node> stack = new Stack<>();
            Node node = list.top.next;
            while (node != null) {

                stack.push(node);
                node = node.next;
            }

            node = list.top.next;
            while (node != null) {

                if (!node.equals(stack.pop())) {

                    return false;
                } // 栈可以使得其逆序输出，所以正着与倒着只要不相等就可以判断为false了
                node = node.next;
            }

            return true;
        }

        public static boolean method2(ListNode list) {// 改进版本1，可以使得内存消耗减半

            Node node = list.top.next;

            if (node == null) {

                return false;
            } else if (node.next == null) {

                return true;
            } else if (node.next.next == null) {

                return node.equals(node.next);
            }

            Node p1 = node.next;
            Node p2 = node.next.next;

            while (p2.next != null && p2.next.next != null) {

                p1 = p1.next;
                p2 = p2.next.next;
            } // 好像单链表只能通过这种方式算出中点位置

            // p1是绝对中点或者偶数的上中点
            Stack<Node> stack = new Stack<>();

            while (p1.next != null) {

                p1 = p1.next;
                stack.push(node);
            }

            while (!stack.isEmpty()) {

                if (!node.equals(stack.pop())) {

                    return false;
                }
            } // 这样栈的大小就只有一半大了

            return true;
        }

        public static boolean method3(ListNode list) {// 改进版本2，可以使空间复杂度为O(1)

            Node node = list.top.next;

            if (node == null || node.next == null) {

                // 链表为空或者链表只有一个元素自然是回文的
                return true;
            }

            Node slow = node;
            Node fast = node;

            while (fast.next != null && fast.next.next != null) {

                // 快指针可以向前走
                slow = slow.next;
                fast = fast.next.next;
            } // 经过这一步慢指针位于绝对中点或者是上中点

            Node now = slow.next;
            slow.next = null;// 将中点的下一个指向空，作为后一步遍历时候的终止条件

            Node pre = slow;// now的上一个指针
            Node next;

            while (now != null) {

                next = now.next;
                now.next = pre;// 往回指
                pre = now;// pre为下一次做准备
                now = next;
            } // 完成单节点的反转

            // 现在pre是右边的开头

            boolean result = true;
            Node node_ = pre;
            while (node != null && node_ != null) {

                // node和pre分别是两端的头节点
                if (!node.equals(node_)) {

                    // 如果不相等，则结果是false;
                    result = false;
                    break;
                }

                node = node.next;
                node_ = node_.next;
            }

            // 将后面的反转回来
            // 这里的pre，now，next都是作为相对位置存在的，也就是反转后的顺序
            now = pre;
            pre = null;

            while (now != null) {

                next = now.next;
                now.next = pre;
                pre = now;// 向前移动
                now = next;
            } // 将链表反转回来

            return result;
        }
    }

    public static class ListNode {// 链表

        Node top = new Node((char) 0);// 头节点不存储东西，同时不计入长度

        public void addList(Node a) {

            Node node = top;

            while (node.next != null) {

                node = node.next;
            }

            node.next = a;
        }// 完成数组链表的创建，不属于解题过程，自然不算时间复杂度等等

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
    }
}
