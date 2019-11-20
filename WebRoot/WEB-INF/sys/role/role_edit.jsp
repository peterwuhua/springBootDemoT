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
					<li><a href="javascript:backMainPage();">角色</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">名称:</label></td>
								<td class="width-35"><input id="name" placeholder="名称" class="form-control required" validate="required"  maxlength=64 name="name" type="text" value="${vo.name}" /></td>
								<td class="width-15 active"><label class="pull-right">code:</label></td>
								<td class="width-35"><input id="code" name="code" class="form-control required" validate="required" maxlength=64 placeholder="编码" type="text" value="${vo.code }" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">排序:</label></td>
								<td><input id="sort" name="sort" class="form-control " placeholder="排序" type="text" value="${vo.sort }" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">说明:</label></td>
								<td colspan="3">
									<textarea  id="describtion" name="describtion" class="form-control ">${vo.describtion }</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('save.do');">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
							</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();">
								<i class="fa fa-check" aria-hidden="true"></i> 保存并返回
							</button>
							<a class="btn btn-w-m btn-white" href="javascript:;" onclick="backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
</html>
