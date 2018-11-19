package app.jubilate.piribisoft.com.jubilate;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DataHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.helper = new DataHelper(this);

        updateUI();
    }

    private void checkIfTodayIsWorked() {
        Calendar c = Calendar.getInstance();
        String theDate = this.helper.formatCalendar(c);

        Button btn = (Button) findViewById(R.id.btnWork);
        if (helper.getWorkedDays().contains(theDate)) {
            // Disable button and change text.
            btn.setText("Ya has trabajado hoy.");
            btn.setEnabled(false);
        } else if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            btn.setText("Los domingos no se trabaja!");
            btn.setEnabled(false);
        }
    }

    private void updateUI() {
        checkIfTodayIsWorked();

        TextView txt = (TextView) findViewById(R.id.txtMiddle);
        txt.setText(helper.calculateDays() + "");

        TextView txtFinalDay = (TextView) findViewById(R.id.txtDetails);
        txtFinalDay.setText("Terminas el día " + helper.getFinalDay());
    }

    public void btnWork_click(View view) {
        SharedPreferences settings = getSharedPreferences(DataHelper.PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();

        ArrayList<String> workedDays = this.helper.getWorkedDays();
        Calendar today = Calendar.getInstance();
        workedDays.add(this.helper.formatCalendar(today));

        String wdString = "";
        for (String day : workedDays) {
            wdString += day + ";";
        }

        editor.putString(DataHelper.WORKED_DAYS, wdString);
        // Commit the edits!
        editor.commit();

        Toast toast = Toast.makeText(this, "Enhorabuena, un dia menos!", Toast.LENGTH_SHORT);
        toast.show();

        updateUI();
    }
}
