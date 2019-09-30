package org.firstinspires.ftc.team9351.hardware;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumWheelsAutonomous {

    //based on this image: https://i.imgur.com/R82YOwT.png

    //wheel motor power
    public double wheelUpRightPower = 0;
    public double wheelUpLeftPower = 0;
    public double wheelDownRightPower = 0;
    public double wheelDownLeftPower = 0;

    public Hardware hdw;

    public MecanumWheelsAutonomous(Hardware hdwMap){ this.hdw = hdw; }

    public void forward(double speed){
        wheelUpRightPower = 1 / speed;
        wheelUpLeftPower = 1 / speed;
        wheelDownRightPower = 1 / speed;
        wheelDownLeftPower = 1 / speed;
        setAllMotorPower();
    }

    public void backwards(double speed){
        wheelUpRightPower = -1 / speed;
        wheelUpLeftPower = -1 / speed;
        wheelDownRightPower = -1 / speed;
        wheelDownLeftPower = -1 / speed;
        setAllMotorPower();
    }

    public void strafeRight(double speed){
        wheelUpRightPower = -1 / speed;
        wheelUpLeftPower = 1 / speed;
        wheelDownRightPower = 1 / speed;
        wheelDownLeftPower = -1 / speed;
        setAllMotorPower();
    }

    public void strafeLeft(double speed){
        wheelUpRightPower = 1 / speed;
        wheelUpLeftPower = -1 / speed;
        wheelDownRightPower = -1 / speed;
        wheelDownLeftPower = 1 / speed;
        setAllMotorPower();
    }

    public void turnRight(double speed){
        wheelUpRightPower = -1 / speed;
        wheelUpLeftPower = 1 / speed;
        wheelDownRightPower = -1 / speed;
        wheelDownLeftPower = 1 / speed;
        setAllMotorPower();
    }

    public void turnLeft(double speed){
        wheelUpRightPower = 1 / speed;
        wheelUpLeftPower = -1 / speed;
        wheelDownRightPower = 1 / speed;
        wheelDownLeftPower = -1 / speed;
        setAllMotorPower();
    }

    public void tiltRight(double speed){
        wheelUpLeftPower = 1 / speed;
        wheelUpRightPower = 0;
        wheelDownRightPower = 1 / speed;
        wheelDownLeftPower = 0;
        setAllMotorPower();
    }

    public void tiltLeft(double speed){
        wheelDownLeftPower = 1 / speed;
        wheelDownRightPower = 0;
        wheelUpRightPower = 1 / speed;
        wheelUpLeftPower = 0;
        setAllMotorPower();
    }

    public void stop(){
        wheelDownLeftPower = 0;
        wheelDownRightPower = 0;
        wheelUpRightPower = 0;
        wheelUpLeftPower = 0;
        setAllMotorPower();
    }

    public void setAllMotorPower(){
        hdw.wheelDownLeft.setPower(wheelDownLeftPower);
        hdw.wheelDownRight.setPower(wheelDownRightPower);
        hdw.wheelUpLeft.setPower(wheelUpLeftPower);
        hdw.wheelUpRight.setPower(wheelUpRightPower);
    }
}
