layui.use(['element','table','jquery','form'], function(){
    var element = layui.element,
        table=layui.table,
        form=layui.form,
        layer=layui.layer,
        $=layui.$;

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

    //导航条点击监听，新增tab
    element.on('nav(userInfo)',function (elem) {
        var title = $(this).text();
        var content = $(this).attr('lay-href');
        var exist = $("li[lay-id='" + content + "']").length; //判断是否存在tab
        if (exist == 0) {
            if(content=="11") {
                element.tabAdd('stuff', {
                    title: title,
                    content:"<table id=\"user\" lay-filter=\"user\"></table>\n",
                    id: content
                });
                userIns();
            }
            if(content=='12'){
                element.tabAdd('stuff', {
                    title: title,
                    content:"<table id=\"topic\" lay-filter=\"topic\"></table>\n",
                    id: content
                });
                topicIns();
            }
        }
        element.tabChange('stuff', content);//切换tab
    });

    var index;
    var userIns;
    userIns=function () {
        return table.render({
            elem: '#user'
            , height: 380
            , url: '/topic/userList' //数据接口
            , page: true //开启分页
            , toolbar: 'default'
            , cols: [[ //表头
                {type: 'radio', fixed: 'left'}
                , {field: 'id', title: '工号ID', fixed: 'left'}
                , {field: 'password', title: '密码'}
                , {field: 'name', title: '用户名'}
                , {field: 'gender', title: '性别'}
                , {field: 'phone', title: '电话'}
                , {field: 'role', title: '职务'}
            ]]
        });
    }
    userIns();

    var topicIns=function () {

        return table.render({
            elem: '#topic'
            , height: 380
            , url: '/topic/report' //数据接口
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
                , {field: 'process', title: '上报状态', width: 100}
                , {field: 'planContributeTime', title: "计划发稿时间", width: 120}
                , {field: 'planPublishTime', title: '计划出版时间', width: 120}
                , {field: 'introduction', title: '内容简介', width: 100}
                , {field: 'declareReason', title: '申报理由', width: 100}
                , {fixed: 'right', title: '操作', toolbar: '#toolReport', width: 70}
            ]]
        });

    };


    //监听头工具栏事件
    table.on('toolbar(user)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取选中的数据
        switch(obj.event){
            case 'add':
                index=layer.open({
                    type:1,
                    title:"添加职员",
                    content:$("#addUser")
                });
                break;
            case 'update':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else if(data.length > 1){
                    layer.msg('只能同时编辑一个');
                } else {
                    form.val("updateUser",{
                        "id":data[0].id,
                        "password":data[0].password,
                        "name":data[0].name,
                        "gender":data[0].gender=="男"?"1":"0",
                        "phone":data[0].phone,
                        "role":data[0].role=="编辑"?"1":data[0].role=="主编"?"2":"3"
                    });
                    index=layer.open({
                        type:1,
                        title:"编辑员工信息",
                        content:$("#updateUser")
                    });

                }
                break;
            case 'delete':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else {
                    layer.confirm("确定删除吗？",function (index) {
                        $.ajax({
                            type:'delete',
                            url:'/topic/user/'+data[0].id,
                            contentType:'application/json',
                            success:function (da) {
                                if (da == 0) {
                                    layer.msg("删除失败");
                                }
                                else {
                                    layer.msg("删除成功");
                                    userIns();
                                }
                            }
                        });
                        layer.close(index);
                    });
                }
                break;
        };
    });

    form.on("submit(add)",function (data) {
        $.ajax({
            type:'post',
            url:'/topic/user',
            data:JSON.stringify(data.field),
            contentType:'application/json',
            success:function (da) {
                if (da == 0) {
                    layer.msg("添加失败");
                }
                else {
                    layer.msg("添加成功");
                    userIns();
                }
            }
        });
        layer.close(index);
        return false;
    });

    form.on("submit(update)",function (data) {
        $.ajax({
            type:'put',
            url:'/topic/user/',
            contentType:'application/json',
            data:JSON.stringify(data.field),
            success:function (da) {
                if (da == 0) {
                    layer.msg("修改失败");
                }
                else {
                    layer.msg("修改成功");
                    userIns();
                }
            }
        });
        layer.close(index);
        return false;
    });


    table.on('tool(topic)', function(obj){
        var data = obj.data;
        if(obj.event == 'next'){
            if(data.reportPhase==="已上报"){
                layer.alert("该选题已经上报");
            }else{
            layer.confirm('确定上报吗？', function(index){
                $.ajax({
                    type:'put',
                    url:'/topic/report/'+data.id,
                    contentType:'application/json',
                    success:function (da) {
                        if (da == 0) {
                            layer.msg("操作失败");
                        }
                        else {
                            layer.msg("操作成功");
                            topicIns();
                        }
                    }
                });
                layer.close(index);
            });
        }}
    });

});
