package com.otot.mikdah.websocket.clients;

import com.otot.mikdah.websocket.server.OutgoingMessage;
import com.otot.mikdah.websocket.server.Defines;
import com.otot.mikdah.websocket.server.IncomingMessage;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * This client send its message to server which in turn send it to common topic for communication.
 */
public class SocketJSClient {

    private static String location = "ws://localhost:8080" + Defines.END_POINT;
    private static boolean isConnected;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        SocketJSClient sc = new SocketJSClient();
        WebSocketStompClient stompClient = sc.registerSocket();
        StompSession session = sc.registerToSocketIncoming(stompClient);

        sc.doChat(session);
    }

    private WebSocketStompClient registerSocket(){
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        return stompClient;
    }

    private StompSession registerToSocketIncoming(WebSocketStompClient stompClient) throws ExecutionException, InterruptedException {
        SocketHandler SocketHandler = new SocketHandler();
        ListenableFuture<StompSession> sessionAsync = stompClient.connect(location, SocketHandler);
        StompSession session = sessionAsync.get();
        session.subscribe(Defines.MSG_FROM_SERVER, SocketHandler);
        isConnected = true;
        return session;
    }

    private void doChat(StompSession session){
        while(isConnected){
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object

            System.out.println("Write to chat (write close to exit.. )");
            String reply = myObj.nextLine();
            if (reply.equals("close")) {
                isConnected = false;
                session.disconnect();
            }else
                session.send(Defines.MSG_TO_SERVER, new IncomingMessage(reply));
        }
    }
}

class SocketHandler extends StompSessionHandlerAdapter {

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return OutgoingMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("Received : " + ((OutgoingMessage) payload).getContent());
    }
}
