package Utils;

import java.util.HashMap;

import Utils.Model.DataType;

import org.rendersnake.HtmlCanvas;

public abstract class Controller {

    protected Model model;
    protected HtmlCanvas html;
    private String defaultErrorMessage = "<h1>Ops! SOMETHING GOES WRONG!!</h1>";

    public Controller(Model model) {
        this.model = model;
        this.html = new HtmlCanvas();
    }

    public HashMap<String, DataType> getATTRIBS() {
        return this.model.getATTRIBS();
    }

    public String indexHTML() {
        DataTable table = this.model.selectAll();
        return table.toString();
    }

    public String createHTML(String args[]) {
        try {
            String errorMessage = Validator.getInstance().validateData(args, this.model);

            if (errorMessage != null) {
                return errorMessage;
            }

            Object[] parsedData = Validator.getInstance().parseData(args, model);

            if (model.create(parsedData)) {
                return "<h1>CREATE SUCCESSFULL!!</h1>";
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return defaultErrorMessage;
    }

    public String editHTML(String args[]) {
        Object[] data = new Object[args.length];

        try {
            data[args.length - 1] = Integer.parseInt(args[0]);
            String errorMessage = Validator.getInstance().validateData(args, this.model);

            if (errorMessage != null) {
                return errorMessage;
            }

            data = Validator.getInstance().parseData(args, this.model);

            if (this.model.update(data)) {
                return "<h1>UPDATE SUCCESSFULL!!</h1>";
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        return "";
    }

    public String showHTML(String id) {
        try {
            DataTable table = this.model.selectOne(id);
            if (table.getRowCount() > 0) {
                return "<h1>SHOW SUCCESSFULL!!</h1>";
            } else {
                return "<h1>ERROR 404 NOT FOUND!!</h1>";
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        return defaultErrorMessage;
    }

    public String DeleteHTML(String id) {
        try {
            if (this.model.delete(id)) {
                return "<h1>CREATE SUCCESSFULL!!</h1>";
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        return defaultErrorMessage;
    }
}
