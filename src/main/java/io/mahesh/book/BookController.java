package io.mahesh.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.mahesh.userbooks.UserBooks;
import io.mahesh.userbooks.UserBooksController;
import io.mahesh.userbooks.UserBooksPrimaryKey;
import io.mahesh.userbooks.UserBooksRepository;

@Controller
public class BookController {
    private final String URL_PREFIX="https://covers.openlibrary.org/b/id/";
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserBooksRepository userBooksRepository;
    @GetMapping(value = "/books/{bookId}")
    public String getBook(@PathVariable String bookId,Model model,@AuthenticationPrincipal OAuth2User principal){
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            String coverImgUrl="/images/noimg.jpg";
            if(book.getCoverIds()!=null && book.getCoverIds().size()>0)
            {
                coverImgUrl=URL_PREFIX+book.getCoverIds().get(0)+"-L.jpg";
            }
            model.addAttribute("coverImage", coverImgUrl);
            model.addAttribute("book", book);
            if(principal!=null && principal.getAttribute("login")!=null){
                model.addAttribute("loginId",principal.getAttribute("login"));
                UserBooksPrimaryKey key= UserBooksPrimaryKey.
                                        builder()
                                        .userId(principal.getAttribute("login"))
                                        .bookId(bookId)
                                        .build();
                Optional<UserBooks> userBooks = userBooksRepository.findById(key);
                if(userBooks.isPresent())
                model.addAttribute("userBooks", userBooks.get());
                else
                model.addAttribute("userBooks", new UserBooks());
        }
            return "book";
    }
    return "book-not-found";
    
}
}
