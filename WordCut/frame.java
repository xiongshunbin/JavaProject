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
    public JLabel labSerial;
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
    JRadioButton ab;
    JButton prev;
    JButton next;
    private JLabel answerLabel;
    private int i;
    private static int a;

    public frame() throws IOException {

        Panel panel = new Panel();
        panel.setLayout(null);
        readWords.ws.add(word);
        i = 1;
        a = i;
        this.setTitle("百词斩");
        panel.setSize(400, 500);
        this.setLayout(null);
        totalNum = readWords.words.size();

        //设置题号
        labSerial = new JLabel("第1题");
        labSerial.setForeground(Color.red);//设置答对单词数的文字颜色
        labSerial.setFont(font); //设置答对单词数的字体
        labSerial.setForeground(Color.blue);
        labSerial.setBounds(40, 20, 300, 30);

        //设置答对单词数的标签
        labScore = new JLabel("答对单词数：" + okNum + "/" + totalNum);
        labScore.setForeground(Color.red);//设置答对单词数的文字颜色
        labScore.setFont(font); //设置答对单词数的字体
        labScore.setBounds(200, 20, 300, 30);

        //设置英语单词的标签
        labWord = new JLabel(words);
        panel.setBackground(Color.white);
        labWord.setFont(new Font("Times New Roman", Font.PLAIN, 20)); //设置英语单词的字体
        labWord.setBounds(40, 70, 200, 30);

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

        rb1.setFocusPainted(false);
        rb2.setFocusPainted(false);
        rb3.setFocusPainted(false);
        rb4.setFocusPainted(false);


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
        answerLabel.setBounds(50, 340, 300, 50);
        answerLabel.setFont(font);
        answerLabel.setForeground(Color.red);//设置答案的文字颜色
        answerLabel.setText(null);
        panel.add(answerLabel);

        prev = new JButton("上一题");
        btnSet(prev);
        prev.setBounds(80, 400, 100, 30);
        prev.setEnabled(false);

        next = new JButton("下一题");
        btnSet(next);
        next.setBounds(220, 400, 100, 30);

        //将个组件添加到窗口中
        panel.add(labSerial);
        panel.add(labScore);
        panel.add(labWord);
        panel.add(rb1);
        panel.add(rb2);
        panel.add(rb3);
        panel.add(rb4);
        panel.add(prev);
        panel.add(next);

        this.setContentPane(panel);
        pack();
        this.setLocationRelativeTo(null);//设置窗口位置
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        ab = (JRadioButton) e.getSource();
        if (e.getSource() == abt) {
            if (((Word) readWords.ws.get(i - 1)).getState() == 0) {
                okNum++;
            }
            labScore.setText("答对单词数：" + okNum + "/" + totalNum);
            if (answerLabel.getText() == null && ((Word) readWords.ws.get(i - 1)).getState() != -1) {
                ((Word) readWords.ws.get(i - 1)).setState(1);
            }
            answerLabel.setText("√ ");
            noAvailable(ab);
        } else {
            ((Word) readWords.ws.get(i - 1)).setState(-1);
            answerLabel.setText("× " + answer);
            noAvailable(ab);
        }
        if (readWords.words.size() == 1) {
            int n;
            for(n=0;n<totalNum;n++){
                if(((Word)readWords.ws.get(n)).getState()==0){
                    break;
                }
            }
            if(n==totalNum){
                JOptionPane.showMessageDialog(null, "恭喜你已完成所有题目！" + "本次共答对" + okNum + "道题目");
                prev.setEnabled(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("上一题")) {
            if (answerLabel.getText() != null) {
                clear();
            }
            Word lastWord = (Word) readWords.ws.get(i - 2);
            changeWord(lastWord);
            display(lastWord);
            i--;
            if (i <= 1) {
                prev.setEnabled(false);
            }
            labSerial.setText("第" + i + "题");
        } else if (e.getActionCommand().equals("下一题")) {
            prev.setEnabled(true);
            if (i >= a) {
                if (answerLabel.getText() == null) {
                    ((Word) readWords.ws.get(i - 1)).setState(0);
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
                a = i;
            } else {
                if (answerLabel.getText() == null) {
                    ((Word) readWords.ws.get(i - 1)).setState(0);
                } else {
                    clear();
                }
                Word nextWord = (Word) readWords.ws.get(i);
                changeWord(nextWord);
                display(nextWord);
                i++;
            }
            labSerial.setText("第" + i + "题");
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

    public void noAvailable(JRadioButton ab) {
        if (rb1 != ab) {
            rb1.setEnabled(false);
        }
        if (rb2 != ab) {
            rb2.setEnabled(false);
        }
        if (rb3 != ab) {
            rb3.setEnabled(false);
        }
        if (rb4 != ab) {
            rb4.setEnabled(false);
        }
        ((Word) readWords.ws.get(i - 1)).setSelection(ab.getText());
    }

    public void recover() {
        rb1.setEnabled(true);
        rb2.setEnabled(true);
        rb3.setEnabled(true);
        rb4.setEnabled(true);
    }

    public void clear() {
        group.clearSelection();
        answerLabel.setText(null);
        recover();
    }

    public void display(Word w) {
        if (w.getState() == -1) {
            ab = w.getSelection().equals(rb1.getText()) ? rb1 : answer.equals(rb2.getText()) ? rb2 : answer.equals(rb3.getText()) ? rb3 : rb4;
            ab.setSelected(true);
            noAvailable(ab);
            answerLabel.setText("× " + w.getAnswer());
        } else if (w.getState() == 1) {
            ab = w.getSelection().equals(rb1.getText()) ? rb1 : answer.equals(rb2.getText()) ? rb2 : answer.equals(rb3.getText()) ? rb3 : rb4;
            ab.setSelected(true);
            noAvailable(ab);
            answerLabel.setText("√ ");
        }
    }

    public void btnSet(JButton bt) {
        bt.setBackground(new Color(7, 188, 252));
        bt.setForeground(Color.white);
        bt.addActionListener(this);
        bt.setFont(new Font("黑体", Font.PLAIN, 16));
        bt.setBorder(null);
        bt.setFocusPainted(false);
    }

    public static void main(String[] args) throws IOException {
        frame f = new frame();
        f.setVisible(true);
    }
}
