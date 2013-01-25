/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package httl.test.cases;

import httl.test.BenchmarkCase;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.Engine;

/**
 * Smarty4jCase
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class Smarty4jCase implements BenchmarkCase {

	private Engine engine = new Engine();
	
	public void execute(int times, String name, Map<String, Object> map, Object out) throws Exception {
		name += ".st";
		if (out instanceof OutputStream) {
			out = new OutputStreamWriter((OutputStream) out);
		}
		String path = new File(Thread.currentThread().getContextClassLoader().getResource(name.startsWith("/") ? name.substring(1) : name).getFile()).getAbsolutePath().replace('\\', '/');
		engine.setTemplatePath(path.substring(0, path.length() - name.length()));
		Context context = new Context();
		context.putAll(map);
		for (int i = times; i >= 0; i--) {
			engine.getTemplate(name).merge(context, (Writer) out);
		}
	}

}
