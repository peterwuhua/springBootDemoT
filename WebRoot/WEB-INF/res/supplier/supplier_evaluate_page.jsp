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
					<li><a>供应商</a></li>
					<li><a>评价管理</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="id" id="id" value="${vo.id}">
				<input type="hidden" name="supplierVo.id" id="supplierVo.id" class="formText" value="${vo.id}"/>
				<input type="hidden" name="ids" id="ids">
				<div>
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
					<a class="btn btn-primary" href="javascript:;" onclick="fnEdit('')">新增</a> 
					<a class="btn btn-danger" href="javascript:;" onclick="gridDelete();">删除</a>
					<a class="btn btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
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
		layer.open({
			title:'新增/修改',	
			type: 2,
			area: ['800px', '470px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}/res/supplierEvaluate/edit.do?supplierVo.id=${vo.supplierVo.id}&id='+id,
			btn: ['确定','取消'], //按钮
			yes: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  iframeWin.fnSelect();
			}
		});
	}
	function gridDelete(){
		var selectIds = getSelectIds();
		if(selectIds.length<1){
			layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
			return false;
		}
		layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
			$("#ids").val(selectIds);
			$("#listForm").attr("action","/res/supplierEvaluate/update2del.do");
			$("#listForm").submit();
		});
	}
	$(function() {
		var url = '/res/supplierEvaluate/gridData.do?supplierVo.id='+$("#id").val();
		var colNames = ['供应商名称 ','商品名称','供应时间','综合评价','评价人','评价时间 ','操作'];
		var colModel = [
			  {
				name : 'supplierVo.name',
				index : 'supplier.name',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'tradeName',
				index : 'tradeName',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'supTime',
				index : 'supTime',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'productQuality',
				index : 'productQuality',
				width : 200,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'evaluatePerson',
				index : 'evaluatePerson',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'evaluateTime',
				index : 'evaluateTime',
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 70,
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
			be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:fnEdit(\''+ids[i]+'\')">修改</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	</script> 
	
</body>
</html>
