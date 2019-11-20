<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/tld.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>reset password</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="favicon.ico"> 
	<link href="${basePath}h/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="${basePath}h/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="${basePath}h/css/animate.min.css" rel="stylesheet">
    <link href="${basePath}h/css/style.min862f.css?v=4.1.0" rel="stylesheet">

  </head>
  
  <body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>重置密码</h5>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="commentForm">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">账户：</label>
                                <div class="col-sm-8">
                                    <input id="loginName" name="loginName" minlength="2" type="text" class="form-control" placeholder="输入您用于登陆的账户" required="" aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">E-mail：</label>
                                <div class="col-sm-8">
                                    <input id="email" type="email" class="form-control" name="email" placeholder="输入您的常用邮箱" required="" aria-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                               
                            </div>
                            <div class="form-group">
                               
                            </div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-3">
                                    <button class="btn btn-primary" id="resetBtn">重置</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <p class="m-t">提示:账户号是您曾经正常登陆过系统的账户</p>
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮箱用于接受您的新密码,务必保密!!!</p>
                    </div>
                </div>
            </div>
           
        </div>
    </div>
    <script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
    <script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${basePath}h/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${basePath}h/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${basePath}h/js/demo/form-validate-demo.min.js"></script>
    <script  src="${basePath}h/js/login.js" ></script>
</body>
</html>
