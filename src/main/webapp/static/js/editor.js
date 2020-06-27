layui.use(['element','jquery','table','laydate','form','layer','upload'],function () {
    var element=layui.element,
        table=layui.table,
        laydate=layui.laydate,
        form=layui.form,
        $=layui.$,
        upload=layui.upload,
        layer=layui.layer;

    laydate.render({
        elem: '#date2'
    });
    laydate.render({
        elem: '#date1'
    });

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
            gender:layui.sessionData('user').gender=="男"?1:0,
            phone:layui.sessionData('user').phone,
            role:layui.sessionData('user').role=="责任编辑"?1:(layui.sessionData('user').role=='主编'?2:3)
        });
    });


    var renderNew=function(){
        return table.render({
            elem: '#newTopicTable'
            ,height: 380
            ,url: '/topic/newTopicList' //数据接口
            ,toolbar:'#toolbarNew'
            ,page: true //开启分页
            ,cols: [[ //表头
                {type:'radio',fixed:'left'}
                ,{field: 'id', title: 'ID',width:50}
                ,{field: 'bookName', title: '书名',width:75}
                ,{field: 'edition', title: '版次',width:70}
                ,{field: 'bookClassify', title: '图书分类',width:90}
                ,{field: 'bookNature', title: '著作性质',width:90}
                ,{field: 'editor', title: '责任编辑',width:90}
                ,{field: 'process',title: '选题状态',width:120}
                ,{field: 'planContributeTime',title:"计划发稿时间",width:120}
                ,{field: 'planPublishTime',title:'计划出版时间',width:120}
                ,{field:'introduction',title:'内容简介',width:95}
                ,{field:'declareReason',title:'申报理由',width:95}
                ,{fixed:'right',title:'操作', toolbar: '#toolNew',width:70}
            ]]
        });
    }

    var tableInsNew=renderNew();

    var tableInsSurvey;
    var renderSurvey=function(){
        return table.render({
            elem: '#surveyTable'
            ,height: 380
            ,url: '/topic/onePageSurvey' //数据接口
            ,toolbar:'#toolbarSurvey'
            ,page: true //开启分页
            ,cols: [[ //表头
                {type:'radio',fixed:'left'}
                ,{field: 'id', title: 'ID',width:50}
                ,{field: 'bookName', title: '书名',width:75}
                ,{field: 'edition', title: '版次',width:70}
                ,{field: 'bookClassify', title: '图书分类',width:90}
                ,{field: 'bookNature', title: '著作性质',width:90}
                ,{field: 'editor', title: '责任编辑',width:90}
                ,{field: 'process',title: '选题状态',width:120}
                ,{field: 'planContributeTime',title:"计划发稿时间",width:120}
                ,{field: 'planPublishTime',title:'计划出版时间',width:120}
                ,{field:'introduction',title:'内容简介',width:95}
                ,{field:'declareReason',title:'申报理由',width:95}
                ,{field:'reportTime',title:'申报时间',width:120}
                ,{fixed:'right',title:'操作', toolbar: '#toolSurvey',width:70}
            ]]
        });
    }

    element.on('nav(edit)',function (elem) {
        var title = $(this).text();
        var content = $(this).attr('lay-href');
        // alert(content);
        var exist = $("li[lay-id='" + content + "']").length; //判断是否存在tab
        if (exist == 0) {

            if(content=="11") {
                element.tabAdd('editor', {
                    title: title,
                    content: "<table class=\"layui-hide\" id=\"newTopicTable\" lay-filter=\"newTopicTable\"></table>",
                    id: content
                });
                tableInsNew=renderNew();
            }
            if(content=="12"){
                element.tabAdd('editor', {
                    title: title,
                    content: "<table class=\"layui-hide\" id=\"surveyTable\" lay-filter=\"surveyTable\"></table>",
                    id: content
                });
               tableInsSurvey=renderSurvey();
            }
        }
        element.tabChange('editor', content);//切换tab
    });

      /*  <button class="layui-btn layui-btn-sm" lay-event="newTopic">新增</button>
        <button class="layui-btn layui-btn-sm" lay-event="editTopic">编辑</button>
        <button class="layui-btn layui-btn-sm" lay-event="deleteTopic">删除</button>*/

    var index; //弹出层下标

    //头工具栏事件
    table.on('toolbar(newTopicTable)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;

        switch(obj.event){
            case 'newTopic':
                index=layer.open({
                   type:1,
                   title:'新建选题',
                    area:'700px',
                   content:$('#newForm')
                });
                 // $("newForm")[0].reset();
                form.render();
                form.val("new",{
                    "editor":layui.sessionData('user').name
                });
                $('#newLayerSubmit').text("保存");
                $('#newLayerSubmit').attr('lay-id','add');
                break;
            case 'editTopic':
                if(data.length!=1){
                    layer.msg('请选择一行');
                }else{
                    index=layer.open({
                        type:1,
                        title:'编辑选题信息',
                        area:'700px',
                        content:$('#newForm')
                    });
                    form.val("new",{
                        "id":data[0].id,
                        "bookName":data[0].bookName,
                        "bookClassify":data[0].bookClassify,
                        "edition":data[0].edition,
                        "bookNature":data[0].bookNature,
                        "planContributeTime":data[0].planContributeTime,
                        "planPublishTime":data[0].planPublishTime,
                        "introduction":data[0].introduction,
                        "declareReason":data[0].declareReason,
                        "editor":data[0].editor
                    });
                    $('#newLayerSubmit').text("修改");
                    $('#newLayerSubmit').attr('lay-id','edit');
                }
                break;
            case 'deleteTopic':
                if(data.length!=1){
                    layer.msg('请选择一行');
                }else{
                    layer.confirm('确定删除吗？', function(index){
                        $.ajax({
                            type:'delete',
                            url:'/topic/newTopic/'+data[0].id,
                            contentType:'application/json',
                            success:function (da) {
                                if (da == 0) {
                                    layer.msg("操作失败");
                                }
                                else {
                                    layer.msg("操作成功");
                                    tableInsNew.reload();
                                }
                            }
                        });
                        layer.close(index);
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
                }
        };
    });

   /* form.on('submit(submitNew)', function(data){
         layer.alert(JSON.stringify(data.field), {
            title: '最终的提交信息'
       })
        return false;
    });*/

    form.on("submit(submitNew)",function (data) {
        var method;
        if($('#newLayerSubmit').attr('lay-id')=='add') {
            method='post';
        }else {
            method='put';
        }
        $.ajax({
            type:method,
            url:'/topic/newTopic',
            data:JSON.stringify(data.field),
            contentType:'application/json',
            success:function (da) {
                if (da == 0) {
                    layer.msg("操作失败");
                }
                else {
                    layer.msg("操作成功");
                    tableInsNew.reload();
                }
            }
        });

        layer.close(index);
        return false;
    });

/*
       <script type="text/html" id="toolNew">
        <a class="layui-btn layui-btn-xs" lay-event="look">查看</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="next">送审</a>
        </script>*/
    //监听行工具事件
    table.on('tool(newTopicTable)', function(obj){
        var data = obj.data;
        //console.log(obj)
        if(obj.event == 'next'){
            layer.confirm('确定送审吗？', function(index){
                $.ajax({
                    type:'put',
                    url:'/topic/next/'+data.id,
                    contentType:'application/json',
                    success:function (da) {
                        if (da == 0) {
                            layer.msg("操作失败");
                        }
                        else {
                            layer.msg("操作成功");
                            obj.del();
                        }
                    }
                });
                layer.close(index);
            });
        } /*else if(obj.event == 'look'){
            layer.prompt({
                formType: 2
                ,value: data.email
            }, function(value, index){
                obj.update({
                    email: value
                });
                layer.close(index);
            });
        }*/
    });

    //社会调查

    //头工具栏事件  toolBar(surveyTable) 为<table>的lay-filter
    table.on('toolbar(surveyTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;

        switch (obj.event) {
            case 'detail':
                if (data.length !== 1) {
                    layer.msg('请选择一行');
                } else {
                    layer.open({
                        type: 1,
                        title: '选题详细信息',
                        area: '700px',
                        content: $('#detailForm')
                    });
                    form.val("detailForm", {
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
                        "process":data[0].process,
                        "reportTime":data[0].reportTime
                    });
                }

                break;
           /* case 'survey':
                if (data.length != 1) {
                    layer.msg('请选择一行');
                } else {
                    layer.open({
                        type: 1,
                        title: '社会调查信息',
                        area: '700px',
                        content: $('#newForm')
                    });

                }
                break;*/
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
                }
        }
    });


    var index;
    var up=null;
    //监听行工具事件
    table.on('tool(surveyTable)', function (obj) {
        var data = obj.data;
        if (obj.event == 'next') {


            var date=new Date();

            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            m = m < 10 ? '0' + m : m;
            var d = date.getDate();
            d = d < 10 ? ('0' + d) : d;

            form.val('surveyForm',{
                'topicId':data.id,
                'investigator':layui.sessionData('user').name,
                'surveyTime':y+'-'+m+'-'+d
            });
            if(up==null) {

                up = upload.render({
                    elem: '#test8'
                    , url: '/topic/upload/' + data.id
                    , auto: false
                    , accept: 'file'
                    // ,multiple: true
                    , bindAction: '#test9'
                    , before: function () {
                        layer.load();
                    }
                    , done: function (res) {
                        layer.closeAll('loading');
                        layer.msg('上传成功');
                        layer.close(index);
                        form.val('surveyForm', {
                            'path': res.data.src
                        });
                        $("#submitSurvey").click();
                    }
                    , error: function () {
                        layer.closeAll('loading');
                        layer.msg('上传失败');
                        layer.close(index);
                    }
                });
            }else{
                up.reload();
            }
            index = layer.open({
                title:'上传调查市场调查报告',
                type:1,
                content:$('#surveyForm')
            });
        }
    });




//上传市场调查表单
    form.on('submit(submitSurvey)',function(data){
        $.ajax({
            type:'post',
            url:'/topic/survey',
            data:JSON.stringify(data.field),
            contentType:'application/json',
            success:function(da){
                if(da==0)
                    layer.msg('操作失败');
                else{
                    layer.msg('操作成功');
                    tableInsSurvey.reload();
                }
            }
        });
        layer.close(index);
        return false;
    });

});