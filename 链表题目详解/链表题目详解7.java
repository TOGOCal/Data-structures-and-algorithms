/**
 * 只给定链表中一个节点，（不给定头节点
 * 能否将这个节点从链表中删去
 * 
 * 捏嘛这种题的答案是不能
 */

public class 链表题目详解7 {
    public static void main(String[] args) {

    }

    public static class Solution {

        public void method1(Node node) {// 李代桃僵

            node.value = node.next.value;// 将node的下一个节点复制到这个节点上来
            node.next = node.next.next;// 删掉下一个节点
            // 问题：如果每个节点是服务器的话，就不能这么整
            // 问题2：删不掉最后的节点（very sad）
            /**
             * 原因：
             * 你将node置为空没有作用，node只是一个指向内存地址的指针而已
             * 置成null只是这个指针指向了空
             * 实际上的节点仍仍然串联在表中
             */

            // 所以这种方式不行
            // 正常删除节点只能给头节点

            /**
             * 这道题就是让你说为什么不能用这个方式删除
             */
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
