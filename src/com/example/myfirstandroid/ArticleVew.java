package com.example.myfirstandroid;

import java.util.ArrayList;
import java.util.List;

import com.example.db.DataBaseHelper;
import com.example.model.Article;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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

public class ArticleVew extends Activity {
	TableLayout layout;
	
	private SQLiteOpenHelper database = null;
	private SQLiteDatabase db = null;
	private int lesId;           //当前的课文ID
	private Spinner sp;

	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		
		database = new DataBaseHelper(this);
		db = database.getReadableDatabase();
		
		ScrollView sv = new ScrollView(this);
		
		
		layout = new TableLayout(this);
		
//		Button btnAddGroup = new Button(this);
//		btnAddGroup.setText("添加分组");
//		btnAddGroup.setOnClickListener(new AddGroupOnClick());
//		layout.addView(btnAddGroup);
		
//		initLayout();
		
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
		int lessonId = CommonTool.getCurLesson(this,"articleLessonId");
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
				Toast.makeText(ArticleVew.this, "当前已经是最后一课！",Toast.LENGTH_LONG).show();
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
				Toast.makeText(ArticleVew.this, "当前已经是第一课！",Toast.LENGTH_LONG).show();
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
			CommonTool.setCurLesson(ArticleVew.this, lessonId,"articleLessonId");
			initArticle(lessonId);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}
	
	private void initArticle(int lessonId){
		
		int count = layout.getChildCount();
		for (int i = 0; i < count - 1; i++) {
			layout.removeView(layout.getChildAt(1));
		}
		
		
		List<Article> list = getArticle(lessonId);
		//添加课文标题
		addTextView("第"+lesId+"课",1);
		addTextView("基本课文",1);
		//添加基本课文的句子
		for(Article a:list){
			if(a.getType().equals("基本课文"))
				addTextView(a.getJptext(),0,a);
		}
		//添加应用课文
		addTextView("应用课文",1);
		for(Article a:list){
			if(a.getType().equals("应用课文"))
				addTextView(a.getJptext(),0,a);
		}
	}
	
	private class TextOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			TextView tv = (TextView)arg0;
			if(tv.getTag()!=null){
				Article at = (Article)tv.getTag();
				if(tv.getText().toString().equals(at.getJptext())){
					tv.setText(tv.getText().toString()+"\n"+at.getChtext());
				}else{
					tv.setText(at.getJptext());
				}
			}
			
		}
		
	}
	
	/**
	 * 根据参数添加一个文本显示组件
	 * @param msg  要显示的文本
	 * @param mode 0：左对齐    1：居中对齐    2：右对齐
	 * @param a  要绑定到组件上的对象
	 */
	private void addTextView(String msg,int mode,Article a){
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
		if(a!=null){
			tv.setTag(a);
		}
		tv.setWidth(getScreenWidth());
//		tv.setTextSize(20);
		tv.setText(msg);
		tv.setOnClickListener(new TextOnClick());
		row.addView(tv);
		layout.addView(row);
	}

	/**
	 * 根据参数添加一个文本显示组件
	 * @param msg 需要显示的文本
	 * @param mode 0左对齐    1居中对齐   2右对齐
	 */
	private void addTextView(String msg,int mode){
		addTextView(msg, mode, null);
	}
	
	/**
	 * 获取屏幕宽度
	 * @return
	 */
	private int getScreenWidth(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	
	/**
	 * 根据课文序号，查询当前课文的所有句子
	 * @param lessonid  课程ID
	 * @return
	 */
	private List<Article> getArticle(int lessonid){
		List<Article> list = new ArrayList<Article>();
		String sql = "select * from gj_article where lessonid=" + lessonid
				+ " and textbook=" + CommonTool.getTextbook(this);
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex("id"));
			int textbook = c.getInt(c.getColumnIndex("textbook"));
			String type = c.getString(c.getColumnIndex("type"));
			String jptext = c.getString(c.getColumnIndex("jptext"));
			String chtext = c.getString(c.getColumnIndex("chtext"));
			String desc = c.getString(c.getColumnIndex("desc"));
			Article a = new Article(id, lessonid, type, jptext, chtext, textbook, desc);
			list.add(a);
		}
		return list;
	}
	
	/**
	 * 获取所有课文
	 * @return
	 */
	private List<ItemClass> getLesson() {
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
			list.add(ic);
		}
		return list;
	}
}
