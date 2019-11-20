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
						 			<option value="cyd_zw_ty">化学物质定点采样记录单</option>
						 			<option value="cyd_zw_ryht">一氧化碳、二氧化碳采样记录单</option>
						 			<option value="cyd_zw_fc_dd">粉尘（定点）采样记录单</option>
						 			<option value="cyd_zw_fc_eyhg">游离二氧化硅含量采样记录表</option>
						 			<option value="cyd_zw_cgp">超高频辐射采样记录单</option>
						 			<option value="cyd_zw_gd">高频电磁场采样记录单</option>
						 			<option value="cyd_zw_gp">工频电场采样记录单</option>
						 			<option value="cyd_zw_jg">激光辐射采样记录单</option>
						 			<option value="cyd_zw_wb">微波辐射采样记录单</option>
						 			<option value="cyd_zw_zw">紫外辐射采样记录单</option>
						 			<option value="cyd_zw_gw_cy">高温（常用）采样记录单</option>
						 			<option value="cyd_zw_zs_dd">噪声（定点）采样记录单</option>
						 			<option value="cyd_zw_zs_mc">噪声（脉冲）采样记录单</option>
						 			<option value="cyd_zw_zs_zjq">噪声（周计权）采样记录单</option>
						 			<option value="cyd_zw_sczd">手传振动采样记录单</option> 
						 			<option value="cyd_zw_zd">照度采样记录单</option> 
						 			<option value="cyd_fs">放射防护检测采样记录单</option> 
						 			<option value="cyd_fs_x">X射线采样记录单</option> 
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
function createFile(){
	var id='${vo.id}';
	var temp=$('#temp').val();
	if(temp==''){
		parent.layer.msg("请选择模板！", {icon: 0,time: 3000})
		return false;
	}
	$('#editFile').show();
	POBrowser.openWindow('${basePath}bus/sampCyd/createCydFile.do?id='+id+'&temp='+temp,'width=1200px;height=800px;');
}
function fnOpenFile(){
	var id='${vo.id}';
	var temp=$('#temp').val();
	if(temp==''){
		parent.layer.msg("请选择模板！", {icon: 0,time: 3000})
		return false;
	}
	POBrowser.openWindow('${basePath}bus/sampCyd/editCydFile.do?id='+id+'&temp='+temp,'width=1200px;height=800px;');
}
$(function(){
	var temp='${vo.temp}';
	var type='${vo.type}';
	if(temp!=''){
		$('#temp').val('${vo.temp}');
	}else if(type!=''){
		if(type=='cyd_hx_dd'){
			$('#temp').val('cyd_zw_ty');
		}else if(type=='cyd_hx_ht'){
			$('#temp').val('cyd_zw_ryht');
		}else if(type=='cyd_hx_eyhg'){
			$('#temp').val('cyd_zw_fc_eyhg');
		}else if(type=='cyd_wl_cg'){
			$('#temp').val('cyd_zw_zd');
		}else if(type=='cyd_wl_cp'){
			$('#temp').val('cyd_zw_cgp');
		}else if(type=='cyd_wl_gd'){
			$('#temp').val('cyd_zw_gd');
		}else if(type=='cyd_wl_gp'){
			$('#temp').val('cyd_zw_gp');
		}else if(type=='cyd_wl_gw'){
			$('#temp').val('cyd_zw_gw_cy');
		}else if(type=='cyd_wl_zs'){
			$('#temp').val('cyd_zw_zs_dd');
		}else if(type=='cyd_wl_zs_b'){
			$('#temp').val('cyd_zw_zs_mc');
		}else if(type=='cyd_wl_jg'){
			$('#temp').val('cyd_zw_jg');
		}else if(type=='cyd_wl_wb'){
			$('#temp').val('cyd_zw_wb');
		}else if(type=='cyd_wl_zf'){
			$('#temp').val('cyd_zw_zw');
		}
	}
});
</script>
</body>
</html>
