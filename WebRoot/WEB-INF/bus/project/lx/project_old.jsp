<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../../include/css.jsp"%>
</head>
<body id="tab_context">
<div class="col-sm-12" style="height: 100%">
	<div class="ibox float-e-margins">
		<div class="ibox-content" style="padding: 0px;">
			<form action="" method="post" >
				<div class="jqGrid_wrapper">
					<table id="listTable"></table>
				</div>
			</form>
		</div>
	</div>
</div>
<%@ include file="../../../include/js.jsp"%>
<%@ include file="../../../include/grid_page.jsp"%>
</body>
<script type="text/javascript">
	$(function() {
		var url = '${basePath}bus/project/gridData4Old.do';
		var colNames = ['','项目编号','受检单位','检测类型','样品名称','样品来源','是否踏勘','是否评价','立项日期','拟完成日期','操作'];
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
				name : 'taskType',
				index : 'taskType',
				width : 80,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'sampName',
				index : 'sampName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'zsy',
				index : 'zsy',
				sortable : false,
				width : 80,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'xctk',
				index : 'xctk',
				sortable : false,
				width : 80,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'pj',
				index : 'pj',
				sortable : false,
				width : 80,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'rdate',
				index : 'rdate',
				width : 80,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'finishDate',
				index : 'finishDate',
				width : 80,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
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
		gridInitAuto(url, colNames, colModel, '', -1,'',true);
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-90);
	});
	function fnSelect(index){
		var id = getSelectIds()[0];
		$.ajax({
			url:'${basePath}bus/project/copyProject.do?id='+id,
			dataType:"json",
			type:"post",
			async:false,
			success: function(data){
				parent.layer.msg(data.message, {icon: 0,time: 3000})
				if("success"==data.status){
					parent.layer.close(index);
				}
			},
			error:function(ajaxobj){
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }  
		});
	}
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
			sortorder : "DESC",
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
			beforeSelectRow: beforeSelectRow
		});
		setFilterToolbar();//行内查询
		setJgridWidth();//宽度
		setJgridHeight();//高度
		setNavGrid();//功能按钮
		jQuery("#listTable").jqGrid('setFrozenColumns'); //冻结表头
		$(".ui-search-oper").hide();
		fncleanName();
	}
	function beforeSelectRow(){
    	$("#listTable").jqGrid('resetSelection');  
        return(true);
    }
</script>
 
</html>
