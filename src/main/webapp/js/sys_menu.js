
var sysMenuList_datagrid;
var menuAddRemark = UE.getEditor('menuAddRemark');
var menuEditRemark = UE.getEditor('menuEditRemark');
var menuId = '';
	$(function(){

        /**
         * list
         */

		//初始化datagrid
		sysMenuList_datagrid = $('#sysMenuList_datagrid').datagrid({
			title: '菜单列表',
			//iconCls: 'icon-man',
			url: EasySSH.basePath +'menu/list' ,
			//默认是POST请求方式
			method: 'post',
			idField: 'menuId',
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
			    {field:'menuId',title:'编号',width:30,checkbox:true},
			    {field:'menuName',title:'菜单名称',width:60,sortable:true},
			    {field:'menuAction',title:'路径',width:60,sortable:true},
			    {field:'menuIcon',title:'小标图',width:60,sortable:true},
			    {field:'menuParent',title:'父菜单',width:60,sortable:true,
			    	formatter: function(value,row){
                        //console.info(row);
						if (row.menuParent){
							return row.menuParent.menuName;
						}
					}
                 },
			    {field:'menuRemark',title:'备注',width:60,sortable:true}
			]]
			
		})
		$.ajax({
			url:EasySSH.basePath +'menu/operations',
			method:'get',
			dataType:'json',
			success:function(r){
				if(r){
					var data = r.data;
					var temp = '';
					//console.log(data);
					for(var i=0;i<data.length;i++){
						if(data[i].sysMenu != null && data[i].sysMenu.menuId == r.statusCode){
							//console.info(r.statusCode+' role');
                            temp =temp+ '<input type="button" value = "'+data[i].operationName+'" onclick="' + data[i].operationAction + '"/>';
							}
					   }
						$('#menuToolbar').html(temp);
						$('#sysMenuList_datagrid').datagrid({
							toolbar:'#menuToolbar'	
						})
					
				}
			}
		})

        /**
         * add
         */
        $('#sysMenuAdd').bind('click',function(){

            //处理uedit
            var menuAddRemarkValue = menuAddRemark.getContentTxt();
            $('#menuRemarkInput').attr('value' , menuAddRemarkValue);

            var menuParentIds = $('#menuParents').tree('getChecked');
            //console.info(menuParentIds);
            var ids = '';
            if(menuParentIds){
                for(var a = 0;a < menuParentIds.length; a++){
                    ids += menuParentIds[a].id + ',';
                }
                ids = ids.substr(0,ids.length-1);
            }

            //alert(ids);
            $('#sysMenuAdd_form').form({
                url : EasySSH.basePath +'menu/add',
                method:'post',
                queryParams:{
                    menuParents:ids
                },
                success: function(r){
                    var r = JSON.parse(r);
                    //alert(r.statusCode);
                    if(r.statusCode == 200){
                        sysMenuList_datagrid.datagrid('clearChecked');
                        $('#sysMenuAdd_dialog').dialog('close');
                        $('#sysMenuList_datagrid').datagrid('load');
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
         * edit
         */
        $('#sysMenuEdit').bind('click',function(){
            var editMenuParents = $('#menuEditParent').tree('getChecked');
            var ids = '';
            //console.info(menu);
            for(var i =0;i <editMenuParents.length; i++){
                if(editMenuParents.length > 1 && editMenuParents[i].id != menuId){
                    ids += editMenuParents[i].id +',';
                }else{
                    ids += editMenuParents[i].id +',';
                }
            }
            ids = ids.substr(0,ids.length-1);
            var menuEditRemarkValue = menuEditRemark.getContentTxt();
            $('#menuRemarkInput').attr('value' , menuEditRemarkValue);
            //console.info(str);
            //console.info(ids);
            $('#sysMenuEdit_form').form({
                url : EasySSH.basePath +'menu/update',
                method:'post',
                queryParams:{
                    parentIds:ids
                },
                success: function(r){
                    var r = JSON.parse(r);
                    //alert(r.statusCode);
                    if(r.statusCode == 200){
                        $('#sysMenuEdit_dialog').dialog('close');
                        $('#sysMenuList_datagrid').datagrid('load');
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
	function menuAdd(){
        sysMenuList_datagrid.datagrid('clearChecked');
        menuAddRemark.ready(function(){
            menuAddRemark.setContent('');
        })
		//打开ID 为sysUserAdd_dialog的easyui-dialog对话框
		$('#sysMenuAdd_dialog').dialog('open');
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
                $('#menuParents').tree({
                    data:j.data,
                    checkbox:true,
                    cascadeCheck:false
                })
            }
        })
	}
	
	//修改
	function menuEdit(){
		//获取datagrid选中的行数
		var rows = sysMenuList_datagrid.datagrid('getChecked');
       // console.info(rows[0]);
		if(rows.length == 1){
            if(rows[0].menuId == 1){
                alert("不可修改!");
                sysMenuList_datagrid.datagrid('clearChecked');
                return;
            }
			$('#sysMenuEdit_dialog').dialog('open');
			//回显数据到修改页面
			$('#sysMenuEdit_form').form('load', rows[0]);
			//对角色的菜单权限 以及操作权限进行 额外处理
			
			//处理ueditor
			var menuEditRemark = UE.getEditor('menuEditRemark');
			var menuEditValue = $('#menuEditRemarkInput').val()
			menuEditRemark.ready(function(){
				menuEditRemark.setContent(menuEditValue);    
			})
			
			//对菜单权限进行 额外处理
            var myId = rows[0].menuId;
            if(rows[0].menuParent){
                menuId = rows[0].menuParent.menuId;
            }
            sysMenuList_datagrid.datagrid('clearChecked');
			//console.info(menuId);
			$.ajax({
				url:EasySSH.basePath +'login/menuTree',
				method:'post',
				dataType:'Json',
				success:function(j){
					console.info(j.data);
                    var data = j.data;
                    isEquest(data,myId);
					$('#menuEditParent').tree({
						data:data,
						checkbox:true,
						cascadeCheck:false,
						formatter:function(node){
							//console.info(menus);
							//debugger;
                            node.checked = false;
                            if(menuId != null && menuId == node.id){
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

   function isEquest(arr , id){
        for(var i = 0; i<arr.length; i++){
           // debugger;

            //console.info(arr[i].length);
           if(arr[i].children.length >1){
               isEquest(arr[i].children,id);
           }else {
               if (arr[i].id == id) {
                   arr.splice(i,1);
               }
           }
       }
   }
	
	//删除用户
	function menuDele(){
		//获取datagrid选中的行数
		var rows = null;
		rows = sysMenuList_datagrid.datagrid('getChecked');
		var ids = [];
		if(rows != ""){
			$.messager.confirm('系统提示', '您确认要删除吗？', function(r){
				if (r){
					for(var i = 0; i < rows.length; i++){
						if(rows[i].menuId < '6'){
							alert('没有删除\nSSH\n权限管理\n角色管理\n用户管理\n操作管理\n菜单管理\n日志管理\n等菜单的权限!');
							return;
						}
						ids.push(rows[i].menuId);
					}
					sysMenuList_datagrid.datagrid('clearChecked');
					//alert(ids);
					$.post(
							EasySSH.basePath +'menu/delete/' + ids.join(','),
							{time : new Date()},
							
							function(r){
								if(r.statusCode == 200){
									sysMenuList_datagrid.datagrid('load');
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
function exportMenuExcl(){
    $.messager.confirm('系统提示', '导出Excl?', function(r){
        if(r){
            //window.location.href=EasySSH.basePath + 'menu/export/';
            alert('功能未开发');
        }
    })
}