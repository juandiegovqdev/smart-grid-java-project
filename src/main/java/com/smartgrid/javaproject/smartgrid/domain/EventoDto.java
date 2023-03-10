package com.smartgrid.javaproject.smartgrid.domain;

// public record EventoDto(long id, long fuente_id, String timestamp, long valor) { }

import com.smartgrid.javaproject.smartgrid.model.Evento;
import jdk.jfr.Event;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventoDto {
    private Long id;
    private long fuente_id;
    private String timestamp;
    private long valor;

    public EventoDto() {
    }

    public Evento toEvent(){
        return new Evento(id, fuente_id, timestamp, valor);
    }
}