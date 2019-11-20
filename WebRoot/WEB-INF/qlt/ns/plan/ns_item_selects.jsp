<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../../include/css.jsp"%>
<style type="text/css">
	#gview_listTable{
		margin-bottom: 0px;
		overflow-x:hidden;
	}
</style>
</head>
<body id="tab_context">
<div class="col-sm-12"  style="padding: 0px;">
	<div class="ibox float-e-margins">
		<div class="ibox-content" style="padding: 0px;">
			<div class="jqGrid_wrapper">
				<table id="listTable"></table>
			</div>
		</div>
	</div>
</div>
<%@ include file="../../../include/js.jsp"%>
<%@ include file="../../../include/grid_page.jsp"%>
</body>
<script type="text/javascript">
	$(function() {
		var url = '${basePath}qlt/ns/list4Ys.do?id=${vo.id}';
		var colNames = ['条款','要素'];
		var colModel = [ 
			{
				name : 'code',
				index : 'code',
				sortable : false,
				width : 30,
				searchoptions:{
					sopt : ['cn']
				}
			},{
				name : 'name',
				index : 'name',
				sortable : false,
				searchoptions:{
					sopt : ['cn']
				}
			}];
		gridInitAuto(url, colNames, colModel, '', -1,'',true);
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-90);
	});
	var index = parent.layer.getFrameIndex(window.name); 
	function fnSelect(){
		var selectIds = getSelectStrIds();
		$.ajax({ 
			url:"${basePath}qlt/ns/addDetail.do",
			dataType:"json",
			data:{'id':'${vo.id}','ids':selectIds},
			type:"post",
			success: function(data){
				parent.layer.msg(data.message, {icon: 0,time: 3000})
				if("success"==data.status){
					parent.location.href="${basePath}qlt/ns/edit.do?id=${vo.id}";
					parent.layer.close(index);
				}
			},
			error:function(ajaxobj){  
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }  
		});
	}

</script>
 <script type="text/javascript">
 function gridInitAuto(url, colNames, colModel, editurl,rowNum,pager,multiselect) {
		$.jgrid.defaults.styleUI = "Bootstrap";
		var mygrid = $("#listTable").jqGrid({
			url : url,
			datatype : "json",
			mtype : "POST",
			colNames : colNames,
			colModel : colModel,
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
			loadComplete : loadComplete
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
</html>
