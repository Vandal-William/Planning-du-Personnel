import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class CalendarTableSelectionListener implements ListSelectionListener {
    private JTable calendarTable;
    private JList<String> moisList;
    private JLabel dateLabel;

    public CalendarTableSelectionListener(JTable calendarTable, JList<String> moisList, JLabel dateLabel) {
        this.calendarTable = calendarTable;
        this.moisList = moisList;
        this.dateLabel = dateLabel;
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = calendarTable.getSelectedRow();
            int selectedColumn = calendarTable.getSelectedColumn();
            
            if (selectedRow >= 0 && selectedColumn >= 0) {
                Object selectedValue = calendarTable.getValueAt(selectedRow, selectedColumn);
                if (selectedValue != null && selectedValue instanceof String) {
                    String selectedDay = (String) selectedValue;
                    String selectedMonth = moisList.getSelectedValue();
                    String selectedYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                    
                    String selectedDate = selectedDay + " " + selectedMonth + " " + selectedYear;
                    dateLabel.setText(selectedDate);
                }
            }
        }
    }
}
