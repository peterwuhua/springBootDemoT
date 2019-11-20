<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
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
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="active" style="width: 60px;text-align: center;" rowspan="4"><label>基<br>本<br>信<br>息</label></td>
							<td class="active" style="width: 10%"><label class="pull-right">客户名称:</label></td>
							<td>
								${vo.custName}
							</td>
							<td class="active" style="width: 10%"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
							<td>
								${vo.custUser}
							</td>
						</tr>
						<tr>
							 <td class="active"><label class="pull-right">单位地址:</label></td>
							<td>
								${vo.custAddress}
							</td>
							<td class="active"><label class="pull-right">联系电话:</label></td>
							<td>
								${vo.custMobile} 
							</td>
						</tr>
						<tr>
							 <td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
							<td>
								${vo.custEmail} 
							</td>
							<td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
							<td>
								${vo.custZip}
							</td>
						</tr>
						<tr>
							 <td class="active"><label class="pull-right">监测类别:</label></td>
							<td>
								${vo.taskType} 
							</td>
							<td class="active"><label class="pull-right">项目频次:</label></td>
							<td>
								${vo.pc} ${vo.pcUnit}
							</td>
						</tr>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						 <thead>
						 	<tr>
						 		<th style="width: 50px;">序号</th>
						 		<th style="width: 200px;">样品类型</th>
						 		<th style="width: 150px;">样品名称</th>
						 		<th >检测项目</th>
						 		<th style="width: 100px;">分析费</th>
						 		<th style="width: 100px;">采样费</th>
						 	</tr>
						 </thead>
						<tbody id="detail_tb">
							<c:forEach items="${vo.detailList}" var="e" varStatus="s">
								<tr index="${s.index}">
							 		<td>
							 			${s.index+1}
							 		</td>
							 		<td>
							 			${e.sampTypeName}
							 		</td>
							 		<td>
							 			${e.sampName}
							 		</td>
							 		<td>
							 			${e.itemNames}
							 		</td>
							 		<td>
							 			${e.fxPrice}
							 		</td>
							 		<td>
							 			${e.cyPrice}
							 		</td>
							 	</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tr>
								<td class="active"><label class="pull-right">检测费用:</label></td>
								<td>
									${vo.price}
								</td>
								<td class="active"><label class="pull-right">折&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;扣:</label></td>
								<td>
									${vo.discount}
								</td>
								<td class="active"><label class="pull-right">交通费用:</label></td>
								<td>
									${vo.jtPrice}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">报告费用:</label></td>
								<td>
									${vo.bgPrice}
								</td>
								<td class="active"><label class="pull-right">税费等其他费用:</label></td>
								<td>
									${vo.otherPrice}
								</td>
								<td class="active"><label class="pull-right">合同费用:</label></td>
								<td>
									${vo.htPrice}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">报价日期:</label></td>
								<td>
									${vo.bdate}
								</td>
								<td class="active"><label class="pull-right">有效期至:</label></td>
								<td>
									${vo.yxq}
								</td>
								<td class="active"><label class="pull-right">报&nbsp;&nbsp;价&nbsp;&nbsp;人:</label></td>
								<td>
									${vo.buserName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="5">
									${vo.remark}
								</td>
							</tr>
					</table>
					 
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
</html>
