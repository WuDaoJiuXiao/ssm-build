package com.jiuxiao.mapper;

import com.jiuxiao.pojo.Books;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 书籍操作接口
 *
 * @author: WuDaoJiuXiao
 * @Date: 2022/05/12 09:37
 * @since: 1.0.0
 */
public interface BookMapper {

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
    int deleteBookById(@Param("bookId") int bookId);

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
    Books selectBookById(@Param("bookId") int bookId);

    /**
     * 查寻全部书籍
     *
     * @return
     */
    List<Books> selectAllBook();
}
