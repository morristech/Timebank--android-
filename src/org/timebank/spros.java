package org.timebank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class spros extends Activity {
	TableLayout tableLayout;
	TableRow tableRow;
	TableRow tableRow1;
	TextView reson;
	TextView reson1;
	TextView reson2;
	TextView reson3;
	TextView reson4;
	TextView reson5;
	ProgressDialog dialog;
	Spinner spin;
	private List ter;
	private Integer col;
	public String rez, rez1;
	private String numb, site, strok, sel;
	private Boolean nt=false;
	final List<SpinnerObject> list = new ArrayList<SpinnerObject>();
@Override
public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.spros);
		Bundle extras = getIntent().getExtras();
		rez = extras.getString("rez");
		numb = extras.getString("numb");
		sel = extras.getString("sel", "");
		ter = new ArrayList();
		spin = (Spinner)findViewById(R.id.spinner1);
		strok=""; nt=false;
		strok=rez.substring(1, rez.indexOf("<"));
		int rt=0; String th="";
		while (true) {
			if (strok.indexOf("|", rt)==-1) {
				th=strok.substring(rt, strok.length());
				list.add(new SpinnerObject (Integer.parseInt(th.substring(0, th.indexOf(","))), th.substring(th.indexOf(",")+1, th.length()))); 
				break;}
				th=strok.substring(rt, strok.indexOf("|", rt));
				list.add(new SpinnerObject (Integer.parseInt(th.substring(0, th.indexOf(","))), th.substring(th.indexOf(",")+1, th.length())));
				rt=strok.indexOf("|", rt)+1;
			}
			ArrayAdapter<SpinnerObject> adapter = new ArrayAdapter<SpinnerObject>(spros.this, android.R.layout.simple_spinner_dropdown_item, list);
			spin.setAdapter(adapter);
			if (sel!="") {
				spin.setSelection(Integer.parseInt(sel));
			}
		rez1=rez;
		rez=rez.substring(rez.indexOf("<")+1, rez.length());
		loadd(0, 0);
		spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected (AdapterView<?> parent,
					View itemSelected, int selectedItemPosition, long selectedId) {
				if (nt==true) {loadd (1, list.get(selectedItemPosition).getId());} else {
				nt=true;}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
    }
public void onBackPressed()
{
Intent mIntent1 = new Intent();
mIntent1.setClass(spros.this, two.class);
mIntent1.putExtra(two.EXT_COLS, numb);
startActivity(mIntent1);
finish ();
}

private class MyThread10 extends Thread {
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
	loadd (1, list.get(spin.getSelectedItemPosition()).getId());
}

public class SpinnerObject {

    private int databaseId;
    private String databaseValue;

    public SpinnerObject ( int databaseId , String databaseValue ) {
        this.databaseId = databaseId;
        this.databaseValue = databaseValue;
    }

    public int getId () {
        return databaseId;
    }

    public String getValue () {
        return databaseValue;
    }

    @Override
    public String toString () {
        return databaseValue;
    }

}

public void loadd (final int j, int g)  {
	if (j==1) {
		tableLayout=(TableLayout)findViewById(R.id.maintable);
		tableLayout.removeAllViews();
		rez="";
		ter.clear();
		ter.add(new BasicNameValuePair("view", "mobile"));
	    ter.add(new BasicNameValuePair("layout", "mobile"));
	    ter.add(new BasicNameValuePair("no_html", "1"));
	    ter.add(new BasicNameValuePair("idx", "2"));
	    ter.add(new BasicNameValuePair("group", Integer.toString(g)));
	    site = "http://timebankspb.ru/component/";
	    new MyThread10().start();
		while (rez=="") {}
	}
	if (rez.charAt(0)=='>') {
		if (j==1) {
		rez1=rez;
		rez=rez.substring(rez.indexOf("<")+1, rez.length());	
		Toast toast = Toast.makeText(spros.this, "Обновлено успешно", Toast.LENGTH_SHORT); 
		toast.show();}
	tableLayout=(TableLayout)findViewById(R.id.maintable);
	boolean prov = false; int st=0; strok = ""; int prom=0; final String i1="";
	LayoutParams t1 = new LayoutParams(
            145,
            LayoutParams.WRAP_CONTENT, 1.0f);
	t1.setMargins(3, 0, 0, 0);
	LayoutParams t2 = new LayoutParams(
            230,
            LayoutParams.WRAP_CONTENT, 1.0f);
	t2.setMargins(3, 0, 0, 0);
	LayoutParams t3 = new LayoutParams(
            260,
            LayoutParams.WRAP_CONTENT, 1.0f);
	t3.setMargins(3, 0, 0, 0);
	LayoutParams t4 = new LayoutParams(
            185,
            LayoutParams.WRAP_CONTENT, 1.0f);
	t4.setMargins(3, 0, 0, 0);
	LayoutParams t5 = new LayoutParams(
            245,
            LayoutParams.WRAP_CONTENT, 1.0f);
	t5.setMargins(3, 0, 0, 0);
	LayoutParams t6 = new LayoutParams(
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
				tableRow.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	rez=null;
                	dialog = ProgressDialog.show (spros.this, "Загрузка", "Получение данных...");
                	site = "http://timebankspb.ru/component/";
                	ter.clear();
                	ter.add(new BasicNameValuePair("view", "mobile"));
                    ter.add(new BasicNameValuePair("layout", "mobile"));
                    ter.add(new BasicNameValuePair("no_html", "1"));
                    ter.add(new BasicNameValuePair("idx", "5"));
                    ter.add(new BasicNameValuePair("no", Integer.toString(v.getId())));
                	new MyThread10().start();
                	while (rez==null) {}
                	if (rez.charAt(0)=='>') {
                	Intent mIntent = new Intent();
                    mIntent.setClass(spros.this, obj.class);
                	mIntent.putExtra("rez", rez);
                	mIntent.putExtra("rez1", rez1);
                	mIntent.putExtra("numb", numb);
                	mIntent.putExtra("sel", Integer.toString(spin.getSelectedItemPosition()));
                	startActivity(mIntent);
                	finish ();} else {Toast toast = Toast.makeText(spros.this, "Проблемы с интернетом", Toast.LENGTH_SHORT); 
                	toast.show();
                	dialog.dismiss();}
                    }});
				tableRow.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View v, MotionEvent event) {
						if(event.getAction() == MotionEvent.ACTION_DOWN){
							col=((ColorDrawable)v.getBackground()).getColor();
							v.setBackgroundColor(Color.rgb(47, 49, 118));
		                }
						if(event.getAction() == MotionEvent.ACTION_UP){
							v.setBackgroundColor(col);
		                }
						if(event.getAction() == MotionEvent.ACTION_CANCEL){
							v.setBackgroundColor(col);
		                }
						return false;
					}
				});
				strok=rez.substring(st, (i+1));
				reson = new TextView(this);
				reson.setLayoutParams(t1);
				reson.setText(strok.substring(1, strok.indexOf("|")));
				tableRow.setId(Integer.parseInt(strok.substring(1, strok.indexOf("|"))));
				reson.setTextSize(10);
				tableRow.addView(reson);
				prom=strok.indexOf("|")+1;
				if (Integer.parseInt(strok.substring(prom, strok.indexOf("|", prom)))==2) {tableRow.setBackgroundColor(Color.rgb(40, 70, 40));} 
				else if (Integer.parseInt(strok.substring(prom, strok.indexOf("|", prom)))==1) {tableRow.setBackgroundColor(Color.rgb(70, 70, 40));}
				else 
				{tableRow.setBackgroundColor(Color.rgb(40, 40, 40));}
				prom=strok.indexOf("|", prom)+1;
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
				reson3.setGravity(Gravity.CENTER_HORIZONTAL);
				reson3.setText(strok.substring(prom, strok.indexOf("|", prom)));
				tableRow.addView(reson3);
				prom=strok.indexOf("|", prom)+1;
				reson4 = new TextView(this);
				reson4.setLayoutParams(t5);
				reson4.setText(strok.substring(prom, strok.indexOf("<", prom)));
				tableRow.addView(reson4);
				reson5 = new TextView(this);
				reson5.setLayoutParams(t6);
				reson5.setText(" ");
				prom=0;
				tableRow1.addView(reson5);
				tableRow1.setBackgroundColor(Color.rgb(0, 0, 0));
				tableLayout.addView(tableRow, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				tableLayout.addView(tableRow1, t4);
				}}}
	} else {
		Toast toast = Toast.makeText(spros.this, "Проблемы с интернетом", Toast.LENGTH_SHORT); 
		toast.show();
		rez="";
	}}
}
