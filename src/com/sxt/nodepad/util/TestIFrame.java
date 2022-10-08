package com.sxt.nodepad.util;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JMenu;

public class TestIFrame extends JFrame {
    static SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    //添加状态栏“时间文本框”的事件监听器，用来实现动态刷新时间
    static class TimeActionListener implements ActionListener{
        public TimeActionListener(){
            javax.swing.Timer t = new javax.swing.Timer(1000,this);
            t.start();
        }

        @Override
        public void actionPerformed(ActionEvent ae){
           NotepadMainFrame.time.setText(myfmt.format(new java.util.Date()).toString());
        }
    }

    public static void main(String[] args){
        new TestIFrame();
    }

}