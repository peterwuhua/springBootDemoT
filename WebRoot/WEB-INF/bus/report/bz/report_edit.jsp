<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>

<style type="text/css">
legend{
	border-bottom:0px;
	width: 80px;
	margin-bottom:0px;
	font-size: 14px !important;
}
.form-control{
	padding: 2px 4px;
}
.input-group-addon{
padding: 2px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">报告编制</a></li>
					<li><strong>编辑</strong>
						<c:if test="${vo.isBack=='Y'}">
							（<span style="color: red"> 退回原因：${logVo.msg}</span> ）
						</c:if>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">报告编号:</label></td>
							<td class="width-35">
								<input id="reportNo" name="reportNo" class="form-control required" validate="required" type="text" value="${vo.reportNo}" />
							</td>
							<td class="width-15 active"><label class="pull-right">任务编号:</label></td>
							<td class="width-35">
								<a href="javascript:void(0);" onclick="fnTask('${vo.taskVo.id}','${vo.taskVo.no}');">${vo.taskVo.no}</a>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">客户名称:</label></td>
							<td>
								${vo.custVo.custName}
							</td>
							 <td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">样品名称:</label></td>
							<td>
								${vo.sampName}
							</td>
							<td class="active"><label class="pull-right ">检测日期:</label></td>
							<td>
								<div class="input-group date">
	                              	<input name="testDate" class="form-control required dateISO" validate="required" type="text" value="${vo.testDate}" />
	                            	<span class="input-group-addon">至</span>
	                            	<input name="testEndDate" class="form-control required dateISO" validate="required" type="text" value="${vo.testEndDate}" />
	                            </div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">报告日期:</label></td>
							<td>
								<div class="input-group date">
	                              	<input id="reportDate" name="reportDate" class="form-control required dateISO" validate="required" placeholder="报告日期" type="text" value="${vo.reportDate}" />
	                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                            </div>
							</td>
							<td class="active"><label class="pull-right">要求完成日期:</label></td>
							<td>
								${vo.finishDate}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">&nbsp;&nbsp;检测计划<br>和程序说明:</label></td>
							<td colspan="3">
								<textarea rows="4" class="form-control" id="jhsm" name="jhsm">${vo.jhsm}</textarea>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">检测内容:</label></td>
							<td colspan="3">
								<textarea rows="4" class="form-control" id="jcct" name="jcct">${vo.jcct}</textarea>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测结论:</label></td>
							<td colspan="3">
								<textarea rows="8" id="result" name="result" class="form-control">${vo.result}</textarea>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">解释和说明:</label></td>
							<td colspan="3">
								<textarea id="jssm" name="jssm" class="form-control">${vo.jssm}</textarea>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测环境条件:</label></td>
							<td colspan="3">
								<textarea id="jchj" name="jchj" class="form-control">${vo.jchj}</textarea>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea class="form-control" rows="2" id="remark" maxlength="2000" name="remark" placeholder="特殊情况说明">${vo.remark}</textarea>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">编&nbsp;制&nbsp;&nbsp;人:</label></td>
							<td>
								<input type="text" id="makeUser" name="makeUser" class="form-control" placeholder="编制人员" value="${vo.makeUser}"  readonly="readonly">
								<input type="hidden" name="makeUserId"  value="${vo.makeUserId}">
							</td>
							<td class="width-15 active"><label class="pull-right">编制日期:</label></td>
							<td>
								<div class="input-group date">
	                              	<input id="makeDate" name="makeDate" class="form-control required datetimeISO" validate="required" placeholder="编制日期" type="text" value="${vo.makeDate}" />
	                            	 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                            </div>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">报告信息:</label></td>
							<td colspan="3">
								<div class="btn-group" id="repeatReport">
			                       <button class="btn btn-danger" type="button" onclick="createReport();">在线生成报告 </button>
			                   </div>
		                  	 	<div class="btn-group" id="editReprt">
			                       <button class="btn btn-info" type="button" onclick="fnOpenReport();">在线编辑报告</button>
			                    </div>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:</label></td>
							<td colspan="3">
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" >${e.fileName}</a>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">审核人员:</label></td>
							<td>
								<select class="form-control required" validate="required" id="reptUserId" name="reptUserId">
									<option value="">-请选择-</option>
									<c:forEach items="${userList}" var="e" varStatus="v">
									 	 <c:choose>
									 	 	<c:when test="${vo.reptUserId==e.id}">
									 	 		<option value="${e.id}" selected="selected">${e.userVo.name}</option>
									 	 	</c:when>
									 	 	<c:otherwise>
									 	 		<option value="${e.id}">${e.userVo.name}</option>
									 	 	</c:otherwise>
									 	 </c:choose>
									 </c:forEach>
								</select>
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="4">
								检测项目
								<a class="pull-right btn btn-xs btn-outline btn-danger" onclick="itemToggle(this);"><i class="fa fa-chevron-down" aria-hidden="true"></i>闭合</a>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								 <table class="table table-bordered">
									<thead>
										<tr>
											<th width="60">序号</th>
											<th style="text-align: center;width: 10%;">检测点位</th>
											<th style="width: 100px;">采样日期</th>
											<th style="width: 12%" colspan="2">检测项目</th>
											<th style="text-align: center;width: 120px;">样品编号</th>
											<th style="text-align: center;">检测结果</th>
											<th style="text-align: center;width: 100px;">标准值</th>
											<th style="text-align: center;width: 150px;">检出限</th>
											<th style="text-align: center;width:80px;">单项判定</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="num" value="0"/>
										<c:forEach items="${vo.detailList}" var="t">
											<tr>
												<td colspan="10">
													${t.sampName}
												</td>
											</tr>
											<c:forEach items="${t.subList}" var="e" varStatus="v">
												<c:choose>
													<c:when test="${fn:length(e.subList)>0}">
														<c:forEach items="${e.subList}" var="e1" varStatus="v1">
															<tr>
																<c:if test="${v1.index==0}">
																	<td rowspan="${fn:length(e.subList)}">
																		<input type="text" class="form-control digits required" validate="required" name="detailList[${num}].sort" value="${e.sort}">
																		<input type="hidden" name="detailList[${num}].id" value="${e.id}">
																	</td>
																	<td rowspan="${fn:length(e.subList)}">
																		${e.pointName}
																	</td>
																	<td rowspan="${fn:length(e.subList)}">
																		${e.cyDate}
																	</td>
																	<td rowspan="${fn:length(e.subList)}">
																		${e.itemName}
																	</td>
																</c:if>
																<td>
																	${e1.itemName}
																</td>
																<td>
																	${e1.sampCode}
																</td>
																<td>
																	<c:choose>
																		<c:when test="${t.sampName.indexOf('有组织')>=0}">
																			浓度：
																			<input type="text" class="form-control" name="detailList[${num}].value" value="${e1.value}">
																			速率：
																			<input type="text" class="form-control" name="detailList[${num}].sl" value="${e1.sl}">
																		</c:when>
																		<c:otherwise>
																			<input type="text" class="form-control"  name="detailList[${num}].value" value="${e1.value}"  readonly="readonly"/>
																		</c:otherwise>
																	</c:choose>
																</td>
																<td>${e1.limited}</td>
																<td>${e1.limitLine}</td>
																<td>
																	${e1.result}
																</td>
															</tr>
															<c:set var="num" value="${num+1}"/>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr index="${v.index}" >
															<td>
																<input type="text" class="form-control digits required" validate="required" name="detailList[${num}].sort" value="${e.sort}">
																<input type="hidden" name="detailList[${num}].id" value="${e.id}">
															</td>
															<td>
																${e.pointName}
															</td>
															<td>
																${e.cyDate}
															</td>
															<td colspan="2">
																${e.itemName}
															</td>
															<td>
																${e.sampCode}
															</td>
														 	<td>
																<c:choose>
																	<c:when test="${t.sampName.indexOf('有组织')>=0}">
																		<div class="input-group date" style="width: 50%;float: left;">
											                            	 <span class="input-group-addon">浓度</span>
											                            	 <input type="text" class="form-control" name="detailList[${num}].value" value="${e.value}">
											                            </div>
											                            <div class="input-group date" style="width: 50%;float: left;">
											                            	 <span class="input-group-addon">速率</span>
											                            	 <input type="text" class="form-control" name="detailList[${num}].sl" value="${e.sl}">
											                            </div>
																	</c:when>
																	<c:otherwise>
																		<input type="text" class="form-control"  name="detailList[${num}].value" value="${e.value}"  readonly="readonly"/>
																	</c:otherwise>
																</c:choose>
															</td>
															<td>${e.limited}</td>
															<td>${e.limitLine}</td>
															<td>
																${e.result}
															</td>
														</tr>
														<c:set var="num" value="${num+1}"/>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<!-- <div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" type="button" onclick="fnSubmitSave('updateData.do?isCommit=0')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('updateData.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div> -->
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('updateData.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 提交</a>
							<a class="btn btn-w-m btn-danger" type="button" onclick="formSubmit4Back('updateBack.do')"><i class="fa fa-check" aria-hidden="true"></i> 退回</a>
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
<script>
function formSubmit4Back(url){
	var remark=$('#remark').val();
	if(remark==''){
		parent.toastr.error('请输入退回原因！', '');
		return false;
	}
	swal({
        title: "您确定要提交该任务吗",
        text: "提交后将无法修改，请谨慎操作！",
        type: "success",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
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
	});
}


//判定标准 弹出层
function fnSelectPstand(){
	var id=$('#standIds').val();
	layer.open({
		title:'测试标准',	
		type: 2,
		area: ['800px', '500px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/pstandard/select.do?ids='+id,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
		  var iframeWin = window[layero.find('iframe')[0]['name']];
		  var data=iframeWin.fnSelect();
		  $('#standIds').val(data.id);
		  $('#standNames').val(data.name);
		}
	});
}
	function fnSubmitSave(url){
		$('#thisForm').attr('action',url);
		 $('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
	}
	function fnSubmit(url){
		var msg=checkValue();
    	if(msg!=''){
    		layer.msg(msg, {icon: 0,time: 3000});
    		return true;
    	}
		swal({
	        title: "您确定要提交该任务吗",
	        text: "提交后将无法修改，请谨慎操作！",
	        type: "success",
	        showCancelButton: true,
	        confirmButtonColor: "#1ab394",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消"
	    }, function () {
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
	    });
	}
	function checkValue(){
		var msg='';
		$.ajax({ 
			url:"checkReportFile.do?id=${vo.id}",
			dataType:"json",
			type:"post",
			async:false,
			success: function(data){
				if("error"==data.type){
					msg=data.message;
				}
			},
			error:function(ajaxobj){  
				msg=ajaxobj;
		    }  
		});
		return msg;
	}
	function createReport(){
		$('#thisForm').attr('action','updateData.do?isCommit=0');
		 $('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    		var msg=checkValue();
	        	if(msg==''){
	        		if(confirm("报告已经存在，要重新生成吗？")){
	        			POBrowser.openWindow('${basePath}bus/report/cearteWord.do?id=${vo.id}','width=1200px;height=800px;');
	        		}
	        	}else{
	        		$('#editReprt').show();
	        		POBrowser.openWindow('${basePath}bus/report/cearteWord.do?id=${vo.id}','width=1200px;height=800px;');
	        	}
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
	}
	function fnOpenReport(){
		POBrowser.openWindow('${basePath}bus/report/editReport.do?id=${vo.id}','width=1200px;height=800px;');
	}
	$(function(){
		var has='${vo.filePath}';
		if(has==''){
			$('#editReprt').hide();
		}
	})
</script>
<script type="text/javascript">
$(function(){
	/*$('.fa-chevron-down').each(function(){
		$(this).closest('tr').next().hide();
		$(this).parent().html('<i class="fa fa-chevron-up" aria-hidden="true"></i>展开');
	});*/
});
function itemToggle(obj){
	var tt=$(obj).html();
	if(tt.indexOf('展开')>=0){
		$(obj).html('<i class="fa fa-chevron-down" aria-hidden="true"></i>闭合');
		$(obj).closest('tr').next().show()
	}else{
		$(obj).html('<i class="fa fa-chevron-up" aria-hidden="true"></i>展开');
		$(obj).closest('tr').next().hide()
	}
}
function fnTask(id,no){
	parent.layer.open({
		title:'任务【'+no+'】',	
		type: 2,
		area: ['1000px','85%'],
		fix: false, //不固定
		maxmin: true,
		content: '/bus/task/show.do?id='+id
	});
}
</script>
</body>
</html>
