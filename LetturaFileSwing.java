import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class LetturaFileSwing extends JFrame {
    private JComboBox<String> fileComboBox;
    private JTextArea textArea;
    private JButton save;

    public LetturaFileSwing() {
        setTitle("Lettura File con Swing");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ComboBox per selezionare il file
        fileComboBox = new JComboBox<>();
        fileComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFile = (String) fileComboBox.getSelectedItem();
                if (selectedFile != null) {
                    try {
                        leggiFile(selectedFile);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(LetturaFileSwing.this, "Errore durante la lettura del file.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        add(fileComboBox, BorderLayout.NORTH);

        // TextArea per visualizzare il contenuto del file
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        //JButton per salvare il file txt 
        save = new JButton("save"); 
        save.addActionListener(e2->{
            save();
        });

        add(save, BorderLayout.SOUTH);
        
        // Caricamento dei file nella ComboBox
        caricaFile();

        setLocationRelativeTo(null);
    }

//metodo salva le modifiche dei file txt.
    public void save() {
        String file = (String) fileComboBox.getSelectedItem();
        if (file != null) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(textArea.getText());
                writer.close();
                JOptionPane.showMessageDialog(LetturaFileSwing.this, "File salvato con successo.","salvataggio", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(LetturaFileSwing.this, "Errore durante il salvataggio del file", "errore",JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(LetturaFileSwing.this, "Nessun file selezionato.", "errore",JOptionPane.ERROR_MESSAGE);
        }
    }
    

            
    //Metodo  che carica i nomi dei file con estensione .txt presenti nella directory corrente in un oggetto JComboBox.
    private void caricaFile() {
        File directory = new File("."); // director corrente
        File[] files = directory.listFiles(); // Ottiene un array di oggetti File che rappresentano tutti i file e le directory contenuti nella directory corrente.
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) { //se l oggetto e un file e finisce con .txt verra aggiunta alla combobox
                    fileComboBox.addItem(file.getName());
                }
            }
        }
    }

    // Metodo per leggere il contenuto del file e visualizzarlo nella JTextArea
    private void leggiFile(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        textArea.setText(content.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LetturaFileSwing frame = new LetturaFileSwing();
                frame.setVisible(true);
            }
        });
    }
}
