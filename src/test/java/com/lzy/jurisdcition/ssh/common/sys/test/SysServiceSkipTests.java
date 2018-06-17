package com.lzy.jurisdcition.ssh.common.sys.test;

import javax.annotation.Resource;

import com.lzy.jurisdcition.ssh.common.sys.entity.SysRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lzy.jurisdcition.ssh.common.sys.entity.SysUser;
import com.lzy.jurisdcition.ssh.common.sys.service.SysMenuService;
import com.lzy.jurisdcition.ssh.common.sys.service.SysOperationService;
import com.lzy.jurisdcition.ssh.common.sys.service.SysRoleService;
import com.lzy.jurisdcition.ssh.common.sys.service.SysUserService;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext_test.xml"})
public class SysServiceSkipTests {

    @Resource(name = "user")
    private SysUserService sysUserService;
    @Resource(name = "menu")
    private SysMenuService sysMenuService;
    @Resource(name = "operation")
    private SysOperationService sysOperationService;
    @Resource(name = "role")
    private SysRoleService sysRoleService;


    @Test
    public void test() {
//		System.out.println("hello world");
        Set<SysRole> roles = new HashSet<SysRole>();
        SysRole r = new SysRole();
        r.setRoleId(1);
        roles.add(r);
        SysUser u = new SysUser();
        u.setUserName("lisi");
        u.setUserRealName("李四");
        u.setUserPassword("123");
        u.setSysRoles(roles);
        u.setUserStatus(1);
        sysUserService.save(u);
//		Map map = sysOperationService.getAllOpreationNames();
//		System.out.println(map);
//		List lm = sysOperationService.getIdsByRoleId(1);
//		System.out.println(lm);
//		List l = sysRoleService.getAllPermissionsIds(1);
//		System.out.println(l);
//		System.out.println(sysRoleService.findMaxPage(4));
//		sysRoleService.list();
//		sysRoleService.list();
//		sysRoleService.list();
        //System.out.println(sysRoleService.list().get(0));
//		sysRoleService.delete(3);
//		SysRole re = new SysRole();
//		re.setRoleName("gly");
//		re.setRoleStatus("1");
//		sysRoleService.add(re);

        //SysMenu 测试
//		SysMenu menu2 = new SysMenu();
//		menu2.setMenuId(2);
//		menu2.setMenuName("bbb");
//		sysMenuService.delete(menu2);
//		System.out.println(sysMenuService.getIdsByRoleId(3));
//		System.out.println(sysMenuService.hashChildMenu(2));
//		sysMenuService.move(2, true);
//		System.out.println(sysMenuService.getSortOrder(2));
//		System.out.println(sysMenuService.getMaxSortOrder(null));
//		System.out.println(sysMenuService.list().get(0));
//		SysMenu menu1 = new SysMenu();
//		menu1.setMenuId(2);
//		menu1.setMenuName("菜单");
//		System.out.println(sysMenuService.get(2));
//		SysMenu menu1 = new SysMenu();
//		menu1.setMenuId(1);
//		menu1.setMenuName("菜单");
//		sysMenuService.delete(menu1);
//		SysMenu menu = new SysMenu();
//		menu.setMenuName("菜单");
//		sysMenuService.add(menu);


        //SysUserService测试
//		SysUser user = new SysUser();
//		user.setUserName("lzy");
//		user.setUserPassword("123");
//		SysUser user1 = sysUserService.login(user);

//		System.out.println((user1.getSysRoles()));
//		System.out.println(sysUserService.getPwd(1));
//		sysUserService.delete(1);
//		sysUserService.delete(new String[]{"1","3"});
//		user.setUserId(1);
//		user.setUserName("ttt");
//		user.setUserStatus(1);
//		System.out.println(sysUserService.get(1));
//		PageBean pb = new PageBean();
//		pb.setRowsPerPage(3);
//		pb.setPageNo(1);
//		SysUserCriteria sc = new SysUserCriteria();
//		sc.setStatus(1);
//		sysUserService.findByPage(pb, sc);
//		System.out.println((SysUser)(pb.getData().get(0)));
//		sysUserService.changePwd(1, "33333");
//		System.out.println(sysUserService.existsName("tt",1));
//		System.out.println(sysUserService.findMaxPage(5));


    }


//	public SysUserService getSysUserService() {
//		return sysUserService;
//	}
//	public void setSysUserService(SysUserService sysUserService) {
//		this.sysUserService = sysUserService;
//	}
//	public SysMenuService getSysMenuService() {
//		return sysMenuService;
//	}
//	public void setSysMenuService(SysMenuService sysMenuService) {
//		this.sysMenuService = sysMenuService;
//	}
//	public SysOperationService getSysOperationService() {
//		return sysOperationService;
//	}
//	public void setSysOperationService(SysOperationService sysOperationService) {
//		this.sysOperationService = sysOperationService;
//	}
//	public SysRoleService getSysRoleService() {
//		return sysRoleService;
//	}
//	public void setSysRoleService(SysRoleService sysRoleService) {
//		this.sysRoleService = sysRoleService;
//	}


}
