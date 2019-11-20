<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>


<link href="${basePath}h/css/plugins/switchery/switchery.css"
	rel="stylesheet">
<link href="${basePath}h/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${basePath}h/css/plugins/summernote/summernote.css"
	rel="stylesheet">
<link href="${basePath}h/css/plugins/summernote/summernote-bs3.css"
	rel="stylesheet">


</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<div class="row diff-wrapper2">
					<div class="col-md-6" <c:if test="${config.msg.mail.isSend=='false' }">style="display: none;"</c:if>>
						<input id="mySwitch_mail" type="checkbox" class="js-switch" /> <label
							class="checkbox-inline i-checks">发送邮件</label>
					</div>
					
					<div  class="col-md-6"  <c:if test="${!config.msg.sms.isSend }">style="display: none;"</c:if>>
						<input id="mySwitch_sms" type="checkbox" class="js-switch_3" />
						<label class="checkbox-inline i-checks">发送短息</label>
					</div>
				</div>
				<div class="row diff-wrapper2">
					<div id="mail_div" class="col-md-12">
						<form id="mailform" method="post"  class="form-horizontal">
							<table id="mail"
								class="table table-bordered  table-condensed dataTables-example dataTable no-footer"
								style="display: none">
								<tbody>
									<tr>
										<td class="width-15 active"><label
											class="pull-right">收件人:</label></td>
										<td class="width-85"><input id="receiver"
											placeholder="收件人" class="form-control required" maxlength=64
											name="receiver" validate="required"  type="text" value="${mailData['receiver']} " /></td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">主题:</label></td>
										<td><input id="subject" name="subject"
											class="form-control " placeholder="主题" type="text" value="${mailData['subject']}" />
										</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">模块:</label></td>
										<td><input id="subject" name="busType"
											class="form-control " placeholder="模块" type="text" value="${mailData['busType']}" />
										</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">描述:</label></td>
										<td><input id="subject" name=busInfo
											class="form-control " placeholder="描述" type="text" value="${mailData['busInfo']}" />
										</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">内容:</label></td>
										<td>
											<!--  <button id="edit" class="btn btn-primary btn-xs m-l-sm" onclick="edits()" type="button">编辑</button>
                        <button id="save" class="btn btn-primary  btn-xs" onclick="saves()" type="button">保存</button>
                   		<div class="diff-wrapper2" id="eg"><div class="click2edit wrapper"> </div> </div> -->
											<div class="diff-wrapper2 no-padding">

												<div class="summernote">${mailData['content']}</div>

											</div>
											<input type="hidden" id="mailContent" name="content" value="${mailData['content']}">
										</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">备注:</label></td>
										<td><input maxlength="128" id="remark" name="remark"
											class="form-control " placeholder="备注" type="text" value="${mailData['remark']}" /></td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
					<div id="sms_div" class="col-md-12">
						<form id="smsform" method="post"  class="form-horizontal">
							<table id="sms"
								class="table table-bordered  table-condensed dataTables-example dataTable no-footer"
								style="display: none">
								<tbody>
									<tr>
										<td class="width-15 active" onclick="fntextssss();"><label
											class="pull-right">收件人:</label></td>
										<td class="width-85"><input id="receiver"
											placeholder="收件人" class="form-control required" maxlength=64
											name="receiver" validate="required mobile" type="text" value="${smsData['receiver']}" /></td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">模块:</label></td>
										<td><input id="busType" name="busType"
											class="form-control " placeholder="主题" type="text" value="${smsData['busType']}" />
										</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">描述:</label></td>
										<td><input id="busInfo" name="busInfo"
											class="form-control " placeholder="描述" type="text" value="${smsData['busInfo']}" />
										</td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">内容:</label></td>
										<td><textarea id="content" name="content"
												class="form-control diff-textarea " placeholder="内容"
												type="text" value="">${smsData['content']}</textarea></td>
									</tr>
									<tr>
										<td class="active"><label class="pull-right">备注:</label></td>
										<td><input maxlength="128" id="remark" name="remark"
											class="form-control " placeholder="备注" type="text" value="${smsData['remark']}" /></td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>
				<div class="hr-line-dashed"></div>
			</div>
		</div>
	</div>
<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
<script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${basePath}h/js/content.js?v=1.0.0"></script>
<script src="${basePath}h/js/plugins/iCheck/icheck.min.js"></script>
<script src="${basePath}h/js/plugins/layer/layer.min.js"></script>
<script src="${basePath}h/js/plugins/pace/pace.min.js"></script>
<script src="${basePath}h/js/plugins/treeview/bootstrap-treeview.js"></script>
<script src="${basePath}h/js/demo/treeview-demo.js" type="text/javascript"></script>
<script src="${basePath}h/js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
<script src="${basePath}h/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${basePath}h/js/plugins/validate/messages_zh.min.js"></script>
<script src="${basePath}h/js/plugins/blueimp/jquery.blueimp-gallery.min.js"></script>
<script src="${basePath}h/js/plugins/validate/ajaxValidate.js"></script>
<script src="${basePath}ext/js/ext.js" type="text/javascript"></script>
<script src="${basePath}ext/search_suggest/js/search-suggest.js" type="text/javascript"></script>
<!-- Switchery -->
<script src="${basePath}h/js/plugins/switchery/switchery.js"></script>
<script src="${basePath}h/js/plugins/cropper/cropper.min.js"></script>
<script src="${basePath}h/js/plugins/summernote/summernote.min.js"></script>
<script src="${basePath}h/js/plugins/summernote/summernote-zh-CN.js"></script>
	<script type="text/javascript">
      $(document).ready(function () {
    	  
    	  var elem = document.querySelector('.js-switch');
          var switchery = new Switchery(elem, {
              color: '#1AB394'
          });
    	  var elem_3 = document.querySelector('.js-switch_3');
          var switchery_3 = new Switchery(elem_3, {
              color: '#1AB394'
          });

      });
		$('#mySwitch_sms').on('change', function(e) {
			if ($(this).is(':checked') &&　$('#mySwitch_mail').is(':checked')) {
				$('#sms_div').attr("class", "col-md-6");
				$('#mail_div').attr("class", "col-md-6");
			} else {
				$('#sms_div').attr("class", "col-md-12");
				$('#mail_div').attr("class", "col-md-12");
			}
				$("#sms").toggle();
		});
		$('#mySwitch_mail').on('change', function(e) {
			if ($(this).is(':checked') &&　$('#mySwitch_sms').is(':checked')) {
				$('#sms_div').attr("class", "col-md-6");
				$('#mail_div').attr("class", "col-md-6");
			} else {
				$('#sms_div').attr("class", "col-md-12");
				$('#mail_div').attr("class", "col-md-12");
			}
				$("#mail").toggle();
		});
		var index = parent.layer.getFrameIndex(window.name); 
		function saveMessage(){
			var smsChecked = $('#mySwitch_sms').is(':checked');
			var mailChecked = $('#mySwitch_mail').is(':checked');
			if (smsChecked) {
				smsResult = saveSms();
			}
			if (mailChecked) {
				mailResult = saveMail();
			}
			if(smsChecked && !mailChecked){
				var icSms = "success"==smsResult.status ? 1:2;
				layer.msg(smsResult.message, {icon: icSms,time: 3000},function(){
					parent.layer.close(index);
				});
			}
			if(mailChecked && !smsChecked){
				var icMail = "success"==mailResult.status ? 1:2;
				layer.msg(mailResult.message, {icon: icMail,time: 3000},function(){
					parent.layer.close(index);
				});
			}
			if(mailChecked && smsChecked){
				var icSms = "success"==smsResult.status ? 1:2;
				var icMail = "success"==mailResult.status ? 1:2;
				layer.msg(mailResult.message, {icon: icMail,time: 3000},function(){
					layer.msg(smsResult.message, {icon: icSms,time: 3000},function(){
						parent.layer.close(index);
					});
				});
			}
			
		}
		function saveSms(){
			var result;
			var t = $("#smsform").FormValidate();
			if(t){
				$.ajax({
					type: "POST",  
					url: '/sys/sms/saveSms.do',  
					data: $("#smsform").serialize(), 
					async:false,
					success: function(data){
						 result = data;
					},
					error:function(ajaxobj){  
				    }  
				});
			}
			return result;
		}
		function saveMail(){
			var result;
			$("#mailContent").val($(".summernote").code());
			var t = $("#mailform").FormValidate();
			if(t){
				$.ajax({
					type: "POST",  
					url: '/sys/email/saveMail.do',  
					data: $("#mailform").serialize(), 
					async:false,
					success: function(data){
						result =  data;
					},
					error:function(ajaxobj){  
				    }  
				});
			}
			return result;
		}
	</script>
	<script type="text/javascript">
	 $(document).ready(function () {
         $('.summernote').summernote({
             lang: 'zh-CN',
             toolbar: [
				          ['style', ['style']],
				          ['font', ['bold', 'underline', 'clear']],
				          ['fontname', ['fontname']],
				          ['color', ['color']],
				          ['para', ['ul', 'ol', 'paragraph']],
				          ['table', ['table']]
				        ],
				height: "100px",
             focus: true
         });

     });
     var edits = function () {
         $("#eg").addClass("no-padding");
         $('.click2edit').summernote({
             lang: 'zh-CN',
             focus: true
         });
     };
     var saves = function () {
         $("#eg").removeClass("no-padding");
         var aHTML = $('.click2edit').code(); //save HTML If you need(aHTML: array).
         $('.click2edit').destroy();
     };
	</script>
</body>
</html>
