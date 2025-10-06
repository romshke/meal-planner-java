package mealplanner.model;

public enum Day {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private final String name;

    Day(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Day fromString(String text) {
        for (Day day : Day.values()) {
            if (day.name.equalsIgnoreCase(text)) {
                return day;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
