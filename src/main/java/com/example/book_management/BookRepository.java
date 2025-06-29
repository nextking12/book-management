// Repository Interface (Data Access Layer)
package main.java.com.example.book_management;

import com.example.bookmanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

// @Repository marks this as a data access component
// JpaRepository<Book, Long> provides CRUD operations for Book entity with Long ID
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Spring Data JPA automatically implements these methods based on method names

    // Find book by ISBN (Spring generates the query automatically)
    Optional<Book> findByIsbn(String isbn);

    // Find books by author (case insensitive)
    List<Book> findByAuthorIgnoreCase(String author);

    // Find available books
    List<Book> findByAvailableTrue();

    // Find books by title containing a keyword (case insensitive)
    List<Book> findByTitleContainingIgnoreCase(String keyword);

    // Custom JPQL query - find books by author and availability
    @Query("SELECT b FROM Book b WHERE b.author = :author AND b.available = :available")
    List<Book> findBooksByAuthorAndAvailability(@Param("author") String author,
            @Param("available") Boolean available);

    // Custom native SQL query - find books within price range
    @Query(value = "SELECT * FROM books WHERE price BETWEEN :minPrice AND :maxPrice", nativeQuery = true)
    List<Book> findBooksInPriceRange(@Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);
}
