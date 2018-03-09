package com.zfy.springmvc.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zfy.springmvc.dao.BuyerDao;
import com.zfy.springmvc.meta.Buyer_Contents;
import com.zfy.springmvc.meta.Seller_Contents;

@Service
public class BuyerService {

	@Autowired
	private BuyerDao buyer_dao;
	
	public int insertBuyerContents(Buyer_Contents bc)
	{
		return buyer_dao.insertBuyerContents(bc);
	}
	
	public List<Buyer_Contents> getBuyerContentsLists()
	{
		return buyer_dao.getBuyContentsLists();
	}
	
	public List<Buyer_Contents> getShoppingCartLists()
	{
		return buyer_dao.getShoppingCartLists();
	}
	
	public Buyer_Contents getBuyerContentsById(int id)
	{
		return buyer_dao.getBuyContentsById(id);
	}
	
	public int updateBuy_CountAndBuy_Time(int id,int buy_state,Timestamp buy_time)
	{
		return buyer_dao.updateBuy_StateAndBuy_time(id, buy_state, buy_time);
	}
	
	public int updateFromSeller(Seller_Contents sc)
	{
		return buyer_dao.updateFromSeller(sc);
	}
	
	public int deleteFromSeller(int id)
	{
		return buyer_dao.deleteFromSeller(id);
	}
	
}
