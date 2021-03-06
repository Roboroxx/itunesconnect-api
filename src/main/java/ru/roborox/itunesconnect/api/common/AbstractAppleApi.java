package ru.roborox.itunesconnect.api.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class AbstractAppleApi {
    protected final Logger logger = LoggerFactory.getLogger(AbstractAppleApi.class);

    protected final ObjectMapper objectMapper;
    protected final Executor executor;
    protected final String url;
    protected final boolean log;

    public AbstractAppleApi(Executor executor, ObjectMapper objectMapper, String url, boolean log) throws MalformedURLException {
        this.url = url;
        this.log = log;
        this.objectMapper = objectMapper;
        this.executor = executor;
    }

    protected <T> T execute(Request request, Class<T> tClass) throws IOException {
        return objectMapper.readValue(execute(request), tClass);
    }

    protected String execute(Request request) throws IOException {
        final HttpResponse response = executeRequest(request).returnResponse();
        HttpEntity entity = null;
        InputStream in = null;
        try {
            entity = response.getEntity();
            if (entity != null) {
                in = entity.getContent();
                final String content = IOUtils.toString(in, "UTF-8");
                if (log) {
                    logger.info("request={} response={} content={}", request, response, content);
                }
                return content;
            } else {
                if (log) {
                    logger.info("request={} response={} content=none", request, response);
                }
                return null;
            }
        } finally {
            IOUtils.closeQuietly(in);
            EntityUtils.consumeQuietly(entity);
        }
    }

    protected Request get(String path) {
        return Request.Get(url + path);
    }

    protected Request post(String path, Object request) throws JsonProcessingException {
        return post(path, objectMapper.writeValueAsString(request));
    }

    protected Request post(String path, String request) throws JsonProcessingException {
        if (log) {
            logger.info("post to {} using {}", path, request);
        }
        return Request.Post(url + path).bodyString(request, ContentType.APPLICATION_JSON);
    }

    protected Response executeRequest(Request request) throws IOException {
        return executor.execute(request
                .addHeader("X-Requested-By", "analytics.itunes.apple.com")
                .addHeader("Accept", "application/json, text/plain, */*"));
    }
}
