package com.example.model;

public class Article {
	private int id;
	private int lessonid;
	private String type;
	private String jptext;
	private String chtext;
	private int textbook;
	private String desc;
	public Article() {
		super();
	}
	public Article(int id, int lessonid, String type, String jptext,
			String chtext, int textbook, String desc) {
		super();
		this.id = id;
		this.lessonid = lessonid;
		this.type = type;
		this.jptext = jptext;
		this.chtext = chtext;
		this.textbook = textbook;
		this.desc = desc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLessonid() {
		return lessonid;
	}
	public void setLessonid(int lessonid) {
		this.lessonid = lessonid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getJptext() {
		return jptext;
	}
	public void setJptext(String jptext) {
		this.jptext = jptext;
	}
	public String getChtext() {
		return chtext;
	}
	public void setChtext(String chtext) {
		this.chtext = chtext;
	}
	public int getTextbook() {
		return textbook;
	}
	public void setTextbook(int textbook) {
		this.textbook = textbook;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
