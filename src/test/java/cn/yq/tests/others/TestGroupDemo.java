package cn.yq.tests.others;

import org.testng.annotations.Test;

public class TestGroupDemo {
    @Test(groups = {"T1", "T2"})
    public void test1() {
        System.out.println("这是T1,T2组的test1!");
    }

    @Test(groups = {"T1", "T2"})
    public void test2() {
        System.out.println("这是T1,T2组的test2!");
    }

    @Test(groups = {"T1", "broken"})
    public void test3() {
        System.out.println("这是只在T1中的test3！");
    }

    @Test(groups = {"windows.checkintest"})
    public void testWindowsOnly() {
        System.out.println("这是testWindowsOnly");
    }


    @Test(groups = {"linux.checkintest"})
    public void testLinuxOnly() {
        System.out.println("这是testLinuxOnly");
    }

    @Test(groups = {"windows.functest"})
    public void testWindowsToo() {
        System.out.println("这是testWindowsToo");
    }
}
