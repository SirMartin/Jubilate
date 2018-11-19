package app.jubilate.piribisoft.com.jubilate;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Calendar;

public class WidgetProvider extends AppWidgetProvider {

    DataHelper helper;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] Ids) {
        helper = new DataHelper(context);

        for (int i = 0; i < Ids.length; i++) {
            // Create an Intent to launch MainActivity when clicked
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.widget_item);

            // Widgets allow click handlers to only launch pending intents
            views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);

            int daysLeft = this.helper.calculateDays();
            views.setTextViewText(R.id.widgetTextOne, daysLeft + "");

            String lastDay = this.helper.getFinalDay();
            views.setTextViewText(R.id.widgetTextTwo, lastDay);

            appWidgetManager.updateAppWidget(Ids[i], views);
        }
    }

    private void updateDaysLeft() {

    }

    private void updateLastDay() {

    }

}