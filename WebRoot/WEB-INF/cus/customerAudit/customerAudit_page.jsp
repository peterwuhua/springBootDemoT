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
				<ol class="breadcrumb">
					<li><a href="gridPage.do">客户审核</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div class="form-group col-md-6">
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<div class="btn-group">
							<button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">
								导出 <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a href="javascript:;" onclick="jqgridExport('cus-customer-export.xls','客户信息列表.xls',0)">全部数据</a></li>
								<li><a href="javascript:;" onclick="jqgridExport('cus-customer-export.xls','客户信息列表.xls',1)">选中数据</a></li>
							</ul>
						</div>
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
		var editurl='gridEdit.do';
		var colNames = ['客户名称','区域','行业','客户属性','销售人员','操作'];
		var colModel = [
			  {
				name : 'name',
				index : 'name', //客户名称
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},
			{
				name : 'areaPath',  
				index : 'areaPath', //区域
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'industry',  
				index : 'industry', //行业
				sortable : false,
				width : 160,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'cusType',  
				index : 'cusType', //客户属性
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'saler',
				index : 'saler',  //
				sortable : false,
				width : 70,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 100,
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
				sh = '<a class="btn btn-outline btn-success btn-xs" title="审批" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">审批</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : ce +' '+ sh
				});
			}
		}
		function fnShow(id){
			var index = layer.open({
				title:'客户信息',	
				type: 2,
				area: ['800px','85%'],
				fix: false, //不固定
				maxmin: true,
				content: '/cus/customer/show.do?id='+id
			});
		}

		
		
		function fnSearch(){
			var postData = $("#listTable").jqGrid("getGridParam", "postData");
			$.extend(postData, {'areaPath':$('#areaPath').val()
				,'industry':$('#industry').val()
				,'cusType':$('#cusType').val(),'name':$('#name').val()});
			jQuery("#listTable").jqGrid('setGridParam',{url:'gridData4Used.do'}).trigger("reloadGrid")
		}
		
		
	
	
		
	</script>

</body>
</html>
