package com.example.myfirstandroid;

import java.util.ArrayList;
import java.util.List;

import com.example.db.DataBaseHelper;
import com.example.model.Example;
import com.example.model.Word;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class WordDetail extends Activity {

	private int wordId;
	TableLayout layout;
	SQLiteDatabase db = null;
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		
		Intent it = super.getIntent();
		wordId = it.getIntExtra("wordId",0);
		
		SQLiteOpenHelper database = new DataBaseHelper(this);
		db = database.getReadableDatabase();
		
		
		layout = new TableLayout(this);
		ScrollView sv = new ScrollView(this);
		TableLayout.LayoutParams layoutParam = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		sv.addView(layout);
		super.setContentView(sv, layoutParam);
		
		Word w = getWordByid(wordId);
		addTextView(w.getJpword());
//		addTextView(w.getJpch());
		if(w.getJpch().equals(w.getJpword())||w.getChword().equals(w.getJpword())){
			addTextView(w.getChword());
		}else{
			addTextView(w.getJpch());
			addTextView(w.getChword());
			
		}
		addTextView(w.getSpeech());
		addTextView("第"+w.getLessonid()+"课");
		
		List<Example> list = getExampleByWord(w,w.getLessonid());
		for(Example emp:list){
			if(emp.getChtext()!=null&&!emp.getChtext().equals("")){
				addTextView(emp.getJptext());
				addTextView(emp.getChtext());
			}else{
				addTextView(emp.getJptext());
			}
		}
	}
	
	private int getScreenWidth(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	
	private void addTextView(String msg){
		TableRow row = new TableRow(this);
		TextView tv = new TextView(this);
		tv.setPadding(10,10,10,10);
		tv.setWidth(getScreenWidth());
//		tv.setBackgroundColor(Color.GRAY);
//		tv.setTextSize(20);
		tv.setText(msg);
		row.addView(tv);
		layout.addView(row);
	}
	
	public Word getWordByid(int wordid) {

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
	
	private List<Example> getExampleByWord(Word word,int lessonId){
		List<Example> list = basicSearch(word.getJpch(), lessonId);
		if(list.size()==0){
			String wordtext = word.getJpch();
			if(word.getSpeech().equals("动1")){
//				System.out.println("是动1 吗？"+word.getSpeech().indexOf("ます"));
				if(wordtext.indexOf("ます")>-1){
//					System.out.println("是动1 吗？");
					wordtext = word.getJpch().substring(0,word.getJpch().length()-3);
				}
			}else if(word.getSpeech().equals("动2")){
				if(wordtext.indexOf("ます")>-1){
					wordtext = word.getJpch().substring(0,word.getJpch().length()-2);
				}
			}else if(word.getSpeech().equals("动3")){
				if(wordtext.indexOf("ます")>-1){
					wordtext = word.getJpch().substring(0,word.getJpch().length()-2);
				}
			}else if(word.getSpeech().equals("形1")){
				if(wordtext.indexOf("い")>-1){
					wordtext = word.getJpch().substring(0,word.getJpch().length()-1);
				}
			}
//			System.out.println("去掉ます形之后的形态："+wordtext);
			list = basicSearch(wordtext, lessonId);
		}
		return list;
	}

	private List<Example> basicSearch(String word, int lessonId) {
		String sql = "SELECT * from gj_gram_example WHERE aid in (SELECT id FROM gj_grammar WHERE lessonid="+lessonId+") AND jptext LIKE '%"+word+"%'";
		Cursor c = db.rawQuery(sql, null);
		List<Example> list = new ArrayList<Example>();
		while(c.moveToNext()){
			int id = c.getInt(c.getColumnIndex("id"));
			int aid = c.getInt(c.getColumnIndex("aid"));
			String jptext = c.getString(c.getColumnIndex("jptext"));
			String chtext = c.getString(c.getColumnIndex("chtext"));
			String desc = c.getString(c.getColumnIndex("desc"));
			Example emp = new Example(id, aid, jptext, chtext, desc);
			list.add(emp);
		}
		
		if(list.size()==0){
			sql = "SELECT * from gj_article WHERE lessonid="+lessonId+" AND jptext LIKE '%"+word+"%'";
			c = db.rawQuery(sql, null);
			if(c.moveToNext()){
				int id = c.getInt(c.getColumnIndex("id"));
				int aid = 0;
				String jptext = c.getString(c.getColumnIndex("jptext"));
				String chtext = c.getString(c.getColumnIndex("chtext"));
				String desc = c.getString(c.getColumnIndex("desc"));
				Example emp = new Example(id, aid, jptext, chtext, desc);
				list.add(emp);
			}
		}
		return list;
	}
	
	

}
