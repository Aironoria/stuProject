package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {
   private Win win;
   private Grid grid;
   private int x;
   private int score;
   private int level;
   private TollThread tollthread;
   private List<Integer> list;
   boolean bb=true;
   private Winwin winwin;
   private int hint[]=new int[4];
   private boolean isGo=true;
   private boolean isF=false;
   private int time=0;
   
   public Controller(){
	  x=10;
	  //生成其它组件的对象
	  win=new Win(this);
	  grid=new Grid(x);
	  list=new ArrayList<Integer>();
	  tollthread=new TollThread(this,win.getRoll());
	  winwin=new Winwin();
}
public void beforeStart(){
	//
	if(!bb){
		return;
	}
	
	//初始化矩阵
	hint(false);
	grid.start();
	showGame();

	
	//wait 0.1s
	try {
		Thread.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	new Thread(){
		public void run(){
			int j=0;
			while(true){
				bb=false;
				if(j>=220){
					Controller.this.start();
					bb=true;
					break;
				}
				try {
					//初始生成的方块下降速度
					sleep(15);
					j+=10;
					win.getP().setBounds(350,j, 400, 400);
				} catch (InterruptedException e) {
				}
			}
		}
	}.start();
}
public void start(){
	
	win.getFinity().setVisible(false);
	TollThread.go=false;
	win.getScore().setEnabled(true);
	win.getAction().setVisible(true);
	
	grid.start();
	showGame();
	startbomp();
	score=0;
	level=1;
	win.getLevel().setText("第"+Integer.toString(level)+"关");
	win.getScore().setText(Integer.toString(score));
	tollthread=new TollThread(this,win.getRoll());
	new Thread(){
		public void run(){
			try {
				Thread.sleep(1500);
				win.getAction().setVisible(false);
				//时间条的初始化
				TollThread.go=true;
				//控制速度
				TollThread.time=100;
				tollthread.start();
			} catch (InterruptedException e) {
			}
		}
	}.start();
}
public void levelUp(){
	//升级方式，每等级上涨1000分
	if(score>=1000*level){
		level++;
		if(level>8){
			//大于8等级的话
			win.setVisible(false);
			winwin.setVisible(true);
			return;
		}
		win.getLevel().setText("第"+Integer.toString(level)+"关");
		win.getAction().setVisible(true);
		
		grid.start();
		showGame();
		startbomp();
		//时间速度
		TollThread.time-=10;
		
		new Thread(){
			public void run(){
				try {
					Thread.sleep(2000);
					win.getAction().setVisible(false);
					//时间长度随等级变化
					int l=tollthread.getLength()+100*(8-level);
					l=l>760?760:l;
					tollthread.setLength(l);
				} catch (InterruptedException e) {
				}
			}
		}.start();
	}
}
public void lost(){
	//时间条置于0
	if(tollthread.getLength()<=0){
		//不能再加分
		win.getScore().setEnabled(false);
		score=0;
		level=1;
		isF=true;
		//提示失败
		win.getFinity().setVisible(true);
	}
}
public void startbomp(){
	new Thread(){
		public void run(){
			try {
				sleep(300);
				for(int i=19;i>=10;i--){
					for(int j=0;j<10;j++){
						bomp(i,j);
						sleep(10);
					}
				}
			} catch (InterruptedException e) {
			}
		}
	}.start();
}
public boolean bompppp(){
	//全表扫描看有是否有消除的可能（有解）
	//TODO 增加提示两个可交换的方块的功能，使用红框标记出来
	int type=0;
	//第一行和第二行
	for(int j=0;j<9;j++){
			if(type==grid.getGrid()[10][j]&&type==grid.getGrid()[11][j+1]){
				hint[0]=10;hint[1]=j+1;hint[2]=11;hint[3]=j+1;
				return true;
			}
			else if(type==grid.getGrid()[10][j+1]&&type==grid.getGrid()[11][j]){
				hint[0]=10;hint[1]=j;hint[2]=11;hint[3]=j;
				return true;
			}
			type=grid.getGrid()[10][j];
		}
	type=0;
	//最后一行和倒数第二行
	for(int j=0;j<9;j++){
		if(type==grid.getGrid()[19][j]&&type==grid.getGrid()[18][j+1]){
			hint[0]=19;hint[1]=j+1;hint[2]=18;hint[3]=j+1;
			return true;
		}
		else if(type==grid.getGrid()[19][j+1]&&type==grid.getGrid()[18][j]){
			hint[0]=19;hint[1]=j;hint[2]=18;hint[3]=j;
			return true;
		}
		type=grid.getGrid()[19][j];
	}
	//其它行比较是否可以三消
	for(int i=11;i<19;i++){
		type=0;
		for(int j=0;j<9;j++){
			if(type==grid.getGrid()[i][j]&&type==grid.getGrid()[i+1][j+1]){
				hint[0]=i;hint[1]=j+1;hint[2]=i+1;hint[3]=j+1;
				return true;
			}else if(type==grid.getGrid()[i][j]&&type==grid.getGrid()[i-1][j+1]){
				hint[0]=i;hint[1]=j+1;hint[2]=i-1;hint[3]=j+1;
				return true;
			}
			/*****************/
			else if(type==grid.getGrid()[i][j+1]&&type==grid.getGrid()[i+1][j]){
				hint[0]=i;hint[1]=j;hint[2]=i+1;hint[3]=j;
				return true;
			}else if(type==grid.getGrid()[i][j+1]&&type==grid.getGrid()[i-1][j]){
				hint[0]=i;hint[1]=j;hint[2]=i-1;hint[3]=j;
				return true;
			}
			type=grid.getGrid()[i][j];
		}
	}
	type=0;
	//第一列和第二列
	for(int i=10;i<19;i++){
		if(type==grid.getGrid()[i][0]&&type==grid.getGrid()[i+1][1]){
			hint[0]=i+1;hint[1]=0;hint[2]=i+1;hint[3]=1;
			return true;
		}else if(type==grid.getGrid()[i+1][0]&&type==grid.getGrid()[i][1]){
			hint[0]=i;hint[1]=0;hint[2]=i;hint[3]=1;
			return true;
		}
		type=grid.getGrid()[i][0];
	}
	type=0;
	//倒数两列
	for(int i=10;i<19;i++){
		if(type==grid.getGrid()[i][9]&&type==grid.getGrid()[i+1][8]){
			hint[0]=i+1;hint[1]=8;hint[2]=i+1;hint[3]=9;
			return true;
		}else if(type==grid.getGrid()[i+1][9]&&type==grid.getGrid()[i][8]){
			hint[0]=i;hint[1]=8;hint[2]=i;hint[3]=9;
			return true;
		}
		type=grid.getGrid()[i][9];
	}
	//竖着的是否可以三消
	for(int j=1;j<9;j++){
		type=0;
		for(int i=10;i<19;i++){
			if(type==grid.getGrid()[i][j]&&type==grid.getGrid()[i+1][j+1]){
				hint[0]=i+1;hint[1]=j;hint[2]=i+1;hint[3]=j+1;
				return true;
			}else if(type==grid.getGrid()[i][j]&&type==grid.getGrid()[i+1][j-1]){
				hint[0]=i+1;hint[1]=j-1;hint[2]=i+1;hint[3]=j;
				return true;
			}
			/******************/
			else if(type==grid.getGrid()[i+1][j]&&type==grid.getGrid()[i][j+1]){
				hint[0]=i;hint[1]=j;hint[2]=i;hint[3]=j+1;
				return true;
			}else if(type==grid.getGrid()[i+1][j]&&type==grid.getGrid()[i][j-1]){
				hint[0]=i;hint[1]=j-1;hint[2]=i;hint[3]=j;
				return true;
			}
			type=grid.getGrid()[i][j];
		}
	}
		grid.start();
		showGame();
		startbomp();
		return false;
}
public void clicked(int aa,final int j) {
	//点击事件
	final int i=aa+10;
	
	if(list.size()==0||list==null){
		//计算目标方块的点击区域
		win.getSelect().setBounds(350+j*40, 220+aa*40, 40, 40);
		win.getSelect().setVisible(true);
		    //记下第一次鼠标点击的坐标
			list.add(i);
			list.add(j);
	}
	else if(list.size()==2){		
		//如果i或j的变化范围在1之内，即交换的方块距离为1
		if(((i==list.get(0)-1||i==list.get(0)+1)&&j==list.get(1))||
				((j==list.get(1)-1||j==list.get(1)+1)&&i==list.get(0))){
			//点击第二次时就取消选中状态
			win.getSelect().setVisible(false);
			new Thread(){
				public void run(){
					try {
						int a=grid.getGrid()[i][j];
						switcher(i, j, list.get(0), list.get(1),1);
						//交换选中的两个方块
						grid.getGrid()[i][j]=grid.getGrid()[list.get(0)][list.get(1)];
						grid.getGrid()[list.get(0)][list.get(1)]=a;
						sleep(200);
						//输出点击的第一个坐标
						System.out.println(list);
						
						if(list==null||list.size()==0){
							return;
						}
						//判断交换后是否可以消除
						boolean bb=bomp(i,j);
						boolean aa=bomp(list.get(0),list.get(1));
						if(!aa&&!bb){
							//如果不能消除则交换回来
							a=grid.getGrid()[i][j];
							switcher(i, j, list.get(0), list.get(1),2);
							grid.getGrid()[i][j]=grid.getGrid()[list.get(0)][list.get(1)];
							grid.getGrid()[list.get(0)][list.get(1)]=a;
							sleep(500);
						}
						if(list.size()<2||list==null){
							return;
						}
						list.remove(1);
						list.remove(0);
						//驱动hint
						hint(true);
					} catch (InterruptedException e) {
					}
				}
			}.start();
		}
		else{
			//如果第二次点击不是相邻的则重置
			win.getSelect().setVisible(false);
			list.remove(1);
			list.remove(0);
			list.add(i);
			list.add(j);
			win.getSelect().setBounds(350+j*40, 220+aa*40, 40, 40);
			win.getSelect().setVisible(true);
		}
	}
}
public void switcher(final int ii1,final int jj1,final int ii2,final int jj2,final int sw){
	//交换两个方块
	new Thread(){
		public void run(){
			int time=0;
			int aaa=0;
			int bbb=0;
			if(sw==1){
				aaa=10;
				bbb=-10;
			}
			else{
				aaa=-10;
				bbb=10;
			}
			//同一列的交换
			if(jj1==jj2){
				int i1=ii1<=ii2?ii1:ii2;
				int i2=ii1<=ii2?ii2:ii1;
				int j1=jj1;
				int j2=jj2;
				while(true){
					   //这段代码交换两个方块，而且按时间绘制动画
						win.getBoard()[i1-10][j1].setBounds(win.getBoard()[i1-10][j1].getBounds().x,
								win.getBoard()[i1-10][j1].getBounds().y+aaa,40,40);
						win.getBoard()[i2-10][j2].setBounds(win.getBoard()[i2-10][j2].getBounds().x, 
								win.getBoard()[i2-10][j2].getBounds().y+bbb, 40, 40);
						try {
							sleep(70);
							time++;
						} catch (InterruptedException e) {
						}
						if(time>=4){
							showGame();
							break;
						}
			}
			}
			else{
				//同一行的交换
				int j1=jj1<=jj2?jj1:jj2;
				int j2=jj1<=jj2?jj2:jj1;
				while(true){
					//如上，也是绘制交换动画的代码
					win.getBoard()[ii1-10][j1].setBounds(win.getBoard()[ii1-10][j1].getBounds().x+aaa,
							win.getBoard()[ii1-10][j1].getBounds().y,40, 40);
					win.getBoard()[ii2-10][j2].setBounds(win.getBoard()[ii2-10][j2].getBounds().x+bbb,
							win.getBoard()[ii2-10][j2].getBounds().y,40, 40);
					try {
						sleep(70);
						time++;
					} catch (InterruptedException e) {
					}
					if(time>=4){
						showGame();
						break;
					}
		}
			}
		}
	}.start();
}
public boolean bomp(int i1,int j1){
	//判断是否可消
	if(win.getFinity().isVisible()){
		return false;
	}
	boolean bb=false;
	int a=grid.getGrid()[i1][j1];
	int num=0;
	int a1,a2;
	for(a1=i1;a1<grid.getGrid().length;a1++){
		if(a!=grid.getGrid()[a1][j1]){
			a1-=1;
			break;
		}
		num++;
	}
	for(a2=i1-1;a2>=grid.getGrid().length-x;a2--){
		if(a!=grid.getGrid()[a2][j1]){
			a2+=1;
			break;
		}
		num++;
	}
	if(a1==20)a1=19;
	if(a2==9)a2=10;
	
	if(num>=3){
		for(int i=a2;i<=a1;i++){
			win.getBoard1()[i-10][j1].setVisible(true);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		for(int i=a2;i<=a1;i++){
			win.getBoard1()[i-10][j1].setVisible(false);
		}
		goDown(a1,j1,num);
		bb=true;
		
		if(num==3){
			score+=10;
			tollthread.addlength(8);
		}
		else if(num==4){
			score+=15;
			tollthread.addlength(10);
		}
		else{
			score+=20;
			tollthread.addlength(12);
		}
		win.getScore().setText(Integer.toString(score));
		levelUp();
	}
	num=0;
	for(a1=j1;a1<x;a1++){
		if(a!=grid.getGrid()[i1][a1]){
			a1-=1;
			break;
		}
		num++;
	}
	for(a2=j1-1;a2>=0;a2--){
		if(a!=grid.getGrid()[i1][a2]){
			a2+=1;
			break;
		}
		num++;
	}
	if(a1==10)a1=9;
	if(a2==-1)a2=0;
	if(num>=3){
		for(int i=a2;i<=a1;i++){
			win.getBoard1()[i1-10][i].setVisible(true);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		for(int i=a2;i<=a1;i++){
			win.getBoard1()[i1-10][i].setVisible(false);
		}
		for(int i=a2;i<=a1;i++){
			goDown(i1,i,1);
			bb=true;
		}
		if(num==3){
			score+=10;
			tollthread.addlength(8);
		}
		else if(num==4){
			score+=15;
			tollthread.addlength(10);
		}
		else{
			score+=20;
			tollthread.addlength(12);
		}
		win.getScore().setText(Integer.toString(score));
		levelUp();
	}
	showGame();
	return bb;
}
public void goDown(int i,int j,int n){
	//消除
	int ii1=i;
	for(int ii=0;ii<n;ii++){
		grid.getGrid()[ii1][j]=0;
		win.getBoard()[ii1-10][j].setimage(0);
		ii1--;
	}
	//将消掉方块上面的落下来

	for(int ii=i;ii>=n;ii--){
		grid.getGrid()[ii][j]=grid.getGrid()[ii-n][j];
	}
	//上面的方块随机产生
	Random r=new Random();
	for(int ii=0;ii<n;ii++){
		grid.getGrid()[ii][j]=r.nextInt(7)+1;
	}
	for(int a=i;a>=10;a--){
		bomp(a,j);
	}
}
public void showGame(){
	for(int i=grid.getGrid().length-10;i<grid.getGrid().length;i++){
		for(int j=0;j<grid.getGrid()[i].length;j++){
			win.getBoard()[i-10][j].setimage(grid.getGrid()[i][j]);
		}
	}
}
public void hint(boolean isVisible){
	if(isVisible==true){
	new Thread(){
		public void run(){
			try{
			Thread.sleep(5000);
			isGo=bompppp();
			if(isGo==false){
				lost();
			}else{
				win.getHint().setBounds(350+(hint[1])*40, 220+(hint[0]-10)*40, 40, 40);
				win.getHint().setVisible(true);
				win.getHint2().setBounds(350+(hint[3])*40, 220+(hint[2]-10)*40, 40, 40);
				win.getHint2().setVisible(true);
				time++;
			}
			}catch (InterruptedException e){				
			}
		}
	}.start();}else{
		win.getHint().setVisible(false);
		win.getHint2().setVisible(false);
	}

}
public int getX() {
	return x;
}
}
