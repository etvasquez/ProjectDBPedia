package ec.edu.utpl.dbpedia.ProjectDBPedia.Model;

public class ManiferoCompleto {
    private String descripcion;
    private String imagen;
    private String peso;

    public ManiferoCompleto() {
    }

    public ManiferoCompleto(String descripcion,String imagen, String peso) {
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.peso = peso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }
}
