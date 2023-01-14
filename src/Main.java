//package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Database;
import input.InputData;

import java.io.File;
import java.io.IOException;

public final class Main {
    private Main() {
    }

    /**
     * The entry point of the implementation, it's where the main database is created
     * @param args string containing command line arguments
     * @throws IOException exception for IO
     */
    public static void main(final String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputData inputData = objectMapper.readValue(new File(args[0]), InputData.class);

        ArrayNode output = objectMapper.createArrayNode();

        Database database = new Database(inputData);
        database.parseInput(output, objectMapper);

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File("results.out"), output);
    }
}
