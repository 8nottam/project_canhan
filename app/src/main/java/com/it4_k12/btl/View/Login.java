package com.it4_k12.btl.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.it4_k12.btl.Adapter.CartAdapter;
import com.it4_k12.btl.Controller.DAO;
import com.it4_k12.btl.Model.User;
import com.it4_k12.btl.R;
import com.it4_k12.btl.View.Fragment.GioHangA;
import com.it4_k12.btl.View.Fragment.MainActivity;

public class Login extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonRegister;
    private TextView textViewError;
    private DAO dao = new DAO();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewError = findViewById(R.id.textViewError);




        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_dangky();
            }
        });
    }

    private void bt_dangky() {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }

    private void login() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            textViewError.setText("Please enter both email and password.");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }

        try {
            User user = dao.login(username, password);

            if (user != null) {
                // Lưu thông tin người dùng vào SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("userId", user.getMaUser());
                editor.putString("userRole", user.getVaiTro()); // Lưu vai trò
                editor.apply();


                // Chuyển đến MainActivity với thông tin người dùng
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Invalid credentials
                textViewError.setText("Email hoặc mật khẩu không hợp lệ!");
                textViewError.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.e("Login", "Login failed", e);
            textViewError.setText("Đã xảy ra lỗi. Vui lòng thử lại!");
            textViewError.setVisibility(View.VISIBLE);
        }
    }
}
