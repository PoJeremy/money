package hyh.money.ui.setting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hyh.money.R;
import hyh.money.ui.main.Act_Main;
import hyh.money.view.PasswordLock.GestureLockShowAdapter;
import hyh.money.view.PasswordLock.GestureLockView;

public class Act_GestureLock extends Activity {

	private GestureLockView gv;
	private TextView textView;
	private Animation animation;
	private LinearLayout layout;
	private float x,currentX,tempX;
	private RelativeLayout.LayoutParams params1;
	private int width,height;
	private ImageView imageView;
	public static int SETCOUNT = 2;
	private GridView gridView;
	private  ArrayList<Integer> pointPosition = new ArrayList<Integer>();
	private GestureLockShowAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_lock);

		gv = (GestureLockView) findViewById(R.id.gv);
		textView = (TextView) findViewById(R.id.textview);
		layout = (LinearLayout) findViewById(R.id.layout);
		gridView = (GridView) findViewById(R.id.gv_show);
		//缩略图显示
		adapter = new GestureLockShowAdapter(getApplicationContext(), pointPosition);
		gridView.setAdapter(adapter);

		animation = new TranslateAnimation(-10, 10, 0, 0);
		animation.setDuration(50);
		animation.setRepeatCount(10);
		animation.setRepeatMode(Animation.REVERSE);
		//是否设置密码
		SharedPreferences preferences = getSharedPreferences("locked", MODE_PRIVATE);
		Boolean isbidExist = preferences.getBoolean("have", false);

		if(!isbidExist){
			textView.setVisibility(View.VISIBLE);
			textView.setText("请设置密码");
			textView.startAnimation(animation);
			gridView.setVisibility(View.VISIBLE);

		}else {
			SharedPreferences preferences3 = getSharedPreferences("locked",MODE_PRIVATE);
			String Stranswer = preferences3.getString("answer", "0124678");
			gridView.setVisibility(View.VISIBLE);
			gv.setKey(Stranswer); // 取出设置的密码
			SETCOUNT = 0;
		}

		gv.setOnGestureFinishListener(new GestureLockView.OnGestureFinishListener() {
			private String key_first;
			private String key_second;

			@Override
			public void OnGestureFinish(boolean success,String key,int setCount) {
				Log.e("key", key);
				if (setCount==2) {
					textView.setVisibility(View.VISIBLE);
					textView.setText("确认密码");
					textView.startAnimation(animation);
					textView.setTextColor(Color.GREEN);
					key_first = key;
					for (int i = 0; i < key.length(); i++) {
						String chatitem = key.charAt(i)+"";
						int aitem =Integer.parseInt(chatitem);
						pointPosition.add(aitem);
					}
					adapter.notifyDataSetChanged();
					
					SETCOUNT--;

					return;
				}else if (setCount==1) {
					key_second = key;
					if (key_first.equals(key_second)) {
						finish();

//						gridView.setVisibility(View.VISIBLE);
//						textView.setVisibility(View.VISIBLE);
//						textView.setText("设置成功请录入密码");
//						textView.startAnimation(animation);
//						textView.setTextColor(Color.GREEN);
						SETCOUNT--;
						SharedPreferences preferences2 = getSharedPreferences("locked",MODE_PRIVATE);
						Editor edit = preferences2.edit();
						edit.putBoolean("have", true);
						edit.putString("answer", key_second);
						edit.commit();
						gridView.setVisibility(View.GONE);
						gv.setKey(key_first);
					}else {
						gridView.setVisibility(View.VISIBLE);
						textView.setVisibility(View.VISIBLE);
						textView.setText("两次密码不一致");
						textView.setTextColor(Color.RED);
						textView.startAnimation(animation);
						SETCOUNT =2;
						pointPosition.clear();
						adapter.notifyDataSetChanged();
					}
					return;
				}
				if (SETCOUNT==0) {
					if (!success) {
						textView.setVisibility(View.VISIBLE);
						textView.setText("密码错误");
						textView.startAnimation(animation);
					}else {
						textView.setVisibility(View.VISIBLE);
						textView.setText("密码正确");
						textView.setTextColor(Color.GREEN);
//						Intent intent = new Intent(Act_GestureLock.this, Act_Main.class);
//						intent.putExtra("flag", 0);
//						startActivity(intent);
//						int page = getIntent().getIntExtra("flag", 0);
//						if(page == 10) {
							finish();
//						}
					}
				}

			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//设置密码中断   
		if (SETCOUNT ==1) {
			SETCOUNT = 2;
		}
	
	}
}




















