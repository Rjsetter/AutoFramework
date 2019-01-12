package cn.yq.tests.others;

import org.testng.annotations.Factory;

public class TestFactory {

    @Factory
    public Object[] test() {
        Object[] object = new Object[2];
        for (int i = 0; i < 2; i++) {
            TomandyFactory tomandyFactory = new TomandyFactory(i + "");
            object[i] = tomandyFactory;
        }
        return object;
    }
}
