package com.example.pos.Model.Mappers;


import com.example.pos.Model.DTO.BookReduceInfoDTO;
import com.example.pos.Model.Entities.BookReduceInfo.BookReduceInfo;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BookReduceInfoMapper {

    public BookReduceInfoDTO bookReduceToBookReduceDTO(BookReduceInfo bookReduceInfo){
        BookReduceInfoDTO bookReduceInfoDTO = BookReduceInfoDTO.builder()
                .isbn(bookReduceInfo.getIsbn())
                .title(bookReduceInfo.getTitle())
                .genre(bookReduceInfo.getGenre())
                .build();
        return bookReduceInfoDTO;
    }


    public BookReduceInfo bookReduceDTOTobookReduce(BookReduceInfoDTO bookReduceInfoDTO){
        BookReduceInfo bookReduceInfo = BookReduceInfo.builder()
                .isbn(bookReduceInfoDTO.getIsbn())
                .title(bookReduceInfoDTO.getTitle())
                .genre(bookReduceInfoDTO.getGenre())
                .build();
        return bookReduceInfo;
    }


    public List<BookReduceInfoDTO> bookReduceToBookReduceDTOList(List<BookReduceInfo> bookReduceInfoList){
        List<BookReduceInfoDTO> bookReduceInfoDTO = bookReduceInfoList.stream().map(BookReduceInfoMapper::bookReduceToBookReduceDTO).collect(Collectors.toList());
        return bookReduceInfoDTO;
    }


    public List<BookReduceInfo> bookReduceDTOToBookReduceList(List<BookReduceInfoDTO> bookReduceInfoDTOList){
        List<BookReduceInfo> bookReduceInfo = bookReduceInfoDTOList.stream().map(BookReduceInfoMapper::bookReduceDTOTobookReduce).collect(Collectors.toList());
        return bookReduceInfo;
    }

}
