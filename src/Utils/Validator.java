package Utils;

import java.util.Map;
import java.util.HashMap;
import Utils.Model.DataType;
import java.util.function.Function;

/**
 * Validator
 */
public final class Validator extends Parser {

    private static Validator instance;

    private Map<DataType, Function<String, String>> ERROR_MAP = new HashMap<DataType, Function<String, String>>() {
        {
            put(DataType.FLOAT, arg -> !isFloat(arg) ? "NUMBER REQUIRED !! 🔢" : null);
            put(DataType.INTEGER, arg -> !isInteger(arg) ? "NUMBER REQUIRED !! 🔢" : null);
            put(DataType.DATE, arg -> !isDate(arg) ? "FORMAT DATE REQUIRED !! 📅" : null);
            put(DataType.TIME, arg -> !isTime(arg) ? "FORMAT TIMESTAMP REQUIRED !! ⏱️" : null);
            put(DataType.STRING, arg -> !isString(arg) ? "NOT EMPTY STRING REQUIRED !! 🔡" : null);
        }
    };

    private Validator() {
        super();
    }

    public static Validator getInstance() {
        if (instance == null) {
            instance = new Validator();
        }

        return instance;
    }

    public String validateData(String[] data, Model model) {
        String errorMessage = null;
        String[] columns = model.getCOLUMNS();

        try {
            if (data.length != columns.length) {
                errorMessage = "ERROR PARAMS LENGTH !!❌ ";
                throw new Exception(errorMessage);
            }

            DataType[] dataTypes = model.getDataTypes();

            for (int i = 0; i < data.length; i++) {
                errorMessage = ERROR_MAP.get(dataTypes[i]).apply(data[i]);

                if (errorMessage != null) {
                    throw new Exception(errorMessage);
                }
            }

        } catch (Exception e) {
            System.err.println(e);
        }

        return errorMessage;
    }
}