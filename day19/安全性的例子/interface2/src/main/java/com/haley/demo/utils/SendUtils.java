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
    protected static final String APP_KEY = "e3e657823f3f32d6b13e97e5";
    protected static final String MASTER_SECRET = "e0339a9abe24ff8a9996edce";

    public static void main(String[] args) {

       // testSendPush();

    }



    public static PushResult testSendPush(String s1,String s2,String s3) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
        final PushPayload payload = buildPushObject_android_and_ios( s1, s2, s3);
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




   /**s1表示人的名字，s2表示内容，s3是标签）*/
   /**s1 对应的是userEntity.getName()
    *  s2对应的是userEntity.getPassword()
    *  s3对应的是userEntity.getPhone()*/
    public static PushPayload buildPushObject_android_and_ios(String s1,String s2,String s3) {
        Map<String, String> extras = new HashMap<String, String>();
        //extras.put(s1, s2);
        System.out.println(s3);
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag(s3))
                .setNotification(Notification.newBuilder()
                        .setAlert(s2)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(s1)
                                .addExtras(extras).build())
                        .build())
                .build();
    }


}
