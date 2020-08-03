package ec.edu.utpl.dbpedia.ProjectDBPedia.Consultas;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;

import ec.edu.utpl.dbpedia.ProjectDBPedia.Model.ManiferoCompleto;
import ec.edu.utpl.dbpedia.ProjectDBPedia.Model.Maniferos;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class consultasBDPedia {

    public static final String WS_URL_DBPEDIA = "http://dbpedia.org/sparql";

    public String consultasDBPedia() {
        boolean res=false;
        String defaultGraph = "http://dbpedia.org";
        String qry = "ASK " +
                "WHERE { " +
                "<http://dbpedia.org/resource/Giraffa> rdf:type ?a. " +
                "}";
        String salida = "&output=json";
        String url = "";
        try {
            defaultGraph = URLEncoder.encode(defaultGraph, "UTF-8");
            qry = URLEncoder.encode(qry, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }

        Client client = new Client();
        url = WS_URL_DBPEDIA + "?default-graph-uri=" + defaultGraph
                + "&query=" + qry + salida;
        WebResource resource = client.resource(url);
        String result = resource.accept("application/sparql-results+json").
                get(String.class);
        try {
            System.out.println(result);
            res = new JSONObject(result).getBoolean("boolean");
            //procesar(result);
        } catch (JSONException ex) {
        }
        return String.valueOf(res);
    }

    public ArrayList<Maniferos> consultarAnimalesM() {
        ArrayList<Maniferos> listaAnimales = new ArrayList<>();
        String defaultGraph = "http://dbpedia.org";
        String qry = "SELECT ?animal ?nombre " +
                "WHERE { " +
                "?animal rdf:type dbo:Mammal. " +
                "?animal rdfs:label ?nombre ." +
                "FILTER (lang(?nombre) = 'es')" +
                "} LIMIT 500";
        String salida = "&output=json";
        String url = "";
        try {
            defaultGraph = URLEncoder.encode(defaultGraph, "UTF-8");
            qry = URLEncoder.encode(qry, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        Client client = new Client();
        url = WS_URL_DBPEDIA + "?default-graph-uri=" + defaultGraph
                + "&query=" + qry + salida;
        WebResource resource = client.resource(url);
        String result = resource.accept("application/sparql-results+json").
                get(String.class);
        try {
            System.out.println(result);
            JSONObject respuesta = new JSONObject(result).getJSONObject("results");
            JSONArray bindings = respuesta.getJSONArray("bindings");
            JSONObject nombre;
            JSONObject animal;
            for (int i = 0; i < bindings.length(); i++) {
                animal = bindings.getJSONObject(i).getJSONObject("animal");
                nombre = bindings.getJSONObject(i).getJSONObject("nombre");
                String uri = animal.get("value").toString().substring(28);
                Maniferos maniferos = new Maniferos(uri,nombre.get("value").toString());
                listaAnimales.add(maniferos);
                System.out.println("URI COMPLETA: "+animal.get("value").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaAnimales;
    }

    public ArrayList<Maniferos> AnimalAleatorio() {
        ArrayList<Maniferos> listaAnimales = new ArrayList<>();
        ArrayList<Maniferos> listaAnimalesAleatoria = new ArrayList<>();
        String defaultGraph = "http://dbpedia.org";
        String qry = "SELECT str(?nombre) as ?nombre ?imagen " +
                "WHERE { " +
                "?animal rdf:type dbo:Mammal. " +
                "?animal rdfs:label ?nombre ." +
                "?animal dbo:thumbnail ?imagen ." +
                "FILTER (lang(?nombre) = 'es')"+
                "} LIMIT 50";
        String salida = "&output=json";
        String url = "";
        try {
            defaultGraph = URLEncoder.encode(defaultGraph, "UTF-8");
            qry = URLEncoder.encode(qry, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        Client client = new Client();
        url = WS_URL_DBPEDIA + "?default-graph-uri=" + defaultGraph
                + "&query=" + qry + salida;
        WebResource resource = client.resource(url);
        String result = resource.accept("application/sparql-results+json").
                get(String.class);
        try {
            System.out.println(result);
            JSONObject respuesta = new JSONObject(result).getJSONObject("results");
            JSONArray bindings = respuesta.getJSONArray("bindings");
            JSONObject nombre;
            JSONObject imagen;
            for (int i = 0; i < bindings.length(); i++) {
                imagen = bindings.getJSONObject(i).getJSONObject("imagen");
                nombre = bindings.getJSONObject(i).getJSONObject("nombre");
                Maniferos maniferos = new Maniferos(imagen.get("value").toString(),nombre.get("value").toString());
                listaAnimales.add(maniferos);
            }
            for(int i = 0; i<3;i++){
                double valor = Math.random();
                BigDecimal posicion = new BigDecimal(String.valueOf((valor*(listaAnimales.size()-1))));
                listaAnimalesAleatoria.add(listaAnimales.get((int) posicion.longValue()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaAnimalesAleatoria;
    }

    public ManiferoCompleto consultarDescripcionAnimal(String tipoAnimal) {
        System.out.println("ID CONSULTA " +tipoAnimal);
        ManiferoCompleto maniferos = new ManiferoCompleto();
        String defaultGraph = "http://dbpedia.org";
        String qry = "SELECT COALESCE(str(?peso),\"S/N\") AS ?peso COALESCE(str(?imagen),\"S/N\") AS ?imagen str(?descripcion) AS ?descripcion  " +
                "WHERE { " +
                "OPTIONAL { <http://dbpedia.org/resource/"+tipoAnimal+"> "+"dbo:abstract ?descripcion } . "+
                "OPTIONAL { <http://dbpedia.org/resource/"+tipoAnimal+"> "+"dbo:thumbnail ?imagen } . "+
                "OPTIONAL { <http://dbpedia.org/resource/"+tipoAnimal+"> "+"dbp:fat ?peso } . "+
                "FILTER (lang(?descripcion) = 'es')" +
                "}";
        String salida = "&output=json";
        String url = "";
        try {
            defaultGraph = URLEncoder.encode(defaultGraph, "UTF-8");
            qry = URLEncoder.encode(qry, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        Client client = new Client();
        url = WS_URL_DBPEDIA + "?default-graph-uri=" + defaultGraph
                + "&query=" + qry + salida;
        WebResource resource = client.resource(url);
        String result = resource.accept("application/sparql-results+json").
                get(String.class);
        try {
            System.out.println(result);
            JSONObject respuesta = new JSONObject(result).getJSONObject("results");
            JSONArray bindings = respuesta.getJSONArray("bindings");
            JSONObject peso = bindings.getJSONObject(0).getJSONObject("peso");
            JSONObject imagen = bindings.getJSONObject(0).getJSONObject("imagen");
            JSONObject descripcion = bindings.getJSONObject(0).getJSONObject("descripcion");;
            maniferos = new ManiferoCompleto(descripcion.get("value").toString(),imagen.get("value").toString(),
                    peso.get("value").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return maniferos;
    }

}
