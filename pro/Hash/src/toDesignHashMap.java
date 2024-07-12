public class toDesignHashMap {

    /**
     * 哈希函数经典设计逻辑：(理论上logN
     * 先给定一个值a，代表算出来的哈希值需要模的数大小
     *
     * 再给定一个数num，代表每个单链表最多能串的链
     *
     * 操作：
     * 输入一个对象class ， 使用哈希函数生成哈希值h
     * 使用h%a ，代表这个数要挂在哪里
     * 这个挂在哪里的对象相当于一个单链表，每次加入时直接在链表后面挂 key-value就行了
     *
     * 现在的问题是，如果单链表一直往后面挂，哈希表查找的时间复杂度O(1)就不能达到
     * 所以设定这个数字num，一旦某个链表的长度超过了num，出发哈希表的扩容操作
     *
     * 将a*2，再重新计算里面已经有的所有值，得到新的表
     *
     * 在查询时直接重复计算 - 查找对应 - 进行查找
     *
     * 时间复杂度计算：
     * 调用哈希函数算哈希值：O(1) 但是这个常数比较大（相对
     * 模a 时间O(1)
     * 遍历链表：（链表长度num，是一个常数，所以这一步相当于也是常数的时间操作
     * 扩容操作：加入n个数据，扩容至多log(n)次(肯定保证是这个级别的数
     *         每次扩容要重新计算全部加过的数，相当于O(N)级别的时间复杂度
     *         所以可以认为总共扩容代价N*log(N),总共操作的数据是N个数据，这就意味着每个平均每个数据log(N)的时间复杂度
     *         但是，我们可以设定更长的单链表，比如单链表num设置为10，对于查找而言这认识常数项的时间复杂度
     *         但是可以使得平均每步的log(N)直接简化成log10(N)，这对于我们时常能够接触到的数字而言是非常非常小数了（也可认为是常数操作时间
     *
     * 注：具体语言中可能有改进
     * java：单链表改成有序数组
     * 以及其他的改进
     * 例如：（了解就行了
     * 开放地址法
     */

    /**
     * 离线扩容技术(使得使用的时间复杂度来到O(1)
     * 对于像java这种依赖虚拟机的语言而言，就算用户不再使用程序，JVM仍然在运作
     * 这就可以使得扩容操作不影响用户的使用，使得使用时的时间复杂度来到O(1)
     * （c++就不行
     *
     */

    public static void main(String[] args) {

    }
}