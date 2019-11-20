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
		var content = "";
		for(var i=0;i<rowId.length;i++){
			if(0!=i) content+="，";
			var rData = $('#listTable').jqGrid('getRowData',rowId[i]);
			content += rData["userVo.name"];
		}
		var data={};
		data.id=rowId;
		data.name=content;
		return data;
	}
	$(function() {
		var url = 'gridData.do';
		var editurl='';
		var colNames = ['部门','用户名','登录名'];
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
			}];
			gridInitAuto(url, colNames, colModel, editurl, -1,'',true);
		});
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-85);
	}
	//当窗口大小发生改变时触发
	window.onresize = function doResize() {
		 var win = getWinSize();
		 $("#listTable").jqGrid('setGridWidth', win.WinW-10).jqGrid('setGridHeight', win.WinH-215);
	}
	function gridComplete() {
		var idstr='${vo.ids}';
    	if(idstr!=''){
    		var	ids=idstr.split(',');
    		for(var i=0;i<ids.length;i++){
    			if(ids[i]!=''){
    				$(this).jqGrid('setSelection', ids[i]);
    			}
    		}
    	}
	}
	</script>
</body>
</html>