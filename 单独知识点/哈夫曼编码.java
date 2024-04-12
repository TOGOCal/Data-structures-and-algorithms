import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class 哈夫曼编码 {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        String str = s.nextLine();

        System.out.println(encoder(str));
        s.close();
    }

    public static String encoder(String str) {

        StringBuilder sb = new StringBuilder();
        char arr[] = str.toCharArray();

        HashMap<Character, Integer> map = new HashMap<>();// 存放对应次数
        HashMap<Integer, Character> map_ = new HashMap<>();// 存放在哈希表中有哪些元素，方便比对

        int index = 0;
        for (int i = 0; i < arr.length; i++) {

            if (map.containsKey(arr[i])) {

                map.put(arr[i], map.get(arr[i]) + 1);
            } else {

                map.put(arr[i], 1);
                map_.put(index, arr[i]);
                index++;
            }
        }

        Node nodes[] = new Node[index];

        for (int i = 0; i < index; i++) {

            char c = map_.get(i);
            nodes[i] = new Node(c, map.get(c));

        } // 存放完了对应次数

        sort(nodes, 0, nodes.length - 1);
        // 开始建树

        while (nodes != null) {

            if (nodes.length == 1) {

                break;
            }

            Node head = new Node((char) 0, nodes[nodes.length - 1].times + nodes[nodes.length - 2].times);
            head.left = nodes[nodes.length - 1];
            head.right = nodes[nodes.length - 2];
            nodes[nodes.length - 1].pre = head;
            nodes[nodes.length - 2].pre = head;

            nodes = delete(nodes, nodes.length - 1);
            nodes = delete(nodes, nodes.length - 1);
            nodes = add(nodes, head);

        }

        Node head = nodes[0];
        addNum(head);

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);

        while (!queue.isEmpty()) {

            Node nowNode = queue.poll();

            if (nowNode.value != 0) {

                sb.append(nowNode.value);
                sb.append(nowNode.sb);
                sb.append('\n');
            }

            if (nowNode.left != null) {

                queue.add(nowNode.left);
            }

            if (nowNode.right != null) {

                queue.add(nowNode.right);
            }
        }
        return sb.toString();
    }

    public static void addNum(Node head) {// 为每个符号添加编号

        if (head == null) {

            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);

        while (!queue.isEmpty()) {

            Node nowNode = queue.poll();
            if (nowNode != head) {

                if (nowNode == nowNode.pre.left) {

                    nowNode.sb.append(nowNode.pre.sb).append('0');
                } else if (nowNode == nowNode.pre.right) {

                    nowNode.sb.append(nowNode.pre.sb).append('1');
                }
            } else if (nowNode.value != 0) {

                nowNode.sb.append('1');
            }

            if (nowNode.left != null) {

                queue.add(nowNode.left);
            }

            if (nowNode.right != null) {

                queue.add(nowNode.right);
            }
        }
    }

    public static Node[] add(Node nodes[], Node node) {

        Node n[] = new Node[nodes.length + 1];

        for (int i = 0; i < n.length - 1; i++) {

            n[i] = nodes[i];
        }

        n[n.length - 1] = node;

        sort(n, 0, n.length - 1);
        return n;
    }

    public static Node[] delete(Node nodes[], int i) {

        Node n[] = new Node[nodes.length - 1];

        for (int ii = 0; ii < n.length; ii++) {

            if (ii < i) {

                n[ii] = nodes[ii];
            } else if (ii > i) {

                n[ii] = nodes[ii - 1];
            }
        }

        return n;
    }

    public static void sort(Node nodes[], int L, int R) {

        if (L >= R) {

            return;
        }

        int compareTimes = nodes[(int) (Math.random() * (R - L + 1) + L)].times;
        int p1 = L - 1;
        int p2 = R + 1;
        for (int i = L; i < p2; i++) {

            if (nodes[i].times > compareTimes) {

                Node node = nodes[i];
                nodes[i] = nodes[p1 + 1];
                nodes[p1 + 1] = node;
                p1++;
            } else if (nodes[i].times < compareTimes) {

                Node node = nodes[i];
                nodes[i] = nodes[p2 - 1];
                nodes[p2 - 1] = node;
                p2--;
                i--;
            }
        }

        sort(nodes, L, p1);
        sort(nodes, p2, R);
    }

    public static class Node {

        char value;
        int times;

        StringBuilder sb;

        Node left;
        Node right;
        Node pre;

        Node() {
            ;
        }

        Node(char value, int times) {

            this.value = value;
            this.times = times;
            sb = new StringBuilder();
        }
    }
}
