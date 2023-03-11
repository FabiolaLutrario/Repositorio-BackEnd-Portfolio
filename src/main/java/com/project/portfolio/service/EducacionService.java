package com.project.portfolio.service;

import com.project.portfolio.dto.EducacionDTO;
import com.project.portfolio.entity.Educacion;
import com.project.portfolio.repository.EducacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EducacionService {

    @Autowired
    EducacionRepository educacionRepository;

    public EducacionDTO getOne(Integer id) {
        Educacion educacion = educacionRepository.findById(id).orElse(null);
        return mapToDTO(educacion);
    }

    public Optional<Educacion> getByTitulo(String titulo) {
        return educacionRepository.findByTitulo(titulo);
    }

    public void save(Educacion educacion) {
        educacionRepository.save(educacion);
    }

    public void delete(int id) {
        educacionRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return educacionRepository.existsById(id);
    }

    public boolean exists(String titulo, String centroEducativo, LocalDate fechaInicio) {
        return educacionRepository.existsByTitulo(titulo) && educacionRepository.existsByCentroEducativo(centroEducativo)
                && educacionRepository.existsByFechaInicio(fechaInicio);
    }

    public List<EducacionDTO> mapToDTOS(List<Educacion> estudios) {

        return estudios.stream()
                .map(educacion -> mapToDTO(educacion))
                .collect(Collectors.toList());
    }

    private EducacionDTO mapToDTO(Educacion educacion) {
        String fechaFinEducacion = null;
        if (educacion.getFechaFin() != null) {
            fechaFinEducacion = educacion.getFechaFin().toString();
        }
        EducacionDTO educacionDTO = new EducacionDTO(educacion.getLogo(), educacion.getTitulo(),
                educacion.getCentroEducativo(), educacion.getFechaInicio().toString(), fechaFinEducacion,
                educacion.getSeEncuentraCursando(), educacion.getDescripcion());
        educacionDTO.setId(educacion.getId());
        educacionDTO.setUsuarioId(educacion.getUsuario().getId());

        return educacionDTO;
    }
}
