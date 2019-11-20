<%@ page language="java" import="java.util.*,com.zhuozhengsoft.pageoffice.*" pageEncoding="UTF-8"%>
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
					<li><a href="javascript:backMainPage();">文件</a></li>
					<li><strong>历史</strong></li>
				</ol>
			</div>
			<c:forEach items="${fileHistoryList}" var="e" varStatus="s">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<c:if test="${e.state eq '现行'}">
							<h5>
								<strong style="color: green;">${e.name}</strong>
							</h5>
						</c:if>
						<c:if test="${e.state eq '作废'}">
							<h5>
								<strong>${e.name}</strong>
							</h5>
						</c:if>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content" <c:if test="${s.index gt 0}"> style="display:none;"</c:if>>
						<form method="post" action="" class="form-horizontal">
							<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<c:if test="${e.state eq '现行'}">
											<td class="width-15 active"><label class="pull-right">状态:</label></td>
											<td class="width-35"><strong style="color: green;">${e.state}</strong></td>
										</c:if>
										<c:if test="${e.state eq '作废'}">
											<td class="width-15 active"><label class="pull-right">状态:</label></td>
											<td class="width-35"><strong style="color: red;">${e.state}</strong></td>
										</c:if>
										<td class="width-15 active"><label class="pull-right">操作:</label></td>
										<c:set var="s" value="${e}" scope="request" />
										<c:if test="${fn:length(e.relativePath)>0}">
											<td class="width-35"><c:if test="${fn:length(e.id)>0}">
													<%
														cn.demi.doc.vo.DocumentVo t = (cn.demi.doc.vo.DocumentVo)request.getAttribute("s");
													%>
													<a href="download.do?filePath=${e.relativePath}&trueName=${e.name}${e.type}" class="btn btn-w-m btn-info">下载文件</a>
													<c:if test="${fn:contains(e.type,'doc')||fn:contains(e.type,'xls')||fn:contains(e.type,'ppt')}">
														<a href="<%=PageOfficeLink.openWindow(request,basePath+"/doc/document/open.do?id="+t.getId(),"width=900px;height=600px;")%>" class="btn btn-w-m btn-info">打开文件</a>
													</c:if>
													<c:if test="${e.type eq '.pdf'}">
														<a href="javascript:openFile('${basePath}sys/open/open.do','${e.relativePath}','pdf');" class="btn btn-w-m btn-info">打开文件</a>
													</c:if>
													<c:if test="${fn:contains(e.type,'jpg')||fn:contains(e.type,'png')||fn:contains(e.type,'jpeg')||fn:contains(e.type,'gif')}">
														<a href="javascript:openImg('${e.relativePath}','${e.name}');" class="btn btn-w-m btn-info">打开文件</a>
													</c:if>
												</c:if></td>
										</c:if>
										<c:if test="${fn:length(e.relativePath)==0}">
											<td class="width-35"><strong style="color: red;">未上传文件</strong></td>
										</c:if>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">文件夹:</label></td>
										<td class="width-35">${e.categoryVo.name}</td>
										<td class="width-15 active"><label class="pull-right">名称:</label></td>
										<td class="width-35">${e.name}</td>

									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">标题:</label></td>
										<td class="width-35">${e.title}</td>
										<td class="width-15 active"><label class="pull-right">标记:</label></td>
										<td class="width-35">${e.sign}</td>

									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">大小:</label></td>
										<td class="width-35">${e.size}</td>
										<td class="width-15 active"><label class="pull-right">说明:</label></td>
										<td class="width-35" colspan="3">${e.describtion}</td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			</c:forEach>
			<div class="hr-line-dashed"></div>
			<div class="form-group">
				<div class="col-sm-12 col-sm-offset-1">
					<a href="gridPage.do?dirIds=${dirIds}" class="btn btn-w-m btn-white"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
				</div>
			</div>
		</div>
		<%@ include file="../../include/js.jsp"%>
		<%@ include file="../../sys/open/open_img.jsp"%>
		<script>
		$('input[type="file"]').prettyFile();
	</script>
</body>
</html>
