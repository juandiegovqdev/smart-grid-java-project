package com.smartgrid.javaproject.smartgrid;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgrid.javaproject.smartgrid.domain.EventoDto;
import com.smartgrid.javaproject.smartgrid.domain.FuenteDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class SmartgridApplicationTests {

	static String RESOURCE_URL = "http://localhost:8080";

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void eventosLoadedCorrectly() {
		ObjectMapper mapper = new ObjectMapper();
		List<EventoDto> eventos = null;
		TypeReference<List<EventoDto>> typeReferenceEventosDto = new TypeReference<>() {};
		InputStream inputStreamEventos = TypeReference.class.getResourceAsStream("/static/eventos.json");
		try {
			eventos = mapper.readValue(inputStreamEventos, typeReferenceEventosDto);
			System.out.println(eventos);
		} catch (IOException e) {
			System.out.println("Something went wrong when getting data.");
		}
		assertThat(!eventos.isEmpty());
	}

	@Test
	public void fuentesLoadedCorrectly() {
		ObjectMapper mapper = new ObjectMapper();
		List<FuenteDto> fuentes = null;
		TypeReference<List<FuenteDto>> typeReferenceFuentesDto = new TypeReference<>() {};
		InputStream inputStreamFuentes = TypeReference.class.getResourceAsStream("/static/fuentes.json");
		try {
			fuentes = mapper.readValue(inputStreamFuentes, typeReferenceFuentesDto);
			System.out.println(fuentes);
		} catch (IOException e) {
			System.out.println("Something went wrong when getting data.");
		}
		assertThat(!fuentes.isEmpty());
	}

	@Test
	public void eventsGivenNameTest() {
		String endpoint = RESOURCE_URL + "/event_given_name?name=Nombre1";
		String body = this.restTemplate.getForObject(endpoint, String.class);

		ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

		assertThat(body).isEqualTo("[{\"id\":1,\"fuente_id\":1,\"timestamp\":\"1678386418\",\"valor\":7557757},{\"id\":1,\"fuente_id\":1,\"timestamp\":\"965901951\",\"valor\":547457}]");
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void eventoTimestampTest() {
		String endpoint = RESOURCE_URL + "/event_given_timestamp?fecha_ini=650282751&fecha_fin=1678444000";
		String body = this.restTemplate.getForObject(endpoint, String.class);

		ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

		assertThat(body).isEqualTo("[{\"id\":1,\"fuente_id\":1,\"timestamp\":\"1678386418\",\"valor\":7557757},{\"id\":1,\"fuente_id\":3,\"timestamp\":\"1533895551\",\"valor\":52767},{\"id\":1,\"fuente_id\":1,\"timestamp\":\"965901951\",\"valor\":547457}]");
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void eventoValoresTest() {
		String endpoint = RESOURCE_URL + "/event_given_value?valor_min=1&valor_max=21412413";
		String body = this.restTemplate.getForObject(endpoint, String.class);

		ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

		assertThat(body).isEqualTo("[{\"id\":1,\"fuente_id\":1,\"timestamp\":\"1678386418\",\"valor\":7557757},{\"id\":1,\"fuente_id\":2,\"timestamp\":\"1678442751\",\"valor\":1312},{\"id\":1,\"fuente_id\":4,\"timestamp\":\"1691661951\",\"valor\":4124124},{\"id\":1,\"fuente_id\":3,\"timestamp\":\"1533895551\",\"valor\":52767},{\"id\":1,\"fuente_id\":1,\"timestamp\":\"965901951\",\"valor\":547457}]");
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

}
