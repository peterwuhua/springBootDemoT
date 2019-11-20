<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form id="listForm" method="post" action="updateCfg.do" class="form-horizontal">
					<input name="userId" type="hidden" value="${vo.userId}" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">host:</label></td>
								<td class="width-85"><input id="host" placeholder="smtp.qq.com" class="form-control required" validate="required" name="host" type="text" value="${vo.host}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">账户:</label></td>
								<td class="width-85"><input id="fromMail" placeholder="xxx@qq.com" class="form-control required email" validate="required" name="fromMail" type="text" value="${vo.fromMail}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">密码:</label></td>
								<td class="width-85"><input id="pwd" placeholder="xxx xxx" class="form-control required" validate="required" name="pwd" type="password" value="${vo.pwd}" autocomplete="new-password"/></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">端口:</label></td>
								<td class="width-85"><input id="port" placeholder="465" class="form-control required" validate="required" name="port" type="text" value="${vo.port}" /></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script type="text/javascript">
	var index = parent.layer.getFrameIndex(window.name); 
		function submitSave(){
			var t = $("#listForm").FormValidate();
			if(t){
				$.ajax({ 
					url:"updateCfg.do",
					dataType:"json",
					data:$('#listForm').serialize(),
					type:"post",
					success: function(data){
						parent.layer.msg(data.message, {icon: 0,time: 3000})
						if("success"==data.status){
							parent.layer.close(index);
						}
					},
					error:function(ajaxobj){  
						layer.msg(ajaxobj, {icon: 0,time: 3000});
				    }  
				});
			}
		}
	</script>
</body>
</html>
