package com.jiuxiao.controller;

import com.jiuxiao.pojo.Books;
import com.jiuxiao.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Resource(name = "bookServiceImpl")
    private BookService bookService;

    /**
     * 查询全部书籍，并返回一个展示页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/allBook")
    public String getBookList(Model model) {
        List<Books> books = bookService.selectAllBook();
        model.addAttribute("bookList", books);
        return "allBook";
    }

    /**
     * 跳转到增加书籍的页面
     *
     * @return
     */
    @RequestMapping("/toAddBook")
    public String toAddPage() {
        return "addBook";
    }

    /**
     * 增加一本书
     *
     * @param books
     * @return
     */
    @RequestMapping("/addBook")
    public String addBook(Books books) {
        bookService.addBook(books);
        return "redirect:/book/allBook";
    }

    /**
     * 跳转到修改书籍页面
     *
     * @return
     */
    @RequestMapping("/toUpdateBook")
    public String toUpdatePage(int bookId, Model model) {
        Books bookById = bookService.selectBookById(bookId);
        model.addAttribute("reBook", bookById);
        return "updateBook";
    }

    /**
     * 修改书籍信息
     *
     * @param books
     * @return
     */
    @RequestMapping("/updateBook")
    public String updateBook(Books books) {
        bookService.updateBook(books);
        return "redirect:/book/allBook";
    }

    /**
     * 删除一本书
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteBook/{bookId}")
    public String deleteBook(@PathVariable("bookId") int id) {
        bookService.deleteBookById(id);
        return "redirect:/book/allBook";
    }

    /**
     * 根据名字查询书籍
     *
     * @param queryBookName
     * @param model
     * @return
     */
    @RequestMapping("/queryBook")
    public String queryBook(String queryBookName, Model model) {
        System.out.println("queryBookName ==> " + queryBookName);
        if (queryBookName == null || queryBookName.equals("") || !bookService.isExistBookByName(queryBookName)){
            bookService.selectAllBook();
            model.addAttribute("error", "未查询到相关书籍，请确认名称!");
        }
        List<Books> booksList = bookService.queryBookByName(queryBookName);
        model.addAttribute("bookList", booksList);
        return "allBook";
    }
}