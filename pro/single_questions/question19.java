import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class question19 {

    //本题目的：复习有序表的使用以及跳表的构建
    /**
     * 问题：
     * 给定一个数组集合
     * 要求确定一个最小区间，使每个数组中至少有一个元素位于这个区间内
     * 如果有多个满足要求的最小区间（大小相同，取剋是位置小的哪个区间
     * 每个数组中元素各自有序
     */

    /**
     * 方法介绍：
     * 确定数据结构：节点Node
     * 包含信息：值，属于哪个数组，是这个数组的哪个位置
     * 有序表每次弹出最小值，与最大值进行计算得出区间长度
     * 最小值所在节点数组的系啊一个值进入有序表
     * 直到某个数组超出范围，结束遍历
     * 得出的最小范围就是最小范围
     *
     */




    class ResultInformation{

        int minRange;
        int beginIndex;
        int endIndex;


        public ResultInformation(int beginIndex, int endIndex) {

            this.beginIndex = beginIndex;
            this.endIndex = endIndex;

            minRange = endIndex - beginIndex;
        }


        public void update(int beginIndex, int endIndex) {

            if( endIndex - beginIndex < minRange){

                this.beginIndex = beginIndex;
                this.endIndex = endIndex;
                minRange = endIndex - beginIndex;
            }
        }
    }


    class Solution{
        //使用系统提供的有序表TreeMap
        //本来应该只用Node就行了，但是TreeMap必须添加键和值，那就选一个占用空间最小的boolean来做值了
        TreeMap<Node , Boolean> map;

        public ResultInformation getMinRange(ArrayList<int[]> arrays) {

            ResultInformation res = new ResultInformation(Integer.MIN_VALUE , Integer.MIN_VALUE);

            map = new TreeMap<>(new NodeComparator());

            for(int i = 0; i< arrays.size(); i++){

                int[] array = arrays.get(i);

                if(array.length != 0){

                    Node node = new Node(array[0] , i , 0);
                    map.put(node , true);
                }
            }//加入所有第一项

            while(true){

                //弹出第一个
                Node node = map.firstKey();
                //每弹出一个更新
                res.update(node.value , map.lastKey().value);


                int[] array = arrays.get(node.arrayIndex);

                int index = node.index;
                while(array[index] == node.value){

                    index++;
                    if(index == array.length){

                        //有一个超出就弹出
                        return res;
                    }
                }//弹出的第一个就是不同的值（防止相同的值加入TreeMap导致出错

                Node newNode = new Node(array[index] , node.arrayIndex , index);

                map.put(newNode, true);
                map.remove(node);

            }


        }

        class NodeComparator implements Comparator<Node> {

            @Override
            public int compare(Node o1, Node o2) {
                if(o1.value != o2.value){

                    //从小到大排序
                    return o1.value - o2.value;
                }

                if(o1.arrayIndex != o2.arrayIndex){

                    //从小到大排序
                    return o1.arrayIndex - o2.arrayIndex;
                }

                return o1.index - o2.index;
            }
        }




        class Node{

            int value;
            int arrayIndex;
            int index;

            Node(int value , int arrayIndex , int index) {

                this.value = value;
                this.arrayIndex = arrayIndex;
                this.index = index;
            }

        }


    }




    class Solution2{
        //通过手搓跳表代替TreeMap肺功能你


        public ResultInformation getMinRange(ArrayList<int[]> arrays) {

            SkipList sl = new SkipList();

            ResultInformation res = new ResultInformation(Integer.MIN_VALUE , Integer.MIN_VALUE);


            for(int i = 0; i< arrays.size(); i++){

                int[] array = arrays.get(i);

                if(array.length != 0){

                    sl.addNew(array[0] , i , 0);
                }
            }//加入所有第一项

            while(true){

                SkipList.PackageNode nodeFirst = sl.getFirst();
                SkipList.PackageNode nodeLast = sl.getLast();

                res.update(nodeFirst.value , nodeLast.value);

                //移出第一个
                sl.delete(nodeFirst.value);

                int[] array = arrays.get(nodeFirst.arrayIndex);

                int index = nodeFirst.index;

                while(array[index] == nodeFirst.value){

                    //保证不将同一个数组中的相同值加入
                    index++;

                    if(index == array.length){

                        return res;
                    }

                }

                sl.addNew(array[index] , nodeFirst.arrayIndex, index);

            }




        }




        class SkipList{

            PackageNode first;


            SkipList(){

                first = new PackageNode(Integer.MIN_VALUE , -1 ,-1);////得到头节点
            }


            public PackageNode getFirst(){

                PackageNode.Node point = first.stackTop;


                while(point.down!= null){

                    point = point.down;
                }

                return point.right.getThePackage();

            }

            public PackageNode getLast(){

                PackageNode.Node point = first.stackTop;


                while (!(point.right == null && point.down == null)){

                    if(point.right == null){

                        point = point.down;
                    }else{

                        point = point.right;
                    }
                }

                return point.getThePackage();

            }



            public void addNew(int value , int arrayIndex , int index){


                PackageNode newPackageNode = new PackageNode(value , arrayIndex , index);

                first.biggerSize(newPackageNode.size);//扩大范围

                //开始添加
                //height为此时指针的高度
                int height = first.size;

                //指针
                PackageNode.Node point = first.stackTop;//

                while(height > newPackageNode.size){

                    point = point.down;//向下移动直到与新节点等高
                    height -- ;
                }

                PackageNode.Node newPoint = newPackageNode.stackTop;

                while(height > 0 ){
                    //逐次完成每一层的填充
                    height--;

                    //
                    while (point.right!=null && point.right.getThePackage().value > value){

                        point = point.right;//找到不大于的第一个节点

                        if(point.getThePackage().value == value){
                            //当出现加入相同容的值的时候就要按照arrayIndex排序了

                            //在这里我们保证相同arrayIndex一定不同index
                            while(point.right!=null && point.right.getThePackage().arrayIndex > arrayIndex){

                                point = point.right;//找到不大于的第一个节点
                            }
                            break;
                        }

                    }


                    PackageNode.Node next = point.right;

                    point.right = newPoint;
                    newPoint.left = point;

                    newPoint.right = next;
                    if(next!=null){

                        next.left = newPoint;
                    }

                    point = point.down;
                    newPoint = newPoint.down;
                    //开始执行下一行的连接
                }

            }


            public boolean delete(int value){

                PackageNode packageNode = found(value);

                if(packageNode == null){

                    return false;
                }


                PackageNode.Node point = packageNode.stackTop;

                while(point!=null){
                    point = point.down;

                    PackageNode.Node pre = point.left;
                    PackageNode.Node next = point.right;


                    pre.right = next;
                    if(next!=null){

                        next.left =pre;
                    }

                }

                return true;
            }



            public boolean isExist(int value){

                return found(value) != null;
            }



            //找到对应节点
            public PackageNode found(int value){

                //此时指针位置
                PackageNode.Node nowPoint = first.stackTop;

                while(nowPoint.getThePackage().value!=value){

                    if(nowPoint.right == null){

                        //如果右边是空节点，向下移动
                        nowPoint = nowPoint.down;
                    }else if(nowPoint.right.getThePackage().value > value){

                        nowPoint = nowPoint.down;
                    }else{

                        nowPoint = nowPoint.right;//找到的是值相同的第一个节点
                    }

                    if(nowPoint == null){

                        //找到最底层都没有找到这个节点
                        return null;
                    }
                }

                return nowPoint.getThePackage();
            }



            class PackageNode{

                //最上方的节点
                Node stackTop;

                int value;
                int size;

                int arrayIndex;
                int index;


                PackageNode(int value , int arrayIndex , int index) {

                    this.value= value;
                    this.arrayIndex = arrayIndex;
                    this.index = index;

                    stackTop = new Node();


                    int size = 1;

                    while(Math.random() < 0.5){

                        size++;
                    }

                    this.size = size;

                    for(int i = 0 ;i< size-1 ;i++){

                        Node newNode = new Node();
                        newNode.down = stackTop;

                        stackTop = newNode;
                    }
                }


                //该方法只能由头节点调用
                public void biggerSize(int size){

                    while(this.size < size){

                        Node newNode = new Node();

                        newNode.down = stackTop;

                        stackTop = newNode;

                        this.size++;
                    }
                }



                class Node{

                    //Node up;
                    Node down;
                    Node right;
                    Node left;

                    public PackageNode getThePackage(){

                        return PackageNode.this;
                    }
                }
            }





        }




    }




}
