import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;

public class CalendarApp extends JFrame {
    private DefaultListModel<String> moisListModel;
    private JList<String> moisList;
    private JLabel moisAnneeLabel;
    private JLabel dateLabel;
    private JTable calendarTable;
    private DefaultListModel<String> employerListModel;
    private JList<String> employerList;

    private String[] mois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

    public CalendarApp() {
        setTitle("Calendar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        dateLabel = new JLabel();
        // Liste des mois à gauche
        moisListModel = new DefaultListModel<>();
        for (String m : mois) {
            moisListModel.addElement(m);
        }
        moisList = new JList<>(moisListModel);
        moisList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moisList.addListSelectionListener(new MoisListSelectionListener());
        add(new JScrollPane(moisList), BorderLayout.WEST);

        // Mois et année sélectionnés en haut
        moisAnneeLabel = new JLabel();
        moisAnneeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(moisAnneeLabel, BorderLayout.NORTH);

        // je créer un container
        JPanel centralContainer = new JPanel();
        centralContainer.setLayout(new GridLayout(0,1));
        add(centralContainer, BorderLayout.CENTER);

        // Tableau des jours du mois au centre
        calendarTable = new JTable(6, 7); // 6 lignes pour les jours, 7 colonnes pour les jours de la semaine

        JScrollPane scrollPane = new JScrollPane(calendarTable);
        centralContainer.add(scrollPane);

        // Panel des employés à droite
        JPanel employerPanel = createEmployerPanel();
        centralContainer.add(employerPanel);

        pack();
        setLocationRelativeTo(null);
    }

    private class MoisListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = moisList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    String selectedMonth = mois[selectedIndex];
                    String selectedYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                    moisAnneeLabel.setText(selectedMonth + " " + selectedYear);

                    // Mettre à jour le tableau des jours du mois
                    updateCalendarTable(selectedIndex);

                }
            }
        }
    }

    private void updateCalendarTable(int selectedMonthIndex) {
        // Obtention de l'année actuelle
        int selectedYear = Calendar.getInstance().get(Calendar.YEAR);

        // Obtention du mois sélectionné à partir de l'index
        int selectedMonth = selectedMonthIndex + 1; // L'index commence à 0, donc on ajoute 1 pour obtenir le mois réel

        // Obtention du nombre de jours dans le mois sélectionné
        Calendar calendar = Calendar.getInstance();
        calendar.set(selectedYear, selectedMonth - 1, 1); // Le mois dans Calendar commence à 0, donc on soustrait 1
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Obtention du jour de la semaine du premier jour du mois
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                // Remplissage du tableau des jours du mois
                String[][] data = new String[6][7];
                int day = 1;
                boolean dayStarted = false;
        
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (i == 0 && j < firstDayOfWeek - 1) {
                            // Remplissage des jours précédant le premier jour du mois avec des espaces vides
                            data[i][j] = "";
                        } else {
                            if (day <= daysInMonth) {
                                // Remplissage des jours du mois
                                data[i][j] = Integer.toString(day);
                                day++;
                                dayStarted = true;
                            } else {
                                // Remplissage des jours après la fin du mois avec des espaces vides
                                data[i][j] = "";
                            }
                        }
                    }
                    if (!dayStarted) {
                        // Arrêt du remplissage si tous les jours ont été ajoutés
                        break;
                    }
                }
        
                String[] columnNames = {"Dim", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam"};
                calendarTable.setModel(new DefaultTableModel(data, columnNames));
            }
        
            private JPanel createEmployerPanel() {
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());

                // Nouveau tableau
                JTable planning = new JTable();
                panel.add(new JScrollPane(planning), BorderLayout.CENTER);

                // Liste des employés
                employerListModel = new DefaultListModel<>();
                employerListModel.addElement("Michel LARTA");
                employerListModel.addElement("William VANDAL");
                employerListModel.addElement("Mathieu TOULIN");
                employerList = new JList<>(employerListModel);
                employerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                employerList.addListSelectionListener(new EmployerListSelectionListener(planning, employerList));
                panel.add(new JScrollPane(employerList), BorderLayout.WEST);

                dateLabel = new JLabel();
                dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(dateLabel, BorderLayout.NORTH);
                calendarTable.getSelectionModel().addListSelectionListener(new CalendarTableSelectionListener(calendarTable, moisList, dateLabel));
        
                return panel;
            }
        
            public static void main(String[] args) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new CalendarApp().setVisible(true);
                    }
                });
            }
        }
        
