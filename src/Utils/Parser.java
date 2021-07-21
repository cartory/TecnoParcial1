/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Date;
import java.sql.Time;

import Utils.Model.DataType;

import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;

/**
 *
 * @author cartory
 */
abstract class Parser {

    protected Map<DataType, Function<String, Object>> PARSE_MAP = new HashMap<DataType, Function<String, Object>>() {
        {
            put(DataType.STRING, arg -> arg);
            put(DataType.DATE, arg -> Date.valueOf(arg));
            put(DataType.TIME, arg -> Time.valueOf(arg));
            put(DataType.FLOAT, arg -> Float.parseFloat(arg));
            put(DataType.INTEGER, arg -> Integer.parseInt(arg));
        }
    };

    protected Parser() {
    }

    public Object[] parseData(String[] args, Model model) {
        Object[] parsedData = new String[args.length];

        DataType[] types = model.getDataTypes();

        for (int i = 0; i < args.length; i++) {
            parsedData[i] = PARSE_MAP.get(types[i]).apply(args[i]);
        }
        return parsedData;
    }

    public boolean isInteger(String arg) {
        try {
            Integer.parseInt(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isTime(String arg) {
        try {
            Time.valueOf(arg);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isBoolean(String arg) {
        return Boolean.parseBoolean(arg) || "false".equalsIgnoreCase(arg);
    }

    public boolean isDate(String arg) {
        try {
            Date.valueOf(arg);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isFloat(String arg) {
        try {
            Float.parseFloat(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isString(String arg) {
        return arg.length() != 0;
    }
}
