import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class 最小生成树算法_P算法 {

    /**
     * 在给定一张无向图，如果在它的子图中，任意两个顶点都是互相连通，并且是一个树结构，那么这棵树叫做生成树。
     * 连接顶点之间的图有权重时，权重之和最小的树结构为最小生成树
     *
     * 解法2：
     * 随便选取一个节点为开始
     *
     * 接下来对于每一个节点访问到节点后，解锁所有与这个点向量的节点，
     * 选取其中权重最小的边，如果这个边相连的节点是未解锁的点，则选择这个点
     * 如果是已经解锁的点，就选取第二小的边。。。如此循环直到找到未解锁的点
     * 因为这个是要动态对数据进行排序，所以采用堆排序，每次弹出最小的那个
     */
    public static void main(String[] args) {

    }

    public static List<Edge> method(Graph graph){

        if(graph == null){

            return null;
        }

        if(graph.edges.size() == 0){

            return null;
        }

        MyHeep heep = new MyHeep();

        HashSet<Node> hasVisited = new HashSet<>();//储存已经访问了的点
        Node start = graph.nodes.get(0);//是哪个点都无所谓，所以直接选取0位置的点就行

        ArrayList<Edge> result = new ArrayList<>();//储存结果的地方

        int maxSize = graph.nodes.size();//由于代表哈希set的终止条件，当它存储了所有的点之后自然就访问完了所有点

        Node nowNode = start;

        HashSet<Edge> visited = new HashSet<>();

        while (hasVisited.size() < maxSize){

            for(Edge edge : nowNode.next){//遍历所有的边

                if(!visited.contains(edge)){

                    heep.addEdge(edge);
                }

            }

            Edge edge = heep.popEdge();
            Node node = edge.next;//下一个要走向的点

            while(hasVisited.contains(node)){
                //当目前权重最小的边 的 可到达节点被访问过
                //while代表直到找到未访问过的点
                edge = heep.popEdge();
                node = edge.next;

                if(heep.edges.size() == 0){

                    //为了防止森林（一个图中有多个图(所有的边都被输出完了
                    //但是如果点还没有遍历完，说明有多个图，这时只要找出下一个没有拜访的点就行了

                    for(Node newNode:graph.nodes){

                        if(!hasVisited.contains(newNode)){

                            node = newNode;
                            break;
                        }
                    }
                }
            }

            result.add(edge);

            nowNode = node;//nowNode向下移动

            hasVisited.add(node);//加入这个点代表已访问
        }

        return result;
    }

    public static class MyHeep {

        ArrayList<Edge> edges;//由动态数组构建堆
        //小根堆

        public MyHeep(){
            edges = new ArrayList<>();
        }

        public void upSearch(){//向上检索
            //一般用于加在末尾的向上检索

            int end = edges.size()-1;//最后的索引

            //int pre = (end - (end%2 == 1 ? 1 : 2))/2;
            //换种写法，反正就是奇数-1，偶数减二
            int pre = (end - (2 - (end % 2)))/2;

            int now = end;

            while(pre >=0 && edges.get(pre).weight > edges.get(now).weight){

                //交换两个
                Edge e = edges.get(pre);
                edges.set(pre, edges.get(now));
                edges.set(now, e);

                now = pre;//向上移动\
                pre = (now - (2 - (end % 2)))/2;
            }

        }

        public void downSearch(int index){

            if(index >= edges.size()){

                return ;
            }

            int now = index;
            int left = now*2+1;

            if(edges.size() <= left){

                return ;//左节点都大于等于数组大小了，直接返回（已经在最底下了
            }

            int minNext = (left+1 < edges.size() ? (edges.get(left).weight > edges.get(left+1).weight ? left+1 : left) : left);
                    //右边的点存在吗?             如果存在则比较,由于要满足小根堆，所以要比较子节点中的小的的  不存在就按照存在的left进行向下比较

            while(edges.get(now).weight > edges.get(minNext).weight){

                Edge e = edges.get(now);
                edges.set(now, edges.get(minNext));
                edges.set(minNext, e);

                now = minNext;
                left = now*2+1;
                if(now >= edges.size()){

                    return ;
                }

                minNext = (left+1 < edges.size() ? (edges.get(left).weight > edges.get(left+1).weight ? left+1 : left) : left);
            }

        }

        public void addEdge(Edge e){

            edges.add(e);//加在最后的，所以使用向上检索的方法

            upSearch();
        }

        public Edge popEdge(){//弹出节点

            if(edges.size() == 0){

                return null;
            }

            int end = edges.size()-1;//结尾的点
            Edge e = edges.get(0);//要弹出的是头部的点（最小）
            edges.set(0, edges.get(end));
            edges.set(end, e);//将这个与结尾处的点交换

            downSearch(0);//不知道交换过来的点则么样，所以向下检索

            edges.remove(end);//移出掉原来在头部现在在末尾的点

            return e;
        }
    }

    public static class Graph{

        ArrayList<Node> nodes;
        ArrayList<Edge> edges;
        public Graph(){

            nodes = new ArrayList<>();
            edges = new ArrayList<>();
        }
    }

    public static class Node{

        int value;
        ArrayList<Edge> next;

        public Node(int value){

            this.value = value;
            next = new ArrayList<>();
        }

    }

    public static class Edge{

        int weight;
        Node next;

        public Edge(int weight, Node next){
            this.weight = weight;
            this.next = next;
        }
    }
}
