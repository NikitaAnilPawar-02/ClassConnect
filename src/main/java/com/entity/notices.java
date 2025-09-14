package com.entity;

import java.time.LocalDateTime;

public class notices {
	private int noticeId;
	public String title, description;
	public LocalDateTime date_posted;
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getDate_posted() {
		return date_posted;
	}
	public void setDate_posted(LocalDateTime date_posted) {
		this.date_posted = date_posted;
	}
	@Override
	public String toString() {
		return "notices [noticeId=" + noticeId + ", title=" + title
				+ ", description=" + description + ", date_posted="
				+ date_posted + "]";
	}

}
