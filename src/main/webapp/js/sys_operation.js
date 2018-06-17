
var sysOperationList_datagrid;
var operationAddRemark = UE.getEditor('operationAddRemark');
var operationEditRemark = UE.getEditor('operationEditRemark');
var menuId = '';
	$(function(){
		//初始化datagrid
        /**
         * list
         * @type {*|jQuery}
         */
		sysOperationList_datagrid = $('#sysOperationList_datagrid').datagrid({
			title: '操作列表',
			//iconCls: 'icon-man',
			url: EasySSH.basePath +'operation/list' ,
			//默认是POST请求方式
			method: 'post',
			idField: 'operationId',
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
			    {field:'operationId',title:'编号',width:30,checkbox:true},
			    {field:'operationName',title:'操作名称',width:60,sortable:true},
			    {field:'sysMenu.menuName',title:'所属菜单',width:60,sortable:true},
			    {field:'operationRemark',title:'备注',width:60,sortable:true}
			]]
			
		})
		$.ajax({
			url:EasySSH.basePath +'operation/operations',
			method:'get',
			dataType:'json',
			success:function(r){
				if(r){

					var data = r.data;
					var temp = '';
					//console.log(data);
					for(var i=0;i<data.length;i++){
						if(data[i].sysMenu != null && data[i].sysMenu.menuId == r.statusCode) {
                          //  alert(r.statusCode);
                            //console.info(r.statusCode+' role');
                            temp = temp + '<input type="button" value = "' + data[i].operationName + '" onclick="' + data[i].operationAction + '"/>';

                        }
                    }
						$('#operationToolbar').html(temp);
						$('#sysOperationList_datagrid').datagrid({
							toolbar:'#operationToolbar'	
						})
					
				}
			}
		})

        /**
         * edit
         */
        $('#sysOperationEdit').bind('click',function(){
            var menu = $('#operationMenu').tree('getChecked');
            var menuIds = '';
            //console.info(menu);
            for(var i =0;i <menu.length; i++){
                if(menu.length > 1 && menu[i].id != menuId){

                }else{
                    menuIds += menu[i].id +',';
                }
            }
            ids = menuIds.substr(0,menuIds.length-1);
            var operationEditRemarkValue = operationEditRemark.getContentTxt();
            $('#operationRemarkInput').attr('value' , operationEditRemarkValue);
            //alert( $('#operationRemarkInput').val());
            sysOperationList_datagrid.datagrid('clearChecked');
            //console.info(str);
            //console.info(ids);
            $('#sysOperationEdit_form').form({
                url : EasySSH.basePath +'operation/update',
                method:'post',
                queryParams:{
                    menuIds:ids
                },
                success: function(r){
                    var r = JSON.parse(r);
                    //alert(r.statusCode);
                    if(r.statusCode == 200){
                        $('#sysOperationEdit_dialog').dialog('close');
                        $('#sysOperationList_datagrid').datagrid('load');
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
        $('#sysOperationAdd').bind('click',function(){
            var operationAddRemarkValue = operationAddRemark.getContentTxt();
            $('#operationRemarkInput').attr('value' , operationAddRemarkValue);

            var menuIds = $('#operationMenus').tree('getChecked');
            //console.info(menuIds);
            var ids = '';
            for(var a = 0;a < menuIds.length; a++){
                ids += menuIds[a].id + ',';
            }
            ids = ids.substr(0,ids.length-1);

            //alert(ids);
            $('#sysOperationAdd_form').form({
                url : EasySSH.basePath +'operation/add',
                method:'post',
                queryParams:{
                    menuIds:ids
                },
                success: function(r){
                    var r = JSON.parse(r);
                    //alert(r.statusCode);
                    if(r.statusCode == 200){
                        sysOperationList_datagrid.datagrid('clearChecked');
                        $('#sysOperationAdd_dialog').dialog('close');
                        $('#sysOperationList_datagrid').datagrid('load');

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
	function operationAdd(){
        sysOperationList_datagrid.datagrid('clearChecked');
        //判断ueditor 编辑器是否创建成功
        operationAddRemark.ready(function(){
            operationAddRemark.setContent('');
        })
		//打开ID 为sysUserAdd_dialog的easyui-dialog对话框
		$('#sysOperationAdd_dialog').dialog('open');
		//清空easyui-dialog对话框的数据
		$('.easyui-textbox').textbox('setText', '');
		$('.easyui-combobox').textbox('setText', '');
        //构建菜单树列表
        $.ajax({
            url:EasySSH.basePath +'login/menuTree',
            method:'post',
            dataType:'Json',
            success:function(j){
                //console.info(j);
                $('#operationMenus').tree({
                    data:j.data,
                    checkbox:true,
                    cascadeCheck:false
                })
            }
        })

	}
	
	//修改
	function operationEdit(){
		//获取datagrid选中的行数
		var rows = sysOperationList_datagrid.datagrid('getChecked');
		if(rows.length == 1){
			$('#sysOperationEdit_dialog').dialog('open');
			//回显数据到修改页面
			$('#sysOperationEdit_form').form('load', rows[0]);
			
			//处理ueditor
			var operationEditRemark = UE.getEditor('operationEditRemark');
			var operationEditValue = $('#operationEditRemarkInput').val()
			operationEditRemark.ready(function(){
				operationEditRemark.setContent(operationEditValue);    
			})

			//对菜单权限进行 额外处理
			menuId = rows[0].sysMenu.menuId;
            sysOperationList_datagrid.datagrid('clearChecked');
			//console.info(menuId);
			$.ajax({
				url:EasySSH.basePath +'login/menuTree',
				method:'post',
				dataType:'Json',
				success:function(j){
					//console.info(j.data);
					$('#operationMenu').tree({
						data:j.data,
						checkbox:true,
						cascadeCheck:false,
						formatter:function(node){
							//console.info(menus);
							//debugger;
							node.checked = false;
							if(menuId == node.id){	
								//console.info(menus[m]);
				                //console.info(node);
								node.checked = true;
							}
							return node.text;
						}

					})	
				}
			})

		}else{
			$.messager.alert('系统提示','请选择一项进行操作！','info');
		}
	}
	
	//删除
	function operationDele(){
		//获取datagrid选中的行数
		var rows = null;
		rows = sysOperationList_datagrid.datagrid('getChecked');
		var ids = [];
		if(rows != ""){
			$.messager.confirm('系统提示', '您确认要删除吗？', function(r){
				if (r){
					for(var i = 0; i < rows.length; i++){
                        if(rows[i].operationId < '17'){
                            alert('没有删除系统默认操作的权限!');
                            sysOperationList_datagrid.datagrid('clearChecked');
                            return;
                        }
						ids.push(rows[i].operationId);
					}
					sysOperationList_datagrid.datagrid('clearChecked');
					//alert(ids);
					$.post(
							EasySSH.basePath +'operation/delete/' + ids.join(','),
							{time : new Date()},
							function(r){
								if(r.statusCode == 200){
									sysOperationList_datagrid.datagrid('load');
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
function exportOperationExcl(){
    $.messager.confirm('系统提示', '导出Excl?', function(r){
        if(r){
            window.location.href=EasySSH.basePath + 'operation/export/';
        }
    })
}