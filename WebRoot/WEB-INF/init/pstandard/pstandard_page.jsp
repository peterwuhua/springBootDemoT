<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>评价标准</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<a class="btn btn-primary" href="javascript:openEditPage('edit.do')">新增</a> 
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
		function fnEdit() {
			openEditPage('edit.do');
		}
		$(function() {
			var url = 'gridData.do';
			var editurl = 'gridEdit.do';
			var colNames = [ '标准编号', '标准名称', '类型','发布日期', '实施日期','标准文件', '状态', '操作' ];
			var colModel = [ {
				name : 'code',
				index : 'code',
				width : 90,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'name',
				index : 'name',
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'type',
				index : 'type',
				align : 'center',
				width : 40,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'fbDate',
				index : 'fbDate',
				align : 'center',
				width : 50,
				search : false
			}, {
				name : 'ssDate',
				index : 'ssDate',
				align : 'center',
				width : 50,
				search : false
			},{
				name : 'fileName',
				index : 'fileName',
				formatter:showFile,
				search : false
			}, {
				name : 'status',
				index : 'status',
				align : 'center',
				width : 50,
				search : false
			}, {
				name : 'act',
				index : 'act',
				width : 100,
				fixed : true,
				title : false,
				search : false,
				sortable : false,
			} ];
			gridInitAuto(url, colNames, colModel, '','20','#pager',true)
		});
		function loadComplete(data) {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var rData = data.datas[i];
				if(rData.status=='现行'){
					be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ ids[i] + '\')">修改</a>';
					ce = '<a class="btn btn-outline btn-success btn-xs" title="限值" href="javascript:;" onclick="goTo(\''+rData.sampTypeId+'\',\''+rData.sampTypeName+'\',\''+ids[i]+'\')">限值</a>';
					jQuery("#listTable").jqGrid('setRowData', ids[i], {
						act : be+' '+ce
					});
				}else{
					be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:openEditPage(\'show.do?id='+ ids[i] + '\')">查看</a>';
					jQuery("#listTable").jqGrid('setRowData', ids[i], {
						act : be
					});
				}
			}
		}
		function goTo(sampTypeId,sampTypeName,standId){
			openEditPage('${basePath}init/pstandItem/gridPage.do?sampTypeId='+sampTypeId+'&standId='+ standId+ '&sampTypeName='+encodeURI(sampTypeName));
		}
		function showFile(cellValue,options,rowObject){
			return '<a href="javascript:;" onclick="openPdf(\'${basePath}'+rowObject.filePath+'\',\''+cellValue+'\')" >'+cellValue+'</a>';
		}
		function openPdf(uri,allName){
			var url = uri+'?allName='+allName;
			 layer.confirm("请选择打开方式", {icon:3, title:'系统提示',btn:['当前窗口打开','新窗口打开']},
			 function(index, layero){
				 parent.layer.open({
					  type: 2,
					  content: url,
					  area: ['893px', '600px'],
					  shadeClose: true,
					  maxmin: true,
					  fix: false, //不固定
					  resize: true
					});
				 layer.close(index);
			 }, 
			 function(index){
				 window.open(url);
				 layer.close(index);
			 });
		}
	</script>
</body>
</html>
