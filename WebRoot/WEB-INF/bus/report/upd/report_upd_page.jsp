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
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>报告返工</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
						<a class="btn btn-primary" href="javascript:;" onclick="showOldWin()"><i class="fa fa-plus" aria-hidden="true"></i> 添加</a> 
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
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>

	<script>
	function showOldWin(){
		parent.layer.open({
			title:'已完成报告',	
			type: 2,
			area: ['1000px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}bus/reportUpd/report.do',
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
	$(function() {
		var url ="";
		url = 'gridData.do';
		var colNames = ['','报告编号','任务编号','客户名称','检测性质','样品类别','样品名称','返工日期','处理人员','操作'];
		var colModel = [ 
			{
				name : 'status',
				index : 'status',
				hidden:true,
				title:false,
				search:false
			},{
				name : 'reportNo',
				index : 'reportNo',
				width :250,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.no',
				index : 'task.no',
				width :120,
				fixed:true,
				formatter:formatNo,
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
			}, {
				name : 'reportVo.taskType',
				index : 'report.taskType',
				sortable : false,
				width :80,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'reportVo.sampTypeName',
				index : 'report.sampTypeName',
				sortable : false,
				width :100,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'reportVo.sampName',
				index : 'report.sampName',
				sortable : false,
				width :150,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'date',
				index : 'date',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'userName',
				index : 'userName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 110,
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
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			var be='';
			if(rData.status=='1'){
				be = '<a class="btn btn-outline btn-success btn-xs" title="查看"  href="javascript:fnShow(\''+ids[i]+'\',\''+rData.reportNo+'\')">查看</a>';
			}else{
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>'
				    +' <a class="btn btn-outline btn-success btn-xs" title="查看"  href="javascript:fnShow(\''+ids[i]+'\',\''+rData.reportNo+'\')">查看</a>';
			}
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnShowTask(\''+rowObject.taskVo.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.taskVo.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function fnShowTask(id,no){
		parent.layer.open({
			title:'任务单【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/task/show.do?id='+id
		});
	}
	function fnShow(id,no){
		var index = layer.open({
			title:'报告返工单【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/reportUpd/show.do?id='+id
		});
	}
	</script>
</body>
</html>