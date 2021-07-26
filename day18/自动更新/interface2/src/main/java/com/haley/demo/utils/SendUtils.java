package com.haley.demo.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.CIDResult;
import cn.jpush.api.push.GroupPushClient;
import cn.jpush.api.push.GroupPushResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class SendUtils {
    protected static final Logger LOG = LoggerFactory.getLogger(SendUtils.class);

    // demo App defined in resources/jpush-api.conf
    protected static final String APP_KEY = "750ab9f73a4f158d8ea8f1a5";
    protected static final String MASTER_SECRET = "64fc59d3ac4d9afb59ac7e3f";

    public static void main(String[] args) {

        testSendPush();

    }



    public static PushResult testSendPush() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
        final PushPayload payload = buildPushObject_android_and_ios();
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("获得结果" + result);
            System.out.println(result);

            return result;
        } catch (APIConnectionException e) {
            LOG.error("连接错误 ", e);
            LOG.error("发送号码为: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("极光服务器回复错误 ", e);
            LOG.error("Sendno: " + payload.getSendno());
        }
        return null;
    }





    public static PushPayload buildPushObject_android_and_ios() {
        Map<String, String> extras = new HashMap<String, String>();
        extras.put("内容1", "我就是内容1111");
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag("haha","666666"))
                .setNotification(Notification.newBuilder()
                        .setAlert("弹框内容")
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("弹框标题")
                                .addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtra("extra_key", "extra_value").build())
                        .build())
                .build();
    }


}
