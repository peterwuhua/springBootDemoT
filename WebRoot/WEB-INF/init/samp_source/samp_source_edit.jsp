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
			<div class="ibox-title">
					<ol class="breadcrumb">
						<li><a>采样方法</a></li>
						<li><strong>编辑</strong></li>
					</ol>
				</div>
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'update4Data.do':'add4Data.do'}" class="form-horizontal" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" id="id" value="${vo.id}" type="hidden" />
					</c:if>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">方法编号:</label></td>
								<td class="width-35"><input id="code" name="code"  class="form-control" maxlength=32 type="text" value="${vo.code}" /></td>
								<td class="width-15 active"><label class="pull-right">方法名称:</label></td>
								<td class="width-35"><input id="name" class="form-control required" validate="required" maxlength=64 name="name" type="text" value="${vo.name}" /></td>
							</tr>
							<tr>
								<th class="active"><label class="pull-right">上传文件:</label></th>
								<td>
									<input type="hidden" name="filePath" id="filePath" value="${vo.filePath}"/>
									<input type="hidden" name="fileName" id="fileName" value="${vo.fileName}"/>
									<input type="file" name="file"  accept=".pdf"/>
								</td>
								<td colspan="2" id="removeFile">
									<c:if test="${fn:length(vo.fileName)>0}">
										<a href="javascript:;" onclick="openPdf('${basePath}${vo.filePath}','${vo.fileName}')" class="btn btn-w-m btn-info">${vo.fileName}</a>
										<button type="button" class="btn btn-danger " onclick="removeFile();">删除</button>
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序:</label></td>
								<td class="width-35"><input id="sort" name="sort" class="form-control digits" maxlength=8 type="text" value="${vo.sort}" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td class="width-35" colspan="3">
									<textarea maxlength="128" rows="2" cols="60"  id="remark" name="remark" class="form-control">${vo.remark}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('save4Data.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</button>
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
 
	$('input[type="file"]').prettyFile();
	function removeFile(){
		 $("#removeFile").html("");
		 $("#filePath").val("");
		 $("#fileName").val("");
	}
</script>
</body>
</html>
