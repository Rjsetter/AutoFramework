package cn.yq.tests.接口测试;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

public class Md5 {
    /**
     * @param content
     * @param charset
     * @return byte[]
     * @Title: getContentBytes
     */
    private static byte[] getContentBytes(String content, String charset) throws Exception {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
//            Log.error("MD5签名过程中出现错误,指定的编码集不对", e);
//            log.error("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:{}", charset);
            throw new Exception("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * @param channeId 渠道号
     * @param text     请求报文
     * @param key      密钥
     * @return 签名结果
     * @throws Exception
     */
    public static String sign(String channeId, String text, String key) throws Exception {
        text = channeId + text + key;
        return DigestUtils.md5Hex(getContentBytes(text, "utf-8"));
    }

    public static void main(String[] args) throws Exception {
        String channeId = "test";
        String text = "{\"phoneNo\":\"17721295199\",\"vinNo\":\"LHWCG75D5G1636669\",\"vehicleNo\":\"粤X37828\",\"engineNo\":\"0778040716B162\"}";
        String key = "ADFSAFWERQ3221312312312";
        String Authorization = Md5.sign(channeId, text, key);
        System.out.println(Authorization);
    }
}
