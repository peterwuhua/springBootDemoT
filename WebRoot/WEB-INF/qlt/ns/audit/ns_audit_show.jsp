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
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
#ct_tb >tr >td{
	padding: 2px;
}
#ct_tb .btn{
	padding-left: 2px;
	padding-right: 2px;
}
.mtd{
	padding-left: 6px !important;
	padding-right: 2px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
					<tr>
						<td class="width-15 active"><label class="pull-right">计划名称:</label></td>
						<td class="width-35">
							${vo.title} 
						</td>
						<td class="width-15 active"><label class="pull-right">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份:</label></td>
						<td class="width-35">
							${vo.year}
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
						<td class="width-35">
							${vo.deptName}
						</td>
						<td class="width-15 active"><label class="pull-right">审核目的:</label></td>
						<td class="width-35">
							${vo.purpose}
						</td>
					</tr>
					<tr>
						<td class="active"><label class="pull-right">审核范围:</label></td>
						<td>
							${vo.ranges}
						</td>
						<td class="active"><label class="pull-right">审核依据:</label></td>
						<td>
							${vo.stand}
						</td>
					</tr>
					<tr>
						<td class="active"><label class="pull-right">审核方式:</label></td>
						<td>
							${vo.auditType}
						</td>
						<td class="active"><label class="pull-right">内审地点:</label></td>
						<td>
							${vo.address}
						</td>
					</tr>
					<tr>
						<td class="active"><label class="pull-right">内审组长:</label></td>
						<td>
							${vo.headName}
						</td>
						<td class="active"><label class="pull-right">审核人员:</label></td>
						<td>
							${vo.nsName}
						</td>
					</tr>
					<tr>
						<td class="active"><label class="pull-right">编&nbsp;&nbsp;制&nbsp;&nbsp;人:</label></td>
						<td>
							${vo.userName}
						</td>
						<td class="active"><label class="pull-right ">编制日期:</label></td>
						<td>
							${vo.date}
						</td>
					</tr>
					<tr>
						<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
						<td colspan="3">
							${vo.remark}
						</td>
					</tr>
				</table>
				<table class="table table-bordered">
					<thead>
						<tr>
							<td colspan="16" style="background-color:#fff;border: 0px;">要素信息</td>
						</tr>
						<tr>
							<th style="width: 80px;text-align: center;">条款</th>
							<th style="text-align: center;">要素</th>
							<th style="width: 30px;">一月</th>
							<th style="width: 30px;">二月</th>
							<th style="width: 30px;">三月</th>
							<th style="width: 30px;">四月</th>
							<th style="width: 30px;">五月</th>
							<th style="width: 30px;">六月</th>
							<th style="width: 30px;">七月</th>
							<th style="width: 30px;">八月</th>
							<th style="width: 30px;">九月</th>
							<th style="width: 30px;">十月</th>
							<th style="width: 30px;">十一月</th>
							<th style="width: 30px;">十二月</th>
							<th style="width: 120px;text-align: center;">负责人</th>
							<th style="width: 150px;text-align: center;">协助人</th>
						</tr>
					</thead>
					<tbody id="ct_tb">
						<c:forEach items="${vo.detailList}" var="e" varStatus="v">
							<tr>
								<td>
									${e.code}
								</td>
								<td>
									${e.name}
								</td>
								 <c:forEach begin="1" end="12" var="i">
										<td class="mtd">
											<c:set var="a"><c:out value="${i}"/></c:set>
											<c:set var="b" value="${i},"/>
											<c:set var="c" value=",${i}"/>
											<c:set var="d" value=",${i},"/>
											<div class="checkbox i-checks">
												<div class="icheckbox_square-green">
													<c:choose>
														<c:when test="${e.months==a || fn:startsWith(e.months,b) || fn:endsWith(e.months,c) || fn:contains(e.months,d)}">
															<input type="checkbox" name="detailList[${v.index}].months" value="${i}" checked="checked" disabled>
														</c:when>
														<c:otherwise>
															<input type="checkbox" name="detailList[${v.index}].months" value="${i}" disabled>
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</td>
									</c:forEach>
								<td>
									${e.headName}
								</td>
								<td>
									${e.xzName}
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<table class="table table-bordered">
						<tr>
							<td colspan="4" style="background-color:#fff;">审核信息</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">审&nbsp;&nbsp;核&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								${vo.auditName}
							</td>
							<td class="width-15 active"><label class="pull-right ">审核日期:</label></td>
							<td>
								${vo.auditDate}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${logVo.msg}
							</td>
						</tr>
					</table>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$(".i-checks").iCheck({
		checkboxClass : "icheckbox_square-green",
		radioClass : "iradio_square-green",
	});
});
</script>
</body>
</html>
