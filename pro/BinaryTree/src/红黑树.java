import java.util.*;

public class 红黑树 {

    /**
     * 红黑树是一种搜索二叉树，（具有搜索二叉树左<中<右的性质
     * 红黑树额外性质：
     * 1.根节点和所有叶子节点都是黑色，注：红黑树中所有的叶子节点都是指的是空节点（将本来是叶子节点的加上两个null节点
     * 2.每个红色节点的两个子节点都是黑色，也就是说，红黑树中不能有两个连续的红色节点
     * 3.任意节点到其每一个叶子节点的所有路径都包含相同数目的黑色节点
     *
     * 节点插入：
     *      插入节点默认插入红色节点（如果插入黑色节点必然违反 性质3
     *      插入红色节点可能违反 性质2，所有之后需要进行调整
     *      插入节点可能违反的性质：
     *          1.插入节点是根节点 ->直接将其变黑
     *          违反性质2：（由于违反性质2，所以父必是红色，，爷爷必不是红色（黑色
     *          //所以父节点和爷爷都一定不是空
     *          2.插入节点的叔叔节点是红色：将爷爷变红，父与叔叔变黑，cur指针指向爷爷，让爷爷作为新插入的节点进行判定
     *          3.插入节点的叔叔是黑测：需要进行插入节点到爷爷节点的位置判定（同AVL树，有LL，LR，RL，RR四种类型
     *          具体旋转策略与AVL相同，LL变换时，爷爷节点被换到了父节点的下面，此时将爷爷变红，父节点变黑
     *                              LR变换时，先对父节点进行左旋，在对爷爷节点进行右旋（目的：将cur节点换上去），在将cur变黑，爷爷变红
     *                              RL变换时，类似与LR
     *                              RR变换时，同LL，爷爷变红，父节点变黑
     *
     * 节点删除：
     *      删除过程与平衡二叉树相同相同，只是需要额外处理（判定是否违规
     *          【1.左右两棵子树都是null 2.有一颗树 3.有两棵树】
     *          由于在搜索二叉树中的学习我们可以知道，3情况最终会转化成1，2两种情况进行考虑
     *          所以删除操作只需要考虑1，2两种情况就可以了
     *          1.删除节点是红节点：直接删除
     *            删除节点是黑节点：---------------》
     *              (1.删除节点的兄弟是黑色：
     *                  {1:兄弟至少有一个红孩子：设该红孩子为r，兄弟节点为s，父节点为p
     *                  分析p s r属于LL，LR，RL，RR的哪一种
     *                      注：如果有两个红孩子统一按照LL，RR处理
     *                      LL：r变成s的颜色，s变成p的颜色，p变黑，右旋p
     *                      RR：变色，左旋p，
     *                      LR：将r变p的颜色，p变成黑色，左旋s节点，右旋p节点，目的：使r变成p的位置
     *                      RL：将r变p的颜色，p变成黑色，右旋s节点，左旋p节点，目的：使r变成p的位置
     *                      （变换策略与AVL树相同
     *                  {2.兄弟节点两个都是黑节点：
     *                      删除节点的父节点是p，兄弟s
     *                      s节点变红，cur指针移动到p，
     *                      如果p是红色节点，直接将其变黑即可，如果是根节点，直接终止就行
     *                      如果p是黑色节点，将其视为删除节点，再进行判定，（返回上面---------》标记处）进行递归
     *              (2.删除节点的兄弟是红色
     *                   删除节点的父节点是p，兄弟s
     *                   将交换s，p的颜色，p节点朝删除节点的方向进行旋转（在左子树就左旋。。。
     *                   cur指针保持不变，在进行判定，（返回上面----------》标记处）进行递归
     *          2.由于红黑树的性质可以做以下判定：
     *              (1.只有一边的节点一定不是红节点，原因：红节点子节点一定不是红色的，而如果是黑色的则从该红节点位置分析违反了性质3
     *              ->这个节点一定是黑色的
     *              (2.这个节点左右之可能有一个节点，不可能是一个树，原因：违反性质3
     *              ->左右一定只有一个节点，且这个节点是红色的
     *          所以删除时，直接代替变黑就行了
     *
     * 由于这些性质，可以保证
     *      最长路径不超过最短路径的两倍（最短：全是黑，最长：红，黑交替）
     */

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("请输入节点个数：");
        int n= s.nextInt();

        List<Integer> list = new ArrayList<>();

        for(int i = 0;i < n;i++){

            list.add(s.nextInt());
        }
        s.nextLine();

        RBTree rbTree = new RBTree(list);

        char c = 0;

        while(c!='0'){

            System.out.println("请输入操作：");
            System.out.println("1.插入节点 2.删除节点 3.查询存在 4.打印 5.检查树是否正确 0.退出");

            c = s.nextLine().charAt(0);

            switch (c) {
                case '1' ->{
                    System.out.println("请输入要插入的节点：");
                    rbTree.insert(s.nextInt());
                    s.nextLine();
                }

                case '2' ->{

                    System.out.println("请输入要删除的节点：");
                    rbTree.delete(s.nextInt());
                    s.nextLine();
                }

                case '3' ->{

                    System.out.println("请输入要查询的节点：");

                    System.out.println("存在:"+rbTree.exist(s.nextInt()));
                    s.nextLine();
                }

                case '4' -> rbTree.MorrisMiddleTraversal();

                case '5' -> System.out.println(rbTree.check());

                default -> c = '0';

            }
        }

        s.close();
    }

    public static class RBTree {

        Node head;

        public boolean check(){
            //检查红黑树是否构建正确的代码

            boolean key = true;

            //1.检查双红
            Node cur = head;
            Queue<Node> q = new LinkedList<>();
            q.add(cur);

            List<Node> list = new ArrayList<>();

            Node pre = null;
            while(!q.isEmpty()){

                cur= q.poll();
                if(cur.parent ==pre){

                    System.out.println();
                    pre = cur;
                }

                System.out.print(cur.value+":"+cur.color+" ");

                if(cur.left!=null){

                    if(cur.color && cur.left.color){
                        //两个红
                        System.out.println("两个红");
                        return false;
                    }

                    q.add(cur.left);
                }

                if(cur.right!=null){

                    if(cur.color && cur.right.color){
                        //两个红
                        System.out.println("两个红");
                        return false;
                    }

                    q.add(cur.right);
                }

                if(cur.left == null && cur.right == null){

                    list.add(cur);//存下每个最后的节点
                }
            }

            //2.检查每条路是否正确
            int result = 0;

            Node cur1 = list.get(0);
            while (cur1!=null){

                if(!cur1.color){

                    result++;
                }
                cur1 = cur1.parent;
            }

            for(int i = 1;i<list.size();i++){
                int check = 0;
                Node node = list.get(i);
                while (node!=null){

                    if(!node.color){

                        check++;
                    }
                    node = node.parent;
                }

                if(result!=check){

                    System.out.println(check+":"+result+"不相等");
                    return false;
                }
            }

            return key;
        }

        public RBTree(List<Integer> list){

            for(int i : list){

                insert(i);
            }
        }

        public void leftRotate(Node cur){

            Node pre = cur.parent;

            Node right = cur.right;

            Node rl = right.left;

            right.left = cur;
            cur.parent = right;

            cur.right = rl;
            if(rl != null){
                rl.parent = cur;
            }

            right.parent = pre;
            if(pre != null){

                if(right.value < pre.value){

                    pre.left= right;
                }else{

                    pre.right = right;
                }
            }else{

                head = right;
            }
        }

        public void rightRotate(Node cur){

            Node pre = cur.parent;

            Node left = cur.left;

            Node lr = left.right;

            left.right = cur;
            cur.parent = left;

            cur.left = lr;
            if(lr != null){

                lr.parent = cur;
            }


            left.parent = pre;
            if(pre != null){

                if(left.value < pre.value){

                    pre.left= left;
                }else{

                    pre.right = left;
                }
            }else{

                head = left;
            }
        }

        public void delete(int value) {

            Node cur = head;

            while (cur != null) {

                if (cur.value == value) {

                    break;
                }

                if (value < cur.value) {

                    cur = cur.left;

                } else {

                    cur = cur.right;
                }
            }

            if (cur == null) {

                return;
            }

            if(cur.left != null && cur.right != null){
                //两边都不空。则找到rightMin代替，并删去rightMin
                cur = getRightMin(cur);
            }

            //以下分析的是只有一边的情况
            Node pre = cur.parent;
            if(cur.left !=null){//分析过了，只有可能是一个节点,且这个节点必是红色

                if(cur.value < pre.value){
                    pre.left = cur.left;
                    cur.left.parent = pre;
                    pre.left.color = false;//完成代替
                }else{

                    pre.right = cur.left;
                    cur.left.parent = pre;
                    pre.right.color = false;//完成代替
                }
            }else if(cur.right !=null){

                if(cur.value < pre.value){

                    pre.left = cur.right;
                    cur.right.parent = pre;
                    pre.left.color = false;//完成代替
                }else{

                    pre.right = cur.right;
                    cur.right.parent = pre;
                    pre.right.color = false;//完成代替
                }
            }else{
                //两边都是空
                checkDelete(cur);
            }


        }

        public Node getRightMin(Node thisNode){

            Node node = thisNode.right;
            while(node.left != null){

                node = node.left;
            }

            return node;
        }

        public void checkDelete(Node node){
            //进入这里的只有可能是无子树
            //  删除节点是红节点：直接删除
            Node pre = node.parent;
            if(node.color){
                //红节点则一定有pre节点
                if(node.value<pre.value){

                    pre.left = null;
                }else{

                    pre.right = null;
                }

                return ;
            }

            //删除节点是黑节点：---------------》单独开一个方法
            //调整完之后再进行删除操作
            deleteBlackCheck(node);
            if(node.value < pre.value){

                pre.left = null;
            }else{

                pre.right = null;
            }

        }

        public void deleteBlackCheck(Node node){

            Node pre = node.parent;

            Node s;
            String key;
            if(node.value < pre.value){

                key = "left";
                s = pre.right;
            }else{

                key = "right";
                s = pre.left;
            }

            Node p = pre;
            if(s.color){
            //  (2.删除节点的兄弟是红色删除节点的父节点是p，兄弟s

            //  将交换s，p的颜色，p节点朝删除节点的方向进行旋转（在左子树就左旋。。。
                boolean color = s.color;
                s.color = p.color;
                p.color = color;

                if(node.value < p.value){
                    //一定在左树
                    leftRotate(p);
                }else{

                    rightRotate(p);
                }
                // cur指针保持不变，在进行判定，（返回上面----------》标记处）进行递归
                deleteBlackCheck(p);

            }else{//(1.删除节点的兄弟是黑色：
                //兄弟节点为s，父节点为p

                if( (s.left == null || !s.left.color)&&(s.right == null || !s.right.color) ){//两个都是黑色
                //  2.兄弟节点两个都是黑节点：删除节点的父节点是p，兄弟s
                //  s节点变红，cur指针移动到p，
                    s.color = true;
                    if(p.color || p.parent == null){

                        p.color = false;
                        if(p.parent == null){

                            head = p;//保险起见
                        }
                        return;
                    }else{
                        //   如果p是黑色节点，将其视为删除节点，再进行判定，（返回上面---------》标记处）进行递归
                        deleteBlackCheck(p);
                    }

                }else{
                    //  {1:兄弟至少有一个红孩子：//设该红孩子为r，
                    String thisKey;
                    Node r ;
                    if(s.left == null || !s.left.color){
                        //只有右孩子是红色
                        r = s.right;
                        thisKey = "right";
                    }else if(s.right == null || !s.right.color){

                        r = s.left;
                        thisKey = "left";
                    }//只有一边的情况
                    else{//有两边，等会再决定
                        r =null;
                        thisKey = "notSure";
                    }
                    // 分析p s r属于LL，LR，RL，RR的哪一种
                    if(key.equals("right")){
                        //当node是在右边,
                        //代表s是在p的左边
                        if(thisKey.equals("notSure")){

                            thisKey = "left";
                            r = s.left;
                        }//按照ll处理

                        if(thisKey.equals("left")){
                            //情况ll
                            //  LL：r变成s的颜色，s变成p的颜色，p变黑，右旋p
                            r.color = s.color;
                            s.color = p.color;
                            p.color = false;
                            rightRotate(p);
                        }else{
                            //情况lr
                            // LR：将r变p的颜色，p变成黑色，左旋s节点，右旋p节点，目的：使r变成p的位置
                            r.color = p.color;
                            p.color = false;
                            leftRotate(s);
                            rightRotate(p);
                        }
                    }else{
                        if(thisKey.equals("notSure")){

                            thisKey = "right";
                            r = s.right;
                        }

                        if(thisKey.equals("right")){
                            // RR：变色，左旋p，
                            r.color = s.color;
                            s.color = p.color;
                            p.color = false;
                            leftRotate(p);
                        }else{
                            //  RL：将r变p的颜色，p变成黑色，右旋s节点，左旋p节点，目的：使r变成p的位置
                            r.color = p.color;
                            p.color = false;
                            rightRotate(s);
                            leftRotate(p);
                        }
                    }

                }
            }
        }

        public void MorrisMiddleTraversal(){

            Node cur = head;
            System.out.println(cur.value);

            Node mostRight;

            while(cur != null){

                if(cur.left == null){

                    System.out.print(cur.value+":"+cur.color+" ");
                    cur = cur.right;
                }else{

                    mostRight = cur.left;

                    while(mostRight.right != null && mostRight.right != cur){

                        mostRight = mostRight.right;
                    }

                    if(mostRight.right == null){

                        mostRight.right = cur;
                        cur = cur.left;
                    }else{

                        System.out.print(cur.value+":"+cur.color+" ");
                        mostRight.right = null;
                        cur = cur.right;
                    }
                }
            }
            System.out.println();
        }

        public boolean exist(int value){

            Node cur = head;

            while(cur != null){

                if(cur.value == value){

                    return true;
                }

                if(value < cur.value){

                    cur = cur.left;

                }else{

                    cur = cur.right;
                }
            }

            return false;
        }

        public void insert(int value){

            Node newNode = new Node(value);//默认设置好的了个是红色

            if(head == null){

                newNode.color = false;
                head = newNode;
                return;
            }//插入节点是根节点

            Node cur = head;
            Node pre = null;

            while(cur != null){

                if(cur.value == value){

                    return;
                }//相等就忽略这步操作

                pre = cur;

                if(value < cur.value){

                    cur = cur.left;

                }else{

                    cur = cur.right;
                }
            }//找到了即将要挂在的点pre

            newNode.parent = pre;

            checkInsert(newNode);

        }

        public void checkInsert(Node newNode){

            if(newNode.parent ==null){

                head = newNode;
                newNode.color = false;
                return;
            }

            Node pre = newNode.parent;
            int value = newNode.value;
            String key;
            if(value < pre.value){

                pre.left = newNode;
                key = "left";
            }else{

                pre.right = newNode;
                key = "right";
            }

//            3.插入节点的叔叔是黑色：需要进行插入节点到爷爷节点的位置判定（同AVL树，有LL，LR，RL，RR四种类型
//            具体旋转策略与AVL相同，
//            LL变换后，爷爷节点被换到了父节点的下面，此时将爷爷变红，父节点变黑
//            LR变换后，先对父节点进行左旋，在对爷爷节点进行右旋（目的：将cur节点换上去），在将cur变黑，爷爷变红
//            RL变换后，类似与LR
//            RR变换后，同LL，爷爷变红，父节点变黑
            Node grand = pre.parent;
            if(pre.color){
                //如果父节点的颜色也是红色的，就违反了性质2
                Node uncle = getUncle(pre,key);
                if(uncle == null||!uncle.color){
                    //null也是黑色的
                    if(pre.value < grand.value){

                        if(key.equals("left")){
                            //ll型,单独右爷爷
                            //LL变换后，爷爷节点被换到了父节点的下面，此时将爷爷变红，父节点变黑
                            rightRotate(grand);
                            grand.color = true;
                            pre.color = false;
                        }else{
                            //lr型,先左旋父，在右旋爷爷
                            //在将cur变黑，爷爷变红
                            leftRotate(pre);
                            rightRotate(grand);
                            newNode.color = false;
                            grand.color = true;
                        }
                    }else{

                        if(key.equals("left")){
                            //rl型.类似于LR
                            rightRotate(pre);
                            leftRotate(grand);
                            newNode.color = false;
                            grand.color = true;
                        }else{
                            //rr型 //爷爷变红，父节点变黑
                            leftRotate(grand);
                            grand.color = true;
                        }
                    }
                }else{
                    //2.插入节点的叔叔节点是红色：将爷爷变红，父与叔叔变黑，cur指针指向爷爷，让爷爷作为新插入的节点进行判定
                    grand.color = true;
                    pre.color = false;
                    uncle.color = false;
                    checkInsert(grand);
                }
            }
        }

        public Node getUncle(Node node,String position){

            Node pre = node.parent;
            if(position.equals("left")){

                return pre.right;
            }else{

                return pre.left;
            }
        }

        private class Node{

            int value;
            Node parent;
            Node left;
            Node right;

            boolean color;//红色为true，黑色为false

            public Node(int value){

                this.value= value;

                parent = null;
                left = null;
                right = null;
                color = true;//默认为红色
            }
        }
    }
}
