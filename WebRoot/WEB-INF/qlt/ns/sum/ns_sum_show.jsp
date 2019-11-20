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
						<td class="active"><label class="pull-right">汇&nbsp;&nbsp;总&nbsp;&nbsp;人:</label></td>
						<td>
							${vo.sumName}
						</td>
						<td class="active"><label class="pull-right ">汇总日期:</label></td>
						<td>
							${vo.sumDate}
						</td>
					</tr>
					<tr>
						<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
						<td colspan="3">
							${vo.remark}
						</td>
					</tr>
					<tr>
						<td class="active"><label class="pull-right ">内审报告:</label></td>
						<td colspan="3">
	                  	 	<div class="btn-group" id="editReprt">
		                       <button class="btn btn-info" type="button" onclick="fnShowReport();">查看报告</button>
		                    </div>
						</td>
					</tr>
				</table>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th style="width: 50px;text-align: center;">月份</th>
							<th style="text-align: center;">要素</th>
							<th style="text-align: center;">情况概述</th>
							<th style="text-align: center;">审核结果</th>
							<th style="text-align: center;">进度</th>
							<th style="text-align: center;">报告</th>
						</tr>
					</thead>
					<tbody id="ct_tb">
						<c:forEach items="${vo.recordList}" var="e" varStatus="v">
							<tr>
								<td style="text-align: center;">
									${e.month}
								</td>
								<td>
									${e.item}
								</td>
								<td>
									${e.content}
								</td>
								<td>
									${e.result}
								</td>
								<td>
									${e.status}
								</td>
								<td>
									<a href="javascript:;" onclick="fnShowReport1('${e.id}');">${e.fileName}</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	  <!--PageOffice.js -->
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
<script type="text/javascript">
function fnShowReport1(id){
	POBrowser.openWindow('${basePath}qlt/nsReport/showReport.do?id='+id,'width=1200px;height=800px;');
}
function fnShowReport(){
	POBrowser.openWindow('${basePath}qlt/nsSum/showReport.do?id=${vo.id}','width=1200px;height=800px;');
}
</script>
</body>
</html>
