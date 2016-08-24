package com.example.model;

public class ItemClass {
	private int id;
	private int classNo;
	private String className;
	private int textbook;        //初级：0   中级：1   高级：2
	private String desc;

	public ItemClass() {
		super();
	}

	public ItemClass(int id, int classNo, String className, int textbook,
			String desc) {
		super();
		this.id = id;
		this.classNo = classNo;
		this.className = className;
		this.textbook = textbook;
		this.desc = desc;
	}
//	public ItemClass(int classNo, String className, int textbook) {
//		super();
//		this.classNo = classNo;
//		this.className = className;
//		this.textbook = textbook;
//	}
	public int getClassNo() {
		return classNo;
	}
	public void setClassNo(int classNo) {
		this.classNo = classNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getTextbook() {
		return textbook;
	}
	public void setTextbook(int textbook) {
		this.textbook = textbook;
	}
	public String toString(){
		return this.className;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
