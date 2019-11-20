<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
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
.ui-pg-selbox{
	width: 43px !important;
}
.showTd:hover{
	background-color:#FBFBFB;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form action="list4Uw.do" method="post" id="thisForm">
                     <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >检测部门：</label>
                          <div class="col-md-8" >
                              <input id="deptName" type="text" name="deptName" value="${vo.deptName}" class="form-control" placeholder="检测部门">
                          </div>
                     </div>
                     <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >检测人员：</label>
                          <div class="col-md-8" >
                              <input id="testMan" type="text" name="testMan" value="${vo.testMan}" class="form-control" placeholder="检测人员">
                          </div>
                     </div>
                     <div class="form-group col-md-6" id="data_5">
                          <label class="col-md-2 control-label" >检测日期：</label>
                          <div class="input-daterange input-group col-md-10" style="width:300px;">
                              <input id="startDate" type="text" class="form-control dateISO" name="startDate" placeholder="开始日期"/>
                              <span class="input-group-addon">到</span>
                              <input id="endDate" type="text" class="form-control dateISO" name="endDate" placeholder="截止日期"/>
                          </div>
					</div>
					 <div class="form-group col-md-1">
						   <a class="btn btn-primary" href="javascript:void(0);" onclick="fnSearch();"><i class="fa fa-search" aria-hidden="true"></i> 查询</a>
					</div>
					<div>
						<table id="listTable" class="table table-bordered">
							<thead>
								<tr>
									<th style="text-align: center;" width="50">序号</th>
									<th style="text-align: center;">检测人</th>
									<th  style="text-align: center;">在检项目数</th>
									<th  style="text-align: center;">已完成项目总数</th>
									<th  style="text-align: center;">已完成样品总数</th>
								</tr>
								<c:set var="c1" value="0" />
								<c:set var="c2" value="0" />
								<c:set var="c3" value="0" />
							</thead>
							<tbody>
								<c:forEach items="${itlist}" var="e" varStatus="v">
									<tr>
										<td  style="text-align: center;">${v.index+1}</td>
										<td  style="text-align: center;">${e.objName}</td>
										<td class="showTd" style="text-align: center;" onclick="showDetail();">
											${e.num}
											<c:set var="c1" value="${c1+e.num}" />
										</td>
										<td class="showTd"  style="text-align: center;" onclick="showDetail();">
											${e.comp}
											<c:set var="c2" value="${c2+e.comp}" />
										</td>
										<td class="showTd"  style="text-align: center;" onclick="showDetail();">
											${e.ypNum}
											<c:set var="c3" value="${c3+e.ypNum}" />
										</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td  style="text-align: center;" colspan="2">总计</td>
									<td  style="text-align: center;">${c1}</td>
									<td  style="text-align: center;">${c2}</td>
									<td  style="text-align: center;">${c3}</td>
								</tr>
							</tfoot>
						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script>
	function fnSearch(){
		$('#thisForm').submit()
	}
	function showDetail(){
		alert(1)
	}
	</script>
</body>
</html>