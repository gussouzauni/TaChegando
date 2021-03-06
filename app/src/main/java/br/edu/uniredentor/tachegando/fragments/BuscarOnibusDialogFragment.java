package br.edu.uniredentor.tachegando.fragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.maps.GoogleMap;

import java.io.Serializable;
import java.util.List;

import br.edu.uniredentor.tachegando.R;
import br.edu.uniredentor.tachegando.adapter.BuscarOnibusAdapter;
import br.edu.uniredentor.tachegando.model.Viagem;
import br.edu.uniredentor.tachegando.utils.ConstantsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarOnibusDialogFragment extends DialogFragment {
    private GoogleMap mapa;

    @BindView(R.id.recyclerView_buscar_onibus) RecyclerView recyclerViewOnibus;

    public BuscarOnibusDialogFragment setMapa(GoogleMap map) {
        this.mapa = map;
        return this;
    }

    public static BuscarOnibusDialogFragment novaInstancia(List<Viagem> listaDeViagensFiltrada) {
        BuscarOnibusDialogFragment buscarOnibusDialogFragment = new BuscarOnibusDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstantsUtils.LISTAFILTRADA, (Serializable) listaDeViagensFiltrada);
        buscarOnibusDialogFragment.setArguments(bundle);
        return buscarOnibusDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar_onibus_dialog, container, false);
        ButterKnife.bind(this, view);

        List<Viagem> listaFiltrada = (List<Viagem>) getArguments().getSerializable(ConstantsUtils.LISTAFILTRADA);
        BuscarOnibusAdapter adapter = new BuscarOnibusAdapter(listaFiltrada, getContext(),
                mapa, BuscarOnibusDialogFragment.this);

        adapter.atualiza(listaFiltrada);

        recyclerViewOnibus.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewOnibus.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewOnibus.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog buscarOnibusDialog = getDialog();
        if (buscarOnibusDialog != null) {
            buscarOnibusDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
