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
            "1/x", "x^2", "sqrt", "��",
            "7", "8", "9", "��",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
    };
    private JButton buttons[] = new JButton[keys.length];
    private JTextField processText = new JTextField();  //������ʽ�ı���
    private JTextField resultText = new JTextField("0");    //��ʾ����ı���
    private boolean firstDigit = true;  // ��־�û������Ƿ����������ʽ�ĵ�һ������,�������������ĵ�һ������
    private double resultNum = 0.0000;   // ������м���
    private String operator = "=";   // ��ǰ����������������"C"ʱ��Ҫ���仹ԭΪ"="��
    private boolean operateValidFlag = true;   // �жϲ����Ƿ�Ϸ�
    private int arr[] = {0, 4, 5, 6, 7, 11, 15, 19, 20, 22};
    private boolean repeatFlag = false;
    private boolean isDelete = false;
    private String lastOperator;
    private double repeatNum;

    public CalculateFrame() {
        init();  // ��ʼ��������
        frame.setTitle("������");
        frame.setSize(334, 510);
        frame.setLocation(500, 100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void init() {
        Color color1 = new Color(0, 103, 192);  //���ں�ר����ɫ
        Color color2 = new Color(239, 244, 249);  //������ɫ
        // ����һ���������ʽ�ı���ͽ���ı���
        JPanel textPanel = new JPanel();
        textPanel.setBackground(color2);
        textPanel.setLayout(new BorderLayout());
        textPanel.add(processText, BorderLayout.NORTH);
        textPanel.add(resultText, BorderLayout.CENTER);
        processText.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));  //�����ı��������ֵ����壬���棬�ֺ�
        processText.setForeground(Color.gray);//�����ı���������ɫ
        resultText.setFont(new Font("Microsoft YaHei", Font.PLAIN, 45));
        processText.setHorizontalAlignment(JTextField.RIGHT);
        resultText.setHorizontalAlignment(JTextField.RIGHT);  //�ı����е����ݲ����Ҷ��뷽ʽ
        processText.setEditable(false);
        resultText.setEditable(false);  //�����޸Ľ���ı���
        resultText.setBorder(null);  //ɾ���ı���ı߿�
        processText.setBorder(null);

        // �����ı��򱳾���ɫ
        processText.setBackground(color2);
        resultText.setBackground(color2);

        // ��ʼ���������ϼ��İ�ť������ť����һ��������
        JPanel keysPanel = new JPanel();
        // �����񲼾�����6�У�4�е���������֮���ˮƽ����ֱ��������Ϊ4������
        keysPanel.setLayout(new GridLayout(6, 4, 4, 4));
        //��ʼ����ť
        for (int i = 0; i < keys.length; i++) {
            buttons[i] = new JButton(keys[i]);
            keysPanel.add(buttons[i]);
            buttons[i].setBackground(Color.white);
            buttons[i].setForeground(Color.black);
            buttons[i].setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
            buttons[i].setBorderPainted(false);  //ȥ����ť�ı߿�
            buttons[i].setFocusPainted(false);

        }
        buttons[23].setBackground(color1);  // '='������������ɫ
        buttons[23].setForeground(Color.white);
        keysPanel.setBackground(color2);

        //���ı������ڵ������ڱ�������keysPanel�����ڼ��������в�
        frame.getContentPane().add("North", textPanel);
        frame.getContentPane().add("Center", keysPanel);
        //�����������ı߿�
        textPanel.setBorder(BorderFactory.createMatteBorder(10, 5, 5, 5, color2));
        keysPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 3, 5, color2));

        // Ϊ����ť����¼�����������ʹ��ͬһ���¼���������
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
        //ɾ������
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
                    text = text.substring(0, i - 1);  // �˸񣬽��ı����һ���ַ�ȥ��
                    if (text.length() == 0) {
                        resultText.setText("0");// ����ı�û�������ˣ����ʼ���������ĸ���ֵ
                        firstDigit = true;
                        if(!processText.getText().equals("")&&processText.getText().charAt(processText.getText().length()-1)!='='){

                        }else {
                            operator = "=";
                        }
                    } else {
                        resultText.setText(text);// ��ʾ�µ��ı�
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
        //��ʼ��������
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
        if (operator.equals(keys[0])) {//%������
            resultNum = getNumberFromText() / 100;
        } else if (operator.equals(keys[4])) {//ȡ����
            resultNum = getNumberFromText();
            if (resultNum == 0) {
                doError();
                operateValidFlag = false;
                processText.setText("1/(0)");
                resultText.setText("��������Ϊ��");
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
                resultText.setText("��Ч����");
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
                                processText.getText().indexOf("��") > 0 ? "��" :
                                        processText.getText().indexOf("��") > 0 ? "��" : "=";
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
                                processText.getText().indexOf("��") > 0 ? "��" :
                                        processText.getText().indexOf("��") > 0 ? "��" : "=";
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
        if (operator.equals(keys[19])) {// �ӷ�����
            if (repeatFlag) {
                resultNum += repeatNum;
            } else {
                resultNum += getNumberFromText();
            }
        } else if (operator.equals(keys[15])) {// ��������
            if (repeatFlag) {
                resultNum -= repeatNum;
            } else {
                resultNum -= getNumberFromText();
            }
        } else if (operator.equals(keys[11])) {// �˷�����
            if (repeatFlag) {
                resultNum *= repeatNum;
            } else {
                resultNum *= getNumberFromText();
            }

        } else if (operator.equals(keys[7])) {//��������
            if (resultNum == 0.0 && getNumberFromText() == 0.0) {
                operateValidFlag = false;
                doError();
                resultText.setText("���δ����");
                firstDigit = true;
                return;
            } else if (getNumberFromText() == 0.0) {
                operateValidFlag = false;
                doError();
                resultText.setText("��������Ϊ��");
                firstDigit = true;
                return;
            } else if (repeatFlag) {
                resultNum /= repeatNum;
            } else {
                resultNum /= getNumberFromText();
            }
        } else if (operator.equals(keys[23]) && !repeatFlag) { //��������
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
            double t2 = resultNum - t1;//�õ�С������
            BigDecimal bd = new BigDecimal(String.valueOf(resultNum));//�õ�С��λ��
            if (t2 == 0) {
                resultText.setText(String.valueOf(t1));//ת��Ϊ�ַ���
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
