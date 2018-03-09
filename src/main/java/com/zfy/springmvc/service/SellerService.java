package com.zfy.springmvc.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zfy.springmvc.dao.SellersDao;
import com.zfy.springmvc.meta.Seller_Contents;

@Service
public class SellerService {

	@Autowired
	private SellersDao seller_dao;
	
	public int insertSeller_Contents(Seller_Contents sc)
	{
		return seller_dao.insertSeller_Contents(sc);
	}
	
	public Seller_Contents getSellerById(int id)
	{
		return seller_dao.getSeller_ContentsById(id);
	}
	
	public List<Seller_Contents> getSellerList()
	{
		return seller_dao.getSellerList();
	}
	
	public List<Seller_Contents> getNoSendList()
	{
		return seller_dao.getNoSendList();
	}
	
	public List<Integer> getSendIds()
	{
		return seller_dao.getSendIds();
	}
	
	public int getSellerContentsNum()
	{
		return seller_dao.getNum();
	}
	
	public int deleteContentById(int id)
	{
		return seller_dao.deleteContentsById(id);
	}
	
	public int updateSellerContents(Seller_Contents sc)
	{
		return seller_dao.updateSeller_Contents(sc);
	}
	
	public int updateCountAndSendtiem(int id,int send_state,Timestamp send_time,int send_count)
	{
		return seller_dao.updateCountAndSendtimeAndState(id, send_state,send_time, send_count);
	}
	
}
