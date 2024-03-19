package store.bookshop;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import store.bookshop.model.Book;
import store.bookshop.service.BookService;

@SpringBootApplication
public class BookshopApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookshopApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book("Title 1", "Author 1", "ISBN1", BigDecimal.valueOf(19.99));
            book.setDescription("description 1");
            book.setCoverImage("cover1.jpg");
            bookService.save(book);

            System.out.println(bookService.findAll());
        };
    }
}
