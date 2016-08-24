package com.example.myfirstandroid;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.tool.CommonTool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterView extends Activity {
	
	private Button btnRegister;
	private Spinner spTextbook;
	private EditText edtRegisterNum;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        
        btnRegister = (Button)super.findViewById(R.id.btnRegister);
        spTextbook = (Spinner)super.findViewById(R.id.spTextbook);
        edtRegisterNum = (EditText)super.findViewById(R.id.edtRegister);
        
        btnRegister.setOnClickListener(new RegisterOnClick());
    }
    
    private class RegisterOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String text = edtRegisterNum.getText().toString();
			int textbook = spTextbook.getSelectedItemPosition()+1;
			if(text.equals("123")){
				saveData(textbook);
				initDb();
				Intent it = new Intent(RegisterView.this,Menu1.class);
				RegisterView.this.startActivity(it);
				Toast.makeText(RegisterView.this, "ﾗ｢ｲ盖ﾉｹｦ｣｡",Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(RegisterView.this, "ﾗ｢ｲ睫ｧｰﾜ｣ｬﾗ｢ｲ眥�ｴ﨔｡",Toast.LENGTH_LONG).show();
			}
		}
    	
    }
    
    /**
     * ｱ｣ｴ贊ﾃｻｧﾑ｡ﾔﾄｿﾎｳﾌ｣ｺｳｶｻﾟﾊﾇﾖﾐｼｶ
     * @param textbook
     */
    private void saveData(int textbook){
    	SharedPreferences share = super.getSharedPreferences("gj_rmm",Activity.MODE_PRIVATE);
    	SharedPreferences.Editor edit = share.edit();
    	edit.putInt("textbook",textbook);
    	edit.putString("curVersion",CommonTool.curVersion);
//    	edit.putBoolean("isFirstIn",false);
    	edit.commit();
    }
    
    @SuppressLint("SdCardPath")
	public void initDb(){
    	String DB_PATH = "/data/data/com.example.myfirstandroid/databases/";
    	String DB_NAME = "jp.db";
    	File f = new File(DB_PATH);
		if(!f.exists()){
			f.mkdir();
		}
		try{
			InputStream is = getBaseContext().getAssets().open(DB_NAME);
			OutputStream os = new FileOutputStream(DB_PATH+DB_NAME);
			byte[]buffer = new byte[1024];
			int length;
			while((length=is.read(buffer))>0){
				os.write(buffer,0,length);
			}
			
			os.flush();
			os.close();
			is.close();
		}catch(Exception e){
			e.printStackTrace();
		}
    	
    }
}
