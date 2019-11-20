<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
						<input name="id" value="${vo.id}" type="hidden" />
					  <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">上传文件:</label></td>
								<td class="width-35" colspan="3">
									<c:if test="${fn:length(vo.id)>0}">
										<a href="download.do?filePath=${vo.filePath}&trueName=${vo.fileName}" class="btn btn-w-m btn-info">下载文件</a>
										<c:if test="${fn:contains(vo.type,'doc')||fn:contains(vo.type,'xls')||fn:contains(vo.type,'ppt')}">
											<a href="javascript:void(0);" onclick="fnShowFile();" class="btn btn-w-m btn-info">打开文件</a>
										</c:if>
										<c:if test="${vo.type eq '.pdf'}">
											<a href="javascript:openFile('${basePath}sys/open/open.do','${vo.filePath}','pdf');" class="btn btn-w-m btn-info">打开文件</a>
										</c:if>
										<c:if test="${fn:contains(vo.type,'jpg')||fn:contains(vo.type,'png')||fn:contains(vo.type,'jpeg')||fn:contains(vo.type,'gif')}">
											<a href="javascript:openImg('${vo.filePath}','${vo.fileName}');" class="btn btn-w-m btn-info">打开文件</a>
										</c:if>
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">文件编号:</label></td>
								<td class="width-35">
									${vo.code }
								</td>
								<td class="width-15 active"><label class="pull-right">文件名称:</label></td>
								<td class="width-35">
									${vo.title }
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">文件来源:</label></td>
								<td>
									${vo.source }
								</td>
								<td class="active"><label class="pull-right">保密等级:</label></td>
								<td>
									${vo.dj }
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">文件目录:</label></td>
								<td class="width-35">
									${vo.path}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述:</label></td>
								<td colspan="3">
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">落实情况:</label></td>
								<td colspan="3">
									${vo.result}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.remark}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">登&nbsp;&nbsp;记&nbsp;人:</label></td>
								<td>
									${vo.userName}
								</td>
								<td class="active"><label class="pull-right">申请日期:</label></td>
								<td>
									${vo.date}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">签&nbsp;&nbsp;发&nbsp;人:</label></td>
								<td>
									${vo.auditName}
								</td>
								<td class="active"><label class="pull-right">签发日期:</label></td>
								<td>
									${vo.auditTime}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">下&nbsp;&nbsp;发&nbsp;人:</label></td>
								<td>
									${vo.sendName}
								</td>
								<td class="active"><label class="pull-right">下发日期:</label></td>
								<td>
									${vo.sendTime}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">指&nbsp;&nbsp;定&nbsp;人:</label></td>
								<td>
									${vo.userNames}
								</td>
								<td class="active"><label class="pull-right">查&nbsp;&nbsp;看&nbsp;人:</label></td>
								<td>
									${vo.viewEdNames}
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<!--PageOffice.js -->
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
<%@ include file="../../sys/open/open_img.jsp"%>
	<script>
	function fnShowFile(){
		POBrowser.openWindow('${basePath}office/dt/open.do?id=${vo.id}','width=1200px;height=800px;');
	}
	</script>
</body>
</html>
