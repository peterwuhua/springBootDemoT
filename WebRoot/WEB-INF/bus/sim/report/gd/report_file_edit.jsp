<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../../include/css.jsp"%>
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../../include/status.jsp"%>

<style type="text/css">
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">备案归档</a></li>
					<li><strong>编辑</strong></li>
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
					</table>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						<legend>归档信息</legend>
					</fieldset>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">存放位置:</label></td>
								<td class="width-35">
									<textarea id="position" name="position" class="form-control" placeholder="纸质文件存放位置：地点+楼层+柜号+列号">${vo.position}</textarea>
								</td>
								<td class="width-15 active"><label class="pull-right">电子存档:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" readonly="readonly" name="categoryName" id="categoryName" class="form-control required" validate="required" placeholder="请选择存放路径" value="${vo.categoryName}"> 
										<input type="hidden" id="categoryId" name="categoryId" value="${vo.categoryId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectcategory()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">文件清单:</label></td>
								<td class="width-35" colspan="3">
									<table class="table">
										<thead>
											<tr>
												<th>
													序号
												</th>
												<th>
													文件名称
												</th>
												<th>
													分类
												</th>
												<th>
													备注
												</th>
												<th width="60">
													操作
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${vo.archList}" var="e" varStatus="v">
												<tr>
													<td>
														${v.index+1}
														<input type="hidden" name="archList[${v.index}].id" value="${e.id}">
													</td>
													<td>
														${e.fileName}
														<input type="hidden" name="archList[${v.index}].fileName" value="${e.fileName}">
														<input type="hidden" name="archList[${v.index}].filePath" value="${e.filePath}">
													</td>
													<td>
														<input type="text" class="form-control" name="archList[${v.index}].sign" value="${e.sign}">
													</td>
													<td>
														<input type="text" class="form-control" name="archList[${v.index}].describtion" value="${e.describtion}">
													</td>
													<td>
														<a class="btn btn-outline btn-sm btn-danger" href="javascript:;" onclick="deleteThis(this);"><i class="fa fa-remove"></i> 删除</font></a>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">归&nbsp;档&nbsp;&nbsp;人:</label></td>
								<td>
									<input type="text" id="fileGder" name="fileGder" class="form-control" placeholder="归档人员" value="${vo.fileGder}"  readonly="readonly">
									<input type="hidden" name="fileGderId"  value="${vo.fileGderId}">
								</td>
								<td class="width-15 active"><label class="pull-right">归档日期:</label></td>
								<td>
									<div class="input-group date">
		                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                              	<input id="fileGdDate" name="fileGdDate" class="form-control required datetimeISO" validate="required" placeholder="归档日期" type="text" value="${vo.fileGdDate}" />
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea style="width: 100%;" id="fileGdMsg" name="fileGdMsg" class="form-control" maxlength="128">${vo.fileGdMsg}</textarea>
								</td>
							</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('updateData.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 提交</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../../include/js.jsp"%>
    <script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script>
	function fnSelectcategory() {
		layer.open({
			title : '档案位置选择',
			type : 2,
			area : [ '300px', '400px' ],
			fix : false, //不固定
			maxmin : true,
			content : '${basePath}bus/archiveType/select.do?id='+ $('#categoryId').val(),
			btn : [ '确定', '取消' ], //按钮
			btn1 : function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#categoryId').val(data.id);
				$('#categoryName').val(data.name);
			}
		});
	}
	function deleteThis(obj){
		layer.confirm('确认要删除?', {icon:3, title:'系统提示'}, function(index){
			$(obj).parent().parent().remove();
			layer.close(index);
		});
	}
		function fnTask(id,no){
			parent.layer.open({
				title:'任务【'+no+'】',	
				type: 2,
				area: ['1000px','85%'],
				fix: false, //不固定
				maxmin: true,
				content: '/bus/sim/task/show.do?id='+id
			});
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
			    });
			}
		}
	</script>
</body>
</html>
