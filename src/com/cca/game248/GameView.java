package com.cca.game248;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout
{
	private Card[][]CardMaps=new Card[4][4];//定义二维数组存储卡片
	private List<Point> emptyPoint=new ArrayList<Point>();//定义集合，存储空点

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		
	}

	

	public GameView(Context context) {
		super(context);
		initView();
	}
	private void initView()
	{
	 setColumnCount(4);
	 
	 setBackgroundColor(0xffbbff00);
	 
	 //触摸事件处理
		setOnTouchListener(new View.OnTouchListener() {
			private float startX=0;
			private float startY=0;
			private float offSetX=0;
			private float offSetY=0;
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				
				switch(event.getAction()){
					case MotionEvent.ACTION_DOWN:
						startX=event.getX();
						startY=event.getY();
						
						break;
					case MotionEvent.ACTION_UP:
						 offSetX=event.getX()-startX;
						 offSetY=event.getY()-startY;
						 
						 if(Math.abs(offSetX)>Math.abs(offSetY)){//水平
							 if(offSetX>5){//向右
								 System.out.println("right");
								 swipeRight();
							 }else if(offSetX<-5){//向左
								 System.out.println("left");
								 swipeLeft();
							 }
						 }else{
							 if(offSetY>5){//向下
								 System.out.println("dowm");
								 swipeDowm();
							 }else if(offSetY<-5){//向上
								 System.out.println("up");
								 swipeUp();
							 }
						 }
						 
						break;
				}
				
				return true;
			}
		});
		
	}
	//开始游戏
	public void startGame(){
		MainActivity.getMainActivity().clearScore();
		for(int y=0;y<4;y++){
			for(int x=0;x<4;x++){
				CardMaps[x][y].setNum(0);
			}
		}
		
		addRandomNum();
		addRandomNum();
	}
	
	//添加随机数
	public void addRandomNum(){
		emptyPoint.clear();
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				if(CardMaps[i][j].getNum()<=0){
					emptyPoint.add(new Point(i,j));
				}
			}
		}
		Point p=emptyPoint.remove((int)(Math.random()*emptyPoint.size()));
		CardMaps[p.x][p.y].setNum(Math.random()>0.1?2:4);
	}
	
	//屏幕宽高改变的时候执行
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		
		int cardWidth=(Math.min(w, h)-10)/4;
		
		addcard(cardWidth,cardWidth);
		
		startGame();
	}


//添加卡片4*4
	private void addcard(int cardWidth, int cardWidth2)
	{
		Card c;
		for(int y=0;y<4;y++){
			for(int x=0;x<4;x++){
				c=new Card(getContext());
				c.setNum(2);
				CardMaps[x][y]=c;
				
				addView(c, cardWidth, cardWidth);
			}
		}
	}
	//判断游戏结束的逻辑
	private void cheakComplete(){
		boolean complete=true;
		ALL:
			for(int y=0;y<4;y++){
				for(int x=0;x<4;x++){
					if(CardMaps[x][y].getNum()==0||
							(x>0&&CardMaps[x][y].equals(CardMaps[x-1][y]))||
							(x<3&&CardMaps[x][y].equals(CardMaps[x+1][y]))||
							(y>0&&CardMaps[x][y].equals(CardMaps[x][y-1]))||
							(y<3&&CardMaps[x][y].equals(CardMaps[x][y+1]))){
						complete=false;
						break ALL;
					}
				}
			}
		
		if(complete){
			new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来", new AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					startGame();
				}
			}).show();

		}
		
	}

	protected void swipeRight()
	{
		boolean merge=false;
		for(int y=0;y<4;y++){
			for(int x=3;x>=0;x--){
				for(int x1=x-1;x1>=0;x1--){
					if(CardMaps[x1][y].getNum()>0){
						
						if(CardMaps[x][y].getNum()<=0){
							CardMaps[x][y].setNum(CardMaps[x1][y].getNum());
							CardMaps[x1][y].setNum(0);
							
							x++;
							merge=true;
						}else if(CardMaps[x][y].equals(CardMaps[x1][y])){
							CardMaps[x][y].setNum(CardMaps[x][y].getNum()*2);
							CardMaps[x1][y].setNum(0);
							MainActivity.getMainActivity().addScore(CardMaps[x][y].getNum());
							merge=true;
							
						}
						
						break;
					}
					
				}
			}
			
		}
		if(merge){
			addRandomNum();
			cheakComplete();
		}
		
	}

	protected void swipeLeft()
	{
		boolean merge=false;
		for(int y=0;y<4;y++){
			for(int x=0;x<4;x++){
				for(int x1=x+1;x1<4;x1++){
					if(CardMaps[x1][y].getNum()>0){
						
						if(CardMaps[x][y].getNum()<=0){
							CardMaps[x][y].setNum(CardMaps[x1][y].getNum());
							CardMaps[x1][y].setNum(0);
							
							x--;
							merge=true;
						}else if(CardMaps[x][y].equals(CardMaps[x1][y])){
							CardMaps[x][y].setNum(CardMaps[x][y].getNum()*2);
							CardMaps[x1][y].setNum(0);
							MainActivity.getMainActivity().addScore(CardMaps[x][y].getNum());
							merge=true;
							
						}
						break;
					}
					
				}
			}
			
		}
		if(merge){
			addRandomNum();
			cheakComplete();
		}
		
	}

	protected void swipeDowm()
	{
		boolean merge=false;
		for(int x=0;x<4;x++){
			for(int y=3;y>=0;y--){
				for(int y1=y-1;y1>=0;y1--){
					if(CardMaps[x][y1].getNum()>0){
						
						if(CardMaps[x][y].getNum()<=0){
							CardMaps[x][y].setNum(CardMaps[x][y1].getNum());
							CardMaps[x][y1].setNum(0);
							
							y++;
							merge=true;
						}else if(CardMaps[x][y].equals(CardMaps[x][y1])){
							CardMaps[x][y].setNum(CardMaps[x][y].getNum()*2);
							CardMaps[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(CardMaps[x][y].getNum());
						
							merge=true;
						}
						break;
					}
					
				}
			}
			
		}
		if(merge){
			addRandomNum();
			cheakComplete();
		}
		
	}

	protected void swipeUp()
	{
		boolean merge=false;
		for(int x=0;x<4;x++){
			for(int y=0;y<4;y++){
				for(int y1=y+1;y1<4;y1++){
					if(CardMaps[x][y1].getNum()>0){
						
						if(CardMaps[x][y].getNum()<=0){
							CardMaps[x][y].setNum(CardMaps[x][y1].getNum());
							CardMaps[x][y1].setNum(0);
							
							y--;
							merge=true;
						}else if(CardMaps[x][y].equals(CardMaps[x][y1])){
							CardMaps[x][y].setNum(CardMaps[x][y].getNum()*2);
							CardMaps[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(CardMaps[x][y].getNum());
							merge=true;
							
						}
						break;
					}
					
				}
			}
			
		}
		if(merge){
		addRandomNum();
		}
		
	}

}
