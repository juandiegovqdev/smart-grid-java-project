package com.smartgrid.javaproject.smartgrid.domain;

import com.smartgrid.javaproject.smartgrid.model.Evento;
import com.smartgrid.javaproject.smartgrid.model.Fuente;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FuenteDto {
    private Long id;
    private String nombre;

    public FuenteDto() {}

    public Fuente toFuente(){
        return new Fuente(id, nombre);
    }
}