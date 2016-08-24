package com.example.myfirstandroid;

import java.util.ArrayList;
import java.util.List;

import com.example.db.DataBaseHelper;
import com.example.model.Area;
import com.example.model.Example;
import com.example.model.Grammar;
import com.example.model.ItemClass;
import com.example.tool.CommonTool;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class GrammarView extends Activity {
	TableLayout layout;
	
	private SQLiteOpenHelper database = null;
	private SQLiteDatabase db = null;
//	private int groupId;
	private int lesId;           //当前的课文ID
	private Spinner sp;

	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		
		database = new DataBaseHelper(this);
		db = database.getReadableDatabase();
		
		ScrollView sv = new ScrollView(this);
		
		
		layout = new TableLayout(this);
		
		TableLayout.LayoutParams layoutParam = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		
		
		createSpinner();
		
		initSpinnerPosition();
		
		sv.addView(layout);
		super.setContentView(sv, layoutParam);
	}
	
	/**
	 * 初始化下拉框的选项
	 */
	private void initSpinnerPosition(){
		int lessonId = CommonTool.getCurLesson(this,"grammarLessonId");
		for(int i=0;i<sp.getAdapter().getCount();i++){
			ItemClass ic = (ItemClass)sp.getAdapter().getItem(i);
			if(ic.getId()==lessonId){
				sp.setSelection(i);
			}
		}
	}
	
	/**
	 * 创建课程选择的下拉框
	 */
	private void createSpinner() {
		LinearLayout line = new LinearLayout(this);
//		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		line.setOrientation(LinearLayout.HORIZONTAL);
		
		sp = new Spinner(this);
		sp.setPrompt("300");
//		sp.set

		List<ItemClass> list = getLesson();
		ArrayAdapter<ItemClass> aa = new ArrayAdapter<ItemClass>(this,
				android.R.layout.simple_spinner_item, list);
		sp.setAdapter(aa);
		sp.setOnItemSelectedListener(new OnClassSelect());
		line.addView(sp);
		
		Button btnLast = new Button(this);
		btnLast.setText("上一课");
		btnLast.setOnClickListener(new LastOnClick());
		line.addView(btnLast);
		
		Button btnNext = new Button(this);
		btnNext.setText("下一课");
		btnNext.setOnClickListener(new NextOnClick());
		line.addView(btnNext);
		
		layout.addView(line);
	}
	
	private class NextOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(lesId==getLesson().get(getLesson().size()-1).getClassNo()){   //如果当前已经是最后一课了，则提示
				Toast.makeText(GrammarView.this, "当前已经是最后一课！",Toast.LENGTH_LONG).show();
			}else{
				sp.setSelection(sp.getSelectedItemPosition()+1);
			}
		}
		
	}
	
	private class LastOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (lesId == getLesson().get(0).getClassNo()) {   //如果当前已经是第一课了，则提示
				Toast.makeText(GrammarView.this, "当前已经是第一课！",Toast.LENGTH_LONG).show();
			} else {
				sp.setSelection(sp.getSelectedItemPosition()-1);
			}
		}
		
	}
	
	private class OnClassSelect implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int position, long id) {

			ItemClass value = (ItemClass) arg0.getItemAtPosition(position);
			int lessonId = value.getId();
			lesId = lessonId;
			CommonTool.setCurLesson(GrammarView.this, lessonId,"grammarLessonId");
			initGrammar(lessonId);
		}



		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}
	
	private void initGrammar(int lessonId){
		
		int count = layout.getChildCount();
		for (int i = 0; i < count - 1; i++) {
			layout.removeView(layout.getChildAt(1));
		}
		
		
		Grammar gram = getGrammar(lessonId);
		//添加课文标题
		addTextView("第"+lesId+"课",1);
		//添加语法
		for(Area a:gram.getList()){
			addTextView(a.getTitle());
			addTextView(a.getContext());
			List<Example> le = a.getListExample();
			for(Example emp:le){
				addTextView(emp.getJptext());
				addTextView("  "+emp.getChtext());
			}
			addTextView("    ");
		}
	}
	
	private void addTextView(String msg){
		addTextView(msg, 0);
	}
	private void addTextView(String msg,int mode){
		TableRow row = new TableRow(this);
		TextView tv = new TextView(this);
		tv.setPadding(10,10,10,10);
		if(mode==0){
			tv.setGravity(Gravity.LEFT);
		}else if(mode==1){
			tv.setGravity(Gravity.CENTER);
		}else if(mode==2){
			tv.setGravity(Gravity.RIGHT);
		}
		tv.setWidth(getScreenWidth());
//		tv.setTextSize(20);
		tv.setText(msg);
		row.addView(tv);
		layout.addView(row);
	}
	private int getScreenWidth(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	
	
	private Grammar getGrammar(int lesssonId){
		Grammar gram = new Grammar();
		gram.setLesssonId(lesssonId);
		gram.setTextbook(CommonTool.getTextbook(this));
		List<Area> list = new ArrayList<Area>();
		String sql = "SELECT * FROM gj_grammar where lessonid="
				+ lesssonId + " and textbook=" + CommonTool.getTextbook(this);
		Cursor c = db.rawQuery(sql,null);
		System.out.println("sql:"+sql);
		while(c.moveToNext()){
			int id =  c.getInt(c.getColumnIndex("id"));
			int seqNo =  c.getInt(c.getColumnIndex("seqNo"));
			String title = c.getString(c.getColumnIndex("title"));
			String context = c.getString(c.getColumnIndex("context"));
//			System.out.println("数据库："+context+"\ttitle:"+title);
			context = context.split("\n")[0];
//			String[]ts = title
			Area a = new Area(id, seqNo, title, context);
			list.add(a);
		}
		
		for(Area a:list){
			List<Example> le = new ArrayList<Example>();
			c = db.rawQuery("select * from gj_gram_example where aid="+a.getId(),null);
			while(c.moveToNext()){
				int id =  c.getInt(c.getColumnIndex("id"));
				String jptext = c.getString(c.getColumnIndex("jptext"));
				String chtext = c.getString(c.getColumnIndex("chtext"));
				String desc = c.getString(c.getColumnIndex("desc"));
				Example emp = new Example(id, a.getId(), jptext, chtext, desc);
				le.add(emp);
			}
			a.setListExample(le);
		}
		gram.setList(list);
		return gram;
	}
	
	public List<ItemClass> getLesson() {
		List<ItemClass> list = new ArrayList<ItemClass>();

		Cursor c = db.rawQuery("SELECT * FROM gj_lesson where textbook="
				+ CommonTool.getTextbook(this), null);
		while (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex("id"));
			int classNo = c.getInt(c.getColumnIndex("lesson"));
			int textbook = c.getInt(c.getColumnIndex("textbook"));
			String desc = c.getString(c.getColumnIndex("desc"));
			String className = "第" + classNo + "课";
			ItemClass ic = new ItemClass(id,classNo, className, textbook,desc);
			ic.setDesc(desc);
			list.add(ic);
		}
		c.close();
		return list;
	}

}
