package io.mahesh.userbooks;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import io.mahesh.book.Book;
import io.mahesh.book.BookRepository;
import io.mahesh.user.BooksByUser;
import io.mahesh.user.BooksByUserRepository;

@Controller
public class UserBooksController {
    @Autowired
    UserBooksRepository userBooksRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BooksByUserRepository booksByUserRepository;
    @PostMapping("/addUserBook")
    public ModelAndView addBookForUser(@RequestBody MultiValueMap<String, String> formData, @AuthenticationPrincipal OAuth2User principal){
        if(principal==null || principal.getAttribute("login")==null)
            return null;
        Optional<Book> optionalBook = bookRepository.findById(formData.getFirst("bookId"));
        Book book = optionalBook.get();

        UserBooksPrimaryKey key=UserBooksPrimaryKey.builder()
                                .userId(principal.getAttribute("login"))
                                .bookId(formData.getFirst("bookId"))
                                .build();
        UserBooks userBooks = UserBooks.builder()
                            .key(key)
                            .startedDate(LocalDate.parse(formData.getFirst("startDate")))
                            .completedDate(LocalDate.parse(formData.getFirst("completedDate")))
                            .rating(Integer.parseInt(formData.getFirst("rating")))
                            .readingStatus(formData.getFirst("readingStatus"))
                            .build();
        userBooksRepository.save(userBooks);
        
        BooksByUser booksByUser = new BooksByUser();
        booksByUser.setId(principal.getAttribute("login"));
        booksByUser.setBookId(formData.getFirst("bookId"));
        booksByUser.setBookName(book.getName());
        booksByUser.setCoverIds(book.getCoverIds());
        booksByUser.setAuthorNames(book.getAuthorNames());
        booksByUser.setReadingStatus(formData.getFirst("readingStatus"));
        booksByUser.setRating(Integer.parseInt(formData.getFirst("rating")));
        booksByUserRepository.save(booksByUser);
        return new ModelAndView("redirect:/books/"+formData.getFirst("bookId"));
    }
    
}
