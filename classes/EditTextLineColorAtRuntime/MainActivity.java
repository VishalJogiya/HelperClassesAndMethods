public class MainActivity extends AppCompatActivity {

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText myEditText = (EditText) view.findViewById(R.id.my_et);
        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.my_fab);

        //to change the color of EditTExt at runtime we use WrappedDrawable
            Drawable wrappedDrawable = DrawableCompat.wrap(addTaskET.getBackground());
            DrawableCompat.setTint(wrappedDrawable, color);
            myEditText.setBackgroundDrawable(wrappedDrawable);
            myEditText.getBackground().mutate().setColorFilter(ContextCompat.getColor(context, R.color.new_red), PorterDuff.Mode.SRC_ATOP);
        
        
        //change color of FAB at runtime
        myFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.new_red)));


   }
   
}
