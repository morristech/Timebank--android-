package org.timebank;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.Toast;

public class options extends Activity {
	private String rez, rez1, numb, site, sel;
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	CheckBox gps1;
	private Boolean gpsidl;
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.options);
		Bundle extras = getIntent().getExtras();
		settings = getApplicationContext().getSharedPreferences("timebank", 0);
		numb = extras.getString("numb");
		gpsidl=settings.getBoolean("gpsidl", false);
		if (gpsidl) {
		 gps1 = (CheckBox)findViewById(R.id.checkBox1);
		 gps1.setChecked(gpsidl);
		}
    }
	public void gps (View view) {
		editor = settings.edit();
		gps1 = (CheckBox)findViewById(R.id.checkBox1);
		editor.putBoolean ("gpsidl", gps1.isChecked());
		editor.commit();
		if (gps1.isChecked()==true) {
			Toast toast = Toast.makeText(options.this, "Перезапустите GPS отправку", Toast.LENGTH_SHORT); 
			toast.show();
		}
	}
	public void back (View view) {
		Intent mIntent = new Intent();
		mIntent.setClass(options.this, two.class);
		mIntent.putExtra("numb", numb);
		startActivity(mIntent);
		finish ();
	}
	
}
