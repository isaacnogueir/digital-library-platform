package com.project2025.digital_library_platform.services;

import com.project2025.digital_library_platform.converters.BookConverter;
import com.project2025.digital_library_platform.domain.book.Book;
import com.project2025.digital_library_platform.domain.book.Status;
import com.project2025.digital_library_platform.domain.book.dtos.BookCreateDTO;
import com.project2025.digital_library_platform.domain.book.dtos.BookResponseDTO;
import com.project2025.digital_library_platform.domain.book.dtos.BookUpdateDTO;
import com.project2025.digital_library_platform.events.BookCreatedEvent;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.repositories.BookRepository;
import org.hibernate.dialect.function.array.ArrayGetUnnestFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.project2025.digital_library_platform.domain.book.Status.AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookConverter bookConverter;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private BookService bookService;

    private BookCreateDTO bookCreateDTO;
    private BookUpdateDTO bookUpdateDTO;
    private BookResponseDTO bookResponseDTO;
    private Book book;

    @BeforeEach
    void setUp() {

        // Dados de entrada
        bookCreateDTO = new BookCreateDTO("As Walkírias",
                "Paulo Coelho",
                "Casa Publicadora de letras",
                "10-05-1989",
                "5454545454",
                "545454524",
                "Livro falando sobre as Wiccas",
                230,
                "45454545434", AVAILABLE);

        // Livro mock
        book = new Book();
        book.setId(1L);
        book.setTitle("As Walkírias");
        book.setAuthors("Paulo Coelho");
        book.setPublisher("Casa publicadora de Letras");
        book.setPublishedDate("10-05-1989");
        book.setIsbn10("5454545454");
        book.setIsbn13("545454524");
        book.setDescription("Livro falando sobre as Wiccas");
        book.setPageCount(230);
        book.setStatus(AVAILABLE);

        // Response mock
        bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setId(1L);
        bookResponseDTO.setTitle("As Walkírias");
        bookResponseDTO.setAuthors("Paulo Coelho");
        bookResponseDTO.setPublisher("Casa publicadora de Letras");
        bookResponseDTO.setPublishedDate("10-05-1989");
        bookResponseDTO.setIsbn10("5454545454");
        bookResponseDTO.setIsbn13("545454524");
        bookResponseDTO.setDescription("Livro falando sobre as Wiccas");
        bookResponseDTO.setPageCount(230);
        bookResponseDTO.setStatus(AVAILABLE);

        // Update mock
        bookUpdateDTO = new BookUpdateDTO("As Walkírias: Uma jornada espiritual",
                "Paulo Coelho",
                "Casa publicadora de Letra",
                "10-05-1989",
                "5454545454",
                "545454524",
                "Livro falando sobre as Wiccas",
                300);
    }

    private BookResponseDTO buildUpdatedBookResponse() {
        BookResponseDTO updatedResponse = new BookResponseDTO();
        updatedResponse.setId(1L);
        updatedResponse.setTitle("As Walkírias Uma jornada espiritual");
        updatedResponse.setAuthors("Paulo Coelho");
        updatedResponse.setPublisher("Casa publicadora de Letra");
        updatedResponse.setPublishedDate("10-05-1989");
        updatedResponse.setIsbn10("5454545454");
        updatedResponse.setIsbn13("545454524");
        updatedResponse.setDescription("Livro falando sobre as Wiccas");
        updatedResponse.setPageCount(300);
        updatedResponse.setStatus(AVAILABLE);
        return updatedResponse;
    }

    private Book createTestBook(Long id, String title, Status status) {
        Book testBook = new Book();
        testBook.setId(id);
        testBook.setTitle(title);
        testBook.setAuthors("Autor teste");
        testBook.setPublisher("Editora");
        testBook.setPublishedDate("10-10-2010");
        testBook.setIsbn10("54545485242");
        testBook.setIsbn13("852852652");
        testBook.setDescription("Descrição livro");
        testBook.setPageCount(400);
        testBook.setStatus(status);
        testBook.activate();
        return testBook;
    }

    private BookResponseDTO createTestBookResponseDTO(Long id, String title, Status status) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(id);
        dto.setTitle(title);
        dto.setAuthors("Autor Teste");
        dto.setPublisher("Editora Teste");
        dto.setPublishedDate("2023-01-01");
        dto.setIsbn10("123456789" + id);
        dto.setIsbn13("978123456789" + id);
        dto.setDescription("Descrição teste");
        dto.setPageCount(200);
        dto.setStatus(status);
        return dto;
    }

    // =============== TESTES DE CRIAÇÃO ===============

    @Test
    @DisplayName("Deve criar um livro com fluxo completo")
    void createBook_WhenValidData_ShouldExecuteCompleteFlow() {
        // ARRANGE
        when(bookConverter.toEntity(bookCreateDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookConverter.toDto(book)).thenReturn(bookResponseDTO);

        // ACT
        BookResponseDTO result = bookService.createBook(bookCreateDTO);

        // ASSERT
        assertThat(result)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getTitle()).isEqualTo("As Walkírias");
                    assertThat(response.getAuthors()).isEqualTo("Paulo Coelho");
                    assertThat(response.getId()).isEqualTo(1L);
                    assertThat(response.getStatus()).isEqualTo(AVAILABLE);
                });
        //VERIFY
        verify(bookConverter).toEntity(bookCreateDTO);
        verify(bookRepository).save(book);
        verify(eventPublisher).publishEvent(any(BookCreatedEvent.class));
        verify(bookConverter).toDto(book);

        System.out.println("✅ Criação de livro executada COM SUCESSO!");
    }

    @Test
    @DisplayName("Verifica ordem de execução na criação")
    void createBook_ShouldExecuteCorrectOrder() {
        // ARRANGE
        when(bookConverter.toEntity(bookCreateDTO)).thenReturn(book);
        when(bookRepository.save(any())).thenReturn(book);
        when(bookConverter.toDto(any())).thenReturn(bookResponseDTO);

        // ACT
        bookService.createBook(bookCreateDTO);

        //VERIFY
        InOrder inOrder = inOrder(bookConverter, bookRepository, eventPublisher);
        inOrder.verify(bookConverter).toEntity(bookCreateDTO);
        inOrder.verify(bookRepository).save(book);
        inOrder.verify(eventPublisher).publishEvent(any(BookCreatedEvent.class));
        inOrder.verify(bookConverter).toDto(book);

        System.out.println("✅ Verificação de ordem executada COM SUCESSO!");
    }

    @Test
    @DisplayName("Deve capturar e verificar evento publicado corretamente")
    void createBook_shouldPublishCorrectEvent() {
        // ARRANGE
        when(bookRepository.existsByIsbn10(any())).thenReturn(false);
        when(bookRepository.existsByIsbn13(any())).thenReturn(false);
        when(bookConverter.toEntity(any())).thenReturn(book);
        when(bookRepository.save(any())).thenReturn(book);
        when(bookConverter.toDto(any())).thenReturn(bookResponseDTO);

        ArgumentCaptor<BookCreatedEvent> eventCaptor = ArgumentCaptor.forClass(BookCreatedEvent.class);

        // ACT
        bookService.createBook(bookCreateDTO);

        // ASSERT
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        BookCreatedEvent capturedEvent = eventCaptor.getValue();

        assertThat(capturedEvent)
                .isNotNull()
                .satisfies(event ->
                    assertThat(event.getBookId()).isEqualTo(1L)
                );

        System.out.println("✅ Captura de evento executada COM SUCESSO!");
    }

    // =============== TESTES DE ATUALIZAÇÃO ===============

    @Test
    @DisplayName("Deve atualizar livro com sucesso seguindo fluxo completo")
    void updateBook_WhenValidData_ShouldExecuteCompleteFlow() {
        // ARRANGE
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookConverter).updateFromDto(book, bookUpdateDTO);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookConverter.toDto(book)).thenReturn(buildUpdatedBookResponse());

        // ACT
        BookResponseDTO result = bookService.updateBook(1L, bookUpdateDTO);

        // ASSERT
        assertThat(result)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getId()).isEqualTo(1L);
                    assertThat(response.getTitle()).isEqualTo("As Walkírias Uma jornada espiritual");
                });

        InOrder inOrder = inOrder(bookRepository, bookConverter);
        inOrder.verify(bookRepository).findById(1L);
        inOrder.verify(bookConverter).updateFromDto(book, bookUpdateDTO);
        inOrder.verify(bookRepository).save(book);
        inOrder.verify(bookConverter).toDto(book);

        System.out.println("✅ Atualização de livro executada COM SUCESSO!");
    }

    // =============== TESTES DE BUSCA ===============

    @Test
    @DisplayName("Deve buscar livro por ID")
    void findById_WhenValidId_ShouldReturnBook() {
        // ARRANGE
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookConverter.toDto(book)).thenReturn(bookResponseDTO);

        // ACT
        BookResponseDTO result = bookService.findById(1L);

        // ASSERT
        assertThat(result)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getId()).isEqualTo(1L);
                    assertThat(response.getTitle()).isEqualTo("As Walkírias");
                });

        InOrder inOrder = inOrder(bookRepository, bookConverter);
        inOrder.verify(bookRepository).findById(1L);
        inOrder.verify(bookConverter).toDto(book);

        System.out.println("✅ Busca por ID executada COM SUCESSO!");
    }

    @ParameterizedTest
    @ValueSource(strings = {"5454545454", "545454524"})
    @DisplayName("Deve buscar um livro por ISBN")
    void findByIsbn_WhenValidIsbn_ShouldReturnBook(String isbn) {

        // ARRANGE
        when(bookRepository.findByIsbn10OrIsbn13(isbn, isbn)).thenReturn(Optional.of(book));
        when(bookConverter.toDto(book)).thenReturn(bookResponseDTO);

        // ACT
        BookResponseDTO result = bookService.findByIsbn(isbn);

        // ASSERT
        assertThat(result)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getTitle()).isEqualTo("As Walkírias");
                    assertThat(response.getId()).isEqualTo(1L);
                });

        System.out.println("✅ Busca por ISBN executada COM SUCESSO!");
    }

    @ParameterizedTest
    @ValueSource(strings = {"walkirias", "WALKIRIAS", "Walkírias", "jornada", "espiritual"})
    @DisplayName("Deve buscar um livro por título")
    void findByTitle_WhenValidTitle_ShouldReturnBook(String title) {
        // ARRANGE
        when(bookRepository.findByTitleContainingIgnoreCase(title)).thenReturn(Optional.of(book));
        when(bookConverter.toDto(book)).thenReturn(bookResponseDTO);

        // ACT
        BookResponseDTO result = bookService.findByTitle(title);

        // ASSERT
        assertThat(result)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response.getId()).isEqualTo(1L);
                    assertThat(response.getTitle()).isEqualTo("As Walkírias");
                });

        System.out.println("✅ Busca por título executada COM SUCESSO!");
    }

    @Test
    @DisplayName("Deve retornar apenas livros disponíveis")
    void findAvailableBooks_ReturnsOnlyAvailable() {
        // ARRANGE
        List<Book> availableBooks = Arrays.asList(
                createTestBook(1L, "Livro 1", AVAILABLE),
                createTestBook(2L, "Livro 2", AVAILABLE)
        );

        List<BookResponseDTO> expectedDTOs = Arrays.asList(
                createTestBookResponseDTO(1L, "Livro 1", AVAILABLE),
                createTestBookResponseDTO(2L, "Livro 2", AVAILABLE)
        );

        when(bookRepository.findByActiveAndStatus(true, AVAILABLE)).thenReturn(availableBooks);
        when(bookConverter.converterList(availableBooks)).thenReturn(expectedDTOs);

        // ACT
        List<BookResponseDTO> result = bookService.findAvailableBooks();

        // ASSERT
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .allMatch(book -> book.getStatus() == AVAILABLE);

        System.out.println("✅ Listagem de livros disponíveis executada COM SUCESSO!");
    }

    // =============== TESTES DE CENÁRIOS DE ERRO ===============

    @Test
    @DisplayName("Deve lançar uma execeção ao criar um livro com ISBN-1O já existente")
    void createBook_DuplicateIsbn10_ShouldThrowBusinessException() {

        //ARRANGE
        when(bookRepository.existsByIsbn10("5454545454")).thenReturn(true);

        //ACT
        BusinessException ex = assertThrows(BusinessException.class,
                () -> bookService.createBook(bookCreateDTO));

        //ASSERT
        assertTrue(ex.getMessage().contains("ISBN"));
        //assertEquals("Livro com este ISBN-10 já cadastrado!",ex.getMessage());

        //VERIFY
        verify(bookRepository, never()).save(any());
        verify(eventPublisher, never()).publishEvent(any());

        System.out.println("✅ Validação de ISBN duplicado funcionando CORRETAMENTE!");

    }

    @Test
    @DisplayName("Deve lançar um execeção ao criar um livro com ISBN-13 já existente")
    void createBook_DuplicateIbn13_ShouldThrowBusinessExeception() {

        //ARRANGE
        //   when(bookRepository.existsByIsbn10("8532511147")).thenReturn(false);
        when(bookRepository.existsByIsbn13("545454524")).thenReturn(true);

        //ACT
        BusinessException ex = assertThrows(BusinessException.class,
                () -> bookService.createBook(bookCreateDTO));

        //ASSERT
        assertTrue(ex.getMessage().contains("ISBN"));

        //VERIFY
        verify(bookRepository, never()).save(any());
        verify(eventPublisher, never()).publishEvent(any());

        System.out.println("✅ Validação de ISBN duplicado funcionando CORRETAMENTE!");

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("Deve lançar uma axecao o criar um livro com titulo invalido")
    void createBook_InvalidTitle_ShouldThrowBusinessException(String invalidTitle) {

        //ARRANGE
        BookCreateDTO invalidDTO = new BookCreateDTO(
                invalidTitle,
                "Paulo Coelho",
                "Casa Publicadora de letras",
                "10-05-1989",
                "54545FGFG45454",
                "54545FG4524",
                "Livro falando sobre as Wiccas e bruxas",
                230,
                "45454545434", AVAILABLE);

        //ACT
        BusinessException ex = assertThrows(BusinessException.class,
                () -> bookService.createBook(invalidDTO));

        //ASSERT
        assertThat(ex).isNotNull()
                .extracting(BusinessException::getMessage)
                .isEqualTo("Título é obrigatório");
        System.out.println("✅ Exceção lançada corretamente para título: '" + invalidTitle + "'");

    }

    @Test
    @DisplayName("Deve lançar uma exceção de livro inválido ao atuallizar")
    void updateBook_NotFound_ShouldThrowBusinessException() {

        //ARRANGE
        when(bookRepository.findById(5656L)).thenReturn(Optional.empty());

        //ACT
        BusinessException ex = assertThrows(BusinessException.class,
                () -> bookService.updateBook(5656L, bookUpdateDTO));

        //ASSSERT
        assertThat(ex).isNotNull()
                .extracting(BusinessException::getMessage)
                .isEqualTo("Livro não encontrado!");

        //VERIFY
        verify(bookRepository, never()).save(any());
        System.out.println("✅ Exceção lançada corretamente para título inexistente.");

    }
}