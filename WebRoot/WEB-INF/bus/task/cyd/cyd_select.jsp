<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend{
	border-bottom:0px;
	width: 80px;
	margin-bottom:0px;
	font-size: 14px !important;
}
a{
	color: blue;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">选择模板:</label></td>
							<td class="width-35">
								 <select  class="form-control required" validate="required" id="temp" name="temp">
								 	<option value="">-请选择-</option>
								 	<c:choose>
								 		<c:when test="${vo.sampType =='环境'}">
								 			<option value="hj_s_sh">工业企业厂界、社会生活环境、建筑施工场界噪声监测记录表</option>
								 			<option value="hj_s_jt">交通噪声监测记录表</option>
								 			<option value="hj_s_qt">某环境噪声监测记录表</option>
								 			<option value="hj_w">水和废水采样原始记录表</option>
								 			<option value="hj_q_y_gdy">固定源气态污染物采样原始记录表</option>
								 			<option value="hj_q_w">大气环境(无组织废气)采样记录表</option>
								 			<option value="hj_g_gf">固废(废液)采样原始记录表</option>
								 			<option value="hj_g_dz">底质（底泥、沉积物）采样原始记录表</option>
								 			<option value="hj_t">土壤采样原始记录表</option> 
								 		</c:when>
								 	</c:choose>
								 </select>
							</td>
						</tr>
					</table>
					<div align="center">
						<div class="btn-group" id="createFile">
	                       <button class="btn btn-info" type="button" onclick="createFile();">在线生成文件 </button>
	                    </div>
	                    <c:choose>
	                    	<c:when test="${vo.filePath!='' && vo.filePath!=null}">
		                    	<div class="btn-group" id="editFile">
			                       <button class="btn btn-info" type="button" onclick="fnOpenFile();">在线查看文件</button>
			                    </div>
	                    	</c:when>
	                    	<c:otherwise>
	                    		<div class="btn-group" id="editFile" style="display: none;">
			                       <button class="btn btn-info" type="button" onclick="fnOpenFile();">在线查看文件</button>
			                    </div>
	                    	</c:otherwise>
	                    </c:choose>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<!--PageOffice.js -->
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
<script>
	var index = parent.layer.getFrameIndex(window.name); 
	function submitSave(){
		var ids=[];
		if(ids.length==0){
			parent.layer.msg("检测人不能为空！", {icon: 0,time: 3000})
			return false;
		}
		$.ajax({ 
			url:"update4Zy.do",
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
	
function createFile(){
	var id='${vo.id}';
	var temp=$('#temp').val();
	if(temp==''){
		parent.layer.msg("请选择模板！", {icon: 0,time: 3000})
		return false;
	}
	$('#editFile').show();
	POBrowser.openWindow('${basePath}bus/taskXc/createCydFile.do?id='+id+'&temp='+temp,'width=1200px;height=800px;');
}
function fnOpenFile(){
	var id='${vo.id}';
	var temp=$('#temp').val();
	if(temp==''){
		parent.layer.msg("请选择模板！", {icon: 0,time: 3000})
		return false;
	}
	POBrowser.openWindow('${basePath}bus/taskXc/editCydFile.do?id='+id+'&temp='+temp,'width=1200px;height=800px;');
}
$(function(){
	$('#temp').val('${vo.temp}');
});
</script>
</body>
</html>
