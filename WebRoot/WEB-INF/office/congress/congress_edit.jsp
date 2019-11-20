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
					<li><a>会务申请</a></li>
					<li><strong>编辑</strong>
					</li>
				</ol>
			</div>
			<div class="ibox-content" style="padding: 5px 10px;">
				<form method="post" action="" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" id="id" value="${vo.id}" type="hidden" />
					</c:if>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>会<br>议<br>内<br>容</label></td>
								<td class="width-15 active"><label class="pull-right">会议名称:</label></td>
								<td class="width-35">
								    <input  id="name" name="name" class="form-control required" validate="required" type="text" value="${vo.name}" />
								</td>
								<td class="width-15 active"><label class="pull-right">会议地点:</label></td>
								<td class="width-35">
									<input  id="address" name="address" class="form-control required" validate="required" type="text" value="${vo.address}" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">主&nbsp;&nbsp;讲&nbsp;人:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="zjr" name="zjr" class="form-control required" validate="required" placeholder="请选择" value="${vo.zjr}" onclick="fnSelectOneUser()"/> 
										<input type="hidden" id="zjrId" name="zjrId" value="${vo.zjrId}"/>
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectOneUser()">选择</button>
										</div>
									 </div>
								</td>
								<td class="width-15 active"><label class="pull-right">参与人员:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="users" name="users" class="form-control required" validate="required"  placeholder="请选择" value="${vo.users}" onclick="fnSelectUser()"/> 
										<input type="hidden" id="userIds" name="userIds" value="${vo.userIds}"/>
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									 </div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">会议内容:</label></td>
								<td colspan="3">
								   <textarea rows="3" maxlength="128"  class="form-control" id="content" placeholder="工作内容" name="content">${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开始时间:</label></td>
								<td class="width-35">
								 	<div class="input-group date" >
										<input id="beginTime" name="beginTime"  class="form-control  datetimeISO"  type="text" value="${vo.beginTime }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div> 
								</td>
								<td class="width-15 active"><label class="pull-right">结束时间:</label></td>
								<td class="width-35">
									<div class="input-group date" >
										<input id="endTime" name="endTime"  class="form-control datetimeISO"  type="text" value="${vo.endTime }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active" rowspan="2" style="text-align: center;width: 50px;"><label>会<br>议<br>物<br>资</label></td>
								<td class="width-15 active"><label class="pull-right">使用车辆:</label></td>
								<td class="width-35">									
									<div class="input-group">
										<input type="text" id="carNames" name="carNames" class="form-control"  placeholder="请选择" value="${vo.carNames}" onclick="fnSelectCar()"/> 
										<input type="hidden" id="carIds" name="carIds" value="${vo.carIds}"/>
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectCar()">选择</button>
										</div>
									 </div>
								<%-- 	 		<div class="input-group" style="width: 100%">
											<div class="input-group-btn">
												<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectCar('${s.index}')">选择</button>
											</div>
											<input type="hidden" id="carIds${s.index}" name="itemList[${s.index}].carIds" value="${e.carIds}">
											<input type="text" id="carNames${s.index}" name="itemList[${s.index}].carNames" class="form-control required" validate="required" placeholder="车辆" value="${e.carNames}" onclick="fnSelectCar('${s.index}')">
											<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span>
										</div> --%>
								</td>
								<td class="width-15 active"><label class="pull-right">会议预算:</label></td>
								<td class="width-35">
									<input  id="ysfee" name="ysfee" class="form-control number" maxlength="128" type="text" value="${vo.ysfee}" />
								</td>
							</tr>
							<tr>
							    <td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							    <td colspan="3">
							    	<textarea maxlength="128" rows="3" class="form-control" id="qtRemark" placeholder="备注" name="qtRemark">${vo.qtRemark}</textarea>
							    </td>
							</tr>
							<tr>
							<td class="active" style="text-align: center;width: 50px;"></td>
							 <td class="width-15 active"><label class="pull-right">申&nbsp;&nbsp;请&nbsp;人:</label></td>
								<td class="width-35">
										<input id="sqr" name="sqr"  class="form-control"  type="text" value="${vo.sqr }" />
										<input type="hidden" id="sqrId" name="sqrId" value="${vo.sqrId }" >
								</td>
								<td class="width-15 active"><label class="pull-right">申请日期:</label></td>
								<td class="width-35">
									<div class="input-group date" >
										<input id="supportDate" name="supportDate"  class="form-control dateISO"  type="text" value="${vo.supportDate }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
	                <div class="form-group">
	                    <div class="col-sm-12 col-sm-offset-1">
	                        <a class="btn btn-w-m btn-primary" href="javascript:void(0);"
	                           onclick="formSubmitSave('save4Data.do?isCommit=0');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
	                        <a class="btn btn-w-m btn-success" href="javascript:void(0);"
	                           onclick="formSubmit('${fn:length(vo.id)>0? 'update4Data.do?isCommit=1':'add4Data.do?isCommit=1'}');"><i
	                                class="fa fa-mail-forward" aria-hidden="true"></i> 保存并提交</a>
	                        <a class="btn btn-w-m btn-white" href="javascript:backMainPage();">
	                        <i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
	                    </div>
	                </div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
        <!-- Input Mask-->
    <script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
	<script>
	function fnSelectUser() {
		layer.open({
			title:'人员信息',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/selects.do',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var selectData = iframeWin.fnSelect();
			  $("#users").val(selectData.name);
			  $("#userIds").val(selectData.id);
			}
		})
	}
	function fnSelectOneUser() {
		layer.open({
			title:'人员信息',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/select.do',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var selectData = iframeWin.fnSelect();
			  $("#zjr").val(selectData.name);
			  $("#zjrId").val(selectData.id);
			}
		})
	}
	
	
	
	
	function fnSelectCar() {
		layer.open({
			title:'车辆使用信息',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/car/showUseCars.do',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var selectData = iframeWin.fnSelect();
			  $("#carNames").val(selectData.name);
			  $("#carIds").val(selectData.id);
			}
		})
	}
	
	
	function formSubmitSave(url){//保存
		
		$('#thisForm').attr('action',url);
		$('#thisForm').submit();
	}
	function formSubmit(url){//保存并提交
		$('#thisForm').attr('action',url);
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
	
	function backMainPage(){
		 var basePath=location.href.substr(0,location.href.lastIndexOf("/")+1);
		 var currentIframe=$(window.parent.document).find(".J_iframe").filter( ":visible" );
		 var editPageKey=$(currentIframe).attr("data-id");
		 var mainPageKey=window.parent.pageMap.get(editPageKey);
		 window.parent.pageMap.remove(editPageKey);//移除map缓存
		 var mainIframe=$(window.parent.document).find('iframe[data-id="'+mainPageKey+'"]')[0];
		 $(mainIframe).attr("data-id",editPageKey);
		 $(mainIframe).css("display","inline");
		 var treeUrl=mainIframe.contentWindow.treeUrl;
		 if(!!treeUrl&&treeUrl!='undefined'){
			 mainIframe.contentWindow.reloadTree(basePath+treeUrl);
		 }
		 mainIframe.contentWindow.jqgridReload(); 
		 currentIframe.remove();
	}
	
	</script>
</body>
</html>