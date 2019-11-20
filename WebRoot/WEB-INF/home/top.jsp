<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tld.jsp"%>
<style>
#admui-navbarSubMenu{
	width: 40px;	
}
ul.dropdown-menu{
	min-width: 130px !important;
}
.dropdown-menu li a i {
    color: #333;
}
.dropdown-menu li a:hover i{
    color: #fff;
}
</style>
<nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
	<div class="navbar-header">
          <div class="navbar-brand navbar-brand-center site-gridmenu-toggle">
          	<a class="dropdown-toggle count-info" href="main.do?sysId=402881945ffb950d015ffb9a729c0000">
	        	<img style="width:220px;height:60px;" alt="image" class="navbar-logo" src="${basePath}static/upload/avatar/logo.png" />
            	<img style="width:70px;height:60px;" alt="image" class="navbar-logo-mini" src="${basePath}static/upload/avatar/logo_mini.png" />
	        </a>
             
          </div>
     </div>
     <div class="navbar-container">
     	<div class="collapse navbar-collapse navbar-collapse-toolbar">
	     	<ul class="nav navbar-top-links navbar-left">
	     		<li style="width: 40px;">
	     			<a class="navbar-minimalize" href="javascript:;">
	     				<i class="fa fa-bars"></i>
	     			</a>
	     		</li>
	     		<c:forEach items="${sysList}" var="e" varStatus="v">
	     			<li>
	            	 	<a class="dropdown-toggle count-info" href="${e.url}">
	                		<i style="width: 20px;" class="${e.imageUrl}"></i> ${e.name}
	             		</a>
	     			</li>
	     		</c:forEach>
	     		<c:if test="${fn:length(subSysList)>0}">
					<li class="dropdown" id="admui-navbarSubMenu">
	                     <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
	                         <i class="fa fa-ellipsis-v" aria-hidden="true"></i>
	                     </a>
	                     <ul class="dropdown-menu">
	                     	<c:forEach items="${subSysList}" var="e1" varStatus="s1">
								 <li>
		                             <a class="dropdown-toggle count-info" href="${e1.url }">
		                                 <i class="${e1.imageUrl}"></i><span> ${e1.name }</span>
		                             </a>
		                         </li>
							</c:forEach>
	                     </ul>
	                </li>
				</c:if>
	     	</ul>
	     	 <ul class="nav navbar-top-links navbar-right">
		         <li class="dropdown" title="特殊字符">
		             <a class="count-info" href="javascript:;" onclick="fnSelectSpecial();">
		                 <i class="fa fa-info-circle"></i> <span class="label label-primary"></span>
		             </a>
		         </li>
		         <li class="dropdown hidden-xs" title="主题">
		             <a class="right-sidebar-toggle" aria-expanded="false">
		                 <i class="fa fa-tasks"></i> 主题
		             </a>
		         </li>
		         <li class="hidden-xs" title="退出">
		             <a href="logout.do">
		                 <i class="fa fa-sign-out"></i>退出
		             </a>
		         </li>
		     </ul>
     	</div>
     </div>
 </nav>
<script>

function fnSelectSpecial (){
	layer.open({
	  title:'特殊字符',	
	  type: 2,
	  area: ['600px', '400px'],
	  fix: false, //不固定
	  maxmin: true,
	  content:'${basePath}sys/code/select.do',
	  btn: ['取消']
	})
}
</script>

