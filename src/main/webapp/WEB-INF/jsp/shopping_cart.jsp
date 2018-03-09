<%@ page contentType="text/html; charset=gbk" %>

<html>

<head>
</head>

<body>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
	<script type="text/javascript">
		
		function exit()
		{
			window.history.go(-1);
		}
		
		function buy()
		{
			window.location.href="purchase";
		}
		
		
	
	</script>



	<div style="float:left;">Hello,buyer! <a href="/MyContentSales/exit">login out</a></div>
	<div style="float:right;"><a href="/MyContentSales">HomePage</a>  <a href="/MyContentSales/shopping_cart">ShoppingCart</a>  <a href="/MyContentSales/look_purchased">Purchased</a></div>
	<br><br>
	<h4>Contents in shopping cart</h4>
	<HR SIZE=5>
	<table id="tb" border=1 align="center" frame=void>
	<tr>
		<th>Picture</th>
		<th style="width:100px">Title</th>
		<th style="width:500px">Count</th>
		<th style="width:100px">Price</th>
	</tr>
	<%
		int[] ids=(int[])request.getAttribute("ids");
		String[] titles=(String[])request.getAttribute("titles");
		int[] buy_counts=(int[])request.getAttribute("buy_counts");
		double[] prices=(double[])request.getAttribute("prices");
		double totalPrice=0.0;
		for(int i=0;i<ids.length;i++)
		{
			totalPrice+=buy_counts[i]*prices[i];
			out.write("<tr>");
			out.write("<td align=\"center\" valign=\"middle\"><img width=\"25%\" src=\"/MyContentSales/look_buyer_cart?id="+ids[i]+"\"></td>");
			out.write("<td>"+titles[i]+"</td>");
			out.write("<td>"+buy_counts[i]+"</td>");
			out.write("<td>гд"+prices[i]+"</td>");
			out.write("</tr>");
		}
		out.write("<h3>Total Price:гд"+totalPrice+"</h3>");
	%>
	</table>
	
	<br><br>
	<div align="center"><button id="button_buy" type="button" onclick="buy()">buy</button> &nbsp;&nbsp;<button id="button_exit" type="button" onclick="exit()">exit</button></div>
	
	
</body>
</html>