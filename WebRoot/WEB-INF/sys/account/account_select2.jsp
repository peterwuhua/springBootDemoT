<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body>
	<div class="col-sm-3">
		<div class="ibox float-e-margins">
			<div class="ibox-content treeboxheight" style="min-height: 320px;">
				<div class="zTreeDemoBackground left">
					<ul id="tree" class="ztree"></ul>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-9">
		<div class="ibox float-e-margins">
			<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids"> 
				<input type="hidden" name="orgId" id="orgId">
				<div class="jqGrid_wrapper">
					<table id="listTable"></table>
					<div id="pager"></div>
				</div>
			</form>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<link href="${basePath}h/js/plugins/jquery-ztree/3.5.24/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css" />
	<script src="${basePath}h/js/plugins/jquery-ztree/3.5.24/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script src="${basePath}ext/js/tree.js" type="text/javascript"></script>

	<script type="text/javascript">
		var setting = {
			data: {simpleData: {enable: true}},
			// 回调函数
			callback : {
				onClick : function(event, treeId, treeNode, clickFlag) {
					$("#orgId").val(treeNode.id);
					var postData = $("#listTable").jqGrid("getGridParam", "postData");
					$.extend(postData, {orgId:treeNode.id});
					jQuery("#listTable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid")
				}
			}
		};
		$(document).ready(function(){
			$.ajax({
			    url: '${basePath}sys/org/treeData.do',
				success: function(data) {  
					initTree('${selectedIds}',data);
			    }
			});
		});
	</script>
	<script>
	function fnSelect(){
		var rowId = $('#listTable').jqGrid('getGridParam', 'selarrrow');
		var rData = $('#listTable').jqGrid('getRowData',rowId[0]);
		var data={};
		data.id=rowId[0];
		data.name=rData["userVo.name"];
		data.deptname = rData["orgVo.name"];
		data.ygbh = rData["userVo.no"];
		return data;
	}
	$(function() {
		var url = 'gridData.do';
		var editurl='';
		var colNames = ['部门','用户名','登录名','员工编号'];
		var colModel = [ 
			 {
				name : 'orgVo.name',
				index : 'org.name',
				search : false
			}, {
				name : 'userVo.name',
				index : 'user.name',
				width : 150,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'loginName',
				index : 'loginName',
				width : 150,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'userVo.no',
				index : 'user.no',
				width : 150,
				searchoptions : {
					sopt : ['cn']
				}
			}];
			gridInitAuto(url, colNames, colModel, editurl, -1,'',true);
		});
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-85);
	}
	function gridComplete() {
		var id='${vo.id}';
		$(this).jqGrid('setSelection', id);
	}
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
			beforeSelectRow: beforeSelectRow
		});
		setFilterToolbar();//行内查询
		setJgridWidth();//宽度
		setJgridHeight();//高度
		setNavGrid();//功能按钮
		jQuery("#listTable").jqGrid('setFrozenColumns'); //冻结表头
		$(".ui-search-oper").hide();
		fncleanName();
	}
	function beforeSelectRow(){
    	$("#listTable").jqGrid('resetSelection');  
        return(true);
    }
	</script>
</body>
</html>