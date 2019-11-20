<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<link href="${basePath}h/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<style type="text/css">
.form-group{
	margin-bottom: 5px;
}
.form-group > label{
	height: 34px;
	padding-top: 10px;
	padding-left: 0px;
	padding-right: 0px;
}
.form-group > div{
	padding-left: 0px;
	padding-right: 0px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm" class="form-horizontal m-t">
					<div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >项目编号：</label>
                          <div class="col-md-8" >
                              <input id="no" name="no" value="${vo.no}" type="text" class="form-control" >
                          </div>
                    </div>
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >委托单位：</label>
                          <div class="col-md-8" >
                              <input id="custName" type="text" name="custVo.custName" value="${vo.custVo.custName}" class="form-control" >
                          </div>
                    </div>
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >项目类型：</label>
                          <div class="col-md-8" >
                          	 <select id="itemType" name="itemType" class="form-control">
                          	 	<option value="">全部</option>
                          	 	<c:forEach items="${busList}" var="e" varStatus="v">
                          	 		<c:choose>
	                          	 		<c:when test="${vo.itemType==e}">	
	                          	 			<option value="${e}" selected="selected">${e}</option>
	                          	 		</c:when>
	                          	 		<c:otherwise>
	                          	 			<option value="${e}">${e}</option>
	                          	 		</c:otherwise>
	                          	 	</c:choose>
                          	 	</c:forEach>
                          	 </select>
                          </div>
                    </div> 
                    <div class="form-group col-md-4" id="data_5">
                         <label class="col-md-4 control-label" style="width: 79px;">立项日期：</label>
                         <div class="input-daterange input-group col-md-10" style="width:300px;">
                              <input id="startDate" type="text" class="form-control dateISO" name="startDate" placeholder="开始日期"/>
                              <span class="input-group-addon" style="font-size:bold;">到</span>
                              <input id="endDate" type="text" class="form-control dateISO" name="endDate" placeholder="截止日期"/>
                         </div>
					</div>
					<div class="form-group col-md-4">
                          <label class="col-md-3 control-label" >项目负责人：</label>
                          <div class="col-md-6" style="width:190px;">
                              <input id="userName" type="text" name="userName" value="${vo.userName}" class="form-control" placeholder="项目负责人" >
                          </div>
                    </div>
					<div class="form-group col-md-3">
						<a class="btn btn-primary" href="javascript:void(0);" onclick="fnSearch();"><i class="fa fa-search" aria-hidden="true"></i> 查询</a>
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
	<!-- Data picker -->
    <script src="${basePath}h/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script>
	$(function() {
		$('#data_5 .input-daterange').datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true
        });
		var url = 'gridData.do';
		var colNames = ['项目编号','项目名称','项目类型','委托单位','合同金额','立项日期','项目负责人','当前进度','操作'];
		var colModel = [ 
			{
				name : 'no',
				index : 'no',
				width : 120,
				fixed:true,
				search : false,
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
				width : 100,
				fixed:true,
				sortable : false,
				search : false,
			}, {
				name : 'custVo.custName',
				index : 'cust.custName',
				sortable : false,
				search : false
			},{
				name : 'totalPrice',
				index : 'totalPrice',
				width : 100,
				fixed:true,
				sortable : false,
				search : false
			},{
				name : 'rdate',
				index : 'rdate',
				width : 120,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'userName',
				index : 'userName',
				width : 120,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'status',
				index : 'status',
				formatter:formatStatus,
				width : 100,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'act',
				index : 'act',
				width : 100,
				fixed:true,
				search : false,
				sortable : false
			}];
		gridInitAuto(url, colNames, colModel, 20,'#pager');
	});
	function gridInitAuto(url, colNames, colModel,rowNum,pager) {
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
	        search:false,
			gridComplete : gridComplete,
			loadComplete : loadComplete
		});
		setJgridWidth();//宽度
		setJgridHeight();//高度
		fncleanName();
	}
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-230);
	}
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="日志" href="javascript:fnSelectProcess(\''+rData.id+'\',\''+rData.status+'\')">日志</a>';
			ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShowSch(\''+rData.id+'\',\''+rData.no+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act :  be+' '+ce
			});
			if(rData.status=='终止'){
				jQuery('#'+ids[i]).removeClass("jqgrow");
				jQuery('#'+ids[i]).addClass("rowBG");
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
		}
		
		return ops;
	}
	
	function fnShowSch(id,no){
		parent.layer.open({
			title:'项目详情【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/sim/project/show.do?id='+id
		});
	}
	function fnSearch(){
		var postData = $("#listTable").jqGrid("getGridParam", "postData");
		$.extend(postData, {'no':$('#no').val()
			,'custVo.custName':$('#custName').val(),'itemType':$('#itemType').val()
			,'startDate':$('#startDate').val(),'endDate':$('#endDate').val(),'userName':$('#userName').val()});
		jQuery("#listTable").jqGrid('setGridParam',{url:'gridData.do'}).trigger("reloadGrid")
	}
	</script>
</body>
</html>