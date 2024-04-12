import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 给定T类型的几个对象a,b,c,d......
 * 现要求实现几个功能
 * 查询Ti和Tj是否在一个集合中isSameUnion
 * 合并Ti和tj所在的两个集合mergeUnion
 * 
 * 现要求构建这两个函数
 */

public class 并查集 {
    public static void main(String[] args) {

    }

    public static class UnionSet<T> {

        HashMap<T, Union<T>> findUnionMap;// 传入某个T的对象，找到其对应的节点

        HashMap<Union<T>, Union<T>> findSymbolMap;// 从某个节点，找到那个节点所在集合的代表节点

        HashMap<Union<T>, Integer> findNumMap;// 找到某个对应集合的大小

        UnionSet(List<T> list) {

            for (T nowT : list) {

                Union<T> union = new Union<T>(nowT);
                findUnionMap.put(nowT, union);
                findSymbolMap.put(union, union);// 最初每个对象自己就是一个集合
                findNumMap.put(union, 1);
            }
        }

        public Union<T> findSymbol(Union<T> union) {

            Stack<Union<T>> stack = new Stack<>();

            while (union != findSymbolMap.get(union)) {// 向上找节点，直到某个点就是这个集合的代表点//就找到了代表点

                stack.push(findSymbolMap.get(union));
                union = findSymbolMap.get(union);
            }

            while (!stack.isEmpty()) {// 栈里面储存的是这里一路上遇到的点
                // 通过节点没有第一时间找到集合代表点说明这一路上的点也没有建立起与代表点的直接联系
                // 则将这一路上的点的集合代表点全部设置正确，方便寻找

                findSymbolMap.put(stack.pop(), union);
            }

            return union;
        }

        public boolean isSameUnion(T a, T b) {

            if (!findUnionMap.containsKey(a) || !findUnionMap.containsKey(b)) {

                // 如果在节点->集合对应表中没有找到这个点，说明当初加入的时候就没有加入，就是说a或者b不存在
                return false;
            }

            return findSymbol(findUnionMap.get(a)) == findSymbol(findUnionMap.get(b));
        }

        public void mergeUnion(T a, T b) {

            if (!findUnionMap.containsKey(a) || !findUnionMap.containsKey(b)) {

                return;
            }

            Union<T> aSymbol = findSymbol(findUnionMap.get(a));
            Union<T> bSymbol = findSymbol(findUnionMap.get(b));

            if (aSymbol != bSymbol) {

                int aSymbolSize = findNumMap.get(aSymbol);
                int bSymbolSize = findNumMap.get(bSymbol);

                // 谁小就挂在另一个头上
                if (aSymbolSize > bSymbolSize) {

                    findSymbolMap.put(bSymbol, aSymbol);
                    findNumMap.put(aSymbol, findNumMap.get(bSymbol) + findNumMap.get(aSymbol));
                    findNumMap.remove(bSymbol);
                } else {

                    findSymbolMap.put(aSymbol, bSymbol);
                    findNumMap.put(bSymbol, findNumMap.get(bSymbol) + findNumMap.get(aSymbol));
                    findNumMap.remove(aSymbol);
                }
            }
        }

    }

    public static class Union<T> {

        T value;

        Union(T n) {

            this.value = n;
        }
    }// 将node节点包在了一个Union中

    public static class Node {
        ;
    }
}
