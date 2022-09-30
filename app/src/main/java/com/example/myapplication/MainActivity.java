package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    Connection connection;
    String ConnectionResult="";
    ImageView imageView;
    public final int[] i = {1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configurationNextButton();
    }

    private void configurationNextButton()
    {
        Button addData = (Button) findViewById(R.id.addData);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddData.class));
            }
        });
    }
    public void GetTextFromSql(View v)
    {
        TextView BaseId = findViewById(R.id.BaseId);
        TextView BaseName = findViewById(R.id.BaseName);
        TextView GeographyPosition = findViewById(R.id.GeografPosition);
        TextView NumberOfParse = findViewById(R.id.NumberOfParts);
        try
        {
            ConectionHellper conectionHellper = new ConectionHellper();
            connection=conectionHellper.connectionClass();

            if(connection!=null)
            {
                Button NextList = findViewById((R.id.NextList));
                Button BackList = findViewById((R.id.BackList));
                Button DelData = findViewById(R.id.IzmData);
                String query0 = "select count(Base_Id) from Base ";
                Statement statement0 = connection.createStatement();
                ResultSet resultSet0 = statement0.executeQuery(query0);
                int c = 0;
                while (resultSet0.next())
                {
                    c=resultSet0.getInt(1);
                }
                BackList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(i[0]!=1)
                        {
                            i[0] = i[0] - 1;
                        }
                    }
                });
                int finalC = c;
                NextList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            if(i[0]!= finalC)
                            {
                                i[0] = i[0] + 1;
                            }
                    }
                });
                Button IzmData = (Button) findViewById(R.id.IzmData);
                IzmData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, IzmData.class));
                    }});
                DelData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            ConectionHellper conectionHellper = new ConectionHellper();
                            connection = conectionHellper.connectionClass();

                            if (connection != null) {

                                String query2 = "DELETE FROM Base WHERE Base_Id = "+i[0]+"";
                                Statement statement2 = connection.createStatement();
                                statement2.execute(query2);
                            } else {
                                ConnectionResult = "Check Connection";
                            }
                        } catch (SQLException throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
                String query = "Select * From Base where Base_Id="+i[0]+"";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    BaseId.setText(resultSet.getString(1));
                    BaseName.setText(resultSet.getString(2));
                    GeographyPosition.setText(resultSet.getString(3));
                    NumberOfParse.setText(resultSet.getString(4));
                    //String image = (resultSet.getString(5));
                    //byte[] bytes = Base64.decode(image, Base64.DEFAULT);
                    //Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    //imageView.setImageBitmap(bitmap);
                }
            }
            else
            {
                ConnectionResult = "Check Connection";
            }
        }
        catch (Exception ex)
        {

        }

    }
    }
