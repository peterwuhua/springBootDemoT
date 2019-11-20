<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
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
				<ol class="breadcrumb">
					<li><a href="gridPage.do">文件夹</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids"> <input type="hidden" name="pid" id="pid">
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
	<link href="${basePath}h/js/plugins/jquery-ztree/3.5.24/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css" />
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
		var colNames = ['编码','名称','创建人','创建时间','说明','操作'];
		var colModel = [ 
		   			{
		   				name : 'code',
		   				index : 'code',
		   				width : 120,
		   				editable : true,
		   				frozen:true,
		   				searchoptions : {
		   					sopt : ['cn']
		   				}
		   			},{
		   				name : 'name',
		   				index : 'name',
		   				width : 200,
		   				editable : true,
		   				searchoptions : {
		   					sopt : ['cn']
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
						name : 'time',
						index : 'time',
						editable : true,
						resizable : true,
						width : 120,
						searchoptions : {
							sopt : [ 'cn']
						}
					},{
		   				name : 'describtion',
		   				index : 'describtion',
		   				width : 300,
		   				editable : true,
		   				searchoptions : {
		   					sopt : ['cn']
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

		gridInitMin(url, colNames, colModel,false);
		});

		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				be = '<a class="btn btn-outline btn-success btn-xs" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'&pid='+$('#pid').val()+'\')">授权</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
	</script>
</body>
</html>