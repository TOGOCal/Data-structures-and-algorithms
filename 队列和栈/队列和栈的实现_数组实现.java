import java.util.Scanner;

public class 队列和栈的实现_数组实现 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("设置栈或者队列的大小：");
        int size = s.nextInt();
        s.nextLine();

        int arr[] = new int[size];

        System.out.print("输入你想要的模式:");
        System.out.println("1.栈\t2.队列");

        char function = s.nextLine().charAt(0);

        if (function == '1') {

            int index = 0;
            while (index < size) {

                System.out.println("1.入栈     2.出栈");
                System.out.print("操作：");
                char f1 = s.nextLine().charAt(0);
                if (f1 == '1') {

                    arr[index] = s.nextInt();
                    s.nextLine();

                    index++;
                }

                else if (f1 == '2') {
                    if (index == 0) {

                        System.out.println("没有元素了");
                        continue;
                    }
                    index--;
                    System.out.println(arr[index]);
                }

                else {
                    break;
                }

                System.out.println("==============================");
            }

            if (index == size) {
                System.out.println("栈溢出");
            }

        }

        if (function == '2') {

            int inindex = 0;
            int outindex = 0;
            int nowsize = 0;

            while (nowsize < size) {

                System.out.println("1.入队列    2.出队列");
                char f1 = s.nextLine().charAt(0);
                if (f1 == '1') {

                    arr[inindex] = s.nextInt();
                    s.nextLine();
                    inindex++;
                    inindex = change(size, inindex);// 实现环线存放
                    nowsize++;
                }

                else if (f1 == '2') {
                    if (nowsize == 0) {
                        System.out.println("队列为空");
                        System.out.println("=====================================");
                        continue;
                    }

                    System.out.println(arr[outindex]);
                    outindex++;
                    outindex = change(size, outindex);
                    nowsize--;
                }

                else
                    break;

                System.out.println("====================================");
            }

            if (nowsize == size) {
                System.out.println("队列已满");
            }
        }
        s.close();
    }

    public static int change(int size, int index) {

        if (index == size) {
            return index - size;
        } else if (index < 0) {
            return index + size;
        }
        return index;
    }
}

/**
 * 进阶1：如何返回栈中最小值返回
 * 设置两个栈
 * 一个经典栈，一个getmin
 * 经典栈里面存放下一个数
 * getmin栈；里，每次加入时，新加入的数与getmin栈顶谁小加入谁
 * 弹出时同步弹出
 * 7 2 2 3 5 4 4 4 底
 * 2 2 2 3 4 4 4 4 底
 * ->压入方向
 */

/**
 * 进阶2：用队列实现栈
 * 设置两个队列，data和help，data正常压入
 * 要弹出时，将data中 除了最后一个数据之外 的数据全部导入help中，之后data弹出最后元素
 * 之后data与help交换（名字交换，data作为help使用，help作为data使用
 */

/**
 * 进阶3：用栈实现队列
 * 设置两个栈，一个pop栈，一个push栈
 * 往push栈中压入数据
 * 需要弹出时，若pop栈为空，则将push压入pop中，12345->54321
 * 再进弹出，压入时依然往push栈中压入数据
 */
