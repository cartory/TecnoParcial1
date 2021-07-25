package Utils;

public abstract class Controller {

    protected Model model;
    protected RenderHTML renderHTML;
    private String defaultErrorMessage = "<h1>Ops! SOMETHING GOES WRONG!!</h1>";

    // MUST CONNECT WITH RENDER CLASS
    public Controller(Model model) {
        this.model = model;
        this.renderHTML = RenderHTML.getInstance();
    }

    public Object[][] getATTRIBS() {
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
                return renderHTML.dataTableHTML(this.model.TABLE, this.model.selectAll());
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
                return this.renderHTML.dataTableHTML(this.model.TABLE, this.model.selectAll());
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        return defaultErrorMessage;
    }

    public String showHTML(String id) {
        try {
            DataTable table = this.model.selectOne(id);
            if (table.getRowCount() > 0) {
                return this.renderHTML.dataTableHTML(this.model.TABLE, this.model.selectAll());
            } else {
                return "<h1>ERROR 404 NOT FOUND!!</h1>";
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        return defaultErrorMessage;
    }

    public String DeleteHTML(String id) {
        if (this.model.delete(id)) {
            return this.renderHTML.dataTableHTML(this.model.TABLE, this.model.selectAll());
        }

        return defaultErrorMessage;
    }
}
