package com.zfy.springmvc.meta;

import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

@Repository
public class Seller_Contents {

	private int id;
	private String title;
	private String abstracts;
	private String description;
	private double price;
	private byte[] photo;
	private int send_state;//0:no send 1:in shopcart 2:send already
	private Timestamp post_time;
	private Timestamp send_time;
	private int send_count;
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
	
	public String getAbstracts() {
		return abstracts;
	}
	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public int getSend_state() {
		return send_state;
	}
	public void setSend_state(int send_state) {
		this.send_state = send_state;
	}
	public Timestamp getPost_time() {
		return post_time;
	}
	public void setPost_time(Timestamp post_time) {
		this.post_time = post_time;
	}
	public Timestamp getSend_time() {
		return send_time;
	}
	public void setSend_time(Timestamp send_time) {
		this.send_time = send_time;
	}
	public int getSend_count() {
		return send_count;
	}
	public void setSend_count(int send_count) {
		this.send_count = send_count;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}
