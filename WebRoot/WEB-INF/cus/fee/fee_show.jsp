<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
.panel-heading{
	padding: 0px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<form method="post" action="" name="form" class="form-horizontal" id="thisForm">
				<input name="id" value="${vo.id}" type="hidden" />
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<ol class="breadcrumb">
						<li><a href="javascript:backMainPage();">费用</a></li>
						<li><strong>查看</strong></li>
					</ol>
				</div>
				<div class="ibox-content">
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
							<td class="width-10 active"></td>
								<td class="width-15 active"><label class="pull-right">发票编号:</label></td>
								<td class="width-35">${vo.billno }</td>
								<td class="width-15 active"><label class="pull-right">费用状态:</label></td>
								<td class="width-35">${vo.fstatus }</td>
							</tr>
							<tr>
							<td class="width-10 active"></td>
								<td class="width-15 active"><label class="pull-right">合同编号:</label></td>
								<td class="width-35">
												    ${vo.contractCode}
								</td>
								<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
								<td class="width-35">${vo.billVo.itemno }</td>
							</tr>
							<tr>
								<td class="width-10 active"></td>
								<td class="width-15 active"><label class="pull-right">合同金额:</label></td>
								<td class="width-35">
								${vo.billVo.htje}
								</td>
								<td class="width-15 active"><label class="pull-right">发票金额:</label></td>
								<td>${vo.billVo.fpje }
								</td>
							</tr>
				
							<tr>
								<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>开<br>票<br>信<br>息</label></td>
								<td class="active"><label class="pull-right">发票类型:</label></td>
								<td>
								${vo.billVo.ftype}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
								<td class="width-35">
									${vo.customerName} 
								</td>
								<td class="width-15 active"><label class="pull-right">信用代码 /<br> 身份证明:</label></td>
								<td class="width-35">${vo.billVo.customerVo.custCode }</td>
							 </tr>
							 <tr>   
							    <td class="width-15 active"><label class="pull-right">注册地址:</label></td>
								<td class="width-35">${vo.billVo.customerVo.address}</td>
								<td class="width-15 active"><label class="pull-right">注册电话:</label></td>
								<td class="width-35">${vo.billVo.customerVo.telephone}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开户银行:</label></td>
								<td class="width-35">${vo.billVo.customerVo.custBank }</td>
								<td class="width-15 active"><label class="pull-right">开户账号:</label></td>
								<td class="width-35">${vo.billVo.customerVo.custAccount }</td>
							</tr>
									<tr>
								<td class="width-10 active"></td>
								<td class="width-15 active"><label class="pull-right">支付方式:</label></td>
								<td class="width-35">${vo.payWay }
								</td>
								<td class="width-15 active"><label class="pull-right">收款比例:</label></td>
								<td class="width-35">
								${vo.feeratio}
								</td>
							</tr>
								<tr>
									<td class="width-10 active"></td>
									<td class="width-15 active"><label class="pull-right">预付款:</label></td>
									<td class="width-35">${vo.yfje }
									</td>
									<td class="width-15 active"><label class="pull-right">应收款:</label></td>
									<td class="width-35">
									${vo.receive}
									</td>
							</tr>
							 <tr>  
							 <td class="width-10 active"></td> 
							    <td class="width-15 active"><label class="pull-right">申&nbsp;&nbsp;请&nbsp;&nbsp;人:</label></td>
								<td class="width-35">${vo.person}</td>
								<td class="width-15 active"><label class="pull-right">申请日期:</label></td>
								<td class="width-35">${vo.supportDate}</td>
							</tr>
							<tr>
							<td class="width-10 active"></td> 
								<td class="width-10 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">${vo.fdesc}</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
				</div>
			</div>
		</form>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
</html>
