package com.jiuxiao.service;

import com.jiuxiao.pojo.Books;

import java.util.List;

/**
 * 书籍相关操作业务层接口
 *
 * @author: WuDaoJiuXiao
 * @Date: 2022/05/12 09:58
 * @since: 1.0.0
 */
public interface BookService {

    /**
     * 增加一本书
     *
     * @param books
     * @return
     */
    int addBook(Books books);

    /**
     * 根据 ID 删除一本书
     *
     * @param bookId
     * @return
     */
    int deleteBookById(int bookId);

    /**
     * 更新一本书
     *
     * @param books
     * @return
     */
    int updateBook(Books books);

    /**
     * 根据 ID 查询一本书
     *
     * @param bookId
     * @return
     */
    Books selectBookById(int bookId);

    /**
     * 查寻全部书籍
     *
     * @return
     */
    List<Books> selectAllBook();

    /**
     * 根据书名查找书籍
     *
     * @param bookName
     * @return
     */
    List<Books> queryBookByName(String bookName);

    /**
     * 根据书名判断数据库中是否有该书籍
     *
     * @param bookName
     * @return
     */
    boolean isExistBookByName(String bookName);
}
