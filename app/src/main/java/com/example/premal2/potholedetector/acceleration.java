package com.example.premal2.potholedetector;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by premal2 on 7/15/2018.
 */

public class acceleration {
    float x;
    float y;
    float z;
    String time;
    String email_id;
    public acceleration()
    {

    }

    public acceleration(float x,float y,float z,String time,String email_id) {
        this.x = x;
        this.y=y;
        this.z=z;
        this.time=time;
        this.email_id=email_id;
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public float getZ()
    {
        return z;
    }
}
