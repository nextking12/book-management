package com.example.book_management;

import com.example.book_management.Book;
import com.example.book_management.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;



// @Service marks this class as a service component
// Service layer contains business logic and acts as intermediary between controller and repository
@Service
public class BookService {
    
    // @Autowired tells Spring to inject the BookRepository dependency
    // This is Dependency Injection - a key principle of Spring
    @Autowired
    private BookRepository bookRepository;
    // Alternative constructor injection (recommended approach)
    // public BookService(BookRepository bookRepository) {
    //     this.bookRepository = bookRepository;
    // }
    // Business method to save a book
    public Book saveBook(Book book) {
        // Business logic can go here (validation, etc.)
        return bookRepository.save(book);
    }
    
    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    // Get book by ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
    
    // Get book by ISBN
    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
    
    // Search books by author
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorIgnoreCase(author);
    }
    
    // Get available books only
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }
    
    // Search books by title keyword
    public List<Book> searchBooksByTitle(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    // Update book availability
    public Book updateBookAvailability(Long id, Boolean available) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setAvailable(available);
            return bookRepository.save(book);
        }
        return null;
    }
    
    // Delete book by ID
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Business method with custom logic
    public List<Book> getAffordableBooks(Double maxPrice) {
        return bookRepository.findBooksInPriceRange(0.0, maxPrice);
    }


}
