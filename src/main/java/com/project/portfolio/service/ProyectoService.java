package com.project.portfolio.service;

import com.project.portfolio.dto.ProyectoDTO;
import com.project.portfolio.entity.Proyecto;
import com.project.portfolio.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProyectoService {

    @Autowired
    ProyectoRepository proyectoRepository;

    public ProyectoDTO getOne(Integer id) {
        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);
        return mapToDTO(proyecto);
    }

    public Optional<Proyecto> getByNombre(String nombre) {
        return proyectoRepository.findByNombre(nombre);
    }

    public void save(Proyecto proyecto) {
        proyectoRepository.save(proyecto);
    }

    public void delete(Integer id) {
        proyectoRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return proyectoRepository.existsById(id);
    }

    public boolean exists(String nombre, LocalDate fechaRealizacion, String link) {
        return proyectoRepository.existsByNombre(nombre) && proyectoRepository.existsByFechaRealizacion(fechaRealizacion)
                && proyectoRepository.existsByLink(link);
    }

    public List<ProyectoDTO> mapToDTOS(List<Proyecto> proyectos) {

        return proyectos.stream()
                .map(proyecto -> mapToDTO(proyecto))
                .collect(Collectors.toList());
    }

    private ProyectoDTO mapToDTO(Proyecto proyecto) {
        ProyectoDTO proyectoDTO = new ProyectoDTO(proyecto.getLogo(), proyecto.getNombre(),
                proyecto.getFechaRealizacion().toString(), proyecto.getDescripcion(),
                proyecto.getLink(), proyecto.getImagen());
        proyectoDTO.setId(proyecto.getId());
        proyectoDTO.setUsuarioId(proyecto.getUsuario().getId());

        return proyectoDTO;
    }
}
