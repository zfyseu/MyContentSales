package com.zfy.springmvc.meta;

import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

@Repository
public class Buyer_Contents {

	private int id;
	private String title;
	private String abstracts;
	private String description;
	private double price;
	private byte[] photo;
	private int buy_state;
	private Timestamp buy_time;
	private int buy_count;
	private int send_id;
	
	public int getSend_id() {
		return send_id;
	}
	public void setSend_id(int send_id) {
		this.send_id = send_id;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public int getBuy_state() {
		return buy_state;
	}
	public void setBuy_state(int buy_state) {
		this.buy_state = buy_state;
	}
	public Timestamp getBuy_time() {
		return buy_time;
	}
	public void setBuy_time(Timestamp buy_time) {
		this.buy_time = buy_time;
	}
	public int getBuy_count() {
		return buy_count;
	}
	public void setBuy_count(int buy_count) {
		this.buy_count = buy_count;
	}
	
	public void SellerToBuyer(Seller_Contents sc)
	{
		title=sc.getTitle();
		abstracts=sc.getAbstracts();
		description=sc.getDescription();
		price=sc.getPrice();
		photo=sc.getPhoto();
	}
	
}
