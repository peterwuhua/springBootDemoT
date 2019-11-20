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
						<li><a>比对验证计划</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
						<a class="btn btn-primary" href="javascript:void(0);" onclick="fnEdit('edit.do');">新增</a> 
						<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
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
		var colNames = ['单号 ','计划名称','部门','联系人','电话','类型','实施日期','状态','操作'];
		var colModel = [
			 {
				name : 'no',
				index : 'no',
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'title',
				index : 'title',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'orgName',
				index : 'orgName',
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
				name : 'udate',
				index : 'udate',
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
					value:{'':'全部','0':'已保存','1':'待记录','2':'已完结'}
				}
			}, {
				name : 'act',
				index : 'act',
				width : 100,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
			gridInitAuto(url, colNames, colModel, '',10,'#pager',true)
		});
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			if(rData.status=='0'){
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:fnEdit(\'edit.do?id='+ rData.id+'\');">修改</a>';
			    be += '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+ rData.id+'\');">查看</a>';
				jQuery("#listTable").jqGrid('setRowData', rData.id, {act : be});
			}else{
				be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+ rData.id+'\');">查看</a>';
				jQuery("#listTable").jqGrid('setRowData', rData.id, {cb:'',act : be});
			}
		}
	}
	function fnEdit(url){
		layer.open({
			title:'计划编辑',	
			type: 2,
			area: ['800px', '500px'],
			fix: false, //不固定
			maxmin: true,
			content: url,
		});
	}
	function fnShow(id){
		layer.open({
			title:'计划查看',	
			type: 2,
			area: ['800px', '450px'],
			fix: false, //不固定
			maxmin: true,
			content: 'show.do?id='+id
		});
	}
	function formatStr(cellValue){
		if(cellValue=='1'){
			return '待记录';
		}else if(cellValue=='2'){
			return '已完结';
		}else{
			return '已保存';
		}
	}
	</script>
	<script>
	function gridInitAuto(url, colNames, colModel, editurl,rowNum,pager,multiselect) {
		$.jgrid.defaults.styleUI = "Bootstrap";
		var mygrid = $("#listTable").jqGrid({
			url : url,
			datatype : "json",
			mtype : "POST",
			colNames : colNames,
			colModel : colModel,
			rownumbers:true,
			rowNum : rowNum,
			rowList : [10,20,50,100],
			pager : pager,
			sortname : 'sort',
			sortorder : "asc",
			viewrecords : true,
			height:'auto',
			autowidth:true,
			recordpos : 'right',
			jsonReader : {
				root : 'datas'
			},
			shrinkToFit:true,    
	        autoScroll: true,
	        search:true,
			editurl:editurl,
			multiselect : multiselect,
			gridComplete : gridComplete,
			loadComplete : loadComplete,
			onSelectAll:function(rowid, status) { //点击全选时触发事件
				var rowIds = jQuery("#listTable").jqGrid('getDataIDs');//获取jqgrid中所有数据行的id
				for(var k=0; k<rowIds.length; k++) {
					var rData=jQuery("#listTable").jqGrid('getRowData', rowIds[k]);
					if(rData.status!='已保存'){
						$("#listTable").jqGrid("setSelection", rowIds[k],false);
					}
				}
			},
			onSelectRow:function(id){
			    var rData=jQuery("#listTable").jqGrid('getRowData', id);
			    if(rData.status!='已保存'){
					$("#listTable").jqGrid("setSelection",id,false);
				}
			}
		});
		setFilterToolbar();//行内查询
		setJgridWidth();//宽度
		setJgridHeight();//高度
		setNavGrid();//功能按钮
		jQuery("#listTable").jqGrid('setFrozenColumns'); //冻结表头
		$(".ui-search-oper").hide();
		fncleanName();
	}
	</script>
</body>
</html>
