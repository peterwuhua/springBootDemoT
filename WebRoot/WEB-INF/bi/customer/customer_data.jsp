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
			<h5>客户源数据</h5>
			<div class="ibox-tools">
				<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
				</a> <a class="close-link"> <i class="fa fa-times"></i>
				</a>
			</div>
		</div>
		<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<input type="hidden" name="saler" id="saler" value="${vo.saler}">
				<input type="hidden" name="souce" id="souce" value="${vo.souce}">
				<input type="hidden" name="industry" id="industry" value="${vo.industry}">
				<input type="hidden" name="customerType" id="customerType" value="${vo.customerType}">
				<input type="hidden" name="buildDate" id="buildDate" value="${vo.buildDate}">
				<input type="hidden" name="grade" id="grade" value="${vo.grade}">
				<input type="hidden" name="areaPath" id="areaPath" value="${vo.areaPath}">
				<div class="col-sm-7">
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<div class="btn-group">
                          <button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">导出 <span class="caret"></span></button>
                          <ul class="dropdown-menu">
                              <li><a href="javascript:;" onclick="jqgridExport('bi-customer-export.xls','客户源模板.xls',0)" >全部数据</a>
                              </li>
                              <li><a href="javascript:;" onclick="jqgridExport('bi-customer-export.xls','客户源模板.xls',1)" >选中数据</a>
                              </li>
                          </ul>
                      </div>
				</div>
				</form>
				<div class="jqGrid_wrapper">
                      <table id="listTable"></table>
                      <div id="pager"></div>
               </div>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
<script>
	$(function() {
		var saler = $('#saler').val(),
			souce=$('#souce').val(),
			industry=$('#industry').val(),
			customerType=$('#customerType').val(),
			buildDate=$('#buildDate').val(),
			grade=$('#grade').val(),
			areaPath=$('#areaPath').val();
		
		var url = 'gridData.do?	saler='+saler+'&souce='+souce+'&industry='+industry+'&customerType='+customerType
					+'&buildDate='+buildDate+'&areaPath='+areaPath+'&grade='+grade;
		var editurl='gridEdit.do';
		var colNames = ['编号','名称','级别','客户经理','客户来源','客户行业','类型','客户建立时间','区域','数据来源','排序'];
		var colModel = [ 
			 {
				name : 'code',
				index : 'code',
				width : 100,
				editable : true,
				frozen : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'name',
				index : 'name',
				editable : true,
				width : 100,
				frozen : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'grade',
				index : 'grade',
				editable : true,
				width : 100,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'saler',
				index : 'saler',
				editable : true,
				width : 100,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'souce',
				index : 'souce', 
				editable : true,
				width : 100,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'industry',
				index : 'industry',
				editable : true,
				width : 100,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'customerType',
				index : 'customerType',
				editable : true,
				width : 100,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'buildDate',
				index : 'buildDate',
				editable : true,
				width : 100,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'areaPath',
				index : 'areaPath',
				editable : true,
				width : 150,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'dataSouce',
				index : 'dataSouce',
				editable : true,
				width : 150,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'sort',
				index : 'sort',
				width : 80,
				editable : true,
				searchoptions : {
					sopt : [ 'cn','ne','eq','le','lt','gt','ge']
				},
			}];

			pageInit(url, colNames, colModel, editurl);
		
		});

		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					
				});
			}
		}
	</script> 
	
</body>
</html>