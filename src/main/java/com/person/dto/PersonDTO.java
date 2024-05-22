package com.person.dto;

import com.person.validation.ValidDateFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDTO {

    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @ValidDateFormat
    private String fechaNacimiento;
    @NotBlank(message = "El DNI no puede estar vacío")
    @Pattern(regexp = "^\\d{8}$", message = "El DNI debe tener exactamente 8 dígitos")
    private String dni;
}
