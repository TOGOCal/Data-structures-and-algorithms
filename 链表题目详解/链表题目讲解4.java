import java.util.HashMap;

/**
 * 一种特殊的单链表，
 * 它主线是非环结构
 * 但是除了一个next指针指向下一个节点外
 * 还有一个rand指针指向这个链表中任意一个节点
 * 现在需要完全拷贝一份这个链表，问如何做到
 *
 * 需要注意的是，是复制一份这样的链表出来，意思是占据了不同的空间，而并不是继续用之前的节点进行连接
 */

public class 链表题目讲解4 {
    public static void main(String[] args) {

    }

    public static class Solution {

        public ListNode method1(ListNode list) {// 这里设置

            HashMap<Node, Node> map = new HashMap<>();// 使用哈希表进行对应

            Node node = list.top.next;

            while (node != null) {

                map.put(node, new Node(node.value));
            } // 将节点在哈希表中进行了复制

            node = list.top.next;

            ListNode l = new ListNode();// 头节点为空
            l.top.next = map.get(node).next;
            l.top.rand = map.get(node).rand;

            while (node != null) {

                map.get(node).next = map.get(node.next);
                map.get(node).rand = map.get(node.rand);
                node = node.next;
            }

            return l;
        }

        public ListNode method2(ListNode list) {

            /**
             * 思想：
             * 哈希表使用的原因是可以通过哈希表找到每个节点自己的克隆节点
             * 想办法替换掉这一个步骤即可
             * 方法2将新建的节点添加到老节点的后面，就可以通过老节点.next的方式找到对应节点
             * 再遍历一遍链表，将每个克隆节点对应的rand指针建立
             * 再最后将两个节点分离即可
             */
            Node node = list.top.next;

            while (node != null) {

                Node next = node.next;

                Node node_ = new Node(node.value);
                node.next = node_;
                node_.next = next;
                /**
                 * 这一部分可以简写为
                 * node.next=new Node(node.value)
                 * node.next.next=next
                 */

                node = next;
            } // 将新节点建立在老节点的后面

            node = list.top.next;

            while (node != null) {

                Node node_ = node.next;
                // node_.rand=node.rand.next;
                node_.rand = (node.rand == null ? null : node.rand.next);// 不能按照上面的形式写的原因是不清楚node.rand是否为空，为空的话自然不会有next指针，就会报错
                node = node_.next;
            } // 建立好新节点的rand指针

            node = list.top.next;

            ListNode l = new ListNode();
            l.addList(node.next);// 串联上第一个克隆节点

            while (node != null) {

                Node node_ = node.next;
                Node next = node.next.next;

                node.next = next;// 原线串联

                node_.next = (next == null ? null : next.next);// 克隆线串联
            }

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
        Node rand;

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
