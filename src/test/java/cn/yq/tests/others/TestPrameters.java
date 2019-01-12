package cn.yq.tests.others;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestPrameters {
    @Parameters({"name"})
    @Test
    public void TestSingleString(String name) {
        System.out.println("输入的参数为：" + name);
        assert "test".equals(name);
    }


}
