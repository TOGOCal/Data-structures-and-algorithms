import java.util.HashMap;

/**
 * 题目背景：
 * 一个人可以有3个信息：
 * 身份证id，github id，b站id
 * 只要对于每个样本，只要有一个信息与之前的信息相同
 * 例如身份证id相同，就可以认为这是同一个人
 * 现给定多个样本，判断这里有几个人
 * 
 * //好像不太对？
 * //算了我不按他讲的来了，我自己整
 * 
 * 不过说起来
 * 如果这每个信息待代表一个人的其中一个信息，那我直接所有String统一处理然后hashMap.size()不是就完了？
 * 
 * 如果每3个信息为一个样本，那我直接取其中一个样本类型做HashMap.size()不也行吗？
 */

public class 并查集题目 {
    public static void main(String[] args) {

    }

    public static class Solution {

        public int howMayPeople(Node[] nodes) {

            int reslut = 0;
            return reslut;
        }

        public class GatherSet<T> {

            HashMap<T, Gather<T>> findGather;// 通过节点找到对应集合

            HashMap<String, T> findId;
            HashMap<String, T> findIdGitHub;
            HashMap<String, T> findIdBStatition;// 通过信息找到节点

        }

        public class Gather<T> {

            T node;

            Gather(T node) {

                this.node = node;
            }
        }
    }

    public static class Node {

        String id;
        String idGitHub;
        String idBStation;

        Node(String str1, String str2, String str3) {

            id = str1;
            idGitHub = str2;
            idBStation = str3;
        }
    }
}
