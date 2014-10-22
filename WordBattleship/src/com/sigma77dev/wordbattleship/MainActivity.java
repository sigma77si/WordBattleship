package com.sigma77dev.wordbattleship;

import com.sigma77dev.wordbattleship.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.os.Build;

public class MainActivity extends Activity implements OnClickListener {
	Button startgame, exit;
	RelativeLayout rl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startgame = (Button) findViewById(R.id.button1);
		exit = (Button) findViewById(R.id.button2);
		startgame.setOnClickListener(this);
		exit.setOnClickListener(this);
		rl = (RelativeLayout) findViewById(R.id.container);

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1: {
			Intent play = new Intent(MainActivity.this, GameActivity.class);
			startActivity(play);

			rl.setBackgroundResource(R.drawable.second_screen);
			startgame.setVisibility(View.INVISIBLE);
			exit.setVisibility(View.INVISIBLE);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					rl.setBackgroundResource(R.drawable.wordbatleship_pic);
					startgame.setVisibility(View.VISIBLE);
					exit.setVisibility(View.VISIBLE);

				}
			}, 5000);

		}
			break;
		case R.id.button2: {
			finish();
		}
			break;

		}
		// TODO Auto-generated method stub

	}

}
