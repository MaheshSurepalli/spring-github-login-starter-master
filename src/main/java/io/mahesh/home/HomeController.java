package io.mahesh.home;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.mahesh.user.BooksByUser;
import io.mahesh.user.BooksByUserRepository;

@Controller
public class HomeController {
    private final String URL_PREFIX="https://covers.openlibrary.org/b/id/";
    @Autowired
    BooksByUserRepository booksByUserRepository;
    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal,Model model){
        if(principal==null || principal.getAttribute("login")==null)
            return "index";
        String userId= principal.getAttribute("login");

        Slice<BooksByUser> bookSlice = booksByUserRepository.findAllById(userId, CassandraPageRequest.of(0,100));
        List<BooksByUser> booksByUser=bookSlice.getContent();
        booksByUser = booksByUser.stream().distinct().map(book->
        {
            String coverImgUrl="/images/noimg.jpg";
            if(book.getCoverIds()!=null && book.getCoverIds().size()>0)
            {
                coverImgUrl=URL_PREFIX+book.getCoverIds().get(0)+"-L.jpg";
            }
            book.setCoverUrl(coverImgUrl);
            return book;
        }).collect(Collectors.toList());
        model.addAttribute("booksByUser", booksByUser);
        return "home";
        
    }
    
}
