package com.lzy.jurisdcition.ssh.common.sys.test;

import org.junit.Test;

import java.io.File;

/**
 * Created by Dell on 2016/10/3.
 */
public class Tests {


    @Test
    public void testExcl(){
        //在 UNIX 系统上，此字段的值为 '/'；在 Microsoft Windows 系统上，它为 '\'。
        String basePath = System.getProperties().getProperty("user.dir")+ File.separator + "Excel" + File.separator;

        System.out.println(basePath+"++++++++++");
    }
}
