package pe.edu.idat.chat_websocket_ec3.chat.model;

public class Message {
    private Tiponotificacion tiponotificacion;
    private String contenido;
    private String envio;

    private String hora; // nueva propiedad

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Message() {
    }

    public Message(String contenido, String envio, String hora, Tiponotificacion tiponotificacion) {
        this.contenido = contenido;
        this.envio = envio;
        this.hora = hora;
        this.tiponotificacion = tiponotificacion;
    }

    public Tiponotificacion getTiponotificacion() {
        return tiponotificacion;
    }

    public void setTiponotificacion(Tiponotificacion tiponotificacion) {
        this.tiponotificacion = tiponotificacion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getEnvio() {
        return envio;
    }

    public void setEnvio(String envio) {
        this.envio = envio;
    }
}
