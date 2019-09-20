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

    //servos
    public Servo servoFoundationLeft = null;
    public Servo servoFoundationRight = null;

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

        //esta parte debera ser cambiada dependiendo de si se usaran omniwheels o mecanum
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

        //la posicion default de todos los servos se define a 0 degrees
        servoFoundationLeft.setPosition(0);
        servoFoundationRight.setPosition(0);

        //todos los motores que correran sin encoders
        wheelUpRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheelUpLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheelDownRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheelDownLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}
