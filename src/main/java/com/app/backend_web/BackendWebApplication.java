package com.app.backend_web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.backend_web.entities.Rol;
import com.app.backend_web.repositories.RolRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
public class BackendWebApplication implements CommandLineRunner {

	@Autowired
	private RolRepository rolRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendWebApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		
		Rol rolAdministrador = new Rol();
		rolAdministrador.setNombre("ROLE_ADMINISTRADOR");
		rolRepository.save(rolAdministrador);
		Rol rolUsuario = new Rol();
		rolUsuario.setNombre("ROLE_USUARIO");
		rolRepository.save(rolUsuario);
		Rol rolModerador = new Rol();
		rolModerador.setNombre("ROLE_MODERADOR");
		rolRepository.save(rolModerador);

	}

}
