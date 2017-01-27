package ru.roborox.itunesconnect.api.reporting;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.roborox.itunesconnect.api.common.AbstractAppleApi;
import ru.roborox.itunesconnect.api.login.ConnectTokens;
import ru.roborox.itunesconnect.api.reporting.model.TimeSeriesRequest;
import ru.roborox.itunesconnect.api.reporting.model.TimeSeriesResponse;
import ru.roborox.itunesconnect.api.reporting.model.UserInfo;

import java.io.IOException;

import static ru.roborox.itunesconnect.api.common.Utils.*;

public class ReportingApi extends AbstractAppleApi {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setDateFormat(createDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ReportingApi(ConnectTokens tokens, String url, boolean log) throws IOException {
        super(createExecutor(createNotRecordingCookieStore(tokens)), objectMapper, url, log);
    }

    public TimeSeriesResponse getTimeSeries(TimeSeriesRequest request) throws IOException {
        final TimeSeriesResponse[] result = execute(post("/data/timeseries", request), TimeSeriesResponse[].class);
        if (result.length > 0) {
            return result[0];
        } else {
            return null;
        }
    }

    public UserInfo getUserInfo() throws IOException {
        return execute(get("/user/info"), UserInfo.class);
    }
}