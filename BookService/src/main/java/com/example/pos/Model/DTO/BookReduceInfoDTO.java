package com.example.pos.Model.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookReduceInfoDTO {

    private String isbn;
    private String title;
    private String genre;
}
