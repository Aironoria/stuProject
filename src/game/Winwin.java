package game;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Winwin extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Winwin(){
		init();
		
	}

	private void init() {
		//新的面板
		setBounds(300, 300, 400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//赢的背景
		ImageIcon img=new ImageIcon(Winwin.class.getResource("pic/backdrop08.jpg"));
		img.setImage(img.getImage().getScaledInstance(400, 300, 1));
		JLayeredPane p=new JLayeredPane();
		JLabel back=new JLabel(img);
		back.setBounds(0,0, 400, 300);
		p.add(back,JLayeredPane.DEFAULT_LAYER);
		//提示获胜
		JLabel lbl=new JLabel("恭喜您，赢了！！！！！！！！！！！！1");
		lbl.setFont(new Font("Monospace",1,50));
		lbl.setForeground(Color.RED);
		lbl.setBounds(0, 0, 400, 200);
		p.add(lbl,JLayeredPane.PALETTE_LAYER);
		setContentPane(p);
	}
}
