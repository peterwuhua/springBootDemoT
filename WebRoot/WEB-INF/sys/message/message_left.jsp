<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="ibox float-e-margins">
	<div class="ibox-content mailbox-content">
		<div class="file-manager">
			<a class="btn btn-block btn-primary compose-mail" href="edit.do">写信</a>
			<div class="space-25"></div>
			<ul class="folder-list m-b-md" style="padding: 0">
				<li><a href="inbox.do" id="inbox"><i class="fa fa-inbox"></i>收件箱 <span class="label label-warning pull-right">${messageVo.sj}</span></a></li>
				<li><a href="sentbox.do" id="sentbox"> <i class="fa fa-envelope-o"></i>已发送 <span class="label label-warning pull-right">${messageVo.fj}</span></a></li>
				<li><a href="important.do" id="important"> <i class="fa fa-certificate"></i>重要 <span class="label label-warning pull-right">${messageVo.zy}</span></a></li>
				<li><a href="draft.do" id="draft"> <i class="fa fa-file-text-o"></i>草稿箱 <span class="label label-warning pull-right">${messageVo.cg}</span></a></li>
				<li><a href="trash.do" id="trash"> <i class="fa fa-trash-o"></i>垃圾箱 <span class="label label-warning pull-right">${messageVo.lj}</span></a></li>
			</ul>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
		onload = function (){
			var flag = '${param.flag}';
			if (flag.length>0) {
				var obj = document.getElementById(flag);
				obj.setAttribute('style','color:#f60;font-weight:bold;');
			}
		};
</script>
