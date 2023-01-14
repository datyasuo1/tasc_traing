package com.tass.userservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class ResdisPusherMessageService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void sendMessage(String message , ChannelTopic channelTopic){
        redisTemplate.convertAndSend(channelTopic.getTopic() , message);
    }
}
