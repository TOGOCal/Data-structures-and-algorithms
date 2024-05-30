import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 平衡二叉树 {

    /**平衡二叉树，也叫AVL tree
     * 平衡二叉树时建立在搜索二叉树的基础上的
     * 但是解决了搜索二叉树
     * 不能很好地处理数据本来有序的情况下
     * 导致树呈棒状结构从而导致搜索时间复杂度为O(N)的情况
     */

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("输如初始化的大小：");
        int n = s.nextInt();

        List<Integer> list = new ArrayList<>();

        for(int i=0;i<n;i++){

            list.add(s.nextInt());
        }
        s.nextLine();

        AVLTree tree = new AVLTree(list);

        char key = 0;

        while(key!='0'){

            System.out.println("请输入操作：1.插入 2.删除 3.查询是否有该元素 4.遍历 0.退出");

            key = s.nextLine().charAt(0);

            switch(key){

                case '1' ->{

                    System.out.println("请输入要插入的元素：");

                    tree.insert(s.nextInt());
                    s.nextLine();
                }

                case '2' ->{

                    System.out.println("请输入要删除的元素：");

                    if( tree.delete(s.nextInt()) ){

                        System.out.println("删除成功");
                    }else{

                        System.out.println("删除失败，该元素不存在");
                    }
                    s.nextLine();
                }

                case '3' ->{

                    System.out.println("请输入要查询的元素：");
                    if(tree.exists(s.nextInt())){

                        System.out.println("存在");
                    }else{

                        System.out.println("不存在");
                    }

                    s.nextLine();
                }

                case '4' -> tree.MorrisMiddleTraversal();

                default -> key = '0';
            }
        }

        s.close();

    }

    public static class AVLTree{

        Node head;

        AVLTree(List<Integer> list){

            for(int i:list){

                insert(i);
            }
        }

        public void MorrisMiddleTraversal(){

            Node cur = head;

            Node mostRight;

            while(cur!=null){

                if(cur.left==null){

                    System.out.print(cur.value+" ");
                    cur = cur.right;
                }else{

                    mostRight = cur.left;
                    while(mostRight.right!=null && mostRight.right!=cur){

                        mostRight = mostRight.right;
                    }

                    if(mostRight.right == null){

                        mostRight.right = cur;
                        cur = cur.left;
                    }else{

                        System.out.print(cur.value+" ");
                        mostRight.right = null;
                        cur = cur.right;
                    }
                }
            }
            System.out.println();
        }

        public boolean exists(int value){

            Node cur = head;

            while(cur!=null){

                if(value < cur.value){

                    cur= cur.left;
                }else if(value > cur.value){

                    cur = cur.right;
                }else {

                    return true;
                }
            }

            return false;
        }

        public void insert(int value){

            Node newNode = new Node(value);

            Node current = head;
            Node pre = null;
            while(current!=null){

                pre = current;

                if(value < current.value){

                    current = current.left;
                }else if(value > current.value){

                    current = current.right;
                }else{
                    return;//不考虑相同的情况，如果出现了相同就忽略
                }
            }

            if(pre == null){

                head = newNode;
            }else if(value < pre.value){

                pre.left = newNode;
            }else{

                pre.right= newNode;
            }

            newNode.parent = pre;

            checkAndRevise(newNode);
        }

        public boolean delete(int value){

            Node cur = head;

            while(cur!=null){

                if(value < cur.value){

                    cur = cur.left;
                }else if(value > cur.value){

                    cur = cur.right;
                }else{

                    break;
                }
            }

            if(cur == null){

                return false;
            }


            if(cur.left==null && cur.right==null){

                Node parent = cur.parent;

                if(parent == null){
                    //现在左右头都是空，说明这个树中只有者一个点，之后head只能指向null了
                    head = null;
                }else if(parent.left == cur){

                    parent.left = null;
                }else{

                    parent.right = null;
                }

                checkAndRevise(parent);//对parent产生影响
            }else if(cur.left==null){
                //右树存在
                Node parent = cur.parent;

                Node right = cur.right;

                right.parent = parent;

                if(parent == null){

                    head = right;
                }else if(parent.left == cur){

                    parent.left = right;
                }else {

                    parent.right = right;
                }

                checkAndRevise(parent);
            }else if(cur.right==null){

                Node parent = cur.parent;

                Node left = cur.left;

                left.parent = parent;

                if(parent == null){

                    head = left;
                }else if(parent.left == cur){

                    parent.left = left;
                }else {

                    parent.right = left;
                }

                checkAndRevise(parent);
            }else{

                //左右都存在，找到右树中最小的
                cur.value = getRightMinAndDelete(cur);
                //由于在getRightMinAndDelete中调用了delete这个本方法，检查过了height的情况，所以这里不用check了
            }

            return true;
        }

        int getRightMinAndDelete(Node node){
            //由于if判定，这里右树是肯定存在的

            Node cur = node.right;
            while(cur.left!=null){

                cur = cur.left;
            }

            delete(cur.value);
            return cur.value;
        }

        void updateHeight(Node node){
            /**
             * 明显的，一个节点的高度发生改变只会对其上面的所有节点产生印象，所以同样的，
             * 只需要传入一个节点，向上遍历即可
             */
            int left = (node.left == null ? 0 : node.left.height);
            int right = (node.right == null ? 0 : node.right.height);

            node.height = Math.max(left, right)+1;

            Node parent = node.parent;
            while(parent!=null){

                parent.height = Math.max(parent.left == null ? 0 : parent.left.height, parent.right == null ? 0 : parent.right.height)+1;
                parent = parent.parent;
            }
        }

        void checkAndRevise(Node node){
            /**
             * 参数分析：
             * 会导致平衡二叉树不平衡的操作是加入和删除
             * 加入时，会对加入的这个节点所在的子树的平衡性产生影响
             * 删除时，会对删除的这个节点所在的子树的平衡性产生影响
             *
             * 因此，加入操作后，传入新产生的节点的指针，向上遍历每个节点是否符合要求
             * 删除操作后，传入被删除的节点所在的子树的根节点，向上遍历每个节点是否符合要求
             * （删除只需要删除指向这个点的指针就行了，JVM之后会将其自动清除（应该，等我学了JVM调优再说
             *
             * 失去平衡的类型：
             * ll，lr，rl，rr(新增的节点插入到这些位置导致的
             * ll：
             *       左子树的左子树：单独右旋node即可
             * lr：
             *       左子树的右子树：先左旋node的左节点，再右旋node（目的：使得左子树的右节点充当node的位置
             * rl：
             *       右子树的左子树：先右旋node的右节点，再左旋node（目的：使得右子树的左节点充当node的位置
             * rr：
             *       右子树的右子树：单独左旋node即可
             */

            Node nowNode = node;
            while(nowNode != null){

                int leftHeight = nowNode.left == null ? 0 : nowNode.left.height;
                int rightHeight = nowNode.right == null ? 0 : nowNode.right.height;

                int diff = leftHeight - rightHeight;

                if(diff > 1){//左树过高

                    //diff大于0，但是rightHeight不可能为负数，所以leftHeight一定为正数，所以nowNode.left存在，可忽略此处警告
                    int different = (nowNode.left.left == null ? 0 : nowNode.left.left.height) -
                                         (nowNode.left.right == null ? 0 : nowNode.left.right.height);

                    if(different >= 0){//ll:左子树的左子树
                        rightRevolve(nowNode);
                    }else{//lr

                        leftRevolve(nowNode.left);
                        rightRevolve(nowNode);
                    }

                }else if(diff < -1){//右树过高

                    int different = (nowNode.right.left == null ? 0 : nowNode.right.left.height) -
                                        (nowNode.right.right == null ? 0 : nowNode.right.right.height);

                    if(different >= 0){//rl

                        leftRevolve(nowNode);
                    }else{//rr

                        rightRevolve(nowNode.right);
                        leftRevolve(nowNode);
                    }
                }

                nowNode = nowNode.parent;//向上检查
            }
        }

        void leftRevolve(Node node){
            /**
             * 左旋：某个点向左倒
             * 潜在导致原因：右子树过长，所以右子树不是空
             * 将其右节点作为新根，
             * 原右节点的右子树继续挂在右边
             * 原右子树的左子树由于全部大于node中的值，所以挂在node的右边
             */
            Node parent = node.parent;

            Node right = node.right;

            node.right = right.left;
            right.left.parent = node;

            right.left = node;
            node.parent = right;

            right.parent = parent;
            if(parent !=null){

                if(parent.left == node){
                    parent.left = right;
                }else{
                    parent.right = right;
                }
            }

            updateHeight(node);//调整后，node发生改变，但同时原右跑到上面去了，但是更新的时候检查到了，所以完美

        }

        void rightRevolve(Node node){

            Node parent = node.parent;

            Node left = node.left;

            node.left = left.right;
            left.right.parent = node;

            left.right = node;
            node.parent = left;

            left.parent = parent;

            if(parent !=null){

                if(parent.left == node){
                    parent.left = left;
                }else{
                    parent.right = left;
                }
            }

            updateHeight(node);
        }

        private class Node{

            int value;
            int height;

            Node parent;

            Node left;
            Node right;

            Node(int value){

                this.value = value;

                height = 1;

                parent = null;
                left = null;
                right = null;
            }
        }

    }
}
