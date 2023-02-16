package com.otot.mikdah.websocket.server;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Controller
public class ServerController {


// USE FOR GENERAL SEND
    @MessageMapping(Defines.MAPPPING)
    @SendTo(Defines.MSG_FROM_SERVER)
    public OutgoingMessage processMessage(IncomingMessage incomingMessage) throws Exception{
        return new OutgoingMessage("Hello " + incomingMessage.getName());
    }


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private Gson gson = new Gson();

// USE FOR SPECIFIC SEND
    @MessageMapping("/message")
    @SendToUser("/queue/reply")
    public String processMessageFromClient( @Payload String message, Principal principal) throws Exception {
        String user = gson.fromJson(message, Map.class).get("name").toString();
        System.out.println("user = " + user);
        return user;
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

}
