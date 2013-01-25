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

import httl.test.cases.BeetlCase;
import httl.test.cases.FreemarkerCase;
import httl.test.cases.HttlCase;
import httl.test.cases.JavaCase;
import httl.test.cases.Smarty4jCase;
import httl.test.cases.VelocityCase;
import httl.test.model.Book;
import httl.test.model.User;
import httl.test.util.DiscardOutputStream;
import httl.test.util.DiscardWriter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;

/**
 * BenchmarkTest
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class BenchmarkTest {

	@Test
	public void testBenchmark() throws Exception {
		int count = getProperty("count", 10000);
		int list = getProperty("list", 100);
		boolean stream = "true".equals(System.getProperty("stream"));
		Random random = new Random();
		Book[] books = new Book[list];
		for (int i = 0; i < list; i ++) {
			books[i] = new Book(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), new Date(), random.nextInt(100) + 10, random.nextInt(60) + 30);
		}
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("user", new User("liangfei", "admin", "Y"));
		context.put("books", books);
		BenchmarkCase[] cases = new BenchmarkCase[] { new BeetlCase(), new Smarty4jCase(), new FreemarkerCase(), new VelocityCase(), new HttlCase(), new JavaCase() };
		System.out.println("=======test environment========");
		System.out.println("os: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + " "+ System.getProperty("os.arch")
				+ ", cpu: " + Runtime.getRuntime().availableProcessors() + " cores, jvm: " + System.getProperty("java.version") + ", \nmemory: max: " + Runtime.getRuntime().maxMemory() 
				+ ", total: " + Runtime.getRuntime().totalMemory() + ", free: " + Runtime.getRuntime().freeMemory() 
				+ ", use: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		System.out.println("=======test parameters========");
		System.out.println("count: " + count + ", list: " + list + ", stream: " + stream);
		for (int i = 0; i < cases.length; i ++) {
			BenchmarkCase c = cases[i];
			String name = c.getClass().getSimpleName().replace("Case", "");
			System.out.println("========" + name.toLowerCase() + "========");
			BenchmarkCounter counter = new BenchmarkCounter();
			c.execute(counter, count, "/httl/test/templates/books", context, stream ? new DiscardOutputStream() : new DiscardWriter());
			System.out.println("init: " + counter.getInitialized() + "ms, " +
					"compile: " + counter.getCompiled() + "ms, " +
					"first: " + counter.getExecuted() + "ms, " +
					"total: " + counter.getFinished() + "ms" + ", " +
					"tps: " + (counter.getFinished() == 0 ? 0L : (1000L * count / counter.getFinished())) + "/s.");
		}
	}

	private static int getProperty(String key, int defaultValue) {
		String value = System.getProperty(key);
		if (value != null && value.length() > 0 && value.matches("\\d+")) {
			return Integer.parseInt(value);
		}
		return defaultValue;
	}

	public static void main(String[] args) throws Exception {
		new BenchmarkTest().testBenchmark();
	}

}
