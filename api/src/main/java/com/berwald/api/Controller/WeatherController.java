package com.berwald.api.Controller;

import com.berwald.api.Model.Consulta;
import com.berwald.api.Service.ApiServiceWeather;
import com.berwald.api.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class WeatherController {

    @Autowired
    private ApiServiceWeather apiServiceWeather;

    @Autowired
    private FileService fileService;

    @GetMapping("/")
    public String showForm(Model model) {
        return "formPage";
    }

    @PostMapping("/weather")
    public String getWeather(@RequestParam Double latitude, @RequestParam Double longitude, Model model) {
        System.out.println("Recebendo latitude: " + latitude + ", longitude: " + longitude);

        // Validação de coordenadas
        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            model.addAttribute("error", "Por favor, insira valores válidos para latitude (-90 a 90) e longitude (-180 a 180).");
            return "weatherPage";
        }

        String weatherData = apiServiceWeather.getWeather(latitude, longitude);
        System.out.println("Dados do clima recebidos: " + weatherData);

        if (weatherData != null) {
            model.addAttribute("weatherData", weatherData);
            Consulta novaConsulta = new Consulta(LocalDateTime.now(), latitude, longitude, weatherData);
            fileService.saveHistory(novaConsulta);

            // Adiciona a última consulta ao modelo
            List<Consulta> consultas = fileService.loadHistory();
            if (!consultas.isEmpty()) {
                Consulta latestConsulta = consultas.get(consultas.size() - 1);
                model.addAttribute("latestConsulta", latestConsulta);
                System.out.println("Última consulta: " + latestConsulta);
            }
        } else {
            model.addAttribute("error", "Dados não disponíveis.");
        }
        return "weatherPage";
    }
}
