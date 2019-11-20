<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<%@ include file="../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="middle-box text-center animated fadeInDown">
		<h1>500</h1>
		<h3 class="font-bold" onclick="$('.error-desc').toggle();">${msg }</h3>
		<div class="error-desc" style="display: none">
			<c:forEach items="${ex.stackTrace}" var="e">
				${e}
			</c:forEach>
		</div>
		<div class="error-desc">
			<a href="javascript:;" onclick="history.back()"
				class="btn btn-primary m-t">您可以返回看看</a>
		</div>
	</div>

	<!-- 全局js -->
	<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
	<script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
</body>
</html>
