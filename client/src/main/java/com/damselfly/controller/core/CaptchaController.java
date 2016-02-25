package com.damselfly.controller.core;

import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.util.CryptUtil;
import com.damselfly.common.util.Result;
import com.damselfly.service.core.RedisService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author V
 * @version 1.0
 * @description: 验证码
 * @createDate 2014-1-25;上午11:02:48
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController extends BaseAction {

    @Autowired
    private Producer captchaProducer;

    @Autowired
    private RedisService redisService;

    private static final int SESSION_VAL_TIME_SPAN = 18000;
    final Long expireTime = new Date().getTime() + SESSION_VAL_TIME_SPAN + (5 * 60);

    /**
     * 生成验证码
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/captchaing")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        // store the text in the session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "png", out);
        //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
        //encoder.encode(bi);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }


    /**
     * 根据盐值生成二维码,并插入redis
     *
     * @param salt
     * @return
     * @throws Exception
     */
    @RequestMapping("/zxing/{salt}")
    public ModelAndView zxing(@PathVariable String salt) throws Exception {

        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/png");

        String type = "ODS";
        CryptUtil des2 = new CryptUtil(salt);// 自定义密钥
        final String key = des2.encrypt(type);

        redisService.set(key, type, expireTime);


        int width = 300;
        int height = 300;
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(key, BarcodeFormat.QR_CODE, width, height, hints);
        //二维码
        BufferedImage bi = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "png", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    /**
     * 刷新获取当前盐值的状态
     *
     * @param salt
     * @return
     * @throws Exception
     */
    @RequestMapping("/zxingFlush/{salt}")
    @ResponseBody
    public Result zxingFlush(@PathVariable String salt) throws Exception {
        Result result = new Result();
        String type = "ODS";
        CryptUtil des2 = new CryptUtil(salt);// 自定义密钥
        final String key = des2.encrypt(type);
        String value = redisService.get(key);
        if (value != null) {
            if (value.indexOf(type) > -1 && value.indexOf("-OK") > -1) {
                result.setSuccessful(true);
                result.setMsg("ok");
            } else {
                result.setSuccessful(false);
            }
        } else {
            result.setSuccessful(false);
        }
        return result;
    }

    /**
     * 更具加密的手机号码更新盐值的状态
     *
     * @param salt
     * @param mobile
     * @return
     * @throws Exception
     */
    @RequestMapping("/zxingUpdate/{salt}/{mobile}")
    @ResponseBody
    public Result zxingUpdate(@PathVariable String salt, @PathVariable String mobile) throws Exception {
        Result result = new Result();
        String type = "ODS";
        CryptUtil des2 = new CryptUtil(salt);// 自定义密钥
        final String key = des2.encrypt(type);
        result.setSuccessful(false);
        if (StringUtils.equals("15869100507", mobile.trim())) {
            redisService.append(key, "-OK");
            result.setMsg("激活登陆");
            result.setSuccessful(true);
        }
        return result;
    }


}
