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
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">计划名称:</label></td>
								<td class="width-35">
									${vo.title}
								</td>
								<td class="width-15 active"><label class="pull-right">评审时间:</label></td>
								<td class="width-35">
									${vo.psDate}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
								<td class="width-35">
									${vo.deptName}
								</td>
								<td class="width-15 active"><label class="pull-right">评审主持人:</label></td>
								<td class="width-35">
									${vo.zcName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">评审地点:</label></td>
								<td>
									${vo.address}
								</td>
								<td class="active"><label class="pull-right">评审依据:</label></td>
								<td>
									${vo.stand}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内审组长:</label></td>
								<td>
									${vo.headName}
								</td>
								<td class="active"><label class="pull-right">审核人员:</label></td>
								<td>
									${vo.psName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">计划详情:</label></td>
								<td colspan="3">
									${vo.content}
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
							<tr>
								<td class="active"><label class="pull-right ">评审报告:</label></td>
								<td colspan="3">
			                  	 	<div class="btn-group" id="editReprt">
				                       <button class="btn btn-info" type="button" onclick="fnShowReport();">查看报告</button>
				                    </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">编&nbsp;&nbsp;制&nbsp;&nbsp;人:</label></td>
								<td>
									${vo.reportName}
								</td>
								<td class="active"><label class="pull-right ">编制日期:</label></td>
								<td>
									${vo.reportDate}
								</td>
							</tr>
						</tbody>
					</table>
					<table class="table table-bordered">
						<thead>
							<tr>
								<td>记录人</td>
								<td>记录日期</td>
								<td>评审内容</td>
								<td>评审中发现的问题</td>
								<td>评审结论</td>
								<td>实施纠正/改进的措施</td>
								<td>检查</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${vo.recordList}" var="e" varStatus="v">
								<tr>
									<td>${e.userName}</td>
									<td>${e.date}</td>
									<td>${e.content}</td>
									<td>${e.problem}</td>
									<td>${e.result}</td>
									<td>${e.measures}</td>
									<td>${e.checks}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
</body>
</html>
