package com.danasoftprototype.govet.FrontEndVet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.danasoftprototype.govet.FrontEnd.govethome;
import com.danasoftprototype.govet.databinding.ActivityVetMonitorUpdatePetBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class vet_monitor_update_pet extends AppCompatActivity {

    private ActivityVetMonitorUpdatePetBinding binding;

    TextView petname, statusPet, datePet, timePet;
    EditText status;
    CalendarView calendarView;
    TimePicker timePicker;
    Button update,discharge;
    ImageView back;


    String uid;
    String dayPicked;
    String yearPicked;
    String monthPicked;
    String date;
    String statusOfPet = "Admitted";
    String petName1, petName2, petName3;
    private String time;
    private  int t1Hour, t1Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVetMonitorUpdatePetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        petname = binding.monitorpet;
        status = binding.statusPet;
        update = binding.updatevetbut;
        statusPet = binding.monitorStatus;
        back = binding.back;
        discharge = binding.discharge;


        String petName = getIntent().getStringExtra("name");
        String petStatus = getIntent().getStringExtra("status");
        uid = getIntent().getStringExtra("uid");


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateATM = sdf.format(new Date());

        SimpleDateFormat sdt = new SimpleDateFormat("h:mm a");
        String timeATM = sdt.format(Calendar.getInstance().getTime());

        petname.setText("Pet name : " + petName);
        statusPet.setText("Status : " + petStatus);

        date = dateATM;
        time = timeATM;
        statusOfPet = petStatus;

        String uidUser = getIntent().getStringExtra("uid");
        String name = getIntent().getStringExtra("name");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Monitoring");

        ref.child(uidUser).child("Pet1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();

                        petName1 = String.valueOf(dataSnapshot.child("petName").getValue());
                    }
                }

            }
        });

        ref.child(uidUser).child("Pet2").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();

                        petName2 = String.valueOf(dataSnapshot.child("petName").getValue());
                    }
                }

            }
        });
        ref.child(uidUser).child("Pet3").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();

                        petName3 = String.valueOf(dataSnapshot.child("petName").getValue());
                    }
                }

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        discharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dischargeMethod();
            }
        });


    }

    private void dischargeMethod() {
        String name = getIntent().getStringExtra("name");
        if (name.equals(petName1)){
            dischargePet1();
        }
        else if (name.equals(petName2)){
            dischargePet2();
        }
        else if (name.equals(petName3)){
            dischargePet3();
        }
        else{
            Toast.makeText(this, "Error in discharging " + name, Toast.LENGTH_SHORT).show();
        }

    }

    private void dischargePet1() {
        String uidUser = getIntent().getStringExtra("uid");
        String name = getIntent().getStringExtra("name");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserLogs");
        ref.child(uidUser).push().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> updatedValues = new HashMap<String, Object>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    updatedValues.put(ds.getKey(), ds.getValue());
                }
                statusOfPet  = status.getText().toString();
                statusPet.setText("Status : " + statusOfPet);
                updatedValues.put("name" , name);
                updatedValues.put("status" , "Discharged");
                updatedValues.put("date" , date);
                updatedValues.put("time" , time);
                ref.child(uidUser).push().updateChildren(updatedValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Monitoring");
                        ref.child(uidUser).child("Pet1").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot child: snapshot.getChildren()) {
                                    child.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            finish();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                        Toast.makeText(vet_monitor_update_pet.this, "Discharged", Toast.LENGTH_SHORT).show();
                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void dischargePet2() {
        String uidUser = getIntent().getStringExtra("uid");
        String name = getIntent().getStringExtra("name");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserLogs");
        ref.child(uidUser).push().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> updatedValues = new HashMap<String, Object>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    updatedValues.put(ds.getKey(), ds.getValue());
                }
                statusOfPet  = status.getText().toString();
                statusPet.setText("Status : " + statusOfPet);
                updatedValues.put("name" , name);
                updatedValues.put("status" , "Discharged");
                updatedValues.put("date" , date);
                updatedValues.put("time" , time);
                ref.child(uidUser).push().updateChildren(updatedValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Monitoring");
                        ref.child(uidUser).child("Pet2").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot child: snapshot.getChildren()) {
                                    child.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            finish();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                        Toast.makeText(vet_monitor_update_pet.this, "Discharged", Toast.LENGTH_SHORT).show();
                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void dischargePet3() {
        String uidUser = getIntent().getStringExtra("uid");
        String name = getIntent().getStringExtra("name");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserLogs");
        ref.child(uidUser).push().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> updatedValues = new HashMap<String, Object>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    updatedValues.put(ds.getKey(), ds.getValue());
                }
                statusOfPet  = status.getText().toString();
                statusPet.setText("Status : " + statusOfPet);
                updatedValues.put("name" , name);
                updatedValues.put("status" , "Discharged");
                updatedValues.put("date" , date);
                updatedValues.put("time" , time);
                ref.child(uidUser).push().updateChildren(updatedValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Monitoring");
                        ref.child(uidUser).child("Pet3").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot child: snapshot.getChildren()) {
                                    child.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            finish();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                        Toast.makeText(vet_monitor_update_pet.this, "Discharged", Toast.LENGTH_SHORT).show();
                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void updateDatabase() {

        String name = getIntent().getStringExtra("name");
        if (name.equals(petName1)){
            updatePet1();
        }
        else if (name.equals(petName2)){
            updatePet2();
        }
        else if (name.equals(petName3)){
            updatePet3();
        }
        else{
            Toast.makeText(this, "Error in updating pet status", Toast.LENGTH_SHORT).show();
        }



    }

    private void updatePet1(){
        String uidUser = getIntent().getStringExtra("uid");
        String name = getIntent().getStringExtra("name");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Monitoring");
        ref.child(uidUser).child("Pet1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> updatedValues = new HashMap<String, Object>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    updatedValues.put(ds.getKey(), ds.getValue());
                }
                statusOfPet  = status.getText().toString();
                statusPet.setText("Status : " + statusOfPet);
                updatedValues.put("status" , statusOfPet);
                updatedValues.put("date" , date);
                updatedValues.put("time" , time);
                ref.child(uidUser).child("Pet1").updateChildren(updatedValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        String uidUser = getIntent().getStringExtra("uid");
                        String name = getIntent().getStringExtra("name");


                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserLogs");
                        ref.child(uidUser).push().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Map<String, Object> updatedValues = new HashMap<String, Object>();
                                for (DataSnapshot ds : snapshot.getChildren()){
                                    updatedValues.put(ds.getKey(), ds.getValue());
                                }
                                statusOfPet  = status.getText().toString();
                                statusPet.setText("Status : " + statusOfPet);
                                updatedValues.put("name" , petName1);
                                updatedValues.put("status" , statusOfPet);
                                updatedValues.put("date" , date);
                                updatedValues.put("time" , time);
                                ref.child(uidUser).push().updateChildren(updatedValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(vet_monitor_update_pet.this, "Updated", Toast.LENGTH_SHORT).show();
                                    }
                                });



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void updatePet2(){

        String uidUser = getIntent().getStringExtra("uid");
        String name = getIntent().getStringExtra("name");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Monitoring");
        ref.child(uidUser).child("Pet2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> updatedValues = new HashMap<String, Object>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    updatedValues.put(ds.getKey(), ds.getValue());
                }
                statusOfPet  = status.getText().toString();
                statusPet.setText("Status : " + statusOfPet);
                updatedValues.put("status" , statusOfPet);
                updatedValues.put("date" , date);
                updatedValues.put("time" , time);
                ref.child(uidUser).child("Pet2").updateChildren(updatedValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        String uidUser = getIntent().getStringExtra("uid");
                        String name = getIntent().getStringExtra("name");


                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserLogs");
                        ref.child(uidUser).push().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Map<String, Object> updatedValues = new HashMap<String, Object>();
                                for (DataSnapshot ds : snapshot.getChildren()){
                                    updatedValues.put(ds.getKey(), ds.getValue());
                                }
                                statusOfPet  = status.getText().toString();
                                statusPet.setText("Status : " + statusOfPet);
                                updatedValues.put("name" , petName2);
                                updatedValues.put("status" , statusOfPet);
                                updatedValues.put("date" , date);
                                updatedValues.put("time" , time);
                                ref.child(uidUser).push().updateChildren(updatedValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(vet_monitor_update_pet.this, "Updated", Toast.LENGTH_SHORT).show();
                                    }
                                });



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void updatePet3(){

        String uidUser = getIntent().getStringExtra("uid");
        String name = getIntent().getStringExtra("name");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Monitoring");
        ref.child(uidUser).child("Pet3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> updatedValues = new HashMap<String, Object>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    updatedValues.put(ds.getKey(), ds.getValue());
                }
                statusOfPet  = status.getText().toString();
                statusPet.setText("Status : " + statusOfPet);
                updatedValues.put("status" , statusOfPet);
                updatedValues.put("date" , date);
                updatedValues.put("time" , time);
                ref.child(uidUser).child("Pet3").updateChildren(updatedValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        String uidUser = getIntent().getStringExtra("uid");
                        String name = getIntent().getStringExtra("name");


                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserLogs");
                        ref.child(uidUser).push().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Map<String, Object> updatedValues = new HashMap<String, Object>();
                                for (DataSnapshot ds : snapshot.getChildren()){
                                    updatedValues.put(ds.getKey(), ds.getValue());
                                }
                                statusOfPet  = status.getText().toString();
                                statusPet.setText("Status : " + statusOfPet);
                                updatedValues.put("name" , petName3);
                                updatedValues.put("status" , statusOfPet);
                                updatedValues.put("date" , date);
                                updatedValues.put("time" , time);
                                ref.child(uidUser).push().updateChildren(updatedValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(vet_monitor_update_pet.this, "Updated", Toast.LENGTH_SHORT).show();
                                    }
                                });



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}