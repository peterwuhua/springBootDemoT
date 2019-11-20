<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
			<div class="panel-heading">
						<div class="panel-options">
							<ul class="nav nav-tabs">
								<li ><a href="#cus1" onclick="location.href='${basePath}cus/customer/show.do?id=${vo.customerId}'" data-toggle="tab">客户信息 </a></li>
								<li><a  href="#" onclick="location.href='${basePath}cus/saleContact/gridPage4Cus2.do?customerId=${vo.customerId}&salerId=${vo.custVo.customerVo.salerId}'"  data-toggle="tab">客户跟踪记录</a></li>
								<li class="active"><a  href="#"   data-toggle="tab">历史任务进度</a></li>
							</ul>
						</div>
			</div>
				<form action="gridPage4MySelect.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div class="jqGrid_wrapper">
						<table id="listTable"></table>
						<div id="pager"></div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = 'gridData4MySelect.do?customerId=${vo.customerId}';
		var colNames = ['','任务编号','受检单位','样品名称','样品来源','检测类型','受理日期','受理人','当前进度'];
		var colModel = [ 
			{
				name : 'isBack',
				index : 'isBack',
				hidden:true,
				search : false
			},{
				name : 'no',
				index : 'no',
				formatter:formatNo,
				width : 150,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custVo.custName',
				index : 'cust.custName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'source',
				index : 'source',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskType',
				index : 'taskType',
				width : 100,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'date',
				index : 'date',
				width : 130,
				fixed:true,
				sortable : false,
				search:false
			},{
				name : 'userName',
				index : 'userName',
				width : 100,
				fixed:true,
				sortable : false,
				search:false
			},{
				name : 'status',
				index : 'status',
				width : 80,
				fixed:true,
				sortable : false,
				search : false,
			}];
		gridInitMin(url, colNames, colModel,true);
	});
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			var be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>'
				 +' <a class="btn btn-outline btn-success btn-xs" title="查看"  href="javascript:fnShow(\''+ids[i]+'\')">查看</a>';
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
		layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
			$("#ids").val(selectIds);
			$("#listForm").attr("action","delete.do");
			$("#listForm").submit();
		});
	}
	function showOldWin(){
		parent.layer.open({
			title:'历史记录',	
			type: 2,
			area: ['1000px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}bus/task/old.do',
			btn: ['复制','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin=layero.find('iframe')[0];
				iframeWin.contentWindow.fnSelect(index);
			},
			end: function () {
                location.reload();
            }
		});
	}
	function formatNo(cellValue,options,rowObject){
		var ops=cellValue;
		if(rowObject.isBack =='Y'){
			ops+='<img alt="退回" src="/static/img/tuihui.png" style="height:22px;width:25px;">';
		}
		if(rowObject.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function fnShow(id){
		parent.layer.open({
			title:'委托信息',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/task/show.do?id='+id
		});
	}
	</script>
</body>
</html>