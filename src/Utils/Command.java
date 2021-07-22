package Utils;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.BiFunction;

import Controllers.*;
import Utils.Model.DataType;

public class Command {

    private static final String SPLIT = ";;";

    public static enum Operation {
        Read, Create, Update, Delete,
    }

    Map<String, Controller> useCaseMap;
    Map<Operation, BiFunction<String[], Controller, String>> commandMap;

    public Command() {
        this.commandMap = new HashMap<Operation, BiFunction<String[], Controller, String>>() {
            {
                put(Operation.Read, (args, controller) -> controller.indexHTML());
                put(Operation.Update, (args, controller) -> controller.editHTML(args));
                put(Operation.Create, (args, controller) -> controller.createHTML(args));
                put(Operation.Delete, (args, controller) -> controller.DeleteHTML(args[0]));
            }
        };

        this.useCaseMap = new HashMap<String, Controller>() {
            {
                put("Gestionar Usuario@usuario", new UserController());
                put("Gestionar Servicio@servicio", new ServiceController());
            }
        };
    }

    public String executeCommand(String useCase, Operation operation, String args[]) {
        Controller controller = this.useCaseMap.get(useCase);
        return this.commandMap.get(operation).apply(args, controller);
    }

    private String getCommandString(String key, Controller controller) {
        HashMap<String, DataType> attributes = controller.getATTRIBS();

        String res = "";
        int i = 0, colCount = attributes.size();

        for (Map.Entry<String, DataType> set : attributes.entrySet()) {
            res += set.getValue() + " " + set.getKey();

            if (i < colCount - 1) {
                res += SPLIT;
            }

            i++;
        }

        return key + "[" + res + "];";
    }

    private LinkedList<Object> parseCommand(String header, String args[], String subject) {
        String sub = subject.trim();
        LinkedList<Object> parsedList = new LinkedList<Object>();

        String partesSubject[] = sub.split("\\[");

        header = partesSubject[0];
        String cuerpo[] = partesSubject[1].split("\\]");

        if (cuerpo.length != 0) {
            args = cuerpo[0].split("\\;;");
            for (int i = 0; i < args.length; i++) {
                args[i] = args[i].trim();
            }
        }

        parsedList.push(header);
        parsedList.push(args);

        return parsedList;
    }

    public String getInfoHTML() {
        String[][] table = new String[this.useCaseMap.size() * 4 + 1][3];

        table[0][2] = "COMANDO";
        table[0][1] = "OPERACIÓN";
        table[0][0] = "CASO DE USO";

        int i = 1;
        Operation operations[] = Operation.values();

        for (Map.Entry<String, Controller> set : this.useCaseMap.entrySet()) {
            String[] keys = set.getKey().split("@");
            Operation operation = operations[(i - 1) % 4];

            table[i][0] = "CU" + i + ". " + keys[0];
            table[i][1] = "OPERACIÓN : " + operation;
            table[i][2] = getCommandString(keys[1] + operation, set.getValue());
            i++;
        }

        // TODO: MUST CALL RENDER CLASS
        return table.toString();
    }

    public String getResponseHTML(String subject) {
        String header = "";
        String data[] = null;

        LinkedList<Object> parsedData = parseCommand(header, data, subject);
        header = (String) parsedData.get(1);
        data = (String[]) parsedData.get(0);

        String message = null;

        for (String key : this.useCaseMap.keySet()) {
            for (Operation op : Operation.values()) {
                if (header.toLowerCase().equals(key.toLowerCase() + op.toString().toLowerCase())) {
                    message = this.executeCommand(key, op, data);
                    break;
                }
            }

            if (message != null) {
                break;
            }
        }

        return message;
    }
}
