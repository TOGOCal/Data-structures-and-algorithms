import java.util.HashMap;
import java.util.HashSet;

public class 哈希表 {

    public static void main(String[] args) {
        // key value
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "这是个1");
        map.put(2, "这是个2");
        map.put(3, "这是个3");

        System.out.println(map.size());// 有几条

        System.out.println(map.containsKey(1));// 有没有一个key是1
        System.out.println(map.containsKey(10));// 找不到就是false

        System.out.println(map.get(3));// 找到key为3对应的value
        System.out.println(map.get(10));// 找不到则返回空

        System.out.println(map.get(2));
        map.put(2, "我是一个2");// 如果put到了相同的key则进行更新
        System.out.println(map.get(2));

        map.remove(2);// 删除key为2的项
        System.out.println(map.containsKey(2));

        System.out.println("===============================================");

        // 只有key
        HashSet<String> set = new HashSet<>();// 只有key，没有value但两个东西底层组织是一样的
        set.add("abc");
        System.out.println(set.contains("abc"));
        set.remove("abc");
        System.out.println(set.contains("abc"));

        // 哈希表，增删改查，时间复杂度都是O(1)，常数时间
        // 哈希表中基本数据类型按照值进行传递，而引用类型则传入地址（8字节

    }
}