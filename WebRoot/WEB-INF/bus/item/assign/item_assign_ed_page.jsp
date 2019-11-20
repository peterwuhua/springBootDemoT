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
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>任务分配</a></li>
						<li><strong>已提交列表</strong></li>
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
				<form action="gridPage.do" method="post" id="listForm">
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
		var url ="";
		url = 'gridDatad.do';
		var colNames = ['检测项目','任务编号','检测性质','样品名称','样品数量','检测人','分配日期','上报日期','操作'];
		var colModel = [ 
			{
				name : 'itemName',
				index : 'itemName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.no',
				index : 'task.no',
				formatter:formatTask,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.taskType',
				index : 'task.taskType',
				width : 90,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				search : false,
				sortable : false
			},{
				name : 'sampNum',
				index : 'sampNum',
				width : 80,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'testMan',
				index : 'testMan',
				width : 80,
				fixed:true,
				search : false,
				sortable : false
			},{
				name : 'assignDate',
				index : 'assignDate',
				width : 140,
				fixed:true,
				search : false,
				sortable : false
			},{
				name : 'sbDate',
				index : 'sbDate',
				width : 100,
				fixed:true,
				search : false,
				sortable : false
			},{
				name : 'act',
				index : 'act',
				width : 110,
				fixed:true,
				title : false,
				search : false,
				sortable : false
			}];
		gridInitAuto(url, colNames, colModel,'',20,'#pager',true)
	});
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="进度" href="javascript:fnSelectProcess(\''+rData.id+'\',\''+rData.status+'\')">进度</a>';
			ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+rData.id+'\')">查看</a>';
			if(rData.status=='终止'){
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be+' '+ce
				});
				jQuery('#'+ids[i]).removeClass("jqgrow");
				jQuery('#'+ids[i]).addClass("rowBG");
			}else{
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be+' '+ce
				});
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
			  content: '${basePath}bus/progress/show.do?type=item&busId='+id+'&busType='+encodeURI(status)
		});
	}
	function updateStop(){
		var selectIds = getSelectIds();
		if(selectIds.length<1){
			layer.msg('请选择终止的记录', {icon: 0,time: 3000});
			return false;
		}
		layer.confirm('确认要终止吗?', {icon:3, title:'系统提示'}, function(index){
			$.ajax({ 
				url:"${basePath}bus/itemAssign/update2Stop.do",
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
	function fnShow(id){
		var index = layer.open({
			title:'分配详情',	
			type: 2,
			area: ['1000px','300px'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/itemAssign/show.do?id='+id
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
			rowList : [10,20,50,100,1000],
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
					if(rData.status=='终止'){
						$("#listTable").jqGrid("setSelection", rowIds[k],false);
					}
				}
			},
			onSelectRow:function(id){
			    var rData=jQuery("#listTable").jqGrid('getRowData', id);
			    if(rData.status=='终止'){
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
	function formatTask(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnTask(\''+rowObject.taskVo.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.taskVo.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function fnTask(id,no){
		parent.layer.open({
			title:'任务【'+no+'】',	
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