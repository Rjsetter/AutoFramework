package cn.yq.tests.接口测试;


import cn.yq.base.TestBase;
import cn.yq.data.Users;
import cn.yq.restClient.RestClient;
import cn.yq.util.TestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class Swan extends TestBase {
    TestBase testBase;
    String host;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;


    @BeforeClass
    public void setUp() {
        testBase = new TestBase();
        host = prop.getProperty("Swan");
        url = host + "api/v1/message/forward";

    }

    @Test
    public void postApiTest() throws ClientProtocolException, IOException {
        CloseableHttpResponse closeableHttpResponse;
        restClient = new RestClient();
        //准备请求头信息
        HashMap<String, String> headermap = new HashMap<String, String>();
        headermap.put("msgId", "5");
        headermap.put("msgCreator", "firstmm");
        headermap.put("comment", "test");
        headermap.put("receivers", "liqinkun");
        closeableHttpResponse = restClient.setFormHttpEntity(url,headermap);
        System.out.println(closeableHttpResponse);

    }

}
