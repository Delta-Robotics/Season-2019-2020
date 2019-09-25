package org.firstinspires.ftc.team9351.hardware;

import com.qualcomm.robotcore.hardware.Gamepad;

public class MecanumWheels {

    //based on this image: https://i.imgur.com/R82YOwT.png

    //wheel motor power
    public double wheelUpRightPower = 0;
    public double wheelUpLeftPower = 0;
    public double wheelDownRightPower = 0;
    public double wheelDownLeftPower = 0;

    public MecanumWheels(){ }

    public void joystick(Gamepad gamepad1, double speed){
        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x;
        wheelUpRightPower = (r * Math.cos(robotAngle) + rightX) / speed;
        wheelUpLeftPower = (r * Math.sin(robotAngle) - rightX) / speed;
        wheelDownRightPower = (r * Math.sin(robotAngle) + rightX) / speed;
        wheelDownLeftPower = (r * Math.cos(robotAngle) - rightX) / speed;
    }

    public void forward(double speed){
        wheelUpRightPower = 1 / speed;
        wheelUpLeftPower = 1 / speed;
        wheelDownRightPower = 1 / speed;
        wheelDownLeftPower = 1 / speed;
    }

    public void backwards(double speed){
        wheelUpRightPower = -1 / speed;
        wheelUpLeftPower = -1 / speed;
        wheelDownRightPower = -1 / speed;
        wheelDownLeftPower = -1 / speed;
    }

    public void strafeRight(double speed){
        wheelUpRightPower = -1 / speed;
        wheelUpLeftPower = 1 / speed;
        wheelDownRightPower = 1 / speed;
        wheelDownLeftPower = -1 / speed;
    }

    public void strafeLeft(double speed){
        wheelUpRightPower = 1 / speed;
        wheelUpLeftPower = -1 / speed;
        wheelDownRightPower = -1 / speed;
        wheelDownLeftPower = 1 / speed;
    }

    public void turnRight(double speed){
        wheelUpRightPower = -1 / speed;
        wheelUpLeftPower = 1 / speed;
        wheelDownRightPower = -1 / speed;
        wheelDownLeftPower = 1 / speed;
    }

    public void turnLeft(double speed){
        wheelUpRightPower = 1 / speed;
        wheelUpLeftPower = -1 / speed;
        wheelDownRightPower = 1 / speed;
        wheelDownLeftPower = -1 / speed;
    }

    public void tiltRight(double speed){
        wheelUpLeftPower = 1 / speed;
        wheelDownRightPower = 1 / speed;
    }

    public void tiltLeft(double speed){
        wheelDownLeftPower = 1 / speed;
        wheelUpRightPower = 1 / speed;
    }
}
