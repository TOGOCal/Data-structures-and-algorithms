
/**
 * 重写比较器的方法应该是给系统提供的数据结构使用的
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.PriorityQueue;

public class 比较器 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        int num = s.nextInt();
        s.nextLine();

        student stu[] = new student[num];
        PriorityQueue<student> heep = new PriorityQueue<>(new myCompare1());// 可以实现自定义的排序
        // 通过这种方法可以让系统默认的小根堆变成大根堆

        for (int i = 0; i < num; i++) {

            int id = s.nextInt();
            int age = s.nextInt();
            String name = s.nextLine();

            stu[i] = new student(id, name, age);

            heep.add(stu[i]);
        }

        while (!heep.isEmpty()) {

            System.out.println(heep.poll().toString());
        }

        System.out.println("==================================");

        Arrays.sort(stu, new myCompare2());// 调用系统自带的排序函数（对所有类都可以实现
        // 第一个输入对象名（注意不是类名
        // 后面可以加入自定义的排序标准，比如我这样写的就是从大到小排列

        for (student temp : stu) {

            System.out.println(temp.toString());
        }
        s.close();
    }
}

class student {

    int id;
    String name;
    int age;

    student(int id, String name, int age) {

        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return ("id:" + id + "\tname:" + name + "\tage:" + age);
    }
}

class myCompare1 implements Comparator<student> {

    @Override
    public int compare(student o1, student o2) {

        return o1.id - o2.id;
    }// 默认返回-1的情况就是o1放在前面(传入的o1)
     // 返回值是负数时就代表o1，o2如今顺序不用调整？
     // o1在前
     // 返回0就是认为两个一样

    // 自己的话：如果返回值是正则交换两个数的位置
}

class myCompare2 implements Comparator<student> {

    @Override
    public int compare(student o1, student o2) {

        // return o2.id - o1.id;// 目标：实现从大到小排序

        // 自己的话：如果返回值是正则交换两个数的位置
        // 由于是从大到小排序，所以需要交换的情况是o1<o2的情况
        // 此时o2 - o1为正，执行交换操作，故如此书写程序

        return o2.id != o1.id ? (o2.id - o1.id) : (o1.age - o2.age);
        // 如果id相同，则按照年龄排序
        // 年龄升序
    }

}