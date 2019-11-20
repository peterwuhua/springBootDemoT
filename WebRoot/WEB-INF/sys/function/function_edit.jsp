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
						<li><a href="javascript:backMainPage();">功能</a></li>
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
								<th class="width-15 active"><label class="pull-right">上级功能:</label></th>
								<td class="width-35">
									<div class="input-group">
                                        <input type="text" id="selectName" class="form-control required" validate="required" placeholder="请选择上功能" value="${vo.parentVo.name}"  onclick="fnSelectFunction('${vo.parentVo.id}','${vo.parentVo.name}')">
                                        <input type="hidden" id="selectId" name="parentVo.id" value="${vo.parentVo.id}">
                                         <div class="input-group-btn">
                                            <button tabindex="-1" class="btn btn-info" type="button" onclick="fnSelectFunction('${vo.parentVo.id}','${vo.parentVo.name}')">选择</button>
                                        </div>
                                    </div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</label></td>
								<td class="width-35"><input id="name" placeholder="功能名称"  class="form-control required" validate="required" maxlength=64 name="name" type="text" value="${vo.name}"/></td>
								<td class="active"><label class="pull-right">uri:</label></td>
								<td ><input id="url" name="url"
									class="form-control" placeholder="uri" type="text" value="${vo.url }"/>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">功能编码:</label></td>
								<td ><input id="code" name="code"
									class="form-control" placeholder="功能编码" type="text" value="${vo.code }"/>
								</td>
								<td class="active"><label class="pull-right">流程编码:</label></td>
								<td ><input id="wfCode" name="wfCode" class="form-control " placeholder="流程编码" type="text" value="${vo.wfCode }" /> </td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">ICON:</label></td>
								<td><input id="imageUrl" name="imageUrl"
									class="form-control " placeholder="一级菜单小图标" type="text" value="${vo.imageUrl }" />
								</td>
								<td class="active"><label class="pull-right">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序:</label></td>
								<td><input id="sort" name="sort"
									class="form-control " placeholder="排序" type="text" value="${vo.sort }" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">说&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;明:</label></td>
									<td><input id="describtion" name="describtion"
										class="form-control " placeholder="说明" type="text" value="${vo.describtion }" />
								</td>
								<td></td><td></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					  <div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</button>
							<a href="javascript:backMainPage();" class="btn btn-w-m btn-white" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
					</form>
				</div>
			</div>
		</div>
	<%@ include file="../../include/js.jsp"%>
</body>
<script>
function fnSelectFunction(pId,pName){
	layer.open({
		title:'功能选择',	
		type: 2,
		area: ['350px', '75%'],
		fix: false, //不固定
		maxmin: true,
		content: 'select.do?parentVo.id='+pId+'&parentVo.name='+encodeURI(pName),
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			  //var body = layer.getChildFrame('body', index);
			  //body.fnSelect();
			  //body.find('.btn-primary').click();
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  iframeWin.fnSelect();
			}
	});
}
</script>
</html>
