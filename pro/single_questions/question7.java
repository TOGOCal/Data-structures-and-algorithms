import java.util.Scanner;
import java.util.Stack;

public class question7 {

    /**
     * 题目描述
     * 规定A：
     * 通过任意一个数组arr都可以计算出A，A为数组所有元素的和数组中最小值的积
     * 先给定数组arr，求数组中所有子数组（连续的）中A的最大值
     *
     * 考察知识点：单调栈
     * 思考方向：遍历数组，生成以index位置为最小值的数组
     * 由于需要最大值，所以需要的子数组一定是满足以index位置为最小值的最大数组
     * 所以为了确定数组范围，需要直到数组能够扩展到哪里，这时候据需要去确定数组中每个元素到左右中第一个比它小的数组是哪个
     *
     * 同时由于题目要求明显可以在数组中包含相等的情况例如 2 1 1 有两个相等的1，但是1仍然是数组最小值
     */

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {

            arr[i] = sc.nextInt();
        }

        System.out.println(mainMethod(arr));

        sc.close();
    }

    public static int mainMethod(int[] arr) {

        Information[] info = toGetTheInformation(arr);

        int maxA = Integer.MIN_VALUE;

        for (int index = 0; index < arr.length; index++) {

            int left = 0;
            if (info[index].left != -1) {

                left = info[index].left + 1;
                // left位置比这个数小，如果取到了边界，那这个数就不是最小的数字了
                // 以下的right同理
            }

            int right = 0;
            if (info[index].right != -1) {

                right = info[index].right;
            }

            int sum = 0;

            for (int i = left; i <= right; i++) {

                sum += arr[i];
            }

            maxA = Math.max(maxA, sum * arr[index]);
        }

        return maxA;
    }

    public static Information[] toGetTheInformation(int[] arr) {
        // 目的：得到每个index左右比他小的数字

        Stack<MyLinkedList> stack = new Stack<>();

        Information[] info = new Information[arr.length];

        for (int i = 0; i < arr.length; i++) {

            info[i] = new Information();
        }

        for (int index = 0; index < arr.length; index++) {

            int a = isSuitable(arr, index, stack);

            if (a == 1) {

                MyLinkedList list = new MyLinkedList(index);
                stack.push(list);
            } else if (a == 0) {

                MyLinkedList list = stack.peek();
                list.push(index);
            } else {

                while (isSuitable(arr, index, stack) == -1) {

                    MyLinkedList list = stack.pop();

                    while (!list.isEmpty()) {

                        int i = list.pop();

                        info[i].right = index;

                        if (!stack.isEmpty()) {

                            info[i].left = stack.peek().end.data;
                        }
                    }
                }
            }
        }

        while (!stack.isEmpty()) {

            MyLinkedList list = stack.pop();

            while (!list.isEmpty()) {

                int i = list.pop();

                while (!stack.isEmpty()) {

                    info[i].left = stack.peek().end.data;
                }
            }
        }

        return info;

    }

    public static int isSuitable(int[] arr, int index, Stack<MyLinkedList> stack) {

        if (stack.isEmpty() || arr[stack.peek().end.data] < arr[index]) {

            return 1;
        } else if (arr[stack.peek().end.data] == arr[index]) {

            return 0;
        } else {
            return -1;
        }

    }

    public static class MyLinkedList {

        Node head;
        Node end;

        MyLinkedList() {

            head = new Node(0);
            end = head;
        }

        public boolean isEmpty() {

            return head == end;
        }

        MyLinkedList(int data) {

            head = new Node(0);
            end = head;

            push(data);
        }

        public void push(int data) {

            Node newNode = new Node(data);
            end.next = newNode;
            newNode.pre = end;
            end = newNode;
        }

        public int pop() {

            int a = end.data;

            end = end.pre;
            end.next = null;
            return a;
        }

        private class Node {

            int data;
            Node next;
            Node pre;

            public Node(int data) {

                this.data = data;
                next = null;
                pre = null;
            }
        }
    }

    public static class Information {

        int left;
        int right;

        Information() {

            left = right = -1;
        }

        Information(int left, int right) {

            this.left = left;
            this.right = right;
        }
    }

}
