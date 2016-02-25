package com.damselfly.common.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by v on 2014/7/30.
 */
public class StringUtil {
    public static String getUTF8XMLString(String xml) {
        // A StringBuffer Object
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8="";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
            System.out.println("utf-8 编码：" + xmlUTF8) ;
        } catch (UnsupportedEncodingException e) {
            //  Auto-generated catch block
            e.printStackTrace();
        }
        // return to String Formed
        return xmlUTF8;
    }

    public static void main(String[] args) {
        System.out.println(replaceUnderlineAndfirstToUpper("ni_hao_abc","_",""));
        URL url = ClassLoader.getSystemResource("");
        System.out.println("url = " + url.toString());
        String basePath = StringUtil.class.getResource("/").toString().replaceAll("file:","").replaceFirst("/","");
        File f = new File(basePath);
        basePath = f.getParentFile().getParentFile().getPath();

        System.out.println("basePath = " + basePath);


        System.out.println(Encodes.escapeHtml("/console/security/user/userList?limit=10&offset=0&order=asc&sortName=username&sortOrder=desc&search_email=&search_username=%E8%A5%BF%E7%93%9C&_=1419563345379"));
        System.out.println(Encodes.unescapeHtml("/console/security/user/userList?limit=10&offset=0&order=asc&sortName=username&sortOrder=desc&search_email=&search_username=%E8%A5%BF%E7%93%9C&_=1419563345379"));
        System.out.println(Encodes.escapeXml("/console/security/user/userList?limit=10&offset=0&order=asc&sortName=username&sortOrder=desc&search_email=&search_username=%E8%A5%BF%E7%93%9C&_=1419563345379"));
        System.out.println(Encodes.urlEncode("/console/security/user/userList?limit=10&offset=0&order=asc&sortName=username&sortOrder=desc&search_email=&search_username=%E8%A5%BF%E7%93%9C&_=1419563345379"));
        System.out.println(Encodes.urlDecode("/console/security/user/userList?limit=10&offset=0&order=asc&sortName=username&sortOrder=desc&search_email=&search_username=%E8%A5%BF%E7%93%9C&_=1419563345379"));
        System.out.println(Encodes.urlDecode("/console/security/user/userList?limit=10&offset=0&order=asc&sortName=username&sortOrder=desc&search_email=&search_username=西瓜&_=1419563345379"));
    }


    /**
     * 首字母大写
     *
     * @param srcStr
     * @return
     */
    public static String firstCharacterToUpper(String srcStr) {
        return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param srcStr
     * @return
     */
    public static String firstCharacterToLower(String srcStr) {
        return srcStr.substring(0, 1).toLowerCase() + srcStr.substring(1);
    }
    /**
     * 替换字符串并让它的下一个字母为大写
     * @param srcStr
     * @param org
     * @param ob
     * @return
     */
    public static String replaceUnderlineAndfirstToUpper(String srcStr,String org,String ob)
    {
        String newString = "";
        int first=0;
        while(srcStr.indexOf(org)!=-1)
        {
            first=srcStr.indexOf(org);
            if(first!=srcStr.length())
            {
                newString=newString+srcStr.substring(0,first)+ob;
                srcStr=srcStr.substring(first+org.length(),srcStr.length());
                srcStr=firstCharacterToUpper(srcStr);
            }
        }
        newString=newString+srcStr;
        return newString;
    }
}
