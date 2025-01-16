package software.ulpgc.spark;

import software.ulpgc.control.Input;
import spark.Request;

public class SparkRequestAdapter implements Input {
    private final Request request;

    public SparkRequestAdapter(Request request) {
        this.request = request;
    }

    @Override
    public String getInput(String parameter) {
        return this.request.queryParams(parameter);
    }
}