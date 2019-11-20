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
					<li><a>请假申请</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<a class="btn btn-primary" href="javascript:;" onclick="fnEdit('')"><i class="fa fa-plus"> 新增</i></a> 
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
	function fnEdit(id){
		openEditPage('edit.do?id='+id)
	}
	$(function() {
		var url = 'gridData.do';
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
				width : 150,
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
				var be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+ ids[i]+'\')">查看</a>';
				if(rData.fstatus=='已保存'||rData.fstatus=='已退回'){
					be += '  <a class="btn btn-outline btn-info btn-xs" title="修改" href="javascript:fnEdit(\''+ ids[i]+'\')">修改</a>';
					be += '  <a class="btn btn-outline btn-danger btn-xs" title="删除" href="javascript:fnDel(\''+ ids[i]+'\')">删除</a>';
				}
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
		/**
		 * 删除
		 */
		function fnDel(id){
			layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
				$.ajax({ 
					url:"deleteOne.do",
					dataType:"json",
					data:{'id':id},
					type:"post",
					success: function(data){
						if("success"==data.status){
							 location.reload();
						}
						parent.layer.msg(data.message, {icon: 0,time: 3000})
					},
					error:function(ajaxobj){  
						layer.msg(ajaxobj, {icon: 0,time: 3000});
				    }  
				});
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
