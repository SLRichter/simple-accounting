package de.arnorichter.simpleaccounting.views.stats;

import com.storedobject.chart.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.arnorichter.simpleaccounting.data.accountingposition.AccountingPosition;
import de.arnorichter.simpleaccounting.data.accountingposition.AccountingPositionType;
import de.arnorichter.simpleaccounting.services.AccountingPostionService;
import de.arnorichter.simpleaccounting.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse repräsentiert die "Stats"-Ansicht der Anwendung zur Visualisierung von Finanzdaten.
 * Sie bietet eine Benutzeroberfläche mit Datumsauswahl, Ausgaben-, Einnahmen- und Summenanzeige sowie Diagrammen.
 */
@PageTitle("Stats")
@Route(value = "stats", layout = MainLayout.class)
@Uses(Icon.class)
@PermitAll
public class StatsView extends HorizontalLayout {

	private SOChart soChart;
	private DatePicker datePicker;
	private Button reloadBtn;
	private VerticalLayout leftVLayout;
	private VerticalLayout leftInnerTopVLayout;
	private VerticalLayout leftInnerBottomVLayout;
	private VerticalLayout rightVLayout;
	private VerticalLayout rightInnerTopVLayout;
	private VerticalLayout rightInnerBottomVLayout;
	private H4 monthH4;
	private H5 expensesTextH5;
	private H5 expensesH5;
	private H5 incomeTextH5;
	private H5 incomeH5;
	private H5 sumTextH5;
	private H5 sumH5;
	private List<Chart> charts = new ArrayList<>();
	private DecimalFormat dec = new DecimalFormat("#0.00");
	LocalDate date = LocalDate.now();

	public StatsView(AccountingPostionService service) {

		setSizeFull();
		add(leftLayout(service), rightLayout(service));
	}

	/**
	 * Erstellt und konfiguriert das linke vertikale Layout der Benutzeroberfläche.
	 *
	 * @param service Ein AccountingPostionService-Objekt, das zum Abrufen von Daten verwendet wird.
	 * @return VerticalLayout-Komponente, die die linke Seite der Benutzeroberfläche darstellt.
	 */
	private VerticalLayout leftLayout(AccountingPostionService service) {

		//Elemente und Styling
		leftVLayout = new VerticalLayout();
		leftVLayout.setHeightFull();
		leftVLayout.setWidth("20%");

		leftInnerTopVLayout = new VerticalLayout();
		leftInnerTopVLayout.setHeight("50%");
		leftInnerTopVLayout.setWidthFull();

		leftInnerBottomVLayout = new VerticalLayout();
		leftInnerBottomVLayout.setHeight("50%");
		leftInnerBottomVLayout.setWidthFull();

		datePicker = new DatePicker();
		datePicker.setValue(date);
		datePicker.setWidth("100%");

		LocalDate datePickerValue = datePicker.getValue();

		monthH4 = new H4(date.getMonth() + " " + date.getYear());
		expensesTextH5 = new H5("Expenses:");
		expensesH5 = new H5();
		incomeTextH5 = new H5("Income:");
		incomeH5 = new H5();
		sumTextH5 = new H5("Sum:");
		sumH5 = new H5();

		//Werte setzen
		setValues(service, date);

		//Button-Funktionalität
		reloadBtn = new Button("Reload", event -> {
			try {
				soChart.removeAll();
				soChart.clear();
				charts.clear();

				createCharts(charts, createMatrix(service, datePickerValue));
				charts.forEach(soChart::add);
				soChart.update(false);

				setValues(service, datePickerValue);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		reloadBtn.setWidth("100%");

		leftInnerTopVLayout.add(datePicker, reloadBtn);
		leftInnerBottomVLayout.add(monthH4, expensesTextH5, expensesH5, incomeTextH5, incomeH5, sumTextH5, sumH5);
		leftVLayout.add(leftInnerTopVLayout, leftInnerBottomVLayout);

		return leftVLayout;
	}

	/**
	 * Erstellt und konfiguriert das rechte vertikale Layout der Benutzeroberfläche.
	 *
	 * @param service Ein AccountingPostionService-Objekt, das zum Abrufen von Daten verwendet wird.
	 * @return VerticalLayout-Komponente, die die rechte Seite der Benutzeroberfläche darstellt.
	 */
	private VerticalLayout rightLayout(AccountingPostionService service) {

		//Elemente und Styling
		rightVLayout = new VerticalLayout();
		rightVLayout.setHeightFull();
		rightVLayout.setWidth("80%");

		rightInnerTopVLayout = new VerticalLayout();
		rightInnerTopVLayout.setHeight("50%");
		rightInnerTopVLayout.setWidthFull();

		soChart = new SOChart();
		soChart.setWidthFull();
		soChart.setHeightFull();

		//Diagramme erstellen
		createCharts(charts, createMatrix(service, datePicker.getValue()));
		charts.forEach(soChart::add);

		rightInnerTopVLayout.add(soChart);
		rightVLayout.add(rightInnerTopVLayout);
		return rightVLayout;
	}

	/**
	 * Setzt die Werte für Ausgaben, Einnahmen und Summe basierend auf dem ausgewählten Datum und den abgerufenen
	 * Daten.
	 *
	 * @param service         AccountingPostionService für das holen der Daten aus der DB
	 * @param datePickerValue Ausgewähltes Datum
	 */
	private void setValues(AccountingPostionService service, LocalDate datePickerValue) {

		List<AccountingPosition> accountingPositions = service.findByMonthYear(datePickerValue.getMonthValue(),
				datePickerValue.getYear());
		var income = accountingPositions.stream()
				.filter(e -> e.getType().equals(AccountingPositionType.INCOME))
				.mapToDouble(AccountingPosition::getAmount).sum();
		var expenses = accountingPositions.stream()
				.filter(e -> e.getType().equals(AccountingPositionType.EXPENSES))
				.mapToDouble(AccountingPosition::getAmount).sum();
		var sum = accountingPositions.stream().mapToDouble(AccountingPosition::getAmount).sum();

		monthH4.setText(datePickerValue.getMonth() + " " + datePickerValue.getYear());
		expensesH5.setText(dec.format(expenses) + "€");
		incomeH5.setText(dec.format(income) + "€");
		sumH5.setText(dec.format(sum) + "€");
	}

	/**
	 * Erstellt die Datenmatrix, die als Grundlage für die Diagramme dient.
	 *
	 * @param service AccountingPositionService für das holen der Daten aus der DB
	 * @param date    Datum für welches die Daten geholt werden sollen
	 * @return Die Datenmatrix, welche die Diagrammdaten enthält.
	 */
	private DataMatrix createMatrix(AccountingPostionService service, LocalDate date) {
		List<AccountingPosition> accountingPositions = service.findByMonthYear(date.getMonthValue(), date.getYear());

		DataMatrix dataMatrix = new DataMatrix("Amount");

		dataMatrix.setRowNames("Income", "Expenses");
		dataMatrix.setRowDataName("Days");

		CategoryData dateCategory = new CategoryData();
		Data incomeData = new Data();
		Data expensesData = new Data();
		for (int i = 1; i <= date.lengthOfMonth(); i++) {
			LocalDate dayOfMonth = LocalDate.of(date.getYear(), date.getMonthValue(), i);
			var tempList = accountingPositions.stream().filter(e -> e.getDate().equals(dayOfMonth)).toList();
			var income = tempList.stream().filter(e -> e.getType().equals(AccountingPositionType.INCOME))
					.mapToDouble(AccountingPosition::getAmount).sum();
			var expenses = tempList.stream().filter(e -> e.getType().equals(AccountingPositionType.EXPENSES))
					.mapToDouble(AccountingPosition::getAmount).sum();

			dateCategory.add(dayOfMonth.toString());
			incomeData.add(income);
			expensesData.add(expenses);
		}
		dataMatrix.setColumnNames(dateCategory);
		dataMatrix.addRow(incomeData);
		dataMatrix.addRow(expensesData);

		return dataMatrix;
	}

	/**
	 * Erstellt die Diagramme für den SOChart basierend auf der bereitgestellten Datenmatrix.
	 *
	 * @param charts     Liste, in die die erstellten Diagramme eingefügt werden
	 * @param dataMatrix Datenmatrix, welche die Daten für die Diagramme enthält
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
		rc1.getPosition(true).setBottom(Size.percentage(10));

		RectangularCoordinate rc2 = new RectangularCoordinate();
		rc2.addAxis(xAxisYear, yAxis);
		rc2.getPosition(true).setTop(Size.percentage(10));

		// Balkendiagramme erstellen
		for (int i = 0; i < dataMatrix.getRowCount(); i++) {
			BarChart bc = new BarChart(dataMatrix.getColumnNames(), dataMatrix.getRow(i));
			if (dataMatrix.getRowName(i).equals("Income")) {
				bc.setColors(new Color("#008000"));
			} else {
				bc.setColors(new Color("#FF0000"));
			}
			bc.setName(dataMatrix.getRowName(i));
			bc.plotOn(rc1);
			charts.add(bc);
		}
	}
}
