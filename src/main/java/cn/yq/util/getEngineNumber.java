package cn.yq.util;

import java.util.Random;

public class getEngineNumber {
    /**
     * 随机生成指定长度包含大写字母及数字的发动机号
     *
     * @param length c
     * @return
     */
    public static String getEngineNo(int length) {
        int maxNum = 36;
        int i;
        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < length) {
            i = Math.abs(r.nextInt(maxNum));
            if (count == 0 && i >= 0 && i < 25) {
                pwd.append(str[i]);
                count++;
            } else if (count > 0 && i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    //测试
    public static void main(String[] args) {
        String engine = getEngineNumber.getEngineNo(12);
        System.out.println("随机生成的发动机号：" + engine);
    }
}
