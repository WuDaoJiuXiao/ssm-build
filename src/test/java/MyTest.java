import com.jiuxiao.pojo.Books;
import com.jiuxiao.service.BookService;
import com.jiuxiao.service.BookServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 测试类
 *
 * @author: WuDaoJiuXiao
 * @Date: 2022/05/12 14:55
 * @since: 1.0.0
 */
public class MyTest {
    @Test
    public void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bookService = context.getBean("BookServiceImpl", BookServiceImpl.class);
        List<Books> list = bookService.selectAllBook();
        for (Books books : list) {
            System.out.println(books);
        }
    }
}