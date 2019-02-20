package cn.yq.tests.爬虫;

import cn.yq.restClient.RestClient;
import cn.yq.util.TestUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

public class 智联招聘 {
    public static RestClient restClient;
    public static CloseableHttpResponse closeableHttpResponse;
    public static void main(String [] args)throws IOException {
        String urlHead = "https://fe-api.zhaopin.com/c/i/sou?pageSize=100&cityId=540&workExperience=-1&education=-1&companyType=-1&employmentType=-1&jobWelfareTag=-1&kw=";
        String urlEnd = "&kt=3&_v=0.28509217&x-zp-page-request-id=243679014cb34a5a97b9ff0df91f1bb9-1547654268684-816387";
        String keyWord = "mysql";
        String url = urlHead+keyWord+urlEnd;
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);
        int statusCode = restClient.getStatusCode(closeableHttpResponse);
        JSONObject responseJson = restClient.getResponseJson(closeableHttpResponse);
        String s = TestUtil.getValueByJPath(responseJson, "data/results");
        String d = "["+s+"]";
        JSONArray jsonArray;
        JSONArray json = JSONArray.parseArray(s);
        int l = json.size();
        System.out.println(l);
        for(int i=0;i<json.size();i++){
            String jobname = TestUtil.getValueByJPath(responseJson, "data/results["+i+"]/jobName");
            String city = TestUtil.getValueByJPath(responseJson, "data/results["+i+"]/city/items[0]/name");
            System.out.println(city+"--->"+jobname );
        }
        System.out.println(statusCode);

    }
}
