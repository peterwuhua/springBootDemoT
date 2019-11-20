<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>试剂管理</a></li>
					<li><strong> <c:if test="${fn:length(vo.id)>0}">编辑</c:if> <c:if test="${fn:length(vo.id)==0}">新增</c:if>
					</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal" id="thisForm">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">编号 :</label></td>
								<td class="width-35"><input id="no" placeholder="请输入编号" class="form-control required" validate="required" name="no" type="text" value="${vo.no }" /></td>
								</td>
								<td class="width-15 active"><label class="pull-right">名称:</label></td>
								<td class="width-35"><input type="text" name="name" id="name" placeholder="请输入名称" class="form-control required" validate="required" maxlength=64 value="${vo.name }"></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">俗称 :</label></td>
								<td class="width-35"><input id="sname" placeholder="请输入俗称" class="form-control" name="sname" type="text" value="${vo.sname }" /></td>
								<td class="width-15 active"><label class="pull-right">英文名称:</label></td>
								<td class="width-35"><input id="ename" placeholder="请输入英文名称" class="form-control" name="ename" type="text" value="${vo.ename }" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">级别:</label></td>
								<td class="width-35">
									<select id="grade" class="form-control" name="grade" type="text">
									</select>
								</td>
								<td class="width-15 active"><label class="pull-right">类型:</label></td>
								<td class="width-35">
									<select id="type" class="form-control" name="type" type="text">
									</select>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">数量:</label></td>
								<td class="width-35"><input id="amount" placeholder="请输入数量" class="form-control required number" validate="required" name="amount" type="text" value="${vo.amount }" /></td>
								<td class="width-15 active"><label class="pull-right">警戒数量:</label></td>
								<td class="width-35"><input id="safeAmount" placeholder="请输入警戒数量" class="form-control required number" validate="required" name="safeAmount" type="text" value="${vo.safeAmount }" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">单位:</label></td>
								<td class="width-35"><input id="unit" placeholder="请输入单位" class="form-control" name="unit" type="text" value="${vo.unit }" /></td>
								<td class="width-15 active"><label class="pull-right">供应商:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input name="supplier" id="supplier" value="${vo.supplier}" placeholder="请选择供应商" type="text" class="form-control" onclick="fnSelectSupplier()" /> 
										<input name="supplierId" id="supplierId" value="${vo.supplierId}" type="hidden" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectSupplier()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">批号:</label></td>
								<td class="width-35"><input id="bnum" placeholder="请输入批号" class="form-control" name="bnum" type="text" value="${vo.bnum }" /></td>
								<td class="width-15 active"><label class="pull-right">纯度:</label></td>
								<td class="width-35">
									<select id="purity" class="form-control" name="purity" type="text" >
									</select>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">保存条件:</label></td>
								<td class="width-35"><input id="saveCode" placeholder="请输入保存条件" class="form-control" name="saveCode" type="text" value="${vo.saveCode }" /></td>
								<td class="width-15 active"><label class="pull-right">用途:</label></td>
								<td class="width-35"><input id="purpose" placeholder="请输入用途" class="form-control" name="purpose" type="text" value="${vo.purpose }" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">生产日期:</label></td>
								<td class="width-35">
									<div class="input-group date">
										<input id="mfg" placeholder="请输入生产日期" class="form-control dateISO"  name="mfg" type="text" value="${vo.mfg }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">有效期至:</label></td>
								<td class="width-35">
									<div class="input-group date">
										<input id="exp" placeholder="请输入有效期" class="form-control dateISO"  name="exp" type="text" value="${vo.exp }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">保&nbsp;管&nbsp;&nbsp;人:</label></td>
								<td>
									<div class="input-group">
										<input name="keeper" id="keeper" value="${vo.keeper}" placeholder="请选择保管人" type="text"  class="form-control required"  validate="required" onclick="fnSelectUser()" /> 
										<input name="keepId" id="keepId" value="${vo.keepId}" type="hidden" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">参考单价:</label></td>
								<td class="width-35"><input id="price" placeholder="请输入参考单价" class="form-control number" name="price" type="text" value="${vo.price }" /></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formsubmit2Save('save.do');">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
							</button>
							<button class="btn btn-w-m btn-primary" type="button"  onclick="formsubmit();">
								<i class="fa fa-check" aria-hidden="true"></i> 保存并返回
							</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script type="text/javascript">
		function gradeSelect() {
			$.ajax({
				url : '${basePath}sys/code/ajaxCodeContent.do?code=res-grade',
				datatype : "json",
				success : function(data) {
					var value = data.split(",");
					var optionstring = '<option value="" >请选择</option>';
					for (var i = 0; i < value.length; i++) {
						if(value[i]!=''){
							if('${vo.grade}'== value[i]){
								optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"+ value[i] + "</option>";
							}else{
								optionstring += "<option value=\"" + value[i] + "\" >"+ value[i] + "</option>";
							}
						}
					}
					$("#grade").html(optionstring);
				}
			});
		}
		function typeSelect() {
			$.ajax({
				url : '${basePath}sys/code/ajaxCodeContent.do?code=res-type',
				datatype : "json",
				success : function(data) {
					var value = data.split(",");
					var optionstring = '<option value="" >请选择</option>';
					for (var i = 0; i < value.length; i++) {
						if(value[i]!=''){
							if('${vo.type}'== value[i]){
								optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"+ value[i] + "</option>";
							}else{
								optionstring += "<option value=\"" + value[i] + "\" >"+ value[i] + "</option>";
							}
						}
					}
					$("#type").html(optionstring);
				}
			});
		}
		function puritySelect() {
			$.ajax({
				url : '${basePath}sys/code/ajaxCodeContent.do?code=res-purity',
				datatype : "json",
				success : function(data) {
					var value = data.split(",");
					var optionstring = '<option value="" >请选择</option>';
					for (var i = 0; i < value.length; i++) {
						if(value[i]!=''){
							if('${vo.purity}'== value[i]){
								optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"+ value[i] + "</option>";
							}else{
								optionstring += "<option value=\"" + value[i] + "\" >"+ value[i] + "</option>";
							}
						}
					}
					$("#purity").html(optionstring);
				}
			});
		}
		function fnSelectUser(){
			var keepId=$('#keepId').val();
			layer.open({
				title:'人员选择',	
				type: 2,
				 area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}sys/account/select.do?id='+keepId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var data=iframeWin.fnSelect();
					$('#keepId').val(data.id);
					$('#keeper').val(data.name);
				}
			});
		}
		function fnSelectSupplier(){
			var supplierId=$('#supplierId').val();
			layer.open({
				title:'供应商选择',	
				type: 2,
				 area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}res/supplier/select.do?id='+supplierId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var data=iframeWin.fnSelect();
					console.log(data)
					$('#supplierId').val(data.id);
					$('#supplier').val(data.name);
				}
			});
		}
		function formsubmit2Save(url){
			if(url.length>0){
				$("form").attr('action',url);
				$("form").submit();
			}else{
				layer.alert('传入的url有误!', {icon: 2,title:'系统提示',shadeClose: true});
			}
		}
		function formsubmit(){
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
		$(function(){
			gradeSelect();
			typeSelect();
			puritySelect()
		});
	</script>
</body>
</html>
