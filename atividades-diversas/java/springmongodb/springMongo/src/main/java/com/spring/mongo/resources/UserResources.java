package com.spring.mongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.spring.mongo.domain.User;
import com.spring.mongo.dto.UserDTO;
import com.spring.mongo.services.UserServices;

@RestController
@RequestMapping(value="/users")
public class UserResources 	{
	
	/*	pode ser este método abaixo 
	 * 
			@RequestMapping(method=RequestMethod.GET)
	
	 ou este abaixo em uso ambos são verbos http: GET
	 
	 */
	
	@Autowired
	private UserServices service;  
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll(){
		List<User> list = service.findAll();
		List<UserDTO> listDTO = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<UserDTO> findById(@PathVariable String id){
		User obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(obj));
	}
	
	/* 
	 * o prof° utiliza este: ResponseEntity<void>
	 * mas o compilador não permitiu!
	 * */
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO objDTO){
		User obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}    	
}
