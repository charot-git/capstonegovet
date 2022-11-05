package com.danasoftprototype.govet.FrontEnd;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.danasoftprototype.govet.databinding.ActivityPaymentMethodBinding;

public class paymentMethod extends AppCompatActivity {

    ActivityPaymentMethodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentMethodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}