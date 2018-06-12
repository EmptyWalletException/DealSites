package com.kingguanzhang.dealsites.util;

/**
 * 用于处理图片路径的工具类
 */
public class PathUtil {

    private static String seperator = System.getProperty("file.separator");

    /**
     * 用于根据操作系统的不同获取对应的图片储存的基础路径;
     * @return
     */
    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath="";
        if (os.toLowerCase().startsWith("win")){
            basePath="D:/projectdev/images/";
        }else {
            basePath="/home/projectdev/images/";
        }
        return basePath.replace("/",seperator);
    }

    /**
     * 获取商店对应的图片路径;
     * @param shopId
     * @return
     */
    public static String getShopImagePath(long shopId){
        String imagePath="upload/item/shop/" + shopId + "/";
        return imagePath.replace("/",seperator);
    }

    /**
     * 获取商品对应的图片路径;
     * @param productId
     * @return
     */
    public static String getProductImagePath(long productId){
        String imagePath="upload/item/product/" + productId + "/";
        return imagePath.replace("/",seperator);
    }
}
