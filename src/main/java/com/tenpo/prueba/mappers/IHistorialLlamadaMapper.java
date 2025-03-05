package com.tenpo.prueba.mappers;

import com.tenpo.prueba.dto.HistorialLlamadaDTO;
import com.tenpo.prueba.model.HistorialLlamada;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IHistorialLlamadaMapper {

    HistorialLlamada toHistorialLlamada(HistorialLlamadaDTO historialLlamadaDTO);

    HistorialLlamadaDTO toHistorialLlamadaDTO(HistorialLlamada historialLlamada);
}