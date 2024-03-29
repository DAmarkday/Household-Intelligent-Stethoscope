package com.myhexaville.login.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.myhexaville.login.FlexibleFrameLayout;
import com.myhexaville.login.R;
import com.myhexaville.login.databinding.ActivityMainBinding;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.myhexaville.login.FlexibleFrameLayout.ORDER_LOGIN_STATE;
import static com.myhexaville.login.FlexibleFrameLayout.ORDER_SIGN_UP_STATE;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    protected static ActivityMainBinding binding;
    protected static boolean isLogin = true;
    private FlexibleFrameLayout fr;

    public MainActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
       // moveTaskToBack(true);
        fr = (FlexibleFrameLayout) findViewById(R.id.wrapper);

        binding.button.startAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fr.setVisibility(View.VISIBLE);
              //  coordinatorLayout.setBackgroundColor(Color.parseColor("#131A56"));
                binding.signUpFragment.setVisibility(INVISIBLE);
                binding.loginFragment.setVisibility(VISIBLE);
            }
        },500); //8000为毫秒单位
        //coordinatorLayout.setVisibility(View.VISIBLE);


//        binding.signUpFragment.setVisibility(INVISIBLE);
//        binding.loginFragment.setVisibility(VISIBLE);

        binding.loginFragment.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
               // binding.signUpFragment.setVisibility(INVISIBLE);
                binding.signUpFragment.setRotation(90);
                binding.wrapper.setDrawOrder(ORDER_LOGIN_STATE);
            }
        });

        isLogin = !isLogin;
        LoginFragment topLoginFragment = new LoginFragment();
        SignUpFragment topSignUpFragment = new SignUpFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.login_fragment, topLoginFragment)
                .replace(R.id.sign_up_fragment, topSignUpFragment)
                .commit();
        binding.loginFragment.setRotation(-90);
        binding.button.setOnSignUpListener(topSignUpFragment);
        binding.button.setOnLoginListener(topLoginFragment);

        binding.button.setOnButtonSwitched(isLogin -> {
            binding.getRoot()
                    .setBackgroundColor(ContextCompat.getColor(
                            this,
                            isLogin ? R.color.colorPrimary : R.color.secondPage));
        });
      //  coordinatorLayout.setVisibility(View.VISIBLE);
//        binding.loginFragment.setVisibility(INVISIBLE);



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.loginFragment.setPivotX(binding.loginFragment.getWidth() / 2);
        binding.loginFragment.setPivotY(binding.loginFragment.getHeight());
        binding.signUpFragment.setPivotX(binding.signUpFragment.getWidth() / 2);
        binding.signUpFragment.setPivotY(binding.signUpFragment.getHeight());
    }

    public void switchFragment(View v) {
        if (isLogin) {
            binding.loginFragment.setVisibility(VISIBLE);
            binding.loginFragment.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.signUpFragment.setVisibility(INVISIBLE);
                    binding.signUpFragment.setRotation(90);
                    binding.wrapper.setDrawOrder(ORDER_LOGIN_STATE);
                }
            });
        } else {
            binding.signUpFragment.setVisibility(VISIBLE);
            binding.signUpFragment.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.loginFragment.setVisibility(INVISIBLE);
                    binding.loginFragment.setRotation(-90);
                    binding.wrapper.setDrawOrder(ORDER_SIGN_UP_STATE);
                }
            });
        }

        isLogin = !isLogin;
        binding.button.startAnimation();
    }



    }