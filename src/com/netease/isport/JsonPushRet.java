package com.netease.isport;

import java.util.List;

public class JsonPushRet {
	private int count;
	private String ret;
	
	private List<JsonActivity> list;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}
	public List<JsonActivity> getList() {
		return list;
	}

	public void setList(List<JsonActivity> list) {
		this.list = list;
	}
}
