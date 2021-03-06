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

package com.google.demo.analytics.util;

public class StopWatch {

    private long start;
    private long end;

    public StopWatch() {
        start = System.currentTimeMillis();
    }


    /**
     * Returns the elapsed CPU time (in ms) since the stopwatch was created.
     *
     * @return elapsed CPU time (in ms) since the stopwatch was created
     */
    public long elapsedTime() {
        end = System.currentTimeMillis();
        return end - start;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }
}
