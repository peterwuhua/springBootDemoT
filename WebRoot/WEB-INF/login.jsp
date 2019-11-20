<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/tld.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>实验室运营服务云平台</title>
    <link href="${basePath}h/css/bootstrap.min.css" rel="stylesheet">
    <link href="${basePath}h/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${basePath}h/css/animate.css" rel="stylesheet">
    <link href="${basePath}h/css/style.css" rel="stylesheet">
    <link href="${basePath}h/css/login.css" rel="stylesheet">
    <script src="${basePath}h/js/jquery.min.js" ></script>
    <script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>
        if(window.top!==window.self){window.top.location=window.location};
    </script>
	<style type="text/css">
	.ttt{
		 font-size: 24px;
		 font-family: 幼圆;
		 font-weight: bold;
	}
	a,a:active,a:visited{
		color: #fff;
	}
	a:hover{
		color: #23527c;
	}
	a>label{
		font-weight:normal;
	}
	</style>
</head>


<body class="signin">
    <div class="signinpanel" id="loginsig">
        <div class="row">
            <div class="col-sm-8">
                <div class="signin-info">
                    <div class="logopanel m-b">
                        <h1>${server.name}</h1>
                   		<h2>${server.subName}</h2>
                    </div>
                </div>
            </div>
            <div class="col-sm-4">
                <form method="post" action="login.do" id="loginForm"  style="width: 300px" onsubmit="rembMm()">
                	<c:if test="${fn:length(message)>0 }">
                    <p class="m-t-md" style="color: red;">${message}</p> 
                    </c:if>
                    <h4 class="no-margins">请登录</h4>
                    <input type="text" class="form-control uname" name="loginname" id="loginName" placeholder="输入账号" />
                    <input type="password" class="form-control pword m-b" name="password" id="password" placeholder="密码" />
                    <!-- <i onclick="alert('请联系管理员进行修改');">忘记密码了？</i> -->
                    <button class="btn btn-success btn-block" type="submit">登录</button>
                    <p class="text-muted text-center"> 
                    	<a href="javascript:;"><label class="mm"><input type="checkbox" id="remember"/>
                    	记住密码</label></a>
                    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	<a href="javascript:;" onclick="alert('请联系管理员进行修改');">忘记密码</a>
                    </p>
                </form>
            </div>
        </div>
    </div>
    <div class="signup-footer">
       	<div>
              	浏览器建议IE9.0以上 ；分辨率1366×768以上
           </div>
           <div>
              	 技术支持 <a href="http://116.62.212.62:80" class="servicelink" target="_blank">得淼科技</a> 021-50835869
           </div>
       </div>
</body>
<script type="text/javascript">
 
window.onload = function(){
    var oForm = document.getElementById('loginForm');
    var oUser = document.getElementById('loginName');
    var oPswd = document.getElementById('password');
    var oRemember = document.getElementById('remember');
    //页面初始化时，如果帐号密码cookie存在则填充
    if(getCookie('loginName') && getCookie('password')){
      oUser.value = getCookie('loginName');
      oPswd.value = getCookie('password');
      oRemember.checked = true;
    }
    //复选框勾选状态发生改变时，如果未勾选则清除cookie
    oRemember.onchange = function(){
      if(!this.checked){
        delCookie('loginName');
        delCookie('password');
      }
    };
    //表单提交事件触发时，如果复选框是勾选状态则保存cookie
    oForm.onsubmit = function(){
      if(remember.checked){ 
        setCookie('loginName',oUser.value,30); //保存帐号到cookie，有效期7天
        setCookie('password',oPswd.value,30); //保存密码到cookie，有效期7天
      }
    };
  };
  //设置cookie
  function setCookie(name,value,day){
    var date = new Date();
    date.setDate(date.getDate() + day);
    document.cookie = name + '=' + value + ';expires='+ date;
  };
  //获取cookie
  function getCookie(name){
    var reg = RegExp(name+'=([^;]+)');
    var arr = document.cookie.match(reg);
    if(arr){
      return arr[1];
    }else{
      return '';
    }
  };
  //删除cookie
  function delCookie(name){
    setCookie(name,null,-1);
  };
</script>
</html>
