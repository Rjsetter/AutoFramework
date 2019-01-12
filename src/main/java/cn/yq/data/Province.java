package cn.yq.data;

import java.util.HashMap;
import java.util.Map;

public class Province {
    public static Map<String, Integer> getProvince() {
        Map province = new HashMap<String, Integer>();
        //第一行
        province.put("沪", 11);
        province.put("苏", 12);
        province.put("黑", 13);
        province.put("辽", 14);
        province.put("浙", 15);
        province.put("赣", 16);
        province.put("湘", 17);
        province.put("晋", 18);
        //第二行
        province.put("豫", 21);
        province.put("云", 22);
        province.put("冀", 23);
        province.put("皖", 24);
        province.put("鲁", 25);
        province.put("新", 26);
        province.put("鄂", 27);
        province.put("桂", 28);
        //第三行
        province.put("甘", 31);
        province.put("蒙", 32);
        province.put("陕", 33);
        province.put("吉", 34);
        province.put("闽", 35);
        province.put("贵", 36);
        province.put("粤", 37);
        province.put("川", 38);
        //第四行
        province.put("青", 41);
        province.put("藏", 42);
        province.put("琼", 43);
        province.put("宁", 44);
        province.put("渝", 45);
        province.put("京", 46);
        province.put("津", 47);
        return province;
    }
}
