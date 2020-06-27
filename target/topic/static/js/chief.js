layui.use(['element','jquery','table','laydate','form','layer'],function () {
    var element=layui.element,
        table=layui.table,
        laydate=layui.laydate,
        form=layui.form,
        $=layui.$,
        layer=layui.layer;

    // laydate.render({
    //     elem:'#auditDate',
    //     value:new Date()
    // });

    $('#username').append(layui.sessionData('user').name);

    //查看基本信息
    $('#basicInfo').click(function () {
        layer.open({
            type:1,
            title:"基本信息",
            content:$('#basic')
        });
        form.val('basic',{
            id:layui.sessionData('user').id,
            name:layui.sessionData('user').name,
            gender:layui.sessionData('user').gender,
            phone:layui.sessionData('user').phone,
            role:layui.sessionData('user').role
        });
    });

    var renderAudit = function () {
        return table.render({
            elem: '#auditTable'
            , height: 380
            , url: '/topic/audit' //数据接口
            , toolbar: '#toolbarAudit'
            , page: true //开启分页
            , cols: [[ //表头
                {type: 'radio', fixed: 'left'}
                , {field: 'id', title: 'ID', width: 50}
                , {field: 'bookName', title: '书名', width: 75}
                , {field: 'edition', title: '版次', width: 70}
                , {field: 'bookClassify', title: '图书分类', width: 90}
                , {field: 'bookNature', title: '著作性质', width: 90}
                , {field: 'editor', title: '责任编辑', width: 90}
                , {field: 'reportTime', title: '申报时间', width: 110}
                , {field: 'process', title: '审核阶段', width: 100}
                , {field: 'planContributeTime', title: "计划发稿时间", width: 120}
                , {field: 'planPublishTime', title: '计划出版时间', width: 120}
                , {field: 'introduction', title: '内容简介', width: 100}
                , {field: 'declareReason', title: '申报理由', width: 100}
                , {fixed: 'right', title: '操作', toolbar: '#toolAudit', width: 70}
            ]]
        });
    }

    renderAudit();

    element.on('nav(chief)', function (elem) {
        var title = $(this).text();
        var content = $(this).attr('lay-href');
        var exist = $("li[lay-id='" + content + "']").length; //判断是否存在tab
        if (exist == 0) {

            if (content == "11") {
                element.tabAdd('chief', {
                    title: title,
                    content: "<table class=\"layui-hide\" id=\"auditTable\" lay-filter=\"auditTable\"></table>",
                    id: content
                });
                renderAudit();
            }
        }
        element.tabChange('chief', content);//切换tab
    });

    var index;


    //头工具栏事件  toolBar(auditTable) 为<table>的lay-filter
    table.on('toolbar(auditTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;

        switch (obj.event) {
            case 'detail':
                if (data.length != 1) {
                    layer.msg('请选择一行');
                } else {
                    layer.open({
                        type: 1,
                        title: '选题详细信息',
                        area: '700px',
                        content: $('#topicForm')
                    });
                    form.val("topic", {
                        "id": data[0].id,
                        "bookName": data[0].bookName,
                        "bookClassify": data[0].bookClassify,
                        "edition": data[0].edition,
                        "bookNature": data[0].bookNature,
                        "planContributeTime": data[0].planContributeTime,
                        "planPublishTime": data[0].planPublishTime,
                        "introduction": data[0].introduction,
                        "declareReason": data[0].declareReason,
                        "editor": data[0].editor,
                        "process": data[0].process,
                        "reportTime": data[0].reportTime
                    });
                }
                break;
            case 'survey':
                if (data.length != 1) {
                    layer.msg('请选择一行');
                } else {
                    $.ajax({
                        url: "/topic/survey?topicId=" + data[0].id,
                        type: 'get',
                        contentType: 'application/json',
                        success: function (data) {
                            if (data == '') {
                                layer.open({
                                    title: '提示'
                                    , content: '没有相关记录'
                                });
                            } else {
                                form.val('lookSurvey', {
                                    'topicId': data.topicId,
                                    'investigator': data.investigator,
                                    'surveyTime': data.surveyTime
                                });

                                index = layer.open({
                                    type: 1,
                                    title: '社会调查信息',
                                    content: $('#lookSurvey')
                                });
                            }
                        }
                    });

                }
                break;
            case 'auditList':
                if (data.length != 1) {
                    layer.msg('请选择一行');
                } else {
                index = layer.open({
                    type: 1,
                    title: '审核记录',
                    area:['780px','380px'],
                    content: $("#div"),
                });
                table.render({
                    elem: '#auditList'
                    , url: '/topic/auditList/' + data[0].id //数据接口
                    ,height:300
                    , page: true //开启分页
                    , cols: [[ //表头
                          {field: 'id', title: 'ID',align:'center', width:50}
                        , {field: 'auditor', title: '审核人',align:'center',width:100}
                        , {field: 'result', title: '结果',align:'center',width:100}
                        , {field: 'opinion', title: '审核意见',width:300}
                        , {field: 'date', title: '审核时间',align:'center',width:120}
                        , {field: 'status', title: '审核阶段',align:'center',width:100}
                    ]]
                });
                break;
            }}
        });

        $("#down").click(function (event) {
            var id = $("#topicId").val();
            window.location.href = "/topic/download/" + id;
            layer.close(index);
        });

        //监听行工具事件
        table.on('tool(auditTable)', function (obj) {
            var data = obj.data;
            if (obj.event == 'next') {
                index = layer.open({
                    title: '填写审核信息',
                    type: 1,
                    area: 700,
                    content: $('#auditForm')
                });

                var date = new Date();

                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                m = m < 10 ? '0' + m : m;
                var d = date.getDate();
                d = d < 10 ? ('0' + d) : d;

                form.val('audit', {
                    'topicId': data.id,
                    'auditor': layui.sessionData('user').name,
                    'status': data.process,
                    'date': y + '-' + m + '-' + d
                });
                form.on('submit(auditSubmit)', function (data) {

                    var s = data.field.status;
                    if (s == "初审") {
                        data.field.status = 1;
                    } else {
                        data.field.status = 2;
                    }

                    $.ajax({
                        type: 'post',
                        url: '/topic/audit',
                        data: JSON.stringify(data.field),
                        contentType: 'application/json',
                        success: function (da) {
                            if (da == 0)
                                layer.msg('操作失败');
                            else
                                layer.msg('操作成功');
                            renderAudit();
                        }
                    });
                    layer.close(index);
                    return false;
                });
            }
        });

    });

