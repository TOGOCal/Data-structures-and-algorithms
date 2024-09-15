public class TSP {

    /**
     * 定义：
     * 完全图：图中任意两个顶点之间都存在一条边的图
     * 问题背景：
     * 给定一张非负权重 完全 无向图，代表所有城市之间的距离，求一条最短路径，使得每个城市恰好被访问一次，最后回到起点。
     * 求出其中的最短路径是多长
     *
     * 一些细节：
     * 由于最优情况一定是一个环路，所以不管从哪个城市出发，都无所谓
     */

    //暴力递归
    /*
    递归设计：
    遍历剩下所有还没有被访问过的城市，从某一个城市触发，看走完后面所有再回到a所花费的距离
     */
    class Solution1{

        //参数给定：graph[i][j] = graph[j][i] = i城市到j城市的距离
        public int minDistance(int[][] graph){

            if(graph == null || graph.length == 0){
                return 0;
            }

            //设置出发城市是0 ， 回来的城市是0
            return dfs(graph,0,graph.length - 1,new boolean[graph.length],0);
        }

        /**
         * 递归函数：
         * @param graph 固定参数：距离
         * @param aim 固定参数：目标是那个城市
         * @param rest 固定参数：剩余未访问的城市个数
         * @param visited 可变参数 ，现在还有哪些城市没有访问过
         * @param cur 可变参数，现在要求从几号城市触发
         * @return 在这些限制条件下，从cur出发，访问所有剩余城市再回到aim的最短路径
         */
        private int dfs(int[][] graph,int aim,int rest,boolean[] visited , int cur){

            if(rest == 1){
                //如果剩余未访问的城市只有一个，那么直接返回从cur到aim的距离
                return graph[cur][aim];
            }

            visited[cur] = true;
            int min = Integer.MAX_VALUE;
            for(int i = 0;i < graph.length;i++){
                if(!visited[i]){
                    min = Math.min(min,dfs(graph,aim,rest - 1,visited,i));
                }
            }
            visited[cur] = false;
            return min;
        }

    }

    /**
     * 状态压缩的缓存结构
     * 在之前的情况下，虽然看似是一个数组和一个起始位置两个可变参数
     * 但是其实这个数组由于是boolean类型，所以可以压缩成一个int类型
     * 由0代表没有false，由1代表true
     * 就可以构建一张int - int 的缓存表而不是数组和int的缓存表
     * 注意：int只能解决32个城市的情况，如果城市超过32个，那么需要使用long类型
     */
    class Solution2{
        int[][] dp;

        //参数给定：graph[i][j] = graph[j][i] = i城市到j城市的距离
        public int minDistance(int[][] graph){

            int num = graph.length;
            //1个城市 1  两个城市11 三个城市111  四个城市1111
            dp = new int[(1 << num) -1][num];

            return dfs(graph,0,graph.length - 1,0,0);
        }


        //当然也可以不写递归
        private int dfs(int[][] graph,int aim,int rest, int visited, int cur){

            if(dp[visited][cur] != 0){

                return dp[visited][cur];
            }


            if(rest == 1){
                //如果剩余未访问的城市只有一个，那么直接返回从cur到aim的距离
                dp[visited][cur] = graph[cur][aim];
                return graph[cur][aim];
            }

            //visited[cur] = true;
            visited |= (1 << cur);
            int min = Integer.MAX_VALUE;
            for(int i = 0;i < graph.length;i++){
                //f(!visited[i]){
                if(!(((visited >> i) & 1) == 1)){

                    min = Math.min(min,dfs(graph,aim,rest - 1,visited,i));
                }
            }

            visited ^= (1 << cur);//使用位运算的思想
            dp[visited][cur] = min;
            return min;
        }
    }
}
