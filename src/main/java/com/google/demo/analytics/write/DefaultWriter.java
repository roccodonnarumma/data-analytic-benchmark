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
package com.google.demo.analytics.write;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class DefaultWriter implements Writer {

    private Path output;

    public DefaultWriter(String fileName) {
        String baseDirectory = DefaultWriter.class.getClassLoader().getResource("").getPath();
        this.output = Paths.get(baseDirectory + fileName);
    }

    @Override
    public void write(Iterable<? extends CharSequence> line) throws IOException {
        Files.write(output, line, UTF_8, APPEND, CREATE);
    }

    @Override
    public void close() {}
}
