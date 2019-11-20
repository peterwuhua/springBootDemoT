<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div style="display: block;" class="fixed-table-pagination">
	<input type="hidden" name="pageBean.currentPage" id="pageNumber" value="${pageResult.pageBean.currentPage}" />
	<input type="hidden" name="pageBean.pageSize" id="pageSize" value="${pageResult.pageBean.pageSize}" />
	<input type="hidden" name="order" id="order" value="${pageResult.order}" />
	<input type="hidden" name="orderColumn" id="orderColumn" value="${pageResult.orderColumn}" />
	<div class="pull-left pagination-detail">
	<span class="pagination-info">记录总数: ${pageResult.pageBean.totalRows}条，共${pageResult.pageBean.totalPages}页 </span>
	<span class="page-list">每页显示 
		<span class="btn-group dropup">
			<button aria-expanded="false" data-toggle="dropdown" class="btn btn-default  btn-outline dropdown-toggle" type="button">
				<span class="page-size">${pageResult.pageBean.pageSize}</span> 
			<span class="caret"></span>
		</button>
			<ul role="menu" class="dropdown-menu">
				<li <c:if test="${pageResult.pageBean.pageSize==15}">class="active"</c:if>><a href="javascript:page(1,15);">15</a></li>
				<li <c:if test="${pageResult.pageBean.pageSize==50}">class="active"</c:if>><a href="javascript:page(1,50);">50</a></li>
				<li <c:if test="${pageResult.pageBean.pageSize==100}">class="active"</c:if>><a href="javascript:page(1,100);">100</a></li>
				<li <c:if test="${pageResult.pageBean.pageSize==999}">class="active"</c:if>><a href="javascript:page(1,999);">999</a></li>
			</ul></span> 条记录</span>
	</div>
	<div class="pull-right pagination-roll">
		<ul class="pagination pagination-outline">
			<li class="paginate_button previous <c:if test="${pageResult.pageBean.currentPage<=1}">disabled</c:if>"><a href="javascript:page(1,${pageResult.pageBean.pageSize});"><i class="fa fa-angle-double-left"></i></a></li>
			<li class="paginate_button previous <c:if test="${pageResult.pageBean.currentPage<=1}">disabled</c:if>"><a href="javascript:page(${pageResult.pageBean.currentPage - 1},${pageResult.pageBean.pageSize});"><i class="fa fa-angle-left"></i></a></li>
			<li class="paginate_button active"><a href="javascript:">${pageResult.pageBean.currentPage}</a></li>
			<li class="paginate_button next <c:if test="${pageResult.pageBean.currentPage+1>=pageResult.pageBean.totalPages}">disabled</c:if>"><a href="javascript:page(${pageResult.pageBean.currentPage + 1},${pageResult.pageBean.pageSize});"><i class="fa fa-angle-right"></i></a></li>
			<li class="paginate_button next <c:if test="${pageResult.pageBean.currentPage>=pageResult.pageBean.totalPages}">disabled</c:if>"><a href="javascript:page(${pageResult.pageBean.totalPages},${pageResult.pageBean.pageSize});"><i class="fa fa-angle-double-right"></i></a></li>
		</ul>
	</div>
	<div class="row"></div>
</div>