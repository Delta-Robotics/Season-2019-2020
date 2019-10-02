package org.firstinspires.ftc.team9351.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//hardware de un robot con 4 llantas, se debera cambiar/añadir cosas en el futuro dependiendo de el caso.
public class Hardware {

    //Constructor
    public Hardware(HardwareMap m){ hwMap = m; }

    //hardwaremap que se obtuvo en el constructor
    public HardwareMap hwMap;

    //llantas
    public DcMotor wheelUpRight = null;
    public DcMotor wheelUpLeft = null;
    public DcMotor wheelDownRight = null;
    public DcMotor wheelDownLeft = null;

    //otros motores
    public DcMotor motorArtiClaw = null;
    public DcMotor motorLift = null;

    //servos
    public Servo servoFoundationLeft = null;
    public Servo servoFoundationRight = null;
    public Servo servoClaw = null;

    //sensores
    //public ColorSensor colorSensor = null; (ejemplo)

    public void createHardware(HardwareMap hdwMap){

        hwMap = hdwMap;

        //se obtienen todos los motores, servos y sensores del hardwaremap dado
        wheelUpRight = hwMap.get(DcMotor.class, "wheelUpRight");
        wheelUpLeft = hwMap.get(DcMotor.class, "wheelUpLeft");
        wheelDownRight = hwMap.get(DcMotor.class, "wheelDownRight");
        wheelDownLeft = hwMap.get(DcMotor.class, "wheelDownLeft");
        servoFoundationLeft = hwMap.get(Servo.class, "servoFoundationLeft");
        servoFoundationRight = hwMap.get(Servo.class, "servoFoundationRight");
        servoClaw = hwMap.get(Servo.class, "servoClaw");
        motorArtiClaw = hwMap.get(DcMotor.class, "motorArtiClaw");
        motorLift = hwMap.get(DcMotor.class, "motorLift");

        //La direccion por default de estos motores/servos sera FORWARD
        wheelUpRight.setDirection(DcMotor.Direction.FORWARD);
        wheelDownRight.setDirection(DcMotor.Direction.FORWARD);
        wheelDownLeft.setDirection(DcMotor.Direction.FORWARD);
        wheelUpLeft.setDirection(DcMotor.Direction.FORWARD);
        //La direccion por default de estos motores sera REVERSE

        //Todos los motores siguientes frenaran cuando su power sea 0
        wheelUpRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wheelUpLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wheelDownRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wheelDownLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //el power de todos los motores se define a 0
        wheelUpRight.setPower(0);
        wheelDownRight.setPower(0);
        wheelUpLeft.setPower(0);
        wheelDownLeft.setPower(0);
        motorArtiClaw.setPower(0);
        motorLift.setPower(0);

        //se define la posicion y direccion por default de estos servos
        servoFoundationLeft.setPosition(0);
        servoFoundationLeft.setDirection(Servo.Direction.FORWARD);
        servoFoundationRight.setPosition(0);
        servoFoundationRight.setDirection(Servo.Direction.FORWARD);

        //todos los motores que correran con encoders
        wheelUpRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelUpLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelDownRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelDownLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void servoFoundationUp(){
        //por calcular
    }

    public void servoFoundationDown(){
        //por calcular
    }

}
