package com.rc.framework.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.rc.framework.util.LogUtil;

import org.apache.http.HttpEntity;

import java.util.List;

/**
 * Description: httpclient,所有http请求都由这里发起
 * User: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2014-12-10 16:42
 */
public enum BaseHttpClient {

    INSTANCE;

    private static final String METHOD_GET = "GET:";
    private static final String METHOD_POST = "POST:";
    /**
     * 异步http客户端
     */
    private AsyncHttpClient asyncHttpClient = new ProtocolAsyncHttpClient();

    /**
     * @param context {@link Context}
     * @param url     请求的地址
     * @param params  {@link RequestParams}
     */
    private void Log(Context context, String url, RequestParams params) {
        String msg;
        if (params != null) {
            msg = url + LogUtil.SEPARATE + params.toString();
        } else {
            msg = url + LogUtil.SEPARATE + "params is null";
        }
        LogUtil.d(msg, context);
    }

    /**
     * @param context         {@link Context}
     * @param url             请求的地址
     * @param params          {@link RequestParams}
     * @param responseHandler {@link com.nzlm.base.http.BaseJsonHttpHandler}
     * @return
     */
    public RequestHandle get(final Context context, final String url, final RequestParams params,
                             final JsonHttpResponseHandler responseHandler) {
        Log(context, METHOD_GET + url, params);
        return asyncHttpClient.get(context, url, params, responseHandler);
    }

    /**
     * @param context         {@link Context}
     * @param url             请求的地址
     * @param params          {@link RequestParams}
     * @param responseHandler {@link com.nzlm.base.http.BaseJsonHttpHandler}
     * @return
     */
    public RequestHandle post(final Context context, final String url, final RequestParams params,
                              final JsonHttpResponseHandler responseHandler) {
        Log(context, METHOD_POST + url, params);
        return asyncHttpClient.post(context, url, params, responseHandler);
    }

    /**
     * @param context         {@link Context}
     * @param url             请求的地址
     * @param httpEntity
     * @param contentType
     * @param responseHandler {@link com.nzlm.base.http.BaseJsonHttpHandler}
     * @return
     */
    public RequestHandle post(final Context context, final String url,
                              final HttpEntity httpEntity, final String contentType,
                              final JsonHttpResponseHandler responseHandler) {
        return asyncHttpClient.post(context, url, httpEntity, contentType, responseHandler);
    }

    /**
     * @return
     */
    public AsyncHttpClient getAsyncHttpClient() {
        return asyncHttpClient;
    }

    /**
     *
     */
    public static class Builder {
        /**
         * 应用的上下文
         */
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }
    }

    public static void paramsNotNull(MethodBean methodBean) {
        for (int i = 0; i < methodBean.getParamsValue().size(); i++) {
            if (methodBean.getParamsValue().get(i) == null) {
                throw new IllegalArgumentException("Method(" + methodBean.getMethodName() + ")\n" +
                        "param(String" + methodBean.getParamsName().get(i) + ") can not be null)");
            }
        }
    }

//    /**
//     * 同步http客户端
//     */
//    private SyncHttpClient syncHttpClient = new SyncHttpClient();
//    /**
//     * @param url 请求的地址             地址
//     * @param params {@link com.loopj.android.http.RequestParams}          参数
//     * @param responseHandler {@link com.nzlm.base.http.BaseJsonHttpHandler} 回调
//     */
//    public void getSync(final Context context, final String url,
//                        final RequestParams params,
//                        final JsonHttpResponseHandler responseHandler) {
//        Log(context, METHOD_GET + url, params);
//
//
////        UseRequestView useRequestView;
////        if (context instanceof UseRequestView) {
////            useRequestView = (UseRequestView) context;
////            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
////
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    // TODO Auto-generated method stub
////                    getSync(context, url, params, responseHandler);
////                }
////            };
////
////            if (useRequestView.getRequestView().checkNetWork(context, listener)) {
////            } else {
//        asyncHttpClient.get(context, url, params, responseHandler);
////            }
////        }
//    }

    public static class MethodBean {
        private String methodName;
        private List<String> paramsName;
        private List<String> paramsValue;

        private MethodBean(Builder builder) {
            methodName = builder.methodName;
            paramsName = builder.paramsName;
            paramsValue = builder.paramsValue;
        }

        public static class Builder {
            private String methodName;
            private List<String> paramsName;
            private List<String> paramsValue;

            public Builder() {

            }

            public Builder setMethodName(String methodName) {
                this.methodName = methodName;
                return this;
            }

            public Builder setParamsName(List<String> paramsName) {
                this.paramsName = paramsName;
                return this;
            }

            public Builder setParamsValue(List<String> paramsValue) {
                this.paramsValue = paramsValue;
                return this;
            }

            public MethodBean create() {
                return new MethodBean(this);
            }
        }

        public String getMethodName() {
            return methodName;
        }

        public List<String> getParamsName() {
            return paramsName;
        }

        public List<String> getParamsValue() {
            return paramsValue;
        }

    }

}
