public class 树型dp {

    public static void main(String[] args) {

    }

    public static class MyTree{

        Node head;

        MyTree(){
            head = new Node(-1);
        }
    }

    public static class Node{

        Node pre;
        int data;

        Node left;
        Node right;

        public Node(int data) {

            this.data = data;
            pre = null;
            left = null;
            right = null;
        }
    }

    public static class Question1{
        /**
         * 问题1：
         * 给定一棵二叉树，求二叉树中的任意两节点之间的最大距离
         * 距离：任意一个节点都可以向上走或者向左或者向右走，但是不能走回头路
         * 从这个节点到其他节点的节点个数就是最大距离
         *
         * 方法思考：
         * 将所有节点的情况分为两种情况：
         * 当前节点不参与最大距离的构成
         *      则当前节点代表的最大距离是左右两棵子树中的最大距离的较大值
         * 挡墙节点参与最大距离的构成
         *      则当前节点代表的最大值是左右两颗子树的高度相加再加1（这个1：当前节点
         * 再通过比较选出这两个结果中的较大值
         *
         * 根据这个思考方向，我们可以直到每个节点需要向左右两棵树要什么信息：
         * 子树的最大距离以及其高度
         */

        public static class Information{

            int maxDistance;
            int height;

            Information(int maxDistance, int height){
                this.maxDistance = maxDistance;
                this.height = height;
            }
        }

        public static int maxDistance(Node root){

            return getTheInfo(root).maxDistance;
        }

        public static Information getTheInfo(Node nowNode){

            if(nowNode == null){

                return new Information(0, 0);
            }

            Information ifoLeft = getTheInfo(nowNode.left);
            Information ifoRight = getTheInfo(nowNode.right);

            int maxDistance = Math.max(Math.max(ifoLeft.maxDistance, ifoRight.maxDistance) ,
                    ifoLeft.height + ifoRight.height +1);

            int height = Math.max(ifoLeft.height, ifoRight.height) + 1;

            return new Information(maxDistance, height);
        }

    }
}
