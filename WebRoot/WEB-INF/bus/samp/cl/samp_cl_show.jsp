<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../../include/status.jsp"%>

<style type="text/css">
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">样品信息</a></li>
					<li><strong>查看</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">任务编号:</label></td>
							<td class="width-35">
								${vo.taskVo.no}
							</td>
							<td class="width-15 active"><label class="pull-right">样品编号:</label></td>
							<td class="width-35">
								${vo.sampCode} 
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
							<td class="width-35">
								${vo.custVo.custName} 
							</td>
							<td class="width-15 active"><label class="pull-right">采样点位:</label></td>
							<td class="width-35">
								${vo.pointName} 
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">样品类型:</label></td>
							<td class="width-35">
								${vo.sampTypeName} 
							</td>
							<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
							<td class="width-35">
								${vo.sampName} 
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">收样日期:</label></td>
							<td class="width-35">
								${vo.reciveDate}
							</td>
							 <td class="width-15 active"><label class="pull-right">保存地点:</label></td>
							<td class="width-35">
								${vo.saveAddress}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">样品处理要求:</label></td>
							<td class="width-35">
								${vo.dealRequest}
							</td>
							<td class="width-15 active"><label class="pull-right">保存条件:</label></td>
							<td class="width-35">
								${vo.saveRequest}
							</td>
						</tr>
						<c:choose>
							<c:when test="${type=='水'}">
								<tr>
									<td class="width-15 active"><label class="pull-right">样品性状:</label></td>
									<td class="width-35">
										${vo.xz}
									</td>
									<td class="width-15 active"><label class="pull-right">是否添加添加剂:</label></td>
									<td class="width-35">
										${vo.tjj}
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="width-15 active"><label class="pull-right">样品性状:</label></td>
									<td class="width-35" colspan="3">
										${vo.xz}
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
						<tr>
							<td class="width-15 active"><label class="pull-right">采/送样人:</label></td>
							<td class="width-35">
								${vo.taskVo.cyName}
							</td>
							<td class="width-15 active"><label class="pull-right">采/送样日期:</label></td>
							<td class="width-35">
								${vo.taskVo.cyDate}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">保管部门:</label></td>
							<td class="width-35">
								${vo.deptName}
							</td>
							<td class="width-15 active"><label class="pull-right">保&nbsp;管&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								${vo.reciveUser}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">是否留样:</label></td>
							<td class="width-35">
								${vo.ly}
							</td>
							<td class="width-15 active"><label class="pull-right">当前状态:</label></td>
							<td class="width-35">
								<c:choose>
									<c:when test="${vo.status=='SAMP_00'}">
										库存中
									</c:when>
									<c:when test="${vo.status=='SAMP_10'}">
										已留样
									</c:when>
									<c:otherwise>
										已处理
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<c:if test="${vo.ly=='是'}">
							<tr>
								<td class="width-15 active"><label class="pull-right">留样截止日期:</label></td>
								<td class="width-35">
									${vo.bcqx}
								</td>
								<td class="width-15 active"><label class="pull-right">留样原因:</label></td>
								<td class="width-35">
									${vo.lyReason}
								</td>
							</tr>
						</c:if>
						<c:if test="${vo.status=='SAMP_30'}">
							<tr>
								<td class="width-15 active"><label class="pull-right">处&nbsp;理&nbsp;&nbsp;人:</label></td>
								<td class="width-35">
									${vo.dealUser}
								</td>
								<td class="width-15 active"><label class="pull-right">处理日期:</label></td>
								<td class="width-35">
									${vo.dealDate}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">处理说明:</label></td>
								<td colspan="3">
									${vo.dealRemark}
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.remark}
							</td>
						</tr>
					</table>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
					  <legend style="width:150px;">样品出库 / 归还记录</legend>
					</fieldset>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<thead>
							<tr>
								<th width="50">序号</th>
								<th width="150">领用/归还人</th>
								<th width="150">领用/归还时间</th>
								<th>说明</th>
								<th width="100">类型</th>
								<th width="100">状态</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${suList}" var="e" varStatus="v">
								<tr>
									<td>${v.index+1}</td>
									<td>${e.useName}</td>
									<td>${e.useDate}</td>
									<td>${e.content}</td>
									<td>${e.type}</td>
									<td>${e.status}</td>
								</tr>
							</c:forEach>
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
	<%@ include file="../../../include/js.jsp"%>
</body>
</html>
