package de.arnorichter.simpleaccounting.views.stats;

import com.storedobject.chart.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.arnorichter.simpleaccounting.data.accountingposition.AccountingPosition;
import de.arnorichter.simpleaccounting.data.accountingposition.AccountingPositionType;
import de.arnorichter.simpleaccounting.services.AccountingPostionService;
import de.arnorichter.simpleaccounting.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Stats")
@Route(value = "stats", layout = MainLayout.class)
@Uses(Icon.class)
@PermitAll
public class StatsView extends VerticalLayout {

	private SOChart soChart;
	private DatePicker datePicker;
	private Button reloadBtn;

	public StatsView(AccountingPostionService service) {
		LocalDate date = LocalDate.now();
		List<Chart> charts = new ArrayList<>();

		soChart = new SOChart();
		datePicker = new DatePicker("Date");
		datePicker.setValue(date);
		soChart.setSize("900px", "900px");

		reloadBtn = new Button("Reload", event -> {
			try {
				soChart.removeAll();
				soChart.clear();
				charts.clear();

				createCharts(charts, createMatrix(service, datePicker.getValue()));
				charts.forEach(soChart::add);
				soChart.update(false);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		createCharts(charts, createMatrix(service, datePicker.getValue()));
		charts.forEach(soChart::add);
		add(datePicker, reloadBtn, soChart);
	}

	/**
	 * Erstellen der DataMatrxi für die Diagramme
	 *
	 * @param service AccountingPositionService für das holen der Daten aus der DB
	 * @param date    Datum für welches die Daten geholt werden sollen
	 * @return
	 */
	private DataMatrix createMatrix(AccountingPostionService service, LocalDate date) {
		List<AccountingPosition> list = service.findAll();
		list = list.stream().filter(e -> e.getDate().getMonth().equals(date.getMonth())).toList();
		var income = list.stream().filter(e -> e.getType().equals(AccountingPositionType.INCOME)).count();
		var expenses = list.stream().filter(e -> e.getType().equals(AccountingPositionType.EXPENSES)).count();

		DataMatrix dataMatrix = new DataMatrix("Amount");
		dataMatrix.setColumnNames(date.getMonth().toString());
		dataMatrix.setRowNames("Income", "Expenses");
		dataMatrix.setRowDataName("Days");
		dataMatrix.addRow(income);
		dataMatrix.addRow(expenses);

		return dataMatrix;
	}

	/**
	 * Erstellen der Diagramme für den SoChart
	 *
	 * @param charts     Liste, welche mit Diagramme gefüllt wird
	 * @param dataMatrix Daten für die Diagramme
	 */
	private void createCharts(List<Chart> charts, DataMatrix dataMatrix) {

		// Achsen definieren
		XAxis xAxisProduct = new XAxis(DataType.CATEGORY);
		xAxisProduct.setName(dataMatrix.getColumnDataName());
		XAxis xAxisYear = new XAxis(DataType.CATEGORY);
		xAxisYear.setName(dataMatrix.getRowDataName());
		YAxis yAxis = new YAxis(DataType.NUMBER);
		yAxis.setName(dataMatrix.getName());

		// Koordinaten
		RectangularCoordinate rc1 = new RectangularCoordinate();
		rc1.addAxis(xAxisProduct, yAxis);
		rc1.getPosition(true).setBottom(Size.percentage(55)); // Position it leaving 55% space at the bottom

		RectangularCoordinate rc2 = new RectangularCoordinate();
		rc2.addAxis(xAxisYear, yAxis); // Same Y-axis is re-used here
		rc2.getPosition(true).setTop(Size.percentage(55)); // Position it leaving 55% space at the top

		// Balkendiagramme erstellen
		for (int i = 0; i < dataMatrix.getRowCount(); i++) {
			BarChart bc = new BarChart(dataMatrix.getColumnNames(), dataMatrix.getRow(i));
			bc.setName(dataMatrix.getRowName(i));
			bc.plotOn(rc1);
			charts.add(bc);
		}
	}
}
