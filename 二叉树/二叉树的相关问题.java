import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public class 二叉树的相关问题 {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        Node node1 = new Node('1');
        node1.pre = null;

        Node node2 = new Node('2');
        Node node3 = new Node('3');
        node1.left = node2;
        node2.pre = node1;

        node1.right = node3;
        node3.pre = node1;

        Node node4 = new Node('4');
        Node node5 = new Node('5');
        Node node6 = new Node('6');
        Node node7 = new Node('7');

        node2.left = node4;
        node4.pre = node2;

        node2.right = node5;
        node5.pre = node2;

        node3.left = node6;
        node6.pre = node3;

        node3.right = node7;
        node7.pre = node3;

        Node node8 = new Node('8');
        Node node9 = new Node('9');

        node5.left = node8;
        node8.pre = node5;

        node7.right = node9;
        node9.pre = node7;

        System.out.println("请输入你想找的节点的value值");
        char value = s.nextLine().charAt(0);

        Node node = findNodeAccordingValue(value, node1);
        if (node == null) {

            System.out.println("没有找到节点");
        } else {

            Node answer = Solution.question1(node);
            System.out.print("方法1找到的后继节点是:");
            if (answer == null) {

                System.out.println("没有后继节点");
            } else {

                System.out.println(answer.value);
            }

            answer = Solution.question1pro(node);
            System.out.print("改进方法找到的节点是:");
            if (answer == null) {

                System.out.println("没有找到后继节点");
            } else {

                System.out.println(answer.value);
            }
        }
        System.out.println("检查是否正确：");
        System.out.println(midOrderCheck(node1));

        System.out.println("=========================================================");
        System.out.println("请输入你想要找前继节点的节点value:");

        value = s.nextLine().charAt(0);
        node = findNodeAccordingValue(value, node1);

        if (node == null) {

            System.out.println("没有找到节点");
        } else {

            System.out.print("方法1找到的前继节点是:");
            Node answer = Solution.quetion2(node);
            if (answer == null) {

                System.out.println("没有前继节点");
            } else {

                System.out.println(answer.value);
            }

            System.out.print("方法2找到的前继节点是:");
            answer = Solution.question2pro(node);
            if (answer == null) {

                System.out.println("没有前继节点");
            } else {

                System.out.println(answer.value);
            }
        }

        System.out.println("检查是否正确：");
        System.out.println(midOrderCheck(node1));

        s.close();

    }

    public static Node findNodeAccordingValue(char value, Node head) {

        if (head == null) {

            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);

        while (!queue.isEmpty()) {

            Node node = queue.poll();

            if (node.value == value) {

                return node;
            }

            if (node.left != null) {

                queue.add(node.left);
            }

            if (node.right != null) {

                queue.add(node.right);
            }
        }

        return null;
    }

    public static String midOrderCheck(Node head) {

        if (head == null) {

            return null;
        }

        Node node = head;
        Stack<Node> stack = new Stack<>();

        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty() || node != null) {

            if (node != null) {

                stack.push(node);
                node = node.left;
            } else {

                node = stack.pop();
                sb.append(node.value);
                node = node.right;
            } // 将左边存进去，存完了
        }

        return sb.toString();
    }

    public static class Node {// 这里的节点具有返回头节点的指针

        char value;

        Node left;
        Node right;

        Node pre;

        Node(char x) {

            this.value = x;
        }
    }

    public static class Solution {

        /**
         * 问题1：在有返回头节点的能力的节点的背景下
         * 给定一个节点，写一个方法，来返回其后续节点
         */
        public static Node question1(Node node) {

            Node node_ = node;
            while (node.pre != null) {

                node = node.pre;
            } // 出循环之后的得到的就是头节点

            Node head = node;
            Queue<Node> queue = new LinkedList<>();
            question1Help(queue, head);// 完成队列填充

            while (node_ != queue.poll()) {

                if (queue.isEmpty() | queue.size() == 0) {

                    return null;
                }
            }

            Node result = queue.poll();
            queue = null;// 将这个指针指向空，JVM就会将这个没有指针指着区域释放（我猜的

            return result;
        }

        public static void question1Help(Queue<Node> queue, Node node) {

            if (node == null) {

                return;
            }

            question1Help(queue, node.left);
            queue.add(node);
            question1Help(queue, node.right);
        }

        public static Node question1pro(Node node) {

            /**
             * 相较于之前的方法，之前的方法由于要遍历节点，所以时间复杂度为O(N)
             * 使用这个方法的时间复杂度为k，k是该节点与其后继节点的相对距离
             * 
             * 方法思想：找规律
             * 一个节点如果有右树，则该节点的后继节点一定是右树的最左下的节点（中序遍历特点
             * 一个节点如果没有右树，则顺着父指针向上找，直到找到某个父a（也可以是节点本身）是其父b的左子树，父b就是它的后继节点
             * 原因：中序遍历做头右，只有当某个节点的左子树结束才回去打印这个节点，说明我们要找到这个节点就要找左子树结束，而从某个节点向上找直到找到某个树是父的左子树，则代表这个树结束
             */

            if (node.right != null) {

                node = node.right;

                while (node.left != null) {

                    node = node.left;
                }

            } else {

                Node parent = node.pre;

                while (node != parent.left) {

                    node = node.pre;
                    parent = node.pre;
                    if (parent == null) {

                        break;
                    }
                }

                node = parent;
            }

            return node;
        }

        /**
         * 问题2：在有返回头节点的能力的节点的背景下
         * 给定一个节点，写一个方法，来返回其前驱节点
         */
        public static Node quetion2(Node node) {

            if (node == null) {

                return null;
            }

            Node head = node;
            while (head.pre != null) {

                head = head.pre;
            }

            // 中序遍历存储

            Queue<Node> queue = new LinkedList<>();
            quetion2help(head, queue);

            Node pre = null;
            Node thisnode = null;
            while (queue.size() != 0) {

                thisnode = queue.poll();
                if (thisnode == node) {

                    return pre;
                }

                pre = thisnode;
            }

            return null;
        }

        public static void quetion2help(Node node, Queue<Node> queue) {

            if (node == null) {

                return;
            }

            quetion2help(node.left, queue);
            queue.add(node);
            quetion2help(node.right, queue);
        }

        public static Node question2pro(Node node) {

            /**
             * 找规律：
             * 如果有左子树，则在左子树的最右下的节点
             * 如果没有左子树，则往上找父节点，当某个父节点a（也可能是节点本身）是其父b的右子树，则b为前驱节点
             */

            if (node.left != null) {

                node = node.left;
                while (node.right != null) {

                    node = node.right;
                }
            } else {

                Node parent = node.pre;

                while (node != parent.right) {

                    node = parent;
                    parent = parent.pre;
                    if (parent == null) {

                        return null;
                    }
                }
                node = parent;
            }

            return node;
        }
    }
}