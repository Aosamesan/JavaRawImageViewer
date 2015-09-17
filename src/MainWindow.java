import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;

public class MainWindow extends JFrame{
    // Member Fields
    private JMenuBar mainMenuBar;
    private JFileChooser mainFileChooser;
    private JTabbedPane mainTabPane;


    // constructor and methods
    public MainWindow(){
        System.out.printf("Max : %d, Min : %d\n", Byte.MAX_VALUE, Byte.MIN_VALUE);
        setMainWindow();
        setMainContainer();
        setMainMenuBar();
    }

    private void setMainWindow(){
        setTitle("Test");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFileChooser = new JFileChooser();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
    }

    private void setMainContainer(){
        mainTabPane = new JTabbedPane();
        setContentPane(mainTabPane);
    }

    private void setMainMenuBar(){
        // mainMenuBar
        mainMenuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        mainMenuBar.add(menu);

        ////// File Menu
        JMenuItem openMenuItem = new JMenuItem("Open...");
        openMenuItem.addActionListener((e) -> openRawImage());
        menu.add(openMenuItem);

        JMenuItem setWidthMenuItem = new JMenuItem("Set width...");
        setWidthMenuItem.addActionListener((e) -> {
            try {
                JPanel selectedPanel = (JPanel) mainTabPane.getSelectedComponent();
                for (Component c : selectedPanel.getComponents()) {
                    if (c instanceof RawImageScrollPane) {
                        String s = JOptionPane.showInputDialog(this, "Input width");
                        ((RawImageScrollPane) c).setRawImageWidth(Integer.parseInt(s));
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        menu.add(setWidthMenuItem);

        menu.addSeparator();

        JMenuItem closePresentTabMenuItem = new JMenuItem("Close tab");
        closePresentTabMenuItem.addActionListener((e) -> {
            try{
                Component selectedComponent = mainTabPane.getSelectedComponent();
                if(selectedComponent != null){
                    mainTabPane.remove(selectedComponent);
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }

        });
        menu.add(closePresentTabMenuItem);

        menu.addSeparator();

        JMenuItem quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener((e) -> System.exit(0));
        menu.add(quitMenuItem);

        setJMenuBar(mainMenuBar);
    }

    private void openRawImage(){
        if(mainFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File selectedFile = mainFileChooser.getSelectedFile();
            if(selectedFile.canRead()){
                try {
                    FileInputStream fs = new FileInputStream(selectedFile.getPath());
                    BufferedInputStream bs = new BufferedInputStream(fs);
                    int fileSize = (int)selectedFile.length();
                    byte[] imageBytes = new byte[fileSize];
                    bs.read(imageBytes, 0, fileSize);
                    RawImageScrollPane rawImageScrollPane = new RawImageScrollPane();
                    rawImageScrollPane.setNewImage(imageBytes);
                    rawImageScrollPane.setName("RawImageScrollPane");
                    JPanel shell = new JPanel(new BorderLayout());
                    shell.add(rawImageScrollPane, BorderLayout.CENTER);
                    mainTabPane.addTab(selectedFile.getName(), shell);
                    mainTabPane.setSelectedComponent(shell);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    private void drawImageFromBytes(byte[] bytes, int width){
//        int height = bytes.length / width;
//        int value;
//        Color c;
//        Graphics g = imagePanel.getGraphics();
//        g.clearRect(0, 0, imagePanel.getWidth(), imagePanel.getHeight());
//
//        for(int i = 0; i < height; i++){
//            for(int j = 0; j < width; j++){
//                value = bytes[i * width + j] & 0xFF;
//                c = new Color(value, value, value);
//                g.setColor(c);
//                g.drawRect(j, i, 1, 1);
//            }
//        }
//        g.dispose();
//    }

    // main method
    public static void main(String[] args){
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}