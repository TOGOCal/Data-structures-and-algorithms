import java.util.*;

public class SBTree {
    /**
     * size balance tree
     * 以树的大小作为平衡判断标准
     * 任何一个节点的大小 不小于其侄子中更大的那个（侄子节点：兄弟的子节点）
     *
     * 与AVL树之间的不同点：
     * 在进行左旋右旋操作时要对所有子节点发生改变的节点进行递归检查
     */

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("请输入初始节点个数：");
        int num = s.nextInt();

        List<Integer> list = new ArrayList<>();

        for(int i = 0;i < num;i++){

            list.add(s.nextInt());
        }
        s.nextLine();

        SizeBalanceTree sbTree = new SizeBalanceTree(list);

        char key = 0;

        while(key !='0'){

            System.out.println("请输入操作指令：1.插入 2.删除 3.查找 4.遍历 5.检查树 0.退出");

            key = s.nextLine().charAt(0);

            switch (key){

                case '1'->{

                    System.out.println("请输入要插入的节点：");

                    sbTree.insert(s.nextInt());
                    s.nextLine();
                }

                case '2'->{

                    System.out.println("请输入要删除的节点：");

                    sbTree.delete(s.nextInt());
                    s.nextLine();
                }

                case '3' ->{

                    System.out.println("请输入要查找的节点：");

                    System.out.println("是否存在：" + sbTree.exists(s.nextInt()));
                    s.nextLine();
                }

                case '4' -> sbTree.MorrisMiddleTraversal();

                case '5' -> System.out.println("树是否平衡：" + sbTree.checkTree());

                default -> key = '0';
            }
        }

        s.close();

    }

    public static class SizeBalanceTree {

        Node head;

        public boolean checkTree(){

            if(head == null){

                return true;
            }

            Node node = head;

            Queue<Node> queue = new LinkedList<>();
            queue.add(node);

            while(!queue.isEmpty()){

                Node cur = queue.poll();

                if(cur.left!=null && cur.right !=null){

                    if(cur.right.size < (cur.left.left == null? 0 : cur.left.left.size) ||

                    cur.right.size < (cur.left.right == null? 0 : cur.left.right.size) ||

                    cur.left.size < (cur.right.left == null? 0 : cur.right.left.size) ||

                    cur.left.size < (cur.right.right == null? 0 : cur.right.right.size) ){

                        return false;
                    }
                }

                if(cur.left!=null){

                    queue.add(cur.left);
                }

                if(cur.right!=null){

                    queue.add(cur.right);
                }

            }

            return true;
        }

        SizeBalanceTree(List<Integer> list){

            if(list == null){

                return;
            }

            for(int value:list){

                insert(value);
            }
        }

        public boolean exists(int value){

            Node node = head;

            while(node != null){

                if(node.value == value){

                    return true;
                }

                if(value > node.value){

                    node = node.right;
                }else {

                    node = node.left;
                }
            }

            return false;
        }

        public void MorrisMiddleTraversal(){

            Node cur = head;

            Node mostRight;

            while(cur != null){

                if(cur.left == null){

                    System.out.print(cur.value + " ");
                    cur = cur.right;
                }else {
                    mostRight = cur.left;
                    while(mostRight.right != null && mostRight.right != cur){

                        mostRight = mostRight.right;
                    }

                    if(mostRight.right == null){

                        mostRight.right = cur;
                        cur = cur.left;
                    }else {

                        System.out.print(cur.value + " ");
                        mostRight.right = null;
                        cur = cur.right;
                    }

                }
            }

            System.out.println();
        }

        public void insert(int value){

            if(head == null){

                head = new Node(value);
                return;
            }

            Node node = head;

            while(true){

                if(node.value == value){

                    return;
                }

                if(value > node.value){

                    if(node.right == null){

                        Node newNode = new Node(value);

                        newNode.parent = node;
                        node.right = newNode;

                        Node check = newNode;

                        while(check!=null){

                            check = check(check);
                            check = check.parent;
                        }//向上进行检查

                        return ;
                    }

                    node = node.right;
                }else{

                    if(node.left == null){

                        Node newNode = new Node(value);

                        newNode.parent = node;
                        node.left = newNode;

                        Node check = newNode;

                        while(check!=null){

                            check = check(check);
                            check = check.parent;
                        }//向上进行检查

                        return ;
                    }

                    node = node.left;
                }
            }
        }

        public void delete(int value){

            Node node = head;

            while(node!=null){

                if(node.value == value){

                    break;
                }else if(value > node.value){

                    node = node.right;
                }else{

                    node = node.left;
                }
            }

            if(node == null){

                System.out.println("不存在该节点，删除失败");
                return ;
            }

            if(node.left != null && node.right != null){
                //两边都有
                Node change = getRightMin(node);

                node.value = change.value;

                Node pre = change.parent;

                if(pre.left == change){

                    pre.left = null;
                }else {

                    pre.right = null;
                }

                check(pre);

            }else if(node.left != null){
                //左边有
                Node pre = node.parent;

                if(pre == null){

                    head = node.left;
                    head.parent = null;
                }else {

                    if(pre.left == node){

                        pre.left = node.left;
                        node.left.parent = pre;
                    }else {

                        pre.right = node.left;
                        node.left.parent = pre;
                    }

                    check(pre);
                }

            }else if(node.right != null){
                //右边有

                Node pre = node.parent;

                if(pre == null){

                    head = node.right;
                    head.parent = null;
                }else {

                    if(pre.left == node){

                        pre.left = node.right;
                        node.right.parent = pre;
                    }else {

                        pre.right = node.right;
                        node.right.parent = pre;
                    }

                    check(pre);
                }

            }else {
                //两边都没有
                Node pre = node.parent;

                if(pre == null){

                    head = null;
                }else {

                    if(pre.left == node){

                        pre.left = null;
                    }else {

                        pre.right = null;
                    }

                    check(pre);
                }
            }

        }

        private Node rightRotate(Node node){//返回新的头

            Node left = node.left;
            Node lr = left.right;

            Node pre = node.parent;

            left.parent = node.parent;
            if(pre != null){

                if(left.value < pre.value){

                    pre.left = left;
                }else{

                    pre.right = left;
                }
            }else{

                head = left;
            }

            left.right = node;
            node.parent = left;

            node.left = lr;
            if(lr != null){

                lr.parent = node;
            }

            /**
             * 子节点发生变化的节点：node：node.left = lr
             *                   left:left.right = node
             * 对这两个点进行检查
             */
            node.size = (node.left == null ? 0 : node.left.size)+
                    (node.right == null ? 0 : node.right.size)+1;

            left.size = (left.left == null ? 0 : left.left.size)+
                    (left.right == null ? 0 :left.right.size)+1;
            check(node);
            check(left);

            return left;
        }

        private Node leftRotate(Node node){//返回新的头

            Node right = node.right;
            Node rl = right.left;

            Node pre = node.parent;

            right.parent = node.parent;
            if(pre != null){

                if(right.value < pre.value){

                    pre.left = right;
                }else{

                    pre.right = right;
                }
            }else{

                head = right;//发生变化
            }

            right.left = node;
            node.parent = right;

            node.right = rl;
            if(rl != null){

                rl.parent = node;
            }

            /**
             * 发生子节点改变的节点：
             * node
             * right
             */

            node.size = (node.left == null ? 0 : node.left.size)+
                    (node.right == null ? 0 : node.right.size)+1;

            right.size = (right.left == null ? 0 : right.left.size)+
                    (right.right == null ? 0 : right.right.size)+1;

            right.left = check(node);//检查之后变成新的这个绝对位置的点
            right = check(right);

            return right;
        }

        private Node check(Node node){
            /**
             * 检查节点是否平衡:
             * 从当前节点开始，
             * 检查其左右两个节点的大小是否小于其侄子大小中的较大值
             * 旋转操作类似
             */

            if(node ==null){

                return null;
            }

            Node cur= node;

            if(cur.left!=null && cur.right !=null){

                if(cur.right.size < (cur.left.left == null? 0 : cur.left.left.size)){
                    //ll型：某节点左节点的左节点大小大于了其叔叔节点
                    cur = rightRotate(cur);
                }else if( cur.right.size < (cur.left.right == null? 0 : cur.left.right.size) ){
                    //lr型：某节点的左节点的右节点大小大于了其叔叔节点
                    cur.left = leftRotate(cur.left);
                    cur = rightRotate(cur);
                }else if(cur.left.size < (cur.right.left == null? 0 : cur.right.left.size)){
                    //rl型：某节点的右节点的左节点大小大于了其叔叔节点
                    cur.right = rightRotate(cur.right);
                    cur = leftRotate(cur);
                }else if(cur.left.size < (cur.right.right == null? 0 : cur.right.right.size)){
                    //rr型：某节点的右节点的右节点大小大于了其叔叔节点
                    cur = leftRotate(cur);
                }

                cur.size = cur.left.size + cur.right.size + 1;//保险起见，还是检查一下
            }else if(cur.left !=null){

                cur.size = cur.left.size + 1;
            }else if(cur.right !=null){

                cur.size = cur.right.size + 1;
            }

            return cur;
        }

        private Node getRightMin(Node cur){

            Node node = cur.right;

            while(node.left!=null){

                node = node.left;
            }

            return node;
        }


        private class Node{

            int value;
            int size;

            Node parent;
            Node left;
            Node right;


            public Node(int value){

                this.value = value;

                this.size = 1;

                this.parent = null;
                this.left = null;
                this.right = null;
            }
        }
    }
}
