package cn.yq.util;

import java.util.Map;
import java.util.Set;

import static cn.yq.data.Province.getProvince;
import static cn.yq.data.keyBoard.getKeyBoard;


public class GetXpath {
    public String url = "";          //返回的xpath地址
    public String head = "/html/body/article/article/div[1]/article[1]/section/section/section/ul[";
    public String body = "]/li[";
    public String end = "]";
    public int pos;     //Map返回省份简称的坐标
    public int pos1;   //第一个位置
    public int pos2;   //第二个位置

    /**
     * @param name ,接收一个省份的简称返回一个xpath地址
     * @return，返回xpath地址URL
     */
    public String getPath(String name) {
        //获取省份的Map
        Map province = getProvince();
        Set<String> keys = province.keySet();
        for (String key : keys) {
            if (name.equals(key)) {
                pos = Integer.parseInt(province.get(key).toString());
                //System.out.println(pos);
            }
        }
        pos1 = pos / 10;   //获取第一个位置数字
        pos2 = pos % 10;   //获取第二个位置的数字
        //System.out.println(index1 +" "+index2);
        //组合成xpath地址
        url = head + pos1 + body + pos2 + end;
        return url;
    }

    /**
     * @param name ,接收一个省份的简称返回一个xpath地址
     * @return，返回xpath地址URL
     */
    public String getPath2C(String name) {
        String head2C = "/html/body/article/article/section[7]/section/ul[";
        String body2C = "]/li[";
        String end2C = "]";
        //获取省份的Map
        Map province = getProvince();
        Set<String> keys = province.keySet();
        for (String key : keys) {
            if (name.equals(key)) {
                pos = Integer.parseInt(province.get(key).toString());
                //System.out.println(pos);
            }
        }
        pos1 = pos / 10;   //获取第一个位置数字
        pos2 = pos % 10;   //获取第二个位置的数字
        //System.out.println(index1 +" "+index2);
        //组合成xpath地址
        url = head2C + pos1 + body2C + pos2 + end2C;
        return url;
    }

    /**
     * @param year  年份
     * @param month 月份
     * @param day   天
     * @return，返回日期xpath地址URL
     */
    public static String getDayXpath(int year, int month, int day) {
        String head = "/html/body/article/article/article[1]/section/section[3]/ul/li[";
        int index = DateTools.getWeek(year, month, day);
        String dayXpath = head + index + "]";
        return dayXpath;
    }

    /**
     * @param month 月份
     * @return，返回月份xpath地址URL
     */
    public static String getMonthXpath(int month) {
        String head = "/html/body/article/article/article[1]/section/section[1]/div[2]/ul/li[";
        String dayXpath = head + month + "]";
        return dayXpath;
    }

    /**
     * @param cityName 接受一个城市的名字，返回他的class的名字
     * @return，返回2C页面城市xpath地址
     */
    public static String get2CCityClassName(String cityName) {
        String className = "";
        String head = "code_";
        String end = getCityCode.get_CityCode().get(cityName);
        className = head + end;
        return className;
    }

    /**
     * @param provinceName 接受一个城市的名字，返回他的class的名字
     * @return，返回2C页面省份code
     */
    public static String get2CProvinceClassName(String provinceName) {
        String className = "";
        String head = "code_";
        String end = getProvinceCode.getProvinceMap().get(provinceName);
        className = head + end;
        return className;
    }

    public static String getKeyBoardXpath(char name) {
        String head2C = "/html/body/article/article/section[7]/section/section/ul[";
        String body2C = "]/li[";
        String end2C = "]";
        String url = "";
        int pos = 0;
        int pos1;
        int pos2;
        String s = String.valueOf(name); //效率最高的方法
        //获取省份的Map
        Map keyBoard = getKeyBoard();
        Set<String> keys = keyBoard.keySet();
        for (String key : keys) {
            if (s.equals(key)) {
                pos = Integer.parseInt(keyBoard.get(key).toString());
                //System.out.println(pos);
            }
        }
        if (pos < 100) {
            pos1 = pos / 10;   //获取第一个位置数字
            pos2 = pos % 10;   //获取第二个位置的数字
            //System.out.println(index1 +" "+index2);
        } else {
            pos1 = pos / 100;   //获取第一个位置数字
            pos2 = 10;   //获取第二个位置的数字
        }
        //组合成xpath地址
        url = head2C + pos1 + body2C + pos2 + end2C;
        return url;
    }
//    public static void main(String[] args){
//        GetXpath test = new GetXpath();
//        String a = test.getPath("贵");
//        System.out.println(a);
//    }
}
