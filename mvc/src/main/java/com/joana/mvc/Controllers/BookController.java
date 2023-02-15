package com.joana.mvc.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joana.mvc.models.Book;
import com.joana.mvc.services.BookService;

@Controller
public class BookController {
    @Autowired
    BookService bookService;

    @RequestMapping("/books/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findBook(id);
        model.addAttribute("book", book);
        return "show.jsp";
    }

    @RequestMapping("/books")
    public String allbooks(Model model) {
        List<Book> books = bookService.allBooks();
        model.addAttribute("books", books);
        return "books.jsp";
    }

    @GetMapping("/books/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "new.jsp";
    }

    // Note: We'll be altering this a bit after we introduce data binding.
    @PostMapping("/books")
    public String create(@Valid @ModelAttribute("book") Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "/books/new.jsp";
        } else {
            bookService.createBook(book);
            return "redirect:/books";
        }
    }

}