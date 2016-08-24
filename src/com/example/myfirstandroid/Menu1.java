package com.example.myfirstandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Menu1 extends Activity {
	
	private Button wordBtn = null;       //单词学习
	private Button btnGrammar = null;    //语法学习
	private Button btnTranslate = null;  //句子翻译
	private Button btnArticle = null;    //文章背诵
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        wordBtn = (Button)super.findViewById(R.id.word);
        wordBtn.setOnClickListener(new WordOnClick());
        
        btnGrammar = (Button)super.findViewById(R.id.sentence);
        btnGrammar.setOnClickListener(new GrammarOnClick());
        
        btnTranslate = (Button)super.findViewById(R.id.translate);
        btnTranslate.setOnClickListener(new TranslateOnClick());
        
        btnArticle = (Button)super.findViewById(R.id.article);
        btnArticle.setOnClickListener(new ArticleOnClick());
    }
	
	private class WordOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent it = new Intent(Menu1.this,WordView.class);
			it.putExtra("info","单词学习");
			Menu1.this.startActivity(it);
		}
	}
	
	private class GrammarOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent it = new Intent(Menu1.this,GrammarView.class);
//			it.putExtra("info","单词学习");
			Menu1.this.startActivity(it);
		}
	}
	
	private class TranslateOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent it = new Intent(Menu1.this,SentenceTranslateView.class);
			Menu1.this.startActivity(it);
		}
	}
	
	private class ArticleOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
//			Intent it = new Intent(Menu1.this,ArticleVew.class);
//			Menu1.this.startActivity(it);
			
			Intent it = new Intent(Menu1.this,SyncServer.class);
			Menu1.this.startActivity(it);
		}
	}
}
