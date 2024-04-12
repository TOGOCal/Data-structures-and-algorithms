
/**
 * 前缀树优点：时间复杂度为O(1)既可以查出一个字符串
 */

import java.util.Scanner;

public class 前缀树1 {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        boolean key = true;

        PrefixTree tree = new PrefixTree();
        while (key) {

            menu.printMenu_();
            char function = s.nextLine().charAt(0);

            switch (function) {
                case '1':

                    System.out.println("请输入需要增加的字符串:");
                    tree.add(s.nextLine());
                    break;

                case '2':

                    System.out.println("请输入你想要查询的字符串:");
                    System.out.println("该字符串出现了:" + tree.howMany(s.nextLine()) + "次");
                    break;

                case '3':

                    System.out.println("请输入你想要查看的字符:");
                    char c = s.nextLine().charAt(0);
                    System.out.println("有" + tree.howManyChar(c) + "个以" + c + "字符开头的字符串");
                    break;

                case '4':

                    System.out.println("请输入想要删去的字符串:");
                    String str = s.nextLine();
                    tree.delete(str);
                    break;

                default:
                    key = false;
                    break;
            }

            System.out.println("===========================================");
        }
        s.close();
    }
}

class menu {

    public static void printMenu_() {

        System.out.println("请选择你要进行的操作:");
        System.out.println("如果多输入按照第一个输入的为准");
        System.out.println("1.加入字符串      2.判断一个字符串出现了几次");
        System.out.println("3.判断有几个字符串以该字符串开头 4.删除字符串");
        System.out.println("else:关闭程序");
    }
}

class PrefixTree {// 定义整棵树

    private Node top;

    PrefixTree() {
        top = new Node();// 树节点的空开头
        // 头节点不储存数据
    }

    public void add(String str) {

        if (str == null) {

            return;
        }
        Node node = top;// 保存top的地址
        node.passTime++;

        for (int i = 0; i < str.length(); i++) {

            int lenth = str.charAt(i) - 'a';// 路径

            if (node.nxetnode[lenth] == null) {

                // 如果该路径不存在，则创建
                node.nxetnode[lenth] = new Node();
            }
            node.passTime++;// 每加入一个数，则代表通过头节点的数目++
            node = node.nxetnode[lenth];
        }

        node.endTime++;// 最后出循环的时候说明该字符串已结束，该字符的位置就是结束位置
    }

    public void delete(String str) {

        if (howMany(str) == 0) {
            System.out.println("未加入该字符串");
        } else {

            Node node = top;
            node.passTime--;
            char crr[] = str.toCharArray();// 将字符串转化为字符数组

            for (int i = 0; i < crr.length; i++) {

                node.nxetnode[crr[i] - 'a'].passTime--;

                if (node.nxetnode[crr[i] - 'a'].passTime == 0) {

                    node.nxetnode[crr[i] - 'a'] = null;// 相当于下面的无效了，让其指向空即可
                    return;
                }

                node = node.nxetnode[crr[i] - 'a'];
            }

            node.endTime--;
        }
    }

    public int howMany(String str) {// 判断一个字符串出现了几次

        if (str == null) {

            return 0;
        }

        Node node = top;

        for (int i = 0; i < str.length(); i++) {

            int length = str.charAt(i) - 'a';
            if (node.nxetnode[length] == null) {

                return 0;// 没有找到该字母，说明不存在
            }

            node = node.nxetnode[length];
        }

        return node.endTime;
    }

    public int howManyChar(char c) {// 判断有几个字符串以该字符开头

        Node node = top;
        int length = c - 'a';
        if (node.nxetnode[length] == null) {

            return 0;
        }

        return node.nxetnode[length].passTime;
    }

}

class Node {// 定义每个节点
    int passTime;
    int endTime;

    Node nxetnode[];

    Node() {

        passTime = 0;
        endTime = 0;

        // 不能写Node nextNode[]=new Node[26]
        nxetnode = new Node[26];// 目前的前缀树只能存储小写字母
        // 其实要加 大写字母 好像也简单，加个判断就行了
    }
}

// public void append(char c){

// int lenth=c-'a';//代表走哪条路，26个字母依次对应：优点：不用遍历数组查找
// this.passTime++;
// if(this.nxetnode[lenth]==null){

// this.nxetnode[lenth]=new node();//加入时如果没有路径，则创建该路径
// }

// }