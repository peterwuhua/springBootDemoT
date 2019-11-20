<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
.form-control{
	padding: 4px 4px;
}
.table select{
	padding-top: 2px;
}
#detail_tb .btn{
	padding: 6px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">合同签订</a></li>
					<li><strong>查看</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
							<td class="width-35">
								<a href="javascript:void();" onclick="fnShow('${vo.id}','${vo.no}');">${vo.no}</a>
							</td>
							<td class="width-15 active"><label class="pull-right">受检单位:</label></td>
							<td class="width-35">
								${vo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
							<td class="width-15 active"><label class="pull-right">样品类别:</label></td>
							<td class="width-35">
								${vo.sampTypeName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">立项日期:</label></td>
							<td class="width-35">${vo.rdate}</td>
							<td class="width-15 active"><label class="pull-right">拟完成日期:</label></td>
							<td class="width-35">${vo.finishDate}</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.remark}
							</td>
						</tr>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">合同费用:</label></td>
							<td class="width-35">
								${vo.invoiceVo.htPrice}
							</td>
							 <td class="width-15 active"><label class="pull-right">预付款比例:</label></td>
							<td class="width-35">
								${vo.invoiceVo.payRatio }
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">预付金额:</label></td>
							<td class="width-35">
								${vo.invoiceVo.yfkMin }
							</td>
							<td class="width-15 active"><label class="pull-right">剩余金额:</label></td>
							<td class="width-35">
								${vo.invoiceVo.sskMin }
							</td>
						</tr>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="active"><label class="pull-right">合同文件:</label></td>
							<td>
								<c:if test="${vo.htName!=null && vo.htName!=''}">
								 	<button class="btn btn-info" type="button" onclick="fnShowReport();">${vo.htName}</button>
								</c:if>
							</td>
							<td class="width-15 active"><label class="pull-right">合同编号:</label></td>
							<td class="width-35">
								${vo.htNo}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">签订人员:</label></td>
							<td class="width-35">
								${vo.qdName}
							</td>
							<td class="width-15 active"><label class="pull-right">签订日期:</label></td>
							<td class="width-35">
								${vo.qdDate}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.qdMsg}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">合同状态:</label></td>
							<td>
								${vo.htSt}
							</td>
							<td class="active"><label class="pull-right">收费状态:</label></td>
							<td>
								${vo.paySt}
								<c:if test="${vo.pay>0}">
								（已收金额：${vo.pay}元）
								</c:if>
							</td>
						</tr>
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
	 <!--PageOffice.js -->
     <script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
	<script>
		function fnShowReport(){
			POBrowser.openWindow('${basePath}bus/projectHt/showWord.do?id=${vo.id}','width=1200px;height=800px;');
		}
		function showIM(id){
			parent.layer.open({
				title:'已选项目方法列表',	
				type: 2,
				area: ['700px', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}/bus/im/list4Im.do?busId='+id,
			});
		}
	</script>
</body>
</html>
