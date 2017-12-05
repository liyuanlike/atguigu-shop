package com.atguigu.util;

import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-fastdfs.xml")
public class StorageClientFactoryBeanTest {

    @Resource
    private FastFileStorageClient storageClient;

    @Test
    public void testUpload() throws IOException {
        File picture = new File("src/test/resources/mountain.jpg");
        FileInputStream input = new FileInputStream(picture);
        byte[] bytes = new byte[input.available()];
        input.read(bytes);
        String name = picture.getName();
        String extName = org.apache.commons.lang3.StringUtils.substringAfterLast(name, ".");
        System.out.println(extName);
        StorePath storePath = storageClient.uploadFile(bytes, "jpg");
        System.out.println(storePath.getFullPath());
    }

}