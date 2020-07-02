package com.example.indeksadministrator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextPassword;

    private EditText editTextRecipient;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private Button buttonSendEmail;

    private FirebaseAuth firebaseAuth;

    private String email;
    private String password;
    private String recipient;
    private String subject;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        editTextEmail = findViewById(R.id.editTextEnterEmail);
        editTextPassword = findViewById(R.id.editTextEnterPassword);

        editTextRecipient = findViewById(R.id.editTextRecipient);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSendEmail = findViewById(R.id.buttonSendEmail);

        buttonSendEmail.setOnClickListener(this);
    }

    private void sendEmail() {
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        recipient = editTextRecipient.getText().toString().trim();
        subject = editTextSubject.getText().toString().trim();
        message = editTextMessage.getText().toString().trim();

        SendEmailConfiguration smc = new SendEmailConfiguration();
        smc.execute();
    }

    class SendEmailConfiguration extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Properties prop = new Properties();

            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(prop,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(email, password);
                        }
                    });

            try {
                MimeMessage mess = new MimeMessage(session);
                mess.setFrom(new InternetAddress(email));
                mess.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                mess.setSubject(subject);
                mess.setText(message);

                Transport.send(mess);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            finish();
            Toast.makeText(getApplicationContext(), "Twoja wiadomość została wysłana!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }

}
