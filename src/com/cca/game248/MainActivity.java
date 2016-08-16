package com.cca.game248;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends Activity {

	private TextView tvScore;
	
	private static MainActivity mActivity=null;
	private int score=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tvScore=(TextView) findViewById(R.id.tv_score);
        
    }
    //清楚分数
    public void clearScore(){
   	 score=0;
   	 showScore(score);
   	 
    }
    //显示分数
    public void showScore(int s){
   
   	 tvScore.setText(score+"");
    }
    //添加分数
    public void addScore(int s){
   	 score+=s;
   	 showScore(score);
    }
    
    public MainActivity(){
   	mActivity=this; 
    }
    
    //获取实例
    public static MainActivity getMainActivity(){
   	 return mActivity;
    }

  
}
