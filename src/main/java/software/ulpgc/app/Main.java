package software.ulpgc.app;

import software.ulpgc.control.CalculateTaskDaysWithAvailabilityCommand;
import software.ulpgc.control.CalculateTaskDaysWithRestrictionCommand;
import software.ulpgc.control.Command;
import software.ulpgc.spark.SparkRequestAdapter;
import software.ulpgc.spark.SparkResponseAdapter;
import spark.Spark;

public class Main {
    public static final String TASKDAYS_WITH_RESTRICTION = "/twr";
    public static final String TASKDAYS_WITH_AVAILABILITY = "/twa";

    public static void main(String[] args) {
        CommandFactory factory = new CommandFactory();
        factory.register(TASKDAYS_WITH_RESTRICTION, CalculateTaskDaysWithRestrictionCommand::new)
                .register(TASKDAYS_WITH_AVAILABILITY, CalculateTaskDaysWithAvailabilityCommand::new);
        serverConfiguration(8080, factory);
    }

    private static void serverConfiguration(int port, CommandFactory factory) {
        Spark.port(port);
        setEndpoint(TASKDAYS_WITH_RESTRICTION, factory);
        setEndpoint(TASKDAYS_WITH_AVAILABILITY, factory);
    }

    private static void setEndpoint(String pathParameter, CommandFactory factory) {
        Spark.get(pathParameter, ((request, response) -> {
            Command command = factory.get(request.pathInfo(), new SparkRequestAdapter(request), new SparkResponseAdapter(response));
            command.execute();
            response.status(200);
            response.type("application/json");
            return response.body();
        }));
    }
}
