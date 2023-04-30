import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

class EmployerListSelectionListener implements ListSelectionListener {
    private JTable planningTable;
    private JList<String> employerList;

    public EmployerListSelectionListener(JTable planningTable, JList<String> employerList) {
        this.planningTable = planningTable;
        this.employerList = employerList;
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String selectedEmployer = employerList.getSelectedValue();
            if (selectedEmployer != null) {
                String[][] data = generateTableData();
                String[] columnNames = {"Horaire", selectedEmployer};
                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                planningTable.setModel(tableModel);
            }
        }
    }

    private String[][] generateTableData() {
        String[][] data = new String[11][2];
        int hour = 9;
        for (int i = 0; i < 11; i++) {
            data[i][0] = hour + ":00";
            data[i][1] = "";
            hour++;
        }
        return data;
    }
}
