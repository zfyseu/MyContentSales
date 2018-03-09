<%@ page contentType="text/html; charset=gbk" %>

<html>
<head>
</head>
<body>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String j_account=(String)session.getAttribute("account");
	int id=(Integer)request.getAttribute("id");
%>

	<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
	<script type="text/javascript">
		function btn_visitor()
		{
			var res=confirm("Please login in");
			if(res==true)
			{
				window.location.assign("/MyContentSales/login"); 	
			}
		}
		
		function btn_buyer()
		{
			var url="/MyContentSales/shopping_cart";
			var js_amount=$('input[name=amount]').val();
			var js_id=${id};
			
			if(js_amount<1)
			{
				alert("count must be bigger than 0!");
				return false;
			}
			
			var res=confirm("Are you sure to buy?");
			if(res==true)
			{
				$.ajax({
	        		 type:'post',
	        		 url:'/MyContentSales/buy',
	        		 dataType:'json',
	        		 data:
	        			 {
	        			 	'id':js_id,
	        			 	'amount':js_amount
	        			 },
	                 success: function(data) { // data 保存提交后返回的数据，一般为 json 数据  
	                     var obj = eval(data);    
	                     if(obj.res=="OK")  {  
	                        window.location.assign(url);  
	                     }   
	                     else {  
	                        alert("failed to buy!!");                      
	                     }
	                 }  
	             });
					return false;
			}
		}	
	</script>
<%
	if(j_account!=null&&j_account.equals("buyer"))
	{
		out.write("<div style=\"float:left;\">"+
				"Hello,buyer! <a href=\"/MyContentSales/exit\">login out</a></div>"+
				" <div style=\"float:right;\">"+
				"<a href=\"/MyContentSales\">HomePage</a> &nbsp;<a href=\"/MyContentSales/shopping_cart\">ShoppingCart</a>"+
				"&nbsp;<a href=\"/MyContentSales/look_purchased\">Purchased</a></div><br><br>");
	}
	else if(j_account!=null&&j_account.equals("seller"))
	{
		out.write(" <div style=\"float:left;\">"+
				"Hello,seller! <a href=\"/MyContentSales/exit\">login out</a></div>"+
				" <div style=\"float:right;\">"+
				"<a href=\"/MyContentSales\">HomePage</a> <a href=\"/MyContentSales/post_content\">Post</a></div><br><br>");
	}
	else
	{

		out.write("<div style=\"float:left;\">"+
					"Please <a href=\"/MyContentSales/login\">login in</a></div>"+
					"<div style=\"float:right;\"> <a href=\"/MyContentSales\">HomePage</a></div><br><br>");
	}
%>

	<img src="/MyContentSales/look_seller_contents?id=${id}" alt="${title}">
   <h3>${title}  ￥ ${price}</h3>
   <h3>${abstracts}</h3>
   <%
   		if(j_account!=null&&j_account.equals("seller"))
   		{
   			out.write("<h3>已出售数量："+request.getAttribute("send_count")+"</h3>"+
					"<a href=\"/MyContentSales/modify?id="+id+"\">编辑</a>");
   		}
   		else 
   		{
   			if(j_account==null||j_account.equals("visitor"))
   			{
   	   			out.write("<form id=\"form\" action=\"/MyContentSales/login\" method=\"post\">");
   	   			
   	   		out.write("<input type=\"button\" value=\"+\" onClick=\"javascript:this.form.amount.value++;\">"+
 	   		          "<input type=\"text\" name=\"amount\" value=\"0\" style=\"width:50px\">"+
 					  "<input type=\"button\" value=\"-\" onClick=\"javascript:this.form.amount.value--;\">"+
 	   		          "<input id=\"button\" type=\"button\" onclick=\"btn_visitor()\" value=\"buy\">"+
 					  "<input type=\"hidden\" name=\"id\" value="+id+"></form>");
   	   		}
   			else if(j_account.equals("buyer"))
   	   		{
   	   			out.write("<form id=\"form\" action=\"/MyContentSales/buy\" method=\"post\">");
   	   			
   	   		out.write("<input type=\"button\" value=\"+\" onClick=\"javascript:this.form.amount.value++;\">"+
 	   		          "<input type=\"text\" name=\"amount\" value=\"0\" style=\"width:50px\">"+
 					  "<input type=\"button\" value=\"-\" onClick=\"javascript:this.form.amount.value--;\">"+
 	   		          "<input id=\"button\" type=\"button\" onclick=\"btn_buyer()\" value=\"buy\">"+
 					  "<input type=\"hidden\" name=\"id\" value="+id+"></form>");
   	   		}
   		}
	%>
    <h3>详细信息</h3>
   <HR SIZE=5>
   <h3>${description}</h3>
</body>
</html>