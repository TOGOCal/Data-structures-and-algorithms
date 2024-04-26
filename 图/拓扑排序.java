import java.util.*;

public class 拓扑排序 {

    /**
     * 对于一个 有向无环 图而言，可以用拓扑排序的方式进行处理，方便后续的处理
     * 例如 1 -> 2 -> 4 -> 5
     *       指2  指7   指7
     *      3        7
     * 要先完成1和3才能完成2，要完成2和5才能完成7，而要完成5先要完成4
     * 所以拓扑排序的顺序是：1和3无所谓 2 4 5 7
     *
     */
    public static void main(String[] args) {

    }



    public List<Node> topologicalSorting(Gragh graph){

        List<Node> result=new ArrayList<>();

        HashMap<Node,Integer> inMap=new HashMap<>();//用于保存每个节点剩余的出度
        //因为有的时候不能直接在Node上面做修改，所以用这种方式，并在这个表中进行操作

        Queue<Node> zeroInNode=new LinkedList<>();//用于保存所有现在已经

        for(Node nowNode : graph.nodes.values()){

            inMap.put(nowNode , nowNode.in);
            if(nowNode.in == 0){

                zeroInNode.add(nowNode);
            }
        }

        while(!zeroInNode.isEmpty()){

            Node nowNode=zeroInNode.poll();//弹出就加入

            result.add(nowNode);

            for(Road thisRoad : nowNode.next){

                //将下个节点的入度减去，存回表，如果入度为0入队列
                int nowIn=thisRoad.toWhere.in-1;//
                inMap.put(thisRoad.toWhere,(nowIn));//减去并存回去
                if(nowIn == 0){

                    zeroInNode.add(thisRoad.toWhere);//如果入度是0就存进去
                }
            }
        }

        return result;
    }

    public class Gragh{//图

        HashMap<Integer,Node> nodes;

        Gragh(){

            nodes = new HashMap<>();
        }
    }

    public class Node{

        int in;//出度
        int out;//入度（通常指的是指向这个点的节点个数而不是权重的和
        ArrayList<Node> pre;
        ArrayList<Road> next;

        Node(int in, int out){

            this.in = in;
            this.out = out;
            pre = new ArrayList<>();
            next = new ArrayList<>();
        }
    }

    public class Road{

        int weight;//权重
        Node toWhere;

        Road(int weight, Node toWhere){

            this.weight=weight;
            this.toWhere=toWhere;
        }
    }
}
