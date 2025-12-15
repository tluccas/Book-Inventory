package com.github.tluccas.book_inventory.database.model;

import lombok.Data;
import java.util.List;
@Data
public class Book {

    private String id;
    private String title;
    
    private List<AuthorInfo> authors;

    private List<String> categories;
    
}
