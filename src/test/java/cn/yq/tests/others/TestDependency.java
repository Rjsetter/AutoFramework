package cn.yq.tests.others;

import org.testng.annotations.Test;


public class TestDependency {
    @Test(groups = {"a"}, dependsOnGroups = {"c"})
    public void testa() {
        System.out.println("A");
    }

    @Test(groups = {"b"}, dependsOnGroups = {"d"})
    public void testb() {
        System.out.println("B");
    }

    @Test(groups = {"c"}, dependsOnGroups = {"b"})
    public void testc() {
        System.out.println("C");
    }

    @Test(groups = {"d"})
    public void testd() {
        System.out.println("D");
    }
}
