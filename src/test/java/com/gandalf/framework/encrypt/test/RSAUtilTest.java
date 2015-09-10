package com.gandalf.framework.encrypt.test;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.gandalf.framework.constant.CharsetConstant;
import com.gandalf.framework.encrypt.RSAUtil;
import com.gandalf.framework.test.BaseTest;

/**
 * 类RSAUtil.java的实现描述：RSA加密
 * 
 * @author gandalf 2014-2-21 下午2:22:21
 */
public class RSAUtilTest extends BaseTest {

    public static void main(String[] args) throws Exception {
        Map<String, Object> keyMap = RSAUtil.initKey();
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAITwcS435QVqNRHwAFw0e+M7IPLpaqVvWIRodCTIvcJ0NEYO8rQ2FfUboQ9P7TkLqEutsBN2gQxQoa80PJStCmid4sHJt30ZiPaKcFkCh5l4XWoVtRWAc0ergZ4G9t4IPcmH9d1XoYi6MsUcfjkShcQh5kuvxcsTSbR8xVoaX3onAgMBAAECgYAsUFIKDAvFsE+ceRpFoiEh79Xd4zzvhJxHFuKQWHIY3c+HAviYZecP23PmvMfg2ifxgiZNdpUx27bpxknjbYMoOXWPo93KowoHZpQeikG1ou1kRpXK88uQpBzY224gj5SoexrquqUKHFURjhwyXk0Ynna/zPpt6hN6RzlG+/6twQJBAO2e58vHXPdBjuENvsbh5fB60x0c1IDdkjIKBgrzU5u7cUit70zYW32+ePBF34K0rhjs+RCVeOlMDJfvwJzI9PECQQCPOMDH2kDLiBXWM7E+Vu53g6Iak8hrw0/POnWNc3OTkGHz6lNFT2nr3WOwgA+Q5q8bGROUf7bdsBNv49jBRwCXAkACAUpBbPA64eG7wmrusK9JBTBM2ZZtc/Es3OrKIt18vespytXvFxBGklng5SYKq02gxjcfzbMUfODKU2qTqBpxAkAhEH8hJHf1BRPVgdWXiMZA/Ti9XgTVLCAOvXZKE69JD/otL5nN1ImllFTheI8fasJeRSnKDLoQ8Adsu3NdG6E9AkEA5dVwPNfs8BM0eVeGM/HiPKofaW2ay0D+FXnTrE/GHhzvW+MB4/getfsGzxmtr5Gab+lhRKGAdbTvJi5NwvyBRQ==";
        System.out.println("private key :" + privateKey);
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCE8HEuN+UFajUR8ABcNHvjOyDy6Wqlb1iEaHQkyL3CdDRGDvK0NhX1G6EPT+05C6hLrbATdoEMUKGvNDyUrQponeLBybd9GYj2inBZAoeZeF1qFbUVgHNHq4GeBvbeCD3Jh/XdV6GIujLFHH45EoXEIeZLr8XLE0m0fMVaGl96JwIDAQAB";
        System.out.println("public key :" + publicKey);
        byte[] secByte = RSAUtil.encryptByPrivateKey("你好世界任命大萨克拉将对方拉加深对发窘及的发丝给偶加i哦就嗲分级基金i几次哦啊降低急哦度搜皮肤极爱囧囧偶家is东方急哦炯炯地覅哦啊加深对 假摔帝发窘水平都房间爱欧舒丹房价松动飞".getBytes(CharsetConstant.UTF_8),
                                                     privateKey);
        String secText = Base64.encodeBase64String(secByte);
        System.out.println("encrypt result :" + secText);
    }

}
