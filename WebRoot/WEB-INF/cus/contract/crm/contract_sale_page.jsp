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
			 	<div class="pull-right">
	                <div class="btn-group">
	                    <a type="button" class="btn btn-xs btn-success active">未回签合同</a>
	                    <a type="button" class="btn btn-xs btn-outline btn-default" href="${basePath}cus/contract/saleExecuteGridPaged.do?flag=2">执行中合同</a>
	                    <a type="button" class="btn btn-xs btn-outline btn-default" href="${basePath}cus/contract/saleComGridPaged.do?flag=3">已完结合同</a>
	                    <a type="button" class="btn btn-xs btn-outline btn-default" href="${basePath}cus/contract/saleFastGridPaged.do?flag=4" >快到期合同</a>
	                    <a type="button" class="btn btn-xs btn-outline btn-default" href="${basePath}cus/contract/saleExpiredGridPaged.do?flag=5" >已超期合同</a>
	                </div>
            	</div>
			</div>
			<div class="ibox-content">
				<form action="saleGridPaged.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<div>
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<!-- <a class="btn btn-primary" href="javascript:void(0);" onclick="openEditPage('edit.do');">新增</a>  -->
					<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
					<div class="btn-group">
                       <button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">导出 <span class="caret"></span></button>
                       <ul class="dropdown-menu">
                           <li><a href="javascript:;" onclick="jqgridExport('cus-contract-export.xls','合同信息列表.xls',0)" >全部数据</a>
                           </li>
                           <li><a href="javascript:;" onclick="jqgridExport('cus-contract-export.xls','合同信息列表.xls',1)" >选中数据</a>
                           </li>
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
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = 'gridData4Sale.do';
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
				be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'editSale.do?flag=1&id='+ids[i]+'\')">编辑</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
	</script> 
	
</body>
</html>
