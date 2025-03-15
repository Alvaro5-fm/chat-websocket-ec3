package pe.edu.idat.chat_websocket_ec3.chat.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificacionService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void enviarNotificacion(String mensaje) {
        messagingTemplate.convertAndSend("/topic/notificaciones", mensaje);
    }
}

