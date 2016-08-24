package com.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个课文语法。一个课文语法有多个语法块组成。
 * @author epri
 *
 */
public class Grammar {
	private int lesssonId;
	private int textbook;
	private List<Area> list;

	public Grammar() {
		super();
	}

	public Grammar(int lesssonId, int textbook, List<Area> list) {
		super();
		this.lesssonId = lesssonId;
		this.textbook = textbook;
		this.list = list;
	}

	public int getLesssonId() {
		return lesssonId;
	}

	public List<Area> getList() {
		if(list==null)
			list = new ArrayList<Area>();
		return list;
	}

	public void setLesssonId(int lesssonId) {
		this.lesssonId = lesssonId;
	}

	public void setList(List<Area> list) {
		this.list = list;
	}

	public int getTextbook() {
		return textbook;
	}

	public void setTextbook(int textbook) {
		this.textbook = textbook;
	}

}
