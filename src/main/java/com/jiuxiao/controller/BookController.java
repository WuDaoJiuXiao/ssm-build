package com.jiuxiao.controller;

import com.jiuxiao.pojo.Books;
import com.jiuxiao.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Book控制器
 *
 * @author: WuDaoJiuXiao
 * @Date: 2022/05/12 13:51
 * @since: 1.0.0
 */
@Controller
@RequestMapping("/book")
public class BookController {

    @Resource(name = "BookServiceImpl")
    private BookService bookService;

    @RequestMapping("/allBook")
    public String getBookList(Model model){
        List<Books> books = bookService.selectAllBook();
        model.addAttribute("bookList", books);
        return "allBook";
    }
}