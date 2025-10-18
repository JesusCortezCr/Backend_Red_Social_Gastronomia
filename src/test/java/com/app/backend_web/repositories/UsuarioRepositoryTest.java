package com.app.backend_web.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.app.backend_web.entities.Rol;
import com.app.backend_web.entities.Usuario;

@DataJpaTest
@Transactional
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;

    private Rol rolUsuario;

    @BeforeEach
    void setUp() {
        rolUsuario = new Rol();
        rolUsuario.setNombre("ROLE_USER");
        rolUsuario = rolRepository.save(rolUsuario);
    }

    @Test
    @DisplayName("Test de Inserción (Crear): Guarda un usuario y verifica que tiene ID")
    void guardarUsuario_retornaUsuarioConId() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setApellido("Prueba");
        usuario.setCorreo("test.guardar@gmail.com");
        usuario.setPassword("password123");
        usuario.setRol(rolUsuario);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        assertNotNull(usuarioGuardado.getId(), "El usuario guardado debe tener un ID asignado");
        assertEquals("Test", usuarioGuardado.getNombre());
    }

    @Test
    @DisplayName("Test de Lectura (Leer por ID): Busca un usuario por su ID y lo encuentra")
    void buscarUsuarioPorId_retornaUsuarioCorrecto() {
        usuarioRepository.save(guardarUsuarioDePrueba("usuario.buscar.id@gmail.com"));

        List<Usuario> usuarios = usuarioRepository.findAll();
        Long idBuscado = usuarios.get(0).getId();

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(idBuscado);

        assertTrue(usuarioEncontrado.isPresent(), "El usuario con ID " + idBuscado + " debería existir");
        assertEquals("usuario.buscar.id@gmail.com", usuarioEncontrado.get().getCorreo());
    }

    @Test
    @DisplayName("Test de Lectura (Leer por Correo): Busca un usuario por su correo y lo encuentra")
    void buscarUsuarioPorCorreo_retornaUsuarioCorrecto() {
        guardarUsuarioDePrueba("usuario.buscar.correo@gmail.com");

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByCorreo("usuario.buscar.correo@gmail.com");

        assertTrue(usuarioEncontrado.isPresent(), "El usuario con correo 'usuario.buscar.correo@gmail.com' debería existir");
        assertEquals("Usuario Test", usuarioEncontrado.get().getNombre());
    }
    
    @Test
    @DisplayName("Test de Actualización (Modificar): Modifica un usuario y persiste los cambios")
    void actualizarUsuario_cambioEsPersistido() {
        Usuario usuarioGuardado = guardarUsuarioDePrueba("usuario.actualizar@gmail.com");
        Long id = usuarioGuardado.getId();

        usuarioGuardado.setNombre("Nombre Modificado");
        usuarioRepository.save(usuarioGuardado);

        Optional<Usuario> usuarioActualizado = usuarioRepository.findById(id);
        assertTrue(usuarioActualizado.isPresent(), "El usuario debería existir");
        assertEquals("Nombre Modificado", usuarioActualizado.get().getNombre(), "El nombre del usuario debería haber sido actualizado");
    }

    @Test
    @DisplayName("Test de Eliminación (Borrar): Elimina un usuario y verifica que ya no existe")
    void eliminarUsuarioPorId_yaNoExiste() {
        Usuario usuarioGuardado = guardarUsuarioDePrueba("usuario.eliminar@gmail.com");
        Long idAEliminar = usuarioGuardado.getId();

        assertTrue(usuarioRepository.existsById(idAEliminar), "El usuario debería existir antes de la eliminación");

        usuarioRepository.deleteById(idAEliminar);

        assertFalse(usuarioRepository.existsById(idAEliminar), "El usuario no debería existir después de ser eliminado");
        Optional<Usuario> usuarioEliminado = usuarioRepository.findById(idAEliminar);
        assertFalse(usuarioEliminado.isPresent(), "La búsqueda del usuario eliminado debe devolver vacío");
    }

    @Test
    @DisplayName("Test de Existencia: Verifica si un usuario existe por su correo")
    void existeUsuarioPorCorreo_retornaVerdaderoOFalso() {
        guardarUsuarioDePrueba("usuario.existe@gmail.com");

        assertTrue(usuarioRepository.existsByCorreo("usuario.existe@gmail.com"), "Debería existir un usuario con ese correo");
        assertFalse(usuarioRepository.existsByCorreo("correo.inexistente@gmail.com"), "No debería existir un usuario con ese correo");
    }

    private Usuario guardarUsuarioDePrueba(String correo) {
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario Test");
        usuario.setApellido("Apellido Test");
        usuario.setCorreo(correo);
        usuario.setPassword("password123");
        usuario.setRol(rolUsuario);
        return usuarioRepository.save(usuario);
    }
}