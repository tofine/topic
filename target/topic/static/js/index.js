layui.use(['form','layer','jquery','layedit'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,layedit=layui.layedit
        ,$=layui.jquery;

    //自定义验证规则
    form.verify({
        name: function(value){
            if(value.length == 0){
                return '用户名不能为空';
            }
        }
        ,password: [
            /^[\S]{3,12}$/
            ,'密码必须3到12位，且不能出现空格'
        ]
        ,content: function(value){
            layedit.sync(editIndex);
        }
    });

    //监听提交
    form.on('submit(login)', function(data){
        var content=JSON.stringify(data.field);
        $.ajax({
            type:"post",
            url:"/topic/login",
            data:content,
            contentType:'application/json',
            success:function (da) {
                if(da=='') layer.alert("用户名或密码不正确！");
                else if(da['role']=='责任编辑') window.location="views/editor.html";
                else if(da['role']=='主编') window.location="views/chief.html";
                else window.location="views/stuff.html";
                layui.sessionData('user',{key:'id',value:da['id']});
                layui.sessionData('user',{key:'password',value:da['password']});
                layui.sessionData('user',{key:'name',value:da['name']});
                layui.sessionData('user',{key:'phone',value:da['phone']});
                layui.sessionData('user',{key:'gender',value:da['gender']});
                layui.sessionData('user',{key:'role',value:da['role']});
            }
        });
        return false;
    });

});