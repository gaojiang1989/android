package com.example.myfirstandroid;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.Activity;
import android.content.SharedPreferences;


public class ParamSet extends Activity {

	private Spinner spTextbook;
	private Button btnSure;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paramset);
		
		spTextbook = (Spinner)super.findViewById(R.id.spnClassSet);
		btnSure = (Button)super.findViewById(R.id.btnParamSet);
		btnSure.setOnClickListener(new OnSureClick());
	}
	
	private class OnSureClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			int textbook = spTextbook.getSelectedItemPosition()+1;
			saveData(textbook);
			Toast.makeText(ParamSet.this, "修改完成！",Toast.LENGTH_LONG).show();
		}
		
	}
	
    /**
     * 保存用户选择的课程：初级或者是中级
     * @param textbook
     */
    private void saveData(int textbook){
    	SharedPreferences share = super.getSharedPreferences("gj_rmm",Activity.MODE_PRIVATE);
    	SharedPreferences.Editor edit = share.edit();
    	edit.putInt("textbook",textbook);
//    	edit.putString("curVersion",CommonTool.curVersion);
//    	edit.putBoolean("isFirstIn",false);
    	edit.commit();
    }

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.param_set, menu);
//		return true;
//	}

}
