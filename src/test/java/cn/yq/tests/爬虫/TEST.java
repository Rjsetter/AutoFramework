package cn.yq.tests.爬虫;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TEST {
    public static void main(String []args){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();
        List<NameValuePair> postData = new ArrayList<NameValuePair>();
        //这里可能有多个参数
        postData.add(new BasicNameValuePair("username", "sx_yeqiang"));
        postData.add(new BasicNameValuePair("password", "C3bxak7gna"));
        postData.add(new BasicNameValuePair("did", "NDI0YTRlNzZkMmIwMGU4YTVmYzVlNGNjNTNjMWU5YjMtNjYxNmFmZjE3MDk1NDVhMGJlMzM3ZTg3ZDM0NTdjZmMwMDAzLUZPUiswdFQ2Tk5iSnloY3F1RmZOSk5yTFFHcz0="));
        postData.add(new BasicNameValuePair("token", "1:12:190122:za_sso#prd#login_1::ZUagtGs0:12838:000d2d63202f3ea6fa9eb39cf4daec2bad427942"));
        postData.add(new BasicNameValuePair("lt", "LT-76923567-C07GkmEksI0sqAtRXpixO5bHli1WaIPooyV"));
        postData.add(new BasicNameValuePair("service", "za-idb"));
        postData.add(new BasicNameValuePair("target", "http://idb.zhonganonline.com"));
        //URL是实际的post地址，使用httpFox得到
        String URL = "http://nsso.zhonganonline.com/login/";
        HttpPost httppost = new HttpPost(URL);
        HttpPost get = new HttpPost("http://nsso.zhonganonline.com/login?service=za-idb&error=-301&target=http%3A%2F%2Fidb.zhonganonline.com/");
        System.out.print(get.headerIterator());
        try {
        httppost.setEntity(new UrlEncodedFormEntity(postData, "UTF-8"));

        CloseableHttpResponse response = httpclient.execute(httppost,context);
        CookieStore cookieStore = context.getCookieStore();
        List<Cookie> cookies = cookieStore.getCookies();
//        System.out.println(cookies);
//        System.out.println(response);
        } catch (IOException e) {
        }

    }
}
