import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 搜索二叉树 {

    /**BST binary search tree
     * 搜索二叉树构建特点
     * 1.左子树所有节点都小于根节点
     * 2.右子树所有节点都大于根节点
     * （以上规定对所有根节点有效
     *
     * 最基础的搜索二叉树（我写的这个）不能很好地解决输入数据本身就是有序地情况
     */

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int n = s.nextInt();

        ArrayList<Integer> list = new ArrayList<>();

        for(int i =0;i<n;i++){

            list.add(s.nextInt());
        }
        s.nextLine();

        BST bst = new BST(list);

        char key = '1';

        while(key != '0'){
            System.out.println("======================================");
            System.out.println("选择你需要执行的操作：");
            System.out.println("1.插入 2.删除 3.查找 4.遍历（中序）\n " +
                    "5.找到比某个数小的最大数 6.找到比某个数大的最小数 0.退出");

            key = s.nextLine().charAt(0);

            switch (key){

                case '1' -> {

                    System.out.println("请输入你想要插入的数：");

                    bst.insert(s.nextInt());
                    s.nextLine();
                }

                case '2' -> {

                    System.out.println("请输入你想要删除的数：");

                    bst.delete(s.nextInt());
                    s.nextLine();
                }

                case '3' -> {

                    System.out.println("请输入你想要查找的数：");

                    System.out.println("这个数受否存在："+bst.search(s.nextInt()));
                    s.nextLine();
                }

                case '4' -> bst.MorrisMiddleTraversal();

                case '5' -> {

                    System.out.println("请输入你想要查找的数：");

                    System.out.println("比这个数小的最大值："+bst.getSmallerMax(s.nextInt()));
                    s.nextLine();
                }

                case '6' -> {

                    System.out.println("请输入你想要查找的数：");

                    System.out.println("比这个数大的最小值："+bst.getBiggerMin(s.nextInt()));
                    s.nextLine();
                }

                default -> {}
            }
        }

        s.close();
    }

    public static class BST{

        Node root;

        BST(){

            root = null;
        }

        BST(List<Integer> list){

            root = new Node(list.getFirst());

            for(int i =1;i<list.size();i++){

                insert(list.get(i));
            }
        }

        public void MorrisMiddleTraversal(){
            /**
             * 如果某个节点没有左子树，则向右移动
             * 如果某个节点有左子树
             *      找到左子树中最右的节点
             *      如果这个最右的节点指向空，则将这个最右的节点指向这个节点，并向左移动
             *      如果这个节点的最右节点已经指向了某个节点，则将这个最右的节点指向空，并向右移动
             * 当节点走向空的时候遍历停止
             *
             * 中序遍历：没有左子树的节点直接打印
             * 有左子树的节点第二次到达时打印
             */

            Node node = root;
            Node mostRight;

            while(node != null){

                if(node.left == null){

                    System.out.print(node.value+" ");
                    node = node.right;
                }else{

                    mostRight = node.left;

                    while(mostRight.right !=null && mostRight.right != node){

                        mostRight = mostRight.right;
                    }

                    if(mostRight.right == null){

                        mostRight.right = node;
                        node = node.left;
                    }else{

                        System.out.print(node.value+" ");
                        mostRight.right = null;
                        node = node.right;
                    }
                }
            }

            System.out.println();
        }

        public void delete(int value){
            /**
             * 删除操作情况：
             * 如果这个节点没有子节点，直接删除
             * 如果这个节点有且只有一个子树，将这个子树代替这个节点
             * 如果这个节点有左右两个子树，找到这个节点的右子树的最小节点，或者左子树的最大节点
             * 将这个节点的值替换为这个选定节点，然后删除这个选定节点
             */

            Node pre =root;
            Node nowNode = root;

            while(nowNode != null){

                if(nowNode.value == value){

                    break;
                }else if(nowNode.value > value){

                    pre = nowNode;
                    nowNode = nowNode.left;
                }else{

                    pre = nowNode;
                    nowNode = nowNode.right;
                }
            }

            if(nowNode == null){

                return ;
            }

            if(nowNode.left == null && nowNode.right == null){

                if(nowNode == root){

                    root = null;
                }

                if(pre.left == nowNode){

                    pre.left = null;
                }else{

                    pre.right = null;
                }

                return ;
            }

            //以下是左树存在或者右树存在
            if(nowNode.right==null){
            //左树存在
                if(pre.left == nowNode){

                    pre.left = nowNode.left;
                }else{

                    pre.right = nowNode.left;
                }
            }else if(nowNode.left == null){

                if(pre.left == nowNode){

                    pre.left = nowNode.right;
                }else{

                    pre.right = nowNode.right;
                }
            }else{
                //都存在则选取大于这个点的最小值或者小于这个点的最大值，交换，删除
                nowNode.value = getRightMinAndDelete(nowNode);
            }

        }

        private int getRightMinAndDelete(Node node){

            Node pre = node;
            while(node.left != null){

                pre = node;
                node= node.left;
            }

            if(pre == node){

                pre.right = null;
            }

            pre.left = null;

            return node.value;
        }

        public int getSmallerMax(int value){

            Node nowNode = root;
            int result = nowNode.value;
            while (nowNode!=null){

                if(value > nowNode.value){

                    result = nowNode.value;
                    //当前节点比这个节点小的时候,可能更大的在右边
                    nowNode = nowNode.right;
                }else{

                    nowNode = nowNode.left;
                    //这个情况中考虑了等于的情况，等于则找更小，向左移动
                }
            }

            return result;
        }

        public int getBiggerMin(int value){

            Node nowNode = root;
            int result = nowNode.value;
            while (nowNode!=null){

                if(value < nowNode.value){

                    result = nowNode.value;
                    //当前节点比这个节点大的时候,可能更小的在左边
                    nowNode = nowNode.left;
                }else{

                    nowNode = nowNode.right;
                    //这个情况中考虑了等于的情况，等于则找更大，向有移动
                }
            }

            return result;
        }

        public int getTime(int value){

            Node nowNode = root;

            while(nowNode != null){

                if(nowNode.value == value){

                    return nowNode.time;
                }

                if(nowNode.value > value){

                    nowNode = nowNode.left;
                }else{

                    nowNode = nowNode.right;
                }
            }

            return 0;
        }

        public boolean search(int value){

            Node nowNode = root;

            while(nowNode != null){

                if(nowNode.value == value){

                    return true;
                }

                if(value < nowNode.value){

                    nowNode = nowNode.left;
                }else{

                    nowNode = nowNode.right;
                }
            }

            return false;
        }


        private void insert(int value){

            Node nowNode = root;

            while(true){

                if(nowNode.value == value){

                    nowNode.time++;
                    return;
                }else if(value > nowNode.value){

                    if(nowNode.right == null){

                        nowNode.right = new Node(value);
                        break;
                    }else{

                        nowNode = nowNode.right;
                    }
                }else{

                    if(nowNode.left == null){

                        nowNode.left = new Node(value);
                        break;
                    }else{

                        nowNode = nowNode.left;
                    }
                }
            }

        }

        private class Node{

            int value;
            int time;

            Node left;
            Node right;


            public Node(int value){

                this.value = value;
                this.time = 1;

                left = null;
                right =null;
            }
        }
    }
}
