package com.server.javawebsocketserver;

import ch.qos.logback.classic.BasicConfigurator;
//import org.slf4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import sun.rmi.runtime.Log;

import java.io.IOException;

public class WebSocketHandler extends AbstractWebSocketHandler {
    //private static final Logger logger = (Logger) LogManager.getLogger("WebsocketHandler");

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String msg = String.valueOf(message.getPayload());
        /*String[] split=msg.split("#",2);
        String choice=split[0];
        String uid=split[1];
        System.out.println(choice);
        System.out.println(choice);
        System.out.println(uid);*/
        // Send back unique message depending on the id received from the client
        switch(msg){
            case("1"):
                System.out.println("Discount button was pressed by "+session.getId());
                session.sendMessage(new TextMessage("Jessimarket- Le paquet de chips à moitié prix !"));
                break;

            case("2"):
                System.out.println("Location button was pressed by "+session.getId());
                session.sendMessage(new TextMessage("Le nouveau KFC tout près de vous !"));
                break;

            case("3"):
                System.out.println("News button was pressed by "+session.getId());
                session.sendMessage(new TextMessage("De nouvelles salles de cinéma dans votre centre commercial à partir de mars 2020"));
                break;

            case("4"):
                System.out.println("Other button was pressed by "+session.getId());
                session.sendMessage(new TextMessage("Notification "));
                break;
            default:
                System.out.println("Connected to Client");
        }
    }
}