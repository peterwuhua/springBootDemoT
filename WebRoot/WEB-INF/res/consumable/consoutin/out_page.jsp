<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>

</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>耗材管理</a></li>
						<li><strong>出库信息列表</strong></li>
					</ol>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<div>
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
					<a class="btn btn-primary" href="javascript:;" onclick="fnEdit()"><i class="fa fa-plus" aria-hidden="true"></i> 新增</a> 
					<div class="btn-group">
                          <button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">导出 <span class="caret"></span></button>
                          <ul class="dropdown-menu">
                               <li><a href="javascript:;" onclick="jqgridExportOther('/res/consoutin/exportOutData.do','res-consout-export.xls','耗材出库信息.xls',0)">全部数据</a></li>
                              </li>
                              <li><a href="javascript:;" onclick="jqgridExportOther('/res/consoutin/exportOutData.do','res-consout-export.xls','耗材出库信息.xls',1)">选中数据</a></li>
                              </li>
                          </ul>
                     </div>
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
	<script>
	function fnEdit(){
		openEditPage('outDepot.do');
	}
	$(function() {
		var url = 'outData.do';
		var colNames = ['编号 ','名称 ','类型','单位','型号','领用人','领取时间','领取数量','备注'];
		var colModel = [
	  			 {
	  				name : 'consumableVo.no',
	  				index : 'consumable.no',
	  				width : 80,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			}, {
	  				name : 'consumableVo.name',
	  				index : 'consumable.name',
	  				sortable : false,
	  				width : 80,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			}, {
	  				name : 'consumableVo.type',
	  				index : 'consumable.type',
	  				width : 80,
	  				sortable : false,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			}, {
	  				name : 'consumableVo.unit',
	  				index : 'consumable.unit',
	  				width : 100,
	  				sortable : false,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			},{
	  				name : 'consumableVo.exp',
	  				index : 'consumable.exp',
	  				sortable : false,
	  				width : 100,
	  				searchoptions : {
	  					sopt : [ 'cn']
	  				}
	  			}, {
					name : 'leadingPerson',
					index : 'leadingPerson',
					sortable : false,
					width : 100,
					searchoptions : {
						sopt : [ 'cn']
					}
				}, {
					name : 'leadingDate',
					index : 'leadingDate',
					sortable : false,
					width : 150,
					searchoptions : {
						sopt : [ 'cn']
					}
				}, {
					name : 'leadingNum',
					index : 'leadingNum',
					sortable : false,
					width : 100,
					searchoptions : {
						sopt : [ 'cn']
					}
				}, {
					name : 'remark',
					index : 'remark',
					sortable : false,
					searchoptions : {
						sopt : [ 'cn' ]
					}
				}];
			gridInitMin(url, colNames, colModel, true);
		});

	</script> 
	
</body>
</html>
