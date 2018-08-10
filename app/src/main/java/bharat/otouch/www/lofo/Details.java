package bharat.otouch.www.lofo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {
    TextView id1,n,e;
    ImageView iv;
    Button b;
    String string;
    String name,email,image,id;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        n=(TextView)findViewById(R.id.name);
        e=(TextView)findViewById(R.id.email);
        iv=(ImageView)findViewById(R.id.img);
        b=(Button)findViewById(R.id.bt);
        //

       // Log.d("TAG",image);


        n.setText("Product Name  " + getIntent().getStringExtra("name"));
        e.setText("Email  " + getIntent().getStringExtra("email"));

        Picasso.with(this).load(getIntent().getStringExtra("image"))
                // .placeholder(R.drawable.ic_img_error)
                // .error(R.drawable.ic_img_error)
                .resize(80,80)
                .into(iv);
    b.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Details.this,Proof.class);
            startActivity(intent);
        }
    });
    }
}
