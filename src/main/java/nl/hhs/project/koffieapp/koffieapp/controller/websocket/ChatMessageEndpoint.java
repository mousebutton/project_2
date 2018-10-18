package nl.hhs.project.koffieapp.koffieapp.controller.websocket;

import nl.hhs.project.koffieapp.koffieapp.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Endpoints for the Websocket listener.
 * Only endpoints that will be called by the Client has to be here.
 */

@Controller
public class ChatMessageEndpoint {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Receives all messages send to the @MessageMapping defined url.
     * Push the message to all the clients subscribed to the @SendTo url.
     * @param content
     * @return
     * @throws Exception
     */
    @SendTo("/global-message/chat")
    @MessageMapping("chat-message")
    public String sendChatMessage(String content) throws Exception {
        Thread.sleep(1000);
        String[] data = content.split("%");
        String token = data[1];
        String userName = jwtTokenProvider.getUsername(token);
        return userName + ": " + data[0];
    }


}
