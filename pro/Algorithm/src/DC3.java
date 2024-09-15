//DC3算法(请联系后缀数组食用
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
 *  准备一个长度为Math.max(26,s12.length)的数组，将s0字符串的后面看成一坨东西
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
 *  而这就是真正的排名
 *  时间复杂度：递归调用：O(N) +  O(2/3)，根据master公式，得到这个递归的时间复杂度是O(N)
 *
 */

public class DC3 {

    public int[] rankIndex;

    public int[] indexRank;

    public int[] height;


    //我自己添加的
    //能够传递字符串的
    public DC3(String str){

        int max = 0;
        int[] nums = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {

            nums[i] = str.charAt(i) + 1;//为什么加一：为了让没有位置的字符填充/0，不能让原串中出现/0
            max = Math.max(max, nums[i]);
        }

        new DC3(nums , max);
    }

    public DC3(int[] nums){

        int max = 0;
        for(int num: nums){

            max = Math.max(max, num);
        }

        new DC3(nums, max);
    }

    // 构造方法的约定:
    // 数组叫nums，如果你是字符串，请转成整型数组nums
    // 数组中，最小值>=1
    // 如果不满足，处理成满足的，也不会影响使用
    // max, nums里面最大值是多少
    public DC3(int[] nums, int max) {
        rankIndex = sa(nums, max);
        indexRank = rank();
        height = height(nums);
    }

    //用于生成sa，经过预处理，将保证不会超量程访问，如果需要访问某个元素后两位，就不会超量程访问了
    //因为将量程加了3
    //然后真正用于生成的是skew方法
    private int[] sa(int[] nums, int max) {

        int n = nums.length;
        int[] arr = new int[n + 3];

        for (int i = 0; i < n; i++) {
            arr[i] = nums[i];
        }

        return skew(arr, n, max);
    }

    //真正由于生成sa的（用于生成真正的rankIndex排名与位置的对应
    private int[] skew(int[] nums, int strLength, int maxCharValue) {
        //得到属于s几的数量，分别对应 n0 -> s0   n1 - > s1    n2 -> s2
        int n0 = (strLength + 2) / 3, n1 = (strLength + 1) / 3, n2 = strLength / 3, n02 = n0 + n2;

        //将s1和s2的所有index放入s12这个数组中
        int[] s12 = new int[n02 + 3], sa12RankIndex = new int[n02 + 3];

        for (int i = 0, p = 0; i < strLength + (n0 - n1); i++) {

            if (0 != i % 3) {

                s12[p++] = i;
            }
        }

        //三次铜牌，最终结果放在了sa12中（s12和sa12来回倒
        radixPass(nums, s12, sa12RankIndex, 2, n02, maxCharValue);
        radixPass(nums, sa12RankIndex, s12, 1, n02, maxCharValue);
        radixPass(nums, s12, sa12RankIndex, 0, n02, maxCharValue);

        /**
         * c0 , c1 , c2 相当于是用于记录后三位的比较结果的
         * 后三位 0 +1 +2
         */

        int name = 0, c0 = -1, c1 = -1, c2 = -1;
        for (int i = 0; i < n02; ++i) {
            //只要有一位不相同
            if (c0 != nums[sa12RankIndex[i]] || c1 != nums[sa12RankIndex[i] + 1] || c2 != nums[sa12RankIndex[i] + 2]) {
                name++;

                //记录后三位
                c0 = nums[ sa12RankIndex[i] ];
                c1 = nums[ sa12RankIndex[i] + 1];
                c2 = nums[ sa12RankIndex[i] + 2];
            }

            //完成rankIndex的反向填充 为什么除3：s12位置准备之恩呢放s1和s2的，所以要限定位置
            //顺便将s1的放在前面，s2的放在后面，方便进递归的时候后续下操作
            //此后IndexRank不再是位置-排名的对应，只是一个存储容器
            if (1 == sa12RankIndex[i] % 3) {

                s12[sa12RankIndex[i] / 3] = name;
            } else {//不是1就是2

                s12[sa12RankIndex[i] / 3 + n0] = name;
            }
        }

        //存在重复的
        if (name < n02) {

            //调递归，将s1s2分别放在一起的排名作为数组进入递归
            //由于都是排名，所以这个数组中存在的最大值就是name了
            //得到了s1s2防砸一起的真正排名
            sa12RankIndex = skew(s12, n02, name);

            for (int i = 0; i < n02; i++) {

                s12[ sa12RankIndex[i] ] = i + 1;
            }

        } else {//没有重复的，已经严格排序了（indexRank是准确的
            for (int i = 0; i < n02; i++) {

                //反向得到rankIndex
                sa12RankIndex[s12[i] - 1] = i;
            }
        }

        /**
         * 经过上述步骤后，真正有效的其实是s12IndexRank
         * 因为RankIndex经过嘀咕得到的其实不是在这个意义下的rankIndex
         * 本来的步骤是要更具indexRank生成rankIndex
         * 再由rankIndex得到s0的第一步排序
         */

        //根据s12生成s0的排序
        int[] s0IndexRank = new int[n0], sa0RankIndex = new int[n0];

        //第一次桶排
        for (int i = 0, j = 0; i < n02; i++) {

            if (sa12RankIndex[i] < n0) {

                //这里的IndexRank其实暂时充当的是rankIndex的作用
                //因为下一次桶排会将其倒进RankIndex中
                //为什么*3=》 在之前的s1放一起，s2放一起
                s0IndexRank[j++] = 3 * sa12RankIndex[i];
            }
        }

        //第二次桶排将数据成功导入rankIndex中
        radixPass(nums, s0IndexRank, sa0RankIndex, 0, n0, maxCharValue);


        int[] sa = new int[strLength];

        //k是用来搭配sa使用的
        for (int soPoint = 0, s12Point = n0 - n1, k = 0; k < strLength; k++) {
            int i = sa12RankIndex[s12Point] < n0 ? sa12RankIndex[s12Point] * 3 + 1 : (sa12RankIndex[s12Point] - n0) * 3 + 2;
            int j = sa0RankIndex[soPoint];
            //这个比较是s0和s1的比较，所以只要比较s1和s2就一定能出结果
            if (sa12RankIndex[s12Point] < n0 ? leq(nums[i], s12[sa12RankIndex[s12Point] + n0], nums[j], s12[j / 3])

                    //这个的比较是s0和s2的比较，可能需要比两次才能出结果
                    : leq(nums[i], nums[i + 1], s12[sa12RankIndex[s12Point] - n0 + 1], nums[j], nums[j + 1], s12[j / 3 + n0])) {
                sa[k] = i;
                s12Point++;

                //已经遍历完了某个集合，只需要填充接下来的集合就可以了
                if (s12Point == n02) {
                    for (k++; soPoint < n0; soPoint++, k++) {
                        sa[k] = sa0RankIndex[soPoint];
                    }
                }
            } else {
                sa[k] = j;
                soPoint++;

                //已经遍历完了某个集合，之需要遍历接下来的集合就可以了
                if (soPoint == n0) {
                    for (k++; s12Point < n02; s12Point++, k++) {
                        sa[k] = sa12RankIndex[s12Point] < n0 ? sa12RankIndex[s12Point] * 3 + 1 : (sa12RankIndex[s12Point] - n0) * 3 + 2;
                    }
                }
            }
        }
        return sa;
    }


    /**
     *
     * @param nums 原字符串 加工的数组
     * @param input 需要进行排序的数组
     * @param output 经过本次排序的得到的数组
     * @param offset 这是第几遍排序，分别是 2 1 0 ， 就是此时桶排序需要考虑的字符位置是index + offset
     * @param n 长度限制
     * @param k 所有字符+1后的最大值，由于限定桶的大小
     */
    private void radixPass(int[] nums, int[] input, int[] output, int offset, int n, int k) {
        //所有字符+1后是k，所以能访问到k这个位置，需要将大小+1来访问到k
        int[] cnt = new int[k + 1];

        for (int i = 0; i < n; ++i) {

            //int checkIndex = input[i] + offset; //正在检查的位置
            //char thisChr = nums[checkIndex];
            cnt[nums[input[i] + offset]]++;//其中的数量++
        }

        for (int i = 0, indexInResultArray = 0; i < cnt.length; ++i) {
            int t = cnt[i];//得到字符 i 有几个数字
            cnt[i] = indexInResultArray;//这个字符应该在结果数组中的哪个index
            indexInResultArray += t;
        }

        /**
         * 这个桶排为什么是这么写的：
         * 正常写法是
         * 遍历数组，如果数组中不是i的，就说明这个char对应的数量有这么多个
         * 然后逐渐填充到结果容器中
         *
         * 但是经过这两个for循环，可以做到加速的效果
         * 经过上面的循环可以确定每个元素对应到结果容器中的位置在哪
         * 为什么下面的循环中index要++：
         * 因为可能存在一个字符对应多个的情况出现
         * 所以相同的字符下一次田中的位置是index+1的位置
         */


        for (int i = 0; i < n; i++) {
//            int checkIndex = input[i] + offset;
//            int thisChar = nums[checkIndex];
//            int index = cnt[thisChar];
            output[cnt[nums[input[i] + offset]]++] = input[i];
        }
    }



    private boolean leq(int a1, int a2, int b1, int b2) {
        return a1 < b1 || (a1 == b1 && a2 <= b2);
    }

    private boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
        return a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3));
    }

    private int[] rank() {
        int n = rankIndex.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[rankIndex[i]] = i;
        }
        return ans;
    }


    /**
     *
     * @param s 原始字符串处理后的int数组
     * @return 这个结构中的height
     */
    private int[] height(int[] s) {
        int n = s.length;//得到字符串长度
        int[] ans = new int[n];

        //rank是排名数组，index-rank
        for (int i = 0, k = 0; i < n; i++) {
            if (indexRank[i] != 0) {
                if (k > 0) {
                    --k;
                }
                int j = rankIndex[indexRank[i] - 1];
                while (i + k < n && j + k < n && s[i + k] == s[j + k]) {
                    ++k;
                }
                ans[indexRank[i]] = k;
            }
        }
        return ans;
    }


    //==================================================================================================================
    // 为了测试//生成随机字符串
    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    // 为了测试(时间测试
    public static void main(String[] args) {
        int len = 100000;
        int maxValue = 100;
        long start = System.currentTimeMillis();
        //new DC3(randomArray(len, maxValue), maxValue);
        DC3 d = new DC3("mississippi");
        long end = System.currentTimeMillis();
        System.out.println("数据量 " + len + ", 运行时间 " + (end - start) + " ms");
    }

}