import java.util.Scanner;

/**
 * 快速反转
 */

public class 单链表的反转 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        toLink_LinkList test = new toLink_LinkList();
        boolean key = true;

        while (key) {

            todomenu.printmenu();
            char a = s.nextLine().charAt(0);
            switch (a) {
                case '1':
                    test.printList();
                    break;

                case '2':

                    System.out.print("输入id:");
                    int id = s.nextInt();
                    s.nextLine();

                    System.out.print("输入name:");
                    String name = s.nextLine();

                    LinkList l = new LinkList(id, name);
                    test.addList(l);
                    break;

                case '3':
                    System.out.print("想要修改节点的id:");
                    int id_ = s.nextInt();
                    s.nextLine();

                    System.out.print("想要修改为什么:");
                    String name_ = s.nextLine();

                    LinkList l2 = new LinkList(id_, name_);
                    test.changeList(l2);

                    break;

                case '4':// 删除节点（还没看懂
                    System.out.print("想要删除的那个节点的id:");
                    int _id = s.nextInt();
                    s.nextLine();
                    test.deleteList(_id);
                    break;

                case '5':

                    test.changeDirection();
                    break;

                default:
                    key = false;
                    break;
            }
            System.out.println();
        }

        s.close();
    }
}

class todomenu {

    public static void printmenu() {

        System.out.println("请选择你需要进行的操作：");
        System.out.println("如果多输入按照第一个输入的数字为准");
        System.out.println("1.打印链表\t2.添加节点");
        System.out.println("3.修改节点\t4.删除节点");
        System.out.println("5.反转单链表");
        System.out.println("else:退出程序");
        System.out.println("======================================================");

    }

}

class toLink_LinkList {

    LinkList head = new LinkList(0, "");// 构建表头

    public void printList() {// 打印方法

        if (head.next == null) {

            System.out.println("链表为空");
            return;
        }

        LinkList temp = head.next;

        while (temp != null) {

            System.out.println(temp.myprint());
            temp = temp.next;
        }
    }

    public void addList(LinkList l) {// 添加方法

        LinkList temp = head;
        while (temp.next != null) {

            temp = temp.next;
        }
        temp.next = l;
    }

    public void changeList(LinkList l) {// 修改方法

        if (head.next == null) {

            System.out.println("链表为空");
        }

        LinkList temp = head;

        while (temp != null) {

            if (temp.id == l.id) {

                temp.name = l.name;
                System.out.println("修改成功");
                return;
            }
            temp = temp.next;
        }
        System.out.println("没有找到对应编号");
    }

    public void deleteList(int id) {// 删除方法

        if (head.next == null) {

            System.out.println("链表为空");
            return;
        }

        LinkList temp = head;

        while (temp.next != null) {

            if (temp.next.id == id) {

                temp.next = temp.next.next;
                System.out.println("修改成功");
                return;
            }
        }
        System.out.println("没有找到对应编号");
    }

    public void changeDirection() {// 改变表指向的方向

        if (head.next == null) {

            System.out.println("链表为空");
        }

        LinkList temp = head.next;
        LinkList prev = null;
        LinkList next = null;
        while (temp != null) {

            next = temp.next;// 将该节点下一个节点储存
            temp.next = prev;// 将该节点的下一节点指向之前的节点
            prev = temp;// 将现在的节点作为下一次循环的pre节点
            temp = next;// 将节点向后移动
        }

        head.next = prev;
    }
}

class LinkList {// 构建单个节点

    int id;
    String name;
    LinkList next;

    LinkList(int id, String name) {

        this.id = id;
        this.name = name;
    }

    String myprint() {

        return ("id是:" + id + "\n名字是:" + name);
    }
}
