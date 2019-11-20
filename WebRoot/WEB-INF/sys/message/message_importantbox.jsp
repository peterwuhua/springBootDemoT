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
			<jsp:include page="message_left.jsp?flag=important" />
		</div>
		<div class="col-sm-10">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>
						<ol class="breadcrumb">
							<li>重要</li>
						</ol>
					</h5>
				</div>
				<div class="ibox-content">
					<div class="mail-tools tooltip-demo m-t-md">
						<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" title="刷新邮件列表" onclick="window.location.href =window.location.href;">
							<i class="fa fa-refresh"></i> 刷新
						</button>
						<button class="btn btn-danger btn-sm" data-toggle="tooltip" data-placement="top" title="删除邮件" onclick="fnDrop()">
							<i class="fa fa-trash-o"></i>删除
						</button>
						<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="标为垃圾邮件" onclick="fnSubmit('-1')">
							<i class="fa fa-trash-o"></i>
						</button>
						<form method="post" action="important.do" class="pull-right mail-search">
							<div class="input-group" style="width: 300px; float: right;">
								<input type="hidden" id="ids" name="ids" /> <input type="text" class="form-control input-sm" name="subject" value="${vo.subject }" placeholder="搜索邮件标题">
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
									<tr class="${1==e.position?'read':'unread'} ">
										<td class="check-mail"><input type="checkbox" name="ids" value="${e.id}" class="i-checks"></td>
										<td class="mail-ontact"><a href="detail.do?detailId=${e.id }&flag=important">${e.messageVo.senderVo.userVo.name}【${e.messageVo.senderVo.loginName }】</a> <c:if test="${1!=e.readFlag}">
												<span class="label label-danger pull-right">未读</span>
											</c:if></td>
										<td class="mail-subject"><a href="detail.do?detailId=${e.id }&flag=important">${e.messageVo.subject}</a></td>
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
	<%@ include file="../../include/js.jsp"%>
	<script>
        $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
    </script>
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
			$('form').attr('action','updatePosition.do?from=important&position='+t);
			$('form').submit();
	    }
		
		function fnDrop(){
			if(getSelectIds().length<=0){
				layer.msg('请选择记录', {icon: 0,time: 3000});
				return false;
			}
			$("#ids").val(getSelectIds());
			$('form').attr('action','drop.do?from=important&flag=important');
			$('form').submit();
	    }
    </script>
</body>
</html>
