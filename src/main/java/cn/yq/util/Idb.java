package cn.yq.util;

import cn.yq.data.IdbQuery;
import cn.yq.restClient.RestClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;

public class Idb {
    /**
     *
     * @param dbenv   环境
     * @param dbname  数据库名
     * @param tbname  数据表
     * @param sql     sql语句
     * @throws IOException
     */
    public static void idb(String dbenv,String dbname, String tbname,String sql)throws IOException {
        CloseableHttpResponse closeableHttpResponse;
        RestClient restClient = new RestClient();
        String Url = "http://idb.zhonganonline.com/getqueryrst";
        String Cookie = "IDB_BETA_UID=D%0AAwMTE5MDk%3DM-%3D1548124897-%3D130e0594e2f801a16d47ffb586cd4a14";
        String splitcol = "-1";
        String splitcolmode = "=";
        String selectmod = "1";
        String iscount = "0";
        String idb2Json ;
        //转换为Json数据
        IdbQuery idbquery = new IdbQuery(dbenv,dbname,tbname,splitcol,splitcolmode,sql,selectmod,iscount);
        idb2Json = JSON.toJSONString(idbquery);
        //请求头
        HashMap<String, String> headermap = new HashMap<String, String>();
        headermap.put("Content-Type", "application/json");
        headermap.put("Cookie", Cookie);
        closeableHttpResponse = restClient.post(Url, idb2Json, headermap);
        //验证状态码是不是200
        System.out.println(idb2Json);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        JSONObject responseJson = restClient.getResponseJson(closeableHttpResponse);
        String s = TestUtil.getValueByJPath(responseJson, "");
        System.out.println( "-->" + s);
    }
    public static void main(String[] args){
        try {
            idb("tst","nereus","nereus_message_sent","receiver_no ='17721295133'");
        }catch (IOException e){
            System.out.print(e.getMessage());
        }
    }
}
