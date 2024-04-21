import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Dijkstra算法 {

    /**
     * 使用条件：
     * 非负权重：Dijkstra算法要求图中所有边的权重都是非负的。如果存在负权边，Dijkstra算法无法正常工作。
     *
     * 连通图：Dijkstra算法要求图是连通的，即从给定的源节点可以到达所有其他节点。
     * 如果图不是连通的，则Dijkstra算法只能计算出源节点所属的连通分量的最短路径。
     *
     * 有向或无向图：Dijkstra算法可以应用于有向图或无向图。对于有向图，算法会考虑边的方向，
     * 而对于无向图，算法会将其视为双向边。
     *
     * 不存在环路：Dijkstra算法不能处理存在环路（循环边）的图。如果图中存在环路，则算法可能会陷入无限循环。
     */

    /**
     * 给定一个图，满足：
     * （单向图，无向图无所谓，此处按照单向图举例
     * 所有边的权重都不为负数
     *
     * 现给定一个节点，求出所有节点距离该节点的最短距离
     *
     * 解题思路：
     * 从这个点出发，遍历所有节点，更新直接相邻的点的距离
     * 从这个点出发，寻找直接相邻的边的权重中最小的边
     * 以这条边走向对应节点，重复以上过程
     */

    public static void main(String[] args) {}

    public static HashMap<Node , Integer> method(Node begin){
        //返回哈希表的原因是因为要存储每个点到这个点的距离
        HashMap<Node , Integer> distanceMap = new HashMap<>();//所有看得到的点到开始点的距离
        HashSet<Node> visitedNode = new HashSet<>();//已经访问的点

        distanceMap.put(begin, 0);
        Node current = getSuitableNode(distanceMap , visitedNode);
        while(current != null){

            int distance = distanceMap.get(current);//拿到这个点到给定点的距离

            visitedNode.add(current);//锁住这个点

            for(Edge edge : current.nexts){

                if(!distanceMap.containsKey(edge.point)){

                    distanceMap.put(edge.point, distance + edge.weight);
                }else{

                    if(distance + edge.weight < distanceMap.get(edge.point)){

                        distanceMap.put(edge.point, distance + edge.weight);//如果小于就更新
                    }
                }
            }

            current = getSuitableNode(distanceMap , visitedNode);
        }

        return distanceMap;
    }

    public static Node getSuitableNode(HashMap<Node , Integer> distanceMap, HashSet<Node> visitedNode){

        Node result = null;//当所有可以被访问的点都被锁住的时候，就会返回null

        int minDistance = Integer.MAX_VALUE;

        for(Node node : distanceMap.keySet()){

            int distance = distanceMap.get(node);

            if(distance < minDistance && !visitedNode.contains(node)){

                minDistance = distance;
                result = node;
            }//找到目前距离最近且没有被锁住的点
        }

        return result;

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
        ArrayList<Edge> nexts;

        public Node(int value){
            this.value = value;
            nexts = new ArrayList<>();
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


//对于图：
//1 权重5 连接2
//2 权重2 连接4
//1 权重1 连接3
//3 权重7 连接4
//而言
