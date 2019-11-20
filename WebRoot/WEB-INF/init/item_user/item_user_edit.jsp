<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="" class="form-horizontal" id="thisForm">
					<c:if test="${fn:length(vo.id)>0}">
						<input id="id" name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<input id="itemId" name="itemId" value="${vo.itemId}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-10 active"><label class="pull-right">检测部门:</label></td>
								<td class="width-40">
									<select  id="orgId" name="orgId"  class="form-control required" validate="required">
										<option value="">-请选择-</option>
										<c:forEach items="${orgList}" var="e" varStatus="v">
											<c:choose>
												<c:when test="${e.id==vo.orgId}">
													<option value="${e.id}" selected="selected">${e.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.id}">${e.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<input type="hidden" id="orgName" name="orgName" value="${vo.orgName}">
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">关联检测人:</label></td>
								<td>
									<div class="input-group">
										<input type="text" id="userNames" class="form-control required" validate="required"  name="userNames" value="${vo.userNames}"  onclick="fnSelectUsers()">
										<input type="hidden" id="userIds"  name="userIds" value="${vo.userIds}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUsers()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">默认检测人:</label></td>
								<td>
									<select id="userId"  name="userId"  class="form-control required" validate="required">
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
<script type="text/javascript">
	 
	 
	function fnSelectUsers(){
		var userId=$('#userIds').val();
		var orgId=$('#orgId').val();
		if(orgId==''){
			layer.msg('请选择部门', {icon: 0,time: 3000});
			return true;
		}
		parent.layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'selects.do?userIds='+userId+"&orgId="+orgId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin=layero.find('iframe')[0];
				var data=iframeWin.contentWindow.fnSelectUser();
				$('#userIds').val(data.id);
				$('#userNames').val(data.name);
				initUserOpt(data.id,data.name);
			}
		});
	}
	function fnSelectMethod(){
		var methodId=$('#methodIds').val();
		parent.layer.open({
			title:'检测方法',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/method/select.do?ids='+methodId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin=layero.find('iframe')[0];
				var data=iframeWin.contentWindow.fnSelectM();
				$('#methodIds').val(data.id);
				$('#methodNames').val(data.name);
			}
		});
	}
	 
	function initUserOpt(ids,names){
		var defaut='${vo.userId}';
		var opt='';
		for(var i=0;i<names.length;i++){
			if(defaut==ids[i]){
				opt+='<option value="'+ids[i]+'" selected="selected">'+names[i]+'</option>';
			}else{
				opt+='<option value="'+ids[i]+'">'+names[i]+'</option>';
			}
		}
		$('#userId').html(opt);
	}
	var index = parent.layer.getFrameIndex(window.name); 
	function submitFn(){
		var t = $("#thisForm").FormValidate();
		var url='addData.do';
		if('${vo.id}'!=''){
			url='updateData.do';
		}
		if(t){
			$.ajax({ 
				url:url,
				dataType:"json",
				data:$('#thisForm').serialize(),
				type:"post",
				success: function(data){
					parent.layer.msg(data.message, {icon: 0,time: 3000})
					if("success"==data.status){
						parent.jqgridReload();
						parent.layer.close(index);
					}
				},
				error:function(ajaxobj){  
					layer.msg(ajaxobj, {icon: 0,time: 3000});
			    }  
			});
		}
	}
	$(function(){
		var userIds='${vo.userIds}';
		var userNames='${vo.userNames}';
		initUserOpt(userIds.split(','),userNames.split(','));
	});
</script>
</html>