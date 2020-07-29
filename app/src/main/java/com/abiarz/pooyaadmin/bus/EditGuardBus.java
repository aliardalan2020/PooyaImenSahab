package com.abiarz.pooyaadmin.bus;

public class EditGuardBus {
    public final String id,name,family,shift,user,pass,phone;

    public EditGuardBus(String id, String name, String family, String shift, String user, String pass, String phone) {
        this.id= id;
        this.name = name;
        this.family=family;
        this.shift=shift;
        this.user=user;
        this.pass=pass;
        this.phone=phone;
    }
}
