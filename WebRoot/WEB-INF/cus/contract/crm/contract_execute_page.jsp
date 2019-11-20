<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
.panel-heading {
	padding: 0px;
}

.col-sm-6 {
	padding: 0px 0px 1px 0px;
}
</style>
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
					<div class="pull-right">
							<div class="btn-group">
								<a type="button" class="btn btn-xs btn-outline btn-default" href="${basePath}cus/contract/saleGridPaged.do?flag=1">未回签合同</a> 
								<a type="button" class="btn btn-xs btn-success active" >执行中合同</a> 
								<a type="button" class="btn btn-xs btn-outline btn-default" href="${basePath}cus/contract/saleComGridPaged.do?flag=3">已完结合同</a> 
								<a type="button" class="btn btn-xs btn-outline btn-default" href="${basePath}cus/contract/saleFastGridPaged.do?flag=4">快到期合同</a> 
								<a type="button" class="btn btn-xs btn-outline btn-default" href="${basePath}cus/contract/saleExpiredGridPaged.do?flag=5">已超期合同</a>
							</div>
					</div>
			</div>	
					<div class="ibox-content">
						<form action="saleExecuteGridPaged.do" method="post" id="listForm">
							<input type="hidden" name="ids" id="ids">
					<div class="col-sm-6" >
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
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = 'gridData4ExecuteSale.do';
		var colNames = ['合同编号 ','客户名称','销售人员 ','开始日期','结束日期','合同金额','合同状态','操作'];
		var colModel = [
			{
				name : 'code',
				index : 'code',
				width : 120,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'customerVo.name',
				index : 'customerVo.name',
				width : 200,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},  {
				name : 'saler',
				index : 'saler',
				sortable : false,
				width : 120,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'sdate',
				index : 'sdate',
				sortable : false,
				width : 90,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'edime',
				index : 'edime',
				sortable : false,
				width : 90,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'contractSum',
				index : 'contractSum',
				sortable : false,
				width : 100,
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
				var cl = ids[i];
				ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:;" onclick="fnShow(\''+ids[i]+'\');">查看</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : ce
				});
			}
		}
		
		function fnShow(id){
			layer.config({
				  extend: '${basePath}ext/js/ext.js'
				}); 
			
			var index = layer.open({
				title:'合同信息',	
				type: 2,
				area: ['800px','85%'],
				fix: false, //不固定
				maxmin: true,
				content: '/cus/contract/showCrm.do?id='+id
						
			});
			
			/* layer.full(index);
	        window.sessionStorage.setItem("index", index);
	        $(window).on("resize", function () {
	            layer.full(window.sessionStorage.getItem("index"));
	        }) */
		}
		
		
	</script>
</body>
</html>
