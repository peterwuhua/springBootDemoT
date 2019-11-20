<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/css.jsp"%>
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
<%@ include file="../../include/js.jsp"%>
<%@ include file="../../include/grid_page.jsp"%>
</body>
<script type="text/javascript">
	$(function() {
		var isNow='${vo.isNow}';
		var url = '${basePath}init/item/gridData.do';
		if(isNow=='Y'){//现场项目
			url += '?isNow=Y';
		}else if(isNow=='N'){
			url += '?isNow=N';
		}
		var sampType='${vo.sampTypeIds}';
		if(sampType!=''&& sort==''){
			url+="?sampTypeIds="+sampType;
		}else if(sampType!=''){
			url+="&sampTypeIds="+sampType;
		}
		var colNames = ['检测项目','计量单位','参考价格','积分','现场监测','样品类别'];
		var colModel = [ 
		  {
			name : 'name',
			index : 'name',
			sortable : false,
			searchoptions:{
				sopt : ['cn']
			}
		},{
			name : 'unit',
			index : 'unit',
			sortable : false,
			width : 100,
			fixed:true,
			searchoptions : {
				sopt : ['cn']
			}
		},{
			name : 'price',
			index : 'price',
			sortable : false,
			width : 80,
			fixed:true,
			searchoptions : {
				sopt : [ 'cn']
			}
		},{
			name : 'hours',
			index : 'hours',
			sortable : false,
			width : 80,
			fixed:true,
			searchoptions : {
				sopt : [ 'cn']
			}
		},{
			name : 'isNow',
			index : 'isNow',
			formatter:formatStr,
			stype : 'select',
			width : 80,
			fixed:true,
			searchoptions : {
				sopt : [ 'cn'],
				value:{'':'全部','Y':'是','N':'否'}
			}
		},{
			name : 'sampTypeNames',
			index : 'sampTypeNames',
			sortable : false,
			searchoptions : {
				sopt : [ 'cn']
			}
		}];
		gridInitAuto(url, colNames, colModel, '', -1,'',true);
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-90);
	});
	function formatStr(cellValue){
		if(cellValue=='Y'){
			return '是';
		}else{
			return '否';
		}
	}
	function gridComplete() {
		var id='${vo.id}';
		$(this).jqGrid('setSelection', id);
	}
	function fnSelect(){
		var selectIds = getSelectIds();
		var rowData = $("#listTable").jqGrid('getRowData',selectIds[0]);
		var data={};
		data.id=selectIds[0];
		data.name=rowData.name;
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
