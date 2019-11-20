<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>404</title>
<meta name="keywords" content="">
<meta name="description" content="">
<link rel="shortcut icon" href="favicon.ico">
<link href="<%=basePath%>h/css/bootstrap.min.css?v=3.3.6"
	rel="stylesheet">
<link href="<%=basePath%>h/css/font-awesome.css?v=4.4.0"
	rel="stylesheet">
<link href="<%=basePath%>h/css/animate.css" rel="stylesheet">
<link href="<%=basePath%>h/css/style.css?v=4.1.0" rel="stylesheet">
</head>

<body class="gray-bg">
	<div class="middle-box text-center animated fadeInDown">
		<h3 class="font-bold">页面未找到！</h3>
		<div class="error-desc">
			<h1></h1>
			<font color="#666666"> 当前页面暂时不可用。 </font>
			<hr noshade size=0>
			<p>
				<font color="#666666">☉ 请尝试以下操作：</font>
			</p>
			<ul style="text-align: left">
				<li><font color="#666666">确保浏览器的地址栏中显示的网站地址的拼写和格式正确无误。 </font>
				</li>
				<li><font color="#666666">如果通过单击链接而到达了该网页，请与网站管理员联系，通知他们该链接的格式不正确。</font>
				</li>
				<li><font color="#666666">单击</font><a
					href="javascript:history.back(1)"><font color=#0000ff>后退</font>
				</a><font color="#666666">按钮尝试另一个链接。 </font></li>
				</li>
			</ul>
			<hr noshade size=0>
			<p>
				<font color="#666666">☉ 如果您对本站有任何疑问、意见、建议、咨询，请联系管理员<br>
				</font> <br>
		</div>
	</div>
	<!-- 全局js -->
	<script src="<%=basePath%>h/js/jquery.min.js?v=2.1.4"></script>
	<script src="<%=basePath%>h/js/bootstrap.min.js?v=3.3.6"></script>
</body>

</html>
