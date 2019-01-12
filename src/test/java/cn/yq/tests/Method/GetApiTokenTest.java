package cn.yq.tests.Method;

import cn.yq.base.TestBase;
import cn.yq.restClient.RestClient;
import cn.yq.util.TestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class GetApiTokenTest extends TestBase {
    TestBase testBase;
    String host;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    @BeforeClass
    public void setUp() {
        testBase = new TestBase();
        host = prop.getProperty("HOST");
        url = host + "/api/unknown";
    }

    @Test
    public void getApiTest() throws ClientProtocolException, IOException {
        restClient = new RestClient();
        //准备请求头信息
        HashMap<String, String> headermap = new HashMap<String, String>();
        headermap.put("Content-Type", "application/json"); //这个在postman中可以查询到

        closeableHttpResponse = restClient.get(url, headermap);

        //验证状态码是不是200
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPNSE_STATUS_CODE_200, "status code is not 201");

        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        JSONObject responseJson = JSON.parseObject(responseString);
        System.out.println(responseString);

        String name = TestUtil.getValueByJPath(responseJson, "data[0]/name");
        System.out.println(name);
    }
}
