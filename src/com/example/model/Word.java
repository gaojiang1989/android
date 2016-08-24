package com.example.model;

public class Word {
	private int id;
	private String jpword;
	private String chword;
	private String jpch;
	private int lessonid;
	private int level;
	private int errorNum;
	private String speech;
	private int textbook;
	public Word() {
		super();
	}
	public Word(int id, String jpword, String chword, String jpch,
			int lessonid, int level, int errorNum, String speech, int textbook) {
		super();
		this.id = id;
		this.jpword = jpword;
		this.chword = chword;
		this.jpch = jpch;
		this.lessonid = lessonid;
		this.level = level;
		this.errorNum = errorNum;
		this.speech = speech;
		this.textbook = textbook;
	}
//	public Word(int id, String jpword, String chword, String jpch,
//			int lessonid, int level, int errorNum) {
//		super();
//		this.id = id;
//		this.jpword = jpword;
//		this.chword = chword;
//		this.jpch = jpch;
//		this.lessonid = lessonid;
//		this.level = level;
//		this.errorNum = errorNum;
//	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJpword() {
		return jpword;
	}
	public void setJpword(String jpword) {
		this.jpword = jpword;
	}
	public String getChword() {
		return chword;
	}
	public void setChword(String chword) {
		this.chword = chword;
	}
	public String getJpch() {
		return jpch;
	}
	public void setJpch(String jpch) {
		this.jpch = jpch;
	}
	public int getLessonid() {
		return lessonid;
	}
	public void setLessonid(int lessonid) {
		this.lessonid = lessonid;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getErrorNum() {
		return errorNum;
	}
	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}
	public String getSpeech() {
		return speech;
	}
	public void setSpeech(String speech) {
		this.speech = speech;
	}
	public int getTextbook() {
		return textbook;
	}
	public void setTextbook(int textbook) {
		this.textbook = textbook;
	}

}
