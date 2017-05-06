package game;

import javax.swing.JLabel;

public class TollThread extends Thread{
	private Controller controller;
	private JLabel roll;
	public static boolean go=true;
	public static int time=100;
	private int length;
	public TollThread(Controller controller,JLabel roll){
		this.controller=controller;
		this.roll=roll;
		length=760;
	}
	public void run(){
		int x=roll.getBounds().x;
		int y=roll.getBounds().y;
		int height=roll.getBounds().height;
		while(true){
			if(!go)
				break;
			if(length<=0){
				controller.lost();
				break;
			}
			length-=1;
			roll.setBounds(x, y, length, height);
			try {
				sleep(time);
			} catch (InterruptedException e) {
			}
		}
	}
	public void setLength(int length) {
		this.length = length;
	}
	public void addlength(int l){
		int ll=length+l;
		length=ll>760?760:ll;
	}
	public int getLength() {
		return length;
	}
}
