package com.showview.util;

import java.util.List;

import com.showview.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ShowView extends View {

	private Paint paint = new Paint();
	private DisplayMetrics dm = getResources().getDisplayMetrics();
	private int mWidth = dm.widthPixels;
	private int mHeight = dm.heightPixels;
	private String sound;
	//显示的行数 和 列数   默认=2
	private int showrow = 2;
	private int showcol = 2;
	//正方形的宽
	private int w = 3;
	private int rectwidth = (mWidth-20-(showrow-1)*w)/2;
	private int fcolwidth = rectwidth;
	//滚动的时间间隔 和 步数
	private int runsec = 0, runstep = 0, nowstep = 0;
	private int lightsec = 0;
	// 数据的行数 和 列数
	private int datarow, datacol;
	//第一列正方形显示的数据列数，       当前好画的数据列数
	private int firstdatacol = 0, nowdatacol = 0;
	//数据
	private String[][] data;
	private ShowInfo info = new PullParseXML().PullParseShowXML().get(0);
	// 是否滚动
	private boolean isMove = false;
	private boolean isLight = false;
	
	public ShowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ShowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setShowInfo();
	}
	
	public ShowView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setShowInfo();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setColor(Color.GREEN);
		paint.setAlpha(30);
		mWidth = this.getMeasuredWidth();
		mHeight = this.getMeasuredHeight();
		Log.i("Map Activity", mWidth +" : "+ mHeight);
		Toast.makeText(getContext(), mWidth +" : "+ mHeight, Toast.LENGTH_LONG).show();
		int top_x = 10;
		int top_y = 10;
		nowdatacol = firstdatacol;
		
		for(int x=0; x<showcol; x++) {
			for(int y=0; y<showrow; y++) {
				paint.setColor(Color.GREEN);
				if(x==0) {
					drawRectView(top_x, top_y, fcolwidth, rectwidth, data[y][nowdatacol], paint, canvas);
				}else {
					drawRectView(top_x, top_y, rectwidth, rectwidth, data[y][nowdatacol], paint, canvas);
				}
				top_y = top_y+rectwidth+w;
			}
			if(nowdatacol == datacol-1) {
				nowdatacol = 0;
			}else {
				nowdatacol = nowdatacol+1;
			}
			top_y = 10;
			if(x == 0) {
				top_x = top_x + fcolwidth+w;
			}else {
				top_x = top_x + rectwidth+w;
			}
		}
		
		if(fcolwidth != rectwidth) {
			for(int y=0; y<showrow; y++) {
				drawRectView(top_x, top_y, rectwidth-fcolwidth, rectwidth, data[y][nowdatacol], paint, canvas);
				top_y = top_y + rectwidth + w;
			}
			if(nowdatacol == datacol-1) {
				nowdatacol = 0;
			}else {
				nowdatacol = nowdatacol+1;
			}
		}
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
	private void drawRectView( float left, float top, float width, float hight, String data, Paint paint, Canvas canvas) {
		if(data.equals("1")) {
//			paint.setColor(Color.rgb(135, 206, 235));
			paint.setColor(getResources().getColor(R.color.showcolor));
		}else {
			paint.setColor(Color.WHITE);
		}
		paint.setAlpha(90);
		canvas.drawRect(left, top, left+width, top+hight, paint);
		
		paint.setColor(Color.rgb(211, 211, 211));
		canvas.drawLine(left, top, left+width, top, paint);
		canvas.drawLine(left+width, top, left+width, top+hight, paint);
		canvas.drawLine(left+width, top+hight, left, top+hight, paint);
		canvas.drawLine(left, top, left, top+hight, paint);
		
	}
	
	/**
	 * 滚动 1 的距离
	 */
	private void drawRectMove() {
		if(fcolwidth >= 1){
			fcolwidth = fcolwidth - 1;
		}else {
			fcolwidth = rectwidth;
			nowstep = nowstep +1;
			firstdatacol=(firstdatacol<datacol-1)? (firstdatacol+1):0;
		}
		this.postInvalidate();
	}
	
	/**
	 * 控制开始关闭滚动
	 */
	private void startMove() {
		isMove = true;
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(runsec == 0) {
					return;
				}
				try {
					this.sleep(runsec);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				while(isMove) {
					moveStep();
					try {
						this.sleep(runsec);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	/**
	 * 控制滚动的步数
	 */
	private void moveStep() {
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(nowstep != runstep) {
					drawRectMove();
					try {
						this.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				nowstep = 0;
			}
			
		}.start();
	}
	
	private void startLight() {
		isLight = true;
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(lightsec == 0) {
					return;
				}
				try {
					this.sleep(lightsec);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				while(isLight) {
					try {
						cleanData();
						ShowView.this.postInvalidate();
						this.sleep(20);
						getData();
						ShowView.this.postInvalidate();
						this.sleep(lightsec);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	private void setShowInfo() {
		this.sound = info.getSound();
		this.showrow = info.getSizerow();
		this.showcol = info.getSizecol();
		this.runsec = (int) (info.getRunsec() * 1000);
		this.runstep = info.getRunstep();
		this.lightsec = info.getLight_sec()*1000;
		String[] ss = info.getData().split("\\,");
		datarow = showrow;
		datacol = ss.length/showrow;
		data = new String[datarow][datacol];
		getData();
		if(showcol >= showrow) {
			this.rectwidth = (mWidth-20-(showcol-1)*w)/showcol;
		}else {
			this.rectwidth = (mWidth-20-(showrow-1)*w)/showrow;
		}
		fcolwidth = rectwidth;
		this.postInvalidate(); 
	}
	
	private void getData() {
		String[] ss = info.getData().split("\\,");
		datarow = showrow;
		datacol = ss.length/showrow;
		int flag = 0;
		
		for(int i=0 ;i<datarow; i++) {
			for(int j=0; j<datacol; j++) {
				data[i][j] = ss[flag++];
//				Log.i("pullxml", "get data : " + data[i][j]);
			}
		}
	}
	
	private void cleanData() {
		for(int i=0 ;i<datarow; i++) {
			for(int j=0; j<datacol; j++) {
				data[i][j] = "0";
//				Log.i("pullxml", "clean data : " + data[i][j] + datarow + datacol);
			}
		}
	}

	public void play() {
		if(!isMove) {
			this.startMove();
			this.startLight();
		}
	}
	
	public void stop() {
		this.isMove =false;
		this.isLight = false;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.mWidth = this.getMeasuredWidth();
		this.mHeight = this.getMeasuredHeight();
		if(showcol >= showrow) {
			this.rectwidth = (mWidth-20-(showcol-1)*w)/showcol;
		}else {
			this.rectwidth = (mWidth-20-(showrow-1)*w)/showrow;
		}
		fcolwidth = rectwidth;
	}
	
	
}
