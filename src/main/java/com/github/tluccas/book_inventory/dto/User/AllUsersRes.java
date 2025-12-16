package com.github.tluccas.book_inventory.dto.User;
import com.github.tluccas.book_inventory.database.model.User;
import java.util.List;

public record AllUsersRes( int totalUsers, List<User> users
) {
    
}
