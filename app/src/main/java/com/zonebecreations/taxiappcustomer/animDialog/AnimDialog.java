package com.zonebecreations.taxiappcustomer.animDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.airbnb.lottie.LottieAnimationView;
import com.zonebecreations.taxiappcustomer.R;


public class AnimDialog extends Dialog {

    ImageView img;
    LottieAnimationView lotteView;
    TextView txtTitle;
    TextView txtContent;
    Button btn1, btn2;

    public AnimDialog(Context context) {
        super(context);
    }

    public AnimDialog setImage(Drawable drawable) {
        if (img != null) {
            img.setImageDrawable(drawable);
        }
        return this;
    }

    public AnimDialog setAnimation(int animation) {
        if (lotteView != null)
            lotteView.setAnimation(animation);
        return this;
    }

    public AnimDialog setTitle(String title) {
        if (txtTitle != null)
            txtTitle.setText(title);
        return this;
    }

    public AnimDialog setContent(String content) {
        if (txtContent != null)
            txtContent.setText(content);
        return this;
    }

    public AnimDialog setButton1(String buttonText, int textColor, View.OnClickListener onClickListener) {
        if (btn1 != null) {
            btn1.setText(buttonText);
            btn1.setOnClickListener(onClickListener);

            if (textColor != 0)
                btn1.setTextColor(textColor);
        }
        return this;
    }

    public AnimDialog setButton2(String buttonText, int textColor, View.OnClickListener onClickListener) {
        if (btn2 != null) {
            btn2.setText(buttonText);
            btn2.setOnClickListener(onClickListener);

            if (textColor != 0)
                btn1.setTextColor(textColor);
        }
        return this;
    }

    public AnimDialog createAnimatedSingleDialog() {
        setContentView(R.layout.anim_dialog_layout_animated_single);
        lotteView = findViewById(R.id.lotteView);
        lotteView.setAnimation("");
        txtTitle = findViewById(R.id.txtTitle);
        txtContent = findViewById(R.id.txtContent);
        btn1 = findViewById(R.id.btn1);
        return this;
    }

    public AnimDialog createAnimatedDualDialog() {
        setContentView(R.layout.anim_dialog_layout_animated_dual);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lotteView = findViewById(R.id.lotteView);
        txtTitle = findViewById(R.id.txtTitle);
        txtContent = findViewById(R.id.txtContent);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        return this;
    }

    public AnimDialog createNonAnimatedSingleDialog() {
        setContentView(R.layout.anim_dialog_layout_non_animated_single);
        img = findViewById(R.id.img);
        txtTitle = findViewById(R.id.txtTitle);
        txtContent = findViewById(R.id.txtContent);
        btn1 = findViewById(R.id.btn1);
        return this;
    }

    public AnimDialog createNonAnimatedDualDialog() {
        setContentView(R.layout.anim_dialog_layout_non_animated_dual);
        img = findViewById(R.id.img);
        txtTitle = findViewById(R.id.txtTitle);
        txtContent = findViewById(R.id.txtContent);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        return this;
    }
}
