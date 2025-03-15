package pe.edu.idat.chat_websocket_ec3.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import pe.edu.idat.chat_websocket_ec3.chat.model.Message;
import pe.edu.idat.chat_websocket_ec3.chat.model.Tiponotificacion;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSendingOperations;


    public WebSocketEventListener(SimpMessageSendingOperations messageSendingOperations) {
        this.messageSendingOperations = messageSendingOperations;
    }


    @EventListener
    public void socketDisconectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String usuario = (String) headerAccessor.getSessionAttributes().get("username");

        if (usuario != null) {
            // Notificación de tipo DEJAR
            Message mensajeDejar = new Message();
            mensajeDejar.setTiponotificacion(Tiponotificacion.DEJAR);
            mensajeDejar.setEnvio(usuario);
            mensajeDejar.setContenido("❌ Usuario " + usuario + " ha salido del chat");
            messageSendingOperations.convertAndSend("/topic/public", mensajeDejar);

            // Notificación de tipo INFO con la hora de salida
            Message mensajeInfo = new Message();
            mensajeInfo.setTiponotificacion(Tiponotificacion.INFO);
            mensajeInfo.setEnvio(usuario);
            String horaSalida = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
            mensajeInfo.setContenido("ℹ️ Usuario " + usuario + " salió a las " + horaSalida);
            messageSendingOperations.convertAndSend("/topic/public", mensajeInfo);
        }
    }
}
