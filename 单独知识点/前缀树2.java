import java.util.Scanner;
import java.util.HashMap;

public class 前缀树2 {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        PrefixTree2 tree = new PrefixTree2();
        boolean key = true;

        while (key) {

            menu2.printMenu2();
            char function = s.nextLine().charAt(0);
            switch (function) {
                case '1':

                    System.out.println("请输入需要增加的字符串:");
                    tree.addNode(s.nextLine());
                    break;

                case '2':

                    System.out.println("请输入你想要查询的字符串:");
                    System.out.println("该字符串出现了:" + tree.howmany(s.nextLine()) + "次");
                    break;

                case '3':

                    System.out.println("判断有几个是以该字符串开头的:");
                    String str = s.nextLine();
                    System.out.println("有" + tree.howmanyChar(str) + "个以" + str + "字符串开头的字符串");
                    break;

                case '4':

                    System.out.println("请输入想要删去的字符串:");
                    String st = s.nextLine();
                    tree.deleteNode(st);
                    break;

                default:
                    key = false;
                    break;

            }

            System.out.println("====================================");
        }
        s.close();
    }
}

class menu2 {

    public static void printMenu2() {

        System.out.println("请选择你要进行的操作:");
        System.out.println("如果多输入按照第一个输入的为准");
        System.out.println("1.加入字符串      2.判断一个字符串出现了几次");
        System.out.println("3.判断有几个字符串以该字符串开头 4.删除字符串");
        System.out.println("else:关闭程序");
    }
}

class PrefixTree2 {

    private Node2 top;

    PrefixTree2() {

        top = new Node2();
    }

    public void addNode(String str) {

        char arr[] = str.toCharArray();
        Node2 node = top;

        node.passTime++;

        for (int i = 0; i < arr.length; i++) {

            int index = (int) arr[i];
            if (!node.nextNode.containsKey(index)) {// 不存在即返回false，if是true的时候才执行

                node.nextNode.put(index, new Node2());// 创建新的节点
            }

            node = node.nextNode.get(index);// 方法返回值是一个Node2类型的值，所以可以这样左（要不还是按照指针理解？
            node.passTime++;
        }

        node.endTime++;
    }

    public void deleteNode(String str) {

        if (howmany(str) == 0) {

            System.out.println("没有添加过该字符串");
            return;
        }

        Node2 node = top;

        char crr[] = str.toCharArray();

        for (int i = 0; i < crr.length; i++) {

            int path = (int) crr[i];

            node.nextNode.get(path).passTime--;

            if (node.nextNode.get(path).passTime == 0) {

                node.nextNode.remove(path);// 直接调用函数移走
                return;
            }

            node = node.nextNode.get(path);
        }

        node.endTime--;
    }

    public int howmany(String str) {// 有多少个该字符串

        Node2 node = top;

        char crr[] = str.toCharArray();

        for (int i = 0; i < crr.length; i++) {

            int path = (int) crr[i];
            if (!node.nextNode.containsKey(path)) {

                return 0;
            }

            node = node.nextNode.get(path);
        }

        return node.endTime;
    }

    public int howmanyChar(String str) {

        Node2 node = top;
        char crr[] = str.toCharArray();

        for (int i = 0; i < crr.length; i++) {

            int path = (int) crr[i];

            if (!node.nextNode.containsKey(path)) {

                return 0;
            }

            node = node.nextNode.get(path);
        }

        return node.passTime;
    }
}

class Node2 {

    int passTime;
    int endTime;
    HashMap<Integer, Node2> nextNode;// 用哈希表来表示路径
    // integer 达标传入的符号的ascII（Unicode）码值

    Node2() {

        passTime = 0;
        endTime = 0;
        nextNode = new HashMap<>();
    }
}