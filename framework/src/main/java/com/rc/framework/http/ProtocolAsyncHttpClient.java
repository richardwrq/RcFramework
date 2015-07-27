package com.rc.framework.http;

import com.loopj.android.http.AsyncHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.HttpContext;

/**
 * @Description 异步客户端基类，解决循环重定向bug
 * @Author czm
 * @Email caizemingg@163.com
 * @Version V0.1
 * @CreateDate 2014-10-26 下午1:19:22
 * @ModifyAuthor
 * @ModifyDescri
 * @ModifyDate
 */
public class ProtocolAsyncHttpClient extends AsyncHttpClient {

    public ProtocolAsyncHttpClient() {
        setTimeout(10000);
        //解决循环重定向问题
        getHttpClient().getParams()
                .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
    }

    @Override
    public void setEnableRedirects(final boolean enableRedirects) {
        // TODO Auto-generated method stub
        super.setEnableRedirects(enableRedirects);
        ((DefaultHttpClient) getHttpClient())
                .setRedirectHandler(new DefaultRedirectHandler() {
                    @Override
                    public boolean isRedirectRequested(HttpResponse response,
                                                       HttpContext context) {
                        int statusCode = response.getStatusLine()
                                .getStatusCode();
                        if (statusCode == 301 || statusCode == 302) {
                            return enableRedirects;
                        }
                        return false;
                    }
                });
    }

}
