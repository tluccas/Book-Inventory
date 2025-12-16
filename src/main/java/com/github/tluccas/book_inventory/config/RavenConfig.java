package com.github.tluccas.book_inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.ravendb.client.documents.DocumentStore;

@Configuration
public class RavenConfig {
    
    @Bean
    public DocumentStore documentStore(){
        DocumentStore store = new DocumentStore();
            store.setUrls(new String[]{"http://localhost:8088"}); // Server URL
            store.setDatabase("book_inventory_db"); // Banco de dados padrão
            store.initialize();
            return store;
    }
}
