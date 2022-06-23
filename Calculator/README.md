# Java项目之简易计算器(GUI编程)

​		首先特别感谢CSDN的Turing_Apple73大佬的博客：https://blog.csdn.net/weixin_51625354/article/details/122211758
该项目参考了大佬的代码。并在此基础上，对计算器的界面进一步的还原win11的标准版计算器界面，对一些细节地方进行了进一步的优化。该项目也是应java实训而作，因时间匆忙，现行版本如下：

<img width="242" alt="image" src="https://user-images.githubusercontent.com/102899234/174627488-273f2bdf-7818-413c-baa9-adc36ed96cb4.png"><img width="242" alt="image" src="https://user-images.githubusercontent.com/102899234/174626581-01db34e3-9513-4f1b-8e20-8333d6b8e866.png">

win11标准版计算器(左图)	              java(GUI)制作(右图)

​		项目源码已上传至Github网站：https://github.com/xsb-mushan/JavaProject

## 项目内容

​		模仿Windows自带的标准版计算器，设计并用Java语言实现简易的计算器。

## 项目要求

​		计算器运行界面如下图所示，包含二个文本框（分别显示算式和运算结果）、10个数字按钮(0~9)、4个运算按钮、一个等号按钮、一个清除按钮，要求将按键和结果显示在文本框中。

## 项目代码

```java
package com.mushan.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CalculateFrame implements ActionListener {
    private JFrame frame = new JFrame();
    private String[] keys = {
            "%", "CE", "C", "del",
            "1/x", "x^2", "sqrt", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
    };
    private JButton buttons[] = new JButton[keys.length];
    private JTextField processText = new JTextField();  //过程算式文本框
    private JTextField resultText = new JTextField("0");    //显示结果文本框
    private boolean firstDigit = true;  // 标志用户按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字
    private double resultNum = 0.0000;   // 计算的中间结果
    private String operator = "=";   // 当前运算的运算符（按键"C"时需要将其还原为"="）
    private boolean operateValidFlag = true;   // 判断操作是否合法
    private int arr[] = {0, 4, 5, 6, 7, 11, 15, 19, 20, 22};
    private boolean repeatFlag = false;
    private boolean isDelete = false;
    private String lastOperator;
    private double repeatNum;

    public CalculateFrame() {
        init();  // 初始化计算器
        frame.setTitle("计算器");
        frame.setSize(334, 510);
        frame.setLocation(500, 100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void init() {
        Color color1 = new Color(0, 103, 192);  //等于号专属颜色
        Color color2 = new Color(239, 244, 249);  //背景颜色
        // 建立一个画板放算式文本框和结果文本框
        JPanel textPanel = new JPanel();
        textPanel.setBackground(color2);
        textPanel.setLayout(new BorderLayout());
        textPanel.add(processText, BorderLayout.NORTH);
        textPanel.add(resultText, BorderLayout.CENTER);
        processText.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));  //设置文本框中文字的字体，正规，字号
        processText.setForeground(Color.gray);//设置文本框字体颜色
        resultText.setFont(new Font("Microsoft YaHei", Font.PLAIN, 45));
        processText.setHorizontalAlignment(JTextField.RIGHT);
        resultText.setHorizontalAlignment(JTextField.RIGHT);  //文本框中的内容采用右对齐方式
        processText.setEditable(false);
        resultText.setEditable(false);  //不能修改结果文本框
        resultText.setBorder(null);  //删除文本框的边框
        processText.setBorder(null);

        // 设置文本框背景颜色
        processText.setBackground(color2);
        resultText.setBackground(color2);

        // 初始化计算器上键的按钮，将按钮放在一个画板内
        JPanel keysPanel = new JPanel();
        // 用网格布局器，6行，4列的网格，网格之间的水平方向垂直方向间隔均为4个像素
        keysPanel.setLayout(new GridLayout(6, 4, 4, 4));
        //初始化按钮
        for (int i = 0; i < keys.length; i++) {
            buttons[i] = new JButton(keys[i]);
            keysPanel.add(buttons[i]);
            buttons[i].setBackground(Color.white);
            buttons[i].setForeground(Color.black);
            buttons[i].setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
            buttons[i].setBorderPainted(false);  //去除按钮的边框
            buttons[i].setFocusPainted(false);

        }
        buttons[23].setBackground(color1);  // '='符键用特殊颜色
        buttons[23].setForeground(Color.white);
        keysPanel.setBackground(color2);

        //将文本框所在的面板放在北部，将keysPanel面板放在计算器的中部
        frame.getContentPane().add("North", textPanel);
        frame.getContentPane().add("Center", keysPanel);
        //设置两个面板的边框
        textPanel.setBorder(BorderFactory.createMatteBorder(10, 5, 5, 5, color2));
        keysPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 3, 5, color2));

        // 为各按钮添加事件监听器，都使用同一个事件监听器。
        for (int i = 0; i < keys.length; i++) {
            buttons[i].addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(keys[3])) doDelete();
        else if (command.equals(keys[1])) {
            firstDigit = true;
            if (!operateValidFlag || operator == "=") {
                processText.setText(null);
            }
            resultText.setText("0");
            isDelete = false;
            recover();
        } else if (command.equals(keys[2])) doC();
        else if ("0123456789.".indexOf(command) >= 0) doNumber(command);
        else if (command.equals(keys[0]) || command.equals(keys[4]) ||
                command.equals(keys[5]) || command.equals(keys[6]) ||
                command.equals(keys[20])) doOperator1(command);
        else doOperator2(command);
    }

    private void doDelete() {
        //删除操作
        String text = resultText.getText();
        int i = text.length();
        if (operateValidFlag) {
            if (!processText.getText().equals("") && isDelete) {
                processText.setText(null);
            } else if (operator.equals(keys[0]) || operator.equals(keys[4]) ||
                    operator.equals(keys[5]) || operator.equals(keys[6]) ||
                    operator.equals(keys[20])) {
            } else {
                if (i > 0 && !isDelete) {
                    text = text.substring(0, i - 1);  // 退格，将文本最后一个字符去掉
                    if (text.length() == 0) {
                        resultText.setText("0");// 如果文本没有内容了，则初始化计算器的各种值
                        firstDigit = true;
                        if(!processText.getText().equals("")&&processText.getText().charAt(processText.getText().length()-1)!='='){

                        }else {
                            operator = "=";
                        }
                    } else {
                        resultText.setText(text);// 显示新的文本
                    }
                }
            }
        } else {
            processText.setText(null);
            resultText.setText("0");
        }
        recover();
        operateValidFlag = true;
    }

    public void doC() {
        //初始化计算器
        processText.setText(null);
        resultText.setText("0");
        firstDigit = true;
        operator = "=";
        isDelete = false;
        recover();
    }

    public void doNumber(String key) {
        if (operateValidFlag == false && firstDigit && !key.equals(".")) {
            processText.setText(null);
            resultText.setText(key);
            recover();
            operateValidFlag = true;
            operator = "=";
        } else if (firstDigit && !key.equals(".")) {
            resultText.setText(null);
            resultText.setText(key);
        } else if (key.equals(".") && resultText.getText().indexOf(".") < 0) {
            resultText.setText(resultText.getText() + ".");
        } else if (resultText.getText().charAt(0) == '0') {
            resultText.setText(key);
        } else if (!key.equals(".")) {
            resultText.setText(resultText.getText() + key);
        }
        firstDigit = false;
        isDelete = false;
    }

    public void doOperator1(String key) {
        operator = key;
        if (operator.equals(keys[0])) {//%操作符
            resultNum = getNumberFromText() / 100;
        } else if (operator.equals(keys[4])) {//取倒数
            resultNum = getNumberFromText();
            if (resultNum == 0) {
                doError();
                operateValidFlag = false;
                processText.setText("1/(0)");
                resultText.setText("除数不能为零");
            } else {
                resultNum = 1 / getNumberFromText();
                processText.setText("1/(" + resultText.getText() + ")");
            }
        } else if (operator.equals(keys[5])) {
            resultNum = getNumberFromText() * getNumberFromText();
            processText.setText("sqrt(" + resultText.getText() + ")");
        } else if (operator.equals(keys[6])) {
            if (resultNum < 0) {
                operateValidFlag = false;
                processText.setText("sqrt(" + resultText.getText() + ")");
                resultText.setText("无效输入");
            } else {
                resultNum = Math.sqrt(getNumberFromText());
                processText.setText("sqrt(" + resultText.getText() + ")");
            }
        } else if (operator.equals(keys[20])) {
            resultNum = getNumberFromText() * (-1);
        }
        outcome(operateValidFlag);
        firstDigit = true;
    }

    private void doOperator2(String key) {
        if (key.equals("="))
            isDelete = true;
        if (operator.equals("=") && key.equals("=")&&processText.getText().indexOf("=")>0) {
            repeatFlag = true;
            if (processText.getText().indexOf("-") == 0) {
                lastOperator = processText.getText().indexOf("+") > 0 ? "+" :
                        processText.getText().substring(1).indexOf("-") > 0 ? "-" :
                                processText.getText().indexOf("×") > 0 ? "×" :
                                        processText.getText().indexOf("÷") > 0 ? "÷" : "=";
                if (lastOperator.equals("=")) {
                    resultNum = Double.valueOf(resultText.getText()).doubleValue();
                } else {
                    repeatNum = Double.valueOf(processText.getText().substring(
                            processText.getText().substring(1).indexOf(lastOperator) + 2,
                            processText.getText().length() - 1)).doubleValue();
                }
            } else {
                lastOperator = processText.getText().indexOf("+") > 0 ? "+" :
                        processText.getText().indexOf("-") > 0 ? "-" :
                                processText.getText().indexOf("×") > 0 ? "×" :
                                        processText.getText().indexOf("÷") > 0 ? "÷" : "=";
                if (lastOperator.equals("=")){
                    resultNum=Double.valueOf((resultText.getText())).doubleValue();
                }else {
                    repeatNum = Double.valueOf(processText.getText().substring(
                            processText.getText().indexOf(lastOperator) + 1, processText.getText().length() - 1)).doubleValue();
                }
            }
            if(lastOperator.equals("=")){
                processText.setText(resultText.getText()+lastOperator);
            }
            else if (processText.getText().substring(
                    processText.getText().indexOf(lastOperator) + 1, processText.getText().length() - 1).indexOf(".") > 0) {
                processText.setText(resultText.getText() + lastOperator + repeatNum + operator);
            } else {
                processText.setText(resultText.getText() + lastOperator + (int) repeatNum + operator);
            }
            operator = lastOperator;
            firstDigit = true;
        }
        if (operator.equals(keys[19])) {// 加法运算
            if (repeatFlag) {
                resultNum += repeatNum;
            } else {
                resultNum += getNumberFromText();
            }
        } else if (operator.equals(keys[15])) {// 减法运算
            if (repeatFlag) {
                resultNum -= repeatNum;
            } else {
                resultNum -= getNumberFromText();
            }
        } else if (operator.equals(keys[11])) {// 乘法运算
            if (repeatFlag) {
                resultNum *= repeatNum;
            } else {
                resultNum *= getNumberFromText();
            }

        } else if (operator.equals(keys[7])) {//除法运算
            if (resultNum == 0.0 && getNumberFromText() == 0.0) {
                operateValidFlag = false;
                doError();
                resultText.setText("结果未定义");
                firstDigit = true;
                return;
            } else if (getNumberFromText() == 0.0) {
                operateValidFlag = false;
                doError();
                resultText.setText("除数不能为零");
                firstDigit = true;
                return;
            } else if (repeatFlag) {
                resultNum /= repeatNum;
            } else {
                resultNum /= getNumberFromText();
            }
        } else if (operator.equals(keys[23]) && !repeatFlag) { //等于运算
            resultNum = getNumberFromText();
            processText.setText(null);
        } else if (!operateValidFlag) {
            processText.setText(processText.getText() + key);
            outcome(operateValidFlag);
            operator = key;
            firstDigit = true;
            return;
        }
        if(operator.equals(keys[4]) || operator.equals(keys[5]) || operator.equals(keys[6])){
            processText.setText(processText.getText()+key);
        }
        else if (!repeatFlag) {
            processText.setText(processText.getText() + resultText.getText() + key);
        }
        outcome(operateValidFlag);
        operator = key;
        firstDigit = true;
        repeatFlag = false;
    }

    private double getNumberFromText() {
        double result = 0;
        try {
            result = Double.valueOf(resultText.getText()).doubleValue();
        } catch (NumberFormatException e) {
        }
        return result;
    }

    private void outcome(boolean operateValidFlag) {
        if (operateValidFlag == true) {
            long t1 = (long) resultNum;
            double t2 = resultNum - t1;//得到小数部分
            BigDecimal bd = new BigDecimal(String.valueOf(resultNum));//得到小数位数
            if (t2 == 0) {
                resultText.setText(String.valueOf(t1));//转化为字符串
            } else if (bd.scale() == 1) {
                resultText.setText(String.valueOf(new DecimalFormat("0.0").format(resultNum)));
            } else if (bd.scale() == 2) {
                resultText.setText(String.valueOf(new DecimalFormat("0.00").format(resultNum)));
            } else if (bd.scale() == 3) {
                resultText.setText(String.valueOf(new DecimalFormat("0.000").format(resultNum)));
            } else if (bd.scale() == 4) {
                resultText.setText(String.valueOf(new DecimalFormat("0.0000").format(resultNum)));
            } else if (bd.scale() == 5) {
                resultText.setText(String.valueOf(new DecimalFormat("0.00000").format(resultNum)));
            } else if (bd.scale() == 6) {
                resultText.setText(String.valueOf(new DecimalFormat("0.000000").format(resultNum)));
            } else if (bd.scale() == 7) {
                resultText.setText(String.valueOf(new DecimalFormat("0.0000000").format(resultNum)));
            } else if (bd.scale() == 8) {
                resultText.setText(String.valueOf(new DecimalFormat("0.00000000").format(resultNum)));
            } else {
                resultText.setText(String.valueOf(new DecimalFormat("0.000000000").format(resultNum)));
            }
        }
    }

    private void doError() {
        for (int i = 0; i < arr.length; i++) {
            buttons[arr[i]].setBackground(new Color(242, 245, 250));
            buttons[arr[i]].setEnabled(false);
        }
    }

    private void recover() {
        for (int i = 0; i < arr.length; i++) {
            buttons[arr[i]].setBackground(Color.white);
            buttons[arr[i]].setEnabled(true);
        }
    }

    public static void main(String[] args) {
        new CalculateFrame();
    }
}

```

