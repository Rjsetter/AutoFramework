package cn.yq.tests.图形界面;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test  {
    public void actionPerformed(ActionEvent e,JButton b, JTextField t) {
        if(e.getSource()==b){
            if(t.getText().equals("")){
                JOptionPane.showMessageDialog(null,"请输入内容~","错误",JOptionPane.ERROR_MESSAGE);
                t.grabFocus();
            }else{
                System.out.println(t.getText());
                JOptionPane.showMessageDialog(null,"写入成功","提示",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    public static void main(String[] args) {
        // 创建 JFrame 实例
            JFrame frame = new JFrame("查验证码");
        // Setting the width and height of frame
        frame.setSize(350, 300);
        frame.setLocation(500,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        // 创建 JLabel
        JLabel userLabel = new JLabel("Cookie:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */
        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        //环境
        JLabel envLabel = new JLabel("Env:");
        envLabel.setBounds(10,50,80,25);
        panel.add(envLabel);

        String []env= {"itest","uat","prd"};
        JComboBox box = new JComboBox(env);
        box.setBounds(100,50,80,25);
        panel.add(box);

        // 电话
        JLabel passwordLabel = new JLabel("Phone:");
        passwordLabel.setBounds(10,80,80,25);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        JTextField phoneText = new JTextField(50);
        phoneText.setBounds(100,80,165,25);
        panel.add(phoneText);

        // 创建登录按钮
        JButton loginButton = new JButton("search");
        loginButton.setBounds(135, 110, 80, 25);
        panel.add(loginButton);
        //显示区域
        JLabel showLabel = new JLabel("验证码:");
        showLabel.setBounds(10,140,80,25);
        panel.add(showLabel);

        JTextArea area = new JTextArea("验证码......");
        area.setFont(new Font("楷体",Font.BOLD,16));
        area.setLineWrap(true);
        area.setBackground(Color.YELLOW);
        area.setBounds(100,140,165,80);
        JScrollPane jsp=new JScrollPane(area);
        jsp.setBounds(100,140,165,80);
        panel.add(jsp);

    }
}