package com.example.babarmustafa.chatapplication;

/**
 * Created by BabarMustafa on 11/8/2016.
 */

public class extra {
    //
//                database.child("User-Conversation").child(mAuth.getCurrentUser().getUid()).child(friend_uid_on_clicked).child("push1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Conver c = dataSnapshot.getValue(Conver.class);
////                        User email = new User(data.getUID(),data.getPassword(), data.getName(), data.getEmail(), data.getGEnder(),data.getProfile_image());
//                        Conver e = new Conver(c.getConversationId());
//                        Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
////
//                if (e.equals(null)){
//                    push1 = database.getRef().push().getKey();
//                    database.child("User-Conversation").child(mAuth.getCurrentUser().getUid()).child(friend_uid_on_clicked).setValue(hashObj);
//                    database.child("User-Conversation").child(friend_uid_on_clicked).child(mAuth.getCurrentUser().getUid()).setValue(hashObj);
//                }
//                else {
//                    database.child("Conversation").child("e").addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                            // whenever Data at this location is updated.
//                            NotificationMessage conv = dataSnapshot.getValue(NotificationMessage.class);
//
//                            NotificationMessage email = new NotificationMessage(conv.getMessage(),conv.getTime());
//                            Toast.makeText(getActivity(), ""+email, Toast.LENGTH_SHORT).show();
//
//
//                        }
//
//                        @Override
//                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//
//                }







    //getting if exists
//                database.child("User-Conversation").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.hasChild(friend_uid_on_clicked)) {
////                            String value = dataSnapshot.getValue();
////                            Log.d("Datasnapshot", dataSnapshot + "");
////                            String xy = dataSnapshot.child(friend_uid_on_clicked).child()
//                            database.child("User-Conversation").child(mAuth.getCurrentUser().getUid()).child(friend_uid_on_clicked).child("push1").addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    Conver c = dataSnapshot.getValue(Conver.class);
////////                        User email = new User(data.getUID(),data.getPassword(), data.getName(), data.getEmail(), data.getGEnder(),data.getProfile_image());
//                                    e = new Conver(c.getConversationId());
//                                    Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//
//                                }
//
//                            });
//                            database.child("Conversation").child(String.valueOf(e)).addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    NotificationMessage conv = dataSnapshot.getValue(NotificationMessage.class);
//
//                                    NotificationMessage email = new NotificationMessage(conv.getMessage(), conv.getTime());
//                                    Toast.makeText(getActivity(), "" + email, Toast.LENGTH_SHORT).show();
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//
//                                }
//                            });
//                        } else {
//                            push1 = database.getRef().push().getKey();
//                            database.child("User-Conversation").child(mAuth.getCurrentUser().getUid()).child(friend_uid_on_clicked).setValue(hashObj);
//                            database.child("User-Conversation").child(friend_uid_on_clicked).child(mAuth.getCurrentUser().getUid()).setValue(hashObj);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
    //////////////////////////////////////

}
