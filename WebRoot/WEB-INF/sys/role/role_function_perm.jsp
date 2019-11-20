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
			<div class="ibox-content">
				<div class="zTreeDemoBackground left">
					<ul id="tree" class="ztree"></ul>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-9">
	<div class="ibox float-e-margins">
		<div class="ibox-content" style="padding: 5px 0px;">
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<input type="hidden" name="permIds" id="permIds">
					<input type="hidden" name="funId" id="funId">
					<input type="hidden" name="roleId" id="roleId" value="${vo.id }">
					<a class="btn btn-primary" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" href="javascript:;" onclick="savePerm()">赋权</a> 
					<div class="jqGrid_wrapper">
	                      <table id="listTable"></table>
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
		var funId = '${functionVo.id}';
		var setting = {
			data: {simpleData: {enable: true}},
			// 回调函数
			callback : {
				onClick : function(event, treeId, treeNode, clickFlag) {
					$("#funId").val(treeNode.id);
					$("#mesg").text(treeNode.name);
					var postData = $("#listTable").jqGrid("getGridParam", "postData");
					$.extend(postData, {funId:treeNode.id});
					jQuery("#listTable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
				}
			}
		};
		$(document).ready(function(){
			$.ajax({
			    url: '${basePath}sys/rolePerm/treeData4fun.do?roleId=${vo.id}',
				success: function(data) {  
					initTree('${selectedIds}',data);
					var treeObj = $.fn.zTree.getZTreeObj("tree");
					if(funId.length>0){
						var node = treeObj.getNodeByParam("id", funId);
						tree.checkNode(node,!node.checked, true);
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
	$(function() {
		var url = '${basePath}sys/permission/gridData.do';
		var editurl='gridEdit.do';
		var colNames = ['名称','编码','说明','功能'];
		var colModel = [ 
			{
				name : 'name',
				index : 'name',
				width : 80,
				editable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'code',
				index : 'code',
				editable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'describtion',
				index : 'describtion',
				editable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'functionVo.name',
				index : 'function.name',
				width : 80,
				editable : false,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			} ];
			listAllInit(url, colNames, colModel, true);
		});

		function gridComplete() {
			initSelect();
		}
		function savePerm(){
			var uri = "${basePath}sys/rolePerm/ajaxSaveFunPerm.do";
			var functionId = $("#funId").val();
			var roleId = '${vo.id}';
			if(functionId.length<1){
				layer.msg('请选择要赋权的功能', {icon: 0,time: 3000});
				return false;
			}
			var permIds = getSelectStrIds();
			$.ajax({ 
				url:uri,
				dataType:"json",
				data:{"roleId":roleId,"funId":functionId,"permIds":permIds},
				type:"post",
				async:false,
				success: function(data){
					layer.msg(data.message, {icon: 0,time: 3000});
				},
				error:function(ajaxobj){  
			    }  
			});
		}
		function initSelect(){
			var funId = $("#funId").val();
			var roleId = '${vo.id}';
			$.ajax({ 
				url:'${basePath}sys/rolePerm/ajaxgetExistPermIds.do',
				dataType:"json",
				data:{"roleId":roleId,"funId":funId},
				type:"post",
				async:false,
				success: function(data){
					var selectIds = data.selectPerIds;
					if(null !=selectIds && selectIds.length>0){
						setSelections(selectIds);
					}
				},
				error:function(ajaxobj){  
			    }  
			});
		}
		//设置弹窗中jqgrid表格高度
		function setJgridHeight() {
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH - 140);
		}
	</script>
    </body>
</html>