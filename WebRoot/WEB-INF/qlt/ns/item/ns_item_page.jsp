<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
.rowBG{
   color:#ed5565;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>内审要素</a></li>
						<li><strong>编辑</strong></li>
					</ol>
				</div>
				<div class="pull-right">
					<a style="margin-top: -8px;" class="btn btn-w-m btn-info" href="javascript:;" onclick="fnEdit('');"><i class="fa fa-plus" aria-hidden="true"></i> 要素</a>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th style="width: 10%">要素</th>
								<th style="width: 30%">检查项目</th>
								<th >检查重点</th>
								<th style="width:100px;">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${itemList}" var="e" varStatus="v">
								<c:choose>
									<c:when test="${e.subList==null}">
										<tr>
											<td><c:if test="${e.code!=''}">${e.code}、</c:if>${e.name}</td>
											<td></td>
											<td></td>
											<td>
												<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:fnEdit('${e.id}')">编辑</a>
												<a class="btn btn-outline btn-danger btn-xs" title="删除" href="javascript:deleteOne('${e.id}')">删除</a>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${e.subList}" var="e1" varStatus="v1">
											<c:choose>
												<c:when test="${e1.subList==null}">
													<tr>
														<c:if test="${v1.index==0}">
															<td rowspan="${e.count}"><c:if test="${e.code!=''}">${e.code}、</c:if>${e.name}</td>
														</c:if>
														<td rowspan="${e1.count}"><c:if test="${e1.code!=''}">${e1.code}、</c:if>${e1.name}</td>
														<td></td>
														<c:if test="${v1.index==0}">
															<td rowspan="${e.count}">
																<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:fnEdit('${e.id}')">编辑</a>
																<a class="btn btn-outline btn-danger btn-xs" title="删除" href="javascript:deleteOne('${e.id}')">删除</a>
															</td>
														</c:if>
													</tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${e1.subList}" var="e2" varStatus="v2">
														<tr>
															<c:if test="${v1.index==0 && v2.index==0}">
																<td rowspan="${e.count}"><c:if test="${e.code!=''}">${e.code}、</c:if>${e.name}</td>
															</c:if>
															<c:if test="${v2.index==0}">
																<td rowspan="${e1.count}"><c:if test="${e1.code!=''}">${e1.code}、</c:if>${e1.name}</td>
															</c:if>
															<td>${e2.name}</td>
															<c:if test="${v1.index==0 && v2.index==0}">
																<td rowspan="${e.count}">
																	<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:fnEdit('${e.id}')">编辑</a>
																	<a class="btn btn-outline btn-danger btn-xs" title="删除" href="javascript:deleteOne('${e.id}')">删除</a>
																</td>
															</c:if>
														</tr>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../include/js.jsp"%>
<script>
function fnEdit(id){
	layer.open({
		title:'编辑',	
		type: 2,
		area: ['1000px', '450px'],
		fix: false, //不固定
		maxmin: true,
		content: 'edit.do?id='+id,
		btn: ['确定','关闭'], //按钮
		yes: function(index, layero){
			var iframeWin = window[layero.find('iframe')[0]['name']];
			  iframeWin.submitSave();
		}
	});
}
function deleteOne(id){
	layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
		$("#listForm").attr("action","update2del.do?ids="+id);
		$("#listForm").submit();
	});
}
</script> 
</body>
</html>
