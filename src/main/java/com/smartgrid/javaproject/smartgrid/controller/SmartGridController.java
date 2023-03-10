package com.smartgrid.javaproject.smartgrid.controller;

import com.smartgrid.javaproject.smartgrid.SmartgridApplication;
import com.smartgrid.javaproject.smartgrid.domain.EventoDto;
import com.smartgrid.javaproject.smartgrid.model.Evento;
import com.smartgrid.javaproject.smartgrid.model.Fuente;
import com.smartgrid.javaproject.smartgrid.utils.Helper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
public class SmartGridController {

    /** Endpoint name: http://localhost:8080/event_given_name?name=Nombre1
     *
     * @param name
     * @return List of "Evento" with the ones whose "Fuente" assigned is the same as the one given
     *         as param (we are given the name, so we have to find for the right "Fuente").
     */
    @GetMapping("/event_given_name")
    public List<Evento> eventosGivenName(
            @RequestParam(value = "name", defaultValue = "World") String name
    ) {
        if(Objects.isNull(name)){
            return new ArrayList<>();
        }else{
            Fuente fuente = SmartgridApplication.fuentes
                    .stream()
                    .findFirst()
                    .filter(f -> name.equals(f.getNombre())).get().toFuente();
            return SmartgridApplication.eventos
                    .stream()
                    .filter(e -> e.getFuente_id() == fuente.id())
                    .collect(Collectors.toList()).stream().map(e -> e.toEvent()).toList();
        }
    }

    /** Endpoint example: http://localhost:8080/event_given_timestamp?fecha_ini=650282751&fecha_fin=1678444000
     *
     * @param fechaIni
     * @param fechaFin
     * @return List of "Evento" with the ones whose timestamp are between fechaIni and fechaFin.
     */
    @GetMapping("/event_given_timestamp")
    public List<Evento> eventosTimestamp(
            @RequestParam(value = "fecha_ini", defaultValue = "1678442051") String fechaIni,
            @RequestParam(value = "fecha_max", defaultValue = "1678442051") String fechaFin
    ) {
        if(Objects.isNull(fechaIni) || Objects.isNull(fechaFin)){
            return new ArrayList<>();
        }else{
            return SmartgridApplication.eventos
                    .stream()
                    .filter(e -> Helper.isDateInBetweenIncludingEndPoints(
                                    Helper.stringToDate(fechaIni),
                                    Helper.stringToDate(fechaFin),
                                    Helper.stringToDate(e.getTimestamp())
                            )
                    )
                    .collect(Collectors.toList()).stream().map(e -> e.toEvent()).toList();
        }
    }

    /** Endpoint example: http://localhost:8080/event_given_value?valor_min=1&valor_max=21412413
     *
     * @param valorMin
     * @param valorMax
     * @return List of "Evento" with the ones whose values are between valorMin and valorMax.
     */
    @GetMapping("/event_given_value")
    public List<Evento> eventosValores(
            @RequestParam(value = "valor_min") long valorMin,
            @RequestParam(value = "valor_max") long valorMax
    ) {
        if(Objects.isNull(valorMin) || Objects.isNull(valorMax)){
            return new ArrayList<>();
        }else{
            return SmartgridApplication.eventos
                    .stream()
                    .filter(e -> e.getValor() >= valorMin && e.getValor() <= valorMax)
                    .collect(Collectors.toList()).stream().map(e -> e.toEvent()).toList();
        }
    }
}