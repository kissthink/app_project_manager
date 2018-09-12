package com.example.common.network.exception;

import com.example.common.R;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * 网络请求异常处理类
 */
public class ExceptionHandler {
    /**
     * http 响应错误码
     */
    private class HttpStatusCode {
        private static final int ERROR_REQUEST = 400;
        private static final int UNAUTHORIZED = 401;
        private static final int FORBIDDEN = 403;
        private static final int NOT_FOUND = 404;
        private static final int METHOD_NOT_SUPPORT = 405;
        private static final int REQUEST_TIMEOUT = 408;
        private static final int REQUEST_LARGE = 413;
        private static final int REQUEST_URI_LONG = 414;
        private static final int SERVER_ERROR = 500;
        private static final int GATEWAY_ERROR = 502;
        private static final int SERVICE_UNAVAILABLE = 503;
        private static final int GATEWAY_TIMEOUT = 504;
        private static final int HTTP_NOT_SUPPORT = 505;
    }

    // 未知错误
    private static final int UNKNOWN_ERROR = 1000;
    // 连接失败
    private static final int UNCONNECT_ERROR = 1001;
    // 证书验证失败
    private static final int SSL_ERROR = 1002;
    // 数据解析错误
    private static final int PARSE_ERROR = 1003;

    /**
     * 异常解析
     *
     * @param e
     * @return
     */
    public static HttpFailure parseError(Throwable e) {
        HttpFailure failure = new HttpFailure();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int errorMsgResId;
            switch (httpException.code()) {
                case HttpStatusCode.ERROR_REQUEST:
                    errorMsgResId = R.string.network_error_request;
                    break;
                case HttpStatusCode.UNAUTHORIZED:
                    errorMsgResId = R.string.network_unauthorized;
                    break;
                case HttpStatusCode.FORBIDDEN:
                    errorMsgResId = R.string.network_forbidden;
                    break;
                case HttpStatusCode.NOT_FOUND:
                    errorMsgResId = R.string.network_not_found;
                    break;
                case HttpStatusCode.METHOD_NOT_SUPPORT:
                    errorMsgResId = R.string.network_method_not_support;
                    break;
                case HttpStatusCode.REQUEST_TIMEOUT:
                    errorMsgResId = R.string.network_timeout;
                    break;
                case HttpStatusCode.REQUEST_LARGE:
                    errorMsgResId = R.string.network_request_large;
                    break;
                case HttpStatusCode.REQUEST_URI_LONG:
                    errorMsgResId = R.string.network_uri_long;
                    break;
                case HttpStatusCode.SERVER_ERROR:
                    errorMsgResId = R.string.network_server_error;
                    break;
                case HttpStatusCode.GATEWAY_ERROR:
                    errorMsgResId = R.string.network_gateway_error;
                    break;
                case HttpStatusCode.SERVICE_UNAVAILABLE:
                    errorMsgResId = R.string.network_service_unavailable;
                    break;
                case HttpStatusCode.GATEWAY_TIMEOUT:
                    errorMsgResId = R.string.network_gateway_timeout;
                    break;
                case HttpStatusCode.HTTP_NOT_SUPPORT:
                    errorMsgResId = R.string.network_http_not_support;
                    break;
                default:
                    errorMsgResId = R.string.network_error;
            }
            failure.setErrorCode(httpException.code());
            failure.setErrorMsgId(errorMsgResId);
        } else if (e instanceof ConnectException) {
            failure.setErrorCode(UNCONNECT_ERROR);
            failure.setErrorMsgId(R.string.unconnect_error);
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            failure.setErrorCode(SSL_ERROR);
            failure.setErrorMsgId(R.string.ssl_error);
        } else if (e instanceof JsonParseException || e instanceof JSONException) {
            failure.setErrorCode(PARSE_ERROR);
            failure.setErrorMsgId(R.string.parse_error);
        } else {
            failure.setErrorCode(UNKNOWN_ERROR);
            failure.setErrorMsgId(R.string.unknown_error);
        }
        return failure;
    }
}
