package org.firstinspires.ftc.team9351.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9351.hardware.Hardware;
import org.firstinspires.ftc.team9351.hardware.MecanumWheelsAutonomous;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Autonomo Building Zone", group="Autonomos") //se define que la clase se trata de un teleop con una annotation
public class AutonomoBuildingZone extends LinearOpMode { //la clase extendera a otra llamada 'LinearOpMode'

    //objeto que contiene el hardware del robot
    Hardware hdw;

    private MecanumWheelsAutonomous mecanumWheels;

    @Override
    public void runOpMode(){
        hdw = new Hardware(hardwareMap); //init hardware

        mecanumWheels = new MecanumWheelsAutonomous(hdw);

        telemetry.addData("Sebas", "Dale a play pare que empiece lo bueno"); //manda un mensaje a la driver station
        telemetry.update();

        waitForStart(); //espera hasta que se presione <play> en la driver station

        telemetry.addData("Sebas", "VAMOS CRACK MUEVE ESA FOUNDATION!!!");
        telemetry.update();


        //inician movimientos
        mecanumWheels.strafeRight(1);
        sleep(1000);
        mecanumWheels.stop();
        sleep(1000);
        hdw.servoFoundationDown();
        sleep(1000);
        mecanumWheels.backwards(1);
        sleep(4000);
        hdw.servoFoundationUp();
        sleep(1000);
        mecanumWheels.strafeRight(1);
        sleep(2000);
        mecanumWheels.stop();

        while(opModeIsActive()){
            telemetry.addData("Robot", "*BEEP* Mi labor aqui *BEEP* ha terminado");
            telemetry.update();
        }
    }


}
