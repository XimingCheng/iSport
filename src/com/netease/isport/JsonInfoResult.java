package com.netease.isport;

public class JsonInfoResult {
	
	private String username;
	private String location;
	private String sex;
	private float  score;
	private String completed_act_id;
	private String uncompleted_act_id;
	private String user_image;
	private String label;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getCompleted_act_id() {
		return completed_act_id;
	}
	public void setCompleted_act_id(String completed_act_id) {
		this.completed_act_id = completed_act_id;
	}
	public String getUncompleted_act_id() {
		return uncompleted_act_id;
	}
	public void setUncompleted_act_id(String uncompleted_act_id) {
		this.uncompleted_act_id = uncompleted_act_id;
	}
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
