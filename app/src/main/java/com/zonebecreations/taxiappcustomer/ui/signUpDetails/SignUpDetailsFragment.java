package com.zonebecreations.taxiappcustomer.ui.signUpDetails;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zonebecreations.taxiappcustomer.AuthenticateActivity;

import com.zonebecreations.taxiappcustomer.MainActivity;
import com.zonebecreations.taxiappcustomer.R;
import com.zonebecreations.taxiappcustomer.animDialog.AnimDialog;
import com.zonebecreations.taxiappcustomer.databinding.SignUpDetailsFragmentBinding;
import com.zonebecreations.taxiappcustomer.databinding.SignUpFragmentBinding;
import com.zonebecreations.taxiappcustomer.model.Customer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class SignUpDetailsFragment extends Fragment {

    private SignUpDetailsViewModel mViewModel;
    private SignUpDetailsFragmentBinding signUpDetailsFragmentBinding;

    private static final int PICK_PROFILE_IMAGE_REQUEST = 10;
    private static final int CAPTUE_PROFILE_IMAGE_REQUEST = 11;

    private final int WRITE_STORAGE_REQUEST_CODE = 101;

    AnimDialog imagePickerAnimDialog;
    AnimDialog responseAnimDialog;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef;

    private Uri profileImageUri;

    KProgressHUD hud;

    public static SignUpDetailsFragment newInstance() {
        return new SignUpDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        signUpDetailsFragmentBinding = SignUpDetailsFragmentBinding.inflate(inflater, container, false);
        return signUpDetailsFragmentBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        signUpDetailsFragmentBinding.imgNicFront.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        storageRef = FirebaseStorage.getInstance().getReference();

        hud = KProgressHUD.create(requireActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        System.out.println(((AuthenticateActivity) getActivity()).getEmail());

        signUpDetailsFragmentBinding.imgNicFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("okk");
                showNICFrontImagePickerDialog();
            }
        });

        signUpDetailsFragmentBinding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCustomer();
            }
        });

    }


    void showNICFrontImagePickerDialog() {
        if (imagePickerAnimDialog == null) {
            imagePickerAnimDialog = new AnimDialog(requireActivity())
                    .createAnimatedDualDialog()
                    .setAnimation(R.raw.camera)
                    .setTitle("Select Image")
                    .setContent("You can either select an image from the Gallery or Capture an image from the camera.\nCapturing images from camera requires Storage permissions make sure to provide them.")
                    .setButton1("Camera", 0, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkStoragePermissionsAndOpenCamera();
                            imagePickerAnimDialog.dismiss();
                        }
                    })
                    .setButton2("Gallery", 0, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openFileChooser();
                            imagePickerAnimDialog.dismiss();
                        }
                    });
        }

        imagePickerAnimDialog.show();
    }


    private void checkStoragePermissionsAndOpenCamera() {

        boolean permissionStatus = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;

        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_REQUEST_CODE);

        } else {
            dispatchTakePictureIntent();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        System.out.println("onRequestPermissionsResult");
        switch (requestCode) {
            case WRITE_STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(requireActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(requireActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
                        checkStoragePermissionsAndOpenCamera();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    private void dispatchTakePictureIntent() {

        // Check Camera
        if (requireActivity().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, CAPTUE_PROFILE_IMAGE_REQUEST);
        }
    }

    private void openFileChooser() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, PICK_PROFILE_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("onActivityResult");

        if (resultCode == RESULT_OK && data != null) {

            if (requestCode == PICK_PROFILE_IMAGE_REQUEST) { // nic front gallery
                System.out.println(data.getData());
                Glide.with(this).load(data.getData()).into(signUpDetailsFragmentBinding.imgNicFront);

                profileImageUri = data.getData();

            } else if (requestCode == CAPTUE_PROFILE_IMAGE_REQUEST) {  // nic front camera

                Bitmap photo = (Bitmap) data.getExtras().get("data");

                Glide.with(this).load(photo).into(signUpDetailsFragmentBinding.imgNicFront);
                signUpDetailsFragmentBinding.imgNicFront.setScaleType(ImageView.ScaleType.FIT_XY);

                profileImageUri = getImageUri(requireActivity(), photo);

                System.out.println(profileImageUri);

            }

        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public void saveCustomer() {

        hud.show();

        if (responseAnimDialog == null) {
            responseAnimDialog = new AnimDialog(requireActivity());
        }

        Customer customer = new Customer();
        customer.setName(((AuthenticateActivity) getActivity()).getName());
        customer.setEmail(((AuthenticateActivity) getActivity()).getEmail());
        customer.setNic(signUpDetailsFragmentBinding.editNic.getText().toString());
        customer.setGender(signUpDetailsFragmentBinding.gender.getSelectedItem().toString());
        customer.setTp(signUpDetailsFragmentBinding.editMobile.getText().toString());
        customer.setActiveStatus(true);

        String path = System.currentTimeMillis() + ".png";
        StorageReference storageReference = storageRef.child("customerImages/" + path);
        storageReference.putFile(profileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                System.out.println(taskSnapshot.getStorage().getDownloadUrl());
                System.out.println("download url " + storageRef.child("customerImages/" + path).getDownloadUrl());

                customer.setImage(path);

                db.collection("customers").add(customer).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        hud.dismiss();

                        responseAnimDialog.createAnimatedSingleDialog()
                                .setAnimation(R.raw.success)
                                .setTitle("Success")
                                .setContent("")
                                .setButton1(
                                        "Ok", 0, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                responseAnimDialog.dismiss();
                                                Intent intent = new Intent(requireActivity(), MainActivity.class);
                                                startActivity(intent);
                                                requireActivity().finish();
                                            }
                                        })
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        System.out.println("dismissed");
                                    }
                                });
                        responseAnimDialog.show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hud.dismiss();

                        responseAnimDialog.createAnimatedSingleDialog()
                                .setAnimation(R.raw.error)
                                .setTitle("Error")
                                .setContent("")
                                .setButton1(
                                        "Ok", 0, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                responseAnimDialog.dismiss();
                                            }
                                        });
                        responseAnimDialog.show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                hud.dismiss();

                responseAnimDialog.createAnimatedSingleDialog()
                        .setAnimation(R.raw.error)
                        .setTitle("Error")
                        .setContent("")
                        .setButton1(
                                "Ok", 0, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        responseAnimDialog.dismiss();
                                    }
                                });
                responseAnimDialog.show();

            }
        });


    }

}