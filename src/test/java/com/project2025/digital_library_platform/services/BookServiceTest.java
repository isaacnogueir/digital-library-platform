package com.project2025.digital_library_platform.services;

import com.project2025.digital_library_platform.converters.BookConverter;
import com.project2025.digital_library_platform.domain.book.Book;
import com.project2025.digital_library_platform.domain.book.dtos.BookCreateDTO;
import com.project2025.digital_library_platform.domain.book.dtos.BookResponseDTO;
import com.project2025.digital_library_platform.domain.book.dtos.BookUpdateDTO;
import com.project2025.digital_library_platform.events.BookCreatedEvent;
import com.project2025.digital_library_platform.repositories.BookRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static com.project2025.digital_library_platform.domain.book.Status.AVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        /*
        Prepara ambiente: objetos e mocks antes de cada teste
         */

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
        book.setAuthors("Paulo Coelho"); // ← Corrigido nome
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
        bookResponseDTO.setAuthors("Paulo Coelho"); // ← Corrigido nome
        bookResponseDTO.setPublisher("Casa publicadora de Letras");
        bookResponseDTO.setPublishedDate("10-05-1989");
        bookResponseDTO.setIsbn10("5454545454");
        bookResponseDTO.setIsbn13("545454524");
        bookResponseDTO.setDescription("Livro falando sobre as Wiccas");
        bookResponseDTO.setPageCount(230);
        bookResponseDTO.setStatus(AVAILABLE);

        //Update mock
        bookUpdateDTO = new BookUpdateDTO("As Walkírias: Uma jornada espiritual",
                "Paulo Coelho",
                "Casa publicadora de Letra",
                "10-05-1989",
                "5454545454",
                "545454524",
                "Livro falando sobre as Wiccas",
                300);


        System.out.println("Dados preparados: " + bookCreateDTO.title());
    }


    @Test
    @DisplayName("Deve criar um livro com fluxo completo")
    void createBook_WhenValidData_ShouldExecuteCompleteFlow() {

        System.out.println("\n=== TESTE: Criar livro com sucesso ===");

        // ========== ARRANGE (PREPARAÇÃO) ==========
        when(bookConverter.toEntity(bookCreateDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookConverter.toDto(book)).thenReturn(bookResponseDTO);
        System.out.println("Mocks configurados");

        /*
         VERIFICA O RETORNO DO METODO USANDO UMA VARIAVEL 'RESULT'
        */

        // ========== ACT (EXECUÇÃO) TESTE DE FATO DO METODO ==========
        BookResponseDTO result = bookService.createBook(bookCreateDTO);
        System.out.println("Método executado, resultado: " + result.getTitle());

        // ========== ASSERT (VERIFICAÇÃO DE RETORNO) verifica se retorna o esperado ==========
        System.out.println("\n ASSERT: Verificando resultado...");
        assertNotNull(result);
        assertEquals("As Walkírias", result.getTitle());
        assertEquals("Paulo Coelho", result.getAuthors());
        assertEquals(1L, result.getId());
        assertEquals(AVAILABLE, result.getStatus());
        System.out.println("✓ Todas as assertions passaram!");

        // ========== VERIFY (VERIFICAÇÃO DE CHAMADA) confirma se os métodos dos mocks fora utilizados ==========
        verify(bookConverter).toEntity(bookCreateDTO);
        verify(bookRepository).save(book);
        verify(eventPublisher).publishEvent(any(BookCreatedEvent.class));
        verify(bookConverter).toDto(book);

        System.out.println("\n TESTE PASSOU COM SUCESSO!");
    }

    @Test
    @DisplayName("Verifica ordem de execuação")
    void createBook_ShouldExecuteIncorrectOrder() {
        System.out.println("\n === TESTE: Verificando ordem dexeceução ===");

        //Arrange
        when(bookConverter.toEntity(any())).thenReturn(book);
        when(bookRepository.save(any())).thenReturn(book);
        when(bookConverter.toDto(any())).thenReturn(bookResponseDTO);

        //Act
        bookService.createBook(bookCreateDTO);

        //Verify Order
        System.out.println("Verificando ordem das chamadas....");
        InOrder inOrder = inOrder(bookConverter, bookRepository, eventPublisher);

        inOrder.verify(bookConverter).toEntity(bookCreateDTO);
        System.out.println("1 VERICICFCAÇÃO: bookconverter.toEntity()");

        inOrder.verify(bookRepository).save(book);
        System.out.println("2 VERIFICACAO: bookreposiory.save()");

        inOrder.verify(eventPublisher).publishEvent(any(BookCreatedEvent.class));
        System.out.println("3 VERIFICACAO: eventpublisher.pubilishedevet()");

        inOrder.verify(bookConverter).toDto(book);
        System.out.println("4 VERIFIACAO: bookconveter.toDto()");

        System.out.println("Verificação correta!");

    }

    //CRIAÇÃO DE UM UPDATE MOCKADO
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

    @Test
    @DisplayName("Deve atualizar livro com sucesso seguindo fluxo completo")
    void createBook_updateBook_Sucess() {

        //ARRANGE
        System.out.println("\n=== TESTE: Atualizar livro com sucesso ===");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookConverter).updateFromDto(book, bookUpdateDTO);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookConverter.toDto(book)).thenReturn(buildUpdatedBookResponse());

        //ACT
        BookResponseDTO result = bookService.updateBook(1L, bookUpdateDTO);
        System.out.println("Livro atualizado: " + result.getTitle());

        //ASSERT
        System.out.println("ASSERT: Verificando resultado da atualização...");
        assertNotNull(result);
        assertEquals("As Walkírias Uma jornada espiritual", result.getTitle());

        //VERIFY
        InOrder inOrder = inOrder(bookRepository, bookConverter);
        inOrder.verify(bookRepository).findById(1L);
        inOrder.verify(bookConverter).updateFromDto(book, bookUpdateDTO);
        inOrder.verify(bookRepository).save(book);
        inOrder.verify(bookConverter).toDto(book);
        System.out.println("Todas as operações executadas na ordem correta");

    }

    @Test
    @DisplayName("Deve buscar book por ID")
    void createBook_findById() {

        //ARRANGE
        System.out.println("ARRANGE: Configurando busca por ID...");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookConverter.toDto(book)).thenReturn(bookResponseDTO);

        //ACT
        System.out.println("ACT: Executando busca...");
        BookResponseDTO result = bookService.findById(1L);

        //ASSERT
        System.out.println("ASSERT: Verificando resultado...");
        assertNotNull(result);
        assertEquals("As Walkírias", result.getTitle());
        assertEquals(1L, result.getId());

        //VERIFY
        InOrder inOrder = inOrder(bookRepository, bookConverter);
        inOrder.verify(bookRepository).findById(1L);
        inOrder.verify(bookConverter).toDto(book);
        System.out.println("🔍ordem correta");

        System.out.println("Busca por ID executada COM SUCESSO!");
    }

    @ParameterizedTest
    @ValueSource(strings = {"5454545454", "545454524"})
    @DisplayName("Deve buscar um livro por ISBN")
    void createBook_findByIsbn(String isbn) {

        //ARRANGE
        System.out.println("ARRANGE: Configurando busca por ISBN...");
        when(bookRepository.findByIsbn10OrIsbn13(isbn,isbn)).thenReturn(Optional.of(book));
        when(bookConverter.toDto(book)).thenReturn(bookResponseDTO);

        //ACT
        System.out.println("ACT: Executando busca por ISBN: " + isbn);
        BookResponseDTO result = bookService.findByIsbn(isbn);

        //ASSERT
        assertNotNull(result);
        assertEquals("As Walkírias", result.getTitle());
        System.out.println("Livro encontrado por ISBN " + isbn + ": " + result.getTitle());

        System.out.println("Busca por ISBN executada COM SUCESSO!");

    }



    /*
    @Test
    @DisplayName("Lança exeção ao criar livro com ISBN-10 existente")
    void createBook_DuplicateIsbn10_shouldThrowBusinessExepcetion(){
        System.out.print(" === TESTE: Isbn duplicado");

        //ARRANGE
        when(bookRepository.existsByIsbn10("5454545454")).thenReturn(true);

        //ACT
        BusinessException ex = assertThrows(BusinessException.class,
                ()-> bookService.createBook(bookCreateDTO));

        //ASSERT
        assertEquals("Livro com este ISBN-10 já cadastrado!", ex.getMessage());

    //VERIFY

        verify(bookRepository, never()).save(any());

    }
*/
}
