package br.edu.uniredentor.tachegando.fragments;


import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import br.edu.uniredentor.tachegando.R;
import br.edu.uniredentor.tachegando.model.Viagem;
import br.edu.uniredentor.tachegando.utils.ConstantsUtils;
import br.edu.uniredentor.tachegando.utils.FirebaseUtils;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**

 */
public class NovaViagemQrCodeDialogFragment extends DialogFragment implements ZXingScannerView.ResultHandler {

     private double latitude;
     private double longitude;
     private String rota;



    public static NovaViagemQrCodeDialogFragment novaInstancia(double latitude, double longitude) {
        NovaViagemQrCodeDialogFragment fragment = new NovaViagemQrCodeDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(ConstantsUtils.LATITUDE, latitude);
        bundle.putDouble(ConstantsUtils.LONGITUDE, longitude);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nova_viagem_qr_code_dialog, container, false);
        final ZXingScannerView qrCodeScanner = view.findViewById(R.id.qr_code_scan);
        Button buttonSalvarRotaQrCode = view.findViewById(R.id.button_salvar_rota_qrcode);
        latitude = getArguments().getDouble(ConstantsUtils.LATITUDE);
        longitude = getArguments().getDouble(ConstantsUtils.LONGITUDE);

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        qrCodeScanner.setResultHandler(NovaViagemQrCodeDialogFragment.this);
                        qrCodeScanner.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        Toast.makeText(getContext(), "É necessario aceitar a permissão para utilizar o QRCODE", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();

        buttonSalvarRotaQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Viagem viagem = new Viagem();
                               viagem.setLongitude(longitude);
                               viagem.setLatitude(latitude);
                               viagem.setNome(rota);
                               viagem.setIdUsuario("4");
                               viagem.setId("4");
                               FirebaseUtils.salva(viagem);
                               dismiss();

            }
        });




        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void handleResult(Result rawResult) {

        rota = rawResult.getText();
        
    }
}