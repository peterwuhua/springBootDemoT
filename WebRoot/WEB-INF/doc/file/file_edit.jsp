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
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">文件</a></li>
					<li><strong>信息</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
					<tbody>
						<tr>
							<td class="width-15 active"><label class="pull-right">名称:</label></td>
							<td class="width-35">${vo.name}</td>
							<td class="width-15 active"><label class="pull-right">标题:</label></td>
							<td class="width-35">${vo.title}</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">标记:</label></td>
							<td class="width-35">${vo.sign}</td>
							<td class="width-15 active"><label class="pull-right">大小:</label></td>
							<td class="width-35">${vo.size}</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">说明:</label></td>
							<td class="width-35" colspan="3">${vo.describtion}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>角色信息</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content">
				<form method="post" action="save.do?reSave=0" class="form-horizontal">
					<input name="id" value="${vo.id}" type="hidden" /> <input type="hidden" name="dirIds" id="dirIds" value="${vo.dirIds}">
					<c:forEach items="${roleList}" var="e" varStatus="s">
						<label class="checkbox-inline i-checks ">
							<div class="icheckbox_square-green">
								<input type="checkbox" name="roleIds" value="${e.id}" <c:if test="${fn:indexOf(roleSelectIds, e.id)>-1}">checked="checked"</c:if>>
							</div>${e.name}
						</label>
					</c:forEach>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="botton" onclick="fnSave();"><i class="fa fa-floppy-o" aria-hidden="true"></i> 确定</button>
							<a href="javascript:backMainPage();" class="btn btn-w-m btn-white"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script>
		$(document).ready(function() {
			$(".i-checks").iCheck({
				checkboxClass : "icheckbox_square-green",
				radioClass : "iradio_square-green",
			})
		});

		function fnSave() {
			$('form').attr('action', 'save.do?reSave=1');
			$('form').submit();
		}
	</script>
</body>
</html>
