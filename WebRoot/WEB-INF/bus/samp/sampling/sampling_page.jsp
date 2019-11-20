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
						<li><a href="gridPage.do">样品清单</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
						<a class="btn btn-primary" href="javascript:;" onclick="fnEdit()"><i class="fa fa-plus" aria-hidden="true"></i> 余样处理</a>  
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
	function fnEdit(){
		var selectIds = getSelectIds();
		if(selectIds.length<1){
			layer.msg('请选择要处理的样品', {icon: 0,time: 3000});
			return false;
		}else{
			$("#ids").val(selectIds);
		}
		openEditPage('edit4Deal.do?ids='+selectIds);
	}
	function fnOut(id){
		layer.open({
			title:'新增/修改',	
			type: 2,
			area: ['800px', '400px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}bus/sampCk/edit.do?samplingVo.id='+id,
			btn: ['确定','关闭'], //按钮
			yes: function(index, layero){
				var iframeWin = window[layero.find('iframe')[0]['name']];
				  iframeWin.submitSave();
			}
		});
	}
	$(function() {
		var url ="";
		url = 'gridData.do';
		var colNames = ['任务编号','样品编号','客户名称','采/送样人','保管人','收样日期','状态','操作'];
		var colModel = [ 
			{
				name : 'taskVo.no',
				index : 'task.no',
				width : 120,
				formatter:formatNo,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampCode',
				index : 'sampCode',
				width : 90,
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
				name : 'taskVo.cyName',
				index : 'task.cyName',
				width : 70,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'reciveUser',
				index : 'reciveUser',
				width : 70,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'reciveDate',
				index : 'reciveDate',
				width : 100,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'status',
				index : 'status',
				width : 70,
				formatter:formatStr,
				stype : 'select',
				searchoptions : {
					sopt : [ 'cn'],
					value:{'':'全部','SAMP_00':'库存中','SAMP_10':'已留样'}
				}
			},{
				name : 'act',
				index : 'act',
				width : 120,
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
			if(rData.status=='已留样'){
				be = '<a class="btn btn-outline btn-info btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>';
			}else{
				be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:openEditPage(\'show.do?id='+ids[i]+'\')">查看</a>';
			}
			de = '<a class="btn btn-outline btn-info btn-xs" title="出库" href="javascript:fnOut(\''+ids[i]+'\')">出库</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be+' '+de
			});
		}
	}
	function formatDate(cellValue){
		if(cellValue!=null&&cellValue.length>10){
			cellValue=cellValue.substring(0,10);
		}
		return cellValue;
	}
	function formatStr(cellValue){
		if(cellValue=='SAMP_00'){
			return '库存中';
		}else if(cellValue=='SAMP_10'){
			return '已留样';
		}else if(cellValue=='SAMP_30'){
			return '已处理';
		}else{
			return '其他';
		}
	}
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnShowTask(\''+rowObject.taskVo.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.taskVo.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	</script>
</body>
</html>