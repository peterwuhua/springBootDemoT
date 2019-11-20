<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>

<style type="text/css">
legend {
	border-bottom: 0px;
	width: 80px;
	margin-bottom: 0px;
	font-size: 14px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">数据复核</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" class="form-horizontal" id="thisForm">
					<input name="ids" value="${vo.ids}" type="hidden" />
					<c:forEach items="${itemList}" var="e"  varStatus="v">
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: -1px;">
							<tr>
								<td width="7%" class="active"><label class="pull-right">任务编号:</label></td>
								<td width="18%">
									${e.taskVo.no}
								</td>
								<td width="7%" class="active"><label class="pull-right">检测项目:</label></td>
								<td width="18%">
									${e.itemName}
								</td>
								<td width="7%" class="active"><label class="pull-right">计量单位:</label></td>
								<td width="18%">
									${e.unit}
								</td>
								<td width="7%" class="active"><label class="pull-right">检测方法:</label></td>
								<td width="18%">
									${e.methodName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">检测仪器:</label></td>
								<td>
									${e.appName}
								</td>
								<td class="active"><label class="pull-right">检&nbsp;出&nbsp;&nbsp;限:</label></td>
								<td>
									${e.limitLine}
								</td>
								<td class="active"><label class="pull-right">温&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度:</label></td>
								<td>
									${e.wd}
								</td>
								<td class="active"><label class="pull-right">湿&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度:</label></td>
								<td>
									${e.sd}
								</td>
							</tr>
							<c:if test="${fn:length(e.fileList)>0}">
								<tr>
									<td class="active"><label class="pull-right">附件信息:</label></td>
									<td colspan="7">
										<c:forEach items="${e.fileList}" var="e1" varStatus="v1">
										 	<div style="float: left;margin-right: 10px;">
											 	<a href="download.do?filePath=${e1.filePath}&trueName=${e1.fileName}" class="btn btn-w-m btn-info">${e1.fileName}</a>
										 	</div>
										 </c:forEach>
									</td>
								</tr>
							</c:if>
							<tr>
								<td colspan="4">
									<b>结果信息</b>
								</td>
							</tr>
						</table>
						<c:choose>
							<c:when test="${e.taskVo.sampType=='环境'}">
								<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 1px;">
									<thead>
										<tr>
											<th style="text-align: center;width: 50px;">序号</th>
											<th style="text-align: center;width: 80px;">样品名称</th>
											<th style="text-align: center;width: 80px;">采样日期</th>
											<th style="text-align: center;width: 80px;">样品编号</th>
											<c:choose>
												<c:when test="${e.st=='气'}">
													<th style="text-align: center;width: 150px;">浓度值</th>
													<th style="text-align: center;width: 150px;">速率值</th>
												</c:when>
												<c:otherwise>
													<th style="text-align: center;width: 150px;">检测值</th>
												</c:otherwise>
											</c:choose>
											<th style="text-align: center;width: 80px;">单项判定</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${e.itemList}" var="e1" varStatus="s1">
											<tr>
												<td align="center">
													${s1.index+1}
												</td>
												<td align="center">
													${e1.trVo.sampVo.sampTypeName}
												</td>
												<td align="center">
													${e1.cyDate}
												</td>
												<td>
													${e1.trVo.sampVo.sampCode}
												</td>
												<c:choose>
													<c:when test="${e.st=='气'}">
														<td>
						                                    ${e1.trVo.value}
														</td>
														<td>
						                                   ${e1.trVo.sl}
														</td>
													</c:when>
													<c:otherwise>
														<td>
						                                   ${e1.trVo.value}
														</td>
													</c:otherwise>
												</c:choose>
												<td >
													${e1.result}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:when>
							<c:otherwise>
								<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 1px;">
									<thead>
										<tr>
											<th style="text-align: center;width: 50px;">序号</th>
											<th style="text-align: center;width: 80px;">样品名称</th>
											<th style="text-align: center;width: 80px;">采样日期</th>
											<th style="text-align: center;width: 80px;">样品编号</th>
											<th style="text-align: center;width: 150px;">检测值</th>
											<c:choose>
												<c:when test="${e.limited=='a'}">
													<th style="text-align: center;width: 80px;">Cmac</th>
												</c:when>
												<c:when test="${e.limited=='b'}">
													<th style="text-align: center;width: 80px;">Ctwa</th>
													<th style="text-align: center;width: 80px;">Cstel</th>
												</c:when>
												<c:when test="${e.limited=='c'}">
													<th style="text-align: center;width: 80px;">Ctwa</th>
													<th style="text-align: center;width: 80px;">超限倍数</th>
												</c:when>
												<c:otherwise>
													<th style="text-align: center;width: 80px;">均值</th>
												</c:otherwise>
											</c:choose>
											<th style="text-align: center;width: 80px;">单项判定</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${e.itemList}" var="e1" varStatus="s1">
											<c:forEach  items="${e1.trList}" var="r2" varStatus="rs2">
												<tr>
													<c:if test="${rs2.index==0}">
														<td align="center" rowspan="${fn:length(e1.trList)}">
															${s1.index+1}
														</td>
														<td align="center" rowspan="${fn:length(e1.trList)}">
															${e1.pointVo.sampName}
														</td>
														<td align="center" rowspan="${fn:length(e1.trList)}">
															${e1.cyDate}
														</td>
													</c:if>
													<td>
														${r2.sampVo.sampCode}
													</td>
													<td>
					                                   ${r2.value}
													</td>
													<c:if test="${rs2.index==0}">
														<c:choose>
															<c:when test="${e.limited=='a'}">
																<td rowspan="${fn:length(e1.trList)}">
								                                   ${e1.mac}
																</td>
															</c:when>
															<c:when test="${e.limited=='b'}">
																<td rowspan="${fn:length(e1.trList)}">
								                                    ${e1.twa}
																</td>
																<td rowspan="${fn:length(e1.trList)}">
								                                   ${e1.stel}
																</td>
															</c:when>
															<c:when test="${e.limited=='c'}">
																<td rowspan="${fn:length(e1.trList)}">
								                                  ${e1.twa}
																</td>
																<td rowspan="${fn:length(e1.trList)}">
								                                   ${e1.lmt}
																</td>
															</c:when>
															<c:otherwise>
																<td rowspan="${fn:length(e1.trList)}">
								                                    ${e1.value}
																</td>
															</c:otherwise>
														</c:choose>
														<td rowspan="${fn:length(e1.trList)}">
															${e1.result}
														</td>
													</c:if>
												</tr>
											</c:forEach>
										</c:forEach>
									</tbody>
								</table>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">审&nbsp;&nbsp;核&nbsp;人:</label></td>
							<td class="width-35">
								<input id="checkManId" name="checkManId" type="hidden" value="${vo.checkManId}" /> 
								<input id="checkMan" name="checkMan" class="form-control" maxlength=32  type="text" value="${vo.checkMan}" readonly="readonly" />
							</td>
							<td class="width-15 active"><label class="pull-right">审核时间:</label></td>
							<td class="width-35">
								<div class="input-group date">
	                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                              	<input id="checkTime" name="checkTime" class="form-control dateISO required" validate="required" maxlength=20 placeholder="复核时间" type="text" value="${vo.checkTime}" />
	                            </div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea id="remark" name="checkMsg" maxlength="128" class="form-control"></textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('updateData.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 提交</a>
							<a class="btn btn-w-m btn-danger" type="button" onclick="fnSubmitBack('updateData.do?isCommit=-1')"><i class="fa fa-check" aria-hidden="true"></i> 退回</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<!-- Sweet alert -->
	<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<!--PageOffice.js -->
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
</body>

<script type="text/javascript">
function fnOpenTemp(id){
	POBrowser.openWindow('${basePath}bus/itemTest/showTemp.do?id='+id,'width=1200px;height=800px;');
}
function fnSubmit(url){
	$('#thisForm').attr('action',url);
	var b = $("#thisForm").FormValidate();
	if(b){
		swal({
	        title: "您确定要提交该任务吗",
	        text: "提交后将无法修改，请谨慎操作！",
	        type: "success",
	        showCancelButton: true,
	        confirmButtonColor: "#1ab394",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消"
	    }, function () {
			 $('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
			        backMainPage();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
			 swal("提交成功！", "", "success");
   		 });
	}
}
function fnSubmitBack(url){
	var remark=$('#remark').val();
	if(remark==''){
		swal("请在备注栏填写处理结果！", "", "warning");
		return ;
	}
	swal({
        title: "您确定要退回该任务吗",
        text: "将会退回重新录入，请谨慎操作！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "退回",
        cancelButtonText: "取消"
    }, function () {
    	$('#thisForm').attr('action',url);
		$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
		        backMainPage();
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
        swal("退回成功！", "", "success");
    });
}
function copySelect(obj){
	obj=$(obj);
	var idValue=obj.closest('td').find('select').val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#jcTb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('select').val(idValue);
	});
}
</script>
</html>
