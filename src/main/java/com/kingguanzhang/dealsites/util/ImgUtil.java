package com.kingguanzhang.dealsites.util;


import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * 用于处理图片的工具类
 */
public class ImgUtil {

    //获取当前线程运行所在的路径;
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    //简单时间格式对象和随机数,用于拼接成唯一文件名;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    public static String generateThumbnail(InputStream shopImgInputStream, String targetAddr,String fileName){
        String realFileName = getRandomFileName();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName +extension;
        String imgAddr = PathUtil.getImgBasePath()+ relativeAddr;
        File dest = new File (imgAddr);

        System.out.print(dest.getPath());
        try{
            //将Thumbnail传入进来的图片流写入到指定的文件夹路径里;
           // File dest2 = new File ("D:/test.jpg");
           // Thumbnails.of(dest2).size(200,200).toFile(dest);
            //Thumbnails.of(dest2).size(200,200).toFile("D:/1.jgp");
            Thumbnails.of(shopImgInputStream).size(1920,1920).outputQuality(1.0D).toFile(dest);//size是将图片自动缩放成适应的最大像素,会保持长宽比;
           // Thumbnails.of(shopImgInputStream).toFile(dest);
        }catch (IOException e) {
            //如果图片文件保存失败则返回一个默认的图片路径;
            imgAddr = "D:/test.jpg";
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 生成随机文件名;
     * @return
     */
    private static String getRandomFileName() {
        int randowNum = r.nextInt(89999) + 10000;
        String datetime = sdf.format(new Date());
        String realFileName =randowNum + datetime;
        return realFileName;
    }

    /**
     * 获取文件扩展名;
     * @param thumbnailFile
     * @return
     */
    /*private static String getFileExtension(File thumbnailFile) {
        String originalFileName = thumbnailFile.getPath();
        //截取最后一个"."号之后的字符串,也就是文件的扩展名,例如".jpg"
        String extension =originalFileName.substring(originalFileName.lastIndexOf("."));
        return extension;
    }*/

    /**
     * 生成文件路径所涉及到的目录;
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("D:test.jpg")).size(100,100).toFile("D:test2.jpg");
    }
}
