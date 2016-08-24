package com.example.model;

public class Example {

	private int id;
	private int aid;
	private String jptext;
	private String chtext;
	private String desc;
	public Example() {
		super();
	}
	public Example(int id, int aid, String jptext, String chtext, String desc) {
		super();
		this.id = id;
		this.aid = aid;
		this.jptext = jptext;
		this.chtext = chtext;
		this.desc = desc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
