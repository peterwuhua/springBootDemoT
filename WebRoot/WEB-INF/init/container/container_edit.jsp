<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="" class="form-horizontal" id="listForm">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" id="id" value="${vo.id}" type="hidden" />
					</c:if>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">编号:</label></td>
								<td class="width-35"><input id="code" name="code"  class="form-control required" validate="required" maxlength=32 placeholder="计量单位" type="text" value="${vo.code}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">名称:</label></td>
								<td class="width-35"><input id="name" class="form-control required" validate="required" maxlength=64 name="name" type="text" value="${vo.name}" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">规格:</label></td>
								<td class="width-35"><input id="maxVal" name="maxVal" class="form-control required" validate="required" maxlength=64 type="text" value="${vo.maxVal}" /></td>
							</tr>
							 <tr>
								<td class="active"><label class="pull-right">计量单位:</label></td>
								<td class="width-35"><input id="unit" name="unit" class="form-control required" validate="required" maxlength=64 placeholder="计量单位" type="text" value="${vo.unit}" /></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script>
		function save(){
			var t = $("#listForm").FormValidate();
			if(t&&validateCode()){
				var conainer={};
				conainer.id=$('#id').val();
				conainer.code=$('#code').val();
				conainer.name=$('#name').val();
				conainer.maxVal=$('#maxVal').val();
				conainer.unit=$('#unit').val();
				$.ajax({ 
					url:"ajaxSave.do",
					dataType:"json",
					data:conainer,
					type:"post",
					success: function(data){
						if("success"==data.status){
							layer.msg(data.message, {icon: 0,time: 3000});
							parent.jqgridReload();
							location.reload();
						}
					},
					error:function(ajaxobj){  
						layer.msg(ajaxobj, {icon: 0,time: 3000});
				    }  
				});
			}
		}
		function validateCode(){
			var flag=false;
			var code=$('#code').val();
			var id=$('#id').val();
			$.ajax({ 
				url:"ajaxCheckCode.do",
				dataType:"json",
				data:{'id':id,'code':code},
				type:"post",
				async: false,
				success: function(data){
					if("success"!=data.status){
						layer.msg(data.message);
						$('#code').focus();
					}else{
						flag=true;
					}
				},
				error:function(ajaxobj){  
					layer.msg(ajaxobj, {icon: 0,time: 3000});
			    }  
			});
			return flag;
		}
	</script>
</body>
</html>
