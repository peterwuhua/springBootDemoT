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
					<li><a>销假</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
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
		var url = 'gridData4Xj.do';
		var editurl='gridEdit.do';
  		var colNames = ['编号','类型','部门','申请人','开始时间','截止时间','描述','状态','操作'];
		var colModel = [ 
			{
				name : 'no',
				index : 'no',
				width : 100,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'type',
				index : 'type',
				sortable : false,
				width : 80,
				fixed:true,
				stype : 'select',
				searchoptions : {
					sopt : ['eq'],
					value:{'':'全部','事假':'事假','婚假':'婚假','病假':'病假','丧假':'丧假','哺乳假':'哺乳假','陪产假':'陪产假','产假':'产假','外出':'外出'}
				}
			}, {
				name : 'deptName',
				index : 'deptName',
				width : 100,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'userName',
				index : 'userName',
				width : 80,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'startTime',
				index : 'startTime',
				width : 140,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'endTime',
				index : 'endTime',
				width : 140,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'content',
				index : 'content',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'fstatus',
				index : 'fstatus',
				width : 80,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'act',
				index : 'act',
				width : 120,
				fixed:true,
				title : false,
				search : false,
				sortable : false
			}];
		gridInitMin(url, colNames, colModel,false);
		});
		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var rData = $('#listTable').jqGrid('getRowData',ids[i]);
				var be = '<a class="btn btn-outline btn-info btn-xs" title="查看" href="javascript:fnShow(\''+ ids[i]+'\')">查看</a>';
					be += '  <a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:fnUpdate(\''+ ids[i]+'\')">销假</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
		function fnUpdate(id)
		{
			$.ajax({ 
				url:'update4Xj.do?id='+id,
				dataType:"json",
				type:"post",
				success: function(data){
					parent.toastr.success(data.message, '');
					if("success"==data.status){
						 location.reload();
					}else{
						parent.toastr.error(ajaxobj, '');
					}
				},
				error:function(ajaxobj){  
					parent.toastr.error(ajaxobj, '');
			    }  
			});
		}
		function fnShow(id){
			layer.open({
				title:'查看',	
				type: 2,
				area: ['1000px', '400px'],
				fix: false, //不固定
				maxmin: true,
				content: 'show.do?id='+id
			});
		}
	</script>

</body>
</html>
