package org.timebank;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TextView;

public class obj extends Activity {
	TableLayout tableLayout;
	TableRow tableRow;
	TableRow tableRow1;
	TextView reson;
	TextView reson1;
	TextView reson2;
	TextView reson3;
	TextView reson4;
	TextView reson5;
	private List ter;
	private String rez, rez1, numb, site, sel;
@Override
public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.obj);
		Bundle extras = getIntent().getExtras();
		rez = extras.getString("rez");
		rez1 = extras.getString("rez1");
		numb = extras.getString("numb");
		sel = extras.getString("sel");
		loadd(0);
		ter = new ArrayList();
    }
public void onBackPressed()
{
Intent mIntent1 = new Intent();
mIntent1.setClass(obj.this, spros.class);
mIntent1.putExtra("numb", numb);
mIntent1.putExtra("rez", rez1);
mIntent1.putExtra("sel", sel);
startActivity(mIntent1);
finish ();
}

private class MyThread7 extends Thread {
	@Override
	public void run() {
		try {
		    rez = TimebankActivity.postrec(site, ter);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}
}

public void refr (View view) {
	loadd (1);
}

private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
ImageView bmImage;

public DownloadImageTask(ImageView bmImage) {
    this.bmImage = bmImage;
}

protected Bitmap doInBackground(String... urls) {
    String urldisplay = urls[0];
    Bitmap mIcon11 = null;
    try {
        InputStream in = new java.net.URL(urldisplay).openStream();
        mIcon11 = BitmapFactory.decodeStream(in);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return mIcon11;
}

protected void onPostExecute(Bitmap result) {
   bmImage.setImageBitmap(result);
}}

public void loadd (int j)  {
	if (j==1) {
		tableLayout=(TableLayout)findViewById(R.id.maintable);
		tableLayout.removeAllViews();
		rez="";
    	site = "http://timebankspb.ru/component/";
    	ter.clear();
    	ter.add(new BasicNameValuePair("view", "mobile"));
        ter.add(new BasicNameValuePair("layout", "mobile"));
        ter.add(new BasicNameValuePair("no_html", "1"));
        ter.add(new BasicNameValuePair("idx", "5"));
        ter.add(new BasicNameValuePair("no", rez.substring(1, rez.indexOf("|"))));
        new MyThread7().start();
		while (rez=="") {}
	}
	if (rez.charAt(0)=='>') {
				int prom=0;
				prom=rez.indexOf("|")+1;
				new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
		        .execute("http://timebankspb.ru/images/com_adsmanager/img/"+rez.substring(1, rez.indexOf("|"))+"a.jpg");
				reson = (TextView)findViewById(R.id.textView1);
				reson.setText(rez.substring(prom, rez.indexOf("|", prom)));
				prom=rez.indexOf("|", prom)+1;
				reson = (TextView)findViewById(R.id.textView2);
				reson.setText(rez.substring(prom, rez.indexOf("|", prom)));
				prom=rez.indexOf("|", prom)+1;
				reson = (TextView)findViewById(R.id.textView3);
				reson.setText(rez.substring(prom, rez.indexOf("|", prom)));
				prom=rez.indexOf("|", prom)+1;
				reson = (TextView)findViewById(R.id.textView4);
				reson.setText(rez.substring(prom, rez.indexOf("|", prom)));
				prom=rez.indexOf("|", prom)+1;
				reson = (TextView)findViewById(R.id.textView5);
				reson.setText(rez.substring(prom, rez.indexOf("|", prom)));
				prom=rez.indexOf("|", prom)+1;
				reson = (TextView)findViewById(R.id.textView6);
				reson.setText(rez.substring(prom, rez.indexOf("<", prom)));
				prom=0;
				if (j==1) {Toast toast = Toast.makeText(obj.this, "Обновлено успешно", Toast.LENGTH_SHORT); 
				toast.show();}
	} else {
		Toast toast = Toast.makeText(obj.this, "Проблемы с интернетом", Toast.LENGTH_SHORT); 
		toast.show();
		rez="";
	}}
}