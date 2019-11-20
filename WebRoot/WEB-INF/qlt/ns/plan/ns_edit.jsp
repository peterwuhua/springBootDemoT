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
					<li><a>内审计划</a></li>
					<li><strong>编辑</strong>
						<c:if test="${vo.isBack=='Y'}">
							（<span style="color: red"> 退回原因：${logVo.msg}</span> ）
						</c:if>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm" >
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">计划名称:</label></td>
								<td class="width-35">
									<input type="text" id="title" name="title" class="form-control required" validate="required" value="${vo.title}"> 
								</td>
								<td class="width-15 active"><label class="pull-right">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份:</label></td>
								<td class="width-35">
									<div class="input-group date">
		                              	<input id="year" name="year" class="form-control required" validate="required" type="text" value="${vo.year}" />
		                              	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="deptName" name="deptName" class="form-control required" validate="required" placeholder="请选择" value="${vo.deptName}"  onclick="fnSelectOrg()"> 
										<input type="hidden" id="deptId" name="deptId" value="${vo.deptId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectOrg()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">审核目的:</label></td>
								<td class="width-35">
									<input type="text" id="purpose" name="purpose" class="form-control" value="${vo.purpose}"> 
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">审核范围:</label></td>
								<td>
									<input type="text" id="ranges" name="ranges"  class="form-control" value="${vo.ranges}">
								</td>
								<td class="active"><label class="pull-right">审核依据:</label></td>
								<td>
									<input type="text" id="stand" name="stand"  class="form-control" value="${vo.stand}">
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">审核方式:</label></td>
								<td>
									<input type="text" id="auditType" name="auditType"  class="form-control" value="${vo.auditType}">
								</td>
								<td class="active"><label class="pull-right">内审地点:</label></td>
								<td>
									<input type="text" id="address" name="address"  class="form-control" value="${vo.address}">
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内审组长:</label></td>
								<td>
									<div class="input-group">
										<input type="text" id="headName" name="headName" class="form-control required" validate="required" placeholder="请选择" value="${vo.headName}"  onclick="fnSelectHead('')"> 
										<input type="hidden" id="headId" name="headId" value="${vo.headId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectHead('')">选择</button>
										</div>
									</div>
								</td>
								<td class="active"><label class="pull-right">审核人员:</label></td>
								<td>
									<div class="input-group">
										<input type="text" id="nsName" name="nsName" class="form-control required" validate="required" placeholder="请选择" value="${vo.nsName}"  onclick="fnSelectUsers('')"> 
										<input type="hidden" id="nsId" name="nsId" value="${vo.nsId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUsers('')">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">编&nbsp;&nbsp;制&nbsp;&nbsp;人:</label></td>
								<td>
									<input type="hidden" id="userId" name="userId" value="${vo.userId}">
									<input type="text" id="userName" name="userName" class="form-control" value="${vo.userName}" readonly="readonly">
								</td>
								<td class="active"><label class="pull-right ">编制日期:</label></td>
								<td>
									<div class="input-group date">
		                              	<input id="date" name="date" class="form-control required dateISO" validate="required" type="text" value="${vo.date}" />
		                              	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea maxlength="128" rows="2" class="form-control" id="remark" name="remark">${vo.remark}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<table class="table table-bordered">
						<thead>
							<tr>
								<td colspan="15" style="background-color:#fff;border: 0px;">要素信息</td>
								<td style="background-color:#fff;border: 0px;text-align: right;">
									<a class="btn btn-xs btn-info" href="javascript:;" onclick="addDt();"><i class="fa fa-plus" aria-hidden="true"></i> 内容</a>
								</td>
							</tr>
							<tr>
								<th style="width: 80px;text-align: center;">条款</th>
								<th style="text-align: center;">要素</th>
								<th style="width: 30px;">一月</th>
								<th style="width: 30px;">二月</th>
								<th style="width: 30px;">三月</th>
								<th style="width: 30px;">四月</th>
								<th style="width: 30px;">五月</th>
								<th style="width: 30px;">六月</th>
								<th style="width: 30px;">七月</th>
								<th style="width: 30px;">八月</th>
								<th style="width: 30px;">九月</th>
								<th style="width: 30px;">十月</th>
								<th style="width: 30px;">十一月</th>
								<th style="width: 30px;">十二月</th>
								<th style="width: 120px;text-align: center;">负责人</th>
								<th style="width: 150px;text-align: center;">协助人</th>
								<th style="width: 30px;"></th>
							</tr>
						</thead>
						<tbody id="ct_tb">
							<c:forEach items="${vo.detailList}" var="e" varStatus="v">
								<tr>
									<td>
										<input id="code${v.index}" name="detailList[${v.index}].code" value="${e.code}" class="form-control" type="text"/>
									</td>
									<td>
										${e.name}
										<input name="detailList[${v.index}].name" value="${e.name}" type="hidden"/>
										<input name="detailList[${v.index}].id" value="${e.id}" type="hidden"/>
									</td>
									<c:forEach begin="1" end="12" var="i">
										<td class="mtd">
											<c:set var="a"><c:out value="${i}"/></c:set>
											<c:set var="b" value="${i},"/>
											<c:set var="c" value=",${i}"/>
											<c:set var="d" value=",${i},"/>
											<div class="checkbox i-checks">
												<div class="icheckbox_square-green">
													<c:choose>
														<c:when test="${e.months==a || fn:startsWith(e.months,b) || fn:endsWith(e.months,c) || fn:contains(e.months,d)}">
															<input type="checkbox" name="detailList[${v.index}].months" value="${i}" checked="checked">
														</c:when>
														<c:otherwise>
															<input type="checkbox" name="detailList[${v.index}].months" value="${i}">
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</td>
									</c:forEach>
									<td>
										<div class="input-group">
											<input id="headName${v.index}" name="detailList[${v.index}].headName" value="${e.headName}" class="form-control" type="text"  onclick="fnSelectHead(${v.index})"/>
											<input id="headId${v.index}" name="detailList[${v.index}].headId" value="${e.headId}" type="hidden"/>
											<div class="input-group-btn">
												<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectHead(${v.index})">选择</button>
											</div>
										</div>
									</td>
									<td>
										<div class="input-group">
											<input id="nsName${v.index}" name="detailList[${v.index}].xzName" value="${e.xzName}" class="form-control" type="text"  onclick="fnSelectUsers(${v.index})"/>
										<input id="nsId${v.index}" name="detailList[${v.index}].xzId" value="${e.xzId}" type="hidden"/>
											<div class="input-group-btn">
												<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUsers(${v.index})">选择</button>
											</div>
										</div>
									</td>
									<td align="center">
										<a class=" btn btn-xs fa fa-times btn-danger" title="删除" href="javascript:;" onclick="deleteThis('${e.id}',this)"></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitSave('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a id="sub_btn" class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('${fn:length(vo.id)>0? 'updateData.do?isCommit=1':'addData.do?isCommit=1'}');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
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
	laydate.render({
		  elem: '#year',
		  theme: 'molv',
		  type: 'year'
	});
	function fnSelectOrg(){
		var deptId=$('#deptId').val();
		layer.open({
			title:'部门选择',	
			type: 2,
			area: ['300px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/org/select.do?id='+deptId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#deptId').val(data.id);
			  $('#deptName').val(data.name);
			}
		});
	}
	function fnSelectHead(n){
		var headId=$('#headId'+n).val();
		layer.open({
			title:'人员选择',	
			type: 2,
			area: ['800px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/select.do?id='+headId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#headId'+n).val(data.id);
			  $('#headName'+n).val(data.name);
			}
		});
	}
	function fnSelectUsers(n){
		var nsId=$('#nsId'+n).val();
		layer.open({
			title:'人员选择',	
			type: 2,
			area: ['800px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/selects.do?ids='+nsId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#nsId'+n).val(data.id);
			  $('#nsName'+n).val(data.name);
			}
		});
	}
	function addDt(){
		var id='${vo.id}';
		var b = $("#thisForm").FormValidate();
		$('#thisForm').attr('action','addData.do');
		if(id!=''){
			$('#thisForm').attr('action','updateData.do');
		}
		$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status!='success'){
	    	    parent.toastr.success(res.message, '');
		        return false;
	        }else{
	        	id=res.object;
	        	
	        	layer.open({
	    			title:'要素选择',	
	    			type: 2,
	    			area: ['600px', '500px'],
	    			fix: false, //不固定
	    			maxmin: true,
	    			content: '${basePath}qlt/ns/item_selects.do?id='+id,
	    			btn: ['确定','取消'], //按钮
	    			yes: function(index, layero) {
	    				 var iframeWin = window[layero.find('iframe')[0]['name']];
	    				  var data=iframeWin.fnSelect();
	    			}
	    		});
	        }
		});
	}
	function deleteThis(id,obj){
		layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
			$.ajax({ 
				url:"${basePath}qlt/ns/deleteDetail.do",
				dataType:"json",
				data:{'id':id},
				async: false,
				type:"post",
				success: function(data){
					layer.msg(data.message, {icon: 0,time: 3000})
					if("success"==data.status){
						 $(obj).parent().parent().remove();
					}
				},
				error:function(ajaxobj){  
					layer.msg(ajaxobj, {icon: 0,time: 3000});
			    }  
			});
		});
	}
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
	});
		function formSubmitSave(url){
			$('#thisForm').attr('action',url);
			$('#thisForm').submit()
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
