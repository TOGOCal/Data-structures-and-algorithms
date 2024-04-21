import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Dijkstra算法pro {

    /**
     * 这个算法是算出所有能够到达的点某个点的最小距离
     */

    /**
     * 争争对之前的算法的改进：
     * 之前选出哪个边权重最小的时候使用的是遍历哈希表选出最小的
     * 但是我们完全可以考虑将这些点放在堆里，就不用每次遍历n次了
     *
     */
    public static void main(String[] args) {

    }

    public static HashMap<Node , Integer> method(Node node){

        if(node == null){

            return null;
        }

        //HashMap<Node , Integer> result = new HashMap<>();
        MyHeep heep = new MyHeep();
        HashMap<Node , Integer> result = new HashMap<>();
        HashSet<Node> visited = new HashSet<>();

        //result.put(node, 0);
        heep.addHeepNode(node, 0);

        HeepNode next = suitableNode(heep, visited);
        while(next!=null){

            int distance = next.distance;//找到距离
            Node nowNode = next.node;
            visited.add(nowNode);

            for(Edge edge : nowNode.edges){

                if(!heep.containsNode(edge.point)){

                    heep.addHeepNode(edge.point, distance + edge.weight);
                    result.put(edge.point, distance + edge.weight);
                }else {

                    if(heep.getHeepNode(edge.point).distance > distance + edge.weight ){

                        //原来的距离大才更新
                        heep.addHeepNode(edge.point, distance + edge.weight);
                        result.put(edge.point, distance + edge.weight);
                    }
                }

            }

            next = suitableNode(heep, visited);
        }

        return result;
    }

    public static HeepNode suitableNode(MyHeep heep, HashSet<Node> visited){
        //找到合适的点
        HeepNode result = null;

        while(heep.heepNodes.size() != 0){
            HeepNode help = heep.popHeepNode();
            if(!visited.contains(help.node)){

                result = help;
                break;
            }
        }

        return result;
    }

    public static class HeepNode{

        int distance;
        Node node;

        HeepNode( int distance , Node node){
            this.node = node;
            this.distance = distance;
        }
    }

    public static class MyHeep{

        ArrayList<HeepNode> heepNodes;
        HashMap<Node , Integer> getPosition;//找到某个点在堆中的位置



        MyHeep(){
            heepNodes = new ArrayList<>();
            getPosition = new HashMap<>();
        }

        public boolean containsNode(Node node){

            return getPosition.containsKey(node);
        }

        public HeepNode getHeepNode(Node node){

            return heepNodes.get(getPosition.get(node));
        }

        public HeepNode popHeepNode(){
            //弹出最上面的元素

            HeepNode pop = heepNodes.get(0);//最上面的点

            //交换第一和最后的点
            HeepNode changeHelp = heepNodes.get(0);
            heepNodes.add(0, heepNodes.get(heepNodes.size() - 1));
            heepNodes.add(heepNodes.size() - 1 , changeHelp);

            heepNodes.remove(heepNodes.size() - 1);//移出掉现在最后的点

            //同时更新getPosition中的内容
            getPosition.put(heepNodes.get(0).node, 0);
            getPosition.remove(changeHelp);//将原来那个点移除

            downCheck(heepNodes.get(0));//检查位置

            return pop;
        }

        public void downCheck(HeepNode heepNode){

            if(heepNodes.size() <= 1){

                return ;
            }

            int position = getPosition.get(heepNode);
            int left = position * 2 + 1;

            int minIndex = left + 1 < heepNodes.size() ? ( heepNodes.get(left + 1).distance < heepNodes.get(left).distance ? left+1 : left ) : left;//小根堆，要换也是把子节点中小的换上来

            while( heepNode.distance > heepNodes.get(minIndex).distance ){

                HeepNode changeHelp = heepNodes.get(position);
                heepNodes.set(position , heepNodes.get(minIndex));
                heepNodes.set(minIndex , changeHelp);

                getPosition.put(heepNodes.get(position).node, position);
                getPosition.put(heepNodes.get(minIndex).node, minIndex);

                position = minIndex;

                left = position * 2 + 1;
                if(left >= heepNodes.size()){

                    return ;
                }

                minIndex = left + 1 < heepNodes.size() ? ( heepNodes.get(left + 1).distance < heepNodes.get(left).distance ? left+1 : left ) : left;
            }
        }

        public void addHeepNode(Node node , int distance){

            HeepNode heepNode = null;

            if(containsNode(node)){

                getHeepNode(node).distance = distance;
                heepNode = getHeepNode(node);
                //只有变小了才走这里，说明只用向上检查就可以了
            }else{

                heepNode = new HeepNode(distance, node);

                heepNodes.add(heepNode);
                getPosition.put(node , heepNodes.size()-1);
            }

            upcheck(heepNode);//向上检查这个点放对没
        }

        public void upcheck(HeepNode heepNode){
            //从某个节点开始向上检查
            int index = getPosition.get(heepNode);

            if(index == 0){

                return ;
            }

            int pre = (index - (2 - (index & 1)))>>1;
            HeepNode preHeepNode = heepNodes.get(pre);

            while(preHeepNode.distance > heepNode.distance && pre >= 0){
                //小根堆，上面的不能比下面的大
                HeepNode changeHelp = preHeepNode;
                preHeepNode = heepNode;
                heepNode = changeHelp;

                getPosition.put(preHeepNode.node , index);
                getPosition.put(heepNode.node, pre);//同时更新在表中的位置

                index = pre;
                pre = (index - (2 - (index & 1)))>>1;
            }
        }
    }

    public static class Graph{

        ArrayList<Node> allNodes;
        ArrayList<Edge> allEdges;

        Graph(){
            allNodes = new ArrayList<>();
            allEdges = new ArrayList<>();
        }
    }

    public static class Node{

        int value;
        ArrayList<Edge> edges;

        public Node(int value){
            this.value = value;
            edges = new ArrayList<>();
        }

    }

    public static class Edge{

        int weight;
        Node point;

        public Edge(int weight, Node point){
            this.weight = weight;
            this.point = point;
        }
    }

}
