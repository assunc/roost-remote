import javax.swing.*;
import java.sql.Time;

public class TimePicker extends JComboBox<String> {
    String[] times;
    DefaultComboBoxModel<String> model;
    public TimePicker() {
        super();
        times = new String[48];
        for(int i = 0; i < 48; i++) {
            times[i] = i/2 + ":" + (i%2==0?"00":"30");
        }
        model = new DefaultComboBoxModel<>(times);
        setModel(model);
    }
}