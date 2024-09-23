package com.example.gerenciamentodenotas;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNome, editTextEmail, editTextIdade, editTextDisciplina, editTextNota1, editTextNota2;
    private TextView textViewErro, textViewResumo;
    private Button buttonEnviar, buttonLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextIdade = findViewById(R.id.editTextIdade);
        editTextDisciplina = findViewById(R.id.editTextDisciplina);
        editTextNota1 = findViewById(R.id.editTextNota1);
        editTextNota2 = findViewById(R.id.editTextNota2);
        textViewErro = findViewById(R.id.textViewErro);
        textViewResumo = findViewById(R.id.textViewResumo);
        buttonEnviar = findViewById(R.id.buttonEnviar);
        buttonLimpar = findViewById(R.id.buttonLimpar);

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarFormulario();
            }
        });

        buttonLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparFomr();
            }
        });
    }

    private void validarFormulario() {
        String nome = editTextNome.getText().toString();
        String email = editTextEmail.getText().toString();
        String idadeStr = editTextIdade.getText().toString();
        String disciplina = editTextDisciplina.getText().toString();
        String nota1Str = editTextNota1.getText().toString();
        String nota2Str = editTextNota2.getText().toString();
        double nota1 = 0;
        double nota2 = 0;
        double media;
        String text;

        textViewErro.setVisibility(View.GONE);
        textViewResumo.setVisibility(View.GONE);

        try {
            if (TextUtils.isEmpty(nome)) {
                throw new IllegalArgumentException("O campo de nome está vazio");
            }

            if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                throw new IllegalArgumentException("O email é inválido");
            }

            if (TextUtils.isEmpty(idadeStr) || !idadeStr.matches("\\d+") || Integer.parseInt(idadeStr) <= 0) {
                throw new IllegalArgumentException("A idade deve ser um número positivo");
            }

            if (TextUtils.isEmpty(disciplina)) {
                throw new IllegalArgumentException("O campo de disciplina está vazio");
            }

            nota1 = parseNota(nota1Str, "1º Bimestre");
            nota2 = parseNota(nota2Str, "2º Bimestre");

            media = (nota1 + nota2) / 2;

            text  = "Nome: " + nome + "\n" +
                    "Email: " + email + "\n" +
                    "Idade: " + idadeStr + "\n" +
                    "Disciplina: " + disciplina + "\n" +
                    "Nota 1º Bimestre: " + nota1 + "\n" +
                    "Nota 2º Bimestre: " + nota2 + "\n" +
                    "Média: " + String.format("%.2f", media) + "\n" +
                    "Situação: " + (media >= 6.0 ? "Aprovado" : "Reprovado");

            textViewResumo.setText(text);
            textViewResumo.setVisibility(View.VISIBLE);
        } catch (IllegalArgumentException e) {
            textViewErro.setText(e.getMessage());
            textViewErro.setVisibility(View.VISIBLE);
        }
    }

    private double parseNota(String notaStr, String notaerro) {
        try {
            double valor = Double.parseDouble(notaStr);
            if (valor < 0 || valor > 10) {
                throw new IllegalArgumentException("A nota do " + notaerro + " deve ser um número entre 0 e 10");
            }
            return valor;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A nota do " + notaerro + " deve ser um número válido");
        }
    }

    private void limparFomr() {
        editTextNome.setText("");
        editTextEmail.setText("");
        editTextIdade.setText("");
        editTextDisciplina.setText("");
        editTextNota1.setText("");
        editTextNota2.setText("");
        textViewErro.setVisibility(View.GONE);
        textViewResumo.setVisibility(View.GONE);
    }
}
