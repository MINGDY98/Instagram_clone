package com.example.instagram_clone;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class MenuAddFeed extends Fragment {
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS    = 3; //여러 권한체크를 위한
    private static final int READ_REQUEST_CODE                  = 42;//갤러리에서 이미지 가져오기 위한
//    private static final int REQUEST_IMAGE_CAPTURE              = 1; //카메라 촬영을 위한.
    private static final int REQUEST_TAKE_PHOTO                 = 1; //
    private ArrayList<String> permissions = new ArrayList();
    private View view;
    private ImageButton load_feed_image;//이미지 미리보기
    private EditText feed_contents;
    public MenuAddFeedAppBar menuAddFeedAppBar;//menuAddFeedAppBar넘기기
    protected Uri uri;//앨범선택했을때 사진의 uri
    private String currentPhotoPath;
    private StorageReference mStorageRef;//firebase storage사용
    private FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();//firebase database사용
//    private String feed_num;//피드번호
//    private int num=0;//피드번호에추가할 전역변수
    private String parent;//힘겹게 얻은 firebase database의 부모 name
    MainActivity mainActivity = new MainActivity();

    public MenuAddFeed(MenuAddFeedAppBar menuAddFeedAppBar) {
        menuAddFeedAppBar=menuAddFeedAppBar;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_feed, container, false);
        //카메라 & 외부저장소 접근 권한 가져오기
        checkPermission();
        load_feed_image = view.findViewById(R.id.ImageButton_load_feed_image);
        load_feed_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();//앨범선택,사진촬영,취소 용 alertDialog
            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference();//firebase storage 사용
        feed_contents = view.findViewById(R.id.EditText_feed_contents);
        return view;
    }

    public boolean checkPermission() {
        //카메라 권한 & 외부 저장소 접근 권한
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    Toast.makeText(getActivity(), "카메라 사용을 위해 권한 허용 필요", Toast.LENGTH_SHORT).show();
                }
            }
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getActivity(), "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
                }
            }
            if (!permissions.isEmpty()) {
                ActivityCompat.requestPermissions(getActivity(), permissions.toArray(new String[permissions.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    public void alertDialog(){
        AlertDialog.Builder alert_dialog = new AlertDialog.Builder(getContext());
        alert_dialog.setTitle("사진 업로드").setCancelable(false)
                .setPositiveButton("사진촬영", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("알림", "다이얼로그 > 사진촬영 선택");
                        // 사진 촬영 클릭
                        takePhoto();
                    }
                }).setNeutralButton("앨범선택", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Log.d("알림", "다이얼로그 > 앨범선택 선택");
                        //앨범에서 선택
                        selectAlbum();
                    }
                }).setNegativeButton("취소   ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("알림", "다이얼로그 > 취소 선택");
                        // 취소 클릭. dialog 닫기.
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alert_dialog.create();
        alert.show();
    }

    public void selectAlbum(){
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);//opened가능한거만열기
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    public void takePhoto() {
        Log.d(TAG, "happy: " + "1들어옴");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.instagram_clone",
                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.putExtra(String.valueOf(Uri.fromFile(photoFile)),photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
            else{
                Log.d(TAG, "happy: " + "photofile없음");
            }
        }
    }

    public Uri getUri() {
        Log.d("smile",uri.toString());
        return uri;
    }

    @Override//문서를 선택한 후에 호출되는 함수
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {
            //uri가져오기
            if(resultData!=null) {
                uri = null;
                uri = resultData.getData();
                Log.d(TAG, "Uri: " + uri.toString());
                load_feed_image.setImageURI(uri);//이미지버튼에 붙이기
                load_feed_image.setScaleType(ImageButton.ScaleType.FIT_XY);
            }
        }
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.d(TAG, "happy: " + "2들어옴");
//            Uri uri = null;//
            if(resultData!=null) {

                Log.d(TAG, "happy: " + "3들어옴");
                Bundle extras = resultData.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                load_feed_image.setImageBitmap(imageBitmap);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                //sw
                SaveBitmapToFileCache(imageBitmap,
                        getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath(),
                        timeStamp+".jpg");
//                galleryAddPic();//사진 갤러리에 제대로 넣기
//                uri = resultData.getData();
//                load_feed_image.setImageURI(uri);//이미지버튼에 붙이기

            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        File image = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        System.out.println("happy"+storageDir.getAbsolutePath());
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    /*
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }
*/

    //sw
    public static void SaveBitmapToFileCache(Bitmap bitmap, String strFilePath, String filename) {
        File file = new File(strFilePath);
        if (!file.exists())
            file.mkdirs();//이게뭔가여?

        File fileCacheItem = new File(strFilePath + filename);
        OutputStream out = null;
        try {

            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
            System.out.println(fileCacheItem.getAbsolutePath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void uploadFile(){//firebase storage에 삽입

//        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference()
                .child(uri.getLastPathSegment());
        storageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        // EditText에서 이미지 이름을 가져 와서 문자열 변수에 저장합니다.
                        String TempImageName = feed_contents.getText (). toString (). trim ();
                        String downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                        final DtoFeed dtoFeed = new DtoFeed();//Dtofeed

                        // Find all users which match the child node email.
                        DatabaseReference ref = firebaseDatabase.getReference("profiles");
                        ref.orderByChild("profile_num").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                    Log.d("fullmoon","이건가"+childSnapshot.child("profile_name").getValue().toString());
                                    parent=childSnapshot.child("profile_name").getValue().toString();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        dtoFeed.setProfile_name(parent);
                        dtoFeed.setFeed_picture(downloadUrl);
                        dtoFeed.setFeed_contents(TempImageName);
                        firebaseDatabase.getReference().child("feeds").push().setValue(dtoFeed);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(getActivity(), "실패했습니다 와이파이 연결상태 체크 필요"+exception.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("smile",exception.getMessage());
                    }
                });
    }

}
