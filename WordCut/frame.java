package com.mushan.WordCut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class frame extends JFrame implements ItemListener, ActionListener {
    Reader readWords = new Reader();      //读取文件中的单词，并存入数组
    Font font = new Font("宋体", Font.PLAIN, 20);
    public static int okNum = 0;            //答对题数（根据答题结果，不断变化）
    public static int totalNum;             //总题数（根据实际情况而定）

    //初始界面，获取第一个单词
    Word word = readWords.selectWord();
    public String answer = word.getAnswer();
    public String words = word.getWord(); //应该随机产生
    public String s1 = word.getS1(), s2 = word.getS2();
    public String s3 = word.getS3(), s4 = word.getS4();

    JLabel labScore;
    JLabel labWord;
    ButtonGroup group;
    JRadioButton rb1;
    JRadioButton rb2;
    JRadioButton rb3;
    JRadioButton rb4;
    JRadioButton abt;
    JButton prev;
    JButton next;
    private JLabel answerLabel;
    private int i;
    private static int a;

    public frame() throws IOException {

        readWords.ws.add(word);
        i = 1;
        a = i;
        this.setTitle("百词斩");
        this.setSize(500, 600);
        this.setLayout(null);
        totalNum = readWords.words.size();

        //设置答对单词数的标签
        labScore = new JLabel("答对单词数：" + okNum + "/" + totalNum);
        labScore.setForeground(Color.red);//设置答对单词数的文字颜色
        labScore.setFont(font); //设置答对单词数的字体
        labScore.setBounds(220, 20, 300, 30);

        //设置英语单词的标签
        labWord = new JLabel(words);
        labWord.setFont(font); //设置英语单词的字体
        labWord.setBounds(40, 80, 200, 30);

        //设置各个单选按钮及按钮组
        group = new ButtonGroup();//单选按钮组
        rb1 = new JRadioButton();//选项一单选按钮
        rb2 = new JRadioButton();//选项二单选按钮
        rb3 = new JRadioButton();//选项三单选按钮
        rb4 = new JRadioButton();//选项四单选按钮

        rb1.setText(s1);//设置选项一的文字
        rb2.setText(s2);//设置选项二的文字
        rb3.setText(s3);//设置选项三的文字
        rb4.setText(s4);//设置选项四的文字

        abt = answer.equals(s1) ? rb1 : answer.equals(s2) ? rb2 : answer.equals(s3) ? rb3 : rb4;

        rb1.setFont(font);//设置选项一的字体字号
        rb2.setFont(font);//设置选项二的字体字号
        rb3.setFont(font);//设置选项三的字体字号
        rb4.setFont(font);//设置选项四的字体字号

        rb1.addItemListener(this);
        rb2.addItemListener(this);
        rb3.addItemListener(this);
        rb4.addItemListener(this);

        rb1.setBounds(40, 120, 300, 50);
        rb2.setBounds(40, 170, 300, 50);
        rb3.setBounds(40, 220, 300, 50);
        rb4.setBounds(40, 270, 300, 50);
        //将各个单选按钮加入到按钮组，确保多选一（一个按钮组内只能选一项）
        group.add(rb1);
        group.add(rb2);
        group.add(rb3);
        group.add(rb4);

        //设置答案标签
        answerLabel = new JLabel();
        answerLabel.setBounds(50, 350, 300, 50);
        answerLabel.setFont(font);
        answerLabel.setForeground(Color.red);//设置答案的文字颜色
        answerLabel.setText(null);
        this.add(answerLabel);

        prev = new JButton("上一题");
        prev.addActionListener(this);
        prev.setFont(font);
        prev.setBounds(100, 450, 120, 30);
        prev.setEnabled(false);

        next = new JButton("下一题");
        next.addActionListener(this);
        next.setFont(font);
        next.setBounds(250, 450, 120, 30);

        //将个组件添加到窗口中
        this.add(labScore);
        this.add(labWord);
        this.add(rb1);
        this.add(rb2);
        this.add(rb3);
        this.add(rb4);
        this.add(prev);
        this.add(next);

        this.setLocationRelativeTo(null);//设置窗口位置
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == abt) {
            if (((Word) readWords.ws.get(i - 1)).getState() == 0) {
                okNum++;
            }
            labScore.setText("答对单词数：" + okNum + "/" + totalNum);
            if (answerLabel.getText() == null && ((Word) readWords.ws.get(i - 1)).getState() != -1) {
                ((Word) readWords.ws.get(i-1)).setState(1);
            }
            answerLabel.setText("√ ");
        } else {
            ((Word) readWords.ws.get(i - 1)).setState(-1);
            answerLabel.setText("× " + answer);
        }
        if (readWords.words.size() == 1) {
            JOptionPane.showMessageDialog(null, "恭喜你已完成所有题目！" + "共答对" + okNum + "道题目");
            prev.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("上一题")) {
            if (answerLabel.getText() != null) {
                clear();
            }
            changeWord((Word) readWords.ws.get(i - 2));
            i--;
            if (i <= 1) {
                prev.setEnabled(false);
            }
        } else if (e.getActionCommand().equals("下一题")) {
            prev.setEnabled(true);
            if (i >= a) {
                if (answerLabel.getText() == null) {
                    ((Word) readWords.ws.get(i-1)).setState(0);
                } else {
                    clear();
                }
                readWords.words.remove(readWords.index);
                if (readWords.words.size() == 1) {
                    next.setEnabled(false);
                }
                Word nextWord = readWords.selectWord();
                changeWord(nextWord);
                readWords.ws.add(nextWord);
                i++;
                a=i;
            } else {
                if (answerLabel.getText() == null) {
                    ((Word) readWords.ws.get(i-1)).setState(0);
                } else {
                    clear();
                }
                Word nextWord = (Word) readWords.ws.get(i);
                changeWord(nextWord);
                i++;
            }
        }
    }

    public void changeWord(Word word) {
        labWord.setText(word.getWord());
        rb1.setText(word.getS1());
        rb2.setText(word.getS2());
        rb3.setText(word.getS3());
        rb4.setText(word.getS4());
        answer = word.getAnswer();
        abt = answer.equals(rb1.getText()) ? rb1 : answer.equals(rb2.getText()) ? rb2 : answer.equals(rb3.getText()) ? rb3 : rb4;
    }

    public void clear() {
        group.clearSelection();
        answerLabel.setText(null);
    }

    public static void main(String[] args) throws IOException {
        frame f = new frame();
        f.setVisible(true);
    }
}
