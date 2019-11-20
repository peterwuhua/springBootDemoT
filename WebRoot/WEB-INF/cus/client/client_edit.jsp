<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
    <%@ include file="../../include/status.jsp"%>
 <style type="text/css">
.panel-heading{
	padding: 0px;
}
</style>
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
						<h5>
							<ol class="breadcrumb">
								<li><a>受检单位</a></li>
								<li><strong>编辑</strong></li>
							</ol>
						</h5>
					</div>
                    <div class="ibox-content">
                       <form method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal" id="thisForm">
		                    <c:if test="${fn:length(vo.id)>0}">
		                    	<input name="id" value="${vo.id}" type="hidden" />
		                    </c:if>
		                    <div class="panel-heading">
								<div class="panel-options">
									<ul class="nav nav-tabs">
										<li class="active"><a href="#cus1" data-toggle="tab">单位信息 </a></li>
										<li><a onmousedown="checkSave();" href="#" onclick="location.href='${basePath}cus/clientPoint/gridTab.do?clientVo.id=${vo.id}'" data-toggle="tab">监测点位</a></li>
									</ul>
								</div>
							</div>
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">单位名称:</label></td>
										<td class="width-35"><input id="name" placeholder="请输入受检单位名称"  class="form-control required"  maxlength="128" name="name" type="text" value="${vo.name}"/></td>
										<td class="width-15 active"><label class="pull-right">单位性质:</label></td>
										<td class="width-35">
											<select id="attribute" name="attribute" class="form-control"></select>
										</td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">所属行业:</label></td>
										<td class="width-35">
											<select id="industry" name="industry" class="form-control">
											</select>
										<td class="width-15 active"><label class="pull-right">所在区域:</label></td>
										<td class="width-35">
											<div class="input-group">
		                                        <div class="input-group-btn">
		                                            <button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect('${vo.areaId}','${vo.areaPath}')">选择</button>
		                                        </div>
		                                        <input type="text" readonly="readonly" id="selectPath" name="areaPath" class="form-control" placeholder="请选择上级区域" value="${vo.areaPath}">
		                                        <input type="hidden" id="selectId" name="areaId" value="${vo.areaId}">
		                                    </div>
										</td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">单位地址:</label></td>
										<td class="width-35"><input id="address" placeholder="请输入受检单位地址"  class="form-control" maxlength="128" name="address" type="text" value="${vo.address}"/></td>
										<td class="width-15 active"><label class="pull-right">联&nbsp;系&nbsp;&nbsp;人:</label></td>
										<td class="width-35"><input id="person" placeholder="请输入联系人"  class="form-control" maxlength="32" name="person" type="text" value="${vo.person}"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">联系电话:</label></td>
										<td class="width-35"><input id="phone" placeholder="请输入电话"  class="form-control" name="phone" type="text" value="${vo.phone}"/></td>
										<td class="width-15 active"><label class="pull-right">电子邮箱:</label></td>
										<td class="width-35"><input id="email" placeholder="请输入邮箱"  class="form-control email" name="email" type="text" value="${vo.email}"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">主要产品:</label></td>
										<td class="width-35" colspan="3">
											<textarea rows="2" name="product" class="form-control" id="product">${vo.product}</textarea>
										</td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">备注信息:</label></td>
										<td class="width-35" colspan="3">
											<textarea rows="2" name="remark" class="form-control" id="remark" maxlength="128">${vo.remark}</textarea>
										</td>
									</tr>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                               <div class="col-sm-12 col-sm-offset-1">
									<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit4Save('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
									<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit();"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</button>
									<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
								</div>
                            </div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
<script>
$(document).ready(function(){
	$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})
	cusTypeSelect();	
	industrySelect();
});
function fnSelect() {
	var pId=$('#selectId').val();
	layer.open({
		title : '区域选择',
		type : 2,
		area : [ '300px', '470px' ],
		fix : false, //不固定
		maxmin : true,
		content : '${basePath}sys/area/select.do?id=' + pId,
		btn : [ '确定', '取消' ], //按钮
		btn1 : function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#selectId').val(data.id);
			$('#selectPath').val(data.path);
		}
	});
}
function cusTypeSelect() {
	$.ajax({
		url : '${basePath}sys/code/ajaxCodeContent.do?code=cus-khlx',
		datatype : "json",
		success : function(data) {
			var value = data.split(",");
			var optionstring = "<option value=\"\" >-请选择-</option>";
			for (var i = 0; i < value.length; i++) {
				if('${vo.attribute}'== value[i]){
				optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
						+ value[i] + "</option>";
				}else{
					optionstring += "<option value=\"" + value[i] + "\" >"
						+ value[i] + "</option>";
				}
			}
			$("#attribute").html(optionstring);
		}
	});
}

function industrySelect() {
	$.ajax({
		url : '${basePath}sys/code/ajaxCodeContent.do?code=cus-sshy',
		datatype : "json",
		success : function(data) {
			var value = data.split(",");
			var optionstring = "<option value=\"\" >-请选择-</option>";
			for (var i = 0; i < value.length; i++) {
				if('${vo.industry}'== value[i]){
				optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
						+ value[i] + "</option>";
				}else{
					optionstring += "<option value=\"" + value[i] + "\" >"
						+ value[i] + "</option>";
				}
			}
			$("#industry").html(optionstring);
		}
	});
}
function checkSave(){
	var id='${vo.id}';
	if(id==''){
		layer.alert('请先保存单位基本信息', {icon: 6});
		return false;
	}else{
		return true;
	}
}
function formSubmit4Save(url){
	$('#thisForm').attr('action',url);
	$('#thisForm').submit()
}
function formSubmit(){
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
</script>
</body>
</html>
