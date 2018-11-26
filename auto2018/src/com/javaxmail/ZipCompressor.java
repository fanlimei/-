package com.javaxmail;

import java.io.BufferedInputStream;    
import java.io.File;    
import java.io.FileInputStream;    
import java.io.FileOutputStream;    
import java.util.zip.CRC32;    
import java.util.zip.CheckedOutputStream;    
  
import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.zip.ZipEntry;    
import org.apache.tools.zip.ZipOutputStream;    
  
/** 
 * @ClassName: ZipCompressor 
 * @CreateTime Apr 28, 2013 1:12:16 PM 
 * @author : Mayi 
 * @Description: ѹ���ļ���ͨ�ù�����-����org.apache.tools.zip.ZipOutputStreamʵ�֣��ϸ��ӡ� 
 * 
 */  
public class ZipCompressor {  
    private Logger logger = Logger.getLogger(ZipCompressor.class);  
    static final int BUFFER = 8192;    
    private File zipFile;    
      
    /** 
     * ѹ���ļ����캯�� 
     * @param pathName ѹ�����ļ����Ŀ¼ 
     */  
    public ZipCompressor(String pathName) {    
        zipFile = new File(pathName);    
    }    
    
    /** 
     * ִ��ѹ������ 
     * @param srcPathName ��ѹ�����ļ�/�ļ��� 
     */  
    public void compressExe(String srcPathName) {    
        File file = new File(srcPathName);    
        if (!file.exists()){  
            throw new RuntimeException(srcPathName + "�����ڣ�");    
        }  
        try {    
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);    
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,new CRC32());    
            ZipOutputStream out = new ZipOutputStream(cos);    
            String basedir = "F:\\Three\\lhzgithub\\auto2018\\test-output\\html";    
            compressByType(file, out, basedir);    
            out.close();    
        } catch (Exception e) {   
            e.printStackTrace();  
            logger.error("ִ��ѹ������ʱ�����쳣:"+e);  
            throw new RuntimeException(e);    
        }    
    }    
    
    /** 
     * �ж���Ŀ¼�����ļ����������ͣ��ļ�/�ļ��У�ִ�в�ͬ��ѹ������ 
     * @param file  
     * @param out 
     * @param basedir 
     */  
    private void compressByType(File file, ZipOutputStream out, String basedir) {    
        /* �ж���Ŀ¼�����ļ� */    
        if (file.isDirectory()) {    
            logger.info("ѹ����" + basedir + file.getName());    
            this.compressDirectory(file, out, basedir);    
        } else {    
            logger.info("ѹ����" + basedir + file.getName());    
            this.compressFile(file, out, basedir);    
        }    
    }    
    
    /** 
     * ѹ��һ��Ŀ¼ 
     * @param dir 
     * @param out 
     * @param basedir 
     */  
    private void compressDirectory(File dir, ZipOutputStream out, String basedir) {    
        if (!dir.exists()){  
             return;    
        }  
             
        File[] files = dir.listFiles();    
        for (int i = 0; i < files.length; i++) {    
            /* �ݹ� */    
            compressByType(files[i], out, basedir + dir.getName() + "/");    
        }    
    }    
    
    /** 
     * ѹ��һ���ļ� 
     * @param file 
     * @param out 
     * @param basedir 
     */  
    private void compressFile(File file, ZipOutputStream out, String basedir) {    
        if (!file.exists()) {    
            return;    
        }    
        try {    
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));    
            ZipEntry entry = new ZipEntry(basedir + file.getName());    
            out.putNextEntry(entry);    
            int count;    
            byte data[] = new byte[BUFFER];    
            while ((count = bis.read(data, 0, BUFFER)) != -1) {    
                out.write(data, 0, count);    
            }    
            bis.close();    
        } catch (Exception e) {    
            throw new RuntimeException(e);    
        }    
    }

	

}  
