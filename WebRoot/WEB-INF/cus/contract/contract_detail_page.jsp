<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
.panel-heading{
	padding: 0px;
}
.col-sm-6{
	padding: 0px 0px 1px 0px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
		  <div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="${basePath}cus/contract/gridPage.do">合同</a></li>
					<li><strong>合同详情</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
			  <div class="panel-heading">
	               <div class="panel-options">
	               		<div class="col-sm-6">
							<ul class="nav nav-tabs">
							    <li>
							        <a href="javascript:;" onclick="location.href='${basePath}cus/contract/edit.do?id=${vo.contractVo.id}&flag=${vo.contractVo.flag}'" data-toggle="tab">基本信息 </a>
							    </li>
							    <li class="active"><a href="javascript:;" data-toggle="tab">样品&参数</a></li>  
							   <li><a href="javascript:;" onclick="location.href='${basePath}cus/contract/execute/gridPage.do?contractVo.id=${vo.contractVo.id}&contractVo.flag=${vo.contractVo.flag}'"data-toggle="tab">执行情况</a></li>
							</ul>
						</div>
						<div class="col-sm-6" align="right">
							<a class="btn btn-primary" href="javascript:fnEidt(${vo.id});">新增</a> 
							<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
							<a href="javascript:backMainPage();" class="btn btn-white" type="submit"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
		                     <!--<c:choose>
		                     	<c:when test="${vo.contractVo.flag=='1'}"><a href="${basePath}cus/contract/gridPage.do" class="btn btn-white" type="submit"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a></c:when>
		                     	<c:when test="${vo.contractVo.flag=='2'}"><a href="${basePath}cus/contract/gridPage4Execut.do" class="btn btn-white" type="submit"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a></c:when>
		                     	<c:when test="${vo.contractVo.flag=='3'}"><a href="${basePath}cus/contract/gridPage4Com.do" class="btn btn-white" type="submit"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a></c:when>
		                     	<c:when test="${vo.contractVo.flag=='4'}"><a href="${basePath}cus/contract/gridPage4Fast.do" class="btn btn-white" type="submit"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a></c:when>
		                     	<c:when test="${vo.contractVo.flag=='5'}"><a href="${basePath}cus/contract/gridPage4Expired.do" class="btn btn-white" type="submit"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a></c:when>
		                     	<c:otherwise><a href="${basePath}cus/contract/gridPage.do" class="btn btn-white" type="submit"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a></c:otherwise>
		                     </c:choose>-->
						</div>
				    </div>
			    </div>
				<form action="gridPage.do" method="post" id="listForm">
					 <input type="hidden" value="${vo.contractVo.id}" id="contractId" name="contractVo.id" />
					 <input type="hidden" name="ids" id="ids">
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
	<script>
	$(function() {
		var url = '${basePath}cus/contract/detail/gridData.do?contractVo.id=${vo.contractVo.id}';
		var colNames = ['合同编号','合同名称','样品名称 ','样品类型 ','测试参数','检测标准','样品批次','样品单价','操作'];
		var colModel = [
			{
				name : 'contractVo.code',
				index : 'contract.code',
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'contractVo.name',
				index : 'contract.name',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				width : 120,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'sampType',
				index : 'sampType',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'item',
				index : 'item',
				sortable : false,
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'standard',
				index : 'standard',
				sortable : false,
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'batch',
				index : 'batch',
				sortable : false,
				width : 70,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'price',
				index : 'price',
				sortable : false,
				width : 80,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 70,
				fixed:true,
				title : false,
				search : false,
				frozen : true,
				resizable : false,
				sortable : false,
			}];

			gridInitMin(url, colNames, colModel,true);
		});

		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:fnEidt(\''+ids[i]+'\');">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
	</script>
	<script type="text/javascript">
	function fnEidt(parm){
		var id = parm;
		if(id == null || id == undefined || id == ''){
			id = "";
		}
		layer.open({
			title:'测试参数',	
			type: 2,
			area: ['800px', '400px'],
			fix: false, //不固定
			maxmin: true,
			content: 'edit.do?id='+id+'&contractVo.id=${vo.contractVo.id}',
			btn: ['确定','取消'], //按钮
			yes: function(index, layero) {
				 var iframeWin = window[layero.find('iframe')[0]['name']];
				 var frameName = '_blank';
				 if(top!=this){
					 frameName= self.frameElement.name;
				 }
				 iframeWin.saveTable(frameName);
			}
		});
	}
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-250);
	}
	</script> 
</body>
</html>
