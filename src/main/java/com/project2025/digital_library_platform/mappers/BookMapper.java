package com.project2025.digital_library_platform.mappers;

import com.project2025.digital_library_platform.entity.book.Book;
import com.project2025.digital_library_platform.DTOs.bookDtos.BookCreateDTO;
import com.project2025.digital_library_platform.DTOs.bookDtos.BookResponseDTO;
import com.project2025.digital_library_platform.DTOs.bookDtos.BookUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookResponseDTO toDto(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "googleBooksId", ignore = true)
    @Mapping(target = "active", ignore = true)
    Book toEntity(BookCreateDTO dto);

    List<BookResponseDTO> toResponseList (List<Book> books);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "googleBooksId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromDto(@MappingTarget Book book, BookUpdateDTO dto);


}
