<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>实验室运营服务云平台</title>
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${basePath}h/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="${basePath}h/css/animate.css" rel="stylesheet">
<link href="${basePath}h/css/style.css?v=4.1.0" rel="stylesheet">
<!-- Data Tables -->
<link href="${basePath}h/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
<style type="text/css">
#tongzhigonggao > .list-group-item{
	display: none;
}
#tongzhigonggao > .list-group-item.show{
	display: inline;
}
.carousel-control-up > .up {
    position: absolute;
    top: 0;
    right:0;
    width:20%;
    height:100px;
    padding-right:20px;
    font-size: 30px;
    text-align: right;
    text-shadow: 0 1px 2px rgba(0,0,0,.6);
    background-color: rgba(0,0,0,0);
    filter: alpha(opacity=50);
    opacity: .5;
     z-index:9999;
}
.carousel-control-up > .down {
    position: absolute;
    bottom: 0;
    right:0;
    width:20%;
    height:100px;
    padding-top:60px;
    padding-right:20px;
    font-size: 30px;
    text-align: right;
    text-shadow: 0 1px 2px rgba(0,0,0,.6);
    background-color: rgba(0,0,0,0);
    filter: alpha(opacity=50);
    opacity: .5;
     z-index:9999;
}
.down:focus,.down:hover {
    opacity: 0.9;
    text-decoration: none;
    outline: 0px;
    color:  #676a6c;
}
.up:focus, .up:hover {
    opacity: 0.9;
    text-decoration: none;
    outline: 0px;
    color:  #676a6c;
}
.col-sm-3{
	padding: 0px 1px;
}
.col-sm-6{
	padding: 0px 1px;
}
.modal-dialog{
	width: 300px;
}
.modal-body{
	text-align: center;
	font-size: 18px;
}
.modal-footer{
	text-align: center;
}
.hr-line-dashed {
    border-top: 1px dashed #676a6c;
    color: #ffffff;
    background-color: #ffffff;
    height: 1px;
    margin: 20px 0;
}
.text-navy{
	color: red;
}
.timeline-item .date{
	width: 80px;
}
.chat-users{
    margin-left:0px;
}
.chat-user {
    padding: 8px 0px;
    width: 48%;
    float: left;
    margin: 2px;
}
.image{
	height: 80px;
	width: 100%;
	box-shadow: 0px 3px 3px #B5DBF4;
}
.image.hover{
	padding: 1px;
}
.navy-bg{
	text-shadow: 0px 1px 0px #797676;
	box-shadow: 0px 5px 5px #19a588,
				0px 5px 5px #14806a,
				0px 5px 5px #0e5d4d,
				0px 5px 5px #0a4237,
				0 6px 1px rgba(0, 0, 0, 0.1), 
				0 5px 10px rgba(0, 0, 0, 0.75), 
				0 5px 5px -5px rgba(0, 0, 0, 0.05);
}
.lazur-bg{
	text-shadow: 0px 1px 0px #797676;
	box-shadow: 0px 5px 5px #1fafb1,
				0px 5px 5px #1a9496,
				0px 5px 5px #157c7d,
				0px 5px 5px #0e5252,
				0 6px 1px rgba(0, 0, 0, 0.1), 
				0 5px 10px rgba(0, 0, 0, 0.75), 
				0 5px 5px -5px rgba(0, 0, 0, 0.05);
}
.yellow-bg{
	text-shadow: 0px 1px 0px #797676;
	box-shadow: 0px 5px 5px #d8954c,
				0px 5px 5px #bf8443,
				0px 5px 5px #a57139,
				0px 5px 5px #82582c,
				0 6px 1px rgba(0, 0, 0, 0.1), 
				0 5px 10px rgba(0, 0, 0, 0.75), 
				0 5px 5px -5px rgba(0, 0, 0, 0.05);
}
.dbDiv.hover{
	margin-top: -1px !important;
}
</style>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content" style="margin-bottom: 25px;">
		<div class="row">
			<div class="col-sm-3" style="background-color: #fff; height: 400px;" align="center">
				<div class="ibox float-e-margins">
					<span class="profile-picture" style="padding: 0px;"> 
						<img width="200px" height="220px" id="avatar" class="editable img-responsive editable-click editable-empty" alt="Alex's Avatar" src="${basePath}static/upload/${(current.avatar==null||current.avatar=='')?'avatar/profile-pic.jpg':current.avatar}">
					</span>
					<div class="no-padding" style="text-align: left; width: 200px;">
						<p>
							<b>姓名：</b><span id="userName">${current.userName}</span>
						</p>
						<p>
							<b>部门：</b><span id="orgName">${current.orgName}</span>
						</p>
						<p>
							<b>岗位：</b><span id="duty">${current.duty}</span>
						</p>
					</div>
					<table style="width: 80%">
						<tr>
							<td>
								<button type="button" class="btn-danger">${current.yj}</button>
								收件箱&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td>
								<button type="button" class="btn-info">${current.tz}</button>
								公告通知
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="col-sm-6" style="padding: 0px 2px;">
				<div class="ibox float-e-margins">
					<div class="ibox-content no-padding" style="height:160px;border: 0px;">
						<div style="float: left;">
							<iframe name="weather_inc"
							src="http://i.tianqi.com/index.php?c=code&id=19&num=5"
							width="600" height="150" frameborder="0" marginwidth="0"
							marginheight="0" allowtransparency="true"
							scrolling="no" style="padding-top: 30px;"></iframe>
						</div>
					</div>
					<div class="ibox-content no-padding carousel" style="height: 240px;">
						<div class="carousel-inner">
							<ul class="list-group" id="tongzhigonggao">
							</ul>
						</div>
						<div class="right carousel-control-up">
							<a class="up" onclick="showUp();"><i class="fa fa-angle-up" aria-hidden="true"></i></a>
							<a class="down" onclick="showDown();"><i class="fa fa-angle-down" aria-hidden="true"></i></a>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-3" style="padding-left:4px; background-color: #fff; height: 400px;">
				<div class="ibox float-e-margins">
					<jsp:include page="calendar.jsp"/>
					<!-- <img style="width: 100%; height: 100%" src="${basePath}static/img/portal/demo_img_01.jpg"> -->
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><i class="fa fa-list-ol" aria-hidden="true"></i> 待办任务</h5>
                        <div class="ibox-tools">
                            <span class="label label-warning-light">${fn:length(menuList)}条</span>
                        </div>
                    </div>
                    <div class="ibox-content" style="height: 410px;overflow-y: auto;">
                    	<table class="table table-hover no-margins">
                             <thead>
								<tr>
									<th>类型</th>
									<th>数量</th>
								</tr>
							</thead>
                             <tbody>
                             	<c:forEach items="${menuList}" var="e" varStatus="v">
	                                 <tr>
	                                     <td>
	                                     	<a  class="J_menuItem" href="${e.functionVo.url}">${e.functionVo.name} </a>
	                                     </td>
	                                     <td class="text-navy">
	                                     	<span class="label label-primary"> ${e.functionVo.count}</span>
	                                     </td>
	                                 </tr>
                                 </c:forEach>
                             </tbody>
                         </table>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><i class="fa fa-bell-o" aria-hidden="true"></i> 提醒信息</h5>
                        <div class="ibox-tools">
                            <span class="label label-warning-light">${fn:length(txList)}条</span>
                        </div>
                    </div>
                    <div class="ibox-content timeline" style="height: 410px;overflow-y: auto;">
                    	<c:forEach items="${txList}" var="e" varStatus="v">
                    		<div class="timeline-item">
	                            <div class="row">
	                                <div class="col-xs-2 date ui-sortable">
	                                    <i class="${e.icon}"></i>
	                                    <small class="text-navy">${e.status}</small>
	                                </div>
		                    		<c:choose>
		                    			<c:when test="${v.index==0}">
			                                <div class="col-xs-8 content no-top-border ui-sortable" style="min-height:60px;">
			                                    <p class="m-b-xs"><strong>${e.name}</strong></p>
			                                    <p>${e.content}</p>
			                                </div>
                    					</c:when>
                    					<c:otherwise>
			                                <div class="col-xs-8 content ui-sortable" style="min-height:60px;">
			                                    <p class="m-b-xs"><strong>${e.name}</strong></p>
			                                    <p>${e.content}</p>
			                                </div>
		                    			</c:otherwise>
		                    		</c:choose>
                    			</div>
			            	</div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><i class="fa fa-hand-o-right" aria-hidden="true"></i> 便捷入口</h5>
                    </div>
                    <div class="ibox-content" style="padding: 5px 5px;">
                    	<div class="chat-users">
                           <div class="users-list">
                           		<div class="chat-user">
                                  <a href="http://www.jstbjc.cn/main.aspx" target="_blank" title="公司官网">
				                      <img class="image" src="${basePath}static/img/bz/gsgw.jpg">
				                  </a>
                               </div>
                               <div class="chat-user">
                                  <a href="https://www.cnas.org.cn/" target="_blank" title="认证规范检索平台">
				                      <img class="image" src="${basePath}static/img/bz/7.jpg">
				                  </a>
                               </div>
                           		<div class="chat-user">
                                  <a href="http://wmdw.jswmw.com/home/?lid=3851" target="_blank" title="徐州环保局">
				                      <img class="image" src="${basePath}static/img/bz/xzhbj.jpg">
				                  </a>
                               </div>
                               <div class="chat-user">
                                  <a href="http://wmdw.jswmw.com/home/?lid=1406" target="_blank" title="徐州质监局">
				                      <img class="image" src="${basePath}static/img/bz/xzzjj.jpg">
				                  </a>
                               </div>
                               <div class="chat-user">
                                  <a href="http://ws.xz.gov.cn/" target="_blank" title="徐州卫计委">
				                      <img class="image" src="${basePath}static/img/bz/xzwjj.jpg">
				                  </a>
                               </div>
                               <div class="chat-user">
                                  <a href="http://yjgl.xz.gov.cn/ajj/qyaqcn/" target="_blank" title="徐州应急管理局">
				                      <img class="image" src="${basePath}static/img/bz/xzyjglj.jpg">
				                  </a>
                               </div>
                               <div class="chat-user">
                                  <a href="http://www.mee.gov.cn" target="_blank" title="国家生态环境部">
				                      <img class="image" src="${basePath}static/img/bz/gjsthjb.jpg">
				                  </a>
                               </div>
                               <div class="chat-user">
                                  <a href="http://hbt.jiangsu.gov.cn" target="_blank" title="江苏省生态环境厅">
				                      <img class="image" src="${basePath}static/img/bz/jsssthjt.jpg">
				                  </a>
                               </div>
                           </div>
                       </div>
                    </div>
                </div>
            </div>
		</div>
	</div>
	
	<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
 	<!-- 全局js -->
    <script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${basePath}h/js/plugins/layer/layer.min.js"></script>
    <!-- Data Tables -->
    <script src="${basePath}h/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="${basePath}h/js/plugins/dataTables/dataTables.bootstrap.js"></script>
    
    <!-- portal 页代办提示点击链接专用js,代办提示页面必须 -->
    <script src="${basePath}h/js/contabs_portal.js"></script>
 <script type="text/javascript">
	//通知信息
	var max=0,cut=0;
	function ajaxGetTongzhi(){
		$.ajax({ 
			url:"${basePath}sys/notice/ajaxGetList.do",
			success: function(data){
				$('#tongzhigonggao').html('');
				for(var i=0;i<data.length;i++){
					var content='';
					if(data[i].content.length>200){
						content=data[i].content.substring(0,200)+'...';
					}else{
						content=data[i].content;
					}
					var li='<li class="list-group-item animated fadeInUp" data-animation="slideInDown" style="padding-top:30px;">'+
							'<p><a class="J_menuItem" href="${basePath}sys/notice/gridPage.do"><i class="fa fa-volume-up" aria-hidden="true"></i> '+data[i].subject+'</a></p> '+
							'<p>'+content+'</p> '+
							'<small class="block text-muted">@'+data[i].userName+'  <i class="fa fa-clock-o"></i> '+data[i].sendTime+'</small></li>';
					$('#tongzhigonggao').append(li);
				}
				max=data.length-1;
				cut=0;
				showTZ();
			},
			error:function(ajaxobj){  
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }  
		});
	}
	//心跳方法
	function showTZ(){
		$('#tongzhigonggao').find('li').each(function(i){
			if(i!=cut){
				$(this).removeClass('show');
			}else{
				$(this).addClass('show');
			}
		});
		if(max==cut){
			cut=0;
		}else{
			cut++;
		}
		var timerID = setTimeout('showTZ()',5000);
	}
	function showUp(){
		if(cut==0){
			cut=max;
		}else{
			cut--;
		}
		$('#tongzhigonggao').find('li').each(function(i){
			if(i!=cut){
				$(this).removeClass('show');
			}else{
				$(this).addClass('show');
			}
		});
	}
	function showDown(){
		if(max==cut){
			cut=0;
		}else{
			cut++;
		}
		$('#tongzhigonggao').find('li').each(function(i){
			if(i!=cut){
				$(this).removeClass('show');
			}else{
				$(this).addClass('show');
			}
		});
	}
	function fnQd(){
		$.ajax({ 
			url:"${basePath}office/dg/add2Dk.do",
			dataType:"json",
			type:"post",
			success: function(data){
				if(data.status=='success'){
		    	    parent.toastr.success(data.message, '');
		        }else{
		        	parent.toastr.error(data.message, '');
		        }
			},
			error:function(ajaxobj){  
				parent.toastr.error(ajaxobj, '');
		    }  
		});
	}
	$(function(){
		ajaxGetTongzhi();
		$('.image,.dbDiv').each(function(){
			$(this).hover(function() {
				if(!$(this).hasClass('hover'))
				{
				    $(this).addClass("hover");
				 }
			},
			function() {
			   if($(this).hasClass('hover'))
			   {
			    $(this).removeClass("hover");
			   }  
			});
		});
	})
</script>
 </script>
</body>
</html>



