package com.app.backend_web.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.backend_web.entities.Publicacion;
import com.app.backend_web.repositories.PublicacionRepository;
import com.app.backend_web.repositories.UsuarioRepository;
import com.app.backend_web.repositories.CategoriaRepository;
import com.app.backend_web.entities.Usuario;
import com.app.backend_web.entities.Categoria;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicacionService {
    
    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public List<Publicacion> listadoTodasPublicaciones() {
        return publicacionRepository.findAll();
    }

    public Optional<Publicacion> obtenerPublicacionPorId(Long id) {
        return publicacionRepository.findById(id);
    }

    public Publicacion guardarPublicacion(Long usuarioId, Long categoriaId, Publicacion publicacion) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        publicacion.setUsuario(usuario);
        publicacion.setCategoria(categoria);
        
        return publicacionRepository.save(publicacion);
    }

    public Optional<Publicacion> actualizarPublicacion(Long id, Publicacion publicacion) {
        return publicacionRepository.findById(id).map(pub -> {
            pub.setTitulo(publicacion.getTitulo());
            pub.setDescripcion(publicacion.getDescripcion());
            pub.setContenido(publicacion.getContenido());
            pub.setImagen(publicacion.getImagen());
            pub.setEstadoPublicacion(publicacion.getEstadoPublicacion());
            pub.setCantidadReportes(publicacion.getCantidadReportes());
            
            if (publicacion.getUsuario() != null) {
                Usuario usuario = usuarioRepository.findById(publicacion.getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                pub.setUsuario(usuario);
            }
            
            if (publicacion.getCategoria() != null) {
                Categoria categoria = categoriaRepository.findById(publicacion.getCategoria().getId())
                        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
                pub.setCategoria(categoria);
            }
            
            return publicacionRepository.save(pub);
        });
    }

    public void eliminarPublicacion(Long id) {
        publicacionRepository.deleteById(id);
    }
}