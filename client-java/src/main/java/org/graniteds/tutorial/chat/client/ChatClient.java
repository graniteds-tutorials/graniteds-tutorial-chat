package org.graniteds.tutorial.chat.client;

import org.granite.client.messaging.Consumer;
import org.granite.client.messaging.Producer;
import org.granite.client.messaging.TopicMessageListener;
import org.granite.client.messaging.channel.ChannelType;
import org.granite.client.messaging.events.TopicMessageEvent;
import org.granite.client.tide.Context;
import org.granite.client.tide.impl.ComponentImpl;
import org.granite.client.tide.impl.SimpleContextManager;
import org.granite.client.tide.server.Component;
import org.granite.client.tide.server.ServerSession;
import org.granite.client.tide.server.TideFaultEvent;
import org.granite.client.tide.server.TideResponder;
import org.granite.client.tide.server.TideResultEvent;


public class ChatClient {

    public static void main(String[] args) throws Exception {
        // tag::client-setup[]
        Context context = new SimpleContextManager().getContext(); // <1>
        ServerSession serverSession = context.set(
                new ServerSession("/chat", "localhost", 8080)); // <2>
        serverSession.start(); //  <3>

        Consumer chatConsumer = serverSession.getConsumer(ChannelType.LONG_POLLING, "chatTopic", "room"); // <4>
        Producer chatProducer = serverSession.getProducer(ChannelType.LONG_POLLING, "chatTopic", "room"); // <5>
        // end::client-app[]

        // tag::client-consumer[]
        chatConsumer.addMessageListener(new TopicMessageListener() {
            @Override
            public void onMessage(TopicMessageEvent event) {
                System.out.println(event.getData());
            }
        });

        chatConsumer.subscribe().get();
        // end::client-consumer[]

        for (int i = 0; i < 5000; i++) {
            // tag::client-publish[]
            String message = "Message " + i;
            chatProducer.publish(message);
            // end::client-publish[]
            Thread.sleep(800L);
        }

        serverSession.stop();
    }
}
