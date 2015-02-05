package com.timeview;

import com.showview.R;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class TimeView extends View {
	
	private Paint paint = new Paint();
	private DisplayMetrics dm = getResources().getDisplayMetrics();
	private int mWidth = dm.widthPixels;
	private int mHeight = dm.heightPixels;
	private String sTime = "08:00";
	private String eTime = "24:40";
	public TimeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TimeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setShowInfo();
	}
	
	public TimeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setShowInfo();
	}
	
	private void setShowInfo() {
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		mWidth = this.getMeasuredWidth();
		mHeight = this.getMeasuredHeight();
		int tHeight = mHeight / 2;
		int divWidth = mWidth / 48;
		int divHeight = mHeight / 4;
		String ss[] = sTime.split("\\:");
		String se[] = eTime.split("\\:");
		int s = ( Integer.valueOf(ss[0])*2 ) + ( Integer.valueOf(ss[1])%30 );
		int e = ( Integer.valueOf(se[0])*2 ) + ( Integer.valueOf(se[1])%30 );
		Log.i("TEST", s + " : " + e);
//		drawVLine(0, 0, divWidth*10, mHeight, paint, canvas, 0);
//		drawVLine(divWidth*10, 0, divWidth*28, mHeight, paint, canvas, 1);
//		drawVLine(divWidth*38, 0, divWidth*10, mHeight, paint, canvas, 0);
		drawRectView(0, 0, divWidth*s, divHeight*2, paint, canvas, 0);
		drawRectView(divWidth*s, 0, divWidth*(e-s), divHeight*2, paint, canvas, 1);
		if( 48 != e) {
			drawRectView(divWidth*e, 0, divWidth*(48-e), divHeight*2, paint, canvas, 0);
		}
		paint.setColor(Color.BLACK);
		paint.setTextSize(tHeight);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		canvas.drawText(sTime, divWidth*1, mHeight, paint);
		canvas.drawText(eTime, divWidth*34, mHeight, paint);
	}
	
	private void drawVLine(float left, float top, float width, float height, Paint paint, Canvas canvas, int i) {
		if(i == 0) {
			paint.setColor(Color.WHITE);
		}else {
			paint.setColor(Color.GREEN);
		}
		canvas.drawLine(left, top, left+width, top+height, paint);
	}
	
	/**
	 * 画一个矩形
	 * @param left    左上角x
	 * @param top     左上角y
	 * @param width   宽
	 * @param hight   高
	 * @param data    数据  0或1
	 * @param paint
	 * @param canvas
	 */
	private void drawRectView( float left, float top, float width, float hight, Paint paint, Canvas canvas, int i) {
		if( i == 1) {
//			paint.setColor(Color.rgb(135, 206, 235));
			paint.setColor(getResources().getColor(R.color.showcolor));
		}else {
			paint.setColor(Color.WHITE);
		}
		paint.setAlpha(90);
		canvas.drawRect(left, top, left+width, top+hight, paint);
		
		paint.setColor(Color.rgb(169, 169, 169));
		canvas.drawLine(left, top, left+width, top, paint);
		canvas.drawLine(left+width, top, left+width, top+hight, paint);
		canvas.drawLine(left+width, top+hight, left, top+hight, paint);
		canvas.drawLine(left, top, left, top+hight, paint);
	}
	
	public void setTime(String time1, String time2) {
		this.sTime = time1;
		this.eTime = time2;
		this.postInvalidate();
	}
}
