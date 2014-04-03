package org.timebank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Application;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


public class two extends Activity {
ProgressDialog dialog;
public static final String EXT_COLS = "numb";
public static final String spros1 = null;
private static final String TAG = null;
public static String rez=null;
public String site;
LocationGpsOnlyService th;
SharedPreferences settings;
SharedPreferences.Editor editor;
public List ter;
private LocationManager locationManager;
private Boolean gps, gpsidle, upp=false;
private static String numb;
@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.two);
	TextView txt=(TextView)findViewById(R.id.textView2);
	TextView txt1=(TextView)findViewById(R.id.textView3);
	Bundle extras = getIntent().getExtras();
	settings = getApplicationContext().getSharedPreferences("timebank", 0);
	editor = settings.edit();
	if (th==null) {th = new LocationGpsOnlyService(getApplication());}
	gps = settings.getBoolean("gps", false);
	gpsidle = settings.getBoolean("gpsidl", false);
	if ((gps==true)) {th.enableLocationListening();} else {th.disableLocationListening();}
	numb = extras.getString(EXT_COLS);
	txt.setText("Мой счёт: "+ numb.substring(0, numb.indexOf("|")));
	txt1.setText("Кредит: "+ numb.substring(numb.indexOf("|qqw234|")+8, numb.length()));
	ter = new ArrayList();
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.gen_menu, menu);
    if (gps==true) {
    	menu.findItem(R.id.item1).setChecked(true);} 
    else {
    		menu.findItem(R.id.item1).setChecked(false);
    	}
    return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	if (item.getItemId()==R.id.item1) {
		if (item.isChecked()==true) {
			item.setChecked(false);
			editor.putBoolean ("gps", false);
			editor.commit();
			th.disableLocationListening();
			site="http://timebankspb.ru/component/?view=mobile&layout=mobile&no_html=1&idx=8";
			ter.clear();
			new MyThread().start();
		} else {item.setChecked(true);
		editor.putBoolean ("gps", true);
		editor.commit();
		th.enableLocationListening();
		}
	
	}
	if (item.getItemId()==R.id.item2) {
		opt (null);
	}
	return true;
}

public void onBackPressed()
{
logout (1);
}

public void logout0 (View view) {
logout (0);
}

public void logout (int g) {
	site="http://timebankspb.ru/component/comprofiler/logout";
	ter.clear();
	new MyThread().start();
	th.disableLocationListening();
if (g==1) {
	site="http://timebankspb.ru/component/?view=mobile&layout=mobile&no_html=1&idx=8";
	ter.clear();
	new MyThread().start();
Intent mIntent1 = new Intent();
mIntent1.setClass(two.this, TimebankActivity.class);
mIntent1.putExtra("auto", true);
startActivity(mIntent1);
finish ();} else {
	Intent mIntent1 = new Intent();
	mIntent1.setClass(two.this, TimebankActivity.class);
	mIntent1.putExtra("auto", false);
	finish ();}
}

@Override
protected void onDestroy() {
	if (dialog != null && dialog.isShowing()) {
		dialog.dismiss();
		dialog = null;
	}
	super.onDestroy();
}

public class MyThread extends Thread {

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

public void spros(View view) 
{	
	rez=null;
	dialog = ProgressDialog.show (this, "Загрузка", "Получение данных...");
	site="http://timebankspb.ru/component/";
	ter.clear();
	ter.add(new BasicNameValuePair("view", "mobile"));
    ter.add(new BasicNameValuePair("layout", "mobile"));
    ter.add(new BasicNameValuePair("no_html", "1"));
    ter.add(new BasicNameValuePair("idx", "2"));
	new MyThread().start();
while (rez==null) {}
if (rez.charAt(0)=='>') {
	Intent mIntent = new Intent();
	mIntent.setClass( two.this, spros.class);
	mIntent.putExtra("rez", rez);
	mIntent.putExtra("numb", numb);
	startActivity(mIntent);
	finish ();} else {Toast toast = Toast.makeText(two.this, "Проблемы с интернетом", Toast.LENGTH_SHORT); 
	toast.show();
	dialog.dismiss();}
}
public void predl(View view) 
{
	rez=null;
	dialog = ProgressDialog.show (this, "Загрузка", "Получение данных...");
	site="http://timebankspb.ru/component/";
	ter.clear();
	ter.add(new BasicNameValuePair("view", "mobile"));
    ter.add(new BasicNameValuePair("layout", "mobile"));
    ter.add(new BasicNameValuePair("no_html", "1"));
    ter.add(new BasicNameValuePair("idx", "3"));
    new MyThread().start();
while (rez==null) {}
if (rez.charAt(0)=='>') {
	Intent mIntent = new Intent();
	mIntent.setClass( two.this, predl.class);
mIntent.putExtra("rez", rez);
mIntent.putExtra("numb", numb);
	startActivity(mIntent);
	finish ();} else {
	Toast toast = Toast.makeText(two.this, "Проблемы с интернетом", Toast.LENGTH_SHORT); 
	toast.show();
	dialog.dismiss();}
}
public void soob(View view) 
{
Toast toast = Toast.makeText(getApplicationContext(), "В разработке...", Toast.LENGTH_SHORT); 
toast.show();
}

public class LocationGpsOnlyService implements LocationListener {

private static final int BEST_PROVIDER_SHCEDULE = 5000;
private static final int BEST_PROVIDER_SHCEDULE1 = 10000;

private Location gpsLocation;

private Location firstLocation;

private Context context;

private TimerTask locationCheckingTask;

private Timer t = new Timer();

//long updatedGpsTime = System.currentTimeMillis();
long upd = System.currentTimeMillis();
long gt;
double old_lat = 0;
double old_long = 0;
public LocationGpsOnlyService(Application application)
{ context = application.getApplicationContext();
gt=upd;
locationManager = (LocationManager) application.getApplicationContext()
.getSystemService(Context.LOCATION_SERVICE);
}

public void cancelChecking() {
if (locationCheckingTask != null)
{ locationCheckingTask.cancel(); }

}

public void disableLocationListening()
{ locationManager.removeUpdates(this);
cancelChecking();
}

public void enableChecking() {
locationCheckingTask = new TimerTask() {
@Override
public void run() {
if ((gpsLocation != null) && (gpsLocation.getLatitude()!=old_lat) && (gpsLocation.getLongitude()!=old_long))
{
old_lat=gpsLocation.getLatitude();
old_long=gpsLocation.getLongitude();
notifyAboutUpdatedLocation();
}}};
t.schedule(locationCheckingTask, BEST_PROVIDER_SHCEDULE, BEST_PROVIDER_SHCEDULE1);
}

public void enableLocationListening()
{initBestProvider();
enableChecking(); }

public Location getInaccurateLocation()
{ return firstLocation; }

public Location getLatestLocation()
{ return gpsLocation; }

public void onLocationChanged(Location location) {
//updatedGpsTime = System.currentTimeMillis();
gpsLocation = location;
if (firstLocation == null)
{ firstLocation = gpsLocation; }
}

public void onProviderDisabled(String provider) {
if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER))
{ initBestProvider(); }
}

public void onProviderEnabled(String provider) {
if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER))
{ initBestProvider(); }
}

public void onStatusChanged(String provider, int status, Bundle extras)
{ // do nothing 
	
}

private void initBestProvider() {
//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
//0 /* m */, this);
locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, Looper.getMainLooper());
if (firstLocation == null) {
gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
firstLocation = gpsLocation;
}
if (gpsLocation!=null) {
old_lat=gpsLocation.getLatitude();
old_long=gpsLocation.getLongitude();
new MyThread34().start();}
}

public class MyThread34 extends Thread {
	@Override
	public void run() {
		notifyAboutUpdatedLocation();
	}
}

private void notifyAboutUpdatedLocation()
{	
	if (gpsidle==true) {
		if (upp==false) {
			
				List ter1;
				ter1= new ArrayList();
				site="http://timebankspb.ru/component/";
				ter1.clear();
				ter1.add(new BasicNameValuePair("view", "mobile"));
			    ter1.add(new BasicNameValuePair("layout", "mobile"));
			    ter1.add(new BasicNameValuePair("no_html", "1"));
			    ter1.add(new BasicNameValuePair("idx", "7"));
			    ter1.add(new BasicNameValuePair("lat", Double.toString(gpsLocation.getLatitude())));
			    ter1.add(new BasicNameValuePair("lon", Double.toString(gpsLocation.getLongitude())));
			    try {
					rez=TimebankActivity.postrec(site, ter1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			locationManager.removeUpdates(this);
			upp=true;
		} else {
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
						0 /* m */, this);
				upp=false;
		}
	} else {
	List ter1;
	ter1= new ArrayList();
	site="http://timebankspb.ru/component/";
	ter1.clear();
	ter1.add(new BasicNameValuePair("view", "mobile"));
    ter1.add(new BasicNameValuePair("layout", "mobile"));
    ter1.add(new BasicNameValuePair("no_html", "1"));
    ter1.add(new BasicNameValuePair("idx", "7"));
    ter1.add(new BasicNameValuePair("lat", Double.toString(gpsLocation.getLatitude())));
    ter1.add(new BasicNameValuePair("lon", Double.toString(gpsLocation.getLongitude())));
    try {
		rez=TimebankActivity.postrec(site, ter1);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}}
}

public void all(View view) 
{
	rez=null;
	dialog = ProgressDialog.show (this, "Загрузка", "Получение данных...");
	site="http://timebankspb.ru/component/";
	ter.clear();
	ter.add(new BasicNameValuePair("view", "mobile"));
    ter.add(new BasicNameValuePair("layout", "mobile"));
    ter.add(new BasicNameValuePair("no_html", "1"));
    ter.add(new BasicNameValuePair("idx", "4"));
    new MyThread().start();
while (rez==null) {}
if (rez.charAt(0)=='>') {
	Intent mIntent = new Intent();
	mIntent.setClass( two.this, all.class);
	mIntent.putExtra(all.ext, rez);
	mIntent.putExtra(all.prex, numb);
	startActivity(mIntent);
	finish ();} else {
	Toast toast = Toast.makeText(two.this, "Проблемы с интернетом", Toast.LENGTH_SHORT); 
	toast.show();
	dialog.dismiss();}
}

public void opt(View view) 
{
	Intent mIntent = new Intent();
	mIntent.setClass(two.this, options.class);
	mIntent.putExtra("numb", numb);
	startActivity(mIntent);
	finish ();
}

public void moiotch(View view) 
{
	rez=null;
	dialog = ProgressDialog.show (this, "Загрузка", "Получение данных...");
	Intent mIntent = new Intent();
	mIntent.setClass( two.this, moiotch.class);
	site="http://timebankspb.ru/";
	ter.clear();
	ter.add(new BasicNameValuePair("option", "com_tranz"));		
	ter.add(new BasicNameValuePair("view", "first"));
    ter.add(new BasicNameValuePair("layout", "first"));
    ter.add(new BasicNameValuePair("no_html", "1"));
    new MyThread().start();
while (rez==null) {}
rez=rez.trim();
if (rez.charAt(0)=='>') {
	mIntent.putExtra(moiotch.ext, rez);
	mIntent.putExtra(moiotch.prex, numb);
	startActivity(mIntent);
	finish ();} else {
		Toast toast = Toast.makeText(two.this, "Проблемы с интернетом", Toast.LENGTH_SHORT); 
		toast.show();
		dialog.dismiss();}
}
}