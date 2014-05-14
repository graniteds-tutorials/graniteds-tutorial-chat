package org.graniteds.tutorial.chat.server;

import org.granite.config.servlet3.ServerFilter;
import org.granite.gravity.config.servlet3.MessagingDestination;

// tag::server-filter[]
@ServerFilter // <1>
public class ChatApplication {

    @MessagingDestination(noLocal=false)
    public String chatTopic; // <2>
}
// end::server-filter[]
