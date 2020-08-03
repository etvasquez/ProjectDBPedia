package ec.edu.utpl.dbpedia.ProjectDBPedia.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ec.edu.utpl.dbpedia.ProjectDBPedia.Consultas.consultasBDPedia;
import ec.edu.utpl.dbpedia.ProjectDBPedia.Model.ManiferoCompleto;
import ec.edu.utpl.dbpedia.ProjectDBPedia.Model.Maniferos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class HomeController {
    ArrayList<Maniferos> listaManiferos = new ArrayList<>();
    ArrayList<Maniferos> listaManiferosAleatoria = new ArrayList<>();
    ManiferoCompleto manifero = new ManiferoCompleto();
    consultasBDPedia consultas = new consultasBDPedia();

    @GetMapping("/")
    public String homePage(Model model) {
        listaManiferos = consultas.consultarAnimalesM();
        model.addAttribute("listaManiferos", listaManiferos);
        return "home";
    }

    @GetMapping("/manifero/{id}")
    public ResponseEntity<ManiferoCompleto> maniferoDescripcion(@PathVariable(value="id") String id) throws JsonProcessingException {
        manifero = consultas.consultarDescripcionAnimal(id);
        return new ResponseEntity<ManiferoCompleto>(manifero, HttpStatus.OK);
    }

    @GetMapping("/juguemos")
    public String juguemos(Model model) {
        listaManiferosAleatoria = consultas.AnimalAleatorio();
        model.addAttribute("listaManiferos", listaManiferosAleatoria);
        return "juguemos";
    }

}
