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
			<jsp:include page="message_left.jsp?flag=sentbox" />
		</div>
		<div class="col-sm-10">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>
						<ol class="breadcrumb">
							<li>发件箱</li>
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
						<form method="post" action="sentbox.do" class="pull-right mail-search">
							<div class="input-group" style="width: 300px; float: right;">
								<input type="hidden" id="ids" name="ids" /> 
								<input type="text" class="form-control input-sm" name="subject" value="${vo.subject }" placeholder="搜索邮件标题">
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
									<tr class="read">
										<td class="check-mail"><input type="checkbox" name="ids" value="${e.id}" class="i-checks"></td>
										<td class="mail-ontact" title="${e.reciver}"><a href="messageAnswer.do?id=${e.id}&flag=sentbox">${fn:substring(e.reciver,0,60)}</a>
										<td class="mail-subject"><a href="messageAnswer.do?id=${e.id}&flag=sentbox">${e.subject}</a></td>
										</td>
										<td class="text-right mail-date" width="200px">${e.sendTimeSDF}</td>
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
	        
		function fnDrop(){
			var selectIds = getSelectIds();
			if(selectIds.length<1){
				layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
				return false;
			}
			layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
				$("#ids").val(getSelectIds());
				$('form').attr('action','drop.do?from=sentbox&flag=sentbox');
				$('form').submit();
			});
			
	    }
    </script>
</body>
</html>
