package cn.yq.tests.接口测试;

import cn.yq.base.TestBase;
import cn.yq.data.vehicle;
import cn.yq.restClient.RestClient;
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

public class ZAQuery extends TestBase {
    TestBase testBase;
    String host;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    String phoneNo = "17721295139";
    String vinNo = "LSGUD84X2DE024065";   //车架号
    String vehicleNo = "琼A0KM76";         //车牌号
    String engineNo = "131120442";   //发动机号
    String vehicleJsonString;               //请求信息
    private static int count = 0;
    String Authorization;   //验签信息

    @BeforeClass
    public void setUp() throws Exception {
        testBase = new TestBase();
        host = prop.getProperty("HOST");
        url = host + "/open/api/illegal/queryIllegal";
        //payload信息转为json格式
        vehicle vehicle = new vehicle(phoneNo, vinNo, vehicleNo, engineNo);
        vehicleJsonString = JSON.toJSONString(vehicle);
        Authorization = Md5.sign("test", vehicleJsonString, "ADFSAFWERQ3221312312312");
    }

    //    invocationCount=10000,threadPoolSize = 25
    @Test(invocationCount = 1, threadPoolSize = 1)
    public void PostApply() throws ClientProtocolException, IOException {
        count += 1;
//        System.out.println("正在进行第"+count+"请求！");
        restClient = new RestClient();
        //准备请求头
        HashMap<String, String> headermap = new HashMap<String, String>();
        headermap.put("Authorization", Authorization);
        headermap.put("channel", "test");
        headermap.put("Content-Type", "application/json");

//        System.out.println(vehicleJsonString);
        closeableHttpResponse = restClient.post(url, vehicleJsonString, headermap);
        //验证状态码是不是200
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPNSE_STATUS_CODE_200, "status code is not 201");

        //断言响应json内容中name和job是不是期待结果
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        JSONObject responseJson = JSON.parseObject(responseString);
        System.out.println(count + "-->" + responseString);

    }

}
