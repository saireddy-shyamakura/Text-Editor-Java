import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener {
        JTextArea textArea;
        JScrollPane scrollPane;
        JSpinner fontSizeSpinner;
        JLabel fontLabel;
        JButton fontColorButton;
        JComboBox fontBox;
        JMenuBar menuBar;
        JMenu fileMenu;
        JMenuItem openItem;
        JMenuItem saveItem;
        JMenuItem exitItem;
        TextEditor(){
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setTitle("Sai Text Editor");
            this.setSize(500,500);
            this.setLayout(new FlowLayout());
            this.setLocationRelativeTo(null);

            textArea = new JTextArea();
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(new Font("Arial",Font.PLAIN,20));

            scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500,500));
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            fontLabel = new JLabel("Font :");

            fontSizeSpinner = new JSpinner();
            fontSizeSpinner.setPreferredSize(new Dimension(50,25));
            fontSizeSpinner.setValue(20);
            fontSizeSpinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)fontSizeSpinner.getValue()));
                }
            });
            fontColorButton = new JButton("Color");
            fontColorButton.addActionListener(this);

            String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
            fontBox = new JComboBox(fonts);
            fontBox.addActionListener(this);
            fontBox.setSelectedItem("Arial");

// --------- menu bar ---------//

            menuBar = new JMenuBar();
            fileMenu = new JMenu("Options");
            openItem = new JMenuItem("Open New File");
            saveItem = new JMenuItem("Save File");
            exitItem = new JMenuItem("Exit the File");

            openItem.addActionListener(this);
            saveItem.addActionListener(this);
            exitItem.addActionListener(this);

            fileMenu.add(openItem);
            fileMenu.add(saveItem);
            fileMenu.add(exitItem);
            menuBar.add(fileMenu);

            // -------------- menu bar --------//

            this.setJMenuBar(menuBar);
            this.add(fontLabel);
            this.add(fontSizeSpinner);
            this.add(fontColorButton);
            this.add(fontBox);
            this.add(scrollPane);
            this.setVisible(true);

        }
    @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==fontColorButton){
                JColorChooser colorChooser = new JColorChooser();
                Color color = JColorChooser.showDialog(null,"Choose a Color", Color.black);
                textArea.setForeground(color);
            }

            if(e.getSource()==fontBox) {
                textArea.setFont(new Font((String) fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));

            }

            if(e.getSource()==openItem){

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files","txt");
                fileChooser.setFileFilter(filter);
                int response = fileChooser.showOpenDialog(null);
                        if(response == JFileChooser.APPROVE_OPTION){
                            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                            Scanner fileIn = null;
                            try {
                                fileIn = new Scanner(file);
                                if(file.isFile()){
                                    while (fileIn.hasNextLine()){
                                        String line = fileIn.nextLine()+"\n";
                                        textArea.append(line);
                                    }
                                }
                            }
                            catch (FileNotFoundException e1){
                                e1.printStackTrace();
                            }
                            finally {
                                fileIn.close();
                            }
                        }

            }

        if(e.getSource()==saveItem){

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showSaveDialog(null);

            if(response == JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter fileout = null;
                file= new File(fileChooser.getSelectedFile().getAbsolutePath());

                try {
                    fileout = new PrintWriter(file);
                    fileout.println(textArea.getText());
                }
                catch (FileNotFoundException e1){
                    e1.printStackTrace();
                }
                finally {
                    fileout.close();
                }

            }
        }

        if(e.getSource()==exitItem){
            System.exit(0);

        }

        }
    }