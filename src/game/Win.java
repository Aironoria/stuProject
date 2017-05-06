package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Win extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private MyLabel[][] board;
	private JLabel[][] board1;
	private JLayeredPane main;
	private JLabel score;
	private JLabel select;
	private JLabel hint;
	private JLabel hint2;
	private JLabel roll;
	private JLabel finity;
	private JLabel action;
	private JLabel level;
	private JPanel p;
	
	public Win(Controller controller){
		this.controller=controller;
		main=new JLayeredPane();
		init();
	}
	
	private void init() {
		setTitle("对对碰Ver1.0");
		setBounds(150,150,900, 750);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createPanel());
	}
	private  JLayeredPane createPanel() {
		//background
		ImageIcon background=new ImageIcon(Win.class.getResource("pic/back.jpg"));
		background.setImage(background.getImage().getScaledInstance(getWidth(), getHeight(), 1));
		JLabel back=new JLabel(background);
		back.setBackground(Color.BLACK);
		back.setBounds(0, 0,900,750);
		main.add(back,JLayeredPane.DEFAULT_LAYER);
		//start button
		ImageIcon startimg=new ImageIcon(Win.class.getResource("pic/start.png"));
		JLabel start=new JLabel(startimg);
		//鼠标点击start监听器
		start.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e) {
				controller.beforeStart();				
			}
		});
		main.add(start,JLayeredPane.POPUP_LAYER);
		start.setBounds(198, 455,70,70);
		
		//main menu
		ImageIcon menuimg1=new ImageIcon(Win.class.getResource("pic/all.png"));
		menuimg1.setImage(menuimg1.getImage().getScaledInstance(350,250, 1));
		JLabel menu1=new JLabel(menuimg1);
		//坐标和边界大小
		menu1.setBounds(-30, 440,350,250);		
		main.add(menu1,JLayeredPane.MODAL_LAYER);
		
		//方块下面的背景
		ImageIcon boardimg1=new ImageIcon(Win.class.getResource("pic/action.png"));
		boardimg1.setImage(boardimg1.getImage().getScaledInstance(550,600, 1));
		JLabel board11=new JLabel(boardimg1);
		board11.setBounds(280, 50,550,600);
		main.add(board11,JLayeredPane.PALETTE_LAYER);
		
		//方块所在的地方
		p=new JPanel(new GridLayout(controller.getX(),controller.getX()));
		//transparent
		p.setOpaque(false);
		//设定方块的长和宽，修改的话需要变动算法Controller部分
		board=new MyLabel[controller.getX()][controller.getX()];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j]=new MyLabel(i,j,controller);
				p.add(board[i][j]);
			}
		}
		p.setBounds(350, 220, 400,400);
		main.add(p,JLayeredPane.MODAL_LAYER);
		
		//选中时的边框
		ImageIcon selectimg=new ImageIcon(Win.class.getResource("pic/1select.png"));
		selectimg.setImage(selectimg.getImage().getScaledInstance(40, 40, 1));
		select=new JLabel(selectimg);
		select.setBounds(350, 220, 40, 40);
		main.add(select,JLayeredPane.POPUP_LAYER);
		
		//提示的边框
		//TODO 修改hint area
		ImageIcon hintimg=new ImageIcon(Win.class.getResource("pic/hint.png"));
		hintimg.setImage(hintimg.getImage().getScaledInstance(40, 40, 1));
		hint=new JLabel(hintimg);
		hint.setBounds(350, 220, 40, 40);
		main.add(hint,JLayeredPane.POPUP_LAYER);
		/********/
		ImageIcon hintimg2=new ImageIcon(Win.class.getResource("pic/hint.png"));
		hintimg2.setImage(hintimg2.getImage().getScaledInstance(40, 40, 1));
		hint2=new JLabel(hintimg2);
		hint2.setBounds(350, 220, 40, 40);
		main.add(hint2,JLayeredPane.POPUP_LAYER);
		
		//彩色边框，此部分修改会造成游戏异常
		ImageIcon boardimg=new ImageIcon(Win.class.getResource("pic/guang.png"));
		boardimg.setImage(boardimg.getImage().getScaledInstance(40,40, 1));
		JPanel p1=new JPanel(new GridLayout(controller.getX(),controller.getX()));
		p1.setOpaque(false);
		board1=new JLabel[controller.getX()][controller.getX()];
		for (int i = 0; i < board1.length; i++) {
			for (int j = 0; j < board1[i].length; j++) {
				board1[i][j]=new JLabel(boardimg);
				board1[i][j].setVisible(false);
				p1.add(board1[i][j]);
			}
		}
		p1.setBounds(350, 220, 400,400);
		main.add(p1,JLayeredPane.DRAG_LAYER);
		
		createButton();
		createWord();
		return main;
	}
	
	private void createButton() {
		//分数提示文字区
		ImageIcon scoreimg1=new ImageIcon(Win.class.getResource("pic/1score.png"));
		JLabel scoreri1=new JLabel(scoreimg1);
		scoreri1.setBounds(10, 0, 149, 81);
		main.add(scoreri1,JLayeredPane.PALETTE_LAYER);
		//记分牌
		ImageIcon scoreimg=new ImageIcon(Win.class.getResource("pic/score.png"));
		JLabel scoreri=new JLabel(scoreimg);
		scoreri.setBounds(0,81, 177, 94);
		main.add(scoreri,JLayeredPane.PALETTE_LAYER);
		//分数提示文字
		JLabel scorer=new JLabel("得分：");
		scorer.setFont(new Font("Monospace",1,23));
		scorer.setForeground(Color.WHITE);
		scorer.setBounds(50,5, 100, 50);
		main.add(scorer,JLayeredPane.MODAL_LAYER);
		//分数数字
		score=new JLabel("0");
		score.setFont(new Font("Monospace",1,23));
		score.setForeground(Color.GREEN);
		score.setBounds(60, 35, 200, 200);
		main.add(score,JLayeredPane.MODAL_LAYER);
		//时间条背景
		ImageIcon rollimg=new ImageIcon(Win.class.getResource("pic/roll.png"));
		rollimg.setImage(rollimg.getImage().getScaledInstance(750, 50, 1));
		JLabel roll1=new JLabel(rollimg);
		roll1.setBounds(100,660,750, 50);
		main.add(roll1,JLayeredPane.PALETTE_LAYER);
		//滚动时间条
		ImageIcon rollerimg =new ImageIcon(Win.class.getResource("pic/1tool.png"));
		rollerimg.setImage(rollerimg.getImage().getScaledInstance(760, 50, 1));
		roll=new JLabel(rollerimg);
		roll.setBounds(110,660,760, 50);
		main.add(roll,JLayeredPane.MODAL_LAYER);
		
	}
	public void createWord(){
		//关卡
		level=new JLabel("第1关");
		level.setFont(new Font("Sans",1,30));
		level.setForeground(Color.BLACK);
		level.setBounds(100, 500, 100, 100);
		main.add(level,JLayeredPane.POPUP_LAYER);
		//menu按钮
		ImageIcon menuimg=new ImageIcon(Win.class.getResource("pic/menu.png"));
		JLabel menu=new JLabel(menuimg);
		menu.setBounds(30, 460, 70, 70);
		main.add(menu,JLayeredPane.POPUP_LAYER);
		//quit按钮
		ImageIcon quitimg=new ImageIcon(Win.class.getResource("pic/Quit.png"));
		JLabel quit=new JLabel(quitimg);
		quit.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e) {
				System.exit(0);
				
			}
		});
		quit.setBounds(200, 580, 50, 50);
		main.add(quit,JLayeredPane.POPUP_LAYER);
		//finity，失败提示文字
		ImageIcon finityimg=new ImageIcon(Win.class.getResource("pic/finity.png"));
		finityimg.setImage(finityimg.getImage().getScaledInstance(150, 100, 1));
		finity=new JLabel(finityimg);
		finity.setBounds(445, 220, 150, 100);
		finity.setVisible(false);
		main.add(finity,JLayeredPane.POPUP_LAYER);
		//action，开始提示文字
		ImageIcon actionimg=new ImageIcon(Win.class.getResource("pic/action22.png"));
		actionimg.setImage(actionimg.getImage().getScaledInstance(150, 100, 1));
		action=new JLabel(actionimg);
		action.setBounds(450, 330, 150, 100);
		action.setVisible(false);
		main.add(action,JLayeredPane.POPUP_LAYER);
	}
	
	public JLabel getLevel() {
		return level;
	}
	public JLabel getAction() {
		return action;
	}
	public JLabel getFinity() {
		return finity;
	}
	public JLabel getRoll() {
		return roll;
	}
	public JLabel getSelect() {
		return select;
	}
	public void setSelect(JLabel select) {
		this.select = select;
	}
	public JLabel getScore() {
		return score;
	}
	public MyLabel[][] getBoard() {
		return board;
	}
	public void setBoard(MyLabel[][] board) {
		this.board = board;
	}
public JLabel[][] getBoard1() {
	return board1;
}
public JPanel getP() {
	return p;
}
public JLabel getHint(){
	return hint;
}
public JLabel getHint2(){
	return hint2;
}
}
