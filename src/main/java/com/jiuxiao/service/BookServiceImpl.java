package com.jiuxiao.service;

import com.jiuxiao.mapper.BookMapper;
import com.jiuxiao.pojo.Books;

import java.util.List;

/**
 * 书籍相关操作业务层接口实现类
 *
 * @author: WuDaoJiuXiao
 * @Date: 2022/05/12 09:59
 * @since: 1.0.0
 */
public class BookServiceImpl implements BookService {

    private BookMapper bookMapper;

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public int addBook(Books books) {
        return bookMapper.addBook(books);
    }

    public int deleteBookById(int bookId) {
        return bookMapper.deleteBookById(bookId);
    }

    public int updateBook(Books books) {
        return bookMapper.updateBook(books);
    }

    public Books selectBookById(int bookId) {
        return bookMapper.selectBookById(bookId);
    }

    public List<Books> selectAllBook() {
        return bookMapper.selectAllBook();
    }

    public List<Books> queryBookByName(String bookName) {
        return bookMapper.queryBookByName(bookName);
    }

    public boolean isExistBookByName(String bookName) {
        return bookMapper.isExistBookByName(bookName);
    }
}