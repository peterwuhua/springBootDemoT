<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">报价审批</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="active" style="width: 60px;text-align: center;" rowspan="4"><label>基<br>本<br>信<br>息</label></td>
							<td class="active" style="width: 10%"><label class="pull-right">客户名称:</label></td>
							<td>
								${vo.custName}
							</td>
							<td class="active" style="width: 10%"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
							<td>
								${vo.custUser}
							</td>
						</tr>
						<tr>
							 <td class="active"><label class="pull-right">单位地址:</label></td>
							<td>
								${vo.custAddress}
							</td>
							<td class="active"><label class="pull-right">联系电话:</label></td>
							<td>
								${vo.custMobile} 
							</td>
						</tr>
						<tr>
							 <td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
							<td>
								${vo.custEmail} 
							</td>
							<td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
							<td>
								${vo.custZip}
							</td>
						</tr>
						<tr>
							 <td class="active"><label class="pull-right">监测类别:</label></td>
							<td>
								${vo.taskType} 
							</td>
							<td class="active"><label class="pull-right">项目频次:</label></td>
							<td>
								${vo.pc} ${vo.pcUnit}
							</td>
						</tr>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						 <thead>
						 	<tr>
						 		<th style="width: 50px;">序号</th>
						 		<th style="width: 200px;">样品类型</th>
						 		<th style="width: 150px;">样品名称</th>
						 		<th >检测项目</th>
						 		<th style="width: 100px;">分析费</th>
						 		<th style="width: 100px;">采样费</th>
						 	</tr>
						 </thead>
						<tbody id="detail_tb">
							<c:forEach items="${vo.detailList}" var="e" varStatus="s">
								<tr index="${s.index}">
							 		<td>
							 			${s.index+1}
							 		</td>
							 		<td>
							 			${e.sampTypeName}
							 		</td>
							 		<td>
							 			${e.sampName}
							 		</td>
							 		<td>
							 			${e.itemNames}
							 		</td>
							 		<td>
							 			${e.fxPrice}
							 		</td>
							 		<td>
							 			${e.cyPrice}
							 		</td>
							 	</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tr>
								<td class="active"><label class="pull-right">检测费用:</label></td>
								<td>
									${vo.price}
								</td>
								<td class="active"><label class="pull-right">折&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;扣:</label></td>
								<td>
									${vo.discount}
								</td>
								<td class="active"><label class="pull-right">交通费用:</label></td>
								<td>
									${vo.jtPrice}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">报告费用:</label></td>
								<td>
									${vo.bgPrice}
								</td>
								<td class="active"><label class="pull-right">税费等其他费用:</label></td>
								<td>
									${vo.otherPrice}
								</td>
								<td class="active"><label class="pull-right">合同费用:</label></td>
								<td>
									${vo.htPrice}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">报价日期:</label></td>
								<td>
									${vo.bdate}
								</td>
								<td class="active"><label class="pull-right">有效期至:</label></td>
								<td>
									${vo.yxq}
								</td>
								<td class="active"><label class="pull-right">报&nbsp;&nbsp;价&nbsp;&nbsp;人:</label></td>
								<td>
									${vo.buserName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="5">
									${vo.remark}
								</td>
							</tr>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">确认人员:</label></td>
							<td class="width-35">
								<input type="hidden" id="auditId" name="auditId" value="${vo.auditId}">
								<input type="text" id="auditName" name="auditName" value="${vo.auditName}" class="form-control"  readonly="readonly">
							</td>
							<td class="width-15 active"><label class="pull-right">确认时间:</label></td>
							<td class="width-35">
								<div class="input-group date">
									<input type="text" id="auditDate" name="auditDate" class="form-control datetimeISO required" validate="required" value="${vo.auditDate}">
									  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea rows="2" class="form-control" id="remark" name="remark" maxlength="128"></textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('updateData.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 通过</a>
							<a class="btn btn-w-m btn-danger" href="javascript:;" onclick="formSubmitTh('updateData.do?isCommit=-1');"><i class="fa fa-check" aria-hidden="true"></i> 退回</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
		   <!-- Sweet alert -->
    <script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script>
		  
		function formSubmitTh(url){
			$('#thisForm').attr('action',url);
			var b = $("#thisForm").FormValidate();
			if(b){
				swal({
			        title: "您确定要退回该任务吗",
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
		    	});
			}
		}
	</script>
</body>
</html>
