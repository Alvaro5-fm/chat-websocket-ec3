package pe.edu.idat.chat_websocket_ec3.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import pe.edu.idat.chat_websocket_ec3.chat.model.Message;
import pe.edu.idat.chat_websocket_ec3.chat.model.Tiponotificacion;

@Component
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSendingOperations;


    public WebSocketEventListener(SimpMessageSendingOperations messageSendingOperations) {
        this.messageSendingOperations = messageSendingOperations;
    }


    @EventListener
    public void socketDisconectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String usuario = (String)headerAccessor.getSessionAttributes().get("username");

        if(usuario != null){
            Message mensaje = new Message();
            mensaje.setTiponotificacion(Tiponotificacion.DEJAR);
            mensaje.setEnvio(usuario);

            messageSendingOperations.convertAndSend("/topic/public",mensaje);
        }


    }

}
