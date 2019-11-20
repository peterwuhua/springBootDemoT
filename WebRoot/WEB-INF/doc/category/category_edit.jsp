<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">文件夹</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
						<input name="dirIds" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">上级文件夹:</label></th>
								<td class="width-35">
									<div class="input-group">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectcategory('${vo.parentVo.id}','${vo.parentVo.name}')">选择</button>
										</div>
										<input type="text" readonly="readonly" id="selectName" class="form-control" placeholder="请选择上级文件夹" value="${vo.parentVo.name}"> <input type="hidden" id="selectId" name="parentVo.id" value="${vo.parentVo.id}">
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">名称:</label></td>
								<td class="width-35"><input id="name" placeholder="名称" class="form-control required" validate="required" name="name" type="text" value="${vo.name}" onblur="checkCategoryName();" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">编码:</label></td>
								<td><input id="code" name="code" class="form-control alnum" placeholder="编码" type="text" value="${vo.code }" /></td>
								<td class="width-15 active"><label class="pull-right">顺序:</label></td>
								<td class="width-35"><input id="sort" class="form-control digits" name="sort" type="text" value="${vo.sort}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">说明:</label></td>
								<td colspan="3">
									<textarea id="describtion" placeholder="请输入文件说明" class="form-control" name="describtion" >${vo.describtion}</textarea>
								</td>	
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="fnSave();">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
							</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="fnAdd();">
								<i class="fa fa-check" aria-hidden="true"></i> 保存并返回
							</button>
							<a href="javascript:backMainPage();" class="btn btn-w-m btn-white"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
<script>
	function fnSave(){
		if(checkCategoryName()){
			$('form').attr('action','save.do');
			$('form').submit();
		}
	}
	function fnAdd(){
		if(checkCategoryName()){
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
	}

function fnSelectcategory(pId,pName){
	layer.open({
		title:'文件夹选择',	
		type: 2,
		area: ['300px', '470px'],
		fix: false, //不固定
		maxmin: true,
		content: 'select.do?parentVo.id='+pId+'&parentVo.name='+encodeURI(pName),
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  iframeWin.fnSelect();
			}
	});
}

//验证文件夹名不能为空
function checkCategoryName(){
	var CategoryName = $("#name").val();//名称
	var flag = false;
	if(CategoryName ==""){
		layer.msg('文件夹名不能为空', {icon: 0,time: 3000});
	}else{
		$.ajax({
			url:'isExistName.do',
			type:"GET",
			data:{'name':CategoryName,id:'${vo.id}'},//文件名是否存在
			dataType:'json',
			async: false,
			success:function(data){
				if(data.status == "success"){
				  flag = true;
				}else{
				   layer.msg('文件夹名已存在', {icon: 0,time: 3000});
				}
			}
		});
	}
	return flag;
}
</script>
</html>
