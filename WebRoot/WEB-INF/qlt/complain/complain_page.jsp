<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
.rowBG{
   color:#ed5565;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>业务投诉</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
						<a class="btn btn-primary" href="javascript:void(0);" onclick="fnEdit('edit.do');">新增</a> 
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
		var colNames = ['单号 ','投诉单位','投诉人','电话','投诉日期','受理人','状态','操作'];
		var colModel = [
			 {
				name : 'no',
				index : 'no',
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'custName',
				index : 'custName',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'user',
				index : 'user',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'phone',
				index : 'phone',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'date',
				index : 'date',
				resizable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'userName',
				index : 'userName',
				resizable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'status',
				index : 'status',
				formatter:formatStr,
				sortable : false,
				width : 100,
				stype : 'select',
				searchoptions : {
					sopt : [ 'cn'],
					value:{'':'全部','1':'未处理','2':'已处理'}
				}
			}, {
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
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			var be='';
			if(rData.status=='1'){
				be = '<a class="btn btn-outline btn-success btn-xs" title="回访" href="javascript:fnEdit(\'edit.do?id='+ rData.id+'\');">处理</a>';
			}else{
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:fnEdit(\'edit.do?id='+ rData.id+'\');">修改</a>';
			}
			var ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+ rData.id+'\');">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', rData.id, {
				act : be+' '+ce
			});
			if(rData.status=='1'){
				jQuery('#'+ rData.id).addClass("rowBG");
			}
		}
	}
	function fnEdit(url){
		layer.open({
			title:'投诉单',	
			type: 2,
			area: ['800px', '480px'],
			fix: false, //不固定
			maxmin: true,
			content: url,
			btn: ['确定','取消'], //按钮
			yes: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  iframeWin.fnSelect();
			}
		});
	}
	function fnShow(id){
		layer.open({
			title:'投诉单',	
			type: 2,
			area: ['800px', '480px'],
			fix: false, //不固定
			maxmin: true,
			content: 'show.do?id='+id
		});
	}
	function formatStr(cellValue){
		if(cellValue=='1'){
			return '未处理';
		}else{
			return '已处理';
		}
	}
	function jqgridDelete(){
		var selectIds = getSelectIds();
		if(selectIds.length<1){
			layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
			return false;
		}
		layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
			$("#ids").val(selectIds);
			$("#listForm").attr("action","update2del.do");
			$("#listForm").submit();
		});
	}
	</script> 
</body>
</html>
