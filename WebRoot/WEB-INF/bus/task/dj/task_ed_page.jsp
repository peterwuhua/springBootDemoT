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
						<li><a>任务登记</a></li>
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
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
						<a class="btn btn-danger" href="javascript:void(0);" onclick="updateStop();">终止</a> 
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
     <script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
	<script>
	 
	$(function() {
		var url = 'gridDatad.do';
		var colNames = ['任务编号','受检单位','样品名称','检测类型','受理日期','受理人','状态','操作'];
		var colModel = [ 
			{
				name : 'no',
				index : 'no',
				width : 150,
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
			},{
				name : 'sampName',
				index : 'sampName',
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
				width : 100,
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
				width : 70,
				fixed:true,
				search:false
			},{
				name : 'act',
				index : 'act',
				width : 140,
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
			if(rData.status!='完成' && rData.status!='终止'){
				be += '  <a class="btn btn-outline btn-success btn-xs" title="更新"  href="javascript:openEditPage(\'edited.do?id='+ids[i]+'\')">更新</a>';
			}
			ce = '<a class="btn btn-outline btn-success btn-xs" title="查看"  href="javascript:fnShow(\''+rData.id+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {act :be+' '+ce});
			if(rData.status=='终止'){
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
			  content: '${basePath}bus/progress/show.do?busId='+id+'&busType='+encodeURI(status)
		});
	}
	function formatNo(cellValue,options,rowObject){
		var ops=cellValue;
		if(rowObject.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
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
				url:"${basePath}bus/task/update2Stop.do",
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
	function showRwd(id){
		POBrowser.openWindow('${basePath}bus/task/showRwd.do?id='+id,'width=1200px;height=800px;');
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
			sortname : 'lastUpdTime',
			sortorder : "desc",
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
</script>
</body>
</html>