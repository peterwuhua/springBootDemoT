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
				<h5>
					<ol class="breadcrumb">
						<li><a href="gridPage.do">联系人</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</h5>
			</div>
			<div class="ibox-content">
				<form action="" method="post" id="listForm">
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
		var colNames = [ '客户名称','样品类型', '样品名称', '监测点位', '点位代码','监测项目','备注','操作'];
		var colModel = [
			{
				name : 'customerVo.name',
				index : 'customer.name',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			},{
				name : 'sampTypeName',
				index : 'sampTypeName',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'sampName',
				index : 'sampName',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'name',
				index : 'name',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'code',
				index : 'code',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			},{
				name : 'itemName',
				index : 'itemName',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'remark',
				index : 'remark',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			},{
				name : 'act',
				index : 'act',
				width : 60,
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
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
	</script> 
	
</body>
</html>
