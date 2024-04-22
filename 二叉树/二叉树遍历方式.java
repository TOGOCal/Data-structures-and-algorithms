import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class 二叉树遍历方式 {

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

        TraverseMethod.method2(node1);
    }

    public static class Node {

        String value;

        Node left;
        Node right;

        Node(String value) {

            this.value = value;
        }
    }

    public static class TraverseMethod {

        public static void method1(Node head) {

            // 先序遍历：先头节点再左节点再右节点
            if (head == null) {

                return;
            }
            System.out.print(head.value);

            method1(head.left);
            method1(head.right);
        }

        public static void method2(Node head) {

            // 中序遍历：先左子树，再头节点，再右子树
            if (head == null) {

                return;
            }
            method2(head.left);
            System.out.print(head.value);
            method2(head.right);
        }

        public static void method3(Node head) {

            // 后序遍历：先左子树，再右子树，在头节点s
            if (head == null) {

                return;
            }

            method3(head.left);
            method3(head.right);
            System.out.print(head.value);
        }

        public static void understandmethod(Node head) {

            // 递归序：递归遍历二叉树的时候每个节点会到达三次
            /**
             * 第一次是函数从上节点指针到达这个节点
             * 第二次是左子树返回
             * 第三次是右子树返回
             * 而所谓的先序，中序，后续遍历
             * 不过是分别在第1，2，3次到达节点的时候打印节点信息而已
             */
            if (head == null) {

                return;
            }

            understandmethod(head.left);
            understandmethod(head.right);
        }

        public static void method1pro(Node head) {

            // 一切递归操作都可以改成非递归操作
            // 先序遍历改
            if (head != null) {

                Stack<Node> stack = new Stack<>();
                stack.push(head);

                while (!stack.isEmpty()) {

                    // 第一步弹出打印
                    // 第二步压入右节点
                    // 第三步压入左节点
                    // 栈是反的
                    Node node = stack.pop();

                    System.out.print(node.value + "  ");

                    if (node.right != null) {

                        stack.push(node.right);
                    }

                    if (node.left != null) {

                        stack.push(node.left);
                    }
                }
            }

        }

        public static void method2pro(Node head) {

            // 中序遍历：先左，再头，后右
            // 思想：任何一棵二叉树都可以按照左子树进行分解
            // (每一个右节点向左化斜线进行分解)
            // 所以要想按照先左再头后右的方式进行打印，可以先将这个左入栈
            Node node = head;

            if (node != null) {

                Stack<Node> stack = new Stack<>();
                while (!stack.isEmpty() || node != null) {

                    if (node != null) {

                        stack.push(node);

                        node = node.left;// 向左移动
                        // 将一条左边全部存入栈中
                    } else {
                        // node==null
                        // 说明一条左边已经全部存入
                        node = stack.pop();// 输出
                        System.out.print(node.value + "  ");
                        node = node.right;// 向右移动
                    }
                }
            }
        }

        public static void method3pro(Node head) {

            // 在一改的基础上，先序遍历的顺序是头左右
            // 如果在压栈的时候先左后右，弹出并打印的顺序就是头右左
            // 逆序就是左右头，后续遍历

            if (head != null) {

                Stack<Node> stack = new Stack<>();
                Stack<Node> stack_ = new Stack<>();// 保存逆序

                while (!stack.isEmpty()) {

                    Node node = stack.pop();

                    stack_.push(node);

                    if (node.left != null) {

                        stack.push(node.left);
                    }

                    if (node.right != null) {

                        stack.push(node.right);
                    }
                }

                while (!stack_.isEmpty()) {

                    Node node = stack_.pop();

                    System.out.print(node.value + "  ");
                }
            }
        }

        public static void method3propro(Node node) {

            // 左右头
            // node就是头节点
            if (node != null) {

                Stack<Node> stack = new Stack<>();

                Node check = null;

                stack.push(node);

                while (!stack.isEmpty()) {

                    check = stack.peek();// 栈顶位置（最后放进去的

                    /**
                     * 分析：
                     * 对于每个树的最后一个节点，left为空，right为空
                     * 则两个if都不进，直接打印（左右头说明先打印子树节点，对于最后的节点自然一遇到就打印
                     * 
                     * node停留的位置始终是上次访问的位置
                     * check的位置是当前位置
                     * 栈的作用是把将要访问的位置存下来
                     * 
                     * 如果上次访问的位置既不是目前节点的左节点，又不是右节点，说明这个节点是新访问的节点if_1
                     * 
                     * 如果上一次访问的是左节点（node==check.left)则说明这次该访问的是右节点if_2
                     * 疑问：node != check.left && node != check.right的反就是node == check.left || node ==
                     * check.right
                     * 将这个部分按照if_2中的形式与上node != check.right那结果不应该就是node == check.left吗，这两种写法有区别吗
                     * 
                     * 剩下的部分就是node == check.right（if_3
                     * 这时说明左右都被访问完了
                     * 该打印头节点了
                     * 同时将node移动到这个刚刚打印的位置，代表刚刚访问的是这个位置
                     */
                    if (check.left != null && node != check.left && node != check.right) {

                        stack.push(check.left);
                        // 压入节点后，下一个while循环访问的就是这个地址
                    } else if (check.right != null && node != check.right) {

                        stack.push(check.right);
                    } else {

                        System.out.print(stack.pop().value);
                        node = check;
                    }
                }
            }
        }

        public static void breadthFirstTraversal1(Node node) {// 二叉树对的广度优先遍历（按层遍历

            if (node == null) {

                return;
            }
            Queue<Node> queue = new LinkedList<>();// 申请一个队列、

            queue.add(node);// 往队列中加入一个

            while (!queue.isEmpty()) {

                // 打印一个节点的同时加入下面的两个节点
                System.out.println(queue.poll().value);

                if (node.left != null) {

                    queue.add(node.left);
                }

                if (node.right != null) {

                    queue.add(node.right);
                }

            }
        }

    }
}
