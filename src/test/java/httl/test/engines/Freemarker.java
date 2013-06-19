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
package httl.test.engines;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import httl.test.Benchmark;
import httl.util.Version;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

/**
 * FreemarkerCase
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class Freemarker implements Benchmark {

	public String getVersion() {
		return Version.getVersion(Configuration.class, "0.0.0");
	}

	private Configuration configuration = new Configuration();

	public Freemarker() {
		configuration.setTemplateLoader(new ClassTemplateLoader(Freemarker.class, "/"));
	}
	
	public void execute(int times, String name, Map<String, Object> context, Object out) throws Exception {
		name += ".ftl";
		if (out instanceof OutputStream) {
			out = new OutputStreamWriter((OutputStream) out);
		}
		while (-- times >= 0) {
			configuration.getTemplate(name).process(context, (Writer) out);
		}
	}

}
