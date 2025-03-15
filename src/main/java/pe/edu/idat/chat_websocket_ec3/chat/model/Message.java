package pe.edu.idat.chat_websocket_ec3.chat.model;

public class Message {
    private Tiponotificacion tiponotificacion;
    private String contenido;
    private String envio;

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
