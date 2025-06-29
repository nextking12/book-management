// Book Entity Class (Demonstrates Inheritance from BaseEntity)
package main.java.com.example.book_management;

import jakarta.persistence.*;



// @Entity marks this class as a JPA entity (will create a table)
// @Table specifies the table name (optional if class name matches table name)
@Entity
@Table(name = "books")
public class Book extends BaseEntity {  // Inheritance - Book "is-a" BaseEntity
    
 // Private fields demonstrate Encapsulation
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    @Column(name = "author", nullable = false, length = 100)
    private String author;
    
    @Column(name = "isbn", unique = true, length = 20)
    private String isbn;
    
    @Column(name = "price")
    private Double price;
    
    @Column(name = "available")
    private Boolean available;

    // Default constructor (required by JPA)
    public Book() {
        super(); // Call parent constructor
        this.available = true; // Default value
    }

        // Parameterized constructor for easy object creation
    public Book(String title, String author, String isbn, Double price) {
        super(); // Call parent constructor
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.available = true;
    }

    // Getter methods (Encapsulation - controlled read access)
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public Boolean getAvailable() {
        return available;
    }
    
    // Setter methods (Encapsulation - controlled write access)
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public void setAvailable(Boolean available) {
        this.available = available;
    }

     // Business method (Encapsulation - behavior with data)
    public void markAsUnavailable() {
        this.available = false;
    }
    
    public void markAsAvailable() {
        this.available = true;
    }

     // toString method for easy debugging and logging
    @Override
    public String toString() {
        return "Book{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", price=" + price +
                ", available=" + available +
                '}';
    }

}
