package com.example.book_management;

import com.example.book_management.Book;
import com.example.book_management.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

// @RestController combines @Controller and @ResponseBody
// This means all methods return data directly (not view names)
@RestController
// @RequestMapping sets the base URL path for all endpoints in this controller
@RequestMapping("/api/books")
public class BookController {
    
    // Inject the service layer
    @Autowired
    private BookService bookService;
    
    // GET /api/books - Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        // ResponseEntity allows us to control HTTP status codes and headers
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    // GET /api/books/{id} - Get book by ID
    // @PathVariable extracts {id} from the URL path
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // POST /api/books - Create a new book
    // @RequestBody converts JSON request body to Book object
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
    
    // PUT /api/books/{id} - Update an existing book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Optional<Book> existingBook = bookService.getBookById(id);
        if (existingBook.isPresent()) {
            Book bookToUpdate = existingBook.get();
            // Update fields
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setAuthor(book.getAuthor());
            bookToUpdate.setIsbn(book.getIsbn());
            bookToUpdate.setPrice(book.getPrice());
            bookToUpdate.setAvailable(book.getAvailable());
            
            Book updatedBook = bookService.saveBook(bookToUpdate);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // DELETE /api/books/{id} - Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // GET /api/books/search?author=authorName - Search by author
    // @RequestParam extracts query parameters from URL
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String author,
                                                  @RequestParam(required = false) String title) {
        List<Book> books;
        
        if (author != null && !author.isEmpty()) {
            books = bookService.getBooksByAuthor(author);
        } else if (title != null && !title.isEmpty()) {
            books = bookService.searchBooksByTitle(title);
        } else {
            books = bookService.getAllBooks();
        }
        
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    // GET /api/books/available - Get only available books
    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        List<Book> availableBooks = bookService.getAvailableBooks();
        return new ResponseEntity<>(availableBooks, HttpStatus.OK);
    }
    
    // PATCH /api/books/{id}/availability - Update book availability
    @PatchMapping("/{id}/availability")
    public ResponseEntity<Book> updateBookAvailability(@PathVariable Long id, 
                                                       @RequestParam Boolean available) {
        Book updatedBook = bookService.updateBookAvailability(id, available);
        if (updatedBook != null) {
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}