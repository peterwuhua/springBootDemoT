<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
.rowBG{
   color:#ed5565;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>回款提醒</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
					</div>
					<div class="jqGrid_wrapper">
	                      <table id="listTable"></table>
	                      <div id="pager"></div>
	               </div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<script>
	 
	$(function() {
		var url = '/cus/fee/noticePage.do';
		var colNames = ['单据号 ','项目编号','合同编号','操作'];
		var colModel = [
			 {
				name : 'fdjh',
				index : 'fdjh',
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'itemno',
				index : 'itemno',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'contractno',
				index : 'contractno',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'desc',
				index : 'desc',
				sortable : false,
				width : 70,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];
			gridInitMin(url, colNames, colModel,false);
		});
	
	
	

	</script> 
</body>
</html>
