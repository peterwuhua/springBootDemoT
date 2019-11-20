<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 0px;">
				<form id="thisForm" method="post" action="savePstandItem4Zw.do" class="form-horizontal">
					<input id="standId" name="standId" value="${vo.standId}" type="hidden" />
					<input id="itemId" name="ids" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="padding: 0px;margin: 0px;">
						<tbody>
							<tr>
								<td class="active" style="width: 120px;"><label class="pull-right">样品类型:</label></td>
								<td class="input-group" style="width: 280px;">
                                      <input type="text" readonly="readonly" id="sampTypeName" name="sampTypeName" value="${vo.sampTypeName}" class="form-control required" validate="required" placeholder="请选择"  onclick="fnSelectSampType()">
                                      <input type="hidden" id="sampTypeId" name="sampTypeId" value="${vo.sampTypeId}">
                                      <div class="input-group-btn">
                                          <button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectSampType()">选择</button>
                                      </div>
								</td>
								<td rowspan="7" style="width: 600px;">
									 <div class="jqGrid_wrapper" style="width: 300px;">
					                      <table id="listTable"></table>
					                </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类:</label></td>
								<td>
									<input type="text" value="${vo.type}" id="type" name="type" class="form-control" placeholder="">
								</td>
							</tr>
							<tr>
								 <td colspan="2">标准浓度（mg/m3）</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">MAC:</label></td>
								<td>
							 		<input id="value3"   type="text" name="value3" class="form-control" >
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">PC-WTA:</label></td>
								<td>
							 		<input id="value" type="text" name="value" class="form-control number">
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">PC-STEL:</label></td>
								<td>
							 		<input id="value2" type="text" name="value2" class="form-control number" >
								</td>
							</tr>
							<tr>
								 <td colspan="2">
								 	<p>分类一般指类似粉尘类型等</p>
								 </td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
</body>
<script type="text/javascript">
function fnSelectSampType(){
	layer.open({
		title:'分类选择',	
		type: 2,
		area: ['300', '75%'],
		fix: false, //不固定
		maxmin: true,
		content: 'type_select.do?sampType=${vo.sampType}&sampTypeId='+$('#sampTypeId').val(),
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			 var data= iframeWin.fnSelect();
			 $('#sampTypeId').val(data.id);
			 $('#sampTypeName').val(data.name);
			 reload();
		}
	});
}
function reload(){
	var sampTypeId= $('#sampTypeId').val();
	var postData = $("#listTable").jqGrid("getGridParam", "postData");
	$.extend(postData, {'sampTypeIds':sampTypeId});
	jQuery("#listTable").jqGrid('setGridParam',{url:'${basePath}init/item/gridData4StandItem.do'}).trigger("reloadGrid")
}
var index = parent.layer.getFrameIndex(window.name); 
function fnSelect(){
	var xzType=$('#xzType').val();
	if(xzType=='数值范围'){
		var maxValue=$('#maxValue').val();
		var minValue=$('#minValue').val();
		var reg=/^[+-]?((\d+)|(\d+\.\d+))$/;
		if(maxValue=='' && minValue==''){
			 layer.msg('最大或最小值不能为空', {icon: 0,time: 3000});
			 return null;
		}else if(maxValue!='' && !reg.test(maxValue)){
			layer.msg('最大值输入格式不正确', {icon: 0,time: 3000});
			 return null;
		}else if(minValue!='' && !reg.test(minValue)){
			layer.msg('最小值输入格式不正确', {icon: 0,time: 3000});
			 return null;
		}
	}
	var itemId=getSelectStrIds();
	if(itemId==''){
		layer.msg('请选择参数', {icon: 0,time: 3000});
		 return null;
	}else{
		$('#itemId').val(itemId);
	}
	var b = $("form").FormValidate();
	if(b){
		 $('form').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
	    	    parent.layer.close(index);
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
	}
}

$(function() {
	var url = '${basePath}init/item/gridData4StandItem.do?sampTypeIds='+$('#sampTypeId').val();
	var editurl='';
	var colNames = ['检测项目','计量单位','样品类型'];
	var colModel = [ 
	  {
		name : 'name',
		index : 'name',
		sortable : false,
		searchoptions : {
			sopt : ['cn']
		}
	}, {
		name : 'unit',
		index : 'unit',
		width : 100,
		fixed : true,
		sortable : false,
		searchoptions : {
			sopt : ['cn']
		}
	}, {
		name : 'sampTypeNames',
		index : 'sampTypeNames',
		width : 150,
		fixed : true,
		sortable : false,
		searchoptions : {
			sopt : ['cn']
		}
	}];
	gridInitAuto(url, colNames, colModel, '',-1,'',true);
	var win = getWinSize();
	$("#listTable").jqGrid('setGridHeight', win.WinH-95);
});
/*function gridComplete() {
	var ids = jQuery("#listTable").jqGrid('getDataIDs');
	for (var i = 0; i < ids.length; i++) {
		be = '<a class="btn btn-primary btn-xs" title="关联" href="javascript:;" onclick="ajaxSaveItem(\''+ids[i]+'\')">添加</a>';
		jQuery("#listTable").jqGrid('setRowData', ids[i], {
			act : be
		});
	}
	//initGriePageHiden();
}
 
function initGriePageHiden(){
	var standardId = '${vo.standId}';
	$.ajax({ 
		url:'ajaxgetContactItemIds.do',
		dataType:"json",
		data:{"standId":standardId},
		type:"post",
		async:false,
		success: function(data){
			var selectIds = data.selectItemIds;
			if(null !=selectIds && selectIds.length>0){
				setHiden(selectIds);
			}
		},
		error:function(ajaxobj){  
	    }  
	});
}*/
/**设置影藏行
*
function setHiden(ids){
	if(null!=ids){
		var ids = ids.replace("，",",");
		var s = ids.split(",");
		for ( var i = 0; i <= s.length; i++){
			 $('#listTable').setRowData(s[i],null,{display: 'none'});
		}
	}
}*/
function gridInitAuto(url, colNames, colModel, editurl,rowNum,pager,multiselect) {
	$.jgrid.defaults.styleUI = "Bootstrap";
	var mygrid = $("#listTable").jqGrid({
		url : url,
		datatype : "json",
		mtype : "POST",
		colNames : colNames,
		colModel : colModel,
		rownumbers:false,
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
		loadComplete : loadComplete
	});
	setFilterToolbar();//行内查询
	$("#listTable").setGridWidth(600);
	setJgridHeight();//高度
	jQuery("#listTable").jqGrid('setFrozenColumns'); //冻结表头
	$(".ui-search-oper").hide();
	fncleanName();
}
</script>
</html>