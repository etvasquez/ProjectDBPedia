package ec.edu.utpl.dbpedia.ProjectDBPedia.Model;

public class Maniferos {
    private String uri;
    private  String nombre;

    public Maniferos(String uri, String nombre) {
        this.uri = uri;
        this.nombre = nombre;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
