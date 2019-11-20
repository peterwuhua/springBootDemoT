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
			<ol class="breadcrumb">
				<li><a href="gridPage.do">角色</a></li>
				<li><strong>列表</strong></li>
			</ol>
		</div>
		<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
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
<script>
	$(function() {
		var url = 'gridData.do';
		var colNames = [ '编码','名称','说明','排序','操作 '];
		var colModel = [ 
			 {
				name : 'code',
				index : 'code',
				editable : true,
				frozen:true,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'name',
				index : 'name',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'describtion',
				index : 'describtion',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'sort',
				index : 'sort',
				width : 80,
				editable : true,
				searchoptions : {
					sopt : [ 'cn','ne','eq','le','lt','gt','ge']
				},
			
			},{
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
				ce = '<a class="btn btn-outline btn-success btn-xs" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'&roleIds='+ids[i]+'\')">授权</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : ce
				});
			}
		}
	</script> 
</body>
</html>

</html>