<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><strong>任务登记</strong></li>
					<li><strong>编辑</strong> <c:if test="${vo.isBack=='Y'}">
							（<span style="color: red"> 退回原因：${logVo.msg}</span> ）
						</c:if></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active" rowspan="4" style="text-align: center; width: 50px;"><label>委<br>托<br>单<br>位
								</label></td>
								<td class="active"  style="width: 120px;"><label class="pull-right">单位名称:</label></td>
								<td class="width:35%">
										${vo.custVo.custName} 
										<input type="hidden" id="custId" name="custVo.clientVo.id" value="${vo.custVo.clientVo.id}">
								</td>
								<td class="active"  style="width: 120px;"><label class="pull-right">单位地址:</label></td>
								<td >${vo.custVo.custAddress}</td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td>${vo.custVo.custUser}</td>
								<td class="active"><label class="pull-right">联系电话:</label></td>
								<td>${vo.custVo.custTel}</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">行业分类和代码:</label></td>
								<td>
										${vo.custVo.industry}
								</td>
								<td class="active"><label class="pull-right">单位性质:</label></td>
								<td>${vo.custVo.attribute}</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">主要产品:</label></td>
								<td colspan="3">${vo.custVo.product}</td>
							</tr>
							<tr>
							 	<td id="rowsTd" class="active" rowspan="6" style="text-align: center;"><label>项<br>目<br>要<br>求
								</label></td>
								<td class="active"><label class="pull-right">项目名称:</label></td>
								<td>
									${vo.sampName} 
								</td>
								<td class="active"><label class="pull-right">项目类型:</label></td>
								<td>
									${vo.itemType}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;急:</label></td>
								<td>
									${vo.jj}
								</td>
								<td class="active"><label class="pull-right">拟完成日期:</label></td>
								<td>
									${vo.finishDate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">合同签订:</label></td>
								<td>
									${vo.htqd}
								</td>
								<td class="active"><label class="pull-right ">现场踏勘:</label></td>
								<td class="zcytd">
									${vo.xctk}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">方案编制:</label></td>
								<td>
									${vo.fabz}
								</td>
								<td class="active"></td>
								<td></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">项目负责人:</label></td>
								<td>${vo.userName}
									<input type="hidden" id="userId" name="userId" value="${vo.userId}">
								</td>
								<td class="active"><label class="pull-right">立项日期:</label></td>
								<td>
										${vo.date}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">${vo.remark}</td>
							</tr>
							<tr>
								<td class="active" rowspan="4" style="text-align: center;"><label>登<br>记<br>信<br>息</label></td>	
								<th class="active"><label class="pull-right">上传资料:</label></th>
								<td><input type="file" name="file" multiple="multiple" class="form-control" /></td>
								<td colspan="2" id="removeFile">
									<c:forEach items="${vo.fileList}" var="e" varStatus="v">
										<div style="float: left; margin-right: 10px;">
											<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
											<c:if test="${e.createUser==user}">
												<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a> 
											</c:if>  
										</div>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">登记人员:</label></td>
								<td>
										<input type="hidden" id="taskUserId" name="taskUserId" value="${vo.taskUserId}">
										<input type="text" id="taskUserName" name="taskUserName" value="${vo.taskUserName}" class="form-control "  readonly="readonly" >
								</td>
								<td class="active"><label class="pull-right">登记日期:</label></td>
								<td>
									<div class="input-group date">
										<input type="text" id="date" name="date" class="form-control datetimeISO required" validate="required" value="${vo.date}">
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3"><textarea rows="3" class="form-control" id="taskMsg" name="taskMsg" maxlength="128">${vo.taskMsg}</textarea></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:void(0);" onclick="formSubmitSave('save4Data.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" href="javascript:void(0);" onclick="formSubmit('${fn:length(vo.id)>0? 'update4Data.do?isCommit=1':'add4Data.do?isCommit=1'}');"><i class="fa fa-mail-forward" aria-hidden="true"></i> 保存并提交</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../../include/js.jsp"%>
	<script src="${basePath}h/js/plugins/suggest/bootstrap-suggest.min.js"></script>
	<!-- Sweet alert -->
	<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script>

	function formSubmitSave(url){
		$('#thisForm').attr('action',url);
		$('#thisForm').submit()
	}
	
	function formSubmit(url){
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
		    	 $('#thisForm').attr('action',url);
		    	 $('#thisForm').submit()
	    	})
		}else{
			parent.toastr.error('请检查必填项！', '');
		}
	}
	$('input[type="file"]').prettyFile();
	function removeFiles(id,obj){
		layer.confirm('确认要删除?', {icon:3, title:'系统提示'}, function(index){
			$.ajax({
				url:'${basePath}sys/files/deleteOne.do?id='+id,
				dataType:"json",
				type:"post",
				async:false,
				success: function(data){
					if(data.status=='success'){
						layer.msg(data.message, {icon: 0,time: 1000});
						$(obj).parent().remove();
					}
				},
				error:function(ajaxobj){
			    }  
			});
		});
	}
</script>
</body>
</html>
