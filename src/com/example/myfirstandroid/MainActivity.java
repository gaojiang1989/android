package com.example.myfirstandroid;

import com.example.tool.CommonTool;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button mybut = null;
	private Button mybtnSet = null;
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mybut = (Button)super.findViewById(R.id.mybut1);
        this.mybut.setOnClickListener(new OnClickListenerImpl());
        
        this.mybtnSet = (Button)super.findViewById(R.id.btnset);
        this.mybtnSet.setOnClickListener(new OnParamSetClick());
    }
    
    private class OnParamSetClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this,ParamSet.class);
			MainActivity.this.startActivity(intent);
		}
    	
    }
    
    
    
    private class OnClickListenerImpl implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
//			Boolean isFirstIn = false;
			SharedPreferences share = MainActivity.this.getSharedPreferences("gj_rmm",Activity.MODE_PRIVATE);
			String version = share.getString("curVersion","");
//			boolean isFirstIn = share.getBoolean("isFirstIn",true);
			if(!version.equals(CommonTool.curVersion)){     //如果是第一次进来，则需要进行注册。修改数据库结构后还需要重新读写数据库
				Intent it = new Intent(MainActivity.this,RegisterView.class);
				MainActivity.this.startActivity(it);
			}else{          //如果不是第一次进来，就直接进行
				Intent it = new Intent(MainActivity.this,Menu1.class);
				MainActivity.this.startActivity(it);
			}
			
			
		}
    	
    }
    



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    

}
