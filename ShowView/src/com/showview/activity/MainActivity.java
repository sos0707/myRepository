package com.showview.activity;

import java.util.List;

import com.showview.R;
import com.showview.util.PullParseXML;
import com.showview.util.ShowInfo;
import com.showview.util.ShowView;
import com.timeview.TimeView;

import android.app.Activity;
import android.app.Service;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button btn1, btn2;
	private ShowView sv;
	private List<ShowInfo> list;
	private TimeView tivTime;
	private Button btnVibra, btnRing, btnStop;
	private Vibrator vibra;
	private MediaPlayer player;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sv = (ShowView) findViewById(R.id.sv);
		tivTime = (TimeView) findViewById(R.id.tv);
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btnVibra = (Button) findViewById(R.id.btn_vibra);
		btnRing = (Button) findViewById(R.id.btn_ring);
		btnStop = (Button) findViewById(R.id.btn_stop);
		
		vibra = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
		player = new MediaPlayer();
		try {
			player.setDataSource(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
			player.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
			player.setLooping(true);
			player.prepare();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		btnVibra.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tryVibra();
			}
		});
		
		btnRing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tryRing();
			}
		});
		
		btnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stop();
			}
		});
//		getResources().getXml(R.xml.show);
//		list = new PullParseXML().PullParseShowXML();
		getResources().getColor(R.color.showcolor);
//		tivTime.setTime("08:00", "23:00");
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sv.play();
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sv.stop();
			}
		});
	}
	
	private void tryVibra() {
		long[] pattern = { 500, 1500, 500, 1500};
		vibra.vibrate(pattern, 0);
	}
	
	private void tryRing() {
		player.start();
	}
	
	private void stop() {
		if(vibra != null && vibra.hasVibrator()) {
			vibra.cancel();
		}
		if(player != null && player.isPlaying()) {
			player.stop();
		}
	}
}
