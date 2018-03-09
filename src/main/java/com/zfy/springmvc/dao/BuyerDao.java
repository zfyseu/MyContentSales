package com.zfy.springmvc.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zfy.springmvc.meta.Buyer_Contents;
import com.zfy.springmvc.meta.Seller_Contents;

public interface BuyerDao {

	@Insert("insert into buyer_contents(title,abstracts,description,price,photo,buy_state,buy_time,buy_count,send_id) "
			+ "values(#{title},#{abstracts},#{description},#{price},#{photo},#{buy_state},#{buy_time},#{buy_count},#{send_id})")
	public int insertBuyerContents(Buyer_Contents bc);
	
	@Select("select * from buyer_contents where buy_state=2")
	public List<Buyer_Contents> getBuyContentsLists();
	
	@Select("select * from buyer_contents where buy_state=1")
	public List<Buyer_Contents> getShoppingCartLists();
	
	@Select("select * from buyer_contents where id=#{id}")
	public Buyer_Contents getBuyContentsById(int id);
	
	@Delete("delete from buyer_contents where send_id=#{id}")
	public int deleteFromSeller(@Param("id")int id);
	
	@Update("update buyer_contents set buy_state=#{buy_state},buy_time=#{buy_time} where id=#{id}")
	public int updateBuy_StateAndBuy_time(@Param("id")int id,@Param("buy_state")int buy_state,@Param("buy_time")Timestamp buy_time);
	
	@Update("update buyer_contents set title=#{title},abstracts=#{abstracts},description=#{description},price=#{price},"
			+ "photo=#{photo} where send_id=#{id}")
	public int updateFromSeller(Seller_Contents sc);
	
}
