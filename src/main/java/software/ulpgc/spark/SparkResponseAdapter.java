package software.ulpgc.spark;

import software.ulpgc.io.Output;
import spark.Response;

public class SparkResponseAdapter implements Output {
    private final Response response;

    public SparkResponseAdapter(Response response) {
        this.response = response;
    }

    @Override
    public void setOutput(String output) {
        this.response.body(output);
    }
}
