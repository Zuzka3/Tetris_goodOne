import javax.swing.*;
import java.awt.*;

public class MenuWindow {

    private JFrame windowFrame;
    private Menu menu;
    public static final int WINDOW_WIDTH = 400, WINDOW_HEIGHT = 455;

    public MenuWindow() {
        windowFrame = new JFrame("MENU");
        ImageIcon icon = new ImageIcon("arrows 1.png");
        windowFrame.setIconImage(icon.getImage());

        windowFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setResizable(false);


        JLabel controls = new JLabel("CONTROLS" );
        controls.setForeground(Color.RED);
        controls.setBounds(40,50,500,100);
        controls.setFont(new Font("Verdana", Font.BOLD, 50));
        windowFrame.add(controls);

        JLabel left = new JLabel("<html><b>LEFT</b> arrow - move left</html>");
        left.setBounds(80, 100, 300, 100);
        left.setFont(new Font("Arial", Font.PLAIN, 20));
        windowFrame.add(left);

        JLabel right = new JLabel("<html> <b>RIGHT</b> arrow - move left</hmtl>");
        right.setBounds(80, 150, 300, 100);
        right.setFont(new Font("Arial", Font.PLAIN, 20));
        windowFrame.add(right);

        JLabel up = new JLabel("<html><b>UP</b> arrow - rotate</html>");
        up.setBounds(80, 200, 300, 100);
        up.setFont(new Font("Arial", Font.PLAIN, 20));
        windowFrame.add(up);

        JLabel down = new JLabel("<html><b>DOWN </b> arrow - go faster</html>");
        down.setBounds(80, 250, 300, 100);
        down.setFont(new Font("Arial", Font.PLAIN, 20));
        windowFrame.add(down);

        JLabel line = new JLabel("----------------------" );
        line.setForeground(Color.BLACK);
        line.setBounds(0,290,500,100);
        line.setFont(new Font("Verdana", Font.BOLD, 40));
        windowFrame.add(line);



        JLabel luck = new JLabel("GOOD LUCK <3" );
        luck.setForeground(Color.BLUE);
        luck.setBounds(20,330,500,100);
        luck.setFont(new Font("Verdana", Font.BOLD, 40));
        windowFrame.add(luck);


        menu = new Menu();
        windowFrame.add(menu);
        windowFrame.setVisible(true);
    }
}
