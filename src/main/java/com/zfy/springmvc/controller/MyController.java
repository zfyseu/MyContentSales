package com.zfy.springmvc.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zfy.springmvc.meta.Buyer_Contents;
import com.zfy.springmvc.meta.Seller_Contents;
import com.zfy.springmvc.service.BuyerService;
import com.zfy.springmvc.service.SellerService;

import net.sf.json.JSONObject;

@Controller
public class MyController {

	@Autowired
	private SellerService seller_service;
	
	@Autowired
	private BuyerService buyer_service;
	
	
	@RequestMapping("/login")
	public String login()
	{
		return "login";
	}
	
	@RequestMapping("/verify_password")
	@ResponseBody
	public String verify(String account,String password_md5,String password,HttpServletRequest request)
	{
		System.out.println("\n*********Login***********");
		System.out.println("account: "+account);
		System.out.println("md5:"+password_md5);
		
		HttpSession session=request.getSession();
		
		String md5_buyer="37254660e226ea65ce6f1efd54233424";
		String md5_seller="981c57a5cfb0f868e064904b8745766f";
		Map<String,Object> map=new HashMap<String,Object>();
		if((password_md5.equals(md5_buyer)&&account.equals("buyer"))||(account.equals("seller")&&password_md5.equals(md5_seller)))
		{
			session.setAttribute("account", account);
			map.put("res", "OK");
		}
		else
		{
			session.setAttribute("account", "visitor");
			map.put("res", "failed");
		}

		
		JSONObject jsonObject=JSONObject.fromObject(map);
	    return jsonObject.toString();
	}
	
	@RequestMapping("/exit")
	public String exit(HttpSession session)
	{
		session.setAttribute("account", "visitor");
		return "login";
	}
	
	@RequestMapping("/")
	public String index_page(Map<String,Object> map,@RequestParam(value="x",required=false)String x)
	{
		List<Seller_Contents> sclists=null;
		if(x==null||x.isEmpty())
		{
			sclists=seller_service.getSellerList();
		}
		else if(x.equals("nobuy"))
		{
			sclists=seller_service.getNoSendList();
		}
		
		List<Integer> send_ids=seller_service.getSendIds();
		int num=sclists.size();
		int[] ids=new int[num];
		String[] titles=new String[num];
		double[] prices=new double[num];
		
		int i=0;
		for(Seller_Contents sc:sclists)
		{
			ids[i]=sc.getId();
			titles[i]=sc.getTitle();
			prices[i]=sc.getPrice();
			i++;
		}
		
		map.put("ids", ids);
		map.put("titles", titles);
		map.put("prices", prices);
		map.put("num", num);
		map.put("send_ids", send_ids);
		
		return "index_page";
	}
	
	
	@RequestMapping("/look_seller_contents")
	public void looksellerphoto(@RequestParam("id") int id,Map<String,Object> map,HttpServletResponse response)
	{
		Seller_Contents sc=seller_service.getSellerById(id);
		byte[] data=sc.getPhoto();
		response.setContentType("img/jpg");
		response.setCharacterEncoding("utf-8");
		try
		{
			OutputStream outs=response.getOutputStream();
			InputStream in=new ByteArrayInputStream(data);
			
			int len=0;
			byte[] buf=new byte[1024];
			while((len=in.read(buf,0,1024))!=-1)
			{
				outs.write(buf,0,len);
			}
			outs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/content")
	public String goSeller_Content_Page(@RequestParam("id") int id,Map<String,Object> map)
	{
		Seller_Contents sc=seller_service.getSellerById(id);
		map.put("id", id);
		map.put("title", sc.getTitle());
		map.put("price", sc.getPrice());
		map.put("abstracts", sc.getAbstracts());
		map.put("description", sc.getDescription());
		map.put("send_count", sc.getSend_count());
		map.put("post_time", sc.getPost_time());
		return "content";
	}
	
	@RequestMapping("/buy")
	@ResponseBody
	public String buy(String id,String amount)
	{
		
		
		int m_id=Integer.parseInt(id);
		int m_amount=Integer.parseInt(amount);
		
		Seller_Contents sc=seller_service.getSellerById(m_id);
		Buyer_Contents bc=new Buyer_Contents();
		Timestamp send_time=new Timestamp(System.currentTimeMillis());
		bc.setBuy_state(1);
		bc.setBuy_time(send_time);
		bc.setBuy_count(m_amount);
		bc.setSend_id(m_id);
		bc.SellerToBuyer(sc);
		
		
		if(buyer_service.insertBuyerContents(bc)==1)
		{
			System.out.println("insert OK!!");
		}
		else
		{
			System.out.println("insert failed!!");
		}
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("res", "OK");
		JSONObject jsonObject=JSONObject.fromObject(map);
	    return jsonObject.toString();
	}
	
	@RequestMapping("/shopping_cart")
	public String shopping_cart(Map<String,Object> map)
	{
		List<Buyer_Contents> bclists=buyer_service.getShoppingCartLists();
		int num=bclists.size();
		int[] ids=new int[num];
		String[] titles=new String[num];
		double[] prices=new double[num];
		int[] buy_counts=new int[num];
		int i=0;
		for(Buyer_Contents bc:bclists)
		{
			ids[i]=bc.getId();
			titles[i]=bc.getTitle();
			prices[i]=bc.getPrice();
			buy_counts[i]=bc.getBuy_count();
			i++;
		}
		map.put("ids",ids);
		map.put("titles",titles);
		map.put("prices",prices);
		map.put("buy_counts",buy_counts);
		
		return "shopping_cart";
	}
	
	@RequestMapping("/look_buyer_cart")
	public void look_buyer_cart(@RequestParam("id") int id,HttpServletResponse response,Map<String,Object> map)
	{
		Buyer_Contents bc=buyer_service.getBuyerContentsById(id);
		byte[] data=bc.getPhoto();
		response.setContentType("img/jpg");
		response.setCharacterEncoding("utf-8");
		try
		{
			OutputStream outs=response.getOutputStream();
			InputStream in=new ByteArrayInputStream(data);
			
			int len=0;
			byte[] buf=new byte[1024];
			while((len=in.read(buf,0,1024))!=-1)
			{
				outs.write(buf,0,len);
			}
			outs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/purchase")
	public String purchase(Map<String,Object> map)
	{
		List<Buyer_Contents> bclists=buyer_service.getShoppingCartLists();
		
		for(Buyer_Contents bc:bclists)
		{
			Timestamp time=new Timestamp(System.currentTimeMillis());
			buyer_service.updateBuy_CountAndBuy_Time(bc.getId(), 2,time);
			seller_service.updateCountAndSendtiem(bc.getSend_id(), 2, time, bc.getBuy_count());
		}
		List<Buyer_Contents> buylists=buyer_service.getBuyerContentsLists();
		int num=buylists.size();
		int i=0;
		int[] ids=new int[num];
		String[] titles=new String[num];
		double[] prices=new double[num];
		int[] buy_counts=new int[num];
		Timestamp[] buy_times=new Timestamp[num];
		for(Buyer_Contents bc:buylists)
		{
			ids[i]=bc.getId();
			titles[i]=bc.getTitle();
			prices[i]=bc.getPrice();
			buy_counts[i]=bc.getBuy_count();
			buy_times[i]=bc.getBuy_time();
			i++;
		}
		map.put("ids",ids);
		map.put("titles",titles);
		map.put("prices",prices);
		map.put("buy_counts",buy_counts);
		map.put("buy_times", buy_times);
		
		return "purchased";
	}
	
	@RequestMapping("/look_purchased")
	public String look_purchased(Map<String,Object> map)
	{
		List<Buyer_Contents> buylists=buyer_service.getBuyerContentsLists();
		int num=buylists.size();
		int i=0;
		int[] ids=new int[num];
		String[] titles=new String[num];
		double[] prices=new double[num];
		int[] buy_counts=new int[num];
		Timestamp[] buy_times=new Timestamp[num];
		for(Buyer_Contents bc:buylists)
		{
			ids[i]=bc.getId();
			titles[i]=bc.getTitle();
			prices[i]=bc.getPrice();
			buy_counts[i]=bc.getBuy_count();
			buy_times[i]=bc.getBuy_time();
			i++;
		}
		map.put("ids",ids);
		map.put("titles",titles);
		map.put("prices",prices);
		map.put("buy_counts",buy_counts);
		map.put("buy_times", buy_times);
		
		return "purchased";
	}
	
	@RequestMapping("/look_buyer_contents")
	public void look_buyer_contents(@RequestParam("id") int id,HttpServletResponse response,Map<String,Object> map)
	{
		Buyer_Contents bc=buyer_service.getBuyerContentsById(id);
		byte[] data=bc.getPhoto();
		response.setContentType("img/jpg");
		response.setCharacterEncoding("utf-8");
		try
		{
			OutputStream outs=response.getOutputStream();
			InputStream in=new ByteArrayInputStream(data);
			
			int len=0;
			byte[] buf=new byte[1024];
			while((len=in.read(buf,0,1024))!=-1)
			{
				outs.write(buf,0,len);
			}
			outs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/delete")
	public String deleteContent(@RequestParam("id") int id)
	{
		seller_service.deleteContentById(id);
		buyer_service.deleteFromSeller(id);
		return "redirect:/MyContentSales";
	}
	
	@RequestMapping("/save_photo_test")
	@ResponseBody
	public String savephoto_test(HttpServletRequest request) throws IOException
	{
		
		String upload_way=request.getParameter("upload_way");
		String title=request.getParameter("title");
		String abstracts=request.getParameter("abstracts");
		String description=request.getParameter("description");
		String price=request.getParameter("price");
		
		Map<String,Object> map=new HashMap<String,Object>();
		byte[] data=null;
		if(upload_way.equals("url_upload"))
		{
			String photo_url=request.getParameter("photo_url");
			if(photo_url.isEmpty())
			{
				map.put("res", "url");
				JSONObject jsonObject=JSONObject.fromObject(map);
			    return jsonObject.toString();
			}
			else
			{
				URL url=new URL(photo_url);
				DataInputStream dataInputStream = new DataInputStream(url.openStream());
				ByteArrayOutputStream output = new ByteArrayOutputStream();  
				  
	            byte[] buffer = new byte[1024];  
	            int length;  
	  
	            while ((length = dataInputStream.read(buffer)) > 0) {  
	                output.write(buffer, 0, length);  
	            } 
	            data=output.toByteArray();
			}
		}
		else if(upload_way.equals("local_upload"))
		{
			MultipartHttpServletRequest multipartrequest=(MultipartHttpServletRequest)request;
			MultipartFile photo=multipartrequest.getFile("photo");
			if(photo.isEmpty())
			{
				map.put("res", "photo");
				JSONObject jsonObject=JSONObject.fromObject(map);
			    return jsonObject.toString();
			}
			else 
			{
				data=photo.getBytes();
			}
			
		}

		if(title.isEmpty())
		{
			map.put("res", "title");
		}
		else if(abstracts.isEmpty())
		{
			map.put("res", "abstracts");
			
		}else if(description.isEmpty())
		{
			map.put("res", "description");
		}
		else if(price.isEmpty())
		{
			map.put("res", "price");
		}
		else if(upload_way.isEmpty())
		{
			map.put("res", "upload_way");
		}
		else
		{
			double price_d=Double.parseDouble(price);
			Seller_Contents sc=new Seller_Contents();
			
			
			sc.setTitle(title);
			sc.setSend_count(0);
			sc.setDescription(description);
			sc.setAbstracts(abstracts);
			sc.setSend_state(0);
			sc.setPhoto(data);
			sc.setPost_time(new Timestamp(System.currentTimeMillis()));
			sc.setPrice(price_d);
			
		
			
			int res=seller_service.insertSeller_Contents(sc);
			if(res==1)
			{
				System.out.println("insert Photo Ok!!!!");
				map.put("res", "OK");
			}
			else
			{
				System.out.println("insert photo failed!!!");
				map.put("res", "failed");
			}
		}
		JSONObject jsonObject=JSONObject.fromObject(map);
	    return jsonObject.toString();
	}
	
	@RequestMapping("/post_content")
	public String post_content()
	{
		return "post_content";
	}
	
	@RequestMapping("/modify")
	public String goModifyPage(@RequestParam("id") int id,Map<String,Object> map)
	{
		Seller_Contents sc=seller_service.getSellerById(id);
		map.put("id",id);
		map.put("title", sc.getTitle());
		map.put("price", sc.getPrice());
		map.put("abstracts", sc.getAbstracts());
		map.put("description", sc.getDescription());
		return "modify_page";
	}
	
	@RequestMapping("/modify_photo")
	@ResponseBody
	public String modify_photo(HttpServletRequest request) throws IOException
	{
		
		String upload_way=request.getParameter("upload_way");
		String id=request.getParameter("id");
		String title=request.getParameter("title");
		String abstracts=request.getParameter("abstracts");
		String description=request.getParameter("description");
		String price=request.getParameter("price");
		
		byte[] data=null;
		Map<String,Object> map=new HashMap<String,Object>();
		if(upload_way.equals("url_upload"))
		{
			String photo_url=request.getParameter("photo_url");
			if(!photo_url.isEmpty())
			{
				URL url=new URL(photo_url);
				DataInputStream dataInputStream = new DataInputStream(url.openStream());
				ByteArrayOutputStream output = new ByteArrayOutputStream();  
				  
	            byte[] buffer = new byte[1024];  
	            int length;  
	  
	            while ((length = dataInputStream.read(buffer)) > 0) {  
	                output.write(buffer, 0, length);  
	            } 
	            data=output.toByteArray();
			}
			
		}
		else if(upload_way.equals("local_upload"))
		{
			MultipartHttpServletRequest multipartrequest=(MultipartHttpServletRequest)request;
			MultipartFile photo=multipartrequest.getFile("photo");
			if(photo!=null&&!photo.isEmpty())
			{
				data=photo.getBytes();
			}
			
		}
	
		
		if(title.isEmpty())
		{
			map.put("res", "title");
		}
		else if(abstracts.isEmpty())
		{
			map.put("res", "abstracts");
			
		}else if(description.isEmpty())
		{
			map.put("res", "description");
		}
		else if(price.isEmpty())
		{
			map.put("res", "price");
		}
		else
		{
			int m_id=Integer.parseInt(id);
			double m_price=Double.parseDouble(price);
			Seller_Contents sc=new Seller_Contents();
			if(data==null)
			{
				Seller_Contents original_sc=seller_service.getSellerById(m_id);
				sc.setPhoto(original_sc.getPhoto());	
			}
			else
			{
				sc.setPhoto(data);
			}
			sc.setId(m_id);
			sc.setTitle(title);
			sc.setDescription(description);
			sc.setAbstracts(abstracts);
			sc.setPrice(m_price);
			
			buyer_service.updateFromSeller(sc);
			
			int res=seller_service.updateSellerContents(sc);
			if(res==1)
			{
				System.out.println("insert Photo Ok!!!!");
				map.put("res", "OK");
			}
			else
			{
				System.out.println("insert photo failed!!!");
				map.put("res", "failed");
			}
		}
		JSONObject jsonObject=JSONObject.fromObject(map);
	    return jsonObject.toString();
	}
}
