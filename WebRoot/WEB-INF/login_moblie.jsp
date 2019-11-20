<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/tld.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>检验检测服务云平台</title>
    <link href="${basePath}h/css/bootstrap.min.css" rel="stylesheet">
    <link href="${basePath}h/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${basePath}h/css/animate.css" rel="stylesheet">
    <link href="${basePath}h/css/style.css" rel="stylesheet">
    <link href="${basePath}h/css/myLogin.css" rel="stylesheet">
    <script src="${basePath}h/js/jquery.min.js" ></script>
    <script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>
        if(window.top!==window.self){window.top.location=window.location};
    </script>
	
</head>


<body class="signin">
    <div class="signinpanel" id="loginsig">
        <div class="row" style="margin-top: 10%">
            <div class="col-sm-12" style="display: flex;justify-content: center;align-items: center;padding: 0px;">
                <div  style="width: 260px">
                	<form method="post" action="login.do" id="loginform">
	                	<c:if test="${fn:length(message)>0 }">
	                    <p class="m-t-md" style="color: red;">${message}</p> 
	                    </c:if>
	                    <h4 class="no-margins">请登录</h4>
	                    <input type="text" class="form-control uname" name="loginname" id="loginName" placeholder="输入账号" />
	                    <input type="password" class="form-control pword m-b" name="password" id="password" placeholder="密码" />
	                    <!-- <i onclick="alert('请联系管理员进行修改');">忘记密码了？</i> -->
	                    <button class="btn btn-success btn-block" type="submit">登录</button>
	                </form>
                </div>
            </div>
        </div>
        <div class="signup-footer">
            <div>
               	 技术支持© 2014-2018 <a href="http://116.62.212.62:80" class="servicelink" target="_blank">得淼科技</a> 021-50835869
            </div>
        </div>
    </div>
</body>
</html>
