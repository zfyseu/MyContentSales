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
		 
	 function getObjectURL(file)
	 {
		 var url=null;
		 if(window.createObjectURL!=undefined)
		{
			url=window.createObjectURL(file);	 
		}
		 else if(window.URL!=undefined)
		{
			url=window.URL.createObjectURL(file);	 
		}
		 else if(window.webkitURL!=undefined)
		{
			url=window.webkitURL.createObjectURL(file);	 
		}
		 return url;
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
						var upload_way=document.getElementById("form_savephoto").upload_way;
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
		 
	      $('#form_savephoto').bind('submit', function(){
			
	    	var photo= $('#photo').val();
	     	var title= $('#title').val();
	     	var abstracts= $('#abstracts').val();
	     	var description= $('#description').val();
	     	var price= $('#price').val();
	     	
	     	
	     	

			$(this).ajaxSubmit(
					{ type: 'post', // 提交方式 get/post 
					  url: '/MyContentSales/save_photo_test', // 需要提交的 url
		 			  dataType : "json", //数据类型
	         		  success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
	            				// 此处可对 data 作相关处理
	            				var obj=eval(data);
	            				if(obj.res==("OK"))
	            				{
	            					window.location.href='/MyContentSales';
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
	            				else if(obj.res=="upload_way")
	            				{
	            					alert("upload_way must be choosed!!!");
	            				}
	            				
	         		}
	         //$(this).resetForm(); // 提交后重置表单
	   				});
	      	return false; // 阻止表单自动提交事件
	   });
	});
	
	 </script>
	 <div style="float:left;">Hello,seller! <a href="/MyContentSales/exit">login out</a></div>
	 <div style="float:right;"><a href="/MyContentSales">Home_Page</a> <a href="/MyContentSales/post_content">Post</a></div>
	 <br><br>
	 <h3>Add new content</h3>
	 <HR SIZE=4>
	 
	 <form  id="form_savephoto" name="form_savephoto" action="" method="post" enctype="multipart/form-data" style="text-align:center">
	 		<p><input type="radio" name="upload_way" id="local" value="local_upload" checked="checked"/>local upload<input type="radio" name="upload_way" id="url" value="url_upload"/>url upload</p>
	 		<p id="upload_photo">choose photo:<input type="file" id="photo" name="photo"></p>
  			<p>preview of photo</p>
			<p><img src="" id="img"  width="20%"></p>
  			<p>Title: <input type="text" name="title" style="width:500px"/></p>
  			<p>Abstract: <input type="text" name="abstracts"  style="width:500px"/></p>
  			<p>Description:<input type="text" name="description"  style="height:300px;width:500px"/></p>
  			<p>price:<input type="text" name="price"/></p>
  			
  			<input type="submit" value="Submit" />
	</form>
	
</body>
</html>