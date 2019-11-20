<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">采样方法</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">检测项目:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="itemName" name="itemVo.name" class="form-control required" validate="required" value="${vo.itemVo.name}" onclick="fnSelectItem()">
										<input type="hidden" id="itemId"  name="itemVo.id" value="${vo.itemVo.id}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectItem()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">中文名称:</label></td>
								<td class="width-35">
									<input type="text" id="name" name="name" class="form-control required" validate="required"  value="${vo.name}" >
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">化学文摘号:</label></td>
								<td>
									<input type="text" id="cas" name="cas" class="form-control"  value="${vo.cas}" >
								</td>
								<td class="width-15 active"><label class="pull-right">英文名:</label></td>
								<td class="width-35">
									<input type="text" id="ename" name="ename" class="form-control"  value="${vo.ename}" >
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">MAC:</label></td>
								<td class="width-35">
									<input type="text" id="mac" name="mac" class="form-control number" value="${vo.mac}">
								</td>
								<td class="width-15 active"><label class="pull-right">PC-TWA:</label></td>
								<td class="width-35">
									<input type="text" id="pctwa" name="pctwa" class="form-control number" value="${vo.pctwa}">
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">PC-STEL:</label></td>
								<td class="width-35">
									<input type="text" id="pcstel" name="pcstel" class="form-control number" value="${vo.pcstel}">
								</td>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td class="width-35">
									<input type="text" id="sort" name="sort" class="form-control" value="${vo.sort}">
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td class="width-35" colspan="3">
									<textarea maxlength="128" rows="2" id="remark" name="remark" class="form-control">${vo.remark}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script>
		function fnSelectItem(){
			var id=$('#itemId').val();
			layer.open({
				title:'监测项目',	
				type: 2,
				area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '/init/item/select.do?sort=1&id='+id,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var data=iframeWin.fnSelect();
				  $('#itemName').val(data.name);
				  $('#name').val(data.name);
				  $('#itemId').val(data.id);
				}
			});
		}
	</script>
</body>
</html>
