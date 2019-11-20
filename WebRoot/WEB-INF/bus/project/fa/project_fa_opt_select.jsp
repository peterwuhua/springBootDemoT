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
		var sampType='${vo.sampType}';
		var url = '${basePath}cus/clientPoint/listPoint.do?clientVo.id=${vo.custVo.clientVo.id}&sampTypeId=${vo.sampTypeId}&sampType='+encodeURI(sampType);
		var colNames = ['检测点位','车间/岗位','点位代码','样品类别','检测项目'];
		var colModel = [ 
			{
				name : 'name',
				index : 'name',
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'room',
				index : 'room',
				searchoptions : {
					sopt : [ 'cn']
				}
			},  {
				name : 'code',
				index : 'code',
				width : 90,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'sampTypeName',
				index : 'sampTypeName',
				width : 90,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'itemName',
				index : 'itemName',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];
			gridInitAuto(url, colNames, colModel, '', -1,'',true);
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-90);
	});
	 
	function gridComplete() {
		//var id='${vo.ids}';
		//$(this).jqGrid('setSelection', id);
	}
	function fnSelect(){
		var data={};
		data.id=getSelectStrIds();
		return data;
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
