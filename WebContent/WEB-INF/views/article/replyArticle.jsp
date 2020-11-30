<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
	integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
	integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
	crossorigin="anonymous"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@500&display=swap" rel="stylesheet"> -->
<title>文章回覆</title>
<style>
h1 {
	font-family: 'Noto Sans TC', sans-serif
}

.fixed0 {
	position: fixed;
	bottom: 15%;
	right: 2%;
}
</style>
</head>
<body>
	<jsp:include page="../public/top.jsp" />
	<%-- <jsp:include page="../nn/top.jsp" /> --%>

	<main role="main" class="container">

		<div class="starter-template">
			<h1>文章回覆</h1>
			<p class="lead">
				Share your story.<br> ...
			</p>
		</div>

	</main>
	<!-- /.container -->
	
	
	
	<form:form action="replyToDB" method="POST" modelAttribute="forums">
		<br>
回覆內容:<form:textarea path="content" />
		<br>
		<form:hidden value="1" path="voteid" />
<%-- 		<form:hidden value="${article.getId()}" path="aricle.id" /> --%>
<%-- 		<form:hidden value="${forum.getArticle().getId()}" path="forums.aricle.id" /> --%>
<%--        <input type="hidden" name="id" value="${forums.getArticle().getId()}"> --%>
       <input type="hidden" name="id" value="${article.getId()}">
                                
		<form:button value="submit" type="submit">送出</form:button>
	</form:form>
	
	<a href="<c:url value='backArticle' />" class="fixed0"><button
			type="button" class="btn btn-success">回討論版</button></a>
</body>
</html>