<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<div class="tabs-container">
					<div class="tab-content">
								<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
									<tbody>
										<tr>
											<th class="width-15 active"><label class="pull-right">客户名称:</label></th>
											<td class="width-35">${vo.customerVo.name}</td>
											<td class="width-15 active"><label class="pull-right">合同编号 :</label></td>
											<td class="width-35">${vo.code}</td>
										</tr>
										<tr>
											<td class="width-15 active"><label class="pull-right">检测内容 :</label></td>
											<td class="width-35">${vo.name}</td>
											<td class="width-15 active"><label class="pull-right">合同批次:</label></td>
											<td class="width-35">${vo.num}</td>
										</tr>
										<tr>
											<td class="width-15 active"><label class="pull-right">开始日期:</label></td>
											<td class="width-35">${vo.sdate}</td>
											<td class="width-15 active"><label class="pull-right">结束日期:</label></td>
											<td class="width-35">${vo.edime}</td>
										</tr>
										<tr>
											<td class="width-15 active"><label class="pull-right">优惠折扣:</label></td>
											<td class="width-35">${vo.discount}</td>
											<td class="width-15 active"><label class="pull-right">合同金额:</label></td>
											<td class="width-35">${vo.contractSum}</td>
										</tr>
										<tr>
											<td class="width-15 active"><label class="pull-right">付款类型:</label></td>
											<td class="width-35">${vo.payType}</td>
											<td class="width-15 active"><label class="pull-right">付款方式:</label></td>
											<td class="width-35">${vo.payWay}</td>
										</tr>
										<tr>
											<td class="width-15 active"><label class="pull-right">联系人:</label></td>
											<td class="width-35">${vo.contacts}</td>
											<td class="width-15 active"><label class="pull-right">联系电话:</label></td>
											<td class="width-35">${vo.contactPhone}</td>
										</tr>
										<tr>
											<td class="width-15 active"><label class="pull-right">负责人:</label></td>
											<td class="width-35">${vo.saler}</td>
											<td class="width-15 active"><label class="pull-right">执行状态:</label></td>
											<td class="width-35">${vo.status}</td>
										</tr>
										<tr>
											<td class="width-15 active"><label class="pull-right">备注:</label></td>
											<td>${vo.remark}</td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td class="active"><label class="pull-right">合同原件:</label></td>
											<td colspan="2"><c:if test="${fn:length(vo.path)>0}">
													<a href="download.do?filePath=${vo.path}&trueName=${vo.trueName}" class="btn btn-w-m btn-info">下载文件</a>
												</c:if></td>
										</tr>
									</tbody>
								</table>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../../include/js.jsp"%>
		<script>
</script>
</body>
</html>
