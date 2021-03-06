/*
    Copyright 2017, Google, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.google.demo.analytics.benchmark;

import com.google.demo.analytics.executor.JDBCExecutor;
import com.google.demo.analytics.model.QueryPackage;
import com.google.demo.analytics.model.QueryUnit;
import com.google.demo.analytics.model.QueryUnitResult;
import com.google.demo.analytics.write.Writer;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

public class HiveBenchmark extends JDBCBenchmark {

    public final static String ENGINE_NAME = "hive";
    private static final String driverName = "org.apache.hive.jdbc.HiveDriver";

    public HiveBenchmark(List<String> keys, List<QueryPackage> queryPackages) {
        super(keys, queryPackages);
    }

    @Override
    protected Callable<List<QueryUnitResult>> getExecutor(QueryUnit queryUnit, Properties props) {
        String user = props.getProperty("hive.user");
        String password = props.getProperty("hive.password") == null ? "" : props.getProperty("hive.password");
        String connectionUrl = props.getProperty("hive.connection.url");
        return new JDBCExecutor(queryUnit, user, password, connectionUrl, driverName);
    }

    @Override
    public String getEngineName() {
        return ENGINE_NAME;
    }

    @Override
    protected QueryUnit getCheckConnectionQuery(Properties props) {
        return new QueryUnit(
                "check",
                getEngineName(),
                "check-connection",
                props.getProperty("hive.connection.check"),
                1);
    }
}
