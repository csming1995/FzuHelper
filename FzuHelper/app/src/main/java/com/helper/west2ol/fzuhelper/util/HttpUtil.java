package com.helper.west2ol.fzuhelper.util;

/** Login
 * Http request
 * Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*
 *  /*;q=0.8
 * Accept-Encoding:gzip, deflate
 * Accept-Language:zh-CN,zh;q=0.8
 * Cache-Control:max-age=0
 * Connection:keep-alive
 * Content-Length:39
 * Content-Type:application/x-www-form-urlencoded
 * Cookie:ASPSESSIONIDAQCAAAQC=JCFOLEEBMHFIFIEDDLCNFAEA
 * Host:59.77.226.32
 * Origin:http://jwch.fzu.edu.cn
 * Referer:http://jwch.fzu.edu.cn/
 * Upgrade-Insecure-Requests:1
 * User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0
*/

public class HttpUtil {
    public static final String LOGIN = "http://59.77.226.32/logincheck.asp";
    // parameter muser,passwwd,x=0,y=0
    public static final String LOGIN_CHK_XS = "http://59.77.226.35/loginchk_xs.aspx";
    // parameter id num       从logincheck.asp获取
    //返回 id

    /**
     * 获取资源
     */
    public static final String BASE = "http://59.77.226.35/";
    public static final String getCourse = "student/xkjg/wdxk/xkjg_list.aspx"; //课表
    public static final String getGrade = "student/jscp/TeaList.aspx"; //成绩
    public static final String getExamClassRoom = "student/jscp/TeaList.aspx"; //考场 同上

    public static final String getEmptyClassRoom = "kkgl/kbcx/kbcx_choose.aspx"; //空教室


    public static void Login(String muser , String passwwd ){

    }


}
