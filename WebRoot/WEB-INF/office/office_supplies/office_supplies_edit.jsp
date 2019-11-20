<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">办公用品采购</a></li>
					<li><strong>编辑</strong>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					 <input name="id" value="${vo.id}" type="hidden" />
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">员工编号:</label></td>
								<td class="width-35">
									<input id="ygbh" name="ygbh" class="form-control "  type="text" value="${vo.ygbh}" disabled="disabled" />
								</td>
								<td class="width-15 active"><label class="pull-right">员工名称:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="ygmc" name="ygmc" class="form-control required" validate="required" placeholder="请选择" value="${vo.ygmc}" onclick="fnSelectOneUser()"/> 
										<input type="hidden" id="ygId" name="ygId" value="${vo.ygId}"/>
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectOneUser()">选择</button>
										</div>
									 </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label></td>
								<td>
									<input id="deptname" name="deptname" class="form-control " type="text" value="${vo.deptname}" />
								</td>
								<td class="active"><label class="pull-right">申请日期:</label></td>
								<td>
									<div class="input-group date">
		                           		<input id="sqDate" name="sqDate" class="form-control required dateISO" validate="required" type="text" value="${vo.sqDate}" />
		                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">总&nbsp;&nbsp;价&nbsp;格:</label></td>
								<td>
									<select name="zj" id="zj" class="form-control required" validate="required">
										<option value="">请选择</option>
										<c:forEach items="${priceList}" var="e" varStatus="s">
											<c:choose>
												<c:when test="${e==vo.zj}">
													<option value="${e}" selected="selected">${e}</option>
												</c:when>
												<c:otherwise>
													<option value="${e}">${e}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
								<td class="active"><label class="pull-right">描述内容:</label></td>
								<td colspan="3">
									<textarea maxlength="128" rows="2" class="form-control" name="content" id="content" placeholder="描述内容">${vo.content }</textarea>
								</td>
							</tr>
							<tr>
							<td colspan="5">
								办公用品信息
								<div  style="float: right;">
									<button onclick="addRow('')" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新增一行</button>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="5">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th width="30">序号</th>
											<th width="110">办公用品名称</th>
											<th width="70">数量</th>
											<th width="70" >计量单位</th>
											<th width="150" >备注</th>
											<th width="50">
											</th>
										</tr>
									</thead>
									<tbody id="point_tb">
										<c:forEach items="${vo.ypList}" var="e" varStatus="v">
											<tr key="${v.index}" >
												<td width="30">
													<input name="ypList[${v.index}].sort" value="${e.sort}" type="text"  class="form-control digits required" validate="required"/>
													<input name="ypList[${v.index}].id" value="${e.id}" type="hidden" />
												</td>
												<td width="150">
													 <input id="bgname_${v.index}" name="ypList[${v.index}].bgname" value="${e.bgname}" type="text"  class="form-control required" validate="required"/>
												</td>
												<td width="100">
													<div class="input-group" style="width: 100%">
														<input id="sl_${v.index}" name="ypList[${v.index}].sl" value="${e.sl}" type="text"  class="form-control required" validate="required"/>
														<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span>
													</div>
												</td>
												<td width="80">
													<div class="input-group" style="width: 100%">
														<input id="dw_${v.index}" name="ypList[${v.index}].dw" value="${e.dw}" type="text"  class="form-control required" validate="required"/>
														<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span>
													</div>
												</td>
												<td width="100">
													<div class="input-group" style="width: 100%">
															<textarea id="remark_${v.index}" name="ypList[${v.index}].remark" class="form-control " maxlength="128" >${e.remark}</textarea>
															<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span>
													</div>
												</td>
												<td width="50" align="center">
													<a class="btn btn-xs btn-outline btn-danger"  href="javascript:;" onclick="removeTr(this);"><i class="fa fa-times"></i></a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitSave('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('${fn:length(vo.id)>0? 'updateData.do?isCommit=1':'addData.do?isCommit=1'}');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
		   <!-- Sweet alert -->
    <script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script>
	function formSubmit(url){
		$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
			swal({
		        title: "您确定要提交吗",
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
	   		});
		}
	}
  //新增一行
	function addRow(data){
		var l=$('#point_tb').find('tr').length;
		var index=0;
		if(l>0){
			index=parseInt($('#point_tb').find('tr').eq(l-1).attr('key'));
			index++;
		}
		var bgname='';
		var sl='';
		var dw='';
		var price='';
		var remark='';
		if(data!=''){
			bgname=data.bgname;
			sl=data.sl;
			dw=data.dw;
			price=data.price;
			remark=data.remark;
		} 
		$('#point_tb').append($('<tr key="'+index+'">')
				.append('<td width="30" ><input name="ypList['+index+'].sort" value="'+(index+1)+'" type="text"  class="form-control digits required" validate="required"/></td>')
				.append('<td width="150"><input id="bgname_'+index+'" name="ypList['+index+'].bgname" value="'+bgname+'" class="form-control required" validate="required"  /></td>')
				.append('<td width="100"><div class="input-group" style="width: 100%"><input id="sl_'+index+'" name="ypList['+index+'].sl" value="'+sl+'" type="text"  class="form-control required" validate="required" /><span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span></div></td>')
				.append('<td width="80"><div class="input-group" style="width: 100%"><input id="dw_'+index+'" name="ypList['+index+'].dw" value="'+dw+'" type="text"  class="form-control required" validate="required"/><span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span></div></td>')
				.append('<td width="100"><div class="input-group" style="width: 100%"><textarea maxlength="128" id="remark_'+index+'" name="ypList['+index+'].remark" class="form-control required" validate="required" >'+remark+'</textarea><span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span></div></td>')
				.append('<td width="50" align="center"><a class="btn btn-xs btn-outline btn-danger"  href="javascript:;"  onclick="removeTr(this);"><i class="fa fa-times"></i></a></td>'));
	}
   //删除某一行
	function removeTr(obj){
		$(obj).closest('tr').remove();
	}
  
	//复制
	function copyItem(obj){
		obj=$(obj);
		var idValue=obj.closest('td').find('input').val();
		var textValue=obj.closest('td').find('textarea').val();
		var indexTr=obj.closest('td').closest('tr').index();
		var indexTd=obj.closest('td').index();
		$('#point_tb tr:gt('+indexTr+')').each(function(){
				$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
				$(this).find('td').eq(indexTd).find('textarea').eq(0).val(textValue);
		});
	}
	
	function fnSelectOneUser() {
		layer.open({
			title:'人员信息',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/select2.do',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var selectData = iframeWin.fnSelect();
			  $("#ygmc").val(selectData.name);
			  $("#ygId").val(selectData.id);
			  $("#deptname").val(selectData.deptname); 
			  $("#ygbh").val(selectData.ygbh); 
			}
		})
	}
	</script>
</body>
</html>
