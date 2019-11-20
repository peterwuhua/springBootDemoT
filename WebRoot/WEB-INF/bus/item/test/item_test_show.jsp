<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<!-- Sweet Alert -->
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>

<style type="text/css">
 
#jcTb .input-group-btn .btn{
	padding: 6px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="update4Data.do?isCommit=0" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<input name="ids" value="${vo.ids}" type="hidden" />
					<input name="taskVo.id" value="${vo.taskVo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: -1px;">
						<tr>
							<td class="width-15 active"><label class="pull-right">任务编号:</label></td>
							<td class="width-35">
								<a href="javascript:void(0);" onclick="fnTask('${vo.taskVo.id}','${vo.taskVo.no}');">${vo.taskVo.no}</a>
							</td>
							<td class="width-15 active"><label class="pull-right">受检单位:</label></td>
							<td class="width-35">
								${vo.taskVo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">检测性质:</label></td>
							<td class="width-35">
								${vo.taskVo.taskType}
							</td>
							<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
							<td class="width-35">
								${vo.taskVo.sampName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">检测项目:</label></td>
							<td class="width-35">
								${vo.itemName}
							</td>
							<td class="width-15 active"><label class="pull-right">计量单位:</label></td>
							<td class="width-35">
	                         	${vo.unit}
							</td>
						</tr>
						<c:if test="${vo.taskVo.pj=='是'}">
							<tr>
								<td class="width-15 active"><label class="pull-right">评价标准:</label></td>
								<td class="width-35">
									${vo.standName}
								</td>
								<td class="width-15 active"><label class="pull-right">标&nbsp;准&nbsp;&nbsp;值:</label></td>
								<td class="width-35">
									<c:choose>
										<c:when test="${vo.limited=='a'}">
											MAC：${vo.mac} ${vo.unit}
											<input type="hidden" id="mac" class="form-control" value="${vo.mac}">
										</c:when>
										<c:when test="${vo.limited=='b'}">
											 <label>  
											 	PC-TWA：${vo.twa} ${vo.unit}
											    <input type="hidden" id="twa" class="form-control" value="${vo.twa}">
											 </label>
											 <label>
											 	PC-STEL：${vo.stel} ${vo.unit}
											 	<input type="hidden" id="stel" class="form-control" value="${vo.stel}">
											 </label>
										</c:when>
										<c:when test="${vo.limited=='c'}">
											<label>  
												PC-TWA：${vo.twa} ${vo.unit}
											  <input type="hidden" id="twa" class="form-control" value="${vo.twa}">
											</label>
											<label> 
												超限倍数：${vo.lmt}
												<input type="hidden" id="lmt" class="form-control" value="${vo.lmt}"> 
											</label>
										</c:when>
										<c:when test="${vo.limited!=''&&vo.limited!=null}">
												${vo.limited} ${vo.unit}
										</c:when> 
										<c:otherwise>
											<font color="red">限值未找到</font>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="width-15 active"><label class="pull-right">检测方法:</label></td>
							<td class="width-35">
								${vo.methodName}
							</td>
							<td class="width-15 active"><label class="pull-right">检&nbsp;出&nbsp;&nbsp;限:</label></td>
							<td class="width-35">
								${vo.limitLine}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">使用仪器:</label></td>
							<td class="width-35">
								${vo.appName}
							</td>
							<td class="width-15 active"><label class="pull-right">标准物质:</label></td>
							<td class="width-35">
	                         ${vo.stName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">温&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度:</label></td>
							<td class="width-35">
								${vo.wd}
							</td>
							<td class="width-15 active"><label class="pull-right">湿&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度:</label></td>
							<td class="width-35">
	                         	${vo.sd}
							</td>
						</tr>
						<tfoot>
							<tr>
								<td colspan="7"><b>检测结果</b></td>
							</tr>
						</tfoot>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 1px;">
						<thead>
							<tr>
								<th style="text-align: center;width: 50px;">序号</th>
								<th style="text-align: center;width: 80px;">样品名称</th>
								<th style="text-align: center;width: 80px;">采样日期</th>
								<th style="text-align: center;width: 80px;">样品编号</th>
								<c:choose>
									<c:when test="${vo.st=='气'}">
										<th style="text-align: center;width: 150px;">浓度值</th>
										<th style="text-align: center;width: 150px;">速率值</th>
									</c:when>
									<c:otherwise>
										<th style="text-align: center;width: 150px;">检测值</th>
									</c:otherwise>
								</c:choose>
								<th style="text-align: center;width: 80px;">单项判定</th>
							</tr>
						</thead>
						<tbody id="jcTb">
							<c:forEach items="${vo.itemList}" var="e" varStatus="s">
								<tr id="${e.id}" key="${s.index}">
									<td align="center">
										${s.index+1}
										<input type="hidden" value="${e.id}" name="itemList[${s.index}].id" />
										<input type="hidden" value="${e.trVo.id}" name="itemList[${s.index}].trVo.id" />
									</td>
									<td align="center">
										${e.trVo.sampVo.sampTypeName}
									</td>
									<td align="center">
										${e.cyDate}
									</td>
									<td>
										${e.trVo.sampVo.sampCode}
	                                     <input type="hidden" id="sampCode_${s.index}_${rs.index}" class="form-control" value="${r.sampVo.sampCode}">
									</td>
									<c:choose>
										<c:when test="${vo.st=='气'}">
											<td>
			                                    ${e.trVo.value}
											</td>
											<td>
			                                   ${e.trVo.sl}
											</td>
										</c:when>
										<c:otherwise>
											<td>
			                                   ${e.trVo.value}
											</td>
										</c:otherwise>
									</c:choose>
									<td >
										${e.result}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="active"><label class="pull-right">原始记录:</label></td>
							<td colspan="3">
								 <div class="input-group-btn">
										<c:if test="${vo.filePath!='' && vo.filePath!=null}">
											<button tabindex="-1" class="btn btn-default" type="button" onclick="fnOpenTemp(this)">在线查看</button>
										</c:if>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">上传附件:</label></td>
							<td colspan="3" >
								<div id="removeFile">
									<c:forEach items="${vo.fileList}" var="e" varStatus="v">
										<div style="float: left; margin-right: 10px;">
											<c:choose>
												<c:when test="${fn:contains(e.fileName,'.doc')||fn:contains(e.fileName,'.xls')||fn:contains(e.fileName,'.ppt')||fn:contains(e.fileName,'.pdf')}">
													<a href="javascript:void(0);" onclick="fnShowFile('${e.id}');" class="btn btn-w-m btn-info">${e.fileName}</a>
												</c:when>
												<c:when test="${fn:contains(e.fileName,'.jpg')||fn:contains(e.fileName,'.png')||fn:contains(e.fileName,'.jpeg')||fn:contains(e.fileName,'.gif')}">
													<a href="javascript:openImg('${e.filePath}','${e.fileName}');" class="btn btn-w-m btn-info">${e.fileName}</a>
												</c:when>
												<c:otherwise>
													<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
												</c:otherwise>
											</c:choose>
										</div>
									</c:forEach>
								</div>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">检&nbsp;测&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								${vo.testMan}
							</td>
							<td class="active"><label class="pull-right">检测时间:	</label></td>
							<td>
								${vo.testTime} ~
								${vo.testEndTime}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.testMsg}
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../include/js.jsp"%>
<!-- Sweet alert -->
<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
<%@ include file="../../../sys/open/open_img.jsp"%>
</body>
<script>
$('input[type="file"]').prettyFile();
function fnShowFile(id){
	POBrowser.openWindow('${basePath}sys/files/showFile.do?id='+id,'width=1200px;height=800px;');
} 
function fnTask(id,no){
	parent.layer.open({
		title:'任务【'+no+'】',	
		type: 2,
		area: ['1000px','85%'],
		fix: false, //不固定
		maxmin: true,
		content: '/bus/task/show.do?id='+id
	});
} 
function fnOpenTemp(obj){
	POBrowser.openWindow('${basePath}bus/itemTest/showTemp.do?id=${vo.id}','width=1200px;height=800px;');
	$(obj).removeClass('btn-default')
	$(obj).addClass('btn-info')
}
</script>
</html>
