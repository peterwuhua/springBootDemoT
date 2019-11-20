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
						<li><a>行政审批</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
					<div class="btn-group">
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPage.do">未提交的记录</a>
						<a type="button" class="btn btn-xs btn-success active" href="javascript:;">已提交的记录</a>
					</div>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPaged.do" method="post" id="listForm">
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
	$(function() {
		var url = 'gridDatad.do';
		var colNames = ['编号','类型','成本','工时',"申请人",'申请日期','操作'];
		var colModel = [ 
			{
				name : 'code',
				index : 'code',
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'type',
				index : 'type',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'price',
				index : 'price',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'hours',
				index : 'hours',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'userName',
				index : 'userName',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'sdate',
				index : 'sdate',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 70,
				title : false,
				search : false,
				sortable : false
			}];
			gridInitMin(url, colNames, colModel,false);
		});
		function loadComplete(data) {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var rData = data.datas[i];
				je = '<a class="btn btn-outline btn-success btn-xs" title="进度" href="javascript:fnSelectProcess(\''+rData.id+'\',\''+rData.status+'\')">进度</a>';
				be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+rData.id+'\',\''+rData.code+'\')">查看</a>';
				jQuery("#listTable").jqGrid('setRowData', rData.id, {
					act :je +' ' + be
				});
			}
		}
		function fnShow(id,no){
			var index = layer.open({
				title:'查看【'+no+'】',	
				type: 2,
				area: ['1000px','500px'],
				fix: false, //不固定
				maxmin: true,
				content: '/office/cmAudit/show.do?id='+id
			});
		}
		//进度 弹出层
		function fnSelectProcess(id,status){
			layer.open({
				  type: 2,
				  title: false,
				  closeBtn: 1, //不显示关闭按钮
				  fix: false, //不固定
				  shadeClose: true,
				  anim:5,
				  area: ['1000px','500px'],
				  content: '${basePath}bus/progress/show.do?busId='+id+'&busType='+encodeURI(status)
			});
		}
	</script>

</body>
</html>