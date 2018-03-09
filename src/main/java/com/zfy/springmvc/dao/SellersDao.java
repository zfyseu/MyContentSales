package com.zfy.springmvc.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zfy.springmvc.meta.Seller_Contents;

public interface SellersDao {

	@Insert("insert into seller_contents(title,abstracts,description,price,photo,send_state,post_time,send_time,send_count) "
			+ "values(#{title},#{abstracts},#{description},#{price},#{photo},#{send_state},#{post_time},#{send_time},#{send_count})")
	public int insertSeller_Contents(Seller_Contents sc);
	
	@Select("select * from seller_contents where id=#{id}")
	public Seller_Contents getSeller_ContentsById(@Param("id") int id);
	
	@Select("select * from seller_contents")
	public List<Seller_Contents> getSellerList();
	
	@Select("select * from seller_contents where send_state<>2")
	public List<Seller_Contents> getNoSendList();
	
	@Select("select id from seller_contents where send_state=2")
	public List<Integer> getSendIds();
	
	@Select("select count(*) from seller_contents")
	public int getNum();
	
	
	@Delete("delete  from seller_contents where id=#{id}")
	public int deleteContentsById(@Param("id")int id);
	
	@Update("update seller_contents set title=#{title},abstracts=#{abstracts},description=#{description},price=#{price},photo=#{photo}"
			+ "where id=#{id}")
	public int updateSeller_Contents(Seller_Contents sc);

	@Update("update seller_contents set send_state=#{send_state},send_time=#{send_time},send_count=send_count+#{send_count} where id=#{id}")
	public int updateCountAndSendtimeAndState(@Param("id")int id,@Param("send_state")int send_state,@Param("send_time")Timestamp send_time,@Param("send_count")int send_count);
	
	
}
