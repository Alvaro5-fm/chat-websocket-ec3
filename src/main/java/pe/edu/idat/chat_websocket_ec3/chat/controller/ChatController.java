package pe.edu.idat.chat_websocket_ec3.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import pe.edu.idat.chat_websocket_ec3.chat.model.Message;
import pe.edu.idat.chat_websocket_ec3.chat.model.Tiponotificacion;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message enviarMensaje(@Payload Message message){
        message.setHora(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        return message;
    }
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message agregarUsuario(@Payload Message message, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username",message.getEnvio());
        message.setHora(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        message.setContenido("✅Usuario " + message.getEnvio() + " se ha unido al chat");
        return message;
    }

    @MessageMapping("/enviarNotificacion")
    @SendTo("/topic/notificaciones")
    public Message enviarNotificacion(@Payload Message message) {
        if (message.getContenido().toLowerCase().contains("error")) {
            message.setTiponotificacion(Tiponotificacion.ERROR);
        } else {
            message.setTiponotificacion(Tiponotificacion.INFO);
        }
        return message;
    }


}
