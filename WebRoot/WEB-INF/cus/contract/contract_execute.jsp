<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
		  <div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><a href="${basePath}cus/contract/gridPage.do">合同</a></li>
						<li><strong>合同详情</strong></li>
					</ol>
				</h5>
				<div class="ibox-tools">
					<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
				</div>
			</div>
			<div class="ibox-content">
			  <div class="panel-heading">
               <div class="panel-options">
					<ul class="nav nav-tabs">
					    <li>
					        <a href="javascript:;" onclick="location.href='${basePath}cus/contract/edit.do?id=${vo.contractVo.id}'" data-toggle="tab">基本信息 </a>
					    </li>
					    <li><a href="javascript:;" onclick="location.href='${basePath}cus/contract/detail.do?contractVo.id=${vo.id}'"data-toggle="tab">合同详情</a></li>
					    <li class="active"><a href="javascript:;" data-toggle="tab">合同执行情况</a></li>  
					</ul>
			    </div>
			    </div>
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
				 <input type="hidden" value="${vo.customerVo.id}" name="customerVo.id" />
				 <input type="hidden" name="ids" id="ids">
				<div class="col-sm-7">
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" href="import.do?customerVo.id=${vo.customerVo.id}">导入</a>
					<div class="btn-group">
                       <button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">导出 <span class="caret"></span></button>
                       <ul class="dropdown-menu">
                           <li><a href="javascript:;" onclick="jqgridExport('cus-contract-export.xls','合同信息列表.xls',0)" >全部数据</a>
                           </li>
                           <li><a href="javascript:;" onclick="jqgridExport('cus-contract-export.xls','合同信息列表.xls',1)" >选中数据</a>
                           </li>
                       </ul>
                     </div>
					<a href="${basePath}cus/customer/gridPage.do" class="btn btn-white" type="submit">返回</a>
				</div>
				<div class="col-sm-5"></div>
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
		var url = '/cus/contract/gridData4Tab.do?customerVo.id=${vo.customerVo.id}';
		var editurl='/cus/contract/gridEdit.do?customerVo.id=${vo.customerVo.id}';
		var colNames = ['合同编号 ','合同名称 ','开始日期','结束日期','样品数量','优惠折扣','合同金额','付款类型','付款方式'];
		var colModel = [
			 {
				name : 'code',
				index : 'code',
				width : 150,
				frozen : true,
				resizable : false,
				editable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'name',
				index : 'name',
				frozen : true,
				editable : true,
				resizable : false,
				width : 200,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'sdate',
				index : 'sdate',
				editable : false,
				resizable : true,
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'edime',
				index : 'edime',
				editable : false,
				resizable : true,
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'num',
				index : 'num',
				editable : true,
				resizable : true,
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'discount',
				index : 'discount',
				editable : true,
				resizable : true,
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'contractSum',
				index : 'contractSum',
				editable : true,
				resizable : true,
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'payType',
				index : 'payType',
				editable : true,
				resizable : true,
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'payWay',
				index : 'payWay',
				editable : true,
				resizable : true,
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];

			pageInit(url, colNames, colModel, editurl);
		});

		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				be = '';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
		
		function setNavGrid() {
			jQuery("#listTable").jqGrid('navGrid','#pager',{add:true,edit:true,del:false,search:false,refresh:true});
		}
	</script> 
</body>
</html>
