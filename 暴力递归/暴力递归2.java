
/**
 * 逆序一个栈
 * 不能申请额外的数据结构
 * 只能使用递归函数
 */

import java.util.Scanner;
import java.util.Stack;

public class 暴力递归2 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        Stack<Integer> stack = new Stack<>();

        int n = s.nextInt();
        for (int i = 0; i < n; i++) {

            stack.push(i);
        }

        System.out.println(stack);

        upSideDown(stack);
        System.out.println(stack);

        s.close();
    }

    public static void upSideDown(Stack<Integer> stack) {

        if (stack.isEmpty()) {

            return;
        }

        int a = buttoon(stack);
        upSideDown(stack);
        stack.push(a);
    }

    public static int buttoon(Stack<Integer> stack) {// 返回的是栈底的值,同时删除这个栈底的元素

        int value = stack.pop();

        if (stack.isEmpty()) {

            // 如果栈已经空了，说明这个就是栈底的元素，直接返回这个元素
            return value;
        } else {
            int result = buttoon(stack);// 如果栈没有空，则现在弹出的元素不是栈底的元素，先调用button函数找到最小的元素，应该将刚刚弹出的其压回去
            stack.push(value);
            return result;
        }
    }
}
