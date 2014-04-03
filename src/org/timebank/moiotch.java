package org.timebank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class moiotch extends Activity {
	TableLayout tableLayout;
	TableRow tableRow;
	TableRow tableRow1;
	TextView reson;
	TextView reson1;
	TextView reson2;
	TextView reson3;
	TextView reson4;
	TextView reson5;
	TextView reson6;
	TextView reson7;
	TextView reson8;
public static final String ext = "col1";
public static final String prex = "col2";
private static String result1;
public String rez;
private static String rez1;
@Override
public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.moiotch);
		Bundle extras = getIntent().getExtras();
		rez = extras.getString(ext);
		rez1 = extras.getString(prex);
loadd(0);
    }
public void onBackPressed()
{
Intent mIntent1 = new Intent();
mIntent1.setClass(moiotch.this, two.class);
mIntent1.putExtra(two.EXT_COLS, rez1);
startActivity(mIntent1);
finish ();
}

private class MyThread78 extends Thread {
	@Override
	public void run() {
		try {
			List ter = new ArrayList();
			ter.add(new BasicNameValuePair("option", "com_tranz"));		
			ter.add(new BasicNameValuePair("view", "first"));
		    ter.add(new BasicNameValuePair("layout", "first"));
		    ter.add(new BasicNameValuePair("no_html", "1"));
		    rez = TimebankActivity.postrec("http://timebankspb.ru/", ter);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}
}

public void refr (View view) {
	loadd (1);
}

public void loadd (int j)  {
	if (j==1) {
		tableLayout=(TableLayout)findViewById(R.id.maintable);
		tableLayout.removeAllViews();
		rez="";
new MyThread78().start();
		while (rez=="") {}
	}
	rez=rez.trim();
	if (rez.charAt(0)=='>') {
		if (j==1) {Toast toast = Toast.makeText(moiotch.this, "Обновлено успешно", Toast.LENGTH_SHORT); 
		toast.show();}
	tableLayout=(TableLayout)findViewById(R.id.maintable);
	boolean prov = false; int st=0; String strok = ""; int prom=0;
	LayoutParams t1 = new LayoutParams(
            105,
            LayoutParams.WRAP_CONTENT, 1.0f);
	t1.setMargins(3, 0, 0, 0);
	LayoutParams t2 = new LayoutParams(
            185,
            LayoutParams.WRAP_CONTENT, 1.0f);
	t2.setMargins(3, 0, 0, 0);
	LayoutParams t3 = new LayoutParams(
            185,
            LayoutParams.WRAP_CONTENT, 1.0f);
	t3.setMargins(3, 0, 0, 0);
	LayoutParams t4 = new LayoutParams(
            140,
            LayoutParams.WRAP_CONTENT, 1.0f);
	t4.setMargins(3, 0, 0, 0);
	LayoutParams t5 = new LayoutParams(
            120,
            LayoutParams.WRAP_CONTENT, 1.0f);
	t5.setMargins(3, 0, 0, 0);
	LayoutParams t6 = new LayoutParams(
            185,
            LayoutParams.WRAP_CONTENT, 1.0f);
	t6.setMargins(3, 0, 0, 0);
	LayoutParams t7 = new LayoutParams(
            210,
            LayoutParams.WRAP_CONTENT, 1.0f);
	t7.setMargins(3, 0, 0, 0);
	LayoutParams t8 = new LayoutParams(
            200,
            LayoutParams.WRAP_CONTENT, 1.0f);
	t8.setMargins(3, 0, 0, 0);
	LayoutParams t9 = new LayoutParams(
            100,
            5, 1.0f);
	for (int i = 0; (i<(rez.length())); i++)
	{
		if (prov==false) {
		if (rez.charAt(i)=='>') {prov=true; st=i;
		}} else {
			if (rez.charAt(i)=='<') {
				prov=false;
				tableRow1 = new TableRow(this);
				tableRow = new TableRow(this);
				strok=rez.substring(st, (i+1));
				reson = new TextView(this);
				reson.setLayoutParams(t1);
				reson.setText(strok.substring(1, strok.indexOf("|")));
				reson.setTextSize(10);
				tableRow.addView(reson);
				prom=strok.indexOf("|")+1;
				reson1 = new TextView(this);
				reson1.setLayoutParams(t2);
				reson1.setText(strok.substring(prom, strok.indexOf("|", prom)));
				tableRow.addView(reson1);
				prom=strok.indexOf("|", prom)+1;
				reson2 = new TextView(this);
				reson2.setLayoutParams(t3);
				reson2.setText(strok.substring(prom, strok.indexOf("|", prom)));
				tableRow.addView(reson2);
				prom=strok.indexOf("|", prom)+1;
				reson3 = new TextView(this);
				reson3.setLayoutParams(t4);
				reson3.setText(strok.substring(prom, strok.indexOf("|", prom)));
				tableRow.addView(reson3);
				prom=strok.indexOf("|", prom)+1;
				reson4 = new TextView(this);
				reson4.setLayoutParams(t5);
				reson4.setGravity(Gravity.CENTER_HORIZONTAL);
				reson4.setText(strok.substring(prom, strok.indexOf("|", prom)));
				tableRow.addView(reson4);
				prom=strok.indexOf("|", prom)+1;
				reson5 = new TextView(this);
				reson5.setLayoutParams(t6);
				reson5.setGravity(Gravity.CENTER_HORIZONTAL);
				reson5.setText(strok.substring(prom, strok.indexOf("|", prom)));
				tableRow.addView(reson5);
				prom=strok.indexOf("|", prom)+1;
				reson6 = new TextView(this);
				reson6.setLayoutParams(t7);
				reson6.setGravity(Gravity.CENTER_HORIZONTAL);
				reson6.setText(strok.substring(prom, strok.indexOf("|", prom)));
				tableRow.addView(reson6);
				prom=strok.indexOf("|", prom)+1;
				reson7 = new TextView(this);
				reson7.setLayoutParams(t8);
				reson7.setText(strok.substring(prom, strok.indexOf("<", prom)));
				tableRow.addView(reson7);
				reson8 = new TextView(this);
				reson8.setLayoutParams(t9);
				reson8.setText(" ");
				tableRow.setBackgroundColor(Color.rgb(40, 40, 40));
				prom=0;
				tableRow1.addView(reson8);
				tableRow1.setBackgroundColor(Color.rgb(0, 0, 0));
				tableLayout.addView(tableRow, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				tableLayout.addView(tableRow1, t4);
				}}}
	} else {
		Toast toast = Toast.makeText(moiotch.this, "Проблемы с интернетом", Toast.LENGTH_SHORT); 
		toast.show();
		rez="";
	}}
}