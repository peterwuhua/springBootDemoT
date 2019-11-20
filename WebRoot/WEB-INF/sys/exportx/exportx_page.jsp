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
					<li><a href="gridPage.do">自定义导出</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</h5>
			<div class="ibox-tools">
				<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
				</a> <a class="close-link"> <i class="fa fa-times"></i>
				</a>
			</div>
		</div>
		<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<div class="col-sm-7">
					<a class="btn btn-primary" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" href="edit.do">新增</a> 
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
		var colNames = ['操作','模块','名称','编码','内容','排序'];
		var colModel = [ 
			 {
				name : 'act',
				index : 'act',
				width : 60,
				title : false,
				search : false,
				resizable : false,
				sortable : false,
				frozen : true
			} ,{
				name : 'busType',
				index : 'busType',
				editable : true,
				resizable : false,
				frozen : true,
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
			}, {
				name : 'content',
				index : 'content',
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
				},
			}];

			pageInit(url, colNames, colModel, editurl);
		});

		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				be = '<a class="btn btn-primary btn-xs" title="修改" href="edit.do?id='+ids[i]+'">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
	</script> 
	
</body>
</html>

</html>