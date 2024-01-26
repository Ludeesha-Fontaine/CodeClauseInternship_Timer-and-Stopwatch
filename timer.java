import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class timer implements ActionListener{
    JFrame f;
    JPanel p;
    JRadioButton r1,r2;
    JButton start,stop,reset;
    JLabel heading,timer,stopwatch,timeLabel;
    JTextField txt1;
    Timer timer1,timer2;
    int elapsedTime;
    timer(){
        f = new JFrame("Timer & Stopwatch");
        p = new JPanel(null);
        ButtonGroup b = new ButtonGroup();
        r1 = new JRadioButton("Timer");
        r2 = new JRadioButton("Stopwatch");
        start = new JButton("START");
        stop = new JButton("STOP");
        reset = new JButton("RESET");
        heading = new JLabel("<html><u>TIMER & STOP WATCH</u></html>");
        timer = new JLabel("Timer");
        stopwatch = new JLabel("Stopwatch");
        timeLabel = new JLabel("00:00:00");
        txt1 = new JTextField("hh:mm:ss");
        txt1.setEditable(true);
        start.addActionListener(this);
        stop.addActionListener(this);
        reset.addActionListener(this);
        r1.setSelected(true);
        r2.setSelected(true);
        f.setVisible(true);
        f.setSize(500, 350);
        f.add(p);
        p.add(heading);
        p.add(r1);
        p.add(r2);
        b.add(r1);
        b.add(r2);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        p.add(start);
        p.add(stop);
        p.add(reset);
        p.add(timer);
        p.add(stopwatch);
        p.add(txt1);
        p.add(timeLabel);

        Font font1 = new Font("Arial", Font.CENTER_BASELINE, 40);
        Font font2 = new Font("Arial", Font.BOLD, 20);
        heading.setFont(font1);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(start);
        buttonPanel.add(stop);
        buttonPanel.add(reset);
        buttonPanel.setLayout(new GridLayout(1, 3));
        p.add(buttonPanel);
        heading.setBounds(18, 10, 450, 50);
        r1.setBounds(100, 60, 150, 50);
        r2.setBounds(250, 60, 150, 50);
        r1.setFont(font2);
        r2.setFont(font2);
        timeLabel.setBounds(140, 120, 202, 50);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        timeLabel.setForeground(Color.BLUE);
        timeLabel.setBorder(new LineBorder(Color.BLACK, 3));
        txt1.setBounds(150, 200, 180, 30);
        buttonPanel.setBounds(40, 250, 400, 50);

        timer2 = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                elapsedTime += 1000;
                int hours = elapsedTime / 3600000;
                int minutes = (elapsedTime % 3600000) / 60000;
                int seconds = (elapsedTime % 60000) / 1000;
                String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                timeLabel.setText(time);
            }
        });
    }

    public void actionPerformed(ActionEvent e){
        if ((r2.isSelected()) && (e.getSource() == start)){
            p.setBackground(Color.LIGHT_GRAY);
            txt1.setEditable(false);
            timeLabel.setForeground(Color.BLUE);
            timer2.start();
        } else if ((r2.isSelected()) && (e.getSource() == stop)) {
            timeLabel.setForeground(Color.RED);
            txt1.setEditable(false);
            timer2.stop();
        } else if ((r2.isSelected()) && (e.getSource() == reset)) {
            p.setBackground(null);
            txt1.setEditable(false);
            timeLabel.setForeground(Color.BLUE);
            timer2.stop();
            elapsedTime = 0;
            int hours = elapsedTime / 3600000;
            int minutes = (elapsedTime % 3600000) / 60000;
            int seconds = (elapsedTime % 60000) / 1000;
            String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timeLabel.setText(time);
        }else if ((r1.isSelected()) && (e.getSource() == start)){
            p.setBackground(Color.LIGHT_GRAY);
            txt1.setEditable(true);
            timeLabel.setForeground(Color.BLUE);
            String timeText = txt1.getText();
            if (isValidTimeFormat(timeText)) {
                String[] timeParts = txt1.getText().split(":");
                int hours = Integer.parseInt(timeParts[0]);
                int minutes = Integer.parseInt(timeParts[1]);
                int seconds = Integer.parseInt(timeParts[2]);
                elapsedTime = hours * 3600 + minutes * 60 + seconds;

                if (elapsedTime <= 0) return;

                timer1 = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        elapsedTime--;
                        if (elapsedTime <= 0) {
                            timer1.stop();
                            elapsedTime = 0;
                        }
                        int hours = elapsedTime / 3600;
                        int minutes = (elapsedTime % 3600) / 60;
                        int seconds = elapsedTime % 60;
                        timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                    }
                });
                timer1.start();

            }else{
                JOptionPane.showMessageDialog(null, "Invalid time format! Please enter time in hh:mm:ss format.");
            }
        }else if ((r1.isSelected()) && (e.getSource() == stop)) {
            timeLabel.setForeground(Color.RED);
            timer1.stop();
        }else if ((r1.isSelected()) && (e.getSource() == reset)){
            p.setBackground(null);
            txt1.setText("hh:mm:ss");
            timeLabel.setForeground(Color.BLUE);
            timer1.stop();
            elapsedTime = 0;
            int hours = elapsedTime / 3600000;
            int minutes = (elapsedTime % 3600000) / 60000;
            int seconds = (elapsedTime % 60000) / 1000;
            String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timeLabel.setText(time);
        }else {
            JOptionPane.showMessageDialog(null, "Please select Timer to start.");
        }
    }

    private boolean isValidTimeFormat(String timeText) {
        String regexPattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
        return timeText.matches(regexPattern);
    }
    public static void main(String[] args) {
        new timer();
    }
}
