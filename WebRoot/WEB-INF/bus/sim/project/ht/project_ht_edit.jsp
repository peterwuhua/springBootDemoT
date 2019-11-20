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
					<li><strong>合同签订</strong></li>
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
										${vo.rdate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">${vo.remark}</td>
							</tr>
							<tr>
							 	<td class="active" rowspan="4" style="text-align: center;"><label>合<br>同<br>信<br>息</label></td>
									<td class="width-15 active"><label class="pull-right">合同编号:</label></td>
									<td class="width-35">
										<input type="text" id="htNo" name="htNo" value="${vo.htNo}" class="form-control required" validate="required">
									</td>
									<td class="width-15 active"><label class="pull-right">合同总价:</label></td>
									<td class="width-35">
										<input type="text" id="totalPrice" name="totalPrice" value="${vo.totalPrice}" class="form-control number required" validate="required">
									</td>
							</tr>
							<tr>
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
								<td class="width-15 active"><label class="pull-right">签订人员:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="hidden" id="qdId" name="qdId" value="${vo.qdId}">
										<input type="text" id="qdName" name="qdName" value="${vo.qdName}" class="form-control required" validate="required" placeholder="请选择" onclick="fnSelectUserHt()">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUserHt()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">签订日期:</label></td>
								<td class="width-35">
									<div class="input-group date" >
											<input id="qdDate" name="qdDate"  class="form-control dateISO"  type="text" value="${vo.qdDate}" />
											<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="qdMsg" name="qdMsg" maxlength="128">${vo.qdMsg}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
						    <a class="btn btn-w-m btn-primary" href="javascript:void(0);" onclick="formSubmitSave('update4Data.do?isCommit=0');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('update4Data.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
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
	function fnShow(id,no){
		parent.layer.open({
			title:'项目详情【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/sim/project/show.do?id='+id
		});
	}
	function fnSelectUserHt(){
		var userId=$('#qdId').val();
		layer.open({
			title:'签订人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/select2.do?ids='+userId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#qdId').val(data.id);
			  $('#qdName').val(data.name);
			}
		});
	}
	function formSubmitSave(url){
    	$('#thisForm').attr('action',url);
		$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
		        location.reload() 
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
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
	
	 $(document).ready(function(){
		 countYSPrice();
	});
	function countYSPrice(){
		var htprice =  $("#htPrice").val();
		var contractJe=parseFloat(htprice);//合同金额
		var payratio=parseFloat($('#payRatio').val());//预付款比例
		var yfk = contractJe*payratio;
		var ssk = contractJe- yfk;
		$('#yfkMin').val(yfk);
		$('#sskMin').val(ssk);
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
