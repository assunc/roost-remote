import javax.swing.*;

public class TimePicker extends JComboBox<String> {
    String[] times;
    public TimePicker() {
        super();
        times = new String[48];
        for(int i = 0; i < 48; i++) {
            times[i] = i/2 + ":" + (i%2==0 ? "00" : "30");
        }
        setModel(new DefaultComboBoxModel<>(times));
    }
}
