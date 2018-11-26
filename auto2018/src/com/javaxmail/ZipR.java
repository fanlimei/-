package com.javaxmail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipR {
	public class ZipCompressor {
	    static final int BUFFER = 8192;
	    private File zipFile;
	    /**
	     * ѹ�������캯��
	     * 
	     * @param pathName ѹ����·��<br>
	     *            e.g /Users/Admin/Desktop/test.zip
	     */   
	public ZipCompressor(String pathName) {
	        zipFile = new File(pathName);
	        if (zipFile.exists()) {
	            System.out.println("ɾ���ɵ�ѹ���ļ�:" + pathName);
	            zipFile.delete();
	        }
	    }
	/**
     * ѹ��һ���ļ�,����һ���ļ���
     * 
     * @param srcPathName �ļ�·��,���ļ���·��
     */
	public void compress(String srcPathName) {
        File file = new File(srcPathName);
        if (!file.exists())
            throw new RuntimeException(srcPathName + "������!");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
                    new CRC32());
            ZipOutputStream out = new ZipOutputStream(cos);
            String basedir = "E:\\ddo";
            compress(file, out, basedir);
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	 /**
     * ѹ������ļ�
     * 
     * @param filePaths �ļ�·��
     */
	public void compress(String[] filePaths) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
                    new CRC32());
            ZipOutputStream out = new ZipOutputStream(cos);
            String basedir = "E:\\ddo";
            for (int i = 0; i < filePaths.length; i++) {
                compress(new File(filePaths[i]), out, basedir);
            }
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	private void compress(File file, ZipOutputStream out, String basedir) {
        /* �ж���Ŀ¼�����ļ� */
        if (file.isDirectory()) {
            System.out.println("ѹ��:" + basedir + file.getName());
            this.compressDirectory(file, out, basedir);
        } else {
            System.out.println("ѹ��:" + basedir + file.getName());
            this.compressFile(file, out, basedir);
        }
    }
	/** ѹ��һ��Ŀ¼ */
	private void compressDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists())
            return;

        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            /* �ݹ� */
            compress(files[i], out, basedir + dir.getName() + "/");
        }
    }
	/** ѹ��һ���ļ� */
	private void compressFile(File file, ZipOutputStream out, String basedir) {
        if (!file.exists())
            return;
        try {
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));
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
}