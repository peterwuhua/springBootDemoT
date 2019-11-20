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
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-35 active"><label class="pull-right">AM上班:</label></td>
								<td class="width-65"><input id="amst" placeholder="08:30" class="form-control required" validate="required" name="amst" type="text" value="${vo.amst}" /></td>
							</tr>
							<tr>
								<td class="width-35 active"><label class="pull-right">AM下班:</label></td>
								<td class="width-65"><input id="amet" placeholder="12:00" class="form-control required" validate="required" name="amet" type="text" value="${vo.amet}" /></td>
							</tr>
							<tr>
								<td class="width-35 active"><label class="pull-right">PM上班:</label></td>
								<td class="width-65"><input id="pmst" placeholder="14:30" class="form-control required" validate="required" name="pmst" type="text" value="${vo.pmst}"/></td>
							</tr>
							<tr>
								<td class="width-35 active"><label class="pull-right">PM下班:</label></td>
								<td class="width-65"><input id="pmet" placeholder="18:00" class="form-control required" validate="required" name="pmet" type="text" value="${vo.pmet}" /></td>
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
