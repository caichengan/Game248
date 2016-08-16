package com.cca.game248;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 
 *@包名:com.cca.game248
 *@类名:Card
 *@时间:上午9:24:32
 *
 * @author :蔡程安
 *
 *@描述:TODO
 */
public class Card extends FrameLayout
{

	private int num=0;
	private TextView label;

	public Card(Context context) {
		super(context);
		
		label=new TextView(context);
		label.setTextSize(30);
		label.setGravity(Gravity.CENTER);
		setBackgroundColor(0x33ffffff);
		LayoutParams params=new LayoutParams(-1, -1);
		params.setMargins(20, 20, 0, 0);
		addView(label, params);
		setNum(0);
	}

	public int getNum()
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
		if(num==0){
		label.setText("");	
		}else{
		label.setText(num+"");
		}
	}
	public boolean equals(Card o)
	{
		return getNum()==o.getNum();
	}
	

}
