package com.kingguanzhang.dealsites.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于转化request中参数的工具,原因是request请求中所有参数都是以String的形式,使用起来比较麻烦
 */
public class RequestUtil {

    //获取请求参数并转化成int类型;
    public static int parserInt(HttpServletRequest request, String key){
        try{
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    //获取请求参数并转化成long类型;
    public static Long parserLong(HttpServletRequest request, String key){
        try{
            return Long.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1L;
        }
    }

    //获取请求参数并转化成double类型;
    public static double parserDouble(HttpServletRequest request, String key){
        try{
            return Double.parseDouble(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    //获取请求参数并转化成boolean类型;
    public static boolean parserboolean(HttpServletRequest request, String key){
        try{
            return Boolean.valueOf(request.getParameter(key));
        }catch (Exception e){
            return false;
        }
    }

    //获取请求参数并转化成String类型;
    public static String parserString(HttpServletRequest request, String key){
        try{


            String result = request.getParameter(key);
            if (null != result){
                result = result.trim();
            }
            if ("".equals(result)){
                result = null;

            }
            return result;
        }catch (Exception e){
            return null;
        }
    }
}
