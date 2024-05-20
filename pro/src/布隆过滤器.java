import java.util.Scanner;

public class 布隆过滤器 {

    /**
     * 布隆过滤器使用场景：
     * 当需要一个类似黑名单系统的时候，（存放的地址不允许用户访问
     * 或者在使用爬虫进行网站爬取的时候，为了效率需要使得同样的网站不被爬第二遍
     * 这样就需要一个结构 执行 加入和查询操作 （不能使用删除操作
     * 达成这样目的的数据结构就是布隆过滤器
     *
     * 布隆过滤器特点：
     * 放进去的东西一定可以检查存在
     * 但是没有放进去过的东西也有可能被检查出错误
     * （宁可错杀1000，不可放过1个
     *
     * 以下是布隆过滤器设计基本流程：
     * 1.存放问题解决：
     *  对于经典的查询操作，当然可以使用hashSet进行置入，查询，但是
     *  假设数据量为100亿，大概需要640G的内存去支持hashSet的存在，代价过于高（无论软件硬件
     *  这就需要新的数据结构：位图（设计流程见 位图 的类设计（在此类中
     *
     * 2.哈希碰撞问题优化：
     *  对于极大的数据量（依然以100亿举例，哈希碰撞的几率就被大大增大了
     *  更别说最后这个哈希函数的范围还被模上了一个大小m
     *  为了解决这个问题，我们可以让这个数据去经过多个不同种哈希函数的调用，得到不同的数据
     *  同时将所有的数据都存入位图
     *  查询时，
     *      让数据经过这多个相同的哈希函数，只有所有的生成数据位置都被描黑，才代表这个数据被存储过
     *      原理：不同哈希函数对于相同数据产生哈希碰撞的概率极低（直接将原来哈希碰撞的几率多次方了，你说低不低
     *  错误判定原因：
     *      次：多个哈希函数都产生了相同的哈希值（额，这。。不好评价
     *      主：其他的数据加入时正好将所有位图中的地方都描黑了（可通过增加位图大小减轻次症状
     *
     * 3.对于查询操作的描述
     *  只有当全部的查找哈希函数返回值都是true的时候才是代表加入过
     *  因为加入的时候一定会将所有的位置位好
     *  但是如果有一个没有置位，就说明其他的位可能是其余的加入的值置位好的，不是这个数置位的
     */

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int sampleSize = s.nextInt();//样本量

        double errorRate = s.nextDouble();//失误率
        s.nextLine();

        ProcessingData processingData = new ProcessingData(sampleSize , errorRate);

        BitMap bitMap = new BitMap(processingData.sizeByCalculating , processingData.numberOfUseHashCode);

        String data = s.nextLine();

        System.out.println(bitMap.checkAdd(data));
        bitMap.add(data);
        System.out.println(bitMap.checkAdd(data));

        s.close();
    }

    private static class ProcessingData{
        /**
         * 对数据进行分析：
         * 得到合适的位图大小，哈希函数调用种类，真实概率等数据
         *
         * 关于为什么要处理数据：
         * 对于每一种情况
         * 如果位图大小太大，那和直接使用hashSet的内存优势体现就不明显了
         * 如果位图太小（那只有1bit举例子，只要这个被置位成1了，那之后不管任意产找都返回加入过数据
         * 所以位图大小要合适
         *
         * 如果哈希函数调用过少，则会导致一定的出错概率
         * 如果哈希函数调用过多：拿8bit位图举例子
         *         如果调用8个哈希函数产生了8个值，那就直接将位图填满了，之后查询任意数据都是查询过
         * 所以当哈希函数调用超过一定值之后出错几率反而会提升
         * 所以要确定哈希函数的调用种类个数
         *
         */

        int sizeByCalculating;//理论计算：需要多少位
        int numberOfUseHashCode;//真实数据：需要调用多少个哈希函数
        double realErrorRate;//在此基础上，误判概率是多少

        public ProcessingData(int sampleSize, double errorRate){
            //公式全部记忆

            sizeByCalculating = (int)(-1* sampleSize * Math.log(errorRate) /  Math.pow( Math.log(2) ,2 ));
            /**
             * 公式1：理论需要多少位：
             *      -1 * 样本量 * In( 需求错误率 ) / ( In(2)的平方 )
             * -1的来源：错误率小于1，所以 In这一坨一定是负的
             */

            double calculateNumberOfUseHashCode = Math.log(2) * ( (double) sizeByCalculating / sampleSize);
            /**
             * 公式2：理论需要多少个哈希函数调用：
             *      In(2) * (计算需要的的位数 / 样本量)   约等于 In(2) 约等于 0.7
             * 实际需要的哈希函数个数一定是向上取整的
             */
            numberOfUseHashCode =
                    (int) (calculateNumberOfUseHashCode +
                            (calculateNumberOfUseHashCode > ((int)calculateNumberOfUseHashCode) ? 1 : 0));


            //以下使用的help数据都是为了使得公式看起来没有这么长使用的辅助变量，没有别的作用
            double help1 = ( (double) sampleSize * numberOfUseHashCode ) / sizeByCalculating;
            realErrorRate = Math.pow( 1 - Math.pow(Math.E, -help1) , numberOfUseHashCode );
            /**
             * 公式3：真实的失误几率：
             *      [1 - e 的 负的( 样本量 * 真实使用的哈希函数个数 / 真实的位图大小 ) 次方 ]的 真实使用哈希函数个数 次方
             * 真实的位图大小（以位做单位）
             * 虽然真实概率肯定是使用真实的位图大小和真实的哈希函数使用次数决定的
             * 但是在数据量足够的时候，真实的位图大小和计算的位图大小并没有差别
             */
        }

    }

    private static class BitMap{//位图
        /**
         * 设计原理思考流程：
         * 1.通过桶排序进行类比，
         *  如果这个数据在0-100之间，我创建一个100int的桶
         *  比如出现了99，就将99设为1，其余为0，查找时直接arr[99]
         *  但是这种方法占用的空间几乎没有变小
         *
         * 2.形式转化
         *  既然状态只有1和0，那我可以直接使用int数据二进制中某个位是不是1来判断算
         *  例如：
         *      原 0000 0000
         *      加入后：0001 0000 ，那我就知道了某个数据被加入过
         *
         * 3.数据类型不同解决：
         *  以上描述的处理方法仅能用于int的寻找，显然，对于更多的使用场景，这完全不能够支持
         *  解决方法：使用哈希函数，就可以将所有的数据类型统一用int类型的数据表示
         *
         */

        int[] arr;
        int numberOfUseHashCode;//哈希函数调用次数

        public BitMap(int sizeByCalculating , int numberOfUseHashCode){
            //sizeByCalculating时计算时产生的理论结果，比如计算结果为15，但是我需要使用int类型的值去拼接，就需要向上取整
            int size = sizeByCalculating/8 + (sizeByCalculating%8 == 0 ? 0 : 1);//向上取整

            arr = new int[size];//完成位图初始化

            this.numberOfUseHashCode = numberOfUseHashCode;//调用次数
        }

        public void add(Object object){

            //调用多个哈希函数的步骤就不在此处使用了这里调用一个就行了
            int place = object.hashCode() %(arr.length *8);
            setPlace(place);
        }

        private void setPlace(int place){
            //将某一个位置置成1
            int index = place/8;//在数组哪个位置
            int indexDetail = place%8;//在这个int类型数据的哪个位置

            arr[index] = arr[index] | (1<<indexDetail);
        }

        public boolean checkAdd(Object object){
            //同样的,多次生成的步骤就不在这里演示了
            int place = object.hashCode();
            place = place % (arr.length *8);//模上位图大小

            boolean key = true;

            key = key& (getPlace(place) == 1);
            //注：多次调用得进循环

            return key;
        }

        private int getPlace(int place){
            //拿到某个位置个数
            int index = place/8;
            int indexDetail = place%8;

            return (arr[index]>>indexDetail &1) ;
        }

    }
}
