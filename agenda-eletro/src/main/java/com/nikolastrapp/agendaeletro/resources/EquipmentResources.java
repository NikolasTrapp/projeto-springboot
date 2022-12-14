package com.nikolastrapp.agendaeletro.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nikolastrapp.agendaeletro.entities.Equipment;
import com.nikolastrapp.agendaeletro.services.EquipmentService;

@RestController // Anotação para definir que esta classe é uma classe controladora
@CrossOrigin("*") // Permitindo o compartilhamento de recursos entre diferentes origens
@RequestMapping(value = "/equipments") // Definindo rota de acesso às rotas referentes a esse controlador
public class EquipmentResources {
	/*
	 * Classe responsável por guardar as rotas essenciais destinadas á entidade
	 * Equipment, esta classe controla operações como queryes, inserts, deletes
	 * e updates, esta é a classe mais próxima do usuário.
	 * As anotações GetMapping, PostMapping, PutMapping e DeleteMapping controlam
	 * as operaçoes essenciais correspondentes aos seus métodos de requisiçao
	 * e correspondentes a sua classe de entidade
	 */

	@Autowired // Definindo que a injeção de dependencia será feita automáticamente
	private EquipmentService service; // Definindo a camada de serviço do equipamento

	@GetMapping
	public ResponseEntity<List<Equipment>> queryAll() {
		List<Equipment> list = service.queryAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/queryEquipmentById/{id}")
	public ResponseEntity<Equipment> queryById(@PathVariable Long id) {
		Equipment p = service.queryById(id);
		return ResponseEntity.ok().body(p);
	}

	@PostMapping("/insertEquipment")
	public ResponseEntity<Equipment> insert(@RequestBody Equipment equipment) {
		equipment = service.insert(equipment);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(equipment.getId())
				.toUri();
		return ResponseEntity.created(uri).body(equipment);
	}

	@DeleteMapping(value = "/deleteEquipment/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/updateEquipment/{id}")
	public ResponseEntity<Equipment> update(@PathVariable Long id, @RequestBody Equipment equipment) {
		equipment = service.update(id, equipment);
		return ResponseEntity.ok().body(equipment);
	}

}
