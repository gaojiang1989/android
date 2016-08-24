package com.example.myfirstandroid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.control.TestArrayAdapter;
import com.example.db.DataBaseHelper;
import com.example.model.ItemClass;
import com.example.model.Word;
import com.example.tool.CommonTool;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ChToJp extends Activity {
	TableLayout layout;
	Spinner sp ;
	SQLiteDatabase db = null;
	private int wordId;
	private int lesId;
	
//	private boolean isChToJp = true;
	private int isChToJp = 1;
	private boolean isRandom = false;

	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);

		SQLiteOpenHelper database = new DataBaseHelper(ChToJp.this);

		db = database.getReadableDatabase();

		ScrollView sv = new ScrollView(this);

		layout = new TableLayout(this);
		TableLayout.LayoutParams layoutParam = new TableLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		createSpinner();
		
		initSpinnerPosition();

		sv.addView(layout);
		super.setContentView(sv, layoutParam);
	}
	
	/**
	 * 初始化下拉框的选项
	 */
	private void initSpinnerPosition(){
		int lessonId = CommonTool.getCurLesson(this,"wordLessonId");
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

		List<ItemClass> list = getLesson();
//		ArrayAdapter<ItemClass> aa = new ArrayAdapter<ItemClass>(this,
//				android.R.layout.simple_spinner_item, list);
		
		ArrayAdapter<ItemClass> aa = new TestArrayAdapter(ChToJp.this, list);
		
		
		sp.setAdapter(aa);
		sp.setOnItemSelectedListener(new OnClassSelect());
		line.addView(sp);
		
		Button btnChange = new Button(this);
		if(isChToJp==1)
			btnChange.setText("日译中");
		else if(isChToJp==2)
			btnChange.setText("中译日");
		else if(isChToJp==3)
			btnChange.setText("汉字译日");
		btnChange.setOnClickListener(new JpToChOnClick());
		line.addView(btnChange);
		
		Button btnSeq = new Button(this);
		if(isRandom)
			btnSeq.setText("顺序");
		else
			btnSeq.setText("随机");
		btnSeq.setOnClickListener(new RandomOnClick());
		line.addView(btnSeq);
		
		
		layout.addView(line);
	}
	
	/**
	 * 点击日译中或者中译日按钮后改变当前学习模式
	 * @author epri
	 *
	 */
	private class JpToChOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Button btn = (Button)arg0;
			if(btn.getText().equals("日译中")){    //
				isChToJp = 2;
				btn.setText("中译日");
			}else if(btn.getText().equals("中译日")){
				isChToJp = 3;
				btn.setText("汉字译日");
			}else if(btn.getText().equals("汉字译日")){
				isChToJp = 1;
				btn.setText("日译中");
			}
			initWord(lesId);
		}
		
	}
	
	/**
	 * 点击随机或者顺序记忆的按钮后触发的事件
	 * @author epri
	 *
	 */
	private class RandomOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Button btn = (Button)arg0;
			if(btn.getText().equals("随机")){
				isRandom = true;
				btn.setText("顺序");
			}else{
				isRandom = false;
				btn.setText("随机");
			}
			initWord(lesId);
		}
		
	}
	
	public void onContextMenuClosed(Menu menu){
		curTv.setBackgroundColor(Color.WHITE);
	}

	private class OnClassSelect implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int position, long id) {

			
			ItemClass value = (ItemClass) arg0.getItemAtPosition(position);
//			System.out.println();
			int lessonId = value.getClassNo();
			lesId = lessonId;
			CommonTool.setCurLesson(ChToJp.this, lessonId,"wordLessonId");
			initWord(lessonId);
		}



		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}
	
	/**
	 * 初始化所有单词
	 * @param lessonId
	 */
	private void initWord(int lessonId) {
		int count = layout.getChildCount();
		for (int i = 0; i < count - 1; i++) {
			layout.removeView(layout.getChildAt(1));
		}

		List<Word> list = getWord(lessonId);
		for (int x = 0; x < list.size(); x++) {
			TableRow row = new TableRow(ChToJp.this);
			// 创建显示日语单词的文本组件
			TextView tv = new TextView(ChToJp.this);
			if(isChToJp==1){
				tv.setText(list.get(x).getJpword());
			}else if(isChToJp==2){
				tv.setText(list.get(x).getChword());
			}else if(isChToJp==3){
				tv.setText(list.get(x).getJpch());
			}
			
			tv.setWidth(layout.getWidth() / 3);
			tv.setTag(list.get(x).getId());
			tv.setPadding(10,10,0,10);
			tv.setGravity(Gravity.CENTER_VERTICAL);
			ChToJp.super.registerForContextMenu(tv);
			row.addView(tv, 0);
			// 创建显示按钮
			Button bw = new Button(ChToJp.this);
			bw.setTag(list.get(x).getId());
			bw.setText("查看");
			bw.setWidth(layout.getWidth() * 2 / 3);
			bw.setOnClickListener(new OnBtnSearch());
			row.addView(bw, 1);
			layout.addView(row);
		}
	}
	
	TextView curTv;

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		// String tag = v.getTag().toString();
		curTv = (TextView)v;
		wordId = (Integer) v.getTag();
		// System.out.println("tag:"+tag);
		menu.setHeaderTitle("信息操作");
		menu.add(Menu.NONE, Menu.FIRST + 1, 11, "添加到分组");
		menu.add(Menu.NONE, Menu.FIRST + 2, 22, "详细信息");
		curTv.setBackgroundColor(Color.GRAY);
		// menu.add(Menu.NONE,Menu.FIRST+2,22,"添加单词组");
		// menu.add(Menu.NONE,Menu.FIRST+3,33,"删除单词组");
	}

	public boolean onContextItemSelected(MenuItem item) {
		// ContextMenu.ContextMenuInfo cmi = item.getMenuInfo();
		AddWordToGroupDialog awt = new AddWordToGroupDialog(this,wordId);
		switch (item.getItemId()) {
		case Menu.FIRST + 1:
			// Toast.makeText(this,
			// "您选择的是添加单词组"+item.getGroupId(),Toast.LENGTH_LONG).show();
			awt.showGroupDialog();
			break;
		case Menu.FIRST + 2:
			Intent it = new Intent(ChToJp.this,WordDetail.class);
			it.putExtra("wordId",wordId);
			ChToJp.this.startActivity(it);
			break;
		}
		return false;
	}


	

	private class OnBtnSearch implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			int tag = (Integer) arg0.getTag();
			Word w = getWordByid(tag);
			Button but = (Button) arg0;
			if(but.getText().equals("查看")){
				if(isChToJp==2){
					if (w.getChword().equals(w.getJpch())
							|| w.getJpch().equals(w.getJpword())) {
						but.setText(w.getJpword());
					} else {
						but.setText(w.getJpword() + "(" + w.getJpch() + ")");
					}
				}else if(isChToJp==1){
					if (w.getChword().equals(w.getJpch())
							|| w.getJpch().equals(w.getJpword())) {
						but.setText(w.getChword());
					} else {
						but.setText(w.getChword()+ "(" + w.getJpch() + ")");
					}
				}else if(isChToJp==3){
					but.setText(w.getJpword()+ "(" + w.getChword()+ ")");
				}
			}else{
				but.setText("查看");
			}
			
			

		}

	}



	public List<ItemClass> getLesson() {
		List<ItemClass> list = new ArrayList<ItemClass>();

		Cursor c = db.rawQuery("SELECT * FROM gj_lesson where textbook="+CommonTool.getTextbook(this), null);

		while (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex("id"));
			int classNo = c.getInt(c.getColumnIndex("lesson"));
			int textbook = c.getInt(c.getColumnIndex("textbook"));
			String desc = c.getString(c.getColumnIndex("desc"));
			String className = "第" + classNo + "课";
			ItemClass ic = new ItemClass(id,classNo, className, textbook,desc);
			list.add(ic);
		}
		c.close();
		return list;
	}

	public List<Word> getWord(int lessonid) {
		Set<Word> set = new HashSet<Word>();
		List<Word> list = new ArrayList<Word>();
		Cursor c = db.rawQuery("SELECT * FROM gj_word where textbook="
				+ CommonTool.getTextbook(this) + " and lessonid=" + lessonid, null);
		System.out.println("SELECT * FROM gj_word where textbook="
				+ CommonTool.getTextbook(this) + " and lessonid=" + lessonid);
		while (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex("id"));
			String jpword = c.getString(c.getColumnIndex("jpword"));
			String chword = c.getString(c.getColumnIndex("chword"));
			String jpch = c.getString(c.getColumnIndex("jpch"));
			int level = c.getInt(c.getColumnIndex("level"));
			int errornum = c.getInt(c.getColumnIndex("errornum"));

			String speech = c.getString(c.getColumnIndex("speech"));
			int textbook = c.getInt(c.getColumnIndex("textbook"));
			Word w = new Word(id, jpword, chword, jpch, lessonid, level, errornum,speech,textbook);
			set.add(w);
			list.add(w);
		}
		if(isRandom){
			list = new ArrayList<Word>(set);
			return list;
		}else{
			return list;
		}
	}

	public Word getWordByid(int wordid) {
		SQLiteOpenHelper database = new DataBaseHelper(ChToJp.this);
		SQLiteDatabase db = null;
		db = database.getReadableDatabase();
		Cursor c = db
				.rawQuery("SELECT * FROM gj_word where id=" + wordid, null);
		Word w = null;
		while (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex("id"));
			String jpword = c.getString(c.getColumnIndex("jpword"));
			String chword = c.getString(c.getColumnIndex("chword"));
			String jpch = c.getString(c.getColumnIndex("jpch"));
			int level = c.getInt(c.getColumnIndex("level"));
			int errornum = c.getInt(c.getColumnIndex("errornum"));
			int lessonid = c.getInt(c.getColumnIndex("lessonid"));
			String speech = c.getString(c.getColumnIndex("speech"));
			int textbook = c.getInt(c.getColumnIndex("textbook"));
			w = new Word(id, jpword, chword, jpch, lessonid, level, errornum,speech,textbook);
		}
		c.close();
		return w;
	}
}