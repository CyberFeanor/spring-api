package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Cliente;
import com.example.service.ClienteService;

public class ClienteController {
	
	@RestController
	@RequestMapping("/api")
	public class ClienteRestController {

		@Autowired
		private ClienteService clienteService;
		
		
		@GetMapping("/cliente")
		public List<Cliente> index(){
			return clienteService.findAll();
		}
		
		/*@GetMapping("/clientes/{id}")
		public Cliente show(@PathVariable Long id) {
			return clienteService.findById(id);
		}*/
		
		
		@GetMapping("/cliente/{id}")
		public ResponseEntity<?> show(@PathVariable Long id){
			Cliente cliente = null;
			Map<String,Object> response = new HashMap<>();
			
			try {
				cliente = clienteService.findById(id);
			} catch (DataAccessException e) {
				response.put("mensaje","Error al realizar consulta en base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			if(cliente == null) {
				response.put("mensaje", "El cliente ID: ".concat(id.toString().concat("no existe en la base de datos")));
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
		}
		
		
		@PostMapping("/cliente")
		@ResponseStatus(HttpStatus.CREATED)
		public Cliente create(@RequestBody Cliente cliente) {
			return clienteService.save(cliente);
		}
		
		@PutMapping("/cliente/{id}")
		@ResponseStatus(HttpStatus.CREATED)
		public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
			Cliente clienteUpdate = clienteService.findById(id);
			
			clienteUpdate.setApellidos(cliente.getApellidos());
			clienteUpdate.setNombre(cliente.getNombre());
			clienteUpdate.setEmail(cliente.getEmail());
			
			return clienteService.save(clienteUpdate);
		}
		
		@DeleteMapping("cliente/{id}")
		public void delete(@PathVariable Long id) {
			clienteService.delete(id);
		}
	}

}
