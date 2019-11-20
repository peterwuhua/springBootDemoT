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
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>报价申请</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
					<div class="btn-group">
						<a type="button" class="btn btn-xs btn-success active" href="javascript:;">未提交的记录</a>
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPaged.do">已提交的记录</a>
					</div>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
						<a class="btn btn-primary" href="javascript:;" onclick="openEditPage('edit.do')">新增</a> 
						<a class="btn btn-danger" href="javascript:;" onclick="jqgridDelete();">删除</a>
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
		var url = 'gridData.do';
		var colNames = ['编号单','客户名称','联系人','电话','合同金额','报价日期','有效期至','操作'];
		var colModel = [ 
			{
				name : 'no',
				index : 'no',
				width : 130,
				fixed:true,
				formatter:formatNo,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custName',
				index : 'custName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custUser',
				index : 'custUser',
				width : 80,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'custMobile',
				index : 'custMobile',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'htPrice',
				index : 'htPrice',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'bdate',
				index : 'bdate',
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'yxq',
				index : 'yxq',
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 70,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
		gridInitMin(url, colNames, colModel,true);
	});
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	function jqgridDelete(){
		var selectIds = getSelectIds();
		if(selectIds.length<1){
			layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
			return false;
		}
		layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
			$("#ids").val(selectIds);
			$("#listForm").attr("action","delete.do");
			$("#listForm").submit();
		});
	}
	 
	function formatNo(cellValue,options,rowObject){
		var ops=cellValue;
		if(rowObject.back =='Y'){
			ops+='<img alt="退回" src="/static/img/tuihui.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	</script>
</body>
</html>