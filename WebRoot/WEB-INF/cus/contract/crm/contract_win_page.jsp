<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>合同管理</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
			</div>
			<div class="ibox-content">
		<!-- 	<div class="panel-heading">
					  <div class="panel-options">
						<div class="col-sm-6" >
							<a class="btn btn-primary" href="javascript:fnConfirm();">选择</a>
						</div>
					</div>
				</div> -->
				<form action="gridList.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div class="jqGrid_wrapper">
	                      <table id="listTable"></table>
	                      <div id="pager"></div>
	               </div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = 'gridListData.do';
		var colNames = ['合同编号 ','客户名称','销售名称','开始日期','结束日期','合同金额','合同状态'];
		var colModel = [
			{
				name : 'code',
				index : 'code',
				width : 160,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'customerVo.name',
				index : 'customer.name',
				width : 220,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},  {
				name : 'saler',
				index : 'saler',
				sortable : false,
				width : 160,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'sdate',
				index : 'sdate',
				sortable : false,
				width : 130,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'edime',
				index : 'edime',
				sortable : false,
				width : 130,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'contractSum',
				index : 'contractSum',
				sortable : false,
				width : 130,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'status',
				index : 'status',
				sortable : false,
				fixed:true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];
			gridInitMin(url, colNames, colModel,true);
		});


	
	
	function fnConfirm()
	{
		var selectIds = getSelectIds();
		var rowid=selectIds[0];
		var rowData = $("#listTable").jqGrid('getRowData',rowid);
		var selectNames = "";
		var obj = {};
		if(selectIds.length<1){
			layer.msg('请选择一个合同', {icon: 0,time: 3000});
			return false;
		}
		if(selectIds.length>1){
			layer.msg('合同至多只能选一个', {icon: 0,time: 3000});
			return;
		}
		if(rowid!=''){
			var rowData = $("#listTable").jqGrid('getRowData',rowid);
			obj.contractId=rowid; 
			obj.code=rowData.code; //合同编号
			obj.contractSum=rowData.contractSum;
		}
		parent.$('#contractId').val(obj.contractId);
		parent.$('#contractCode').val(obj.code);
		parent.$('#contractJe').val(obj.contractSum);
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); //再执行关闭  
	}
	
	
	
	</script> 
	
</body>
</html>
