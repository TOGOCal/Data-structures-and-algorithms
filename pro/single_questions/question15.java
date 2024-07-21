import java.util.*;
import java.util.stream.Collectors;

public class question15 {


    /**
     * 问题背景：
     * 现规定：
     * 如果两个数a和b之间的最大公约数不为1，则定义a和b之间有路
     * 现在需要求两个问题：
     * 图中有几个连通区域
     * 最大的连通区域有多大
     */


    /**
     * 两种方式各有优劣
     * 比如第二种方式
     * 虽然时间复杂度做到了比方法1的 N平方 时间复杂度更低，但是如果遇到第一个数字是 1*10e9，就算开平方，遍历的代价也是巨大的
     *
     * 所以，如果单个数字数据量很大的话，就选择方法1
     * 如果数据量很大，但是单个数据的大小不是很大，这就可以使用方法2了
     *
     * 两种方法使用了并查集，本身方法就很好了，这两种方法的许纳泽需要按章题目给定的数据情况进行选择，
     * 这是一种重要的思维
     */


    //方法1：直接使用N的平法的遍历方式
    class Solution{

        public Information method(int[] arr){

            Union<Integer> union = new Union<>(Arrays.stream(arr).boxed().collect(Collectors.toList()));

            for(int i = 0;i< arr.length;i++) {

                for(int j = i+1;j<arr.length;j++) {

                    //如果最大公约数不是1
                    if( gcd(arr[i], arr[j]) != 1) {

                        union.setSameIUnion(arr[i], arr[j]);
                    }
                }

            }

            return new Information(union.allSymbols.size(), union.maxSize);

        }


        //求最大公约数
        public int gcd(int a, int b) {

            return b == 0 ? a : gcd(b, a % b);
        }



        //这个问题要求返回两个值，所以使用这种方式进行返回
        class Information{

            int howManyUnion;
            int maxLength;


            public Information(int howManyUnion, int maxLength) {

                this.howManyUnion = howManyUnion;
                this.maxLength = maxLength;
            }
        }





        //结构（并查集
        class Union<V>{

            //以下是实现本题要求创建的特殊标识
            HashSet<Node<V>> allSymbols;
            int maxSize;

            //通过值找到对应封装类的哈希表
            HashMap<V , Node<V>> foundNodeMap;

            public Union(List<V> list) {

                allSymbols = new HashSet<>();
                maxSize =1;

                for(V v : list) {

                    Node<V> node = new Node<>(v);
                    //保证进来的每个元素互不相同
                    foundNodeMap.put(v, node);

                    //最开始每个元素的代表点都是自己
                    allSymbols.add(node);

                }
            }


            //将两个元素设置为相同集合的方法
            public void setSameIUnion(V v1 , V v2){

                Node<V> node1 = foundNodeMap.get(v1);
                Node<V> node2 = foundNodeMap.get(v2);

                if(node1 == null || node2 == null) {

                    //这两个集合从未被创建，自然不是一个集合
                    return;
                }


                //找到顶节点，同时将所有节点连接好
                Node<V> symbol1 = foundSymbolNode(node1);
                Node<V> symbol2 = foundSymbolNode(node2);

                if(symbol1.num < symbol2.num){

                    Node<V> temp = symbol1;
                    symbol1 = symbol2;
                    symbol2 = temp;
                }//保证之后出去后1的集合数量更大

                allSymbols.remove(symbol2);
                symbol2.symbol = symbol1;
                symbol1.num += symbol2.num;


                maxSize = Math.max(maxSize , symbol1.num);
            }

            //找到父节点，同时将所有节点连接好
            private Node<V> foundSymbolNode(Node<V> node){

                Node<V> symbol = node;

                Stack<Node<V>> stack = new Stack<>();

                while( symbol.symbol != symbol) {
                    stack.push(symbol);
                    //向上移动
                    symbol = symbol.symbol;
                }

                //出循环后的节点就是最上层的节点了
                while(!stack.isEmpty()){

                    Node<V> nowNode = stack.pop();
                    //全部指向顶节点

                    //移除这些点的集合
                    allSymbols.remove(nowNode.symbol);

                    nowNode.symbol = symbol;
                }

                allSymbols.add(symbol);
                return symbol;
            }

            public boolean isSameUnion(V v1 , V v2){

                Node<V> node1 = foundNodeMap.get(v1);
                Node<V> node2 = foundNodeMap.get(v2);

                if(node1 == null || node2 == null) {

                    //这两个集合从未被创建，自然不是一个集合
                    return false;
                }

                //用于向上遍历，找到集合代表点
                //为了使得时间复杂度为N，每次查询都使所有节点向上连接到顶点
                return foundSymbolNode(node1) == foundSymbolNode(node2);
            }
        }

        //集合中每个元素的封装
        class Node<V> {

            V value;
            //用于指向集合代表点
            Node<V> symbol;
            //该集合中有哪些元素
            int num;

            public Node(V value) {
                this.value = value;
                //最初每个集合的代表点都是自己
                this.symbol = this;
                num = 1;
            }
        }
    }



    //不一一遍历了，使用因子代表法进行
    //例如100包括的因子就有1 2 5 10
    //然后在向下遍历的时候就比较有没有相同的因子
    //如何加速判断有哪些因子：只需要从1尝试到根号n就可以了
    //然后并查集按照元素下标的方式进行存储
    public class Solution2{


        public Information method(int[] arr){


            List<Integer> list = new ArrayList<>();
            for(int i=0;i< arr.length ; i++){

                list.add(i);
            }//使用下标进行Union创建

            Union<Integer> union = new Union<>(list);


            //用于存储 因子：对应下标
            HashMap<Integer , Integer> allFactors = new HashMap<>();

            for(int index = 0; index < arr.length ; index++){
                //终止时间
                int endTime = (int)Math.sqrt(arr[index]);

                //遍历每一个可能的因子
                for(int i =0;i <= endTime;i++){

                    //之前也又数是这个因子
                    if(arr[index] % 2 == 0){

                        if(allFactors.containsKey(i)){

                            int beforeIndex = allFactors.get(i);

                            //如果不在一个集合就进行连接
                            if(!union.isSameUnion(i ,beforeIndex)){

                                union.setSameUnion(index , beforeIndex);
                            }
                        }else{

                            allFactors.put(i, index);
                        }

                        //当前因子对应的另一个数
                        int num = arr[index]/i;

                        if(allFactors.containsKey(num)){

                            int beforeIndex = allFactors.get(num);

                            //如果不在一个集合就进行连接
                            if(!union.isSameUnion(num ,beforeIndex)){

                                union.setSameUnion(index , beforeIndex);
                            }
                        }else{

                            allFactors.put(num, index);
                        }

                    }

                }

            }



            return new Information(union.allSymbols.size() , union.maxSize);


        }



        class Information{

            int howManyUnion;
            int maxSize;

            Information(int howManyUnion , int maxSize){

                this.howManyUnion = howManyUnion;
                this.maxSize = maxSize;
            }

        }



        class Union<V>{

            HashSet<Node<V>> allSymbols;
            int maxSize;

            HashMap<V , Node<V>> foundNodeMap;


            Union(List<V> list){

                allSymbols = new HashSet<>();
                foundNodeMap = new HashMap<>();
                maxSize =1 ;

                for(V value:list){

                    Node<V> newNode = new Node<>(value);

                    allSymbols.add(newNode);
                    foundNodeMap.put(value , newNode);
                }
            }



            public boolean isSameUnion(V v1 , V v2){

                Node<V> node1 = foundNodeMap.get(v1);
                Node<V> node2 = foundNodeMap.get(v2);

                if(node1 == null || node2 == null){

                    return false;
                }


                Node<V> symbol1 = foundSymbolNode(node1);
                Node<V> symbol2 = foundSymbolNode(node2);

                return symbol2 == symbol1;
            }


            public void setSameUnion(V v1 , V v2){

                Node<V> node1 = foundNodeMap.get(v1);
                Node<V> node2 = foundNodeMap.get(v2);

                Node<V> symbol1 = foundSymbolNode(node1);
                Node<V> symbol2 = foundSymbolNode(node2);

                if(symbol1.count < symbol2.count){

                    Node<V> temp = symbol1;
                    symbol1 = symbol2;
                    symbol2= temp;
                }

                allSymbols.remove(symbol2);

                symbol2.symbol = symbol1;
                symbol1.count += symbol2.count;


                maxSize = Math.max(maxSize , symbol1.count);
            }




            public Node<V> foundSymbolNode(Node<V> node){

                Stack<Node<V>> stack = new Stack<>();

                Node<V> symbol = node;

                while(symbol.symbol!=symbol){

                    stack.push(symbol);

                    symbol = symbol.symbol;
                }

                while(!stack.isEmpty()){

                    Node<V> nowNode = stack.pop();

                    allSymbols.remove(nowNode.symbol);
                    nowNode.symbol = symbol;
                }

                allSymbols.add(symbol);
                return symbol;
            }

        }

        class Node<V>{

            V value;
            Node<V> symbol;
            int count;

            Node(V value){

                this.value = value;
                this.symbol = this;
                count =1;
            }
        }
    }


}
