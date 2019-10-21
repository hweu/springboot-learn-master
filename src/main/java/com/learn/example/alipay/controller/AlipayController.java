package com.learn.example.alipay.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.learn.example.alipay.model.AlipayVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/alipay")
public class AlipayController {
    @Value("${ALIPAY.APPID}")
    private String app_id;
    @Value("${ALIPAY.PRIVATEKEY}")
    private String merchant_private_key;
    @Value("${ALIPAY.PUBLICKEY}")
    private String alipay_public_key;
    @Value("${ALIPAY.NOTIFY_URL}")
    private String notify_url;
    @Value("${ALIPAY.RETURNA_URL}")
    private String return_url;
    @Value("${ALIPAY.SIGN}")
    private String sign_type = "RSA2";
    private String charset = "utf-8";

    @Value("${ALIPAY.SERVER}")
    private String gatewayUrL;

    @GetMapping("pay")
    private String alipayPay() throws AlipayApiException {
        //这个应该是从前端端传过来的，这里为了测试就从后台写死了
        AlipayVo vo = new AlipayVo();
        vo.setOut_trade_no(UUID.randomUUID().toString().replace("-", ""));
        vo.setTotal_amount("0.01");
        vo.setSubject("nelson-test-title");
        vo.setProduct_code("FAST_INSTANT_TRADE_PAY"); //这个是固定的
        String json = (String) JSON.toJSON(vo);
        System.out.println(json);

        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrL, app_id, merchant_private_key, "json",charset,alipay_public_key,sign_type);
        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);
        alipayRequest.setBizContent(json);
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        System.out.println(result);
        return result; //这里生成一个表单，会自动提交
    }

    /**
     * @Title: alipayNotify
     * @Description: 支付宝回调接口
     * @author nelson
     * @param request
     * @param out_trade_no 商户订单号
     * @param trade_no 支付宝交易凭证号
     * @param trade_status 交易状态
     * @throws AlipayApiException
     * @return String
     * @throws
     */
    @PostMapping("notify")
    private String alipayNotify(HttpServletRequest request, String out_trade_no, String trade_no, String trade_status)
            throws AlipayApiException {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                System.out.println(valueStr);
            }
            map.put(name, valueStr);
        }
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(map,alipay_public_key,charset,sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ("fail");// 验签发生异常,则直接返回失败
        }
        if (signVerified) {
            //处理你的业务逻辑，更细订单状态等
            return ("success");
        } else {
            System.out.println("验证失败,不去更新状态");
            return ("fail");
        }
    }

    /**
     * @Title: alipayReturn
     * @Description: 支付宝回调接口
     * @author nelson
     * @param request
     * @param out_trade_no 商户订单号
     * @param trade_no 支付宝交易凭证号
     * @param trade_status 交易状态
     * @throws AlipayApiException
     * @return String
     * @throws
     */
    @GetMapping("return")
    private String alipayReturn(Map<String, String> params, HttpServletRequest request, String out_trade_no,String trade_no,String total_amount)
            throws AlipayApiException {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                System.out.println(valueStr);
            }
            map.put(name, valueStr);
        }
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(map, alipay_public_key, charset, sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ("fail");// 验签发生异常,则直接返回失败
        }
        if (signVerified) {
            return ("success");
        } else {
            System.out.println("验证失败,不去更新状态");
            return ("fail");
        }
    }
}
