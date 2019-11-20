<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title></title>
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${basePath}h/css/font-awesome.min.css?v=4.7.0" rel="stylesheet">
<link href="${basePath}h/css/animate.css" rel="stylesheet">
<link href="${basePath}h/css/style.css?v=4.1.0" rel="stylesheet">
<!-- Data Tables -->
<link href="${basePath}h/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
<style type="text/css">
.instDiv{
	float:left;
	width:100%;
	height: 250px;
    padding: 10px;
    margin: 10px;
    border:1px solid #e7eaec;
    text-align: center;
}
.instDiv img{
	width: 90%;
	height: 200px;
}
.instDiv div{
	padding-top:10px;
	padding-left:10px;
	width: 90%;
	text-align: left;
}
.col-sm-12{
	padding: 0px 1px;
}
 
</style>
</head>

<body class="gray-bg">
	<div class="wrapper">
		<div class="row" style="background-color: #fff;padding-top: 10px;">
			 
        </div>
	</div>
	<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
 	<!-- 全局js -->
    <script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${basePath}h/js/plugins/layer/layer.min.js"></script>

<script>
	function ajaxGetApp(){
		var ct=$('#app-List');
		$.ajax({ 
			url:"${basePath}res/appara/listAll.do",
			success: function(data){
				ct.html('');
				for(var i=0;i<data.length;i++){
					var name='';
					if(data[i].name>20){
						name=data[i].name.substring(0,20)+'..';
					}else{
						name=data[i].name;
					}
					if(data[i].rule!=''){
						name+=" "+data[i].spec;
					}
					var logo='static\\upload\\avatar\\defaut.png';
					if(data[i].logo!='' && data[i].logo!=null){
						logo=data[i].logo;
					}
					var mobile='';
					if(data[i].userVo!=null){
						mobile=data[i].userVo.mobile;
					}
					var msg='仪器名称：'+name+
							'\r\n设备编号：'+data[i].no+
							'\r\n仪器状态：'+data[i].state+
							'\r\n负责人：'+data[i].keeper+
							'\r\n联系方式：'+mobile;
					var li='<div class="col-sm-3"><div class="instDiv" data-toggle="tooltip" data-placement="top" title="'+msg+'"><img alt="" src="${basePath}'+logo+'"><p style="padding-top:10px;">'+name+'</p></div></div>';
					ct.append(li);
				}
			},
			error:function(ajaxobj){  
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }  
		});
	}
	$(function(){
		//ajaxGetApp();
	})
</script>
</body>

</html>



