package main.java.com.example.book_management;

import jakarta.persistence.*;
import java.time.LocalDateTime;



// @MappedSuperclass means this class won't have its own table
// but its fields will be inherited by subclasses
@MappedSuperclass
public abstract class BaseEntity {

     // @Id marks this field as the primary key
    // @GeneratedValue tells JPA to auto-generate the ID value
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column annotation is optional for simple mappings
    // but useful for customizing column properties
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

     // Default constructor (required by JPA)
    public BaseEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

     // Getter methods (Encapsulation - controlled access to private fields)
    public Long getId() {
        return id;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    // Setter for updatedAt (typically called before saving)
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

     // @PreUpdate is a JPA lifecycle callback
    // This method runs automatically before entity updates
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
