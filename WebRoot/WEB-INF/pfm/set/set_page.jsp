<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../../include/tld.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="favicon.ico">
    <%@ include file="../../include/css.jsp" %>
    <%@ include file="../../include/status.jsp" %>
</head>
<body class="gray-bg">
<div class="col-sm-12">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <ol class="breadcrumb">
                <li><a>考核规定</a></li>
                <li><strong>列表</strong></li>
            </ol>
        </div>
        <div class="ibox-content">
            <form action="gridPage.do" method="post" id="listForm">
                <input type="hidden" name="ids" id="ids">
                <div>
                    <a class="btn btn-success" href="javascript:void(0);"
                       onclick="jqgridReload();">刷新</a>
                    <a class="btn btn-primary"
                       href="javascript:;" onclick="fnEdit('')">新增</a>
                    <a class="btn btn-danger" href="javascript:void(0);"
                       onclick="jqgridDelete();">删除</a>
                </div>
                <div class="jqGrid_wrapper">
                    <table id="listTable"></table>
                    <div id="pager"></div>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="../../include/js.jsp" %>
<%@ include file="../../include/grid_page.jsp" %>
<script>
    function fnEdit(id) {
        layer.open({
            title: '新增/修改',
            type: 2,
            area: ['700px', '450px'],
            fix: false, //不固定
            maxmin: true,
            content: 'edit.do?id=' + id,
            btn: ['确定', '关闭'], //按钮
            yes: function (index, layero) {
                var iframeWin = window[layero.find('iframe')[0]['name']];
                iframeWin.submitSave();
            },
            end: function () {
                location.reload();
            }
        });
    }

    $(function () {
        var url = 'gridData.do';
        var editurl = 'gridEdit.do';
        var colNames = ['工作名称', '编号', '工作时长', '分值', "备注", '操作'];
        var colModel = [
            {
                name: 'workName',
                index: 'workName',
                sortable: false,
                width: 100,
                searchoptions: {
                    sopt: ['cn']
                }
            }, {
                name: 'code',
                index: 'code',
                sortable: false,
                searchoptions: {
                    sopt: ['cn']
                }
            }, {
                name: 'time',
                index: 'time',
                width: 100,
                sortable: false,
                search: false,
            }, {
                name: 'value',
                index: 'value',
                width: 100,
                sortable: false,
                search: false,
            }, {
                name: 'remarks',
                index: 'remarks',
                width: 100,
                sortable: false,
                search: false,
            }, {
                name: 'act',
                index: 'act',
                width: 70,
                fixed: true,
                title: false,
                search: false,
                sortable: false
            }];
        gridInitMin(url, colNames, colModel, true);
    });

    function gridComplete() {
        var ids = jQuery("#listTable").jqGrid('getDataIDs');
        for (var i = 0; i < ids.length; i++) {
            var cl = ids[i];
            be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:fnEdit(\'' + cl + '\')">修改</a>';
            jQuery("#listTable").jqGrid('setRowData', ids[i], {
                act: be
            });
        }
    }

    /**
     * 删除
     */
    function jqgridDelete() {
        var selectIds = getSelectIds();
        if (selectIds.length < 1) {
            layer.msg('请选择要删除的记录', {icon: 0, time: 3000});
            return false;
        }
        layer.confirm('确认要删除吗?', {icon: 3, title: '系统提示'}, function (index) {
            $("#ids").val(selectIds);
            $("#listForm").attr("action", "delete.do");
            $("#listForm").submit();
        });
    }
</script>

</body>
</html>
