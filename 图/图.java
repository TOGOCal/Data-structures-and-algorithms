import java.util.*;

public class 图 {

    /**
     * 图是由点和线构成的结构
     * 算法并不复杂，重点是如何将不同的图转化为自己熟悉的结构
     * 
     */
    public static void main(String[] args) {

    }

    public static class Node{//每个节点
    
        int value;//代表每个点的编号
        int in;//有多少个点指向这个点
        int out;//有多少个点是这个点向外指出的
        ArrayList<Node> next;//所有与这个点直接相连的节点
        ArrayList<Edge> edges;//与这个点相连的所有边

        Node(int value, int in, int out){
            this.value = value;
            this.in = in;
            this.out = out;
            next = new ArrayList<>();
            edges = new ArrayList<>();
        }

        public void traverseByLevel(){//宽度优先遍历

            if(this.next.size() == 0){

                return;
            }

            HashSet<Node> beTraversed = new HashSet<>();//已经遍历过的元素

            Queue<Node> queue = new LinkedList<>();
            queue.add(this);

            while(!queue.isEmpty()){

                Node thisNode = queue.poll();
                //主要是为了防止环的结构
                System.out.println(thisNode.value);

                for(Node nodes : thisNode.next){

                    if(!beTraversed.contains(nodes)){

                        beTraversed.add(thisNode);
                        queue.add(nodes);//队列和哈希set同步加入
                    }
                }
            }
        }

        public void traverseByDepth(){//深度优先遍历

            if(this.next.size() == 0){

                return;
            }

            Stack<Node> stack = new Stack<>();
            stack.push(this);

            HashSet<Node> visited = new HashSet<>();

            while(!stack.isEmpty()){

                Node thisNode = stack.pop();

                for(Node nodes : thisNode.next){

                    if(!visited.contains(nodes)){

                        visited.add(nodes);
                        stack.push(thisNode);//将这个重新压回去//这个点是与二叉树不同的
                        stack.push(nodes);
                        System.out.println(nodes.value);
                        break;
                    }
                }
            }
        }
    }

    public static class Edge {//每条边

        int weight;// 权重(可能会有的属性)
        Node from;
        Node to;// 从哪个节点到哪个节点

        Edge(int weight, Node from, Node to) {

            this.weight = weight;
            this.to = to;
            this.from = from;
        }
    }

    public static class Graph{

        HashMap<Integer, Node> nodes;//可以通过这张表实现通过value找到对应的节点
        HashSet<Edge> edges;//储存所有边

        public Graph(){
            nodes = new HashMap<>();
            edges = new HashSet<>();
        }
    }
}
