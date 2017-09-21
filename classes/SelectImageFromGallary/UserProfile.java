 public class UserProfile extends AppCompatActivity implements View.OnClickListener {
    private Uri uri;
    ImageView companyLogoUserProfile;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        companyLogoUserProfile = (ImageView) findViewById(R.id.company_logo_user_profile);
        addProfilePictureFab = (FloatingActionButton) findViewById(R.id.fab_user_profile);
            addProfilePictureFab.setOnClickListener(this);
}//onCreate 


@Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.company_logo_user_profile:
                // show custom dialog with user profile image
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.image_layout);

                // set the custom dialog components - text, image and button
                ImageView cancelButton = (ImageView) dialog.findViewById(R.id.cancel_image_layout);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView_image_layout);

//                imageView.setImageBitmap(loadFromFile());
                Bitmap bitmap = loadFromFile();
                if (bitmap == null)
                    imageView.setImageResource(R.drawable.pro_user);
                else
                    imageView.setImageBitmap(bitmap);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                //set full screen view of the window.
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                try {
                    lp.copyFrom(dialog.getWindow().getAttributes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.show();
                dialog.getWindow().setAttributes(lp);
                break;

            case R.id.fab_user_profile:
//                getPermission();
                if (checkPermission(UserProfile.this)) {
                    //pick image intent
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
                break;

            case R.id.done_button_user_profile:
                //done  button
                saveUserProfile();//your method
                break;
        }//switch

    }//onClick
    
    
     @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //on image selected
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            uri = data.getData();
            try {
                ProgressDialog pd = new ProgressDialog(UserProfile.this);
                pd.setMessage("loading...");
                pd.show();

                //get bitmap from uri
                Bitmap b = HelperStaticMethods.getBitmapFromUriNew(uri, UserProfile.this);
//                Bitmap resized = ThumbnailUtils.extractThumbnail(b, 600, 600);
//                resized.compress(Bitmap.CompressFormat.PNG, 100, new ByteArrayOutputStream());
                if (checkExternalMedia())
                    saveImage(b);
                else
                    Toast.makeText(this, getString(R.string.cannot_save_image), Toast.LENGTH_SHORT).show();

                companyLogoUserProfile.setImageBitmap(b);

//                setVibrantColor();
                pd.cancel();
            } catch (Exception e) {
                Toast.makeText(UserProfile.this, getString(R.string.error_loading_image), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onActivityResult: " + e.getMessage());
            }
        }
    }

    /**
     * Method to check whether external media available and writable. This is
     * adapted from
     * http://developer.android.com/guide/topics/data/data-storage.html
     * #filesExternal
     */
    private boolean checkExternalMedia() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
//        Toast.makeText(this, "\nExternal Media: readable=" + mExternalStorageAvailable
//                + " writable=" + mExternalStorageWriteable, Toast.LENGTH_SHORT).show();

        return mExternalStorageAvailable;

    }


    private void saveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/" + "Time Tracker");//do not change this to string
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "profile.png";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showUserInfo() {
try {
//            Bitmap resized = ThumbnailUtils.extractThumbnail(HelperStaticMethods.
//                    getBitmapFromUri(userInfo.getUri(), UserProfile.this), 600, 600);

            Bitmap bitmap = loadFromFile();//get user bitmap from saved folder
            if (bitmap == null)
                //if bitmap null set default resource else bitmap
                companyLogoUserProfile.setImageResource(R.drawable.pro_user);
            else
                companyLogoUserProfile.setImageBitmap(bitmap);

        } catch (NullPointerException e) {
            //  Toast.makeText(this, getString(R.string.error_loading_image), Toast.LENGTH_SHORT).show();

        }

}//showUserInfo

    //load bitmap from file
    public Bitmap loadFromFile() {
//        File sdCard = Environment.getExternalStorageDirectory();
        String root = Environment.getExternalStorageDirectory().toString();

        File directory = new File(root + "/" + "Time Tracker");//do not change this to string

        //using .png format
        File file;
        file = new File(directory, "profile.png"); //or any other format supported}

        FileInputStream streamIn = null;
        Bitmap bitmap = null;
        try {
            streamIn = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(streamIn); //This gets the image

            streamIn.close();
        } catch (IOException e) {
            //if any error occur then retry fetching image.
            file = new File(directory, "profile.jpg"); //or any other format supported
            Log.d(TAG, "directory: " + directory + "file: " + file);
            try {
                streamIn = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(streamIn); //This gets the image
                streamIn.close();
            } catch (IOException e2) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

