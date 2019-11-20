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
							<td class="active"><label class="pull-right">省份:</label></td>
							<td><input id="name" placeholder="省份"  class="form-control" name="name" type="text" value="${vo.name}"/></td>
						</tr>
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
<%@ include file="../../include/js.jsp"%>
 <script type="text/javascript">
var dom = document.getElementById("container");
var myChart = echarts.init(dom);
var app = {};
option = null;
myChart.showLoading();

$.get('/HK.json', function (geoJson) {

    myChart.hideLoading();

    echarts.registerMap('HK', geoJson);

    myChart.setOption(option = {
        title: {
            text: '香港18区',
            //subtext: '人口密度数据来自Wikipedia',
            //sublink: 'http://zh.wikipedia.org/wiki/%E9%A6%99%E6%B8%AF%E8%A1%8C%E6%94%BF%E5%8D%80%E5%8A%83#cite_note-12'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{b}<br/>{c} '
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
        visualMap: {
            min: 800,
            max: 50000,
            text:['高','低'],
            realtime: false,
            calculable: true,
            inRange: {
                color: ['lightskyblue','yellow', 'orangered']
            }
        },
        series: [
            {
                name: '',
                type: 'map',
                mapType: 'HK', // 自定义扩展图表类型
                itemStyle:{
                    normal:{label:{show:true}},
                    emphasis:{label:{show:true}}
                },
                data:[
                    {name: '中西区', value: 20057.34},
                    {name: '湾仔', value: 15477.48},
                    {name: '东区', value: 31686.1},
                    {name: '南区', value: 6992.6},
                    {name: '油尖旺', value: 44045.49},
                    {name: '深水埗', value: 40689.64},
                    {name: '九龙城', value: 37659.78},
                    {name: '黄大仙', value: 45180.97},
                    {name: '观塘', value: 55204.26},
                    {name: '葵青', value: 21900.9},
                    {name: '荃湾', value: 4918.26},
                    {name: '屯门', value: 5881.84},
                    {name: '元朗', value: 4178.01},
                    {name: '北区', value: 2227.92},
                    {name: '大埔', value: 2180.98},
                    {name: '沙田', value: 9172.94},
                    {name: '西贡', value: 3368},
                    {name: '离岛', value: 806.98}
                ],
                // 自定义名称映射
                nameMap: {
                    'Central and Western': '中西区',
                    'Eastern': '东区',
                    'Islands': '离岛',
                    'Kowloon City': '九龙城',
                    'Kwai Tsing': '葵青',
                    'Kwun Tong': '观塘',
                    'North': '北区',
                    'Sai Kung': '西贡',
                    'Sha Tin': '沙田',
                    'Sham Shui Po': '深水埗',
                    'Southern': '南区',
                    'Tai Po': '大埔',
                    'Tsuen Wan': '荃湾',
                    'Tuen Mun': '屯门',
                    'Wan Chai': '湾仔',
                    'Wong Tai Sin': '黄大仙',
                    'Yau Tsim Mong': '油尖旺',
                    'Yuen Long': '元朗'
                }
            }
        ]
    });
});;
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
</script>
 </body>
</html>