package Utils;

import org.rendersnake.HtmlCanvas;
import static org.rendersnake.HtmlAttributesFactory.*;

import java.io.IOException;

public class RenderHTML {
    private static RenderHTML instance = null;

    private HtmlCanvas html;
    private String defaultErrorMessage = "<h1>Ops! SOMETHING GOES WRONG!!</h1>";

    RenderHTML() {
        this.html = new HtmlCanvas();
    }

    public static RenderHTML getInstance() {
        if (instance == null) {
            instance = new RenderHTML();
        }
        return instance;
    }

    String dataTableHTML(String title, DataTable dataTable) {
        String innerHTML;

        try {
            // this.html.style().write(styles)._style();
            this.html.html().div(class_("wrapper"));
            this.html.table(class_(" c-table"));

            this.html.thead(class_("c-table__header")).tr();

            for (String nombre : dataTable.names) {
                this.html.th(class_("c-table__col-label")).write(nombre)._th();
            }

            this.html._tr()._thead();
            this.html.tbody();

            int rowCount = dataTable.getRowCount();
            int colCount = dataTable.getColCount();

            for (int i = 0; i < rowCount; i++) {
                this.html.tr();
                for (int j = 0; j < colCount; j++) {
                    this.html.td(class_("c-table__cell")).write(dataTable.getData(i, j))._td();
                }
                this.html._tr();
            }

            this.html._tbody()._table()._div()._html();

            innerHTML = this.html.toHtml();
            innerHTML = "Content-Type: text/html; charset=\"UTF-8\"\n" + innerHTML;
            this.html = new HtmlCanvas();
        } catch (IOException e) {
            innerHTML = this.defaultErrorMessage;
        }

        return innerHTML;
    }
}
