package org.timebank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
public class TimebankActivity extends Activity {
	private static ProgressDialog dialog;
	private static String rez;
	private static Boolean auto=false;
	public static Handler handler;
	private Thread thread1;
	private String log, pas;
	private CheckBox checkb;
	private EditText edit;
	static HttpClient client = new DefaultHttpClient();
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		auto=false;
		// Create a handler to update the UI
		handler = new Handler();
		// Check if the thread is already running
		thread1 = (Thread) getLastNonConfigurationInstance();
		if (thread1 != null && thread1.isAlive()) {
		dialog = ProgressDialog.show(this, "Проверка", "Вход...");
		}
		SharedPreferences settings = getApplicationContext().getSharedPreferences("timebank", 0);
		log = settings.getString("login", "");
		edit=(EditText)findViewById(R.id.editText1);
		edit.setText(log);
		pas = settings.getString("pas", "");
		edit=(EditText)findViewById(R.id.editText2);
		edit.setText(pas);
		
		checkb=(CheckBox)findViewById(R.id.checkBox1);
		checkb.setChecked(settings.getBoolean("save", true));
		Bundle extras = getIntent().getExtras();
		try {
			auto = extras.getBoolean("auto", false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ((log!="") && (pas!="") && (auto==false)) {auth(null);}
    }

    public void voss (View view) {
    	Uri uri = Uri.parse("http://timebankspb.ru/component/comprofiler/lostpassword");
    	startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
    
    public void reg (View view) {
    	Uri uri = Uri.parse("http://timebankspb.ru/index.php/component/comprofiler/registers");
    	startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
    
	public void auth (View view) {
		boolean prov = false;
		try {
			prov=thread1.isAlive();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (prov==false) {
		dialog = ProgressDialog.show(this, "Проверка", "Вход...");
		checkb=(CheckBox)findViewById(R.id.checkBox1);
		SharedPreferences settings = getApplicationContext().getSharedPreferences("timebank", 0);
		SharedPreferences.Editor editor = settings.edit();
		if (checkb.isChecked()) {
			edit=(EditText)findViewById(R.id.editText1);
			log=edit.getText().toString();
			edit=(EditText)findViewById(R.id.editText2);
			pas=edit.getText().toString();
			editor.putString("login", log);
			editor.putString("pas", pas);
			editor.putBoolean("save", true);
			editor.commit();
		} else {
			editor.putString("login", "");
			editor.putString("pas", "");
			editor.putBoolean("save", false);
			editor.commit();
		}
		thread1 = new MyThread();
		thread1.start();
	}}
	public void onBackPressed()
	{
	finish();
	}
	@Override
	protected void onDestroy() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			dialog = null;
		}
		super.onDestroy();
	}

	public static String postrec (String urlsite, List pairs) throws IOException {
        String result = new String("");
        try{
            HttpPost post =  new HttpPost(urlsite);
          // если используется прокси //
   int portOfProxy = android.net.Proxy.getDefaultPort();
          if(portOfProxy > 0){
			HttpHost proxy = new HttpHost(android.net.Proxy.getDefaultHost(), portOfProxy );
             client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);      
          }
          post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
          HttpResponse response = client.execute(post);
          BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
   StringBuilder sb = new StringBuilder();
   String line = null;
   while ((line = reader.readLine()) != null) {
   sb.append(line + System.getProperty("line.separator"));
   }    
          result = sb.toString();
          } catch (org.apache.http.client.ClientProtocolException e) {
                  // TODO Auto-generated catch block
                  result = "ClientProtocolException: " + e.getMessage();
          } catch (IOException e) {
                  // TODO Auto-generated catch block
                  result = "IOException: " + e.getMessage();
          } catch (Exception e) {
                  // TODO Auto-generated catch block
                  result = "Exception: " + e.toString();
          }
        return result;
	}

	 public class MyThread extends Thread {
		@Override
		public void run() {
			try {
				List pairs = new ArrayList();
				  edit=(EditText)findViewById(R.id.editText1);
		          pairs.add(new BasicNameValuePair("username", edit.getText().toString()));
		          edit=(EditText)findViewById(R.id.editText2);
		          pairs.add(new BasicNameValuePair("passwd", edit.getText().toString()));
		          pairs.add(new BasicNameValuePair("op2", "login"));
		          pairs.add(new BasicNameValuePair("iop", "45ter54"));
				rez = postrec("http://timebankspb.ru/component/comprofiler/login", pairs);
				handler.post(new MyRunnable());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

			}
		}
	}

 public class MyRunnable implements Runnable {
		public void run() {
			if (rez.indexOf("qqw234")!=-1) {
			Intent mIntent = new Intent();
			mIntent.setClass( TimebankActivity.this, two.class);
			mIntent.putExtra(two.EXT_COLS, rez);
			startActivity(mIntent);
			finish ();
			dialog.dismiss();
			} else {
			dialog.dismiss();
			if (rez.indexOf ("Exception")!=-1) {
				Toast toast = Toast.makeText(getApplicationContext(), "Проблемы с интернетом", Toast.LENGTH_SHORT); 
				toast.show();
			} else {
			Toast toast = Toast.makeText(getApplicationContext(), "Логин или пароль не правильный", Toast.LENGTH_SHORT); 
			toast.show();
			}}
		}
	}
}