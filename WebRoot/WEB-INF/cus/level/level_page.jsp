<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><a href="gridPage.do">客户等级</a></li>
						<li><strong>列表</strong></li>
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
				<div class="col-sm-7">
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" href="javascript:void(0);" onclick="openEditPage('edit.do');">新增</a> 
					<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
				</div>
				<div class="col-sm-5"></div>
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
		var editurl='gridEdit.do';
		var colNames = ['操作', '编码','名称','说明'];
		var colModel = [ 
   			 {
   				name : 'act',
   				index : 'act',
   				width : 100,
   				title : false,
   				search : false,
   				frozen : true,
   				resizable : false,
   				sortable : false,
   			} , {
   				name : 'code',
   				index : 'code',
   				width : 100,
   				frozen : true,
   				resizable : false,
   				editable : true,
   				searchoptions : {
   					sopt : [ 'cn']
   				}
   			}, {
   				name : 'name',
   				index : 'name',
   				editable : true,
   				frozen : true,
   				resizable : false,
   				width : 100,
   				searchoptions : {
   					sopt : [ 'cn']
   				}
   			},{
   				name : 'remark',
   				index : 'remark',
   				editable : false,
   				width : 150,
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
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
	</script> 
	
</body>
</html>
