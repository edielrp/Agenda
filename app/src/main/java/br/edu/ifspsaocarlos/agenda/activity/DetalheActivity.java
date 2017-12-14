package br.edu.ifspsaocarlos.agenda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.edu.ifspsaocarlos.agenda.R;
import br.edu.ifspsaocarlos.agenda.data.ContatoDAO;
import br.edu.ifspsaocarlos.agenda.model.Contato;


public class DetalheActivity extends AppCompatActivity {
    private Contato c;
    private ContatoDAO cDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("contato")) {
            this.c = (Contato) getIntent().getSerializableExtra("contato");
            EditText nameText = (EditText) findViewById(R.id.editTextNome);
            nameText.setText(c.getNome());
            EditText foneText = (EditText) findViewById(R.id.editTextFone);
            foneText.setText(c.getFone());
            EditText fone2Text = (EditText) findViewById(R.id.editTextFone2);
            fone2Text.setText(c.getFone2());
            EditText emailText = (EditText) findViewById(R.id.editTextEmail);
            emailText.setText(c.getEmail());
            EditText dataAniversario = (EditText) findViewById(R.id.editTextDataAniversario);
            dataAniversario.setText(c.getDataAniversario());
            int pos = c.getNome().indexOf(" ");
            if (pos == -1)
                pos = c.getNome().length();
            setTitle(c.getNome().substring(0, pos));
        }
        cDAO = new ContatoDAO(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (!getIntent().hasExtra("contato")) {
            MenuItem item = menu.findItem(R.id.delContato);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarContato:
                salvar();
                return true;
            case R.id.delContato:
                apagar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void apagar() {
        cDAO.apagaContato(c);
        Intent resultIntent = new Intent();
        setResult(3, resultIntent);
        finish();
    }

    private void salvar() {

        String dataAniversario =
                ((EditText) findViewById(R.id.editTextDataAniversario)).getText().toString();

        if (isDataAniversarioValida(dataAniversario)) {

            String name = ((EditText) findViewById(R.id.editTextNome)).getText().toString();
            String fone = ((EditText) findViewById(R.id.editTextFone)).getText().toString();
            String fone2 = ((EditText) findViewById(R.id.editTextFone2)).getText().toString();
            String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();

            if (c == null)
                c = new Contato();

            c.setNome(name);
            c.setFone(fone);
            c.setFone2(fone2);
            c.setEmail(email);
            c.setDataAniversario(dataAniversario);


            cDAO.salvaContato(c);
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            showSnackBar(getString(R.string.data_invalida));
        }
    }

    private Boolean isDataAniversarioValida(String dataAniversario) {
        String[] dataAniversarioSplit = dataAniversario.split("/");

        if (dataAniversarioSplit.length != 2) {
            return Boolean.FALSE;
        }

        if ((dataAniversarioSplit[0].length() != 2) || (dataAniversarioSplit[1].length() != 2)) {
            return Boolean.FALSE;
        }

        Integer day = Integer.parseInt(dataAniversarioSplit[0]);
        Integer month = Integer.parseInt(dataAniversarioSplit[1]);

        if (isDiaInvalido(day)) {
            return Boolean.FALSE;
        }

        if (isMesInvalido(month)) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private boolean isMesInvalido(Integer mes) {
        return mes < 1 || mes > 12;
    }

    private boolean isDiaInvalido(Integer dia) {
        return dia < 1 || dia > 31;
    }

    private void showSnackBar(String msg) {
        CoordinatorLayout coordinatorlayout = (CoordinatorLayout) findViewById(R.id.coordlayoutdetalhe);
        Snackbar.make(coordinatorlayout, msg,
                Snackbar.LENGTH_LONG)
                .show();
    }
}

