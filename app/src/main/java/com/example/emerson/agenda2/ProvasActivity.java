package com.example.emerson.agenda2;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.emerson.agenda2.modelo.Prova;
/*
Essa classe gerencia a exibição do fragment, pois todo fragment é vinculado a uma activity.
 */
public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);


        FragmentManager fragmentManager = getSupportFragmentManager(); // gerencia a utilização de fragment
        FragmentTransaction tx = fragmentManager.beginTransaction();// o android exige que se crie uma transação usar fragments

        tx.replace(R.id.frame_principal,new ListaProvasFragment());

        if(estaNoModoPaisagem()){
            tx.replace(R.id.frame_secundario, new DetalhesProvasFragment());
        }

        tx.commit();


    }

    private boolean estaNoModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager manager = getSupportFragmentManager();
        if(!estaNoModoPaisagem()){


            FragmentTransaction tx = manager.beginTransaction();

            DetalhesProvasFragment detalhesFragment = new DetalhesProvasFragment();
            Bundle parametros = new Bundle();
            parametros.putSerializable("prova",prova);

            detalhesFragment.setArguments(parametros);

            tx.replace(R.id.frame_principal,detalhesFragment );
            tx.addToBackStack(null);
            tx.commit();
        }else {
            DetalhesProvasFragment detalhesProvasFragment =
                    (DetalhesProvasFragment) manager.findFragmentById(R.id.frame_secundario);
            detalhesProvasFragment.populaCamposCom(prova);
        }

    }
}
