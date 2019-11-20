<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

</head>
<body class="gray-bg">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<ol class="breadcrumb">
						<li><a>评价标准</a></li>
						<li><strong>查看</strong></li>
					</ol>
				</div>
				<div class="ibox-content">
					<form method="post" class="form-horizontal" >
	                    	<input name="id" value="${vo.id}" type="hidden" />
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tbody>
								<tr>
									<td class="width-15 active"><label class="pull-right">标准名称:</label></td>
									<td class="width-35">${vo.name}</td>
									<td class="width-15 active"><label class="pull-right">标准编号:</label></td>
									<td class="width-35">${vo.code}</td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">英文名称:</label></td>
									<td colspan="3">
										${vo.enName}
									 </td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">样品类别:</label></td>
									<td>
										${vo.sampTypeName}
									</td>
									<td class="active"><label class="pull-right">标准类型:</label></td>
									<td>
									 	 ${vo.type}
									</td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">发布日期:</label></td>
									<td>${vo.fbDate}</td>
									<td class="active"><label class="pull-right">实施日期:</label></td>
									<td>${vo.ssDate}</td>
								</tr>
								<tr>
									<th class="active"><label class="pull-right">上传文件:</label></th>
									<td colspan="3" id="removeFile">
										<c:if test="${fn:length(vo.fileName)>0}">
											<a href="javascript:;" onclick="openPdf('${basePath}${vo.filePath}','${vo.fileName}')" class="btn btn-w-m btn-info">${vo.fileName}</a>
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</label></td>
									<td>
										${vo.status}
									</td>
									<td class="active"><label class="pull-right">替代标准:</label></td>
									<td>
										${vo.pname}
									</td>
								</tr>
								<tr>
									<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
									<td class="width-35" colspan="3">
										${vo.remark}
									</td>
								</tr>
							</tbody>
						</table>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-12 col-sm-offset-1">
								<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	<%@ include file="../../include/js.jsp"%>
	<script src="${basePath}open/openPdf.js" type="text/javascript"></script>
</body>
<script>
 
	var $$ = function(func){ 
        if (document.addEventListener) {  
            window.addEventListener("load", func, false);  
        }  
        else if (document.attachEvent) {  
            window.attachEvent("onload", func);  
        }  
   } 
</script>
</html>
