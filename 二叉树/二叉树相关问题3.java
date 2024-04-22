import java.util.Scanner;

public class 二叉树相关问题3 {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        Node node1 = new Node('1');

        Node node2 = new Node('2');
        Node node3 = new Node('3');
        node1.left = node2;
        node1.right = node3;

        Node node4 = new Node('4');
        Node node5 = new Node('5');
        Node node6 = new Node('6');
        Node node7 = new Node('7');

        node2.left = node4;
        node2.right = node5;

        node3.left = node6;
        node3.right = node7;

        Node node8 = new Node('8');
        Node node9 = new Node('9');

        node5.left = node8;
        node7.right = node9;

        System.out.println(Solution1.question(node1));
        System.out.println(Solution2.question(node1));
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

    public static class Solution1 {

        // 题目1：判断一棵二叉树是不是具有平衡性
        /**
         * 平衡性：二叉树左数与右树高度差不超过1
         */
        public static boolean question(Node head) {

            return mycheck(head).isbalance;
        }

        public static Information mycheck(Node node) {

            if (node == null) {

                return new Information(true, 0);
            } // 如果这个节点是棵空树，则高度差为0，是平衡树

            Information leftInformation = mycheck(node.left);// 左边节点的信息
            Information rightInformation = mycheck(node.right);// 右节点的信息

            int height = Math.max(leftInformation.height, rightInformation.height) + 1;
            // 树的高度由左右最高的height决定，+1的原因是要加上自己

            boolean isbalance = true;// 先将默认值设为真

            if (!leftInformation.isbalance || !rightInformation.isbalance
                    || Math.abs(leftInformation.height - rightInformation.height) > 1) {

                // 左子树不是平衡树或者右子树不是平衡树或者两边的高度差超过了1，这届条件满足其一就可知这棵树不是平衡树
                isbalance = false;
            }

            return new Information(isbalance, height);
        }

        public static class Information {

            boolean isbalance;
            int height;

            Information(boolean isbalance, int height) {

                this.isbalance = isbalance;
                this.height = height;
            }
        }

    }

    public static class Solution2 {

        // 题目2：给定一棵子树的头节点，返回这棵树里里面任意两个节点的距离的最大值

        public static int question(Node head) {

            return check(head).maxDistance;
        }

        public static Information check(Node node) {

            if (node == null) {

                return new Information(0, 0);
            }

            Information leftInformation = check(node.left);
            Information righInformation = check(node.right);

            int height = Math.max(leftInformation.height, righInformation.height) + 1;

            // 最大距离确定具体实现：
            // 有可能节点x两侧又一边的树很大，一边很小那么最大距离可能就是左侧一边的树从最左走到最右
            // 所以确定最大节点就是确定节点x从左到右和z两边的树各自的最大距离哪个更大
            int maxDistance = Math.max(leftInformation.height + 1 + righInformation.height,
                    Math.max(leftInformation.maxDistance, righInformation.maxDistance));

            return new Information(height, maxDistance);
        }

        public static class Information {

            int height;
            int maxDistance;

            Information(int height, int maxDistance) {

                this.height = height;
                this.maxDistance = maxDistance;
            }

        }
    }

    public static class Solution3 {

        /**
         * 搜素二叉树定义：
         * 每个节点中的值不同，且对每个节点而言，左节点小于，右节点大于
         * 需要注意的是，这里讨论的小于和大于，整棵左子树上最大的值小于头节点值，整棵右子树上的最小值大于头节点值
         */

        /**
         * 问题：一棵树可能不完全是搜索二叉树可能只有一部分树满足搜索二叉树的要求
         * 现在给定头节点，返回该二叉树满足搜索二叉树的最大子树
         * 大小定义：节点个数
         */

        public static Node question(Node head) {

            return help(head).satisfiedNode;
        }

        public static Information help(Node node) {

            if (node == null) {

                return null;
            }

            Information leftInformation = help(node.left);

            Information rightInformation = help(node.right);

            if (leftInformation == null && rightInformation == null) {

                return new Information(node.value, node.value, 1, true, node);
            } else if (rightInformation == null) {

                boolean metTheCondition = true;

                if (leftInformation.maxValue >= node.value || !leftInformation.metTheCondition) {

                    metTheCondition = false;
                }

                Node satisfiedNode = null;
                int size = leftInformation.size + 0;

                if (metTheCondition) {

                    satisfiedNode = node;
                    size++;
                } else {

                    satisfiedNode = leftInformation.satisfiedNode;
                }

                int maxValue = Math.max(leftInformation.maxValue, node.value);
                int minValue = Math.min(leftInformation.minValue, node.value);

                return new Information(maxValue, minValue, size, metTheCondition, satisfiedNode);

            } else if (leftInformation == null) {

                boolean metTheCondition = true;

                if (rightInformation.minValue <= node.value || !rightInformation.metTheCondition) {

                    metTheCondition = false;
                }

                Node satisfiedNode = null;
                int size = rightInformation.size + 0;

                if (metTheCondition) {

                    satisfiedNode = node;
                    size++;
                } else {

                    satisfiedNode = rightInformation.satisfiedNode;
                }

                int maxValue = Math.max(rightInformation.maxValue, node.value);
                int minValue = Math.min(rightInformation.minValue, node.value);

                return new Information(maxValue, minValue, size, metTheCondition, satisfiedNode);

            } else {

                boolean metTheCondition = true;
                if (rightInformation.minValue <= node.value || !rightInformation.metTheCondition
                        || leftInformation.maxValue >= node.value || !leftInformation.metTheCondition) {

                    metTheCondition = false;
                }

                Node satisfiedNode = null;
                int size = 0;

                if (metTheCondition) {

                    size = leftInformation.size + rightInformation.size + 1;
                    satisfiedNode = node;
                } else {

                    size = Math.max(leftInformation.size, rightInformation.size);

                    satisfiedNode = leftInformation.size > rightInformation.size ? leftInformation.satisfiedNode
                            : rightInformation.satisfiedNode;
                }

                int maxValue = Math.max(Math.max(rightInformation.maxValue, leftInformation.maxValue), node.value);
                int minValue = Math.min(Math.min(rightInformation.minValue, rightInformation.minValue), node.value);

                return new Information(maxValue, minValue, size, metTheCondition, satisfiedNode);
            }

        }

        public static class Information {

            int maxValue;
            int minValue;
            int size;
            boolean metTheCondition;
            Node satisfiedNode;

            Information(int maxValue, int minValue, int size, boolean metTheCondition, Node node) {

                this.maxValue = maxValue;
                this.minValue = minValue;
                this.size = size;
                this.metTheCondition = metTheCondition;
                this.satisfiedNode = node;
            }
        }
    }
}
