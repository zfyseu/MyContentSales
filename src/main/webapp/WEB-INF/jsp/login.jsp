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
     <script type="text/javascript" src="<%=basePath%>/js/jquery.form.js"></script> 
	 <script type="text/javascript" src="<%=basePath%>js/md5.js"></script>
	 
	 <script type="text/javascript">
    	  function myfun()
    	  {
    		  var url="/MyContentSales"
    		  var js_account=$('input[name=account]').val();
    		  var js_password=$('input[name=password]').val();
    		  var js_password_md5=$.md5(js_password);
    	
        	 $.ajax({
        		 type:'post',
        		 url:'/MyContentSales/verify_password',
        		 dataType:'json',
        		 data:
        			 {
        			 	'account':js_account,
        			 	'password_md5':js_password_md5
        			 },
                 success: function(data) { // data 保存提交后返回的数据，一般为 json 数据  
                     var obj = eval(data);    
                     if(obj.res=="OK")  {  
                        window.location.assign(url);  
                     }   
                     else {  
                        alert("登录失败,请重试!");                       
                     }
                 }  
             });
				return false;
    	  }
	 </script>
	 
	 <a href="/MyContentSales">Home Page</a>
	 <br>
	 
	 <form id="formLogin" name="formLogin" action="##" method="post" align="center">
  			<p>用户名: <input type="text" id="account" name="account" /></p>
  			<p>密码: <input type="password" id="password" name="password" /></p>
			<button id="button" type="button" onclick="myfun()">登录</button>
	</form>
 
</body>
</html>