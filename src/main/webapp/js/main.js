/**
 * 主界面相关处理函数
 */
	// 系统日期显示
$(function(){

	$.ajax({
		url:EasySSH.basePath+'login/menuTree',
		method:'post',
		dataType:'Json',
		success:function(j){
            //console.info(j.data);
			$('#menu').tree({
				data:j.data,
				onClick: function(node){
					//数据库存好的URL 页面的地址
					if(node.url){
						//console.info(node.id+"  menuId");
						addTabToCenterTabs({
							title: node.text,
							iconCls: node.iconCls,
							closable: true,
							href: EasySSH.basePath + node.url +'?menuId='+node.id
						});
					}
				}
			})	
		}
	})
	
	if($("#showtime").length>0){
		setInterval(function(){
			var now=new Date();
			var yyyy=now.getFullYear();
			var MM=now.getMonth()+1;
			var dd=now.getDate();
			var HH=now.getHours();
			var mm=now.getMinutes();
			var ss=now.getSeconds();
			
			MM=MM<10?"0"+MM:MM;
			dd=dd<10?"0"+dd:dd;
			mm=mm<10?"0"+mm:mm;
			ss=ss<10?"0"+ss:ss;
			$("#showtime").html(yyyy+"年"+MM+"月"+dd+"日 "+HH+":"+mm+":"+ss);
		},1000);
	}
	
	
	//普通树菜单初始化
	
	
	
	/**
	 * 打开修改密码对话框
	 */
	$("#btnChangePwd").on("click", function() {
		//$("#dialogChangePwd").dialog({ closed: false});
		//禁用表单验证，清除上一次弹出后的验证信息
		/*uiEx.disableValidate("#formChangePwd");*/
		uiEx.openDialog("#dialogChangePwd");
	});

	
	/**
	 * easyui主题切换下拉菜单
	 */
	$("#themeCombobox").combobox(
		{
		editable : false,
		panelHeight : "auto",
		valueField : "value",
		textField : 'text',
		url : EasySSH.basePath
				+ "easyssh/json/easyui.theme.combobox.json",
		onSelect : function(selObj) {
			
			
			document.getElementById("themeLink").href = EasySSH.basePath
					+ "easyui/themes/"
					+ selObj.value
					+ "/easyui.css";
			
			$.cookie('ui_theme', selObj.value, { expires: 365, path: '/' });
		},
		// 加载成功后设置默认值
		onLoadSuccess : function() {
			var defaultTheme = "bootstrap";
			
			var theme=$.cookie('ui_theme');
			if(theme){
				defaultTheme = theme;
			}
			 $("#themeCombobox").combobox("setValue",
			 defaultTheme);
			 document.getElementById("themeLink").href=EasySSH.basePath+"easyui/themes/"+defaultTheme+"/easyui.css";
		}
	});
})


function addTabToCenterTabs(opts){
		var centerTabs = $('#tabs');
		if(centerTabs.tabs('exists', opts.title)){
			centerTabs.tabs('select', opts.title);
		}else{
			centerTabs.tabs('add', opts);
		}
	}