<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<style type="text/css">
.col-sm-10 {
    padding-right: 5px;
    padding-left: 2px;
}
</style>
</head>
<body class="gray-bg">
	<div class="row">
		<div class="col-sm-2">
			<jsp:include page="message_left.jsp?flag=inbox" />
		</div>
		<div class="col-sm-10">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>
						<ol class="breadcrumb">
							<li>收件箱</li>
						</ol>
					</h5>
				</div>
				<div class="ibox-content">
					<div class="mail-tools tooltip-demo m-t-md">
						<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" title="刷新邮件列表" onclick="window.location.href =window.location.href;">
							<i class="fa fa-refresh"></i> 刷新
						</button>
						<button class="btn btn-danger btn-sm" data-toggle="tooltip" data-placement="top" title="删除邮件" onclick="fnDrop()">
							<i class="fa fa-times"></i>删除
						</button>
						<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="标为已读" onclick="fnSubmit('2')">
							<i class="fa fa-eye"></i>
						</button>
						<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="标为重要" onclick="fnSubmit('3')">
							<i class="fa fa-exclamation"></i>
						</button>
						<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="标为垃圾邮件" onclick="fnSubmit('-1')">
							<i class="fa fa-trash-o"></i>
						</button>
						<form method="post" action="inbox.do" class="pull-right mail-search">
							<div class="input-group" style="width: 300px;float: right;">
								<input type="hidden" id="ids" name="ids" /> 
								<input type="text" class="form-control input-sm" name="subject" value="${vo.subject}" placeholder="搜索信箱标题">
								<div class="input-group-btn">
									<button type="submit" class="btn btn-sm btn-primary">搜索</button>
								</div>
							</div>
						</form>
					</div>
					<div class="mail-box" style="min-height: 350px;">
						<table class="table table-hover table-mail">
							<tbody>
								<c:forEach items="${list}" var="e">
									<tr class="${1==e.readFlag?'read':'unread'} ">
										<td class="check-mail"><input type="checkbox" name="ids" value="${e.id}" class="i-checks"></td>
										<td class="mail-ontact"><a href="detail.do?detailId=${e.id }&flag=inbox">${e.messageVo.senderVo.userVo.name}【${e.messageVo.senderVo.loginName }】</a> <c:if test="${1!=e.readFlag}">
												<span class="label label-danger pull-right">未读</span>
											</c:if></td>
										<td class="mail-subject"><a href="detail.do?detailId=${e.id }&flag=inbox">${e.messageVo.subject}</a></td>
										<td class="text-right mail-date" width="200px">${e.messageVo.sendTimeSDF}</td>
									</tr>
								</c:forEach>
								<c:if test="${fn:length(list)==0}">
									<tr class="read">
										<td colspan="4" style="color: red;">暂无数据</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
	<script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${basePath}h/js/content.js?v=1.0.0"></script>
	<script src="${basePath}h/js/plugins/iCheck/icheck.min.js"></script>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<script> 
	    function getSelectIds(){
	    	var ids = "";
	    	$("input[name='ids']:checkbox").each(function(i){ 
	    		if(true == $(this).is(':checked')){   
	    			if(0!=i) ids+=",";  
	    			ids+=$(this).val();   
	    		}
	    	});
	    	return ids;
	    }
	        
		function fnSubmit(t){
			if(getSelectIds().length<=0){
				layer.msg('请选择记录', {icon: 0,time: 3000});
				return false;
			}
			$("#ids").val(getSelectIds());
			$('form').attr('action','updatePosition.do?from=inbox&position='+t);
			$('form').submit();
	    }
		
		function fnDrop(){
			var selectIds = getSelectIds();
			if(selectIds.length<1){
				layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
				return false;
			}
			layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
				$("#ids").val(getSelectIds());
				$('form').attr('action','drop.do?from=inbox&flag=inbox');
				$('form').submit();
			});
	    }
    </script>
</body>
</html>
