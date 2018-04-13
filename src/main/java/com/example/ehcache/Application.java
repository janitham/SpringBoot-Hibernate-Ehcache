package com.example.ehcache;

import com.example.ehcache.entities.Book;
import com.example.ehcache.repositories.BookRepository;
//import org.hibernate.cache.redis.SingletonRedisRegionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;

//import java.util.List;
import java.util.Optional;

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
        System.out.println("adding completed");
        printAll();

        //SingletonRedisRegionFactory k;
    }

    public void addToDb() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Book book = new Book();
            book.setName("Book:" + i);
            bookRepository.save(book);
        }
    }

    public void printAll() throws InterruptedException {
        for (int i = 1; i < 6; i++) {
            Optional<Book> book = bookRepository.findById(i);
            System.out.println(book.get());
            Thread.sleep(2000);
           // SingletonRedisRegionFactory k;
        }
    }
}
