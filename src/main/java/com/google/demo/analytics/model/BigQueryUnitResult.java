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

package com.google.demo.analytics.model;

public class BigQueryUnitResult extends QueryUnitResult {

    private String jobId;

    public BigQueryUnitResult(
            QueryUnit queryUnit,
            Status status,
            String duration,
            String errorMessage,
            String jobId,
            String start,
            String end) {
        super(queryUnit, status, duration, errorMessage, start, end);
        this.jobId = jobId;
    }

    public static BigQueryUnitResult createSuccess(
            QueryUnit queryUnit,
            String jobId,
            String duration,
            String start,
            String end) {
        return new BigQueryUnitResult(queryUnit, Status.SUCCESS, duration, null, jobId, start, end);
    }

    public static BigQueryUnitResult createFail(
            QueryUnit queryUnit,
            String jobId,
            String errorMessage,
            String start) {
        return new BigQueryUnitResult(queryUnit, Status.FAIL, null, errorMessage, jobId, start, null);
    }

    public String getJobId() {
        return jobId;
    }
}
