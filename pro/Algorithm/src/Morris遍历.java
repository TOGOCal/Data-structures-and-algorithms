public class Morris遍历 {

    /**
     * 遍历特点：
     * 时间复杂度N，空间复杂度1
     *
     * 遍历规则：
     * cur来到当前节点，（最开始来到头节点
     * 如果cur没有左节点，则cur向右移动（在这一步可能执行返回操作
     * 如果cur有左节点，就找到左子树中最右的节点mostRight，
     *  如果mostRight的右指针指向空，则将其指向cur，cur向左移动
     *  如果mostRight的右指针指向cur，则将其指向null，cur向右移动
     * 当cur指向null的时候结束遍历
     *
     * 遍历流程分析：
     *  对于之前所学的递归遍历，每个节点会被访问三次
     *  而走向左树和右树又返回该界定的方式是通过栈的记忆保存功能实现的（递归函数的系统压栈
     *  而对Morris算法， 返回的方式是将所有左树的最右节点连接到返回节点的方式实现的节点返回
     *  而当cur访问到某棵子树的mostRight节点之后就说明这整棵左子树都访问完了，
     *  此时再返回这棵左子树的头节点，向右进行遍历
     *
     * 时间复杂度分析：
     *  没有左子树的点被访问1次，有左子树的点被访问两次（会会去一次 ，假设所有节点都有左子树的情况也只是2N
     *  有左子树的点会找左子树的最右节点，但需要注意的是，对于每个节点而言，遍历右边界寻找最右节点时
     *  每次每个需要遍历的节点遍历的都是不一样的节点，所以整体遍历的点可以认为规模逼近整棵树N
     *  而有左子树的点会被到达两次，也就是2N
     *  总体相加时，时间复杂度4N = O(N)，时间复杂度O(N)
     *
     * 使用Morris实现先序，中序，后序遍历的思想：
     *      有左子树的点会到达两次，没有左子树的点只会到达一次，
     *      先序遍历：头左右
     *      所以有左子树的点第一次到达时进行打印，第二次到达不进行打印，没有左子树的点直接进行打印
     *      中序遍历：左头右
     *      有左子树的点第二次到达时打印，没有左子树的点直接打印
     *      后续遍历：左右头
     *      当某个节点被第二次访问到的时候，逆序打印其左子树所有右边界，最后单独打印整棵树右边界
     *      一个节点知不知道自己的性质：知道
     *      没有左节点就是没有左子树的，只能到达一次
     *      有左节点但是左节点最右节点的右指针指向空的时候是能够到达两次的点的第一次到达
     *      有左节点且左子树最右节点的右指针指向节点本身的时候是能够到达两次的节点的第二次到达
     */

    public static void main(String[] args) {

    }

    public static void MorrisFirst(MyTree tree){

        Node cur = tree.head;

        Node mostRight = null;

        while(cur != null){

            if(cur.left !=null){

                mostRight = cur.left;

                while(mostRight.right != null && mostRight.right != cur){

                    mostRight=mostRight.right;
                }

                if(mostRight.right == cur){

                    mostRight.right = null;
                }else{

                    System.out.print(cur.value + " ");//第一次到达的时候打印
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }

            }else{

                System.out.print(cur.value + " ");
            }

            cur = cur.right;
        }
    }

    public static void MorrisMiddle(MyTree tree){

        Node cur = tree.head;

        Node mostRight = null;

        while(cur != null){

            if(cur.left != null){

                mostRight = cur.left;
                while(mostRight.right != null && mostRight.right != cur){

                    mostRight=mostRight.right;
                }

                if(mostRight.right == cur){

                    System.out.println(cur.value + " ");//第二次到达的时候打印
                    mostRight.right = null;
                }else{

                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }

            }else{

                System.out.print(cur.value + " ");
            }

            cur = cur.right;
        }
    }

    public static void MorrisLast(MyTree tree){

        Node cur = tree.head;

        Node mostRight = null;

        while(cur != null){

            if(cur.left != null){

                mostRight = cur.right;

                while(mostRight.right != null && mostRight.right != cur){

                    mostRight=mostRight.right;
                }

                if(mostRight.right == cur){

                    mostRight.right = null;
                    //第二次到达某个节点的时候 逆序打印 其左子树右边界
                    //注意：不打印当前节点

                    while(mostRight!=cur){

                        System.out.print(mostRight.value + " ");
                        mostRight=mostRight.parent;
                    }
                }else{

                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }
            }

            cur = cur.right;
        }

        cur = tree.head;
        mostRight = cur;

        while(mostRight.right != null){

            System.out.print(mostRight.value + " ");
            mostRight = mostRight.right;
        }//单独打印整棵树的右边界
    }

    public static void MorrisLastPro(MyTree tree){
        //之前的逆序打印操作是在二叉树节点有往回指的指针的时候能够使用
        //这个方法解决的是如何在没有往回指的指针的时候进行右边界的逆序打印

        Node cur = tree.head;
        Node mostRight = null;

        while(cur != null){

            if(cur.left != null){

                while(mostRight.right != null && mostRight.right != cur){

                    mostRight=mostRight.right;
                }

                if(mostRight.right == cur){//第二次到达

                    mostRight.right = null;
                    //此时使用的知识点：单链表的反转，使用后再反转回来//以下单独构建函数
                    singleLinkedListRollbackPrint(cur.left , mostRight);
                }else{

                    mostRight.right = cur;
                    cur = cur.left;
                }
            }

            cur = cur.right;
        }

        cur = tree.head;
        mostRight = cur;
        while(mostRight.right != null){

            mostRight = mostRight.right;
        }

        singleLinkedListRollbackPrint(cur, mostRight);//单独打印右边界

    }

    public static void singleLinkedListRollbackPrint(Node leftNode ,Node mostRight){
        //不要这个mostRight也行，到时候记一下就行了
        //要执行打印的是某个节点的左子树的头节点到最右节点这个右边界的逆序以及使用完之后的倒转
        Node pre = null;
        Node cur = leftNode;
        while(cur != null){

            Node next = cur.right;
            cur.right = pre;
            pre = cur;
            cur = next;
        }//完成了所有的反转

        cur = mostRight;//此时开始倒转
        pre =null;
        while(cur != null){

            System.out.print(cur.value + " ");
            Node next = cur.right;
            cur.right = pre;
            pre = cur;
            cur = next;
        }//如此便完成了反转后打印

    }

    public static void Morris(MyTree tree){

        Node cur = tree.head;
        Node mostRight = null;

        while(cur != null){

            if(cur.left != null){//当左子树为空的时候，if不进去，执行下面的cur向右移动的操作

                mostRight = cur.left;

                while(mostRight.right != null && mostRight.right != cur){

                    mostRight = mostRight.right;
                }//寻找到左子树中的最右节点（不能等于现在的节点

                if(mostRight.right != null){

                    mostRight.right = null;
                    //cur = cur.right;放到后面去执行了
                }else{

                    mostRight.right = cur;
                    cur = cur.left;
                    continue;//为了不执行下面的向右移动的操作
                }//如果最右节点的右节点为空，则将其指向cur,cur向左移动
            }

            cur = cur.right;
        }
    }

    public static class MyTree{

        Node head;

        MyTree(){

            head = new Node(0);
        }
    }

    public static class Node{

        int value;
        Node left;
        Node right;

        Node parent;

        Node(int value) {

            this.value = value;
            left = null;
            right = null;
            parent = null;
        }
    }

}
