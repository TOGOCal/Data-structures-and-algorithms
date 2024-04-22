public class 二叉树的打印问题 {

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

        Solution.printMethod1(node1);

    }

    public static class Node {

        String value;

        Node left;
        Node right;

        Node(String x) {

            this.value = x;
        }
    }

    public static class Solution {

        public static void printMethod1(Node head) {

            // 打印方法1：
            /**
             * ..........4
             * ......v3v-<
             * 1-<
             * ......^2^
             */

            System.out.println("打印方法1:");
            printMethod1Help(head, 0, "H", 10);
            System.out.println();
        }

        public static void printMethod1Help(Node head, int height, String identity, int len) {

            /**
             * 参数解释：
             * head为头节点
             * height为高度，代表这个节点是位于第几层的，再用层数乘以设定好的空格值，就可以使得米格节点对齐
             * identity身份，代表这个节点是什么，如果是头节点身份就是H
             * 左节点由于要向上指，所以用^代表，右节点向下指，用v表示，这个符号指向他们的头节点
             * len的代表是每个值占的位置，因为222与22占地不一样，为了使树的节点对齐，所以默认每个节点都打印len个长度
             * 
             */

            if (head == null) {

                return;
            }

            // 由整棵树希望达到的形状来看，二叉树的遍历方式是右->头->左
            printMethod1Help(head.right, height + 1, "v", len);

            String str = identity + head.value + "-";
            int last = len - str.length();

            str = str + getSpace(last);// 用空格填好剩余位置
            System.out.println(getSpace(height * len) + str);

            printMethod1Help(head.left, height + 1, "^", len);
        }

        public static String getSpace(int howMany) {

            String space = " ";
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < howMany; i++) {

                sb.append(space);
            }

            return sb.toString();
        }
    }
}
