package com.showview.util;

public class ShowInfo {
	private int size_row, size_col;
	private int shape = 1;
	private String data;
	private int light_sec = 0;
	private float run_sec = 0;
	private int run_step = 0;
	private String color = "#00ff00";
	private String sound;
	
	public void setSize(String size) {
		String[] ss = size.split("[*]");
		this.size_row = Integer.parseInt(ss[0]);
		this.size_col = Integer.parseInt(ss[1]);
	}
	
	public void setRun(String sec, String step) {
		this.run_sec = Float.parseFloat(sec);
		//this.run_sec = Integer.parseInt(sec);
		this.run_step = Integer.parseInt(step);
	}
	
	public int getSizerow() {
		return this.size_row;
	}
	
	public int getSizecol() {
		return this.size_col;
	}
	
	public float getRunsec() {
		return run_sec;
	}
	
	public int getRunstep() {
		return run_step;
	}
	public int getShape() {
		return shape;
	}
	public void setShape(int shape) {
		this.shape = shape;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public int getLight_sec() {
		return light_sec;
	}
	public void setLight_sec(int light_sec) {
		this.light_sec = light_sec;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	
	
}
