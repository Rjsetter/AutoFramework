package cn.yq.util;

import cn.yq.data.IdbQuery;
import cn.yq.restClient.RestClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class TestGUI extends JFrame implements ActionListener {
    String environment;//环境
    String cookie;     //cookie
    String phone;      //电话
    static String msgFlag = " test";    //避免重复输出
    static String message;    //信息
    static String messageType ;
    JButton
            searchButton = new JButton("search");
    JLabel
            cookieLabel = new JLabel("Cookie:"),
            envLabel = new JLabel("Env:"),
            phoneLabel = new JLabel("Phone:"),
            showLabel = new JLabel("验证码:"),
            title = new JLabel("<html>模<br>拟<br>进<br>场</html>");
    JPanel panel = new JPanel();
    JFrame frame = new JFrame("查验证码");
    JTextField
            cookieText = new JTextField(100),
            phoneText = new JTextField(50);

    static  JTextArea area = new JTextArea("");
    String []env= {"选择环境","tst","pre","prd"};
    String []msgType = {"消息类型","验证码","通知"};
    JComboBox box = new JComboBox(env),
              msType = new JComboBox(msgType);
    public TestGUI(){
        frame.setSize(800, 400);
        frame.setLocation(500,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加面板
        frame.add(panel);
        frame.setVisible(true);
        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        cookieLabel.setBounds(10,20,80,25);
        panel.add(cookieLabel);
        /*
         * 创建文本域用于用户输入
         */
        cookieText.setBounds(100,20,165,25);
        panel.add(cookieText);

        title.setBounds(300,20,40,80);
//        title.setFont(new Font("楷体",Font.BOLD,12));
        panel.add(title);
        //环境
        envLabel.setBounds(10,50,80,25);
        panel.add(envLabel);
        box.setBounds(100,50,60,25);
        box.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                switch (event.getStateChange())
                {
                    case ItemEvent.SELECTED:
                        System.out.println(event.getItem());
                        break;
                }
            }
        });
        panel.add(box);
        //短信类型
        msType.setBounds(190,50,60,25);
        msType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                switch (event.getStateChange())
                {
                    case ItemEvent.SELECTED:
                        System.out.println(event.getItem());
                        break;
                }
            }
        });
        panel.add(msType);
        // 电话
        phoneLabel.setBounds(10,80,80,25);
        panel.add(phoneLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        phoneText.setBounds(100,80,165,25);
        panel.add(phoneText);
        // 创建搜索按钮
        searchButton.setBounds(135, 110, 80, 25);

        //显示区域
        showLabel.setBounds(10,140,160,25);
        panel.add(showLabel);
        area.setFont(new Font("楷体",Font.BOLD,16));
        area.setLineWrap(true);
//        area.setBackground(Color.ORANGE);
        area.setBounds(100,140,165,80);
        panel.add(searchButton);
        JScrollPane jsp=new JScrollPane(area);
        jsp.setBounds(60,145,700,210);
        panel.add(jsp);
        searchButton.addActionListener(this);

    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            if (cookieText.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Cookie不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                cookieText.grabFocus();
            } else if (phoneText.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "手机号不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
                cookieText.grabFocus();
            } else if (phoneText.getText().length() != 11) {
                JOptionPane.showMessageDialog(null, "请输入正确的手机号！", "错误", JOptionPane.ERROR_MESSAGE);
                cookieText.grabFocus();
            } else if (box.getSelectedItem().toString().equals("选择环境")) {
                JOptionPane.showMessageDialog(null, "请选择环境！", "错误", JOptionPane.ERROR_MESSAGE);
                box.grabFocus();
            } else if (msType.getSelectedItem().toString().equals("消息类型")) {
                JOptionPane.showMessageDialog(null, "请短信类型！", "错误", JOptionPane.ERROR_MESSAGE);
                msType.grabFocus();
            } else {
                cookie = cookieText.getText();
                phone = phoneText.getText();
                environment = box.getSelectedItem().toString();
                if (msType.getSelectedItem().toString().equals("通知"))
                    messageType = "TZ";
                else
                    messageType = "YZM";
                try {
                    System.out.println(environment);
                    System.out.println(phone);
                    System.out.println(cookie);
                    idb(environment, phone, cookie);
                } catch (IOException c) {
                    message = "查询出错！";
                    area.append(message);
                    area.repaint();
                    System.out.println("查询出错！");
                }
//                    area.append(message);
//                    area.repaint();
//                    area.grabFocus();
//                JOptionPane.showMessageDialog(null, "环境："+environment+" 手机号：mngvhjgvjhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"+phone, "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     *
     * @param dbenv   环境
     * @throws IOException
     */
    public static void idb(String dbenv,String phone,String Cookie)throws IOException {
        CloseableHttpResponse closeableHttpResponse;
        RestClient restClient = new RestClient();
        String Url = "http://idb.zhonganonline.com/getqueryrst";
        String dbname = "nereus";
        String tbname = "nereus_message_sent";
        String sql = "receiver_no ='"+phone +"'";
        String splitcol = "-1";
        String splitcolmode = "=";
        String selectmod = "1";
        String iscount = "0";
        String idb2Json ;
        //转换为Json数据
        IdbQuery idbquery = new IdbQuery(dbenv,dbname,tbname,splitcol,splitcolmode,sql,selectmod,iscount);
        idb2Json = JSON.toJSONString(idbquery);
        //请求头
        HashMap<String, String> headermap = new HashMap<String, String>();
        headermap.put("Content-Type", "application/json");
        headermap.put("Cookie", Cookie);
        closeableHttpResponse = restClient.post(Url, idb2Json, headermap);
        //验证状态码是不是200
        System.out.println(idb2Json);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        JSONObject responseJson = restClient.getResponseJson(closeableHttpResponse);
        String s = "aa";
        int i = 0;   //遍历八个表
        while(s.length() == 2 && i<8) {
            System.out.println("------------test1----");
            String flag = "nereus_00.nereus_message_sent_000" + i;
            s = TestUtil.getValueByJPath(responseJson, flag);
            if (s.length() > 2) {
                System.out.println("------------test2----");
                JSONArray test = JSONArray.parseArray(s);
                int size = test.size();
                System.out.println(size);
                for (int j = 0; j < size; j++) {
                    String index = "[" + j + "]";
                    //短信类型  TZ通知，YZM验证码
                       try {
                           if (TestUtil.getValueByJPath(responseJson, flag + index + "/message_type").equals(messageType)) {
                               //时间 和 短信内容
                               message = TestUtil.getValueByJPath(responseJson, flag + index + "/gmt_created") + TestUtil.getValueByJPath(responseJson, flag + index + "/content");
                               if(msgFlag.equals(message)){
                                   System.out.println(TestUtil.getValueByJPath(responseJson, flag + index + "/gmt_created") + TestUtil.getValueByJPath(responseJson, flag + index + "/content"));
                               }else {
                                   area.append(message + "\n");
                                   area.repaint(50);
                                   area.grabFocus();
                                   msgFlag = message;
                               }
                           }
                       }catch (NullPointerException e){
                           area.append("没有查询到信息，请检查环境与手机号是否有误！\n");
                       }
                }
            }
            i++;
        }
    }
    public static void main(String[] args) {
       try{  new TestGUI();}catch (Exception e){
           System.out.println("warning!");
       }
    }
}

