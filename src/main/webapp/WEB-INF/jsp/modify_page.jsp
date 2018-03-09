<%@ page contentType="text/html; charset=gbk" %>

<html>
<head>
</head>
<body>
	<%  
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    
    out.write(" <div style=\"float:left;\">"+
			"Hello,seller! <a href=\"/MyContentSales/exit\">login out</a></div>"+
			" <div style=\"float:right;\">"+
			"<a href=\"/MyContentSales\">HomePage</a> <a href=\"/MyContentSales/post_content\">Post</a></div><br><br>");
     %>  
     
     <script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
     <script type="text/javascript" src="<%=basePath%>/js/jquery.form.js"></script> 
	 <script type="text/javascript" src="<%=basePath%>js/md5.js"></script>
	
	
	 <script type="text/javascript">
	 
	 function exit()
	 {
		 window.history.go(-1);
	 }
	 
		 
	 $(document).ready(function(){
		 
		 $("#photo").change(function()
					{
						 console.log("change!");
						 var objUrl=getObjectURL(this.files[0]);
						 console.log(objUrl);
						 $('img').attr("src",objUrl);
					});
		 			
		 
					$('input:radio[name=upload_way]').change(function()
					{
						var upload_way=document.getElementById("form_modify").upload_way;
						if(upload_way[1].checked)
						{
							document.getElementById("upload_photo").innerHTML="<p id=\"upload_photo\">photo url:<input type=\"text\" id=\"photo_url\" name=\"photo_url\"></p>";
							$("#photo_url").change(function()
									{
										 console.log("change url!");
										 var photo_url=$('#photo_url').val();
										 console.log(photo_url);
										 $('img').attr("src",photo_url);
									});
						}
						else
						{
							document.getElementById("upload_photo").innerHTML="<p id=\"upload_photo\">choose photo:<input type=\"file\" id=\"photo\" name=\"photo\"></p>";
							$("#photo").change(function()
									{
										 console.log("change!");
										 var objUrl=getObjectURL(this.files[0]);
										 console.log(objUrl);
										 $('img').attr("src",objUrl);
									});
						}
					});
		 
	      $('#form_modify').bind('submit', function(){
	    	  
	    	  var id=${id};
	    	  
			$(this).ajaxSubmit(
					{ 
					  type: 'post', // 提交方式 get/post 
					  url: '/MyContentSales/modify_photo', // 需要提交的 url
		 			  dataType : "json", //数据类型
	         		  success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
	            				// 此处可对 data 作相关处理
	            				var obj=eval(data);
	            				if(obj.res==("OK"))
	            				{
	            					window.location.href='/MyContentSales/content?id='+id;
	            				}
	            				else if(obj.res=="photo")
	            				{
	            					alert("photo can not be null!!!");
	            				}
	            				else if(obj.res=='title')
	            				{
	            					alert("title can not be null!!!");
	            					
	            				}
	            				else if(obj.res=="abstracts")
	            				{
	            					alert("abstracts can not be null!!!");
	            					
	            				}
	            				else if(obj.res=="description")
	            				{
	            					alert("description can not be null!!!");
	            					
	            				}
	            				else if(obj.res=="price")
	            				{
	            					alert("price can not be null!!!");
	            				
	            				}
	            				else
	            				{
	            					alert("modify failed!!");	
	            				}
	         		}
	         //$(this).resetForm(); // 提交后重置表单
	   				});
	      	return false; // 阻止表单自动提交事件
	   });
	});
	
	 </script>


	 <form id="form_modify" action="" method="post" enctype="multipart/form-data" style="text-align:center">
			<input type="hidden" name="id" value="${id}">
			<p><input type="radio" name="upload_way" id="url" value="local_upload" checked="checked"/>local upload &nbsp;
			<input type="radio" name="upload_way" id="local" value="url_upload"/>url upload</p>
			<p id="upload_photo">choose photo:<input type="file" id="photo" name="photo"></p>
  			<p>preview of photo</p>
			<p><img src="/MyContentSales/look_seller_contents?id=${id}" id="img"  width="20%"></p>
  			<p>Title: <input type="text" name="title" value="${title}" style="width:500px"/></p>
  			<p>Abstract: <input type="text" name="abstracts" value="${abstracts}" style="width:500px"/></p>
  			<p>Description:<input type="text" name="description" value="${description}" style="height:300px;width:500px"/></p>
  			<p>price:<input type="text" name="price" value="${price}"/></p>
  			<input type="submit" value="modify" />&nbsp;<button id="button" type="button" onclick="exit()">exit</button>
	</form>
</body>
</html>