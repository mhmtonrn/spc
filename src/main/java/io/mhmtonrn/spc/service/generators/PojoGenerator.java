package io.mhmtonrn.spc.service.generators;

public abstract class PojoGenerator {

    protected static String toCamelCase(String s) {
        return toCamelCase(s, true);
    }

    protected static String toCamelCase(String s, boolean capitalizeFirstLetter) {
        StringBuilder result = new StringBuilder();
        boolean nextUpperCase = capitalizeFirstLetter;
        for (char c : s.toCharArray()) {
            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    result.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }

    protected static String mapDataType(String dataType) {
        return switch (dataType.toLowerCase()) {
            case "varchar", "char", "text" -> "String";
            case "int", "integer" -> "Integer";
            case "bigint" -> "Long";
            case "decimal", "numeric" -> "java.math.BigDecimal";
            case "date" -> "java.time.LocalDate";
            case "timestamp without time zone" -> "java.time.LocalDateTime";
            // Add other mappings as needed
            default -> "String";
        };
    }

}
