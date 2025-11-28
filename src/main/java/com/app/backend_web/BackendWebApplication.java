package com.app.backend_web;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.backend_web.entities.Categoria;
import com.app.backend_web.entities.Rol;
import com.app.backend_web.entities.Usuario;
import com.app.backend_web.repositories.CategoriaRepository;
import com.app.backend_web.repositories.RolRepository;
import com.app.backend_web.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;


@SpringBootApplication
@RequiredArgsConstructor
public class BackendWebApplication implements CommandLineRunner {

	private final RolRepository rolRepository;

	private final UsuarioRepository usuarioRepository;

	private final PasswordEncoder passwordEncoder;

	private final CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendWebApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		//CREACION DE ROLES AL INICIAR EL PROYECTO
		if(categoriaRepository.findAll().isEmpty()){
			Categoria categoria1=new Categoria();
			categoria1.setNombre("Marina");
			categoria1.setDescripcion("Platos frescos");
			categoriaRepository.save(categoria1);

			Categoria categoria2=new Categoria();
			categoria2.setNombre("Criolla");
			categoria2.setDescripcion("Platos criollos peruanos");
			categoriaRepository.save(categoria2);
			
			Categoria categoria3=new Categoria();
			categoria3.setNombre("Selva");
			categoria3.setDescripcion("Platos de la amazonia peruan");
			categoriaRepository.save(categoria3);
		}
		//CREACION DE ROLES AL INICIAR EL PROYECTO
		if(rolRepository.findAll().isEmpty()){
			Rol rolAdministrador = new Rol();
			rolAdministrador.setNombre("ROLE_ADMINISTRADOR");
			rolAdministrador.setActivo(true);
			rolRepository.save(rolAdministrador);
			Rol rolUsuario = new Rol();
			rolUsuario.setNombre("ROLE_USUARIO");
			rolUsuario.setActivo(true);
			rolRepository.save(rolUsuario);
			Rol rolModerador = new Rol();
			rolModerador.setNombre("ROLE_MODERADOR");
			rolModerador.setActivo(true);
			rolRepository.save(rolModerador);

		}
		System.out.println("ROLES CREADOS EXITOSAMENTE...");

		//CREACION DE MODERADOR
		if(usuarioRepository.findAllUsuariosModeradores().isEmpty()){
			Usuario usuario=new Usuario();
			usuario.setNombre("Adriel David");
			usuario.setApellido("Cortez Espinoza");
			usuario.setCorreo("adriel@gmail.com");
			Rol rolModerador=rolRepository.findByNombre("ROLE_MODERADOR").orElseThrow(()-> new RuntimeException("No encontrado"));
			usuario.setRol(rolModerador);
			usuario.setPassword(passwordEncoder.encode("12345"));
			usuario.setEstado(true);
			usuario.setBiografia("Moderador de la plataforma Cheftweet");
			usuarioRepository.save(usuario);
		}



		//CREACION DE ADMINISTRADOR
		if(usuarioRepository.findAllUsuariosAdministradores().isEmpty()){
			Usuario usuario=new Usuario();
			usuario.setNombre("Jesus Eduardo");
			usuario.setApellido("Cortez Ramos");
			usuario.setCorreo("jesus@gmail.com");
			Rol rolAdministrador=rolRepository.findByNombre("ROLE_ADMINISTRADOR").orElseThrow(()-> new RuntimeException("No encontrado"));
			usuario.setRol(rolAdministrador);
			usuario.setPassword(passwordEncoder.encode("12345"));
			usuario.setEstado(true);
			usuario.setBiografia("Administrador de la plataforma Cheftweet");
			usuarioRepository.save(usuario);

			Usuario usuario2=new Usuario();
			usuario2.setNombre("Flavio Cesar");
			usuario2.setApellido("Audante Guerrero");
			usuario2.setCorreo("flavio@gmail.com");
			usuario2.setRol(rolAdministrador);
			usuario2.setPassword(passwordEncoder.encode("12345"));
			usuario2.setEstado(true);
			usuario2.setBiografia("Administrador de la plataforma Cheftweet");
			usuarioRepository.save(usuario2);


			Usuario usuario3=new Usuario();
			usuario3.setNombre("Daniel Sariff");
			usuario3.setApellido("Malpartida Huamani");
			usuario3.setCorreo("daniel@gmail.com");
			usuario3.setRol(rolAdministrador);
			usuario3.setPassword(passwordEncoder.encode("12345"));
			usuario3.setEstado(true);
			usuario3.setBiografia("Administrador de la plataforma Cheftweet");
			usuarioRepository.save(usuario3);


			Usuario usuario4=new Usuario();
			usuario4.setNombre("Maria Fernanda");
			usuario4.setApellido("Collana Perez");
			usuario4.setCorreo("maria@gmail.com");
			usuario4.setRol(rolAdministrador);
			usuario4.setPassword(passwordEncoder.encode("12345"));
			usuario4.setEstado(true);
			usuario4.setBiografia("Administrador de la plataforma Cheftweet");
			usuarioRepository.save(usuario4);
			System.out.println("ADMINISTRADORES CREADOS EXITOSAMENTE");
		}


	}

}
