<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<style type="text/css">
.col-sm-3{
	padding: 0px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox-title">
			<h5>
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">角色</a></li>
					<li><strong>信息</strong></li>
					<li><strong style="color: green;">${vo.name}</strong></li>
				</ol>
			</h5>
		</div>
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
					<h5>文件信息</h5>
				</div>
				<div class="ibox-content">
					<form id="thisForm" method="post" action="saveRoleDocument.do?reSave=0" class="form-horizontal">
						<input name="id" value="${vo.id}" type="hidden" /> <input name="roleIds" value="${vo.id}" type="hidden" /> <input name="fileIds" id="fileIds" type="hidden" /> <input name="pid" id="pid" type="hidden" />
						<div class="form-group" style="margin-bottom: 0px;">
							<div class="col-sm-7">
								<button class="btn btn-primary" type="button" id="submitBut"><i class="fa fa-check" aria-hidden="true"></i> 确定</button>
								<a class="btn btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
							</div>
						</div>
						<div class="jqGrid_wrapper">
							<table id="listTable"></table>
							<div id="pager"></div>
						</div>
						<div class="hr-line-dashed"></div>
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
					jQuery("#listTable").jqGrid('setGridParam',{url:'docGridData.do'}).trigger("reloadGrid")
				}
			}
		};
		$(document).ready(function() {
			$.ajax({
				url : '${basePath}doc/category/treeData.do',
				success : function(data) {
					initTree('${selectedIds}', data);
				}
			});
		});
	</script>
		<script type="text/javascript">
		$(function() {
			$('#submitBut').click(function() {
				var selectIds = getSelectIds();
				$("#fileIds").val(selectIds);
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
				        backMainPage();
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
			})
		})
		
		function fnSave(){
				var selectIds = getSelectIds();
				$("#fileIds").val(selectIds);
				$('form').attr('action','saveRoleDocument.do?reSave=1');
				$('form').submit();
			}
	</script>
		<script>
	$(function() {
		var pid = '${vo.pid}';
		var url ="";
		if(pid.length>0){
		 url = 'docGridData.do?pid='+pid;
		}else{
			url = 'docGridData.do';
		}
		var editurl='';
		var colNames = ['名称','标题','大小','类型','标记','上传人','上传时间','说明','状态'];
		var colModel = [
			{
				name : 'name',
				index : 'name',
				width : 200,
				frozen : true,
				resizable : true,
				editable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'title',
				index : 'title',
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
			},{
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
				width : 200,
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
				name : 'time',
				index : 'time',
				editable : true,
				resizable : true,
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'describtion',
				index : 'describtion',
				editable : true,
				width : 200,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'state',
				index : 'state',
				editable : true,
				width : 200,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];
			pageInit(url, colNames, colModel, editurl);
		});
	
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var cl = ids[i];
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
			});
		}
		setSelections('${documentSelectIds}');
	}
	
	function setSelections(ids) {
		if(ids.length>0){
			var ids = ids.replace("，",",");
			var s = ids.split(",");
			for ( var i = 0; i <= s.length; i++){
				if(s[i] != ""){
					jQuery('#listTable').jqGrid('setSelection',s[i]);
				}
			}
		}
	}
	</script>
</body>
</html>
