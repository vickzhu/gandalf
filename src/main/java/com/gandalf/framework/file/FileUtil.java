package com.gandalf.framework.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gandalf.framework.constant.SymbolConstant;
import com.gandalf.framework.util.StringUtil;

/**
 * 类FileUtil.java的实现描述：文件常量
 * 
 * @author gandalf 2014-3-4 下午3:39:34
 */
public class FileUtil {

    public static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 构建文件保存路径
     * 
     * @param pattern 路径模式，会在文件名前面添加这个模式的路径
     * @param fileName
     * @param rename 是否重命名
     * @return
     */
    public static String buildSavePath(FilePathPattern pattern, String fileName, boolean rename) {
        if (pattern == null) {
            throw new RuntimeException("FilePathPattern is needed!");
        }
        StringBuffer sb = new StringBuffer();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        if (pattern == FilePathPattern.YYYY_MM_DD) {
            sb.append(year).append(SymbolConstant.SLASH).append(month).append(SymbolConstant.SLASH).append(date);
        } else if (pattern == FilePathPattern.YYYY_MM) {
            sb.append(year).append(SymbolConstant.SLASH).append(month);
        }
        sb.append(SymbolConstant.SLASH);
        if (rename) {// 重命名
            int index = fileName.lastIndexOf(SymbolConstant.DOT);
            if (index == -1) {
                fileName = StringUtil.getRand(16);
            } else {
                fileName = StringUtil.getRand(16) + fileName.substring(index);
            }
        }
        sb.append(fileName);
        return sb.toString();
    }

    private static File createFile(String filePath, boolean isCover) {
        if (StringUtil.isBlank(filePath)) {
            throw new RuntimeException("Param [" + filePath + "] is needed!");
        }
        File file = new File(filePath);
        if (file.exists() && !isCover) {// 文件已经存在
            throw new RuntimeException("File [" + filePath + "] already exist!");
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return file;
    }

    /**
     * 保存二进制文件
     * 
     * @param absolutePath
     * @param is
     * @param isCover 是否覆盖
     * @throws IOException
     */
    public static void saveFile(String absolutePath, InputStream is, boolean isCover) throws IOException {
        File file = createFile(absolutePath, isCover);
        byte[] b = new byte[1024];
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            while (is.read(b) != -1) {
                os.write(b);
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found!", e);
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("OutputStream close failed!", e);
                }
            }
        }
    }

    /**
     * 读取二进制文件
     * 
     * @param filePath
     * @throws IOException
     */
    public static byte[] readBinaryFile(String absolutePath) throws IOException {
        if (StringUtil.isBlank(absolutePath)) {
            throw new RuntimeException("Param [" + absolutePath + "] is needed!");
        }
        File file = new File(absolutePath);
        if (!file.exists()) {
            throw new RuntimeException("File [" + absolutePath + "] not exist!");
        }
        byte[] b = new byte[(int) file.length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(b);
        } catch (FileNotFoundException e) {
            logger.error("File not found!", e);
        } catch (IOException e) {
            throw e;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("FileInputStream close failed!", e);
                }
            }
        }
        return b;
    }

    /**
     * 保存文本文件
     * 
     * @param absolutePath
     * @param content
     * @param isCover
     * @throws IOException
     */
    public static void saveFile(String absolutePath, String content, boolean isCover) throws IOException {
        File file = createFile(absolutePath, isCover);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(content);
        } catch (IOException e) {
            throw e;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    logger.error("BufferedWriter close failed!", e);
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    logger.error("FileWriter close failed!", e);
                }
            }
        }

    }

    /**
     * 读取文本本件
     * 
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String readTextFile(String absolutePath) throws IOException {
        if (StringUtil.isBlank(absolutePath)) {
            throw new RuntimeException("Param [" + absolutePath + "] is needed!");
        }
        File file = new File(absolutePath);
        if (!file.exists()) {
            throw new RuntimeException("File [" + absolutePath + "] not exist!");
        }
        StringBuffer sb = new StringBuffer();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            while (br.read() != -1) {
                sb.append(br.readLine());
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found!", e);
        } catch (IOException e) {
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("BufferedReader close failed!", e);
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    logger.error("FileReader close failed!", e);
                }
            }
        }
        return sb.toString();
    }

}
