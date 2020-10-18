<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>查詢動物</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
  <script src="https://code.jquery.com/jquery-3.3.1.sactivelim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
<style>
.card2 {
	width: 250px;
	float: left;
	margin: 10px;
	padding: 5px;
}
.div1{
	margin:10px 0 0 10px;
}
.note1 {
	width: 90px;
	height: auto;
	word-wrap: break-word;
	word-break: break-all;
	overflow: hidden;
}
.page1 {
	margin: 0 auto;
	width: 1100px;
}
.span1 {
	display: inline-block;
	width: 120px;
	text-align: right;
}
</style>
</head>
<body>
	<jsp:include page="/nn/top.jsp" />
	<div class="page1">
		<div class="div1">
			<a href="<%=application.getContextPath()%>/wey/CreateAnimal.jsp" class="btn btn-primary">新增</a>
		</div>
<!-- card來源https://www.w3schools.com/bootstrap4/tryit.asp?filename=trybs_card_image&stacked=h -->
		<c:forEach var="AnimalsList" items="${AnimalsList}">
			<div class="card card2">
				<img class="card-img-top" src="" alt="Animal image">
				<div class="card-body">
					<span class="span1">動物編號：</span><span>${AnimalsList.animalId}</span><br>
					<span class="span1">會員編號：</span>${AnimalsList.memberId}<br>
					<span class="span1">收容動物編號：</span>${AnimalsList.acceptionId}<br>
					<span class="span1">品種編號：</span>${AnimalsList.breedId}<br>
					<c:choose>
						<c:when test="${AnimalsList.gender == 1}">
							<span class="span1">性別：</span>公<br>
						</c:when>
						<c:otherwise>
							<span class="span1">性別：</span>母<br>
						</c:otherwise>
					</c:choose>
					<span class="span1">毛色：</span>${AnimalsList.coatColor}<br>
					<c:choose>
						<c:when test="${AnimalsList.isAdoptionAvailable == 1}">
							<span class="span1">是否開放領養：</span>是<br>
						</c:when>
						<c:otherwise>
							<span class="span1">是否開放領養：</span>否<br>
						</c:otherwise>
					</c:choose>
					<span class="span1">備註：</span><span class="note1">${AnimalsList.note}</span><br>
<!-- 備註換行問題 -->
					<a href="ServletPreUpdateAnimal?animalId=${AnimalsList.animalId}" class="btn btn-secondary">維護</a>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>