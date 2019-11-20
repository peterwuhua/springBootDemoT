<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	  <%@ include file="../common/echarts.jsp"%>
</head>
<body class="gray-bg">
	 <div class="col-sm-3">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>统计参数</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
					<a class="close-link"> <i class="fa fa-times"></i></a>
				</div>
			</div>
			<div class="ibox-content">
				<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
					<tbody>
						<tr>
							<td class="width-50 active"><label class="pull-right">级别:</label></td>
							<td><input id="name" placeholder="级别"  class="form-control" name="name" type="text" value="${vo.name}"/></td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">客户经理:</label></td>
							<td><input id="name" placeholder="客户经理"  class="form-control" name="name" type="text" value="${vo.name}"/></td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">客户来源:</label></td>
							<td><input id="name" placeholder="客户来源"  class="form-control" name="name" type="text" value="${vo.name}"/></td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">客户行业:</label></td>
							<td><input id="name" placeholder="客户行业"  class="form-control" name="name" type="text" value="${vo.name}"/></td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">类型:</label></td>
							<td><input id="name" placeholder="类型"  class="form-control" name="name" type="text" value="${vo.name}"/></td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">客户建立时间:</label></td>
							<td><input id="name" placeholder="客户建立时间"  class="form-control" name="name" type="text" value="${vo.name}"/></td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">区域:</label></td>
							<td><input id="name" placeholder="区域"  class="form-control" name="name" type="text" value="${vo.name}"/></td>
						</tr>
					</tbody>
				</table>
				<div class="form-group">
                     <div class="col-sm-offset-2">
                         <button class="btn btn-success" type="submit">确定</button>
                     </div>
                 </div>
			</div>
		</div>
	</div>
	<div class="col-sm-9">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>统计视图</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
					<a class="close-link"> <i class="fa fa-times"></i></a>
				</div>
			</div>
			<div class="ibox-content" >
				<div id="container" style="width:700px;height: 400px"></div>
			</div>
		</div>
	</div>
    </body>
<%@ include file="../../include/js.jsp"%>
   <script type="text/javascript">
var dom = document.getElementById("container");
var myChart = echarts.init(dom);
var app = {};
option = null;
app.title = '嵌套环形图';

option = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'left',
        data:['商超','电商','农贸市场','小作坊','生产企业','农贸市场','餐馆','学生餐厅','外卖','其他']
    },
    toolbox: {
        show: true,
        orient: 'vertical',
        left: 'right',
        top: 'center',
        feature: {
            dataView: {readOnly: false},
            restore: {},
            saveAsImage: {}
        }
    },
    series: [
        {
            name:'客户类型',
            type:'pie',
            selectedMode: 'single',
            radius: [0, '30%'],

            label: {
                normal: {
                    position: 'inner'
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[
                {value:335, name:'其它', selected:true},
                {value:679, name:'流通领域'},
                {value:1548, name:'生产流域'},
                {value:1548, name:'终端'}
            ]
        },
        {
            name:'访问来源',
            type:'pie',
            radius: ['40%', '55%'],

            data:[
                {value:335, name:'商超'},
                {value:310, name:'电商'},
                {value:234, name:'农贸市场'},
                {value:135, name:'小作坊'},
                {value:408, name:'生产企业'},
                {value:251, name:'农贸市场'},
                {value:147, name:'餐厅'},
                {value:102, name:'其他'}
            ]
        }
    ]
};;
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
       </script>
</html>