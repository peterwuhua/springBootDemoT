<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.zhuozhengsoft.pageoffice.PageOfficeLink"%>
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
					<li><a href="javascript:backMainPage();">模板</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'update4Data.do':'add4Data.do'}" class="form-horizontal" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">模块:</label></td>
								<td class="width-35"><input id="busType" placeholder="模块" class="form-control required" validate="required" maxlength=64 name="busType" type="text" value="${vo.busType}" /></td>
								<td class="width-15 active"><label class="pull-right">类型:</label></td>
								<td class="width-35">
									<select name="type" class="input-sm form-control input-s-sm inline"">
										<c:choose>
											<c:when test="${'export' eq vo.type}">
												<option selected="selected">export</option>
												<option>import</option>
											</c:when>
											<c:otherwise>
												<option>export</option>
												<option selected="selected">import</option>
											</c:otherwise>
										</c:choose>
									</select>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">名称:</label></td>
								<td><input id="name" placeholder="XXXXX.xls" class="form-control required" validate="required" maxlength=64 name="name" type="text" value="${vo.name}" /></td>
								<td class="active"><label class="pull-right">code:</label></td>
								<td><input id="code" name="code" class="form-control required" validate="required" maxlength=64 placeholder="模块名-功能-类型.xls(sys-user-import.xls)" type="text" value="${vo.code }" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">模板文件:</label></td>
								<td><input id="file" name="file" class="form-control" placeholder="内容" type="file" /></td>
								<td colspan="2"><c:if test="${fn:length(vo.id)>0 && fn:length(vo.path)>0}">
										<%
												cn.demi.base.system.vo.TemplateVo t = (cn.demi.base.system.vo.TemplateVo)request.getAttribute("vo");
												%>
										<a href="downtemp.do?type=${vo.type}&templateName=${vo.code}&trueName=${vo.name}" class="btn btn-w-m btn-info">下载文件</a>
										<a href="<%=PageOfficeLink.openWindow(request,basePath+"sys/template/open.do?id="+t.getId()+"&type="+t.getType()+"&code="+t.getCode()+"&name="+t.getName(),"width=900px;height=600px;")%>" class="btn btn-w-m btn-info">打开文件</a>
									</c:if></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">排序:</label></td>
								<td><input id="sort" name="sort" class="form-control " placeholder="排序" type="text" value="${vo.sort }" /></td>
								<td class="active"><label class="pull-right">说明:</label></td>
								<td><input id="describtion" name="describtion" class="form-control " placeholder="说明" type="text" value="${vo.describtion }" /></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('save4Data.do');">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
							</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();">
								<i class="fa fa-check" aria-hidden="true"></i> 保存并返回
							</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script>
	    $('input[type="file"]').prettyFile();
    </script>
</body>
</html>
