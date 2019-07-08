package com.jenkins.own.wechart.handler;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weixin4j.model.message.OutputMessage;
import org.weixin4j.model.message.normal.*;
import org.weixin4j.model.message.output.TextOutputMessage;
import org.weixin4j.spi.INormalMessageHandler;

/**
 * 自定义普通消息处理器
 *
 * @author yangqisheng
 */
public class AtsNormalMessageHandler implements INormalMessageHandler {

    protected final Logger LOG = LoggerFactory.getLogger(AtsNormalMessageHandler.class);

    @Override
    public OutputMessage textTypeMsg(TextInputMessage msg) {
        LOG.debug("文本消息：" + JSON.toJSONString(msg));
        TextOutputMessage out = new TextOutputMessage();
        out.setContent("获取JAVA资料成功");
        switch(msg.getContent()){
            case "JAVA":
                out.setContent("获取JAVA资料成功");
                break;
            case "MYSQL":
                out.setContent("获取MYSQL资料成功");
                break;

            default:
                out.setContent("资料不存在");
                break;
        }
        return out;

    }

    @Override
    public OutputMessage imageTypeMsg(ImageInputMessage msg) {
        LOG.debug("图片消息：" + JSON.toJSONString(msg));
        TextOutputMessage out = new TextOutputMessage();
        out.setContent("您发的消息是：" + JSON.toJSONString(msg));
        return out;
    }

    @Override
    public OutputMessage voiceTypeMsg(VoiceInputMessage msg) {
        LOG.debug("语音消息：" + JSON.toJSONString(msg));
        TextOutputMessage out = new TextOutputMessage();
        out.setContent("您发的消息是：" + JSON.toJSONString(msg));
        return out;
    }

    @Override
    public OutputMessage videoTypeMsg(VideoInputMessage msg) {
        LOG.debug("视频消息：" + JSON.toJSONString(msg));
        TextOutputMessage out = new TextOutputMessage();
        out.setContent("您发的消息是：" + JSON.toJSONString(msg));
        return out;
    }

    @Override
    public OutputMessage shortvideoTypeMsg(ShortVideoInputMessage msg) {
        LOG.debug("短视频消息：" + JSON.toJSONString(msg));
        TextOutputMessage out = new TextOutputMessage();
        out.setContent("您发的消息是：" + JSON.toJSONString(msg));
        return out;
    }

    @Override
    public OutputMessage locationTypeMsg(LocationInputMessage msg) {
        LOG.debug("locationTypeMsg：" + JSON.toJSONString(msg));
        TextOutputMessage out = new TextOutputMessage();
        out.setContent("您发的消息是：" + JSON.toJSONString(msg));
        return out;
    }

    @Override
    public OutputMessage linkTypeMsg(LinkInputMessage msg) {
        LOG.debug("linkTypeMsg：" + JSON.toJSONString(msg));
        TextOutputMessage out = new TextOutputMessage();
        out.setContent("您发的消息是：" + JSON.toJSONString(msg));
        return out;
    }

}
