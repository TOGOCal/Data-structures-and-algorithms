import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 问题背景，实现排队快乐值最大
 * 现在公司要办派对，规则如下：
 * 1.如果邀请某个人来，则其所有直系下属不回来
 * 2.派对快乐值是所有到场员工快乐值的累加
 * 3.你的目标是让派对的快乐值最大
 * 4。给定boss节点（不具有直系上级），返回派对最大快乐值
 */

public class 二叉树相关问题4 {
    public static void main(String[] args) {

    }

    public static class Node {

        int happyVlaue;
        List<Node> nexts;

        Node(int happyVlaue) {

            this.happyVlaue = happyVlaue;
            nexts = new ArrayList<>();
        }
    }

    public static class Solution {

        public static int question(Node boss) {

            if (boss == null) {

                return 0;
            }
            Information ifo = ryCheck(boss);// 额。。。关于boss来不来的问题不是程序员考虑的了，就酱
            return Math.max(ifo.beInvite, ifo.notbeInvite);
        }

        public static Information ryCheck(Node node) {

            // if (node == null) {

            // return new Information(0, 0);
            // }

            if (!node.nexts.isEmpty()) {

                return new Information(node.happyVlaue, 0);
            } // 上面的可以优化成这样

            int beInvite = node.happyVlaue;// 被邀请则子节点都不被邀请
            int notbeInvite = 0;// 没有被邀请则子节点可以选择是否被邀请
            for (int i = 0; i < node.nexts.size(); i++) {

                Information ifo = ryCheck(node.nexts.get(i));
                beInvite += ifo.notbeInvite;// 子节点都不被邀请
                notbeInvite += Math.max(ifo.beInvite, ifo.notbeInvite);// 子节点可以被邀请也可以不被邀请，最终需要最大，所以那个大邀请谁
            }

            return new Information(beInvite, notbeInvite);
        }

        public static class Information {

            int beInvite;// 某个节点被邀请的最大欢乐值（包括下面返回来的信息，下同
            int notbeInvite;// 某个节点没有被邀请的最大换了之

            Information(int beInvite, int notbeInvite) {

                this.beInvite = beInvite;
                this.notbeInvite = notbeInvite;
            }
        }
    }

}
