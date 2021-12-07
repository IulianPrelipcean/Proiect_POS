package com.example.pos.Model.DTO;


import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
public class AuthorDTO {

    @NotNull
    private Long id;

    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

}
