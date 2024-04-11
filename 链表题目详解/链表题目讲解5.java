import java.util.HashSet;

/**
 * 给定一个链表，可能有环，可能无环
 * 写一个函数，如果有环则返回环开始的节点（如果是个圈返回头节点
 * 如果无环返回空
 */

public class 链表题目讲解5 {
    public static void main(String[] args) {

    }

    public static class Solution {

        public Node method1(ListNode list) {

            Node node = list.top.next;

            HashSet<Node> set = new HashSet<>();

            while (node != null) {

                if (set.contains(node)) {

                    return node;
                }
                set.add(node);
            }

            return null;
        }

        public Node method2(ListNode list) {

            /**
             * 方法2：
             * 准备快慢两个指针，快的每次走一步，慢的每次走两步
             * 如果快指针走到了空，则说明链表无环，返回空即可
             * 如果两指针相遇，则说明有环
             * 相遇瞬间将快指针置位回开始位置，斌让其每次走一步
             * 下一次两指针相遇的时候就是入环的头节点
             */
            Node node = list.top.next;

            if (node == null || node.next == null || node.next.next == null) {

                return null;
            }

            Node slow = node.next;

            Node fast = node.next.next;

            while (slow != fast) {

                if (fast == null) {

                    return null;
                }
                slow = slow.next;
                fast = fast.next.next;
            }

            fast = node;

            while (fast != slow) {

                slow = slow.next;
                fast = fast.next;
            }

            return fast;// 或者return slow都可以
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
