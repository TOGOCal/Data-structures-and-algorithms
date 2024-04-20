import java.util.*;

public class 最小生成树算法_K算法 {

    /**
     * 在给定一张无向图，如果在它的子图中，任意两个顶点都是互相连通，并且是一个树结构，那么这棵树叫做生成树。
     * 连接顶点之间的图有权重时，权重之和最小的树结构为最小生成树
     *
     * 解法：
     * 1.将所有边按照权重进行排序
     * 2.遍历所有边，使用并查集算法，如果这个边连接的两个点已经联通的
     *   那么这个边就相当于没有作用的边，遍历下一个节点
     * 3.将所有有效的边存入集合中，返回这个集合
     */

    public static void main(String[] args) {

    }

    public static List<Edge> method(Gragh gragh){

        List<Edge> result = new ArrayList<>();

        int size = gragh.edges.size();
        quickSort(gragh.edges,0,size-1);//完成边的排序


        for(int i=0;i<size;i++){

            Edge nowEdge = gragh.edges.get(i);

            Node node1 = nowEdge.link1;
            Node node2 = nowEdge.link2;

            if(!judgeSameGather(node1 , node2)){//如果不是一个集合的

                mergeGather(node1 , node2);//那就合并两个集合
                result.add(nowEdge);//同时说明这条边是有用的
            }
        }

        return result;

    }

    public static void quickSort(ArrayList<Edge> edges,int L,int R){

        if(L >= R){

            return ;
        }

        int p1 = L-1;
        int p2 = R+1;

        int part = edges.get((int)(Math.random()*(R-L+1)+L)).weight;

        for(int i=L; i<p2; i++){

            if(edges.get(i).weight < part){

                Edge edge = edges.get(i);
                edges.set(i , edges.get(p1+1));
                edges.set(p1+1,edge);
                p1++;
            }else if(edges.get(i).weight > part){

                Edge edge = edges.get(i);
                edges.set(i , edges.get(p2-1));
                edges.set(p2-1,edge);
                i--;
                p2--;
            }
        }

        quickSort(edges, L , p1);
        quickSort(edges, p2 , R);
    }

    public static boolean judgeSameGather(Node node1,Node node2){

//        if(getGatherHead(node1) == getGatherHead(node2)){
//
//            return true;
//        }else{
//
//            return false;
//        }

        return getGatherHead(node1) == getGatherHead(node2);
    }

    public static void mergeGather(Node node1,Node node2){//合并集合

        Node head1 = foundHead.get(node1);
        Node head2 = foundHead.get(node2);

        int count1 = countHead.get(head1);
        int count2 = countHead.get(head2);

        if(count1 > count2){

            foundHead.put(head2,head1);//将小的挂在大的上，这样在查询是否是同一个集合时进行挂的操作的时候耗时少
            countHead.put(head1, count1 + count2);
            countHead.remove(head2);
        }else{

            foundHead.put(head1 , head2);
            countHead.put(head2, count1 + count2);
            countHead.remove(head1);
        }
    }

    public static Node getGatherHead(Node node){//找到集合代表点

        if(node == null){
            return null;
        }else if(foundHead.get(node) == node){
            return node;
        }else if(foundHead.get(foundHead.get(node)) == foundHead.get(node)){

            return foundHead.get(node);//直接挂在head上
        }

        Queue<Node> needLinkNode = new LinkedList<>();//需要连在head上面的点

        Node head = node;
        while(foundHead.get(head) != head){//集合代表点所在集合的代表点是自己

            needLinkNode.add(head);//这个点需要挂在head（最终）上
            head = foundHead.get(head);//向下移动
        }

        while(!needLinkNode.isEmpty()){

            Node nowNode = needLinkNode.poll();
            foundHead.put(nowNode, head);//将这个点挂在head上
        }

        return head;
    }


    public static HashMap<Node,Node> foundHead = new HashMap<>();//节点以及其集合代表点
    public static HashMap<Node,Integer> countHead = new HashMap<>();//保存每个集合多少个元素


    public static class Node{//每个节点

        int value;

        //ArrayList<Node> pre;
        ArrayList<Edge> linked;

        public Node(int value){
            this.value = value;
            //pre = new ArrayList<>();
            linked = new ArrayList<>();
        }
    }

    public static class Edge{//每个边

        int weight;
        Node link2;
        Node link1;

        public Edge(int weight, Node link1,Node link2){
            this.weight = weight;
            this.link1 = link1;
            this.link2 = link2;
        }
    }

    public static class Gragh{

        ArrayList<Node> points;
        ArrayList<Edge> edges;

        public Gragh(){

            points = new ArrayList<>();
            edges = new ArrayList<>();
        }
    }

}
