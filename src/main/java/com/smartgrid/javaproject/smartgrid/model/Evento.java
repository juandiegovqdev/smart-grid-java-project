package com.smartgrid.javaproject.smartgrid.model;

public record Evento(long id, long fuente_id, String timestamp, long valor) {
}