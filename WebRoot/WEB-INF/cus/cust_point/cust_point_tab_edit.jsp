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
					<input name="customerVo.id" value="${vo.customerVo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">监测点位:</label></td>
								<td class="width-35">
									<input id="name" class="form-control required" validate="required" name="name" type="text" value="${vo.name}" />
								</td>
								<td class="width-15 active"><label class="pull-right">点位代码:</label></td>
								<td class="width-35">
									<input id="code" class="form-control" name="code" type="text" value="${vo.code}" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">样品类型:</label></td>
								<td class="width-35">
									<select id="sampTypeId" name="sampTypeId" class="form-control" onchange="changeSampType()">
										<c:forEach items="${sampTypeList}" var="e" varStatus="s">
											<c:choose>
												<c:when test="${e.id==vo.sampTypeId}">
													<option value="${e.id}" selected="selected">${e.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.id}">${e.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
								<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
								<td class="width-35">
									<select id="sampId" name="sampId" class="form-control" >
										<c:forEach items="${sampList}" var="e" varStatus="s">
											<c:choose>
												<c:when test="${e.id==vo.sampId}">
													<option value="${e.id}" selected="selected">${e.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.id}">${e.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序:</label></td>
								<td class="width-35"><input id="sort" placeholder="0" class="form-control digits" name="sort" type="text" value="${vo.sort}" /></td>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td class="width-35"><input maxlength="128" id="remark" class="form-control" name="remark" type="text" value="${vo.remark}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">监测项目:</label></td>
								<td class="width-35" colspan="3">
									<textarea id="itemName" name="itemName" class="form-control"  onclick="fnSelectItem()">${vo.itemName}</textarea>
									<input type="hidden" id="itemId"  name="itemId" value="${vo.itemId}">
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
	function changeSampType(){
		var sampTypeId=$('#sampTypeId').val();
		$.ajax({
			url : '${basePath}init/sampType/listData.do?pid='+sampTypeId,
			datatype : "json",
			success : function(data) {
				var optionstring = '';
				for (var i = 0; i < data.length; i++) {
					if('${vo.sampId}'== data[i].id){
						optionstring += '<option value="'+data[i].id+'" selected="selected">'+data[i].name+'</option>';
					}else{
						optionstring += '<option value="'+data[i].id+'" >'+data[i].name+'</option>';
					}
				}
				$("#sampId").html(optionstring);
			}
		});
	}
	function fnSelectItem(){
		 var itemId=$('#itemId').val();
		 var sampId=$('#sampId').val();
		 parent.layer.open({
				title:'监测项目',	
				type: 2,
				area: ['800px', '80%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}init/item/selects.do?ids='+itemId+'&sampTypeIds='+sampId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  	var iframeWin=layero.find('iframe')[0];
				  	var data=iframeWin.contentWindow.fnSelect(index);
					$('#itemId').val(data.id);
					$('#itemName').val(data.name);
				}
			});
	 }
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
