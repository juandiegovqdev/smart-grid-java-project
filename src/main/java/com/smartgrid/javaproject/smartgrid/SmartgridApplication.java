package com.smartgrid.javaproject.smartgrid;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgrid.javaproject.smartgrid.domain.EventoDto;
import com.smartgrid.javaproject.smartgrid.domain.FuenteDto;
import com.smartgrid.javaproject.smartgrid.model.Evento;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class SmartgridApplication {

    public static List<EventoDto> eventos;
    public static List<FuenteDto> fuentes;

    public static void main(String[] args) {
        SpringApplication.run(SmartgridApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            // read json and write to db
            ObjectMapper mapper = new ObjectMapper();

            TypeReference<List<EventoDto>> typeReferenceEventosDto = new TypeReference<>() {};
            TypeReference<List<FuenteDto>> typeReferenceFuentesDto = new TypeReference<>() {};

            InputStream inputStreamEventos = TypeReference.class.getResourceAsStream("/static/eventos.json");
            InputStream inputStreamFuentes = TypeReference.class.getResourceAsStream("/static/fuentes.json");
            try {
                // Obtenemos eventos desde el fichero JSON.
                eventos = mapper.readValue(inputStreamEventos, typeReferenceEventosDto);
                System.out.println(eventos);

                // Obtenemos fuentes desde el fichero JSON.
                fuentes = mapper.readValue(inputStreamFuentes, typeReferenceFuentesDto);
                System.out.println(fuentes);
            } catch (IOException e) {
                System.out.println("Something went wrong when getting data.");
            }
        };
    }
}
