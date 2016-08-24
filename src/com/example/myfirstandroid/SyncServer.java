package com.example.myfirstandroid;

import android.app.Activity;
//import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
  
import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.impl.client.DefaultHttpClient;   

public class SyncServer extends Activity {
	
	private Button sync = null;       //单词学习
	private TextView content = null;    //语法学习
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syncserver);
        
        sync = (Button)super.findViewById(R.id.sync);
        sync.setOnClickListener(new WordOnClick());
        
        content = (TextView)super.findViewById(R.id.tvContent);
//        content.setOnClickListener(new GrammarOnClick());
        
    }
	
	private class WordOnClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
//			Intent it = new Intent(SyncServer.this,WordView.class);
//			it.putExtra("info","单词学习");
//			SyncServer.this.startActivity(it);
			
			content.setText("内容是："+getContent());
		}
	}
	
	public static void main(String ...args){
		System.out.println("内容是："+getContent());
	}
	
	public static String getContent(){
		try{
			HttpPost post = new HttpPost("http://192.168.1.105:80/ci/index.php/mymail/hello1");  
	        //如果传递参数个数比较多，可以对传递的参数进行封装  
//	        List<NameValuePair> params = new ArrayList<NameValuePair>();  
//	        params.add(new BasicNameValuePair("id", id));  
//	        params.add(new BasicNameValuePair("psw", psw));  
//	        //设置请求参数  
//	        post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
			
	          
	        HttpClient httpClient = new DefaultHttpClient();  
	        //发送POST请求  
	        HttpResponse response = httpClient.execute(post);  
	        //如果服务器成功地返回响应  
	        if(response.getStatusLine().getStatusCode() == 200) {  
	            //String msg = EntityUtils.toString(response.getEntity());  
	            HttpEntity entity = response.getEntity();  
	            InputStream is = entity.getContent();  
	              
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);  
	            StringBuilder sb = new StringBuilder();  
	            sb.append(reader.readLine() + "\n"); // 这里 “ + "\n" ”加不加似乎对结果没有什么影响  
	              
	            String line = "0";  
	            while((line = reader.readLine()) != null) {  
	                sb.append(line + "\n"); // 这里 “ + "\n" ”加不加似乎对结果没有什么影响  
	            }  
	            is.close();  
	              
	            //获取请求响应结果  
	            String result = sb.toString();  
	            System.out.println(result);  
	            return result;
//	            //打包成JSON进行解析  
//	            JSONArray jsonArray = new JSONArray(result);  
//	            JSONObject jsonData = null;  
//	            //返回用户ID，用户密码  
//	            String userId = "";  
//	            String userPsw = "";  
//	            //使用List进行存储  
//	            List<String> data = new ArrayList<String>();  
//	            for(int i = 0; i < jsonArray.length(); i++) {  
//	                jsonData = jsonArray.getJSONObject(i);  
//	                userId = jsonData.getString("userId"); //userId是来源于服务器端php程序响应结果res的索引，根据索引获取值  
//	                userPsw = jsonData.getString("userPsw"); //userPsw是来源于服务器端php程序响应结果res的索引，根据索引获取值  
//	                data.add("用户ID：" + userId + "，用户密码：" + userPsw); //保存返回的值，可进行相应的操作，这里只进行显示  
//	            }  
	              
//	            Looper.prepare();  
//	            Toast.makeText(MainActivity.this, data.toString(), Toast.LENGTH_LONG).show();  
//	            Looper.loop();  
	        }  
	        else {  
//	            Looper.prepare();  
//	            Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_LONG).show();  
//	            Looper.loop();  
	        }  
		}catch(Exception e){
			e.printStackTrace();
		}
		
        return null;
	}
	
}
