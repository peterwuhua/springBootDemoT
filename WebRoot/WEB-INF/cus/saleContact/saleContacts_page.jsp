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
							<li><a>客户跟踪</a></li>
							<li><strong>列表</strong></li>
						</ol>
					</div>
					<div class="pull-right">
						<div class="btn-group">
							<a type="button" class="btn btn-xs btn-success active" href="javascript:;">客户跟踪记录</a>
							<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPaged.do">跟踪历史记录</a>
						</div>
					</div>
				</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div class="form-group col-md-8">
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<div class="btn-group">
							<button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">
								导出 <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a href="javascript:;" onclick="jqgridExport('cus-customer-export.xls','客户信息列表.xls',0)">全部数据</a></li>
								<li><a href="javascript:;" onclick="jqgridExport('cus-customer-export.xls','客户信息列表.xls',1)">选中数据</a></li>
							</ul>
						</div>
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
		var colNames = ['客户名称','联系人','联系方式','拜访地点','拜访日期','拜访人员','拜访内容','操作'];
		var colModel = [
			{
				name : 'customerName',
				index : 'customerName', //客户名称
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'contactPerson',
				index : 'contactPerson', 
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'contactWay',
				index : 'contactWay', //联系方式
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'address',
				index : 'address', //拜访地点
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},
			{
				name : 'gzDate',  
				index : 'gzDate', //拜访日期
				sortable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'saler',  
				index : 'saler', //拜访人员
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'content',  
				index : 'content', //拜访内容
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 100,
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
				be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be 
				});
			}
		}

	
		
		
		/**
		 * 删除
		 */
		function jqgridDelete(){
			var selectIds = getSelectIds();
			if(selectIds.length<1){
				layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
				return false;
			}
			layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
				$.ajax({
					url : 'update2delete.do?ids='+selectIds,
					datatype : "json",
					success : function(data) {
						if(data=='false'){
							layer.alert("该客户不能被删除，请先删除关联数据！");
						}else{
							window.location.href="gridPage.do";
						}
					}
				});
				
			});
		}
		
		
		function fnSearch(){
			var postData = $("#listTable").jqGrid("getGridParam", "postData");
			$.extend(postData, {'gjStatus':$('#gjStatus').val(),'areaPath':$('#areaPath').val()
				,'cusCates':$('#cusCates').val(),'industry':$('#industry').val()
				,'cusType':$('#cusType').val(),'name':$('#name').val()});
			jQuery("#listTable").jqGrid('setGridParam',{url:'gridData4Used.do'}).trigger("reloadGrid")
		}
		
	</script>

</body>
</html>
