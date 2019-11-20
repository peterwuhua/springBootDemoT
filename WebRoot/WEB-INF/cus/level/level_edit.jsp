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
					<h5>
						<ol class="breadcrumb">
							<li><a href="javascript:backMainPage();">客户等级</a></li>
							<li><strong>编辑</strong></li>
						</ol>
					</h5>
					<div class="ibox-tools">
						<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
					</div>
				</div>
				<div class="ibox-content">
					<%@ include file="../../include/status.jsp"%>
					<form method="post" action="${fn:length(vo.id)>0? 'update.do':'add.do'}" class="form-horizontal">
                    <c:if test="${fn:length(vo.id)>0}">
                    	<input name="id" value="${vo.id}" type="hidden" />
                    </c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">名称:</label></td>
								<td class="width-35"><input id="name" name="name"
									class="form-control required" maxlength="32" placeholder="名称" type="text" value="${vo.name }" />
								</td>
								<td class="width-15 active"><label class="pull-right">编码:</label></td>
								<td class="width-35"><input id="code" name="code"
									class="form-control required" maxlength="32" placeholder="编号" type="text" value="${vo.code }" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">说明:</label></td>
								<td class="width-85"><input id="remark" name="remark"
									class="form-control " maxlength="128" placeholder="说明" type="text" value="${vo.remark}" />
								</td>
							</tr>
						</tbody>
					</table>
					 <div class="hr-line-dashed"></div>
                     <div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<button class="btn btn-success" type="button" onclick="formSubmit('save.do');">保存</button>
						<button class="btn btn-primary" type="submit">保存并返回</button>
						<a class="btn btn-default" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
					</div>
					</div>
				</div>
			</div>
		</div>
	<%@ include file="../../include/js.jsp"%>
</body>
</html>
