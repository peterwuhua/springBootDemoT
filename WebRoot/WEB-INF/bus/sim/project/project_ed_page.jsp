<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
.rowBG{
   color:#ed5565;
}
.ui-jqgrid tr.jqgrow td {
    overflow: hidden;
    white-space: pre;
    padding-right: 8px!important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>项目立项</a></li>
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
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
						<a class="btn btn-danger" href="javascript:;" onclick="updateStop();">终止</a>
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
	 
	$(function() {
		var url = 'gridDatad.do';
		var colNames = ['','项目编号','项目名称','项目类型','委托单位','拟完成日期','项目负责人','当前进度','操作'];
		var colModel = [ 
			{
				name : 'isBack',
				index : 'isBack',
				hidden:true,
				search : false
			},{
				name : 'no',
				index : 'no',
				width : 150,
				fixed:true,
				formatter:formatNo,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				width : 150,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'itemType',
				index : 'itemType',
				width : 150,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custVo.custName',
				index : 'cust.custName',
				width : 180,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'finishDate',
				index : 'finishDate',
				width : 120,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'userName',
				index : 'userName',
				width : 120,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'status',
				index : 'status',
				width : 80,
				fixed:true,
				formatter:formatStatus,
				sortable : false,
				search : false,
			},{
				name : 'act',
				index : 'act',
				width : 100,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
		gridInitAuto(url, colNames, colModel, '',20,'#pager',true)
	});
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="日志" href="javascript:fnSelectProcess(\''+rData.id+'\',\''+rData.status+'\')">日志</a>';
			ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+rData.id+'\',\''+rData.no+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {act : be+' '+ce});
			if(rData.status=='stop'){
				jQuery('#'+ids[i]).addClass("rowBG");
				jQuery("#listTable").jqGrid('setRowData', ids[i], {cb :''});
			}
		}
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
			  content: '${basePath}bus/progress/show4simple.do?busId='+id+'&busType='+encodeURI(status)
		});
	}
	function formatNo(cellValue,options,rowObject){
		var ops=cellValue;
		if(rowObject.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function formatStatus(cellValue,options,rowObject){
		var ops=cellValue;
		if(ops=="finish"){
			ops = "完成";
		}else if(ops=="stop"){
			ops = "终止";
		}else if(ops=="XM_00"){
			ops = "项目立项";
		}else if(ops=="XM_10"){
			ops = "合同评审";
		}else if(ops=="XM_20"){
			ops = "合同签订";
		}else if(ops=="XM_30"){
			ops = "任务登记";
		}else if(ops=="XM_40"){
			ops = "派发任务";
		}else if(ops=="XM_50"){
			ops = "现场踏勘";
		}else if(ops=="XM_55"){
			ops = "方案编制";
		}else if(ops=="XM_60"){
			ops = "报告编制";
		}else if(ops=="XM_70"){
			ops = "报告评审";
		}else if(ops=="XM_80"){
			ops = "提交备案";
		}else if(ops=="XM_90"){
			ops = "备案归档";
		}
		
		return ops;
	}
	
	function updateStop(){
		var selectIds = getSelectIds();
		if(selectIds.length<1){
			layer.msg('请选择终止的记录', {icon: 0,time: 3000});
			return false;
		}
		layer.confirm('确认要终止吗?', {icon:3, title:'系统提示'}, function(index){
			$.ajax({ 
				url:"${basePath}bus/sim/project/update2Stop.do",
				data: {'ids':selectIds.join(',')},
				async:false,
				success: function(data){
					if(data.status=='success'){
						jqgridReload()
					}
					layer.msg(data.message, {icon: 0,time: 3000});
				},
				error:function(ajaxobj){  
					layer.msg(ajaxobj, {icon: 0,time: 1000});
		    	}  
			});
		});
	}
	</script>
	<script>
	function gridInitAuto(url, colNames, colModel, editurl,rowNum,pager,multiselect) {
		$.jgrid.defaults.styleUI = "Bootstrap";
		var mygrid = $("#listTable").jqGrid({
			url : url,
			datatype : "json",
			mtype : "POST",
			colNames : colNames,
			colModel : colModel,
			rownumbers:true,
			rowNum : rowNum,
			rowList : [10,20,50,100],
			pager : pager,
			sortname : 'sort',
			sortorder : "asc",
			viewrecords : true,
			height:'auto',
			autowidth:true,
			recordpos : 'right',
			jsonReader : {
				root : 'datas'
			},
			shrinkToFit:true,    
	        autoScroll: true,
	        search:true,
			editurl:editurl,
			multiselect : multiselect,
			gridComplete : gridComplete,
			loadComplete : loadComplete,
			onSelectAll:function(rowid, status) { //点击全选时触发事件
				var rowIds = jQuery("#listTable").jqGrid('getDataIDs');//获取jqgrid中所有数据行的id
				for(var k=0; k<rowIds.length; k++) {
					var rData=jQuery("#listTable").jqGrid('getRowData', rowIds[k]);
					if(rData.status=='stop'){
						$("#listTable").jqGrid("setSelection", rowIds[k],false);
					}
				}
			},
			onSelectRow:function(id){
			    var rData=jQuery("#listTable").jqGrid('getRowData', id);
			    if(rData.status=='stop'){
					$("#listTable").jqGrid("setSelection",id,false);
				}
			}
		});
		setFilterToolbar();//行内查询
		setJgridWidth();//宽度
		setJgridHeight();//高度
		setNavGrid();//功能按钮
		jQuery("#listTable").jqGrid('setFrozenColumns'); //冻结表头
		$(".ui-search-oper").hide();
		fncleanName();
	}
	function fnShow(id,no){
		parent.layer.open({
			title:'项目【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/sim/project/show.do?id='+id
		});
	}
	</script>
</body>
</html>