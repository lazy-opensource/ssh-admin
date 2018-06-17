
var sysUserList_datagrid;
var userAddRemark = UE.getEditor('userAddRemark');
var userEditRemark = UE.getEditor('userEditRemark');
$(function(){

    /**
     * list
     */

    //初始化datagrid
    sysUserList_datagrid = $('#sysUserList_datagrid').datagrid({
        title: '用户列表',
        //iconCls: 'icon-man',
        url:  EasySSH.basePath + 'user/list' ,
        //默认是POST请求方式
        method: 'post',
        idField: 'userId',
        fit: true,
        fitColumns:true,
        border:false,
        striped: true,
        /*
         * pagination, 显示分页栏
         * pageSize, 页记录数, 默认值 10
         * pageList, 页记录数选择值, 默认值 [10, 20, 30, 40, 50]
         */
        pagination: true,
        //pageSize: 10,
        //pageList: [10, 20, 30, 40, 50],

        /*
         * 排序；
         * sortName, 指定排序字段
         * sortOrder, 指定升序(asc)或降序(desc), 默认升序.
         */
        //sortName: 'createTime',
        //sortOrder: 'desc',
        checkOnSelect: false,
        selectOnCheck: false,
        columns: [[
            {field:'userId',title:'编号',width:30,checkbox:true},
            {field:'userName',title:'用户别名',width:60,sortable:true},
            {field:'userRealName',title:'用户真名',width:60,sortable:true},
            {field:'userPassword',title:'用户密码',width:60,sortable:true},
            {field:'userStatus',title:'状态',width:60,sortable:true,
                formatter: function(value,row,index){
                    return value == '0' ? '启用' : '禁用';
                }
            },
            {field:'sysRoles',title:'角色',width:60,sortable:true,
                formatter:function(value,row,index){
                    var sysRoles = row.sysRoles;
                    var roles="";
                    for(var i = 0 ;i < sysRoles.length; i++){
                        roles += sysRoles[i].roleName+",";

                    }
                    roles = roles.substr(0,(roles.length)-1);
                    return roles;
                }
            },
            {field:'userRemark',title:'备注',width:60},
        ]]

    });

    $.ajax({
        url:  EasySSH.basePath + 'user/operations',
        method:'get',
        dataType:'json',
        success:function(r){
            if(r){
                var data = r.data;
                var temp = '';
                console.log(data);
                for(var i=0;i<data.length;i++){
                    if(data[i].sysMenu != null && data[i].sysMenu.menuId == r.statusCode){
                        //console.info(r.statusCode+' user');
                        temp =temp+ '<input type="button" value = "'+data[i].operationName+'" onclick="' + data[i].operationAction + '"/>';
                    }
                }
                $('#userToolbar').html(temp);
                $('#sysUserList_datagrid').datagrid({
                    toolbar:'#userToolbar'
                })

            }
        }
    })

    /**
     * edit
     */
    $('#sysUserEdit').bind('click',function(){
        var userEditRemarkValue = userEditRemark.getContentTxt();
        //alert(userAddRemarkValue);
        $('#userRemarkInput').attr('value' , userEditRemarkValue);
        var roles = $('#edit').val();
        //console.info(roleIds);
        $('#sysUserEdit_form').form({
            url : EasySSH.basePath + 'user/update',
            method:'post',
            queryParams:{
                roles:roles
            },
            success: function(r){
                var r = JSON.parse(r);
                //alert(r.statusCode);
                if(r.statusCode == 200){
                    $('#sysUserEdit_dialog').dialog('close');
                    $('#sysUserList_datagrid').datagrid('load');
                    $.messager.show({
                        title:'系统提示!',
                        msg:r.msg,
                        timeout:5000,
                        showType:'slide'
                    });

                }
//						else if(r.statusCode == 555){
//							window.open(EasySSH.basePath +'login/loginPage','_top');
//						}
            }
        }).submit();
    });

    /**
     * add
     */
    $('#sysUserAdd').bind('click',function(){
        var userAddRemarkValue = userAddRemark.getContentTxt();
        //alert(userAddRemarkValue);
        $('#userRemarkInput').attr('value' , userAddRemarkValue);
        //console.info( $('#userRemarkInput').val());
        var roles = $('#userRoles').datagrid('getSelections');
        var roleIds = [];
        for(var a=0;a<roles.length;a++){
            roleIds.push(roles[a].roleId);
        }

        //console.info(roleIds);
        $('#sysUserAdd_form').form({
            url : EasySSH.basePath + 'user/add',
            method:'post',
            queryParams:{
                roles:roleIds
            },
            success: function(r){
                var r = JSON.parse(r);
                //alert(r);
               // console.info(r);
                if(r.statusCode == 200){
                    //alert($('#sysUserAdd_dialog'));
                    sysUserList_datagrid.datagrid('clearChecked');
                    $('#sysUserAdd_dialog').dialog('close');
                    $('#sysUserList_datagrid').datagrid('load');
                    $.messager.show({
                        title:'系统提示!',
                        msg:r.msg,
                        timeout:5000,
                        showType:'slide'
                    });

                }
            }
        }).submit();
    });

})

//添加系统用户
function userAdd(){
    sysUserList_datagrid.datagrid('clearChecked');
    userAddRemark.ready(function(){
        userAddRemark.setContent('');
    })
    //打开ID 为sysUserAdd_dialog的easyui-dialog对话框
    $('#sysUserAdd_dialog').dialog('open');
    //清空easyui-dialog对话框的数据
    $('.easyui-textbox').textbox('setText', '');
    $('.easyui-combobox').textbox('setText', '');
    $('#userRoles').datalist({
        url:EasySSH.basePath + 'role/roles',
        valueField:'roleId',
        textField:'roleName' ,
        checkbox: true,
        lines: true ,
        ctrlSelect:true,
        singleSelect:false
    });
}

//修改用户
function userEdit(){
    //获取datagrid选中的行数
    var rows = sysUserList_datagrid.datagrid('getChecked');
    if(rows.length == 1){
        $('#sysUserEdit_dialog').dialog('open');
        //回显数据到修改页面
        $('#sysUserEdit_form').form('load', rows[0]);

        var userEditValue = $('#userEditRemarkInput').val()
        userEditRemark.ready(function(){
            userEditRemark.setContent(userEditValue);
        })
        //alert(userEditValue);
        //角色下拉框数据回显处理
        var roles = rows[0].sysRoles;
        sysUserList_datagrid.datagrid('clearChecked');
        $.ajax({
            url: EasySSH.basePath + 'role/roles',
            method:'post',
            data:{t : new Date()},
            dataType:'json',
            success:function(r){
                if(r){
                    $('#edit').empty();
                    for(var a =0;a<r.length;a++){
                        var p = new Option(r[a].roleName);
                        p.text=r[a].roleName;
                        p.value=r[a].roleId;
                        //var p = '<option value="'+r[a].roleId+'">'+r[a].roleName+'</option>';
                        for(var b =0; b < roles.length; b++){
                            if(roles != null){
                                if(r[a].roleId == roles[b].roleId){
                                    $(p).attr('selected',true);
                                }
                            }

                        }
                        $('#edit').append(p);
                    }
                    $.messager.show({
                        title:'系统提示',
                        msg: r.msg,
                        timeout:5000,
                        showType:'slide'
                    })
                }
            }
        })
        /* 			$('#edit').combobox({
         url:'${pageContext.request.contextPath}/role/roles',
         method:'post',
         valueField:'roleId',
         textField:'roleName',
         multiple: true,
         editable: false, //不允许手动输入
         formatter: function(row){
         for(var a =0; a < roles.length; a++){
         if(row.roleId == roles[a].roleId){
         $('#edit').combobox('select',row.roleId);
         return row.roleId;
         }else{
         return row.roleId;
         }
         }

         },

         }) */
    }else{
        $.messager.alert('系统提示','请选择一项进行操作！','info');
    }
}

//删除用户
function userDele(){
    //获取datagrid选中的行数
    var rows = null;
    rows = sysUserList_datagrid.datagrid('getChecked');
    var ids = new Array();
    if(rows != ""){
        $.messager.confirm('系统提示', '您确认要删除吗？', function(r){
            if (r){
                for(var i = 0; i < rows.length; i++){
                    if(rows[i].userId == '1'){
                        alert("没有删除超级管理员用户的权限!");
                        return;
                    }
                    ids.push(rows[i].userId);
                }
                //alert(ids);
                sysUserList_datagrid.datagrid('clearChecked');
                $.post(
                    //'${pageContext.request.contextPath }/sysUser/' + ids.join(','),
                    EasySSH.basePath + 'user/delete/' + ids.join(',') ,
                    {time : new Date()},
                    function(r){
                        if(r.statusCode == 200){
                            sysUserList_datagrid.datagrid('load');
                            $.messager.show({
                                title:'系统提示',
                                msg: r.msg,
                                timeout:5000,
                                showType:'slide'
                            });
                        }
                    },'json');
            }
        });
    }else{
        $.messager.alert('系统提示','请至少选择一项进行操作！','info');
    }
}
function exportUserExcl(){
    $.messager.confirm('系统提示', '导出Excl?', function(r){
        if(r){
            window.location.href=EasySSH.basePath + 'user/export/';
        }
    })
}
