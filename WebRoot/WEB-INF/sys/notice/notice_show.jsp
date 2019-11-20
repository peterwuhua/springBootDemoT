<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">公告</a></li>
					<li><strong>详情</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="" class="form-horizontal">
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">主题:</label></td>
								<td colspan="3">${vo.subject }</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内容:</label></td>
								<td colspan="3">${vo.content }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">发布日期:</label></td>
								<td>${vo.sendTime}</td>
								<td class="width-15 active"><label class="pull-right">截至日期:</label></td>
								<td>${vo.endTime }</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">发布人:</label></td>
								<td colspan="3">${vo.userName}</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">通知部门:</label></td>
								<td colspan="3">${vo.orgName}</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
</html>
