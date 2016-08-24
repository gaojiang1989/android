package com.example.model;

import java.util.List;

/**
 * 一个类代表一个语法块，每个语法块中有多个语法例子，用对象Example表示。多个语法块构成一个课文语法
 * @author epri
 *
 */
public class Area {

	private int id;
	private int seqNo;
	private String title;
	private String context;
	private List<Example> listExample;
	public Area() {
		super();
	}
	public Area(int id, int seqNo, String title, String context) {
		super();
		this.id = id;
		this.seqNo = seqNo;
		this.title = title;
		this.context = context;
	}

	public Area(int id, int seqNo, String title, String context,
			List<Example> listExample) {
		super();
		this.id = id;
		this.seqNo = seqNo;
		this.title = title;
		this.context = context;
		this.listExample = listExample;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public List<Example> getListExample() {
		return listExample;
	}
	public void setListExample(List<Example> listExample) {
		this.listExample = listExample;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
}
