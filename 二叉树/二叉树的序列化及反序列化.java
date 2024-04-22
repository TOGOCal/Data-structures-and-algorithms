import java.util.Queue;
import java.util.LinkedList;

public class 二叉树的序列化及反序列化 {
    public static void main(String[] args) {

        Node node1 = new Node("1");
        Node node2 = new Node("2");
        Node node3 = new Node("3");
        node1.left = node2;
        node1.right = node3;

        Node node4 = new Node("4");
        Node node5 = new Node("5");
        Node node6 = new Node("6");
        Node node7 = new Node("7");

        node2.left = node4;
        node2.right = node5;

        node3.left = node6;
        node3.right = node7;

        Node node8 = new Node("8");
        Node node9 = new Node("9");

        node5.left = node8;
        node7.right = node9;

        System.out.println("==================================================================");
        System.out.println("先序遍历检查：");
        System.out.println(Solution.preMethod(node1));
        Solution.printTreeAccordingLevelpro(Solution.inverse(Solution.preMethod(node1)));
        System.out.println("==================================================================");
        System.out.println("中序遍历检查：");
        System.out.println(Solution.preMethod2(node1));
        // Solution.printTreeAccordingLevelpro(Solution.inverse2(Solution.preMethod2(node1)));
        System.out.println("中序序列化无法反序列化");
        System.out.println("==================================================================");
        System.out.println("宽度优先遍历：");
        System.out.println(Solution.breathFirstMethod(node1));
        Solution.printTreeAccordingLevelpro(Solution.prebreathFirstMethod(Solution.breathFirstMethod(node1)));// ok成功

    }

    public static class Node {

        String value;

        Node left;
        Node right;

        Node(String x) {

            this.value = x;
        }
    }

    public class Solution {

        public static Queue<String> preMethod(Node head) {// 先序序列化

            // 将某二叉树按照先序遍历的方式序列化到一个队列里面
            Queue<String> result = new LinkedList<>();

            pre(head, result);// 由于要调用递归，所以再写一个函数
            return result;
        }

        public static void pre(Node node, Queue<String> queue) {// 先序序列化为了使用递归写出的辅助方法

            if (node == null) {

                queue.add(null);// null需要在反序列化的时候使用，所以不能不写
            } else {

                queue.add(String.valueOf(node.value));
                pre(node.left, queue);
                pre(node.right, queue);
            }

        }

        public static Node inverse(Queue<String> queue) {// 先序反序列化

            if (queue == null || queue.size() == 0) {

                return null;
            }

            return preb(queue);
        }

        public static Node preb(Queue<String> queue) {// 先序反序列化为了调用递归写的辅助方法

            String str = queue.poll();

            if (str == null) {

                return null;
            }

            Node head = new Node(str);
            head.left = preb(queue);
            head.right = preb(queue);

            return head;
        }

        public static Queue<String> preMethod2(Node head) {// 中序序列化

            // 左，头，右
            if (head == null) {

                return null;
            }

            Queue<String> queue = new LinkedList<>();
            pre2(head, queue);
            return queue;
        }

        public static void pre2(Node node, Queue<String> queue) {// 中序序列化为了调用递归写的辅助方法

            if (node == null) {

                queue.add(null);// null需要在反序列化的时候使用，所以不能不写
            } else {

                pre2(node.left, queue);
                queue.add(String.valueOf(node.value));
                pre2(node.right, queue);
            }
        }

        // 中序方式无法反序列化
        // public static Node inverse2(Queue<String> queue) {
        // if (queue == null || queue.size() == 0) {

        // return null;
        // }

        // return preb2(queue);
        // }

        // public static Node preb2(Queue<String> queue) {

        // Node head = new Node(null);

        // String str = queue.poll();

        // head.left = preb2(queue);

        // if (str == null) {

        // return null;
        // }

        // head.value = str;
        // head.right = preb2(queue);

        // return head;
        // }

        public static Queue<String> breathFirstMethod(Node head) {// 广度优先序列化

            if (head == null) {

                return null;
            }

            Queue<String> result = new LinkedList<>();

            Queue<Node> help = new LinkedList<>();

            help.add(head);
            result.add(head.value);

            while (!help.isEmpty()) {

                Node node = help.poll();

                /**
                 * 逻辑：如果是空的话加入序列化但是不加入队列（队列中的是还会使用的
                 * 非空则既加队列又加序列化
                 */
                if (node.left != null) {

                    help.add(node.left);
                    result.add(node.left.value);
                } else {

                    result.add(null);
                }

                if (node.right != null) {

                    help.add(node.right);
                    result.add(node.right.value);
                } else {

                    result.add(null);
                }
            }

            return result;
        }

        public static Node prebreathFirstMethod(Queue<String> queue) {// 广度优先序列化反序列化

            if (queue == null || queue.size() == 0) {

                return null;
            }

            String str = queue.poll();
            Node head = new Node(str);

            Queue<Node> q = new LinkedList<>();
            q.add(head);

            Node node = null;

            while (!q.isEmpty()) {

                node = q.poll();
                str = queue.poll();

                if (str != null) {

                    node.left = new Node(str);
                    q.add(node.left);
                } else {

                    node.left = null;
                }

                str = queue.poll();

                if (str != null) {

                    node.right = new Node(str);
                    q.add(node.right);
                } else {

                    node.right = null;
                }

            }
            return head;
        }

        public static void printTreeAccordingLevelpro(Node head) {
            if (head == null) {

                return;
            }

            Queue<Node> queue = new LinkedList<>();

            Node thisLevelEnd = head;
            Node nextLevelEnd = null;

            queue.add(head);

            while (!queue.isEmpty()) {

                Node node = queue.poll();

                if (node.left != null) {

                    queue.add(node.left);
                    nextLevelEnd = node.left;
                }

                if (node.right != null) {

                    queue.add(node.right);
                    nextLevelEnd = node.right;
                }

                System.out.print(node.value);
                if (node == thisLevelEnd) {

                    System.out.println();
                    thisLevelEnd = nextLevelEnd;
                }
            }
        }

    }
}
