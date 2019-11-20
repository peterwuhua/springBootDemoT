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
									${vo.gsVo.title}
								</td>
								<td class="width-15 active"><label class="pull-right">评审时间:</label></td>
								<td class="width-35">
									${vo.gsVo.psDate}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
								<td class="width-35">
									${vo.gsVo.deptName}
								</td>
								<td class="width-15 active"><label class="pull-right">评审主持人:</label></td>
								<td class="width-35">
									${vo.gsVo.zcName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">评审地点:</label></td>
								<td>
									${vo.gsVo.address}
								</td>
								<td class="active"><label class="pull-right">评审依据:</label></td>
								<td>
									${vo.gsVo.stand}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内审组长:</label></td>
								<td>
									${vo.gsVo.headName}
								</td>
								<td class="active"><label class="pull-right">审核人员:</label></td>
								<td>
									${vo.gsVo.psName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">计划详情:</label></td>
								<td colspan="3">
									${vo.gsVo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.gsVo.remark}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">评审内容:</label></td>
								<td colspan="3">
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">评审中发现的问题:</label></td>
								<td colspan="3">
									${vo.problem}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">评审结论:</label></td>
								<td colspan="3">
									${vo.result}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">纠正/改进的措施:</label></td>
								<td colspan="3">
									${vo.measures}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">对纠正/改进措施的检查:</label></td>
								<td colspan="3">
									${vo.checks}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.remark}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">记&nbsp;&nbsp;录&nbsp;&nbsp;人:</label></td>
								<td>
									${vo.userName}
								</td>
								<td class="active"><label class="pull-right ">记录日期:</label></td>
								<td>
									${vo.date}
								</td>
							</tr>
						</tbody>
					</table>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
</body>
</html>
