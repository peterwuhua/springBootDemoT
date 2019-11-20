<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'updateTab.do':'addTab.do'}" class="form-horizontal">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<input name="clientVo.id" value="${vo.clientVo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">点位大类:</label></td>
								<td class="width-35">
									<select class="form-control" name="sampType" id="sampType" onchange="checkSampType();">
										<c:forEach items="${envList}" var="e">
											<c:choose>
												<c:when test="${e==vo.sampType}">
													<option value="${e}" selected="selected">${e}</option>
												</c:when>
												<c:otherwise>
													<option value="${e}">${e}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
								<td class="width-15 active"><label class="pull-right">车间/岗位:</label></td>
								<td class="width-35">
									<input id="room" class="form-control" name="room" type="text" value="${vo.room}" placeholder="职业卫生或公共卫生必填"/>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">监测点位:</label></td>
								<td class="width-35">
									<input id="name" class="form-control required" validate="required" name="name" type="text" value="${vo.name}" placeholder="采样点"/>
								</td>
								<td class="width-15 active"><label class="pull-right">点位代码:</label></td>
								<td class="width-35">
									<input id="code" class="form-control" name="code" type="text" value="${vo.code}" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">样品类型:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="sampTypeName" name="sampTypeName" class="form-control required" validate="required" placeholder="请选择" value="${vo.sampTypeName}" onclick="fnSelectSampType()"> 
										<input type="hidden" id="sampTypeId" name="sampTypeId" value="${vo.sampTypeId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectSampType()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
								<td class="width-35"><input id="sampName" class="form-control" name="sampName" type="text" value="${vo.sampName}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序:</label></td>
								<td class="width-35"><input id="sort" placeholder="0" class="form-control digits" name="sort" type="text" value="${vo.sort}" /></td>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td class="width-35" >
									<textarea id="remark" name="remark" class="form-control" maxlength="128">${vo.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">监测项目:</label></td>
								<td class="width-35" colspan="3">
									<textarea id="itemName" name="itemName" class="form-control"  onclick="fnSelectItem()">${vo.itemName}</textarea>
									<input type="hidden" id="itemId"  name="itemId" value="${vo.itemId}">
									<input type="hidden" id="imId"  name="imId" value="${vo.imId}">
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script>
	function fnSelectSampType(){
		var sampType=$('#sampType').val();
		var sampTypeId=$('#sampTypeId').val();
		var sampType=$('#sampType').val();
		var url=''
		if(sampType=='环境'){
			 url='${basePath}init/sampType/select_last.do?id='+sampTypeId+'&name='+encodeURI(sampType);
		}else{
			url='${basePath}init/sampType/selects_last.do?ids='+sampTypeId+'&name='+encodeURI(sampType);
		}
		parent.layer.open({
			title:'样品类型',	
			type: 2,
			area: ['300px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: url,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin=layero.find('iframe')[0];
			  	var data=iframeWin.contentWindow.fnSelect(index);
			  $('#sampTypeId').val(data.id);
			  $('#sampTypeName').val(data.name);
			}
		});
	}
	function checkSampType(){
		var sampType=$('#sampType').val();
		if(sampType=='环境'){
			if(!$('#sampTypeName').hasClass('required')){
				$('#sampTypeName').addClass('required');
				$('#sampTypeName').addAttr('validate','required');
			}
		}else{
			$('#sampTypeName').removeClass('required');
			$('#sampTypeName').removeAttr('validate');
		}
	}
	function fnSelectItem(){
		 var itemId=$('#imId').val();
		 var sampId=$('#sampTypeId').val();
		 var sampType=$('#sampType').val();
		 if(sampId==null||sampId==''){
			 parent.layer.msg('请选择样品类型', {icon: 0,time: 3000})
			 return false;
		 }
		var	url='${basePath}init/item/im_selects.do?ids='+itemId+'&sampTypeIds='+sampId+'&sampType='+encodeURI(sampType);
		parent.layer.open({
			title:'检测项目',	
			type: 2,
			area: ['1000px', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: url,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin=layero.find('iframe')[0];
				var data=iframeWin.contentWindow.fnSelect();
				$('#itemId').val(data.id);
				$('#itemName').val(data.name);
				$('#imId').val(data.im);
				parent.layer.close(index);
			}
		});
	}
	$(function(){
		checkSampType();
	});
	var index = parent.layer.getFrameIndex(window.name); 
	function saveTable(_target){
		var b = $("#thisForm").FormValidate();
		if(b){
			 $('#thisForm').ajaxSubmit(function(res) {
				parent.layer.msg(res.message, {icon: 0,time: 3000})
		    	if(res.status=='success'){
		    	    parent.jqgridReload();
		    	    parent.layer.close(index);
		        }
			});
		}
	}
</script>
</body>
</html>
