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
		<div class="ibox float-e-margins ">
			<div class="ibox-title">
				<h5>功能树</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
					<a class="close-link"> <i class="fa fa-times"></i></a>
				</div>
			</div>
			<div class="ibox-content treeboxheight">
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
					<li><a href="gridPage.do">权限</a></li>
						<li><strong id="mesg">列表</strong></li>
				</ol>
			</h5>
			<div class="ibox-tools">
				<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
				<a class="close-link"> <i class="fa fa-times"></i></a>
			</div>
		</div>
		<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<input type="hidden" name="funId" id="funId" value="${vo.funId }">
				<div class="col-sm-7">
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" href="javascript:;" onclick="fnEdit()">新增</a> 
					<a class="btn btn-danger" href="javascript:;" onclick="jqgridDelete();">删除</a>
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
    <link href="${basePath}h/js/plugins/jquery-ztree/3.5.24/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css"/>
	<script src="${basePath}h/js/plugins/jquery-ztree/3.5.24/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script src="${basePath}ext/js/tree.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		var setting = {
			data: {simpleData: {enable: true}},
			// 回调函数
			callback : {
				onClick : function(event, treeId, treeNode, clickFlag) {
					$("#funId").val(treeNode.id);
					$("#mesg").text(treeNode.name);
					var postData = $("#listTable").jqGrid("getGridParam", "postData");
					$.extend(postData, {funId:treeNode.id});
					jQuery("#listTable").jqGrid('setGridParam',{url:'gridData.do'}).trigger("reloadGrid")
				}
			}
		};
		$(document).ready(function(){
			$.ajax({
			    url: 'treeData.do',
				success: function(data) {  
					initTree('${selectedIds}',data);
					var funId = '${vo.funId}';
					var treeObj = $.fn.zTree.getZTreeObj("tree");
					if(funId.length>0){
						var node = treeObj.getNodeByParam("id", funId);
						if(null!=node.getParentNode()){
							var parentNode = node.getParentNode();
							treeObj.expandNode(parentNode,true);
						}
						//treeObj.setting.callback.onClick(null,treeObj.setting.treeId,node,null);
					}
			    }
			});
		});
	</script>
	<script>
	function fnEdit(){
		location.href='edit.do?functionVo.id='+$('#funId').val();
	}
	$(function() {
		var funId = '${vo.funId}';
		var url ="";
		if(funId.length>0){
		 url = 'gridData.do?funId='+funId;
		}else{
			url = 'gridData.do';
		}
		var editurl='gridEdit.do';
		var colNames = ['操作','功能','名称','编码','说明','排序'];
		var colModel = [ 
			{
				name : 'act',
				index : 'act',
				width : 60,
				title : false,
				search : false,
				frozen : true,
				resizable : false,
				sortable : false
			},{
				name : 'functionVo.name',
				index : 'function.name',
				frozen : true,
				resizable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'name',
				index : 'name',
				editable : true,
				frozen : true,
				resizable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'code',
				index : 'code',
				editable : true,
				searchoptions : {
					sopt : ['cn']
				}
			},  {
				name : 'describtion',
				index : 'describtion',
				editable : true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sort',
				index : 'sort',
				width : 80,
				editable : true,
				searchoptions : {
					sopt : [ 'cn','ne','eq','le','lt','gt','ge']
				}
			} ];
			pageInit(url, colNames, colModel, editurl);
		});

		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="edit.do?id='+ids[i]+'">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
	</script> 
    </body>
</html>