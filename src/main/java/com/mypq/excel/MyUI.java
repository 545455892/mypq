package com.mypq.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.mypq.excel.model.MyColumn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class MyUI {

    private static JTextArea sqlTextArea = new JTextArea();
    //把定义的JTextArea放到JScrollPane里面去
    private static JScrollPane sqlScrollPane = new JScrollPane(sqlTextArea);
    static {
        sqlTextArea.setBounds(100,50,600,400);

        sqlScrollPane.setBounds(100,50,600,400);
        sqlScrollPane.setVisible(true);
        //分别设置水平和垂直滚动条自动出现
        sqlScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sqlScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    public static void appendSqlTextArea(String sql){
        String text = sqlTextArea.getText();
        if (StringUtils.isEmpty(text)) {
            sqlTextArea.setText(sql);
        } else {
            sqlTextArea.setText(sqlTextArea.getText() + "\n" + sql);
        }
    }

    public static void main(String[] args) {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("SQL");
        // Setting the width and height of frame
        frame.setSize(800, 600);
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
        JLabel userLabel = new JLabel("FilePath:");
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
        userText.setText("C:\\Users\\UncleY\\Desktop\\11111.xlsx");
        userText.setBounds(100,20,600,25);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("SQL:");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */



        panel.add(sqlScrollPane);

        // 创建登录按钮
        JButton loginButton = new JButton("go");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlTextArea.setText(new Date().toString());
                String filePath = userText.getText();
                EasyExcel.read(filePath, MyColumn.class, new MyColumnListener()).sheet().doRead();
            }
        });


        panel.add(loginButton);
    }
}
