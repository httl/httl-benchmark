package httl.test;

import httl.test.cases.BeetlCase;
import httl.test.cases.FreemarkerCase;
import httl.test.cases.HttlCase;
import httl.test.cases.JavaCase;
import httl.test.cases.Smarty4jCase;
import httl.test.cases.VelocityCase;
import httl.test.model.Book;
import httl.test.model.User;
import httl.test.util.DiscardWriter;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;

public class ShowTest {

    @Test
    public void testShow() throws Exception {
        Random random = new Random();
        Book[] books = new Book[] { new Book(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), new Date(), random.nextInt(100) + 10, random.nextInt(60) + 30) };
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("user", new User("liangfei", "admin", "Y"));
        context.put("books", books);
        BenchmarkCase[] cases = new BenchmarkCase[] { new BeetlCase(), new Smarty4jCase(), new FreemarkerCase(), new VelocityCase(), new HttlCase(), new JavaCase() };
        for (int i = 0; i < cases.length; i ++) {
        	BenchmarkCase c = cases[i % cases.length];
            String name = c.getClass().getSimpleName().replace("Case", "");
        	BenchmarkCounter counter = new BenchmarkCounter();
            StringWriter writer = new StringWriter();
            c.execute(counter, 1, "books", context, new DiscardWriter());
            System.out.println("========" + name.toLowerCase() + "========");
            System.out.println(writer.getBuffer().toString());
            System.out.println("================");
        }
    }
    
    public static void main(String[] args) throws Exception {
        new ShowTest().testShow();
    }
    
}
