package game;

import java.util.Random;

public class Grid {
	
    private int[][] grid;
    private int x;
    
    public Grid(int x){
    	this.x=x;
        grid=new int[2*x][x];
    }
    
    public void start(){
    	Random r=new Random();
    	for (int i = 0; i < grid.length; i++) {
	    	for (int j = 0; j < grid[i].length; j++) {
			//产生系统时间到6+1的随机数
		    	int a=r.nextInt(7)+1;
			//每一个方块对应一个数字，这些数字映射成图形
		    	grid[i][j]=a;
	        }
	    }
    }
    public int[][] getGrid() {
    	return grid;
    }
    public void setGrid(int[][] grid) {
    	this.grid = grid;
    }
    public int getX() {
    	return x;
    }
    public void setX(int x) {
    	this.x = x;
    }

}
