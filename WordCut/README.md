# java项目之百词斩(GUI编程)

​		该项目为java期末实训内容，敲了两天的代码终于完工了，由于时间紧迫，百词斩的界面做的比较粗糙、丑陋，仅实现了内部的算法。必定还存在疏漏，欢迎读者批评和指正。注意：测试用的文本文件Word.txt放到工程目录下面。
如图所示：![image](https://user-images.githubusercontent.com/102899234/175303168-fe93af47-843f-48a3-a2dd-e2d80799eabf.png)

## 项目内容

​		模仿“百词斩”手机App，设计并用Java语言实现一个“百词斩”图形界面程序。

#  项目要求

（1）事先将一定数量的英语单词、四个选项（英语单词的汉语解释）及正确答案存放在一个文本文件中（一个单词的信息占一行，各项之间用一个空格分隔）。

（2）从文件中取出所有单词的信息，存入数组中。

（3）从单词数组中，随机找出一个单词，以单项选择题的形式显示出来，供用户答题。答对时显示√，答错时显示×并显示正确结果。每答完一题，都要统计并显示目前答对的单词数量。

（4）对于已经回答正确的单词，以后不会再出现。回答错误的单词，以后还会随机出现。

提示：

假如总共有n个单词，存入数组WORDS。

抽题时：

a. 随机产生一个[0,n)之间的随机整数i。

b. 取出WORDS[i]中的单词，作为选择题供用户选择。

答题时：

如果用户答对了，将WORDS[i]与WORDS[n-1]互换，n的值减1。

（5）所有单词都回答正确时n=0，程序结束。也可以关闭窗口强行退出。

## 参考程序界面：
<img width="366" alt="image" src="https://user-images.githubusercontent.com/102899234/175301994-606b6fb3-9282-4843-9cdc-eab4b0a1e48e.png"><img width="366" alt="image" src="https://user-images.githubusercontent.com/102899234/175302137-94640615-e187-4dad-a162-44622027ac80.png">

## 界面设计参考代码

```java
import java.awt.Color;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
public class Bczh extends JFrame{
	Bczh(){
		this.setTitle("百词斩");
		this.setSize(500,600);
		this.setLayout(null);
		
		//设置答对单词数的标签
		int okNum,totalNum;
		okNum=1; //答对题数（根据答题结果，不断变化）
		totalNum=50; //总题数（根据实际情况而定）
		JLabel labScore=new JLabel("答对单词数："+okNum+"/"+totalNum);
		labScore.setForeground(Color.red);//设置答对单词数的文字颜色
		Font font=new Font("宋体",Font.PLAIN,24);
		labScore.setFont(font); //设置答对单词数的字体
		labScore.setBounds(220, 20,300,30);

		//设置英语单词的标签
		String words="computer"; //应该随机产生
		JLabel labWord=new JLabel(words);
		labWord.setFont(font); //设置英语单词的字体
		labWord.setBounds(40,80,200,30);

		//设置各个单选按钮及按钮组
		ButtonGroup group=new ButtonGroup();//单选按钮组
		JRadioButton rb1=new JRadioButton();//选项一单选按钮
		JRadioButton rb2=new JRadioButton();//选项二单选按钮
		JRadioButton rb3=new JRadioButton();//选项三单选按钮
		JRadioButton rb4=new JRadioButton();//选项四单选按钮
		String s1,s2,s3,s4;
		s1="计算机";
		s2="计算器";
		s3="计算尺";
		s4="程序编译器";
		rb1.setText(s1);//设置选项一的文字
		rb2.setText(s2);//设置选项二的文字
		rb3.setText(s3);//设置选项三的文字
		rb4.setText(s4);//设置选项四的文字
//		rb1.setForeground(Color.black);
//		rb2.setForeground(Color.black);
//		rb3.setForeground(Color.black);
//		rb4.setForeground(Color.black);
		rb1.setFont(font);//设置选项一的字体字号
		rb2.setFont(font);//设置选项二的字体字号
		rb3.setFont(font);//设置选项三的字体字号
		rb4.setFont(font);//设置选项四的字体字号
		rb1.setBounds(40,120,150,50);
		rb2.setBounds(40,170,150,50);
		rb3.setBounds(40,220,150,50);
		rb4.setBounds(40,270,150,50);
		//将各个单选按钮加入到按钮组，确保多选一（一个按钮组内只能选一项）
		group.add(rb1);
		group.add(rb2);
		group.add(rb3);
		group.add(rb4);
		
		//设置答案标签
		JLabel answer=new JLabel(); 
		answer.setBounds(50,350,200,50);
		//answer.setText("× "+s1); //答错时，显示答案
		answer.setText("√ "); //答对
		answer.setFont(font);
		answer.setForeground(Color.red);//设置答案的文字颜色
		
		JButton prev=new JButton("上一题");
		prev.setFont(font);
		prev.setBounds(100,450,120,30);
		prev.setEnabled(false); //第1题时，不能点上一题
		

		JButton next=new JButton("下一题");
		next.setFont(font);
		next.setBounds(250,450,120,30);
		//next.setEnabled(false); //最后一题时，不能点下一题

		//将个组件添加到窗口中
		this.add(labScore);
		this.add(labWord);
		this.add(rb1);
		this.add(rb2);
		this.add(rb3);
		this.add(rb4);
		this.add(prev);
		this.add(next);
		this.add(answer);

		this.setVisible(true); //设置窗口可见
		this.setLocationRelativeTo(null);//设置窗口位置
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Bczh();
		
	}

}


```

