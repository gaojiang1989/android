package com.example.myfirstandroid;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class WordView extends Activity {
//	private Button btnJp;
	private Button btnCh;           //顺序学习
	private Button btnTranslate;    //中日词典
	private Button btnGroup;        //分组学习
//	private static final Logger logger = LoggerFactory.getLogger(); 
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_view);
        
//        btnJp = (Button)super.findViewById(R.id.queueJp);
//        btnJp.setOnClickListener(new QueueJpOnClick());
        
        btnCh = (Button)super.findViewById(R.id.queueCh);
        btnCh.setOnClickListener(new QueueChOnClick());
        
        btnTranslate = (Button)super.findViewById(R.id.translate);
        btnTranslate.setOnClickListener(new TranslateOnClick());
        
        btnGroup = (Button)super.findViewById(R.id.grouplearn);
        btnGroup.setOnClickListener(new GroupLearnOnClick());
    }
	
//	/**
//	 * 顺序日译中
//	 * @author epri
//	 *
//	 */
//	public class QueueJpOnClick implements OnClickListener{
//
//		@Override
//		public void onClick(View arg0) {
//			// TODO Auto-generated method stub
//			Intent it = new Intent(WordView.this,JpToCh.class);
//			WordView.this.startActivity(it);
//		}
//	}
	
	/**
	 * 顺序学习
	 * @author epri
	 *
	 */
	public class QueueChOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
//			logger.debug("点击了顺序中译日的按钮........"); 
			// TODO Auto-generated method stub
			Intent it = new Intent(WordView.this,ChToJp.class);
			WordView.this.startActivity(it);
		}
		
	}
	
	/**
	 * 分组学习
	 * @author epri
	 *
	 */
	public class GroupLearnOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent it = new Intent(WordView.this,GroupType.class);
			WordView.this.startActivity(it);
		}
		
	}
	
	/**
	 * 中日词典
	 * @author d5000
	 *
	 */
	public class TranslateOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent it = new Intent(WordView.this,TranslateWord.class);
			WordView.this.startActivity(it);
		}
		
	}
}

