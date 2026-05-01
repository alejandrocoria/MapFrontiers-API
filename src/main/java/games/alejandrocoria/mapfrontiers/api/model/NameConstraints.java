package games.alejandrocoria.mapfrontiers.api.model;

final class NameConstraints {
    static final int MAX_NAME_LENGTH = 48;

    private NameConstraints() {
    }

    static void validateNameLength(String fieldName, String value) {
        if (value != null && value.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(fieldName + " cannot be longer than " + MAX_NAME_LENGTH + " characters");
        }
    }
}
