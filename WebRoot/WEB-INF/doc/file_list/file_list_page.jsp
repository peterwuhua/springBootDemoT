<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<style type="text/css">
.ui-jqgrid-btable tr.jqgrow td {
	overflow: hidden;
	white-space: nowrap;
	text-overflow : ellipsis;
}
</style>
<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	 <div class="col-sm-3">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>文件夹树</h5>
			</div>
			<div class="ibox-content">
				<div class="zTreeDemoBackground left">
					<ul id="tree" class="ztree"></ul>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-9">
		<div class="ibox float-e-margins">
		 <div class="ibox-title">
		 	<h5>
				<ol class="breadcrumb">
					<li><a href="gridPage.do">文件</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</h5>
		</div>
			<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<input type="hidden" name="pid" id="pid">
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
	<link href="${basePath}h/js/plugins/jquery-ztree/3.5.24/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css"/>
	<script src="${basePath}h/js/plugins/jquery-ztree/3.5.24/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script src="${basePath}ext/js/tree.js" type="text/javascript"></script>
	<script type="text/javascript">
		var setting = {
				data: {simpleData: {enable: true}},
				// 回调函数
				callback : {
					onClick : function(event, treeId, treeNode, clickFlag) {
						$("#pid").val(treeNode.id);
						$("#mesg").text(treeNode.name);
						var postData = $("#listTable").jqGrid("getGridParam", "postData");
						$.extend(postData, {pid:treeNode.id});
						jQuery("#listTable").jqGrid('setGridParam',{url:'gridData.do'}).trigger("reloadGrid")
					}
				}
			};
			$(document).ready(function(){
				$.ajax({
				    url: '${basePath}doc/category/treeData.do',
					success: function(data) {  
						initTree('${selectedIds}',data);
						var pid = '${vo.pid}';
						var treeObj = $.fn.zTree.getZTreeObj("tree");
						if(pid.length>0){
							var node = treeObj.getNodeByParam("id", pid);
							if(null!=node.getParentNode()){
								var parentNode = node.getParentNode();
								treeObj.expandNode(parentNode,true);
							}
						}
				    }
				});
			});
	</script>
	<script>
	$(function() {
		var pid = '${vo.pid}';
		var url ="";
		if(pid.length>0){
		 url = 'gridData.do?pid='+pid;
		}else{
			url = 'gridData.do';
		}
		var editurl='';
		var colNames = ['操作','名称','标题','角色','大小','类型','标记','操作人','操作时间','说明','状态'];
		var colModel = [
			{
		   		name : 'act',
		   		index : 'act',
		   		width : 80,
		   		title : false,
		   		search : false,
		   		frozen : true,
				resizable : true,
		   		sortable : false,
		   	},{
				name : 'name',
				index : 'name',
				width : 200,
				frozen : true,
				resizable : true,
				editable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, 
			{
				name : 'title',
				index : 'title',
				editable : true,
				resizable : true,
				width : 200,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
			
				name : 'roleNames',
				index : 'roleNames',
				editable : true,
				resizable : true,
				width : 200,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'size',
				index : 'size',
				editable : true,
				resizable : true,
				width : 80,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'type',
				index : 'type',
				editable : true,
				resizable : true,
				width : 80,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'sign',
				index : 'sign',
				editable : true,
				resizable : true,
				width : 80,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'lastUpdUser',
				index : 'lastUpdUser',
				editable : true,
				resizable : true,
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'lastUpdTimeStr',
				index : 'lastUpdTimeStr',
				editable : true,
				resizable : true,
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'describtion',
				index : 'describtion',
				editable : true,
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'state',
				index : 'state',
				editable : true,
				width : 80,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];
			gridInit(url, colNames, colModel, editurl,20,'#pager',false,false)
		});

	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rowData = data.datas[i];
			if(rowData.isPer=="Y"){
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+rowData.id+'\')">查看</a>';
			}else{
				be = '<a class="btn btn-danger btn-xs" title="修改" >未授权</a>';
			}
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-220);
	}
	</script> 
	
</body>
</html>
