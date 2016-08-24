package beifen; 

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.db.DataBaseHelper;
import com.example.model.ItemClass;
import com.example.model.Word;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class JpToCh extends Activity {
	TableLayout layout;

	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);

		ScrollView sv = new ScrollView(this);

		layout = new TableLayout(this);
		TableLayout.LayoutParams layoutParam = new TableLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		createSpinner();

		sv.addView(layout);
		super.setContentView(sv, layoutParam);
	}

	/**
	 * 创建课程选择的下拉框
	 */
	private void createSpinner() {
		Spinner sp = new Spinner(this);
		sp.setPrompt("500");

		List<ItemClass> list = getLesson();
		ArrayAdapter<ItemClass> aa = new ArrayAdapter<ItemClass>(this,
				android.R.layout.simple_spinner_item, list);
		sp.setAdapter(aa);
		sp.setOnItemSelectedListener(new OnClassSelect());
		layout.addView(sp);
	}

	private class OnClassSelect implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int position, long id) {
			// TODO Auto-generated method stub
			// String value = arg0.getItemAtPosition(position).toString();
			int count = layout.getChildCount();
			for (int i = 0; i < count - 1; i++) {
				layout.removeView(layout.getChildAt(1));
			}
			ItemClass value = (ItemClass) arg0.getItemAtPosition(position);

			int lessonId = value.getClassNo();
			List<Word> list = getWord(lessonId);
			for (int x = 0; x < list.size(); x++) {
				TableRow row = new TableRow(JpToCh.this);
				// 创建显示日语单词的文本组件
				TextView tv = new TextView(JpToCh.this);
				tv.setText(list.get(x).getJpword());
				tv.setWidth(layout.getWidth() / 3);
				row.addView(tv, 0);
				// 创建显示按钮
				Button bw = new Button(JpToCh.this);
				bw.setTag(list.get(x).getId());
				bw.setText("查看");

				bw.setWidth(layout.getWidth() * 2 / 3);
				bw.setOnClickListener(new OnBtnSearch());
				row.addView(bw, 1);
				layout.addView(row);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

	private class OnBtnSearch implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			int tag = (Integer) arg0.getTag();
			Word w = getWordByid(tag);
			Button but = (Button) arg0;

			if (w.getChword().equals(w.getJpch())
					|| w.getJpch().equals(w.getJpword())) {
				but.setText(w.getChword());
			} else {
				but.setText("(" + w.getJpch() + ")" + w.getChword());
			}
		}

	}

	public List<ItemClass> getLesson() {
		List<ItemClass> list = new ArrayList<ItemClass>();
		SQLiteOpenHelper database = new DataBaseHelper(JpToCh.this);
		SQLiteDatabase db = null;
		db = database.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM gj_lesson", null);

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
		
		SQLiteOpenHelper database = new DataBaseHelper(JpToCh.this);
		SQLiteDatabase db = null;
		db = database.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM gj_word where lessonid="
				+ lessonid, null);
		Set<Word> set = new HashSet<Word>();
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
		}
		c.close();
		List<Word> list = new ArrayList<Word>(set);
		return list;
	}

	/**
	 * 根据word的id来查询word
	 * 
	 * @param wordid
	 * @return
	 */
	public Word getWordByid(int wordid) {
		SQLiteOpenHelper database = new DataBaseHelper(JpToCh.this);
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

			// list.add(w);
		}
		c.close();
		return w;
	}
}