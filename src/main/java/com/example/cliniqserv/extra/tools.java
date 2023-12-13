package com.example.cliniqserv.extra;

public class tools {
    public static Role intToRole(int i) throws Exception {
        switch (i){
            case 1:
                return Role.Patient;
            case 2:
                return Role.Doctor;
            case 3:
                return Role.Admin;

            default:
                throw new Exception("No role with this code");
        }

    }
}
