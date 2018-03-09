<%@ page contentType="text/html; charset=gbk" %>
<%@ page import="java.util.*"%> 
<html>
<style>
.buttonGreen {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 13px 30px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    margin: 4px 2px;
    cursor: pointer;
}

.buttonBlue {
    background-color: #008CBA;
    border: none;
    color: white;
    padding: 13px 30px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    margin: 4px 2px;
    cursor: pointer;
}
</style>
<head></head>
<body>
<%
Integer num=(Integer)request.getAttribute("num");
String[] j_titles=(String[])request.getAttribute("titles");
int[] j_ids=(int[])request.getAttribute("ids");
List<Integer> j_send_ids=(List<Integer>)request.getAttribute("send_ids");
double[] j_prices=(double[])request.getAttribute("prices");

String j_account=(String)session.getAttribute("account");

HashSet<Integer> send_idset=new HashSet<Integer>();
if(j_send_ids!=null)
{
	for(int i=0;i<j_send_ids.size();i++)
	{
		send_idset.add(j_send_ids.get(i));
	}
}



if(j_account!=null&&j_account.equals("seller"))
{
	out.write(" <div style=\"float:left;\">"+
				"Hello,seller! <a href=\"/MyContentSales/exit\">login out</a></div>"+
				" <div style=\"float:right;\">"+
				"<a href=\"/MyContentSales\">HomePage</a> <a href=\"/MyContentSales/post_content\">Post</a></div><br><br>");


	out.write("<h3>All Contents</h3>");
	out.write("<HR size=5>");
	
	for(int i=0;i<num;i++)
	{
		
		if(i==0) out.write("<table align=\"center\"  rules=\"all\" border=\"0.5\" width=\"85%\">");
		if(i%4==0) out.write("<tr>");
		out.write("<td  align=\"center\">");
	 	if(send_idset.contains(j_ids[i]))
	 	{
	 		out.write("<span style=\"font-family:Microsoft YaHei;font-size:10px;\">"+
	 		"<div style=\"position: relative;\">");
	 		out.write("<a href=\"/MyContentSales/content?id="+j_ids[i]+"\"><img width=\"150px\" src=\"/MyContentSales/look_seller_contents?id="+j_ids[i]+"\" alt="+j_titles[i]+"><br><h3>"+j_titles[i]+"  ￥"+j_prices[i]+"</h3></a>");
	 		out.write("<span style=\"position: absolute; top: 0; left: 2;\">已售出</span></div>");
	 	}
	 	else
	 	{
	 		out.write("<a href=\"/MyContentSales/content?id="+j_ids[i]+"\"><img width=\"150px\" src=\"/MyContentSales/look_seller_contents?id="+j_ids[i]+"\" alt="+j_titles[i]+"><br><h3>"+j_titles[i]+"  ￥"+j_prices[i]+"&nbsp;<a href=\"/MyContentSales/delete?id="+j_ids[i]+"\">下架</a></h3></a>");
	 		
	 		}
		out.write("</td>");
	 	if(i%4==3) out.write("</tr>");
	 	if(i==num-1)out.write("</table>");
	}
}
else if(j_account!=null&&j_account.equals("buyer"))
{
	out.write("<div style=\"float:left;\">"+
			"Hello,buyer! <a href=\"/MyContentSales/exit\">login out</a></div>"+
			" <div style=\"float:right;\">"+
			"<a href=\"/MyContentSales\">HomePage</a> <a href=\"/MyContentSales/shopping_cart\">ShoppingCart</a>"+
			" <a href=\"/MyContentSales/look_purchased\">Purchased</a></div><br><br>");
	
	out.write("<a href=\"/MyContentSales\" id=\"button_green\" class=\"buttonGreen\">全部商品</a>");
	out.write("<a href=\"/MyContentSales?x=nobuy\" id=\"button_blue\" class=\"buttonBlue\">未购买商品</a>");
	out.write("<HR size=5>");
	
	for(int i=0;i<num;i++)
	{
		if(i==0) out.write("<table align=\"center\"  rules=\"all\" border=\"0.5\" width=\"85%\">");
		if(i%4==0) out.write("<tr>");
		out.write("<td  align=\"center\">");
		if(send_idset.contains(j_ids[i]))
	 	{
	 		out.write("<span style=\"font-family:Microsoft YaHei;font-size:10px;\">"+
	 		"<div style=\"position: relative;\">");
	 	}
	 	out.write("<a href=\"/MyContentSales/content?id="+j_ids[i]+"\"><img width=\"150px\" src=\"/MyContentSales/look_seller_contents?id="+j_ids[i]+"\" alt="+j_titles[i]+"><br><h3>"+j_titles[i]+"  ￥"+j_prices[i]+"</h3></a>");
	 	if(send_idset.contains(j_ids[i]))
	 	{
	 		out.write("<span style=\"position: absolute; top: 0; left: 2;\">已购买</span></div>");
	 	}
	 	out.write("</td>");
	 	if(i%4==3) out.write("</tr>");
	 	if(i==num-1)out.write("</table>");
	}
}
else
{
	out.write("<div style=\"float:left;\">"+
				"Please <a href=\"/MyContentSales/login\">login in</a></div>"+
				"<div style=\"float:right;\"> <a href=\"/MyContentSales\">HomePage</a></div><br><br>");
	
	for(int i=0;i<num;i++)
	{
		if(i==0) out.write("<table align=\"center\"  rules=\"all\" border=\"0.5\" width=\"85%\">");
		if(i%4==0) out.write("<tr>");
		out.write("<td  align=\"center\">");
	 	out.write("<a href=\"/MyContentSales/content?id="+j_ids[i]+"\"><img width=\"150px\" src=\"/MyContentSales/look_seller_contents?id="+j_ids[i]+"\" alt="+j_titles[i]+"><br><h3>"+j_titles[i]+"  ￥"+j_prices[i]+"</h3></a>");
	 	out.write("</td>");
	 	if(i%4==3) out.write("</tr>");
	 	if(i==num-1)out.write("</table>");
	}
	
}
%>


</body>
</html>