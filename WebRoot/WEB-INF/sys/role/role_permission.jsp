<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<form method="post" action="savePermission.do" class="form-horizontal">
					<input type="hidden" id="roleId" name="roleId" value="${vo.id}"> <input type="hidden" id="functionIds" name="functionIds" value="${functionIds}">
					<div class="zTreeDemoBackground left">
						<ul id="tree" class="ztree"></ul>
					</div>
					<div class="hr-line-dashed"></div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<link href="${basePath}h/js/plugins/jquery-ztree/3.5.24/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css" />
	<script src="${basePath}h/js/plugins/jquery-ztree/3.5.24/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script src="${basePath}ext/js/tree.js" type="text/javascript"></script>

	<SCRIPT type="text/javascript">
		var setting = {
			check: {enable: true},	
			data: {simpleData: {enable: true}},
		};
		
		$(document).ready(function(){
			$.ajax({
			    url: '${basePath}sys/function/treeData.do',
				success: function(data) {  
					initTree('${selectedIds}',data);
			    }
			});
		});
	</SCRIPT>
	<script>
		var index = parent.layer.getFrameIndex(window.name); 
		function fnSelect(){
			setSelect('functionIds');
			var functionIds = $('#functionIds').val();
			var roleId = '${vo.id}';
			$.ajax({ 
				url:"ajaxSavePermission.do",
				dataType:"json",
				data:{"roleId":roleId,"functionIds":functionIds},
				type:"post",
				async:false,
				success: function(data){
					layer.msg(data.message, {icon: 1,time: 1000},function(){
						if("success"==data.status){
							parent.layer.close(index);
						}
					});
				},
				error:function(ajaxobj){  
			    }  
			});
		}
	</script>
</body>
</html>
