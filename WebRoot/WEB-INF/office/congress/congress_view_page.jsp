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
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>会务查阅</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
            </div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);"
							onclick="jqgridReload();">刷新</a> 
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

	function fnShow(id){
		var index = layer.open({
			title:'会务查阅',	
			type: 2,
			area: ['800px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/office/congressView/show.do?id='+id,
			end : function(){
				 window.location.reload();//关闭时刷新当前页面
			}
		});
	}
	
	$(function() {
		var url = 'gridData.do';
  		var colNames = ['会议名称','主 讲人','会议地点','开始时间','结束时间','申请人','申请日期','状态','操作'];
		var colModel = [ 
			 {
				name : 'name',
				index : 'name',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'zjr',
				index : 'zjr',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'address',
				index : 'address',
				width : 180,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'beginTime',
				index : 'beginTime',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'endTime',
				index : 'endTime',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'sqr',
				index : 'sqr',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'supportDate',
				index : 'supportDate',
				width : 100,
				sortable : false,
				search : false
			},{
				name : 'fstatus',
				index : 'fstatus',
				width : 100,
				sortable : false,
				search : false
			},{
				name : 'act',
				index : 'act',
				width : 150,
				title : false,
				search : false,
				sortable : false
			}];
		gridInitMin(url, colNames, colModel,false);
		});
		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				var rowData = jQuery("#listTable").jqGrid("getRowData",cl);	
				ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:;" onclick="fnShow(\''+ids[i]+'\');">查看</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : ce 
				});
			}
		}
		
		
	</script>

</body>
</html>
