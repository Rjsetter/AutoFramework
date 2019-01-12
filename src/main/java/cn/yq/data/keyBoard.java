package cn.yq.data;

import java.util.HashMap;
import java.util.Map;

//其中减号代表删除，等号代表确认
public class keyBoard {
    public static Map<String, Integer> getKeyBoard() {
        Map<String, Integer> keyBoard = new HashMap<String, Integer>();
        //第一行
        keyBoard.put("1", 11);
        keyBoard.put("2", 12);
        keyBoard.put("3", 13);
        keyBoard.put("4", 14);
        keyBoard.put("5", 15);
        keyBoard.put("6", 16);
        keyBoard.put("7", 17);
        keyBoard.put("8", 18);
        keyBoard.put("9", 19);
        keyBoard.put("0", 110);
        //第二行
        keyBoard.put("Q", 21);
        keyBoard.put("W", 22);
        keyBoard.put("E", 23);
        keyBoard.put("R", 24);
        keyBoard.put("T", 25);
        keyBoard.put("Y", 26);
        keyBoard.put("U", 27);
        keyBoard.put("I", 28);
        keyBoard.put("O", 29);
        keyBoard.put("P", 210);
        //第三行
        keyBoard.put("A", 31);
        keyBoard.put("S", 32);
        keyBoard.put("D", 33);
        keyBoard.put("F", 34);
        keyBoard.put("G", 35);
        keyBoard.put("H", 36);
        keyBoard.put("J", 37);
        keyBoard.put("K", 38);
        keyBoard.put("L", 39);
        keyBoard.put("-", 310);
        //第四行
        keyBoard.put("Z", 41);
        keyBoard.put("X", 42);
        keyBoard.put("C", 43);
        keyBoard.put("V", 44);
        keyBoard.put("B", 45);
        keyBoard.put("N", 46);
        keyBoard.put("M", 47);
        keyBoard.put("=", 48);
        return keyBoard;
    }
}
