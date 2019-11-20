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
				<li><a>公告</a></li>
				<li><strong>列表</strong></li>
			</ol>
		</div>
		<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<div>
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" href="javascript:void(0);" onclick="openEditPage('edit.do');">新增</a> 
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
		var editurl='gridEdit.do';
		var colNames = ['主题','发布人','发布日期','截至日期','序号','操作'];
		var colModel = [ 
			 {
				name : 'subject',
				index : 'subject',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'userName',
				index : 'userName',
				sortable : false,
				width : 70,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sendTime',
				index : 'sendTime',
				sortable : false,
				width : 70,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'endTime',
				index : 'endTime',
				sortable : false,
				width : 70,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'sort',
				index : 'sort',
				width : 50,
				sortable : false,
				search : false
			}, {
				name : 'act',
				index : 'act',
				width : 130,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
		gridInitMin(url, colNames, colModel,true);
		});

		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				ae = '<a class="btn btn-outline btn-success btn-xs" title="详情" href="javascript:openEditPage(\'show.do?id='+ids[i]+'\')">详情</a>';
				be = '<a class="btn btn-outline btn-success btn-xs" title="再次发送" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">再次发送</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : ae+' '+be
				});
			}
		}
	</script> 
	
</body>
</html>

</html>