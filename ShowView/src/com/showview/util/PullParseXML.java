package com.showview.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class PullParseXML {
	
	public List<ShowInfo> PullParseShowXML() {
		List<ShowInfo> list = null;
		ShowInfo showinfo = null;
		
		try {
			XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
			XmlPullParser xpp = xppf.newPullParser();
			xpp.setInput(Thread.currentThread().getContextClassLoader().getResourceAsStream("show.xml"), "UTF-8");
			
			int eventType = xpp.getEventType();
			
			try {
				while(eventType != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
//					Log.i("pullxml", "nodeName : " + nodeName);
					switch(eventType) {
					case XmlPullParser.START_DOCUMENT:
						list = new ArrayList<ShowInfo>();
						break;
					case XmlPullParser.START_TAG:
						if("matrix".equals(nodeName)) {
							showinfo = new ShowInfo();
							
						}else if("size".equals(nodeName)) {
							showinfo.setSize(xpp.nextText());
							
						}else if("shape".equals(nodeName)) {
							showinfo.setShape(Integer.parseInt(xpp.nextText()));
							
						}else if("data".equals(nodeName)) {
							showinfo.setData(xpp.nextText());
							
						}else if("light".equals(nodeName)) {
							showinfo.setLight_sec(Integer.parseInt(xpp.getAttributeValue(0)));
							
						}else if("run".equals(nodeName)) {
							showinfo.setRun(xpp.getAttributeValue(0), xpp.getAttributeValue(1));
							
						}else if("color".equals(nodeName)) {
							showinfo.setColor(xpp.getAttributeValue(0));
							
						}else if("sound".equals(nodeName)) {
							showinfo.setSound(xpp.getAttributeValue(0));
							
						}
						break;
					case XmlPullParser.END_TAG:
						if("matrix".equals(nodeName)) {
							list.add(showinfo);
							showinfo = null;
						}
						break;
					default:
						break;
					}
//					Log.i("pullxml", " end ");
					eventType = xpp.next();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
                e.printStackTrace();
            }
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return list;
	}
	
}
