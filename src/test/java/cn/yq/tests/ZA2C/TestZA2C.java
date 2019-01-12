package cn.yq.tests.ZA2C;

import cn.yq.base.TestBase;
import cn.yq.restClient.RestClient;
import cn.yq.tests.Method.GetApiTest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestZA2C extends TestBase {
    TestBase testBase;
    String host;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    final static Logger Log = Logger.getLogger(GetApiTest.class);

    @BeforeClass
    public void setUp() {
        testBase = new TestBase();
        host = prop.getProperty("HOST2C");
        Log.info("测试服务器地址为：" + host.toString());
        url = host + "/?business_id=10002537697010&utm_source=ZhiyingAPP&auto_test=true&auto_payment=false";
        Log.info("当前测试接口的完整地址为：" + url.toString());
    }

    @Test
    public void getAPITest() throws ClientProtocolException, IOException {
        Log.info("开始执行用例...");
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);

        //断言状态码是不是200
        Log.info("测试响应状态码是否是200");
        int statusCode = restClient.getStatusCode(closeableHttpResponse);
        Assert.assertEquals(statusCode, RESPNSE_STATUS_CODE_200, "response status code is not 200");


//        JSONObject responseJson = restClient.getResponseJson();
//        System.out.println("respon json from API-->" + responseJson);

        //json内容解析
//        String s = TestUtil.getValueByJPath(responseJson,"data[0]/first_name");
//        Log.info("执行JSON解析，解析的内容是 " + s);
//        System.out.println(s);
//        Log.info("接口内容响应断言");
//        Assert.assertEquals(s, "Eve","first name is not Eve");
        Log.info("用例执行结束...");
    }
}
