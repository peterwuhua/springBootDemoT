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
						<li><a href="gridPage.do">样品归还记录</a></li>
						<li><strong id="mesg">列表</strong></li>
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
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>
	<script>
	function fnEdit(url){
		layer.open({
			title:'样品归还更新',	
			type: 2,
			area: ['800px', '400px'],
			fix: false, //不固定
			maxmin: true,
			content: url,
			btn: ['确定','关闭'], //按钮
			yes: function(index, layero){
				var iframeWin = window[layero.find('iframe')[0]['name']];
				  iframeWin.submitSave();
			},
			end: function () {
		        location.reload();
		      }
		});
	}
	function fnShow(url){
		layer.open({
			title:'样品归还查看',	
			type: 2,
			area: ['800px', '400px'],
			fix: false, //不固定
			maxmin: true,
			content: url
		});
	}
	$(function() {
		var url ="";
		url = 'gridData.do';
		var colNames = ['任务编号','样品编号','客户名称','归还人','归还日期','备注','状态','操作'];
		var colModel = [ 
			{
				name : 'taskVo.no',
				index : 'task.no',
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampCode',
				index : 'sampCode',
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custVo.custName',
				index : 'cust.custName',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'useName',
				index : 'useName',
				width : 70,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'useDate',
				index : 'useDate',
				width : 150,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'content',
				index : 'content',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'status',
				index : 'status',
				width : 70,
				fixed:true,
				formatter:formatStr,
				stype : 'select',
				searchoptions : {
					sopt : [ 'cn'],
					value:{'':'全部','SAMP_22':'待归还','SAMP_23':'已归还'}
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
		gridInitMin(url, colNames, colModel,false);
	});
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			if(rData.status=='已归还'){
				be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\'show.do?id='+ids[i]+'\')">查看</a>';
			}else{
				be = '<a class="btn btn-outline btn-info btn-xs" title="编辑" href="javascript:fnEdit(\'edit.do?id='+ids[i]+'\')">编辑</a>';
			}
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	function formatStr(cellValue){
		if(cellValue=='SAMP_22'){
			return '待归还';
		}else if(cellValue=='SAMP_23'){
			return '已归还';
		}else{
			return '其他';
		}
	}
	</script>
</body>
</html>