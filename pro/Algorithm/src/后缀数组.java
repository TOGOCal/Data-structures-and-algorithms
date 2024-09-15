import java.util.*;

public class 后缀数组 {

    /**
     * 后缀树是很多字符串题目的最优解
     * 而后缀数组在很多题目上是后缀树的代替方案，以下分别介绍后缀数组的构建和后缀排序
     * 并逐渐进行优化
     */

    /**
     * 1.定义及结构
     * 例如字符串 abcabaa
     * 其后缀数组中包括
     * abcabaa
     * bcabaa
     * cabaa
     * abaa
     * baa
     * aa
     * a
     * 明显的，直接生成的时间复杂度是O(N^2)
     * 在此基础上，我们对其进行排序，得到 结构1
     * 4 6 7 3 5 2 1 ，分别代表从index位置开始的字符串的排名
     * 例如a就是从index = 6开始的字符串，而a的排名是1
     *
     * 同时我们可以得到结构2：
     * 6 5 3 0 4 1 2
     * 代表从index位置开始的字符串的排名，
     * 例如a就是从index = 6开始的字符串，而a的排名是1 所以在结构2中 1-1 = 0 这个位置
     */


    //DC3算法
    /**
     * 加速的策略：
     * 不生成后缀字符串，而是通过对源字符串进行操作，得到排名的数据
     * 做法：
     *  我们给String的每一个下标进行分组
     *  index % 3 == 0 为 s0组
     *  index % 3 == 1 为 s1组
     *  index % 3 == 2 为 s2组
     *
     * 在此基础上，我们可以进行如下分析：
     * 假设：
     *  我们已经得到了s1和s2的排名并且已经将排名进行了合并，得到了现在排名s12
     *  我们能不能通过较少的代价，得到s0的排名呢？
     * 答案是肯定的：
     *  在对s0进行排序的时候，其实相当于只需要进行两次比较
     *  第一次比较首字母，第二次比较后面的字符串
     *  同时后面的字符串的排序其实已经拍好了，
     *  这时候，我们就可以使用基数排序了
     *  准备一个长度为Math.max(26,s12.length)的数组，将s0字符串的后面看成依托东西
     *  先比较低位，对后面的一坨字符串进行基数排序
     *  然后基数排序首字母
     *  时间复杂度 = 26 + s12.length =》 O(N)
     * 现在我们得到了s0的内部排序，现在我们需要做的就是将s0的排序与s12的排序进行合并
     *  我们希望比较的时间尽量少，但是不可避免的是，我们至少需要进行s012次排序
     *  因此我们只能希望做到每次比较的时间复杂度是O(1)
     *  而这是已经做到了的
     *  在比较s0和s12的时候，我们可能遇到以下几种情况：
     *      s0和s1比较
     *          首字母不同:比较首字母 O(1)
     *          首字母相同：比较后面的字符串 s1 和 s2,而这是在s12中已经比较好了的 O(1)
     *      s0和s2比较
     *          首字母不同:比较首字母 O(1)
     *          首字母相同：比较后面的字符串 s1和s0:
     *              首字母不同：比较首字母 O(1)
     *              首字母相同：比较后面的字符串 s2和s1 而这是在s12中已经比较好了的 O(1)
     *  所以，不管是哪种情况，比较的时间复杂度都是O(1)，
     *  所以合并的时间复杂度是 O(N)
     * 总的来说，在s12已经得出的情况下，得到s012的时间复杂度是O(N)
     *
     * 现在我们的问题变成了如何的到s12的排名
     * 进行以下操作：
     *  先对s12的前3位进行基数排序
     *  如果能得到严格排序，则执行s0的操作
     *  如果蹦年得到严格排名，则将s12目前的排名信息中，将s1的排名放在一起，s2的排名放在一起作为字符串，
     *  进行递归调用，得到这个字符串的严格排名，进行对应操作，就得到了严格排名：
     *  距离：
     *      对于字符串mississippi
     *      s12 = mississippi排名如下：
     *      以index = 哪位开头  |    前三位比较是什么   |   前三位排名   |  正真排名
     *        1                |       iss           |    3         |    3
     *        2                |       ssi           |    5         |    6
     *        4                |       iss           |    3         |    2
     *        5                |       ssi           |    5         |    5
     *        7                |       ipp           |    2         |    1
     *        8                |       ppi           |    4         |    4
     *        10               |       i\0\0         |    1         |    0
     *        注：超出范围就用char(0)补位，不会造成影响，因为是最小的char
     *  明显的，这个s12的排名并不严格
     *      注：因为每个字符串长度一定不同，所以一定可以得到严格排名
     *  将排名s1放在一起   s2放在一起
     *  3 3 2 1 | 5 5 4
     *  递归进行排名，得到：
     *  3 2 1 0 | 6 5 4
     *  而这就是正真的排名
     *  时间复杂度：递归调用：O(N) +  O(2/3)，根据master公式，得到这个递归的时间复杂度是O(N)
     *
     */

    public static void main(String[] args) {

        String s = "mississippi";
        Solution2 s2 = new Solution2();
        s2.generateTwoRank(s);
        System.out.println(Arrays.toString(s2.indexRank));
        System.out.println(Arrays.toString(s2.rankIndex));

    }

    //基本生成方法
    //时间复杂度O(N^2)（生成后缀字符串
    //O(N^2*logN)排序耗时
    class Solution1{

        String[] suffixStrings;
        int[] indexRank;//index位置开始的字符串的排名
        int[] rankIndex;//排名为index+1的字符串的起始位置

        public void toGetThewStruct(String s){

            suffixStrings = getSuffixStrings(s.toCharArray());
            quickSort(suffixStrings , 0 , suffixStrings.length-1);//进行排序

            indexRank = getIndexRank(s , suffixStrings);

            rankIndex = getRankIndex(s , suffixStrings);
        }

        public int[] getRankIndex(String s,String[] strings){

            int[] res = new int[strings.length];

            for(int i = 0 ; i < strings.length ; i++){

                res[i] = (s.length() - strings[i].length());//总长度减去当前字符串长度就是开始字符位于的位置了
            }

            return res;
        }

        //代表从index位置开始的字符串的排名
        public int[] getIndexRank(String s,String[] strings){

            int[] res = new int[strings.length];

            for(int i = 0 ; i < strings.length ; i++){

                res[s.length() - strings[i].length()] = i;//总长度减去当前字符串长度就是开始字符位于的位置了,这个index代表的排名是i
            }

            return res;

        }

        public void quickSort(String[] strings , int left , int right){

            if(left >= right){

                return;
            }

            int random = (int)(Math.random()*(right - left + 1)+left);

            String temp = strings[random];

            int p1 = left-1;
            int p2 = right+1;

            for(int i = left ; i < p2 ; i++ ){

                if(strings[i].compareTo(temp) < 0){

                    p1++;

                    swap(strings , i , p1);
                }else if(strings[i].compareTo(temp) > 0){

                    p2--;

                    swap(strings , i , p2);
                    i--;
                }
            }

            quickSort(strings , left , p1);
            quickSort(strings , p2 , right);

        }

        public void swap(String[] strings , int i , int j){

            String temp = strings[i];

            strings[i] = strings[j];

            strings[j] = temp;
        }


        public String[] getSuffixStrings(char[] str){

            String[] res = new String[str.length];

            StringBuilder sb = new StringBuilder();

            for(int i = str.length-1; i >=0 ; i--){
                sb.append(str[i]);
                res[i] = sb.toString();
            }

            return res;
        }
    }


    //我自己写的DC3算法
    static
    class Solution2{

        char[] str;

        int[] indexRank; //从index位置开始的字符串的排名
        int[] rankIndex; //排名为i+1的字符串是从index位置开始的

        int[] s0RankIndex;//初始化成-1

        //注意：凡是可以使用的rank一定是从1开始的
        int[] s12RankIndex;
        int[] s12IndexRank;


        public void generateTwoRank(String s){

            str = s.toCharArray();

            //生成s12
            generate_s12_indexRank();
            generate_s12_rankIndex();
            //生成s0
            generate_s0_rankIndex();
            //生成rankIndex
            generate_s012_rankIndex();
            //生成indexRank
            generate_s012_indexRank();
            //完成
        }


        public void generate_s12_rankIndex(){
            //现在有index - 排名数组
            int[] s12_rankIndex = new int[s12IndexRank.length];

            for(int i = 0 ; i< s12IndexRank.length ; i++){

                int rank = s12IndexRank[i];

                if(rank != -1){

                    s12_rankIndex[rank-1] = i;
                }

            }

            s12RankIndex = s12_rankIndex;
        }

        //生成s12
        private void generate_s12_indexRank(){

            //先比较前3位
            List<Queue<Integer>> letterBucketIndex = new ArrayList<>();
            //为什么27：因为超界限的情况是最靠前的，要使用\0这个来代替那个位置，所以要多一位
            for(int i = 0 ; i< 27 ;i ++){

                letterBucketIndex.add(new PriorityQueue<>());
            }//完成初始化

            int[] s12_RankIndex = new int[str.length];
            Arrays.fill(s12_RankIndex , -1);
            int p =0;
            for(int index = 0; index < str.length ; index+=3){

                if(index + 1 < str.length){

                    s12_RankIndex[p] = index+1;
                    p++;
                }

                if(index + 2 < str.length){

                    s12_RankIndex[p] = index + 2;
                    p++;
                }
            }//得到所有的s1和s2的index

            //先比较3位，所以总共要执行三次
            for(int i = 0 ; i < 3 ; i++){

                int plus = 2 - i;// 每个位置判断 i + 2  i+ 1 ,i 的三个位置进行比较
                for(int j = 0; j < p; j++){

                    int index = s12_RankIndex[j];

                    //这个位置是有效的
                    if(index + plus < str.length){

                        letterBucketIndex.get(str[index + plus] - 'a' + 1).add(index);
                    }else{

                        letterBucketIndex.getFirst().add(index);
                    }


                }

                //完成入桶
                p = 0;

                for(int k = 0; k < 27;k++){

                    Queue<Integer> q = letterBucketIndex.get(k);

                    while(! q.isEmpty()){

                        s12_RankIndex[p] = q.poll();
                        p++;
                    }
                }


            }

            //已经经过了3次桶排序

            int[] s12_IndexRank = new int[str.length];
            Arrays.fill(s12_IndexRank , -1);
            int rank = 0;

            boolean checkAgain = false;//是否需要递归调用

            //不需要与前面的进行比较
            s12_IndexRank[s12_RankIndex[0]] = ++rank;
            for(int i = 1; i< p ; i++){

                int index = s12_RankIndex[i];
                int preIndex = s12_RankIndex[i-1];
                if(checkSame(preIndex , index)){

                    s12_IndexRank[index] = rank;
                    checkAgain = true;
                }else{

                    rank++;
                    s12_IndexRank[index] = rank;
                }
            }

            if(checkAgain){

                //新创建字符串
                StringBuilder sb1 = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();

                for(int i =0 ; i< str.length ; i++){

                    if(i % 3 == 1){

                        sb1.append(s12_IndexRank[i]);
                    }else if(i % 3 ==2 ){

                        sb2.append(s12_IndexRank[i]);
                    }
                }//加工得到字符串

                //递归调用
                Solution2 s2 = new Solution2();
                s2.generateTwoRank(sb1.toString() + sb2);
                //得到了新的两个结构

                //填充s1的
                int index = 1;
                int i = 0;
                for(; i< sb1.length(); i++){

                    s12_IndexRank[index] = s2.indexRank[i];
                    index += 3;
                }

                index = 2;
                for(; i< sb2.length() + sb1.length(); i ++){

                    s12_IndexRank[index] = s2.indexRank[i];
                }

                //完成填充
            }

            //s12_IndexRank已经排好了
            s12IndexRank = s12_IndexRank;
        }

        //检查前三位是不是相同的
        private boolean checkSame(int index1 , int index2){

            return str[index1] == str[index2] &&
                    ( (index1 + 1 >= str.length ? 0 : str[index1+1]) == (index2 + 1 >= str.length ? 0 : str[index2 + 1])) &&
                    ( (index1 + 2 >= str.length ? 0 : str[index1+2]) == (index2 + 2 >= str.length ? 0 : str[index2 + 2]));
        }


        //根据rankIndex生成indexRank只需要O(N)的时间复杂度
        public void generate_s012_indexRank(){

            int[] s012indexRank = new int[str.length];

            for(int rank = 1 ; rank <= str.length ; rank ++){

                int index = rankIndex[rank-1];
                s012indexRank[index] = rank;
            }

            indexRank = s012indexRank;
        }

        //合并两个区间
        private void generate_s012_rankIndex(){

            int[] s012RankIndex = new int[str.length];

            int p = 0;

            int ps0 = 0;
            int ps12 = 0;

            while(ps0 < s0RankIndex.length && ps12 < s12RankIndex.length){

                while(ps0 < s0RankIndex.length && s0RankIndex[ps0] == -1){

                    ps0++;
                }
                if(ps0 == s0RankIndex.length){

                    break;
                }

                while(ps12 < s12RankIndex.length && s12RankIndex[ps12] == -1){

                    ps12++;
                }
                if(ps12 == s12IndexRank.length){

                    break;
                }

                int index0 = s0RankIndex[ps0];
                int index12 = s12RankIndex[ps12];

                //首字母排序如果能排序出来
                if(str[index0] > str[index12]){

                    s012RankIndex[p] = index0;
                    ps0++;
                }else if(str[index0] < str[index12]){

                    s012RankIndex[p] = index12;
                    ps12++;
                }else{
                    //相等的情况

                    //这个判断的点属于s1阵营
                    if(index12 % 3 == 1){
                        //下个比较的是s1和s2，一定能比出大小
                        int index1 = index0 + 1;
                        //这是最后一个字符了，后面没有东西了，这个排名优先
                        if(index1 >= str.length){

                            s012RankIndex[p] = index0;
                        }else{

                            int index2 = index12 + 1;
                            if(index2 >= str.length){

                                s012RankIndex[p] = index12;
                            }else{

                                //这是能够进行比较的
                                if(s12RankIndex[index1] > s012RankIndex[index2]){
                                    //选择排名小的
                                    s012RankIndex[p] = index12;

                                }else if(s12RankIndex[index1] < s012RankIndex[index2]){
                                    //其实可以不写这个的，因为保证s12的排序唯一，但是为了方便理解，还是写一下

                                    s012RankIndex[p] = index0;
                                }
                            }


                        }

                    }else if(index12 % 3 == 2){//这个点属于s2阵营
                        //下个比较的是s1和s0
                        int index0to1 = index0 +1;
                        if(index0to1 >= str.length){

                            s012RankIndex[p] = index0;
                        }

                        int index12to0 = index12 + 1;
                        if(index12to0 >= str.length){

                            s012RankIndex[p] = index12;
                        }

                        //下面的情况是可以比较的
                        if(str[index0to1] < str[index12to0]){

                            s012RankIndex[p] = index0;
                        }else if(str[index0to1] > str[index12to0]){

                            s012RankIndex[p] = index12;
                        }else{
                            //还相等，需要继续比较
                            int index0to1to2 = index0to1 + 1;
                            int index12to0to1 = index12to0 + 1;

                            //接下来就一定能比较出结果了
                            if(index0to1to2 >= str.length){

                                s012RankIndex[p] = index0;
                            }else{

                                if(index12to0to1 >= str.length){

                                    s012RankIndex[p] = index12;
                                }else{

                                    if(s12RankIndex[index0to1to2] < s12RankIndex[index12to0to1]){

                                        s012RankIndex[p] = index0;
                                    }else if(s12RankIndex[index0to1to2] > s12RankIndex[index12to0to1]){

                                        s012RankIndex[p] = index12;
                                    }

                                }
                            }



                        }

                    }


                }



                p++;
            }

            //如果有提前超限制的就将剩下的填好
            if(ps0 >= s0RankIndex.length){

                while(ps12 < s12RankIndex.length){
                    while(ps12 < s12RankIndex.length && s12RankIndex[ps12] == -1){

                        ps12++;
                    }
                    if(ps12 >= s12IndexRank.length){

                        break;
                    }

                    s012RankIndex[p] = s12RankIndex[ps12];
                    p++;
                }
            }


            if(ps12 >= s12IndexRank.length){

                while(ps0 < s0RankIndex.length){
                    while(ps0 < s0RankIndex.length && s0RankIndex[ps0] == -1){

                        ps0++;
                    }
                    if(ps0 >= s0RankIndex.length){

                        break;
                    }

                    s012RankIndex[p] = s0RankIndex[ps0];
                    p++;
                }
            }

            //完成了合并
            rankIndex = s012RankIndex;
        }


        //根据s12生成s0
        private void generate_s0_rankIndex(){

            //进行基数排序//为什么+1：1 - 4名会产生4的空间，但是使用下标访问4会超限制
            int[] s0Sort1 = new int[s12IndexRank.length+1];//桶1
            Arrays.fill(s0Sort1,-1);
            //由于s12中是严格排序，所以不会出现同一个出现两次的情况


            //有多少组
            //int groupNum = str.length / 3;
            //得到s0的数量
            //int s0Num = groupNum + (str.length == groupNum * 3 ? 0 : 1);
            s0RankIndex = new int[str.length];
            Arrays.fill(s0RankIndex, -1);//初始化成-1

            //对s0的进行排序//第一次排序，加入桶
            for(int i = 0 ; i< str.length;i+=3){

                //这个s1对应的rank//超出界限的默认用0填充，代表是排序最前面的，没有超出界限的但是+2 超出界限的会交给 s12排序的时候处理
                int s1Rank = i+1 == str.length ? 0 : s12IndexRank[i+1];

                if(s1Rank != -1){

                    s0Sort1[s1Rank] = i;//第一次排序，
                }
            }

            //加入第二个桶
            List<Queue<Integer>> s0Sort2 = new ArrayList<>();
            //完成初始化
            for(int i =0 ; i < 26;i++){

                s0Sort2.add(new PriorityQueue<>());
            }

            //将第一个桶的东西倒出
            for (int index : s0Sort1) {

                //之前被赋值过
                if (index != -1) {

                    s0Sort2.get(str[index] - 'a').add(index);
                }

            }

            int index = 0;
            //第二次倒出
            for(int i =0 ; i < 26 ; i++){

                Queue<Integer> temp = s0Sort2.get(i);

                while(!temp.isEmpty()){

                    s0RankIndex[index] = temp.poll();
                    index++;
                }

            }

            //完成初始化

        }
    }

}
