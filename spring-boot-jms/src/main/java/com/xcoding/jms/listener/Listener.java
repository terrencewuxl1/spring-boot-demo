package com.xcoding.jms.listener;

import com.google.gson.Gson;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Map;

@Component
public class Listener {

    @JmsListener(destination = "inbound.topic")
    @SendTo("outbound.topic")
    public String receiveMessage(final Message jsonMessage) throws JMSException {
        String messageData = null;
        System.out.println("Received message " + jsonMessage);
        String response = null;
        if(jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)jsonMessage;
            messageData = textMessage.getText();
            Map map = new Gson().fromJson(messageData, Map.class);
            response  = "Hello " + map.get("name");
        }
        return response;
    }
}
