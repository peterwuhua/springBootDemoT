<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>
						<ol class="breadcrumb">
							<li><a href="gridPage.do">权限</a></li>
							<li><strong>编辑</strong></li>
						</ol>
					</h5>
					<div class="ibox-tools">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
					</div>
				</div>
				<div class="ibox-content">
					<%@ include file="../../include/status.jsp"%>
					<form method="post" action="${fn:length(vo.id)>0? 'update.do':'add.do'}" class="form-horizontal" name="form" onsubmit="return checkPermCode(form.code.value);">
                    <c:if test="${fn:length(vo.id)>0}">
                    	<input name="id" value="${vo.id}" type="hidden" />
                    </c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">功能:</label></th>
								<td class="width-35">
									<div class="input-group">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect('${vo.functionVo.id}','${vo.functionVo.name}')">选择</button>
										</div>
										<input type="text" id="selectName" class="form-control " placeholder="请选择功能" value="${vo.functionVo.name}">
										<input type="hidden" id="selectId"  name="functionVo.id" value="${vo.functionVo.id}">
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">名称:</label></td>
								<td><input id="name" name="name"
									class="form-control required" maxlength=64 placeholder="名称" type="text" value="${vo.name }" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">编码:</label></td>
								<td><input id="code" name="code"
									class="form-control required" maxlength=64 placeholder="编码" type="text" value="${vo.code }" />
								</td>
								<td class="active"><label class="pull-right">排序:</label></td>
								<td>
								<input id="describtion" name="sort"
									class="form-control " placeholder="排序" type="text" value="${vo.sort }" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">说明:</label></td>
								<td>
								<input id="sort" name="describtion"
									class="form-control " placeholder="说明" type="text" value="${vo.describtion }" />
								</td>
								<td></td><td></td>
							</tr>
						</tbody>	
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-4 col-sm-offset-2">
							<button class="btn btn-primary" type="button" onclick="formSubmit('save.do');">保存</button>
							<button class="btn btn-primary" type="submit">保存并返回</button>
							<a href="gridPage.do?funId=${vo.functionVo.id }" class="btn btn-white"  data-placement="top" data-toggle="tooltip" data-original-title="111">返回</a>
						</div>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	 <script>
        $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
    </script>
    
    <script>
		function fnSelect(pId,pName){
				layer.open({
					title:'功能选择',	
					type: 2,
					area: ['350px', '75%'],
					fix: false, //不固定
					maxmin: true,
					content: '${basePath}sys/function/select.do?parentVo.id='+pId+'&parentVo.name='+encodeURI(pName),
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
		function checkPermCode(code){
			var id = '${vo.id}';
			var flag = false;
			$.ajax({
				url:'${basePath}sys/permission/checkPermCode.do',
				dataType:"json",
				data:{"id":id,"code":code},
				type:"post",
				async:false,
				success: function(data){
					if("error" == data.type){
						layer.alert(data.message, {icon: 2,shadeClose: true});
					}
					if("success" == data.type){
						flag =true;
					}
				},
				error:function(ajaxobj){
			    }  
			});
			return flag;
		}
</script>
</body>
</html>
