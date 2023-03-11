package com.project.portfolio.service;

import com.project.portfolio.dto.ExperienciaDTO;
import com.project.portfolio.entity.Experiencia;
import com.project.portfolio.repository.ExperienciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExperienciaService {

    @Autowired
    ExperienciaRepository experienciaRepository;

    public ExperienciaDTO getOne(Integer id) {
        Experiencia experiencia = experienciaRepository.findById(id).orElse(null);
        return mapToDTO(experiencia);
    }

    public Optional<Experiencia> getByCargo(String cargo) {
        return experienciaRepository.findByCargo(cargo);
    }

    public void save(Experiencia experiencia) {
        experienciaRepository.save(experiencia);
    }

    public void delete(Integer id) {
        experienciaRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return experienciaRepository.existsById(id);
    }

    public boolean exists(String cargo, String empresa, LocalDate fechaInicio) {
        return experienciaRepository.existsByCargo(cargo) && experienciaRepository.existsByEmpresa(empresa)
                && experienciaRepository.existsByFechaInicio(fechaInicio);
    }

    public List<ExperienciaDTO> mapToDTOS(List<Experiencia> experiencias) {

        return experiencias.stream()
                .map(experiencia -> mapToDTO(experiencia))
                .collect(Collectors.toList());
    }

    private ExperienciaDTO mapToDTO(Experiencia experiencia) {
        String fechaFinExperiencia = null;
        if (experiencia.getFechaFin() != null) {
            fechaFinExperiencia = experiencia.getFechaFin().toString();
        }
        ExperienciaDTO experienciaDTO = new ExperienciaDTO(experiencia.getLogo(), experiencia.getCargo(),
                experiencia.getEmpresa(), experiencia.getFechaInicio().toString(), fechaFinExperiencia,
                experiencia.getEsTrabajoActual(), experiencia.getDescripcion());
        experienciaDTO.setId(experiencia.getId());
        experienciaDTO.setUsuarioId(experiencia.getUsuario().getId());

        return experienciaDTO;
    }
}
