/**
 * 这道题是建立在5的基础上的，看不懂的话参考5
 * 
 * 给定两个可能有环也可能无环的链表
 * 试判断两链表是否相交，如果相交返回第一个相交节点
 * 如果不相交返回空
 */

public class 链表题目讲解6 {
    public static void main(String[] args) {
        /**
         * what can I say
         */
    }

    public static class Solution {

        public Node method(ListNode l1, ListNode l2) {

            Node node1 = method2(l1);
            Node node2 = method2(l2);

            if (node1 == null && node2 == null) {// 第一种情况，两链表均无环

                /**
                 * 如果两个都是无环的链表
                 * 那如果相交，则之后一定有一段共用区域（链表相交不会再分开
                 * 在遍历的时候记录两链表长度
                 * 如果判断相交，则先让长链表指针走完相差的距离，再让短链表指针开始动
                 * 相遇时就是第一个相交的点
                 */

                Node head1 = l1.top;
                Node head2 = l2.top;

                int length1 = 0;
                while (head1.next != null) {

                    length1++;
                    head1 = head1.next;
                }

                int length2 = 0;
                while (head2.next != null) {

                    length2++;
                    head2 = head2.next;
                }

                /**
                 * 例如，只有一个有效节点的链表
                 * top.next不为空则++
                 * 同时往后走，
                 * 不写head==null的原因是head之后还要用
                 */

                if (head1 == head2) {

                    head1 = l1.top;
                    head2 = l2.top;
                    if (length1 >= length2) {

                        int lengthDifference = length1 - length2;

                        for (int i = 0; i < lengthDifference; i++) {

                            head1 = head1.next;
                        } // 验证边界条件
                        /**
                         * 如果两链表等长，则哪个链表都不提前走，不进循环，正确
                         * 如果相差一，只走一步，正确
                         */

                        while (head1 != head2) {

                            head1 = head1.next;
                            head2 = head2.next;
                        }

                        return head1;
                    } else {

                        int lengthDifference = length2 - length1;

                        for (int i = 0; i < lengthDifference; i++) {

                            head2 = head2.next;
                        } // 验证边界条件
                        /**
                         * 如果两链表等长，则哪个链表都不提前走，不进循环，正确
                         * 如果相差一，只走一步，正确
                         */

                        while (head1 != head2) {

                            head1 = head1.next;
                            head2 = head2.next;
                        }

                        return head1;
                    }
                } else {

                    return null;
                } // 直到最后面都没有相交

            } else if (node1 == null || node2 == null) {// 一个有环一个无环

                // 一个有环一个无环不可能相交，因为有环链表不可能再叉出去
                return null;
            } else {// 两个都有环
                /**
                 * 如果两个有环链表相交，则它们一定共用环
                 * 所以该情况下有三种情况
                 * 1.两个环没有任何关系（不相交
                 * 2.两个链表在入环之前相交
                 * 3.两个链表在不同点入环
                 */

                if (node1 == node2) {// 情况2，
                    // 如果两链表在入环前相交则入环节点相同（充要条件
                    // 在这种情况下，就与两个链表都无环的相交问题类似了

                    Node head1 = l1.top;
                    Node head2 = l2.top;

                    int length1 = 0;
                    int length2 = 0;

                    while (head1 != node1) {

                        length1++;
                        head1 = head1.next;
                    }

                    while (head2 != node2) {

                        length2++;
                        head2 = head2.next;
                    }

                    if (length1 >= length2) {

                        int lengthDifference = length1 - length2;

                        head1 = l1.top;
                        head2 = l2.top;

                        for (int i = 0; i < lengthDifference; i++) {

                            head1 = head1.next;
                        }

                        while (head1 != head2) {

                            head1 = head1.next;
                            head2 = head2.next;
                        }

                        return head1;
                    } else {

                        int lengthDifference = length2 - length1;

                        head1 = l1.top;
                        head2 = l2.top;

                        for (int i = 0; i < lengthDifference; i++) {

                            head2 = head2.next;
                        }

                        while (head1 != head2) {

                            head1 = head1.next;
                            head2 = head2.next;
                        }

                        return head1;
                    }

                } else {// 情况1和3

                    Node begin = node1;

                    do {

                        node1 = node1.next;
                        if (node1 == node2) {

                            return node1;
                        } // 情况3，如果两链表只是入环位置不同，则node1遍历一遍这个环
                          // 则当两节点相遇时，就是两链表相遇的节点，返回该节点即可
                    } while (node1 != begin);

                    return null;// 如果node1走了一圈了都没遇到node2，则说明是情况1，两个链表不相交
                }

            }

        }

        public Node method2(ListNode list) {// 从链表题目详解5搬过来的

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
