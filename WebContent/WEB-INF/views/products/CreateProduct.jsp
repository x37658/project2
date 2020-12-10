<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增商品</title>
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
			var content = "選擇分類：<select id='select1' name='categoryId' style='background-color:RGB(236,239,248);height:50px;'>";
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
			var content = "商品顏色：<select id='select2' name='colorId' style='background-color:RGB(236,239,248);height:50px;'>";
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
			var content = "寵物類別：<select id='select3' name='animalTypeId' style='background-color:RGB(236,239,248);height:50px;'>";
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

</script>
<jsp:include page="../fragments/links.jsp" />
<style>
.btncls{
	background-color: #7E4C4F; /* Green */
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 12px;
    border-radius: 10px;
    transition-duration: 0.3s;
    cursor: pointer;
}
button.btncls:hover{
	background-color: #000000;
}
</style>
</head>
<body>
<jsp:include page="../fragments/headerArea.jsp" />

<%-- <jsp:include page="../public/top.jsp" /> --%>
<div class="container">
	<form:form  method="POST" action="${pageContext.servletContext.contextPath}/product/processCreateProduct.controller" modelAttribute="products" enctype="multipart/form-data"  >
		<b>
		商品名稱:<form:input id="input1" path="name"/><span style="color: red;">${errors.name}</span><br/>
		商品價格:<form:input id="input2" path="price"/><span style="color: red;">${errors.price}</span><br/>
		商品折扣:<form:input id="input3" path="discount"/><span style="color: red;">${errors.discount}</span><br/>
		商品圖片:<form:input id="input4" path="multipartFile" type="file" /><span style="color: red;">${errors.multipartFile}</span><br/>
		商品內容圖片:<input  name="contentImage" type="file" multiple/><span style="color: red;">${errors.multipartFile}</span><br/>
		商品描述:<form:input id="input5" path="description"/><span style="color: red;">${errors.description}</span><br/>
		商品數量:<form:input id="input6" path="quantity"/><span style="color: red;">${errors.quantity}</span><br/>
		商品狀態:<br/>	
			 <form:radiobutton style="width:20px;height:20px;" path="status" value="上架中"/>上架中
          	 <form:radiobutton style="width:20px;height:20px;" path="status" value="已下架"/>  已下架 <span style="color: red;">${errors.status}</span><br/>
        <div id='somedivS'></div>
         ${errors.color}<br/>${errors.category}<br/>${errors.animalType}<br/> 
		</b>
        <form:button value="Send" class="btncls">送出</form:button>
	</form:form>
</div>	
<%-- <jsp:include page="../public/top.jsp" /> --%>
<%-- 	<form method="POST" action="${pageContext.servletContext.contextPath}/product/processCreateProduct.controller" modelAttribute="products" enctype="multipart/form-data"  > --%>
<%-- 		商品名稱:<input name="name"/><span style="color: red;">${errors.name}</span><br/> --%>
<%-- 		商品價格:<input name="price"/><span style="color: red;">${errors.price}</span><br/> --%>
<%-- 		商品折扣:<input name="discount"/><span style="color: red;">${errors.discount}</span><br/> --%>
<%-- 		商品封面圖片:<input name="coverImage" type="file" /><span style="color: red;">${errors.multipartFile}</span><br/> --%>
<%-- 		商品描述:<input name="description"/><span style="color: red;">${errors.description}</span><br/> --%>
<%-- 		商品數量:<input name="quantity"/><span style="color: red;">${errors.quantity}</span><br/> --%>
<!-- 		商品狀態:上架中<input type="radio"  name="status" value="上架中"/> -->
<%--           	   已下架<input type="radio" name="status" value="已下架"/><span style="color: red;">${errors.status}</span><br/> --%>
<!--         <div id='somedivS'></div> -->
<%-- <%--         ${errors.color}<br/>${errors.category}<br/>${errors.animalType}<br/> --%> 
<!--         <button value="Send">Submit</button>		 -->
<%-- 	</form>	 --%>

<div class="divFixed btn-style1 pointerCursor" id="createInput">一鍵輸入</div>

<jsp:include page="../fragments/footerArea.jsp" />
<jsp:include page="../fragments/allJs.jsp" />
</body>
<script >
//一鍵輸入(開放)
$('#createInput').click(function() {
	$('#input1').val("米格魯");
	$('#input2').val("米格魯");
	$('#input3').val("W081204-12");
	$('#input4').val("花");
	$('#input5').val("很愛吃、有點邊緣狗個性");
	$('#input6').val("很愛吃、有點邊緣狗個性");
	$('#select1')[0].selectedIndex = 1;
	$('#select2')[0].selectedIndex = 1;
	$('#select3')[0].selectedIndex = 1;

});

</script>


</html>