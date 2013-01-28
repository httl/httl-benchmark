/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package httl.test;

import httl.test.engines.Beetl;
import httl.test.engines.Freemarker;
import httl.test.engines.Httl;
import httl.test.engines.Java;
import httl.test.engines.Smarty4j;
import httl.test.engines.Velocity;
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

/**
 * ShowTest
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ShowTest {

    @Test
    public void testShow() throws Exception {
        Random random = new Random();
        Book[] books = new Book[] { new Book(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), new Date(), random.nextInt(100) + 10, random.nextInt(60) + 30) };
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("user", new User("liangfei", "admin", "Y"));
        context.put("books", books);
        Benchmark[] cases = new Benchmark[] { new Beetl(), new Smarty4j(), new Freemarker(), new Velocity(), new Httl(), new Java() };
        for (int i = 0; i < cases.length; i ++) {
        	Benchmark c = cases[i % cases.length];
            String name = c.getClass().getSimpleName().replace("Case", "");
            StringWriter writer = new StringWriter();
            c.execute(1, "books", context, new DiscardWriter());
            System.out.println("========" + name.toLowerCase() + "========");
            System.out.println(writer.getBuffer().toString());
            System.out.println("================");
        }
    }
    
    public static void main(String[] args) throws Exception {
        new ShowTest().testShow();
    }
    
}
