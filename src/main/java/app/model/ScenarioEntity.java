package app.model;

public record ScenarioEntity(
        int id,
        String passengerName,
        String flightNo,
        double weightKg,
        String status
) {}