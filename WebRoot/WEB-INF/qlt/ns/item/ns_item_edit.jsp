<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>

<style type="text/css">
.table > tbody > tr > td{
padding: 0px;
}
.fc{
	height: 100% !important;
	padding: 5px;
}
.input-group  span{
	width: 30px;
}
#ct_tb .input-group{
	margin: 1px;
}
.xh{
	display: table-cell;
	vertical-align: middle;
	text-align: right;
	font-size: 14px;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<th class="active" style="width: 100px;"><label class="pull-right">条款:</label></th>
							<td style="width: 100px;">
								<input id="code" class="form-control required" validate="required" name="code" type="text" value="${vo.code}" />
							</td>
							<th class="active" style="width: 100px;"><label class="pull-right">要素:</label></th>
							<td>
								<input id="name" class="form-control required" validate="required" name="name" type="text" value="${vo.name}" />
							</td>
						</tr>
					</table>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th style="width: 50px;">
									条款
								</th>
								<th style="width: 300px;">
									检查项目
								</th>
								<th>
									检查重点
								</th>
								<th style="width: 50px;">
									<a class="btn btn-xs btn-info" href="javascript:;" onclick="addCt();"><i class="fa fa-plus" aria-hidden="true"></i> 内容</a>
								</th>
							</tr>
						</thead>
						<tbody id="ct_tb">
							 <c:forEach items="${vo.subList}" var="e" varStatus="v">
							 	<tr index="${v.index}">
							 		<td><input type="text" id="code${v.index}" name="subList[${v.index}].code" class="form-control fc" value="${e.code}"></td>
							 		<td><textarea class="form-control  fc required" validate="required" id="name${v.index}" name="subList[${v.index}].name">${e.name}</textarea></td>
							 		<td id="zd_td_${v.index}">
							 			<c:forEach items="${e.subList}" var="e1" varStatus="v1">
								 			<div  class="input-group" style="width: 100%" id="name_${v.index}_${v1.index}"  index="${v1.index}">
								 				<span class="xh">${v1.index+1}、</span>
								 				<input type="text" name="subList[${v.index}].subList[${v1.index}].name" class="form-control required" validate="required" value="${e1.name}">
									        	<span style="display: table-cell;color:red;" class=" btn btn-xs fa fa-times" aria-hidden="true" onclick="deleteOne(${v.index},${v1.index});return false;"></span>
									        </div>
								        </c:forEach>
								     </td>
								     <td><a class="btn btn-outline btn-xs btn-info" href="javascript:;" onclick="addZd(${v.index});"><i class="fa fa-plus" aria-hidden="true"></i> 重点</a></td>
							 	</tr>
							 </c:forEach>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
<script type="text/javascript">
function addCt(){
	var table=$('#ct_tb');
	var num=table.find('tr').length;
	var index=0;
	if(num>0){
		index=parseInt(table.find('tr').eq(num-1).attr('index'));
		index++;
	}
	var code=$('#code').val();
	if(code!=''){
		code=code+'.'+(index+1);
	}else{
		code=(index+1);
	}
	table.append($('<tr index='+index+'>')
			.append('<td><input type="text" id="code'+index+'" name="subList['+index+'].code" class="form-control fc" value="'+code+'"></td>')
			.append('<td><textarea class="form-control  fc required" validate="required" id="name'+index+'" name="subList['+index+'].name"></textarea></td>')
			.append('<td id="zd_td_'+index+'"><div  class="input-group" style="width: 100%" id="name_'+index+'_0"  index="0"><span class="xh">1、</span><input type="text" name="subList['+index+'].subList[0].name" class="form-control required" validate="required" >'+
			        '<span style="display: table-cell;color:red;" class=" btn btn-xs fa fa-times" aria-hidden="true" onclick="deleteOne('+index+',0);return false;"></span></div></td>')
			.append('<td><a class="btn btn-outline btn-xs btn-info" href="javascript:;" onclick="addZd('+index+');"><i class="fa fa-plus" aria-hidden="true"></i> 重点</a></td>'));
}
function addZd(index){
	var tb=$('#zd_td_'+index)
	var num=tb.find('div').length;
	var n=0;
	if(num>0){
		n=parseInt(tb.find('div').eq(num-1).attr('index'));
		n++;
	}
	tb.append('<div  class="input-group" style="width: 100%" id="name_'+index+'_'+n+'"  index="'+n+'"><span class="xh">'+(n+1)+'、</span><input type="text" name="subList['+index+'].subList['+n+'].name" class="form-control required" validate="required" >'+
	        '<span style="display: table-cell;color:red;" class=" btn btn-xs fa fa-times" aria-hidden="true" onclick="deleteOne('+index+','+n+');return false;"></span></div>');
}
function deleteOne(x,y){
	$('#name_'+x+'_'+y).remove();
}
	var index = parent.layer.getFrameIndex(window.name);
	function submitSave() {
		var t = $("#thisForm").FormValidate();
		if (t) {
			$('#thisForm').ajaxSubmit(function(res) {
				parent.layer.msg(res.message, {
					icon : 0,
					time : 3000
				})
				if (res.status == 'success') {
					parent.location.reload();
					parent.layer.close(index);
				}
			});
		}
	}
</script>
</body>
</html>
