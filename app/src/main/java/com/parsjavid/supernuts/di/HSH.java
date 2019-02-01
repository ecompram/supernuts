package com.parsjavid.supernuts.di;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jaiselrahman.filepicker.activity.CustomTypefaceSpan;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.parsjavid.supernuts.Application;
//import com.sanatyar.emdadkeshavarz.Classes.Utils;
//import com.sanatyar.emdadkeshavarz.CustomViews.CustomTypefaceSpan;
import com.parsjavid.supernuts.R;
import com.parsjavid.supernuts.classes.Utils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import javax.inject.Singleton;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.toptas.fancyshowcase.FancyShowCaseView;

/**
 * Created by kingBoy on 6/28/2014.
 */
@Singleton
public class HSH {

    private static final HSH ourInstance = new HSH();
    private static String[] englishNumbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    public static final int MESSAGE_TYPE_NORMAL=1;
    public static final int MESSAGE_TYPE_WARNING=2;
    public static final int MESSAGE_TYPE_ERROR=3;

    private HSH() {
    }

    public static HSH getInstance() {
        return ourInstance;
    }

    public static String Parse(String text) {
        if (text.contains("."))
            return String.format("%,d", Long.parseLong(String.valueOf(new BigDecimal(text.substring(0, text.indexOf("."))).toBigIntegerExact())))
                    + "." + text.substring(text.indexOf(".") + 1, text.length());
        else
            return String.format("%,d", Long.parseLong(String.valueOf(new BigDecimal(text).toBigIntegerExact())));
    }


    public static void clearSingleCash(final String imgUrl) {
        try {
            DiskCacheUtils.removeFromCache(imgUrl, ImageLoader.getInstance().getDiskCache());
            MemoryCacheUtils.removeFromCache(imgUrl, ImageLoader.getInstance().getMemoryCache());
        } catch (Exception e) {
        }
    }

    public static void vectorRight(Context cn, TextView view, int a) {
        Drawable drawable = AppCompatResources.getDrawable(cn, a);
        view.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public static void vectorLeft(Context cn, TextView view, int a) {
        Drawable drawable = AppCompatResources.getDrawable(cn, a);
        view.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public static String encrypt(String input) {
        // Simple encryption, not very strong!
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    public static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }

    public static void hideViews(FrameLayout v) {

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) v.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        v.animate().translationY(v.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    public static void showViews(FrameLayout v) {
        v.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }
    public static  void showToast(Context cn,String s ){
        showToast(cn, s, MESSAGE_TYPE_NORMAL);
    }
    public static void showToast(Context cn, String s, int messageType) {
        LayoutInflater inflater = (LayoutInflater) cn.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastRoot = inflater.inflate(R.layout.custom_toast, null);
        TextView t = toastRoot.findViewById(R.id.text);
        if(messageType==MESSAGE_TYPE_ERROR)
            t.setBackgroundColor(Color.parseColor("#b61827"));
        t.setText(s);
        Toast toast = new Toast(cn);
        toast.setView(toastRoot);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM,
                0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 250;
        int targetHeight = 250;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }

    public static Bitmap getContactDisplayFacePhoto(Context context, String phoneNumber) {

        Uri phoneUri = Uri.withAppendedPath
                (ContactsContract
                                .PhoneLookup
                                .CONTENT_FILTER_URI,
                        Uri.encode(phoneNumber));
        Uri photoUri = null;
        ContentResolver cr = context.getContentResolver();
        Cursor contact = cr.query(phoneUri,
                new String[]{ContactsContract.Contacts._ID}, null, null, null);

        if (contact.moveToFirst()) {
            long userId = contact.getLong
                    (contact.getColumnIndex
                            (ContactsContract.Contacts._ID));
            photoUri = ContentUris.withAppendedId
                    (ContactsContract.Contacts.CONTENT_URI, userId);

        } else {
            //android.R.drawable.ic_menu_report_image
            Bitmap defaultPhoto =
                    BitmapFactory.decodeResource
                            (context.getResources(),
                                    R.drawable.operator);
            return defaultPhoto;
        }
        if (photoUri != null) {
            InputStream input =
                    ContactsContract
                            .Contacts
                            .openContactPhotoInputStream(
                                    cr, photoUri);
            if (input != null) {
                return BitmapFactory.decodeStream(input);
            }
        } else {
            //android.R.drawable.ic_menu_report_image
            Bitmap defaultPhoto =
                    BitmapFactory.decodeResource
                            (context.getResources(),
                                    R.drawable.operator);
            return defaultPhoto;
        }
        //android.R.drawable.ic_menu_report_image
        Bitmap defaultPhoto =
                BitmapFactory.decodeResource
                        (context.getResources(),
                                R.drawable.operator);
        contact.close();
        return defaultPhoto;
    }

    public static String getCompleteAddress(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Locale lHebrew = new Locale("fa");
        Geocoder geocoder = new Geocoder(context, lHebrew);
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {

                Address address = addresses.get(0);
                String street = address.getThoroughfare();
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
//				String country    = addresses.get(0).getCountryName();
//				String postalCode = addresses.get(0).getPostalCode();
//				String knownName  = addresses.get(0).getFeatureName();

                if (street != null)
                    strAdd = city + " - " + street;
                else
                    strAdd = city;
            } else {
            }

        } catch (Exception e) {
        }
        return strAdd;
    }

    @Singleton
    public static boolean isNetworkConnection(Context context) {
        ConnectivityManager con =
                (ConnectivityManager)
                        context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = con.getActiveNetworkInfo();
        if (info == null)
            return false;
        else
            return true;
    }

    public Dialog onProgress_Dialog(Context context, String text) {

        ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage(setTypeFace(context, text));
        progress.setCancelable(false);
        // progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.show();
        return progress;
    }

    public void editor(String a, String b) {
        Application.editor.putString(a, b);
        Application.editor.apply();
        Application.editor.commit();
    }

    public void vectorRight(Context cn, View view, int a) {
        Drawable drawable = AppCompatResources.getDrawable(cn, a);
        if (view instanceof TextView)
            ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        else if (view instanceof EditText)
            ((EditText) view).setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        else if (view instanceof RadioButton)
            ((RadioButton) view).setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public void checkDrawOverlayPermission(final Activity ac) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            final SweetAlertDialog dialog = new SweetAlertDialog(ac, SweetAlertDialog.NORMAL_TYPE);
            dialog.setTitleText("مجوز دسترسی");
            dialog.setContentText("برای استفاده از برنامه لازم است اجازه ترسیم برنامه بر روی برنامه ها دیگر را بدهید.");
            dialog.setConfirmText("باشه");
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
//                    if (!Settings.canDrawOverlays(ac)) {
//                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                                Uri.parse("package:" + ac.getPackageName()));
//                        ac.startActivityForResult(intent, 123);
//                    }
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(true);
            dialog(dialog);
        }
    }

    public void dialog(Dialog dialog) {
        try {
            Window window = dialog.getWindow();
            ViewGroup.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes((WindowManager.LayoutParams) params);
            window.setGravity(Gravity.CENTER);
            dialog.show();
        } catch (Exception e) {
        }
    }

    public void setTypeFace(ViewGroup viewGroup, Typeface typeface) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(Application.font);
                ((TextView) view).setTextSize(13);
                //((TextView) view).setSingleLine();
            }
            if (view instanceof ViewGroup) {
                setTypeFace(((ViewGroup) view), typeface);
            }
        }
    }

    public void myCustomSnackbar(Snackbar snackbar) {
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_action);
        textView.setGravity(Gravity.LEFT);
        TextView textView2 = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView2.setTypeface(Application.font);
        textView.setTypeface(Application.font);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    public void display(final Context ctx, final View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Animator animator = ViewAnimationUtils.createCircularReveal(v,
                    v.getWidth() - Utils.dpToPx(ctx, 56),
                    Utils.dpToPx(ctx, 23),
                    0,
                    (float) Math.hypot(v.getWidth(), v.getHeight()));
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    v.setEnabled(true);
                    /*if (v instanceof EditText)
                        ((InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);*/
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            v.setVisibility(View.VISIBLE);
            if (v.getVisibility() == View.VISIBLE) {
                animator.setDuration(500);
                animator.start();
                v.setEnabled(true);
            }
        } else {
            v.setVisibility(View.VISIBLE);
            //v.setEnabled(true);
            /*if (v instanceof EditText)
                ((InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);*/
        }
    }

    public void hide(final Context ctx, final View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Animator animatorHide = ViewAnimationUtils.createCircularReveal(v,
                    v.getWidth() - Utils.dpToPx(ctx, 56),
                    Utils.dpToPx(ctx, 23),
                    (float) Math.hypot(v.getWidth(), v.getHeight()),
                    0);
            animatorHide.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    v.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animatorHide.setDuration(500);
            animatorHide.start();
        } else {
            v.setVisibility(View.GONE);
        }
    }

    public SpannableStringBuilder setTypeFace(Context cn, String s)

    {
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(s);
        ssbuilder.setSpan(new CustomTypefaceSpan("fonts/IRANSansMedium.ttf", cn), 0, ssbuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return ssbuilder;
    }

    public void onOpenPage(Context context, @SuppressWarnings("rawtypes") Class tow_class) {
        Intent intent = new Intent(context, tow_class);
        context.startActivity(intent);
    }

    public void Fancy(Context context, View v, String txt, String id) {
        new FancyShowCaseView.Builder((Activity) context)
                .focusOn(v)
                .enableAutoTextPosition()
                .showOnce(id)
                .backgroundColor(Color.parseColor("#DC048b7e"))
                .title(txt)
                .build()
                .show();
    }


    public String toEnglishNumber(String text) {
        if ("".equals(text)) return "";
        String ch, str = "";
        int i = 0;
        while (text.length() > i) {
            ch = String.valueOf(text.charAt(i));
            if (TextUtils.isDigitsOnly(ch)) str += englishNumbers[Integer.parseInt(ch)];
            else str += ch;
            i++;
        }
        return str;
    }

    public void openFragment(Activity activity, Fragment fragment) {
        String fragmentTag = fragment.getClass().getSimpleName();
        FragmentManager fragmentManager = ((AppCompatActivity) activity)
                .getSupportFragmentManager();

        /*boolean fragmentPopped = fragmentManager
                .popBackStackImmediate(fragmentTag, 0);*/

        FragmentTransaction ftx = fragmentManager.beginTransaction();

       /*if ((!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null))
            ftx.addToBackStack(fragment.getClass().getSimpleName());*/

        ftx.setCustomAnimations(R.anim.slide_in_right,
                R.anim.slide_out_left, R.anim.slide_in_left,
                R.anim.slide_out_right);
        //ftx.replace(R.id.frame, fragment, fragmentTag);
        ftx.commit();
    }
}