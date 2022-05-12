package com.jiuxiao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 书籍实体类
 *
 * @author: WuDaoJiuXiao
 * @Date: 2022/05/12 09:34
 * @since: 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Books {

    private int bookId;
    private String bookName;
    private int bookCount;
    private String detail;
}