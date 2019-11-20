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
			<div class="panel-heading" style="padding-bottom: 0px;">
				<div class="panel-options">
					<ul class="nav nav-tabs" id="myTab">
						<li class="" id="tab1">
							<a aria-expanded="true">
								限值
							</a>
						</li>
						<li class="disabled" id="tab2">
							<a aria-expanded="false">
								非限值
							</a>
						</li>
					</ul>
				</div>
			</div>	
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
var isEvaluate='${vo.isEvaluate}';
	$(function() {
		var url = '${basePath}init/pstandItem/listItem.do?sampTypeId=${vo.sampTypeId}';
		var colNames = ['排序','','','指标分类','测试参数','限值'];
		var colModel = [ 
		 {
			name : 'sort',
			index : 'sort',
			width : 40,
			editable : true,
			search : false,
			align:'center',
		},{
			name : 'code',
			index : 'code',
			hidden : true
		},{
			name : 'type',
			index : 'type',
			hidden : true
		},{
			name : 'itemType',
			index : 'itemType',
			width : 80,
			editable : true,
			stype : 'select',
			searchoptions:{
				sopt : ['eq'],
				value:selectStand()
			}
		},{
			name : 'itemName',
			index : 'itemName',
			sortable : false,
			searchoptions : {
				sopt : ['cn']
			}
		},{
			name : 'limitLine',
			index : 'limitLine',
			sortable : false,
			width : 80,
			search : false,
			editable : false,
		}];
		var url1 = '${basePath}init/item/gridData.do';
		var colNames1 = ['测试参数','计量单位','默认检测人'];
		var colModel1 = [ 
		  {
			name : 'name',
			index : 'name',
			editable : true,
			searchoptions:{
				sopt : ['eq']
			}
		},{
			name : 'unit',
			index : 'unit',
			sortable : false,
			searchoptions : {
				sopt : ['cn']
			}
		},{
			name : 'testUserName',
			index : 'testUserName',
			sortable : false,
			searchoptions : {
				sopt : ['cn']
			}
		}];
		
		if(isEvaluate=='是'){
			$('#tab1').addClass('active');
			$('#tab2').removeClass('active');
			$('#tab2').addClass('disabled');
			gridInitAuto2(url, colNames, colModel, '', -1,'',true);
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight',win.WinH-140);
		}else{
			$('#tab2').addClass('active');
			$('#tab1').removeClass('active');
			$('#tab1').addClass('disabled');
			gridInitAuto(url1, colNames1, colModel1, '', -1,'',true);
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-140);
		}
	});
	 
	function gridInitAuto2(url, colNames, colModel, editurl,rowNum,pager,multiselect) {
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
			loadComplete : loadComplete,
			grouping: true,
		   	groupingView : {
		   		groupField : ['code', 'type'],
		   		groupColumnShow : [false, false],
		   		groupText : ['<b>{0} - {1} 个</b>', '<b>{0} - {1} 个</b>'],
		   		groupCollapse : false,
				groupOrder: ['asc', 'asc'],
				groupSummary : [false, false]
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
	function selectStand() {
		var optionstring ={'':'全部',};
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=pstand-item-type',
			datatype : "json",
			async:false,
			success : function(data) {
				var value = data.split(",");
				for (var i = 0; i < value.length; i++) {
					var opt=value[i];
					optionstring[opt]=opt;
				}
				
			}
		});
		return optionstring;
	}
	function selectSamp() {
		var optionstring ={'':'全部',};
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=pstand-item-type',
			datatype : "json",
			async:false,
			success : function(data) {
				var value = data.split(",");
				console.log(value)
				for (var i = 0; i < value.length; i++) {
					var opt=value[i];
					optionstring[opt]=opt;
				}
				
			}
		});
		return optionstring;
	}
	function gridComplete() {
		var id='${vo.ids}';
    	if(id!=null&&id!=''){
    		var ids=id.split(',');
    		for(var i=0;i<ids.length;i++){
    			if(ids[i]!=''){
    				console.log(ids[i])
    				$(this).jqGrid('setSelection', ids[i]);
    			}
    		}
    	}
	}
	var index = parent.layer.getFrameIndex(window.name); 
	
	function fnSelect(){
		var rowIds = $('#listTable').jqGrid('getGridParam', 'selarrrow');
		var names='';
		for(var i=0;i<rowIds.length;i++){
			var rData = $('#listTable').jqGrid('getRowData',rowIds[i]);
			if(isEvaluate=='是'){
				names+=rData.itemName;
			}else{
				names+=rData.name;
			}
			if(i<rowIds.length-1){
				names+=',';
			}
		}
		parent.setItemData(rowIds.join(','),names);
		parent.layer.close(index);
	}
</script>
 
</html>
