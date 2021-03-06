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

import com.google.demo.analytics.executor.BigQueryExecutor;
import com.google.demo.analytics.model.BigQueryUnitResult;
import com.google.demo.analytics.model.QueryPackage;
import com.google.demo.analytics.model.QueryUnit;
import com.google.demo.analytics.write.Writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class BigQueryBenchmark extends Benchmark<BigQueryUnitResult> {

    public final static String ENGINE_NAME = "bq";

    public BigQueryBenchmark(List<String> keys, List<QueryPackage> queryPackages) {
        super(keys, queryPackages);
    }

    @Override
    protected Callable<List<BigQueryUnitResult>> getExecutor(QueryUnit queryUnit, Properties props) {
        String useStopwatch = props.getProperty("bq.stopwatch");
        String useQueryCache = props.getProperty("bq.query.cache");
        String projectId = props.getProperty("bq.projectId");
        return new BigQueryExecutor(
                queryUnit,
                Boolean.parseBoolean(useStopwatch),
                Boolean.parseBoolean(useQueryCache),
                projectId);
    }

    @Override
    public String getEngineName() {
        return ENGINE_NAME;
    }

    @Override
    protected QueryUnit getCheckConnectionQuery(Properties props) {
        return new QueryUnit(
                "check",
                ENGINE_NAME,
                "check-connection",
                props.getProperty("bq.connection.check"),
                1);
    }

    @Override
    protected void writeToOutput(List<BigQueryUnitResult> results, Writer writer)
            throws IOException {
        List<String> baseHeaders = new ArrayList<>(Arrays.asList(
                "id",
                "platform",
                "description",
                "query",
                "job_id",
                "status",
                "start_time",
                "end_time",
                "duration_ms",
                "error_messages"
        ));

        baseHeaders.addAll(getKeys());
        String headers = String.join(DELIMITER, baseHeaders);

        writer.write(Arrays.asList(headers));

        for(BigQueryUnitResult result : results) {
            List<String> baseValues = new ArrayList<>(Arrays.asList(
                    result.getQueryUnit().getId(),
                    getEngineName(),
                    result.getQueryUnit().getDescription(),
                    result.getQueryUnit().getQuery(),
                    result.getJobId(),
                    result.getStatus().toString(),
                    result.getStart(),
                    result.getEnd(),
                    result.getDuration(),
                    result.getErrorMessage() == null ? "" : result.getErrorMessage()
            ));

            baseValues.addAll(result.getQueryUnit().getValues());
            String values = String.join(DELIMITER, baseValues);

            writer.write(Arrays.asList(values));
        }

        writer.close();
    }
}
