package com.example.ehcache;

import com.example.ehcache.entities.Book;
import com.example.ehcache.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    BookRepository bookRepository;

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        addToDb();
        printAll();
    }

    public void addToDb() {
        for (int i = 0; i < 5; i++) {
            Book book = new Book();
            book.setName("Book:" + i);
            bookRepository.save(book);
        }
    }

    public void printAll() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        books.stream().forEach(System.out::println);
    }
}
