package com.example.projetoas;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;


public class tela_ajustes extends AppCompatActivity {

    private static final String PREF_NAME_PHOTO = "AppPrefs";
    private static final String PREF_KEY_PROFILE_URI = "profile_image_uri";

    public static final String PREFS_NAME = "SessaoUsuario";
    public static final String KEY_USER_ID = "ID_USUARIO_LOGADO";
    public static final String KEY_USER_NAME = "NOME_USUARIO";
    public static final String KEY_RESPONSAVEL_ID = "ID_RESPONSAVEL";


    private FloatingActionButton fabEditPhoto;
    private TextView optionAccountInfo, optionGuardians, optionGuardiansInfo;
    private ImageButton btnBack;
    private Button btnSairDaConta;
    private ShapeableImageView profileImage;

    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<String> permissionLauncher;

    private BancoControllerContatos db;

    private long usuarioIdLogado = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_ajustes);

        usuarioIdLogado = getIntent().getLongExtra(KEY_USER_ID, -1);

        db = new BancoControllerContatos(this);

        if (usuarioIdLogado == -1) {
            Toast.makeText(this, "Erro: ID de sessão não carregado. Faça login novamente.", Toast.LENGTH_LONG).show();
        }

        setupLaunchers();
        inicializarViews();
        carregarFotoSalva();
        configurarListeners();
    }


    private void setupLaunchers() {
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            final int readPermissionFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                            try {
                                getContentResolver().takePersistableUriPermission(imageUri, readPermissionFlags);
                            } catch (SecurityException e) {
                                e.printStackTrace();
                            }
                            profileImage.setImageURI(imageUri);
                            salvarFotoUri(imageUri);
                            Toast.makeText(this, "Foto de perfil atualizada!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Seleção de imagem cancelada.", Toast.LENGTH_SHORT).show();
                    }
                });

        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        openGallery();
                    } else {
                        Toast.makeText(this, "Permissão de acesso à galeria negada. Não é possível alterar a foto.", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void inicializarViews() {
        fabEditPhoto = findViewById(R.id.fab_edit_photo);
        optionAccountInfo = findViewById(R.id.option_account_info);
        optionGuardians = findViewById(R.id.option_guardians);
        optionGuardiansInfo = findViewById(R.id.option_guardians_info);
        btnBack = findViewById(R.id.btn_back_ajustes);
        btnSairDaConta = findViewById(R.id.btnSairDaConta);
        profileImage = findViewById(R.id.profile_image);
    }


    private void configurarListeners() {

        btnBack.setOnClickListener(v -> onBackPressed());
        fabEditPhoto.setOnClickListener(v -> checkGalleryPermissionAndOpen());

        optionAccountInfo.setOnClickListener(v -> {
            if (usuarioIdLogado != -1) {
                Toast.makeText(this, "Abrindo informações da conta...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(tela_ajustes.this, info_usuarios.class);
                intent.putExtra(KEY_USER_ID, usuarioIdLogado);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Erro: Faça login para ver as informações da conta.", Toast.LENGTH_LONG).show();
            }
        });

        optionGuardians.setOnClickListener(v -> {
            if (usuarioIdLogado != -1) {
                Toast.makeText(this, "Abrindo cadastro de responsáveis...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(tela_ajustes.this, cadastro_de_responsavel.class);
                intent.putExtra(KEY_USER_ID, usuarioIdLogado);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Erro: Faça login para cadastrar responsáveis.", Toast.LENGTH_LONG).show();
            }
        });

        optionGuardiansInfo.setOnClickListener(v -> {
            if (usuarioIdLogado != -1) {
                long responsavelId = db.getPrimeiroResponsavelIdPorUsuario(usuarioIdLogado);

                if (responsavelId != -1) {
                    Toast.makeText(this, "Abrindo detalhes do responsável...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(tela_ajustes.this, InfoResponsavelActivity.class);

                    intent.putExtra(KEY_RESPONSAVEL_ID, responsavelId);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Nenhum responsável encontrado. Cadastre um.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Erro: ID não encontrado. Faça login.", Toast.LENGTH_LONG).show();
            }
        });

        btnSairDaConta.setOnClickListener(v -> realizarLogout());
    }


    private void salvarFotoUri(Uri uri) {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME_PHOTO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(PREF_KEY_PROFILE_URI, uri.toString());
        editor.apply();
    }

    private void carregarFotoSalva() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME_PHOTO, Context.MODE_PRIVATE);
        String uriString = prefs.getString(PREF_KEY_PROFILE_URI, null);

        if (uriString != null) {
            try {
                Uri savedUri = Uri.parse(uriString);
                profileImage.setImageURI(savedUri);
            } catch (Exception e) {
                prefs.edit().remove(PREF_KEY_PROFILE_URI).apply();
                e.printStackTrace();
            }
        }
    }


    private void checkGalleryPermissionAndOpen() {

        String permission;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = Manifest.permission.READ_MEDIA_IMAGES;
        } else {
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            permissionLauncher.launch(permission);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        galleryLauncher.launch(intent);
    }


    private void realizarLogout() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_USER_NAME);
        editor.apply();

        Toast.makeText(this, "Sessão encerrada.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }
}