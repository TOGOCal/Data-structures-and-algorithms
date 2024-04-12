import java.util.TreeMap;

public class 有序表 {
    public static void main(String[] args) {
        // 哈希表中所有的增删改查有序表都支持
        // 同时，你可以乱序输入进有序表，但在其中是按照顺序拍好的

        TreeMap<Integer, String> treeMap = new TreeMap<>();

        treeMap.put(3, "这是个3");
        treeMap.put(1, "这是个1");
        treeMap.put(2, "这是个2");// 增
        treeMap.put(4, "这是个4");

        System.out.println(treeMap.containsKey(3));// 查
        System.out.println(treeMap.get(3));

        treeMap.put(3, "我是3");// 改

        treeMap.remove(3);// 删

        System.out.println("======================================");

        System.out.println(treeMap.firstKey());// 打印最小的key
        System.out.println(treeMap.lastKey());// 打印最大的key

        System.out.println(treeMap.floorKey(3));// 小于等于2最大的key
        System.out.println(treeMap.floorKey(4));// 大于等于4最大的key//包括

        // 时间复杂度为O(logN)
        // 非基础类型之后会讲
    }
}
