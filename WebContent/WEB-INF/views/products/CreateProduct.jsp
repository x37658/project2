<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Product</title>
<script type="text/javascript" src="<c:url value='/js/jquery-1.12.2.min.js' />" ></script>
<script>
window.onload = function() {
	getColors();
	getCategories();
	getAnimalTypes();
}

function getCategories(){
	var xhr = new XMLHttpRequest();
	xhr.open("GET", "<c:url value='/product/getCategories' />", true);
	xhr.send();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var content = "選擇分類：<select name='categoryId'>";
			content += "<option value='' disabled selected='selected'>請選擇分類</option>"
			var categories = JSON.parse(xhr.responseText);
			for(var i=0; i < categories.length; i++){
			    content += 	"<option value='" + categories[i].id + "'>" + categories[i].name + "</option>";
			}
			content += "</select>"+"<span style='color: red;'>${errors.category}</span>" ;
			var divs = document.getElementById("somedivS");
			divs.innerHTML += content;
			divs.innerHTML += "<br/>";
		}
	}
}

function getColors(){
	var xhr = new XMLHttpRequest();
	xhr.open("GET", "<c:url value='/product/getColors' />", true);
	xhr.send();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var content = "商品顏色：<select name='colorId'>";
			content += "<option value='' disabled selected='selected'>請選擇顏色</option>"
			var colors = JSON.parse(xhr.responseText);
			for(var i=0; i < colors.length; i++){
			    content += 	"<option value='" + colors[i].id + "'>" + colors[i].name + "</option>";
			}
			content += "</select>"+"<span style='color: red;'>${errors.color}</span>" ;
			var divs = document.getElementById("somedivS");
			divs.innerHTML += content;
			divs.innerHTML += "<br/>";
		}
	}
}


function getAnimalTypes(){
	var xhr = new XMLHttpRequest();
	xhr.open("GET", "<c:url value='/product/getAnimalTypes' />", true);
	xhr.send();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var content = "寵物類別：<select name='animalTypeId'>";
			content += "<option value='' disabled selected='selected'>請選擇寵物類別</option>"
			var animalTypes = JSON.parse(xhr.responseText);
			for(var i=0; i < animalTypes.length; i++){
			    content += 	"<option value='" + animalTypes[i].id + "'>" + animalTypes[i].name + "</option>";
			}
			content += "</select>"+"<span style='color: red;'>${errors.animalType}</span>";
			var divs = document.getElementById("somedivS");
			divs.innerHTML += content;
			divs.innerHTML += "<br/>";
		}
	}
}





//  上傳房產證照片
$("#houseFile").fileupload({
    url: "/userRoom/houseImages",
    dataType: 'json',
    formData: {"userRoomId":$("#id").val()},
    add: function (e, data) {
        data.submit();
    },
    done: function (e, data) {
        var result = data.result;
        var image = result.data;
        var imgUrl = image.url;
        var  imgId=image.id;
        console.info("圖片的編號"+imgId);
        if (result.success == false) {
            alert("上傳失敗");
        } else {
            var div = $("<div class='show-fc' id='divHouse"+imgId+"'>");
            var img = $("<img  src='/sysImage/showImg?src="+imgUrl+"'class='thumbnail uploaded-image' />");
            var imgx = $("<img class='del-image' id="+imgId+"  onclick='deleteImage(this.id)' src='/images/deleteLog/deleteImg.png' >");
            div.append(img).append(imgx);
            $("#houseImages").append(div);
        }
    },
    progressall: function (e, data) {
    }
});
</script>
</head>
<body>
<jsp:include page="../public/top.jsp" />
	<form:form method="POST" action="${pageContext.servletContext.contextPath}/product/processCreateProduct.controller" modelAttribute="products" enctype="multipart/form-data"  >
		商品名稱:<form:input path="name"/><span style="color: red;">${errors.name}</span><br/>
		商品價格:<form:input path="price"/><span style="color: red;">${errors.price}</span><br/>
		商品折扣:<form:input path="discount"/><span style="color: red;">${errors.discount}</span><br/>
		商品圖片:<form:input path="multipartFile" type="file" /><span style="color: red;">${errors.multipartFile}</span><br/>
		商品描述:<form:input path="description"/><span style="color: red;">${errors.description}</span><br/>
		商品數量:<form:input path="quantity"/><span style="color: red;">${errors.quantity}</span><br/>
		商品狀態:上架中<form:radiobutton path="status" value="上架中"/>
          	   已下架<form:radiobutton path="status" value="已下架"/><span style="color: red;">${errors.status}</span><br/>
        <div id='somedivS'></div>
         ${errors.color}<br/>${errors.category}<br/>${errors.animalType}<br/> 
        <form:button value="Send">Submit</form:button>
		
	</form:form>
	
<%-- <jsp:include page="../public/top.jsp" /> --%>
<%-- 	<form method="POST" action="${pageContext.servletContext.contextPath}/product/processCreateProduct.controller" modelAttribute="products" enctype="multipart/form-data"  > --%>
<%-- 		商品名稱:<input name="name"/><span style="color: red;">${errors.name}</span><br/> --%>
<%-- 		商品價格:<input name="price"/><span style="color: red;">${errors.price}</span><br/> --%>
<%-- 		商品折扣:<input name="discount"/><span style="color: red;">${errors.discount}</span><br/> --%>
<%-- 		商品封面圖片:<input name="coverImage" type="file" /><span style="color: red;">${errors.multipartFile}</span><br/> --%>
<%-- 		商品內容圖片:<input name="contentImage" type="file" multiple/><span style="color: red;">${errors.multipartFile}</span><br/> --%>
<%-- 		商品描述:<input name="description"/><span style="color: red;">${errors.description}</span><br/> --%>
<%-- 		商品數量:<input name="quantity"/><span style="color: red;">${errors.quantity}</span><br/> --%>
<!-- 		商品狀態:上架中<input type="radio"  name="status" value="上架中"/> -->
<%--           	   已下架<input type="radio" name="status" value="已下架"/><span style="color: red;">${errors.status}</span><br/> --%>
<!--         <div id='somedivS'></div> -->
<%-- <%--         ${errors.color}<br/>${errors.category}<br/>${errors.animalType}<br/> --%> --%>
<!--         <button value="Send">Submit</button>		 -->
<%-- 	</form>	 --%>

</body>
</html>