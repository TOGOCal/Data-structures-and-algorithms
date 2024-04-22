import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class 二叉树相关问题2 {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.print("输入对折次数:");
        int x = s.nextInt();

        System.out.println("方法1输出:");
        Solution.preQuestion(x);

        System.out.println("方法2输出:");
        Solution.preQuestionMethod1pro(x);

        System.out.println("方法3输出:");
        Solution.myPrint(1, x, true);

        s.close();
    }

    public static class Node {

        char value;

        Node left;
        Node right;

        Node(char x) {

            this.value = x;
        }
    }

    public static class Solution {

        public static void preQuestion(int x) {

            /**
             * 问题背景：
             * 将一个纸条对折一次，则相对原纸条而言，多了一条向纸条背面的折痕，输出b代表back；
             * 如果将纸条对折两次，则相对原纸条而言，输出顺序为bbf（f代表front，代表折痕向前
             * 问题：给定对折次数，输出对折后纸条的折痕状态
             */
            System.out.println(preQuestionMethod1(x));
        }

        public static String preQuestionMethod1(int x) {// 我自己写的方法

            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < x; i++) {

                StringBuilder help = new StringBuilder(sb.reverse());
                sb.reverse();
                for (int ii = 0; ii < help.length(); ii++) {

                    if (help.charAt(ii) == 'b') {

                        help.setCharAt(ii, 'f');
                    } else if (help.charAt(ii) == 'f') {// 只有b和f两种情况

                        help.setCharAt(ii, 'b');
                    }
                }
                sb.append('b');
                sb.append(help);
            }

            return sb.toString();
        }

        public static void preQuestionMethod1pro(int x) {

            /**
             * 我自己顿悟的方法2：
             * 核心思想：建立一刻二叉树
             * 构树规则：
             * 1.对于每个头节点左节点凹右节点凸
             * 2.用中序遍历的方式遍历整棵树，得到的就是纸条的规律
             */

            Node head = preQuestionBuildeTree(x);
            printTree(head);

        }

        public static Node preQuestionBuildeTree(int x) {

            if (x == 0) {

                return null;
            }

            Node head = new Node('b');
            Node nextLevelEnd = null;
            Node thisLevelEnd = head;

            Queue<Node> queue = new LinkedList<>();
            queue.add(head);

            for (int i = 1; i < x;) {

                Node nowNode = queue.poll();

                nowNode.left = new Node('b');
                nowNode.right = new Node('f');
                queue.add(nowNode.left);
                queue.add(nowNode.right);
                nextLevelEnd = nowNode.right;

                if (nowNode == thisLevelEnd) {

                    i++;
                    thisLevelEnd = nextLevelEnd;
                }

            }

            return head;
        }

        public static void printTree(Node node) {

            if (node == null) {

                return;
            }

            printTree(node.left);
            System.out.print(node.value);
            printTree(node.right);
        }

        public static void myPrint(int x, int N, boolean down) {

            /**
             * x:当前是第几层
             * N:最多有多少层
             * down:是否向下
             */
            if (x > N) {

                return;
            }

            myPrint(x + 1, N, true);

            System.out.print(down ? "凹" : "凸");

            myPrint(x + 1, N, false);
        }
    }
}
