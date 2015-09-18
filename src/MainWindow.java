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
		private RawFileFilter rawFileFilter;


    // constructor and methods
    public MainWindow(){
        setMainWindow();
        setMainContainer();
        setMainMenuBar();
    }

    private void setMainWindow(){
        setTitle("Raw Image Viewer (Java Swing)");
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFileChooser = new JFileChooser();
				rawFileFilter = new RawFileFilter();
				mainFileChooser.addChoosableFileFilter(rawFileFilter);
				mainFileChooser.setAcceptAllFileFilterUsed(false);
				mainFileChooser.setFileFilter(rawFileFilter);
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
								if(selectedPanel == null)
										return;
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

    // main method
    public static void main(String[] args){
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}
