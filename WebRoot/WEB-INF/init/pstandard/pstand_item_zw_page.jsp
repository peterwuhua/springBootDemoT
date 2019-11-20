<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<ol class="breadcrumb">
						<li><a href="javascript:backMainPage();">评价标准</a></li>
						<li><strong>限值列表</strong></li>
					</ol>
				</div>
				<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input id="standId" name="standId" value="${vo.standId}" type="hidden" />
					<input id="sampType" name="sampType" value="${vo.sampType}" type="hidden" />
					<input id="sampTypeId" name="sampTypeId" value="${vo.sampTypeId}" type="hidden" />
					<input id="sampTypeName" name="sampTypeName" value="${vo.sampTypeName}" type="hidden" />
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<a class="btn btn-primary" href="javascript:;" onclick="fnEdit()"><i class="fa fa-plus" aria-hidden="true"></i> 关联参数</a> 
						<a class="btn btn-danger" href="javascript:;" onclick="jqgridDelete();">删除</a>
						<a class="btn btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回上一层</a>
					</div>
					<div class="jqGrid_wrapper">
	                      <table id="listTable"></table>
	                      <div id="pager"></div>
	                </div>
				</form>
				</div>
			</div>
		</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
</body>
<script type="text/javascript">
	var lastsel;
	function fnEdit(){
		var sampTypeName='${vo.sampTypeName}';
		parent.layer.open({
			title:'关联参数',	
			type: 2,
			area: ['1000px', '90%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/pstandItem/edit4Zw.do?sampTypeId=${vo.sampTypeId}&standId=${vo.standId}&sampTypeName='+encodeURI(sampTypeName),
			btn: ['确定','关闭'],
			btn1: function(index, layero) {
				var iframeWin=layero.find('iframe')[0];
				iframeWin.contentWindow.fnSelect();
			},
			end: function () {
                location.reload();
            }
		});
	}
	$(function() {
		var url = 'gridData.do?standId=${vo.standId}&sampType=${vo.sampType}';
		var	colNames = ['样品分类','检测项目','分类','MAC','PC-TWA','PC-STEL','超限倍数','操作'];
		var	colModel = [ 
			{
				name : 'sampTypeName',
				index : 'sampTypeName',
				width : 80,
				sortable : false,
				search : false,
			},{
				name : 'itemVo.name',
				index : 'item.name',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'type',
				index : 'type',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'value3',
				index : 'value3',
				width : 70,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'value',
				index : 'value',
				sortable : false,
				width : 70,
				search : false
			},{
				name : 'value2',
				index : 'value2',
				sortable : false,
				width : 70,
				search : false
			},{
				name : 'maxValue',
				index : 'maxValue',
				sortable : false,
				width : 70,
				search : false
			},{
				name : 'act',
				index : 'act',
				width : 70,
				fixed : true,
				title : false,
				search : false,
				sortable : false,
			}];
		gridInitAuto(url, colNames, colModel,'', -1,'',true);
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-210);
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
			grouping: true,
		   	groupingView : {
		   		groupField : ['itemType'],
		   		groupColumnShow : [true],
		   		groupText : ['<b>{0} - {1} 个</b>'],
		   		groupCollapse : false,
				groupSummary : [false]
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
	function jqgridDelete(){
		var selectIds = getSelectIds();
		if(selectIds.length<1){
			layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
			return false;
		}
		layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
			$("#ids").val(selectIds);
			$("#listForm").attr("action","${basePath}init/pstandItem/delete.do");
			$("#listForm").submit();
		});
	}
	 
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:editRow(\''+ids[i]+'\')">修改</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	function editRow(id){
		parent.layer.open({
			title:'更新限值信息',	
			type: 2,
			area: ['800px', '500px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/pstandItem/editData4Zw.do?id='+id,
			btn: ['更新','关闭'],
			btn1: function(index, layero) {
				var iframeWin=layero.find('iframe')[0];
				iframeWin.contentWindow.fnSelect();
			},
			end: function () {
                location.reload();
            }
		});
	}
</script>
 
</html>
