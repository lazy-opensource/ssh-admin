
var roleAddRemark = UE.getEditor('roleAddRemark');
var roleEditRemark = UE.getEditor('roleEditRemark');
var sysRoleList_datagrid;
	$(function(){
		//初始化datagrid

        /**
         * list
         */
		sysRoleList_datagrid = $('#sysRoleList_datagrid').datagrid({
			title: '角色列表',
			//iconCls: 'icon-man',
			url: EasySSH.basePath +'role/list' ,
			//默认是POST请求方式
			method: 'post',
			idField: 'roleId',
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
			    {field:'roleId',title:'编号',width:30,checkbox:true},
			    {field:'roleName',title:'角色名称',width:60,sortable:true},
			    {field:'roleRemark',title:'备注',width:60,sortable:true}
			]]
			
		})
		$.ajax({
			url:EasySSH.basePath +'role/operations',
			method:'get',
			dataType:'json',
			success:function(r){
				if(r){
					var data = r.data;
					var temp = '';
					//console.log(data[0].sysMenu.menuId);

					for(var i=0;i<data.length;i++){
						if(data[i].sysMenu != null && data[i].sysMenu.menuId == r.statusCode){
							//console.info(r.statusCode+' role');
                            temp =temp+ '<input type="button" value = "'+data[i].operationName+'" onclick="' + data[i].operationAction + '"/>';
							}
					   }
						$('#roleToolbar').html(temp);
						$('#sysRoleList_datagrid').datagrid({
							toolbar:'#roleToolbar'	
						})
					
				}
			}
		})

        /**
         * edit
         */
        $('#sysRoleEdit').bind('click',function(){
            //debugger;
            var roleEditRemarkValue = roleEditRemark.getContentTxt();
            $('#roleEditRemarkInput').attr("value",roleEditRemarkValue);
            var menus = $('#editRoleMenu').tree('getChecked');
            var operations =  document.getElementById("editRoleOpertions");
            var operationIds = [];
            var menuIds = [];
           // console.info(operationIds);
            for(i=0;i<operations.length;i++){
                //console.info(operationIds.options[i].selected);
                if(operations.options[i].selected){
                    operationIds.push(operations[i].value);
                }
            }
            for(var i =0;i <menus.length; i++){
                alert(menus[i].id);
                menuIds.push(menus[i].id);
            }
            //console.info(operationIds);
            //console.info(menuIds);
            $('#sysRoleEdit_form').form({
                url : EasySSH.basePath +'role/update',
                method:'post',
                queryParams:{
                    menuIds:menuIds,
                    operationIds:operationIds
                },
                success: function(r){
                    var r = JSON.parse(r);
                    //alert(r.statusCode);
                    if(r.statusCode == 200){
                        $('#sysRoleEdit_dialog').dialog('close');
                        $('#sysRoleList_datagrid').datagrid('load');
                        $.messager.show({
                            title:'系统提示!',
                            msg:r.msg,
                            timeout:5000,
                            showType:'slide'
                        });

                    }
                }
            }).submit();
        })

        /**
         * add
         */
        $('#sysRoleAdd').bind('click',function(){
            //alert('roleADD');
            //debugger;
            var roleAddRemarkValue = roleAddRemark.getContentTxt();
            $('#roleAddRemarkInput').attr('value',roleAddRemarkValue);

            var menuIds = $('#roleMenu').tree('getChecked');
            var operationIds = $('#roleOpertions').datagrid('getSelections');
            var mIds = [];
            var oIds = [];
            //console.info(menuIds);
            //console.info(operationIds);
            for(var m = 0;m < menuIds.length; m++){
                mIds.push(menuIds[m].id);
            }
            for(var o = 0;o < operationIds.length; o++){
                oIds.push(operationIds[o].operationId);
            }
            $('#sysRoleAdd_form').form({
                url : EasySSH.basePath +'role/add',
                method:'post',
                queryParams:{
                    menuIds:mIds,
                    operationIds:oIds
                },
                success: function(r){
                    var r = JSON.parse(r);
                    //alert(r.statusCode);
                    if(r.statusCode == 200){
                        sysRoleList_datagrid.datagrid('clearChecked');
                        $('#sysRoleAdd_dialog').dialog('close');
                        $('#sysRoleList_datagrid').datagrid('load');
                        $.messager.show({
                            title:'系统提示!',
                            msg:r.msg,
                            timeout:5000,
                            showType:'slide'
                        });
                    }
                }
            }).submit();
        })

	})
	
	//添加
	function roleAdd(){
        sysRoleList_datagrid.datagrid('clearChecked');
        roleAddRemark.ready(function(){
            roleAddRemark.setContent('');
        })
		//打开ID 为sysUserAdd_dialog的easyui-dialog对话框
		$('#sysRoleAdd_dialog').dialog('open');
		//清空easyui-dialog对话框的数据
		$('.easyui-textbox').textbox('setText', '');
		$('.easyui-combobox').textbox('setText', '');
        //构建菜单树列表
        $.ajax({
            url:EasySSH.basePath +'role/menuTree',
            method:'post',
            dataType:'Json',
            success:function(j){

                //console.info(j);
                $('#roleMenu').tree({
                    data:j.data,
                    checkbox:true,
                    cascadeCheck:false,
                    onCheck: function (node, checked) {
                        if (checked) {
                            var parentNode = $("#roleMenu").tree('getParent', node.target);
                            if (parentNode != null) {
                                $("#roleMenu").tree('check', parentNode.target);
                            }
                        } else {
                            var childNode = $("#roleMenu").tree('getChildren', node.target);
                            if (childNode.length > 0) {
                                for (var i = 0; i < childNode.length; i++) {
                                    $("#roleMenu").tree('uncheck', childNode[i].target);
                                }
                            }
                        }
                    }
                })
            }
        })

        //获得所有的操作
        $.ajax({
            url:EasySSH.basePath +'role/operations',
            method:'get',
            dataType:'Json',
            success:function(j){
                //console.info(j);
                $('#roleOpertions').datalist({
                    data:j.data,
                    valueField:'operationId',
                    textField:'operationName' ,
                    checkbox: true,
                    height:150,
                    width:150,
                    lines: true ,
                    ctrlSelect:true,
                    singleSelect:false
                });
            }
        })
	}
	
	//修改
	function roleEdit(){
		//获取datagrid选中的行数
		var rows = sysRoleList_datagrid.datagrid('getChecked');
		if(rows.length == 1){
			$('#sysRoleEdit_dialog').dialog('open');
			//回显数据到修改页面
			$('#sysRoleEdit_form').form('load', rows[0]);
			//处理ueditor

			var roleEditValue = $('#roleEditRemarkInput').val();
			roleEditRemark.ready(function(){
               // alert(roleEditValue);
				roleEditRemark.setContent(roleEditValue);    
			})
			//对角色的菜单权限 以及操作权限进行 额外处理
			var sysRole = rows[0];
			var menus = [];
			var operation = [];
			menus = sysRole.sysMenus;
			operation = sysRole.sysOperations;
            sysRoleList_datagrid.datagrid('clearChecked');
			//console.info(menus);
			//console.info(operation);
			$.ajax({
				url:EasySSH.basePath +'role/menuTree',
				method:'post',
				dataType:'Json',
				success:function(j){
					//console.info(j.data);
					$('#editRoleMenu').tree({
						data:j.data,
						checkbox:true,
						cascadeCheck:false,
						formatter:function(node){
							//console.info(menus);
							//debugger;
							node.checked = false;
							for(var i =0;i < menus.length; i++){
								if(menus[i].menuId == node.id){	
									//console.info(menus[m]);
					                //console.info(node);
									node.checked = true;
								}
							}
							return node.text;
						},
                        onCheck: function (node, checked) {
                            if (checked) {
                                var parentNode = $("#editRoleMenu").tree('getParent', node.target);
                                if (parentNode != null) {
                                    $("#editRoleMenu").tree('check', parentNode.target);
                                }
                            } else {
                                var childNode = $("#editRoleMenu").tree('getChildren', node.target);
                                if (childNode.length > 0) {
                                    for (var i = 0; i < childNode.length; i++) {
                                        $("#editRoleMenu").tree('uncheck', childNode[i].target);
                                    }
                                }
                            }
                        }
					})	
				}
			})
			
			$.ajax({
				url:EasySSH.basePath +'role/operations', 
				method:'get',
				dataType:'Json',
				success:function(j){
					//console.info(j);
					//debugger;
					$('#editRoleOpertions').empty();
					var data = j.data;
					for(var a =0;a <data.length; a++){
						var o = new Option();
						o.text = data[a].operationName;
						o.value = data[a].operationId;
						for(b =0; b<operation.length; b++){
							if(operation[b].operationId == data[a].operationId){
								o.selected = true;
							}
						}
						$('#editRoleOpertions').append(o);
					}
				}	
			})
		}else{
			$.messager.alert('系统提示','请选择一项进行操作！','info');
		}
	}
	
	//删除
	function roleDele(){
		//获取datagrid选中的行数
		var rows = null;
		rows = sysRoleList_datagrid.datagrid('getChecked');
		var ids = [];
		if(rows != ""){
			$.messager.confirm('系统提示', '您确认要删除吗？', function(r){
				if (r){
					for(var i = 0; i < rows.length; i++){
						if(rows[i].roleId == '1'){
							alert('没有删除超级管理员角色的权限!');
							return;
						}
						ids.push(rows[i].roleId);
					}
					sysRoleList_datagrid.datagrid('clearChecked');
					//alert(ids);
					$.post(
							EasySSH.basePath +'role/delete/' + ids.join(','),
							{time : new Date()},
							
							function(r){
								if(r.statusCode == 200){
									sysRoleList_datagrid.datagrid('load');
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
function exportRoleExcl(){
    $.messager.confirm('系统提示', '导出Excl?', function(r){
        if(r){
           // window.location.href=EasySSH.basePath + 'role/export/';

            alert('功能未开发');
        }
    })
}