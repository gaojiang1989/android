package com.example.myfirstandroid;

import java.util.ArrayList;
import java.util.List;

import com.example.db.DataBaseHelper;
import com.example.model.Word;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TranslateWord extends Activity {

	SQLiteDatabase db = null;
	TableLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		SQLiteOpenHelper database = new DataBaseHelper(TranslateWord.this);
		db = database.getReadableDatabase();
		
		setContentView(R.layout.translate);
		layout = (TableLayout)super.findViewById(R.id.translateTableLayout);
		
		Button btnSearch = (Button)super.findViewById(R.id.translateSearch);
		btnSearch.setOnClickListener(new SearchOnClick());
		
	}
	
	
	TextView curTv;
	private int wordId;

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		// String tag = v.getTag().toString();
		curTv = (TextView)v;
		wordId = ((Word)v.getTag()).getId();
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
			Intent it = new Intent(TranslateWord.this,WordDetail.class);
			it.putExtra("wordId",wordId);
			TranslateWord.this.startActivity(it);
			break;
		}
		return false;
	}
	
	public void onContextMenuClosed(Menu menu){
		curTv.setBackgroundColor(Color.WHITE);
	}
	
	private class SearchOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			initWord();
		}
		
	}
	
	/**
	 * 初始化查询到的单词
	 * @param lessonId
	 */
	private void initWord() {
		int count = layout.getChildCount();
		for (int i = 0; i < count - 1; i++) {
			layout.removeView(layout.getChildAt(1));
		}
		
		EditText etWord = (EditText)super.findViewById(R.id.translateWord);
		List<Word> list = getWordByName(etWord.getText().toString().trim());
		System.out.println("查询到记录："+list.size());
		for(Word w:list){
			TableRow row = new TableRow(TranslateWord.this);
			// 创建显示日语单词的文本组件
			TextView tv = new TextView(TranslateWord.this);
			tv.setText(w.getChword());
			tv.setPadding(10,10,0,10);
			tv.setGravity(Gravity.CENTER_VERTICAL);
			row.addView(tv, 0);
			TextView tv1 = new TextView(this);
			if(w.getJpch().length()+w.getJpword().length()>6){
				tv1.setText(w.getJpword()+"\n（"+w.getJpch()+"）");
			}else{
				tv1.setText(w.getJpword()+"（"+w.getJpch()+"）");
			}
			tv1.setTag(w);
			TranslateWord.super.registerForContextMenu(tv1);
			tv1.setPadding(10,10,0,10);
			tv1.setGravity(Gravity.CENTER_VERTICAL);
			row.addView(tv1, 1);
			layout.addView(row);
		}
	}

	
	private List<Word> getWordByName(String name){
		String sql = "select * from gj_word where jpword like '%"+name+"%' or chword like '%"+name+"%'";
		Cursor c = db.rawQuery(sql, null);
		List<Word> list = new ArrayList<Word>();
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
			Word w = new Word(id, jpword, chword, jpch, lessonid, level, errornum,speech,textbook);

			list.add(w);
		}
		return list;
	}
}
