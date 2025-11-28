package com.app.backend_web.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.backend_web.dto.PublicacionRequest;
import com.app.backend_web.entities.Categoria;
import com.app.backend_web.entities.Image;
import com.app.backend_web.entities.Publicacion;
import com.app.backend_web.entities.Usuario;
import com.app.backend_web.repositories.CategoriaRepository;
import com.app.backend_web.repositories.PublicacionRepository;
import com.app.backend_web.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ImageService imageService;

    @Transactional
    public Publicacion crearPublicacion(Long usuarioId, Long categoriaId, Publicacion publicacion) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoriaId));

        publicacion.setUsuario(usuario);
        publicacion.setCategoria(categoria);

        return publicacionRepository.save(publicacion);
    }

    public List<Publicacion> listarPublicaciones() {
        return publicacionRepository.findAll();
    }

    //VERSION CON IMAGEN(OFICIAL)
    public Publicacion guardarPublicacion(PublicacionRequest publicacionRequest,MultipartFile file,String userEmail) throws IOException{
        Publicacion publicacion=new Publicacion();
        Usuario usuario = usuarioRepository.findByCorreo(userEmail)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + userEmail));
        if(file!=null && !file.isEmpty()){
            Image image=imageService.uploadImages(file);
            publicacion.setCalificacion(0);
            publicacion.setCategoria(categoriaRepository.findById(publicacionRequest.getCategoriaId()).get());
            publicacion.setImage(image);
            publicacion.setTitulo(publicacionRequest.getTitulo());
            publicacion.setDescripcion(publicacionRequest.getDescripcion());
            publicacion.setUsuario(usuario);
        }
        return publicacionRepository.save(publicacion);
    }

    public Publicacion updatePublicacionImage(MultipartFile file, Publicacion publicacion) throws IOException {
        if (publicacion.getImage() != null) {
            imageService.deleteImages(publicacion.getImage());
        }
        Image newImage = imageService.uploadImages(file);
        publicacion.setImage(newImage);
        return publicacionRepository.save(publicacion);
    }

    public Optional<Publicacion> buscarPublicacionPorId(Long id){
        return publicacionRepository.findById(id);
    }
}
