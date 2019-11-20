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
					<li><a href="gridPage.do">监测项目</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="page.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-primary" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
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
			var colNames = [ '项目名称', '计量单位', '样品标识', '现场监测','样品类别','方法标准','操作' ];
			var colModel = [ {
				name : 'name',
				index : 'name',
				width : 200,
				fixed : true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'unit',
				index : 'unit',
				width : 80,
				fixed : true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'code',
				index : 'code',
				width : 80,
				fixed : true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'isNow',
				index : 'isNow',
				sortable : false,
				width : 80,
				fixed : true,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'sampTypeNames',
				index : 'sampTypeNames',
				width : 100,
				fixed : true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			},
			{
				name : 'standNames',
				index : 'standNames',
				sortable : false,
				title : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'act',
				index : 'act',
				width : 120,
				fixed : true,
				title : false,
				search : false,
				sortable : false,
			} ];
			gridInitAuto(url, colNames, colModel, '', '20', '#pager', true)
		});

		function loadComplete(data) {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var rData = data.datas[i];
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ rData.id + '\')">修改</a>';
				be += ' <a class="btn btn-outline btn-success btn-xs" title="检测人" href="javascript:openEditPage(\'${basePath}init/itemUser/gridPage.do?itemId='
						+ rData.id + '\')">检测人</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
		function jqgridDelete() {
			var selectIds = $('#listTable').jqGrid('getGridParam', 'selarrrow');
			if (selectIds.length < 1) {
				layer.msg('请选择要删除的记录', {
					icon : 0,
					time : 3000
				});
				return false;
			}
			layer.confirm('确认要删除吗?', {
				icon : 3,
				title : '系统提示'
			}, function(index) {
				$.ajax({
					url : "${basePath}init/item/update4del.do",
					data : {
						'ids' : selectIds.join(',')
					},
					async : false,
					success : function(data) {
						if ("success" == data.status) {
							parent.toastr.success(data.message, '');
							window.location.reload();
						} else {
							parent.toastr.error(data.message, '');
						}
					},
					error : function(ajaxobj) {
						parent.toastr.error(ajaxobj, '');
					}
				});
			});
		}
	</script>
</body>
</html>
