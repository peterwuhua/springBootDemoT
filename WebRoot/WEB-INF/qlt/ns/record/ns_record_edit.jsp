<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
#ct_tb >tr >td{
	padding: 2px;
}
#ct_tb .btn{
	padding-left: 2px;
	padding-right: 2px;
}
.mtd{
	padding-left: 6px !important;
	padding-right: 2px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>内审记录</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm" >
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<thead>
							<tr>
								<td colspan="4" style="background-color:#fff;border: 0px;">计划信息</td>
							</tr>
						</thead>
						<tr>
							<td class="width-15 active"><label class="pull-right">计划名称:</label></td>
							<td class="width-35">
								${vo.nsVo.title} 
							</td>
							<td class="width-15 active"><label class="pull-right">审核年度:</label></td>
							<td class="width-35">
								${vo.year}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
							<td class="width-35">
								${vo.nsVo.deptName}
							</td>
							<td class="width-15 active"><label class="pull-right">审核目的:</label></td>
							<td class="width-35">
								${vo.nsVo.purpose}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">审核范围:</label></td>
							<td>
								${vo.nsVo.ranges}
							</td>
							<td class="active"><label class="pull-right">审核依据:</label></td>
							<td>
								${vo.nsVo.stand}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">审核方式:</label></td>
							<td>
								${vo.nsVo.auditType}
							</td>
							<td class="active"><label class="pull-right">内审地点:</label></td>
							<td>
								${vo.nsVo.address}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">内审组长:</label></td>
							<td>
								${vo.nsVo.headName}
							</td>
							<td class="active"><label class="pull-right">审核人员:</label></td>
							<td>
								${vo.nsVo.nsName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.nsVo.remark}
							</td>
						</tr>
					</table>
					<table class="table table-bordered">
						<tr>
							<td colspan="4" style="background-color:#fff;">记录信息</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">审核月份:</label></td>
							<td class="width-35">
								${vo.month}月
							</td>
							<td class="width-15 active"><label class="pull-right ">审核要素:</label></td>
							<td>
								${vo.item}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">记&nbsp;录&nbsp;&nbsp;人:</label></td>
							<td>
								<input type="hidden" id="userId" name="userId" value="${vo.userId}">
								<input type="text" id="userName" name="userName" class="form-control" value="${vo.userName}" readonly="readonly">
							</td>
							<td class="active"><label class="pull-right ">记录日期:</label></td>
							<td>
								<div class="input-group date">
	                              	<input id="date" name="date" class="form-control required dateISO" validate="required" type="text" value="${vo.date}" />
	                              	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                            </div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">情况概述:</label></td>
							<td>
								<input type="text" id="content" name="content" class="form-control" value="${vo.content}" >
							</td>
							<td class="active"><label class="pull-right ">内审结论:</label></td>
							<td>
								 <select class="form-control" id="result" name="result">
								 	<c:choose>
								 		<c:when test="${vo.result=='不合格'}">
								 			<option value="合格">合格</option>
								 			<option value="不合格" selected="selected">不合格</option>
								 		</c:when>
								 		<c:otherwise>
								 			<option value="合格" selected="selected">合格</option>
								 			<option value="不合格">不合格</option>
								 		</c:otherwise>
								 	</c:choose>
								 </select>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">跟&nbsp;踪&nbsp;&nbsp;人:</label></td>
							<td>
								<div class="input-group">
									<input id="gzName" name="gzName" value="${vo.gzName}" class="form-control" type="text"  onclick="fnSelectGz()"/>
									<input id="gzId" name="gzId" value="${vo.gzId}" type="hidden"/>
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectGz()">选择</button>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea maxlength="128" rows="2" class="form-control" id="remark" name="remark">${vo.remark}</textarea>
							</td>
						</tr>
					</table>
					<table class="table table-bordered">
						 <thead>
							<tr>
								<td colspan="16" style="background-color:#fff;border: 0px;">要素信息
								</td>
							</tr>
							<tr>
								<th style="width: 50px;text-align: center;">条款</th>
								<th>检查内容</th>
								<th>检查重点</th>
								<th style="width: 30px;">符合</th>
								<th style="width: 30px;">基本符合</th>
								<th style="width: 30px;">不符合</th>
								<th style="width: 150px;text-align: center;">存在问题</th>
							</tr>
						</thead>
						<tbody id="ct_tb">
							<c:forEach items="${vo.detailList}" var="e" varStatus="v">
								<tr>
									<td colspan="8">
										<c:if test="${e.code!=''}">${e.code}、</c:if>${e.name}
										<input type="hidden" name="detailList[${v.index}].id" value="${e.id}">
									</td>
								</tr>
								<c:forEach items="${e.subList}" var="e1" varStatus="v1">
									<c:choose>
										<c:when test="${e1.subList!=null && fn:length(e1.subList)>0}">
											<c:forEach items="${e1.subList}" var="e2" varStatus="v2">
												<tr>
													<c:if test="${v2.index==0}">
														<td rowspan="${e1.count}" align="center">
															${e1.code}
														</td>
														<td rowspan="${e1.count}">
															${e1.name}
															<input type="hidden" name="detailList[${v.index}].subList[${v1.index}].id" value="${e1.id}">
														</td>
													</c:if>
													<td>
														${e2.name}
														<input type="hidden" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].id" value="${e2.id}">
													</td>
													<td class="mtd">
														<div class="checkbox i-checks">
															<div class="iradio_square-green">
																<c:choose>
																	<c:when test="${e2.result=='符合'}">
																		<input type="radio" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].result" value="符合" checked="checked">
																	</c:when>
																	<c:otherwise>
																		<input type="radio" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].result" value="符合">
																	</c:otherwise>
																</c:choose>
															</div>
														</div>
													</td>
													<td class="mtd">
														<div class="checkbox i-checks">
															<div class="iradio_square-green">
																<c:choose>
																	<c:when test="${e2.result=='基本符合'}">
																		<input type="radio" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].result" value="基本符合" checked="checked">
																	</c:when>
																	<c:otherwise>
																		<input type="radio" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].result" value="基本符合">
																	</c:otherwise>
																</c:choose>
															</div>
														</div>
													</td>
													<td class="mtd">
														<div class="checkbox i-checks">
															<div class="iradio_square-green">
																<c:choose>
																	<c:when test="${e2.result=='不符合'}">
																		<input type="radio" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].result" value="不符合" checked="checked">
																	</c:when>
																	<c:otherwise>
																		<input type="radio" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].result" value="不符合">
																	</c:otherwise>
																</c:choose>
															</div>
														</div>
													</td>
													<td>
														<input type="text" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].question" value="${e2.question}"  class="form-control">
													</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td align="center">
													${e1.code}
												</td>
												<td>
													${e1.name}
													<input type="hidden" name="detailList[${v.index}].subList[${v1.index}].id" value="${e1.id}">
												</td>
												<td>
													/
												</td>
												<td class="mtd">
													<div class="checkbox i-checks">
														<div class="iradio_square-green">
															<c:choose>
																<c:when test="${e1.result=='符合'}">
																	<input type="radio" name="detailList[${v.index}].subList[${v1.index}].result" value="符合" checked="checked">
																</c:when>
																<c:otherwise>
																	<input type="radio" name="detailList[${v.index}].subList[${v1.index}].result" value="符合">
																</c:otherwise>
															</c:choose>
														</div>
													</div>
												</td>
												<td class="mtd">
													<div class="checkbox i-checks">
														<div class="iradio_square-green">
															<c:choose>
																<c:when test="${e1.result=='基本符合'}">
																	<input type="radio" name="detailList[${v.index}].subList[${v1.index}].result" value="基本符合" checked="checked">
																</c:when>
																<c:otherwise>
																	<input type="radio" name="detailList[${v.index}].subList[${v1.index}].result" value="基本符合">
																</c:otherwise>
															</c:choose>
														</div>
													</div>
												</td>
												<td class="mtd">
													<div class="checkbox i-checks">
														<div class="iradio_square-green">
															<c:choose>
																<c:when test="${e1.result=='不符合'}">
																	<input type="radio" name="detailList[${v.index}].subList[${v1.index}].result" value="不符合" checked="checked">
																</c:when>
																<c:otherwise>
																	<input type="radio" name="detailList[${v.index}].subList[${v1.index}].result" value="不符合">
																</c:otherwise>
															</c:choose>
														</div>
													</div>
												</td>
												<td>
													<input type="text" name="detailList[${v.index}].subList[${v1.index}].question" value="${e1.question}"  class="form-control">
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a id="sub_btn" class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmit4Save('updateData.do?isCommit=0');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a id="sub_btn" class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('updateData.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
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
<script>
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
	});
	function fnSelectGz(){
		var gzId=$('#gzId').val();
		layer.open({
			title:'人员选择',	
			type: 2,
			area: ['800px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/selects.do?ids='+gzId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#gzId').val(data.id);
			  $('#gzName').val(data.name);
			}
		});
	}
	$('#ct_tb').find('input[name$=".result"]').on('ifChecked', function(event){ 
		//ifCreated 事件应该在插件初始化之前绑定 
		checkResult();
	});
	function checkResult(){
		var flag=true;
		$('#ct_tb').find('input[name$=".result"]:checked').each(function(){
			if($(this).val()=='不符合'){
				flag=false;
				return ;
			}
		});
		if(flag){
			$('#result').val('合格');
		}else{
			$('#result').val('不合格');
		}
	}
	function formSubmit4Save(url){
		$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
			 $('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
			        backMainPage();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	}
	function formSubmit(url){
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
	    	})
		}
	}
	</script>
</body>
</html>
