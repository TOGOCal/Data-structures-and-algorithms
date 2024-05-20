public class 一致性哈希 {

    /**
     * 对于一般的数据库服务器（例如mySQL这样的数据库，
     * 通常是使用一个超强的机器对数据进行统一管理与操作
     *
     * 但是这样的操作方法并不能处理超级大量的数据（再强的机器也会收到当今技术的限制
     *
     * 所以出现了分布式数据库服务器
     * 通过多个数据库服务器对数据进行管理和分析（理论无上限，因为机器可以一直加
     *
     * 如何决定任务的分配
     * 数据端：
     *  尽量选用合适的数据放入哈希函数决定由哪个数据库进行操作
     *  例如：假设你有3个数据库服务器，但是你使用用户所在的国家的名字为key进行任务分配
     *      明显的，有两个机器（甚至一个）会接受超大量的数据信息，同时另外一个的负载很低
     *      （由于哈希函数均匀分布的特点，你并不能使得其他所有国家使用3号服务器，中美占两台，（除非本来就打算这样设计分布式数据库
     *  所以在选择放入哈希函数的key值的时候，选择国家名字不是个好的选择（具体选择什么根据业务需求分析，要求就是不能使得负载分布不均匀
     * 服务端：
     *  原始方法：
     *      假设有三个数据库服务器，将所有数据生成的哈希值模上数据库数量，对要处理的数据进行平均分配
     *      问题时显而易见的，
     *          当业务增加，数据量再次升级，还要再增加服务器数量，这时候却需要将所有的数据进行重新计算并进行分配，
     *          这样的增加明显过于大了
     *  改进方法1：
     *      每个数据库服务端有个唯一标识信息url（或者采用其他什么的唯一特点也行
     *      将url放进哈希函数生成哈希值，进行范围管理
     *      例如：3个服务器，使用md5算法（范围2^64-1） ，这个数据范围本来是个直线，现在将其首尾相连形成环
     *      例如：这三个服务器正好 0  1/3处  2/3处
     *      在处理数据时，将数据生成的哈希值与服务器之间进行范围比较，并存入相应的服务器
     *      （假设选取刚刚大于数据哈希值的节点）没有的话挂在最小的节点上（设计为环的好处
     *      这样，在加入服务器的时候，假设加在了1/3和2/3之间，那需要重新计算的数据就只用一台服务器中的数据了
     *      当服务器的数量更大时，显然的，代价变得更小了
     *      问题是存在的，
     *          在服务器适量较少的时候，有极大的概率导致服务器分配不均匀
     *          就拿刚刚的情况举例子，本来只有任务分配均匀，这下子有一个服务器的任务就被分成两份了，而其他的服务器并没有进行负载减轻
     *          这显然不是一种很好的方法
     *  改进方法2：虚拟节点技术
     *      在改进方法1的基础上，我们给每个机器分配1000（假设）个url，
     *      对这1000个url进行哈希计算，同样的环分配方式进行存储数据
     *      但是由于3000个虚拟节点均匀地分布在环上，这就导致每个服务器的负载在极大水平上被平均了
     *      在加入服务器时，根据这个服务器生成同样数量的虚拟节点
     *      在环上进行计算之后，同样只需要1/服务器数量的代价对数据进行直接的复制迁移
     *  改进3：
     *      在实际的运用中，不同的服务器可能有不同的性能，
     *      这时可以通过虚拟节点技术进行数据分配
     *      比如有 0 1 2 3这四个服务器，其中0的功效最好，3的功效最差，2和1差不多
     *      这时候，我们可以个0生成2000个虚拟节点，给1 和2生成1000个虚拟节点  3生成500个，这时候就完美地利用了每个服务器地性能
     *
     *
     *
     */

    public static void main(String[] args) {

    }
}
