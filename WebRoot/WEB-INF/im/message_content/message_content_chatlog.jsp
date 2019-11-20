<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
    <title>聊天记录</title>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
	<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style>
body .layim-chat-main{height: auto;}
</style>
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
	<div class="layim-chat-main">
  <ul id="LAY_view"></ul>
</div>
<form action="chatLogPage.do" method="post" id="listForm">
	<%@ include file="../../include/im_page.jsp"%>
	<input type="hidden" name="toId" value="${vo.toId}"/>
</form>
<div id="LAY_page" style="margin: 0 10px;"></div>

<textarea title="消息模版" id="LAY_tpl" style="display:none;">
{{# layui.each(d.data, function(index, item){
  if(item.id == parent.layui.layim.cache().mine.id){ }}
    <li class="layim-chat-mine"><div class="layim-chat-user"><img src="{{ item.avatar }}"><cite><i>{{ layui.data.date(item.timestamp) }}</i>{{ item.username }}</cite></div><div class="layim-chat-text">{{ layui.layim.content(item.content) }}</div></li>
  {{# } else { }}
    <li><div class="layim-chat-user"><img src="{{ item.avatar }}"><cite>{{ item.username }}<i>{{ layui.data.date(item.timestamp) }}</i></cite></div><div class="layim-chat-text">{{ layui.layim.content(item.content) }}</div></li>
  {{# }
}); }}
</textarea>

<!-- 
上述模版采用了 laytpl 语法，不了解的同学可以去看下文档：http://www.layui.com/doc/modules/laytpl.html

-->

<%@ include file="../../include/js.jsp"%>
<script src="${basePath}ext/layui/layui.js"></script>
<script>
layui.use(['layim', 'laypage'], function(){
  var layim = layui.layim
  ,layer = layui.layer
  ,laytpl = layui.laytpl
  ,$ = layui.jquery
  ,laypage = layui.laypage;
  //聊天记录的分页此处不做演示，你可以采用laypage，不了解的同学见文档：http://www.layui.com/doc/modules/laypage.html
  //开始请求聊天记录
  var param =  location.search; //获得URL参数。该窗口url会携带会话id和type，他们是你请求聊天记录的重要凭据
  console.log(param);
  //实际使用时，下述的res一般是通过Ajax获得，而此处仅仅只是演示数据格式
	  /* var toId = '${vo.id}';
	  var type = '${vo.type}'; */
	  var s =eval('${data}');
	  var res = s[0];
	 /*  $.ajax({
			url:'${basePath}im/messageContent/chatMessage.do',
			dataType:"json",
			data:{"toId":toId,"type":type},
			type:"post",
			async:false,
			success: function(data){
				console.log(data);
				res = data;
				console.log(res);
			},
			error:function(ajaxobj){
		    }  
		}); */
		laypage({
		    cont: 'LAY_page'
		    ,pages: '${pageResult.pageBean.totalPages}'//分页总数
		    ,groups:2//连续分页数
		    ,curr:'${pageResult.pageBean.currentPage}'//当前页
		    ,skip: false
		    ,jump: function(obj, first){
	          var curr = obj.curr;
          		if(!first){
	        	 	page(curr,'${pageResult.pageBean.pageSize}'); 
          		}
	        }
	  });
  var html = laytpl(LAY_tpl.value).render({
    data: res.data
  });
  $('#LAY_view').html(html);
  
});
</script>
</body>

</html>
