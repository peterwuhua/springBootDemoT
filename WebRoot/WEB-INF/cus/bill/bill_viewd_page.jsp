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
						<li><a>发票查看</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
	                <div class="btn-group">
	                    <a type="button" class="btn btn-xs btn-outline btn-default" href="${basePath}cus/bill/viewPage.do">未提交的记录</a>
	                    <a type="button" class="btn btn-xs btn-success active" >已提交的记录</a>
	                </div>
            	</div>
			</div>
			<div class="ibox-content">
				<form action="viewPage4ykjPage.do" method="post" id="listForm">
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
		var url = '/cus/bill/viewPage4success.do';
		var colNames = ['发票编号','项目编号','合同编号','申请日期','申请人','状态','备注','操作'];
		var colModel = [
			 {
				name : 'billno',
				index : 'billno',
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
				name : 'contractCode',
				index : 'contractCode',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'supportDate',
				index : 'supportDate',
				sortable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'person',
				index : 'person',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'fstatus',
				index : 'fstatus',
				sortable : false,
				width : 70,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'fdesc',
				index : 'fdesc',
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
			}
			];
			gridInitMin(url, colNames, colModel,false);
		});
	
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var cl = ids[i];
			var rowData = $("#listTable").jqGrid("getRowData",cl);//行数据
				ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:;" onclick="fnShow(\''+ids[i]+'\');">查看</a>';	
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act :  ce
			});
			
		}
	}
	
	function fnShow(id){
		var index = layer.open({
			title:'发票信息',	
			type: 2,
			area: ['800px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/cus/bill/show.do?id='+id
		});
	}

	</script> 
</body>
</html>
