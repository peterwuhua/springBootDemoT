<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>


<!-- Mirrored from www.zi-han.net/theme/hplus/mail_detail.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:19:09 GMT -->
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<style type="text/css">
.col-sm-10 {
	padding-right: 5px;
	padding-left: 2px;
}
</style>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-2">
				<jsp:include page="message_left.jsp" />
			</div>
			<div class="col-sm-10 animated fadeInRight">
				<div class="mail-box-header">
					<form method="post" action="detail.do" class="pull-right mail-search">
						<input type="hidden" id="ids" name="ids" />
					</form>
					<div class="pull-right tooltip-demo">
						<a href="messageAnswer.do?id=${vo.messageVo.id}" class="btn  btn-sm btn-primary" data-toggle="tooltip" data-placement="top" title="回复"><i class="fa fa-reply"></i> 回复</a> <a title="" data-placement="top" data-toggle="tooltip" data-original-title="删除邮件" onclick="fnDrop('${vo.id}')" class="btn btn-sm btn-danger"><i class="fa fa-trash-o"></i> 删除</a> <a href="javascript:;" onclick="fnBack()" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="返回"><i class="fa fa-undo"></i>返回</a>
					</div>
					<h2>查看邮件</h2>
					<div class="mail-tools tooltip-demo m-t-md">
						<h3>
							<span class="font-noraml">主题： </span>${vo.messageVo.subject }
						</h3>
						<h5>
							<span class="pull-right font-noraml">${vo.messageVo.sendTime }</span> <span class="font-noraml">发件人： </span>${vo.messageVo.senderVo.userVo.name }【${vo.messageVo.senderVo.loginName}】
						</h5>
						<p>
                    		<span class="font-noraml">附&nbsp;&nbsp;件： </span>
                    	 	<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" >${e.fileName}</a>
							 </c:forEach>
                    	</p>
					</div>
				</div>
				<div class="mail-box">


					<div class="mail-body">
						${vo.messageVo.content }
						<p class="text-right"></p>
					</div>
					<div class="mail-body text-right tooltip-demo">
						<a class="btn btn-sm btn-primary" href="messageAnswer.do?id=${vo.messageVo.id}"><i class="fa fa-reply"></i> 回复</a> <a title="" data-placement="top" data-toggle="tooltip" data-original-title="删除邮件" onclick="fnDrop('${vo.id}')" class="btn btn-sm btn-danger"><i class="fa fa-trash-o"></i> 删除</a> <a href="javascript:;" onclick="fnBack()" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="返回"><i class="fa fa-undo"></i>返回</a>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
	<script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${basePath}h/js/content.js?v=1.0.0"></script>
	<script src="${basePath}h/js/plugins/iCheck/icheck.min.js"></script>
	<%@ include file="../../include/js.jsp"%>
	<script>
        $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
    </script>
	<script>
    function fnDrop(id){
    	var flag = '${param.flag}';
		layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
			var url = "drop.do?from="+flag+"&ids="+id+"&flag="+flag;
			/* $("#ids").val(id);
			$('form').attr('action','drop.do?from=detail');
			$('form').submit(); */
			window.location.href=url;
		});
    }
    function fnSubmit(t){
		$("#ids").val(getSelectIds());
		$('form').attr('action','updatePosition.do?from=inbox&position='+t);
		$('form').submit();
    }
    function fnBack(){
    	var flag = '${param.flag}';
    	if(flag=="trash"){
			location.href='trash.do';
    	}else if(flag=="important"){
    		location.href='important.do';
    	}else if(flag=="inbox"){
    		location.href='inbox.do';
    	}else{
    		location.href='inbox.do';
    	}
    	
	}
</script>
</body>

<!-- Mirrored from www.zi-han.net/theme/hplus/mail_detail.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:19:10 GMT -->
</html>
