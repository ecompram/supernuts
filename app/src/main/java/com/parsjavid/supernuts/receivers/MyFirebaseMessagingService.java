package com.parsjavid.supernuts.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.parsjavid.supernuts.BuildConfig;
import com.parsjavid.supernuts.MainActivity;
import com.parsjavid.supernuts.R;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }
    // [END receive_message]

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            //Object f = remoteMessage;
            sendNotification2(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData() != null) {
            //Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            //Object f = remoteMessage;
            Map<String, String> data = remoteMessage.getData();
            if (data.size() > 0)
                try {
                    if ("NewsNotification".equals(data.get("type")))
                        sendNewsNotification(data);
                    else
                        try {
                            sendNotification(new JSONObject(new JSONObject(data).getString("data")).getString("id"));
                        } catch (Exception e) {
                            sendNotification(data.get("id"));
                        }
                } catch (Exception e) {
                    try {
                        sendNotification(new JSONObject(new JSONObject(data).getString("data")).getString("id"));
                    } catch (Exception e2) {
                        sendNotification(data.get("id"));
                    }
                }
        }
    }

    private void sendNotification(final String messageId) {

        Intent intent = new Intent(this, ReplyQuestionActivity.class);
        intent.putExtra("id", messageId);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        final Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "my_channel_id_03")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Random randomGenerator = new Random();
        final int randomInt = randomGenerator.nextInt(100);

        notificationBuilder.setContentTitle("پاسخ سوال خود را دریافت کردید").setContentText("برای مشاهده کلیک کنید");
        notificationManager.notify(randomInt /* ID of notification */, notificationBuilder.build());
    }

    private void sendNewsNotification(final Map<String, String> data) {

        try {
            Intent intent = new Intent(this, NewsDetailsActivity.class);
            intent.putExtra("id", data.get("id"));

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            final Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "my_channel_id_01")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            new SendHttpRequestTask(data, notificationBuilder).execute();

        } catch (Exception e) {
        }
    }

    private void sendNotification2(final String messageTitle, final String messageBody) {

        try {
            Intent intent = null;
            if (!messageBody.contains("http")) {
                if (!messageBody.contains(getPackageManager().getPackageInfo(getPackageName(), 0).versionName)) {
                    intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
            } else
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(messageBody));

            //
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            final Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "MyChannelId_01")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);


            final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Random randomGenerator = new Random();
            final int randomInt = randomGenerator.nextInt(100);

            notificationBuilder.setContentTitle(messageTitle).setContentText(messageBody);
            notificationManager.notify(randomInt /* ID of notification */, notificationBuilder.build());
        } catch (Exception e) {
        }
    }

    private class SendHttpRequestTask extends AsyncTask<String, Void, Bitmap> {

        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Map<String, String> data;
        NotificationCompat.Builder notificationBuilder;

        public SendHttpRequestTask(final Map<String, String> data, NotificationCompat.Builder notificationBuilder) {
            this.data = data;
            this.notificationBuilder = notificationBuilder;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(BuildConfig.BASEURL + "Present/File/RenderFile?attachmentId=" + data.get("imgUrl"));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            try {
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle()
                                .setBigContentTitle(data.get("title"))
                                .setSummaryText(fromHtml(data.get("body")))
                                .bigPicture(result));
                notificationManager.notify(new Random().nextInt(1000), notificationBuilder.build());
            } catch (Exception e) {
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle()
                                .setBigContentTitle(data.get("title"))
                                .setSummaryText(fromHtml(data.get("body"))));
                notificationManager.notify(new Random().nextInt(1000), notificationBuilder.build());
            }
        }
    }
}