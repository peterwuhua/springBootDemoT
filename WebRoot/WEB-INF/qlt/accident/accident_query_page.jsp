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
						<li><a>事故查询</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
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
		var url = 'gridData4Query.do';
		var colNames = ['单号 ','事故单位','联系人','电话','事故类型','登记日期','状态','操作'];
		var colModel = [
			 {
				name : 'no',
				index : 'no',
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'custName',
				index : 'custName',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'user',
				index : 'user',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'phone',
				index : 'phone',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'type',
				index : 'type',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'date',
				index : 'date',
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'status',
				index : 'status',
				formatter:formatStr,
				sortable : false,
				width : 100,
				stype : 'select',
				searchoptions : {
					sopt : [ 'cn'],
					value:{'':'全部','0':'已保存','1':'待处理','2':'已处理'}
				}
			}, {
				name : 'act',
				index : 'act',
				width : 70,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
			gridInitAuto(url, colNames, colModel, '',10,'#pager',false)
		});
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			var be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+ rData.id+'\');">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', rData.id, {
				act : be
			});
			 
		}
	}
	function fnShow(id){
		layer.open({
			title:'事故处理',	
			type: 2,
			area: ['800px', '450px'],
			fix: false, //不固定
			maxmin: true,
			content: 'show.do?id='+id
		});
	}
	function formatStr(cellValue){
		if(cellValue=='1'){
			return '待处理';
		}else{
			return '已处理';
		}
	}
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-225);
	} 
	</script> 
</body>
</html>
