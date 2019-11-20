<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tld.jsp"%>
<nav class="navbar-default navbar-static-side" role="navigation">
	<div class="nav-close">
		<i class="fa fa-times-circle"></i>
	</div>
	<div class="sidebar-collapse">
		<ul class="nav" id="side-menu">
			<li class="site-menu-category" style="font-size: 24px;font-family: 华文新魏;font-style: italic;text-decoration: underline;">
				<i style="width: 20px;" class="${e.functionVo.imageUrl}"></i>${current.sysName}
			</li>
			<c:forEach items="${menuList}" var="e" varStatus="s">
				<li>
					<c:choose>
						<c:when test="${fn:length(e.list)>0}">
							<a href="#"> 
								<i style="width: 20px;" class="${e.functionVo.imageUrl}"></i> 
								<span class="nav-label">${e.functionVo.name }</span> <span class="fa arrow"></span>
							</a>
							<ul class="nav nav-second-level">
								<c:forEach items="${e.list}" var="e1" varStatus="s1">
									<li>
										<c:choose>
											<c:when test="${fn:length(e1.list)>0}">
												<a href="#">${e1.functionVo.name } <span class="fa arrow"></span></a>
												<ul class="nav nav-third-level">
													<c:forEach items="${e1.list}" var="e2" varStatus="s2">
														<li><a class="J_menuItem" href="${e2.functionVo.url }">${e2.functionVo.name }</a></li>
													</c:forEach>
												</ul>
											</c:when>
											<c:otherwise>
												<a class="J_menuItem" href="${e1.functionVo.url }">${e1.functionVo.name }</a>
											</c:otherwise>
										</c:choose>
									</li>
								</c:forEach>
							</ul>
						</c:when>
						<c:otherwise>
							<a class="J_menuItem" href="${e.functionVo.url }">
								<i style="width: 20px;" class="${e.functionVo.imageUrl}"></i> 
								<span class="nav-label">${e.functionVo.name }</span>
							</a>
						</c:otherwise>
					</c:choose>
				</li>
			</c:forEach>
		</ul>
	</div>
</nav>
